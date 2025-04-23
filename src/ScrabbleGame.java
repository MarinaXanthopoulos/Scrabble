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

        dealTiles(player1);
        dealTiles(player2);
    }

    // Getters & setters
    public Player getCurrentPlayer() { return currentPlayer; }
    public Board getBoard() { return board; }

    // Game Methods //
    // Deal tile's to player
    private void dealTiles(Player p){
        // Make a loop to continuously add tiles to make sure player has 7 at a time
        // Loop so that you can deal tiles after putting down a letter and use same function
        while (p.getTiles().size() < 7 && !bag.isEmpty()){
            p.addTile(bag.deal());
        }
    }

    public boolean playWord(String word, int startRow,int startCol, boolean horizontal) {
        if(!dictionary.isValidWord(word)) return false;

        // Create array list "used" to add letters to as player makes their words
        ArrayList<Tile> hand = currentPlayer.getTiles();
        ArrayList<Tile> used = new ArrayList<>();
        int score = 0;

        // Loop through each letter in the word
        for (int i = 0; i < word.length(); i ++) {
            char c = word.charAt(i);
            Tile match = null;

            // Check each letter with the tiles in the player's hand for a "match" to ensure it's valid
            for (Tile t : hand) {
                if (t.getLetter() == c && !used.contains(t)) {
                    match = t;
                    break;
                }
            }

            // Cancel the move if there's no match because the word can't be made
            if (match == null) {
                return false;
            }

            // Calculate position on the board
            int row = horizontal ? startRow : startRow + i;
            int col = horizontal ? startCol + i : startCol;

            // If it's outside the board, cancel the move
            if (row >= Board.SIZE || col >= Board.SIZE) {
                return false;
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

        // Add score and refill their hand
        currentPlayer.addScore(score);
        dealTiles(currentPlayer);

        // Switch to other player for thier turn
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