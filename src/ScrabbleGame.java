import java.util.ArrayList;

public class ScrabbleGame {
    // Instance Variables
    private Player player1, player2, currentPlayer;
    private Board board;
    private Dictionary dictionary;
    private TileBag bag;

    // Constructor
    public ScrabbleGame() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        board = new Board();
        dictionary = new Dictionary();
        bag = new TileBag();
        currentPlayer = player1;

        // Deal 7 tiles  to players
        dealTiles(player1);
        dealTiles(player2);
    }

    // Getters & setters
    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Board getBoard() { return board; }

    // Deal tile's to player
    private void dealTiles(Player p) {
        // Make a loop to continuously add tiles to make sure player has 7 at a time
        while (p.getTiles().size() < 7 && !bag.isEmpty()) {
            p.addTile(bag.deal());
        }
    }

    // Place a tile on the board, check if it's a real word, check if the location is allowed
    public boolean playWord(String word, int startRow,int startCol, boolean horizontal) {
        // Check if it's an actual word
        if(!dictionary.isValidWord(word)) {
            return false;
        }

        // Check if the word is in a playable spot
        if (!canPlaceWord(word, startRow, startCol, horizontal)) {
            return false;
        }

        // Calculate the score
        int score = calculateScore(word, startRow, startCol, horizontal);
        placeWord(word, startRow, startCol, horizontal);

        // Add score and refill their hand
        currentPlayer.addScore(score);

        // Switch to other player for their turn
        nextTurn();
        return true;
    }

    private boolean canPlaceWord(String word, int startRow, int startCol, boolean horizontal) {
        for (int i = 0; i < word.length(); i++) {
            // Calculate position on the board
            int row = calculateRow(startRow, horizontal, i);
            int col = calculateCol(startCol, horizontal, i);

            // If it's outside the board, cancel the move
            if (row >= Board.SIZE || col >= Board.SIZE) {
                return false;
            }

            Spot spot = board.getSpot(row, col);
            Tile existing = spot.getTile();

            if (existing != null && existing.getLetter() != word.charAt(i)) {
                return false; // Cannot overwrite a different letter
            }
        }
        return true;
    }

    private int placeWord(String word, int startRow, int startCol, boolean horizontal) {
        // Create array list "used" to add letters to as player makes their words
        ArrayList<Tile> hand = currentPlayer.getTiles();
        ArrayList<Tile> used = new ArrayList<>();
        int score = 0;

        // Loop through each letter in the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Calculate position on the board
            int row = calculateRow(startRow, horizontal, i);
            int col = calculateCol(startCol, horizontal, i);

            Spot spot = board.getSpot(row, col);
            if (spot.getTile() != null) {
                continue;
            }

            // Check each letter with the tiles in the player's hand for a "match" to ensure it's valid
            Tile match = null;
            for (Tile t : hand) {
                if (t.getLetter() == c && !used.contains(t)) {
                    match = t;
                    break;
                }
            }

            // Cancel the move if there's no match because the word can't be made
            if (match == null) {
                return -1;
            }

            // Place the tile on the board and track it
            board.placeTile(match, row, col);
            used.add(match);
            score += match.getPointValue();
        }

        // Remove used tiles from the player's hand and add a new one for what we take away
        for (Tile t : used) {
            currentPlayer.removeTile(t);
        }

        dealTiles(currentPlayer);
        return score;
    }

    private int calculateRow(int startRow, boolean horizontal, int i) {
        if (horizontal) {
            return startRow;
        } else {
            return startRow + i; // Row moves down if vertical
        }
    }

    private int calculateCol(int startCol, boolean horizontal, int i) {
        if (horizontal) {
            return startCol + i;
        } else {
            return startCol;
        }
    }

    // Score calculator based off of if the tiles are on a special square or not
    private int calculateScore(String word, int startRow, int startCol, boolean horizontal) {
        int score = 0;
        int wordMultiplier = 1;

        for (int i = 0; i < word.length(); i++) {
            int row;
            int col;

            if (horizontal) {
                row = startRow;
                col = startCol + i;
            } else {
                row = startRow + i;
                col = startCol;
            }
            Spot spot = board.getSpot(row, col);
            char letter = word.charAt(i);
            int baseValue = bag.getPoints(letter);
            int letterScore = baseValue;

            if (spot.getTile() == null) {
                String special = spot.getSpecial();
                if (special.equals("dl")) {
                    letterScore = letterScore * 2;
                } else if (special.equals("tl")) {
                    letterScore = letterScore * 3;
                } else if (special.equals("dw") || special.equals("center")) {
                    wordMultiplier = wordMultiplier * 2;
                } else if (special.equals("tw")) {
                    wordMultiplier = wordMultiplier * 3;
                }
            }
            score = score + letterScore;
        }
        return score * wordMultiplier;
    }

    public boolean exchangeTiles(char letter) {
        Player current = currentPlayer;
        ArrayList<Tile> hand = current.getTiles();

        Tile tileToRemove = null;

        for (Tile t : hand) {
            if (Character.toUpperCase(t.getLetter()) == Character.toUpperCase(letter)) {
                tileToRemove = t;
                break;
            }
        }

        if (tileToRemove == null) {
            return false;
        }

        // Remove the tile
        current.removeTile(tileToRemove);

        // Deal three new tiles
        for (int i = 0; i < 3; i++) {
            if (!bag.isEmpty()) {
                current.addTile(bag.deal());
            }
        }

        nextTurn();
        return true;
    }

    public void nextTurn() {
        // Swap current player
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame(); // Initialize the game
        GameView gameView = new GameView(game); // Create the game view
    }
}