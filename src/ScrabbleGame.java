import java.util.Scanner;

public class ScrabbleGame {
    // Instance Variables
    private Player player1, player2, currentPlayer;
    private Board board;
    private Dictionary dictionary;
    private boolean gameOver;

    // Constructor
    public ScrabbleGame (){
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        board = new Board();
        currentPlayer = player1;
        gameOver = false;
    }

    // Game Methods
    public void startGame(){
        // Start game loop to swap player turns while gameOver is false
        while(!gameOver){
            playTurn(currentPlayer);
            checkGameOver();
            nextTurn();
        }
        // End the game when gameOver is true and someone has won
        displayWinner();
    }

    public void nextTurn(){
        // Swap current player
    }

    public void playTurn(Player player){
        // Print out who's turn it is

        // Print out player's tiles

        // Have player make a word then check if it's valid

        // Calculate and dispplay + update score if it's valid
    }

    public void checkGameOver(){
        if (player1.getTiles().isEmpty() && player2.getTiles().isEmpty()){
            gameOver = true;
        }
    }

    public void displayWinner(){
        Player winner;
        if (player1.getScore() > player2.getScore()){
            winner = player1;
        }
        else if (player1.getScore() < player2.getScore()){
            winner = player2;
        }
        else{
            //tie
        }
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.startGame();
    }
}
