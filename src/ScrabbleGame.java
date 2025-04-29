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
        dealTiles(player1, 7);
        dealTiles(player2, 7);
    }

    // Getters & setters
    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Board getBoard() { return board; }

    // Game Methods //
    // Deal tile's to player
    private void dealTiles(Player p, int numNeeded) {
        for (int i = 0; i < numNeeded; i++) {
            if (!bag.isEmpty()) {
                p.addTile(bag.deal());
            }
        }
    }

    public boolean playWord(String word, int startRow,int startCol, boolean horizontal) {
        // Check if it's an actual word
        if(!dictionary.isValidWord(word)) {
            return false;
        }

        // Check if the word is in a playable spot
        if (!canPlaceWord(word, startRow, startCol, horizontal)) {
            return false;
        }

        int score = placeWord(word, startRow, startCol, horizontal);

        if (score == -1) {
            return false;
        }

        // Add score and refill their hand
        currentPlayer.addScore(score);
        int tilesUsed = countTilesUsed(word, startRow, startCol, horizontal);
        dealTiles(currentPlayer, tilesUsed);

        // Switch to other player for their turn
        nextTurn();
        return true;
    }

    private boolean canPlaceWord(String word, int startRow, int startCol, boolean horizontal) {
        for (int i = 0; i < word.length(); i++) {
            int row = horizontal ? startRow : startRow + i;
            int col = horizontal ? startCol + i : startCol;

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

        // Remove used tiles from the player's hand
        for (Tile t : used) {
            currentPlayer.removeTile(t);
        }

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

    private int countTilesUsed(String word, int startRow, int startCol, boolean horizontal) {
        int used = 0;

        for (int i = 0; i < word.length(); i++) {
            int row = calculateRow(startRow, horizontal, i);
            int col = calculateCol(startCol, horizontal, i);

            Spot spot = board.getSpot(row, col);
            if (spot.getTile() == null) {
                used++;
            }
        }
        return used;
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