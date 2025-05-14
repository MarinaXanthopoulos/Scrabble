// GameView

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame implements MouseListener, KeyListener {
    // Window Constants
    private final int WINDOW_WIDTH = 1010;
    private final int WINDOW_HEIGHT = 1000;
    private final int TILE_SIZE = 23;
    private final int CELL_SIZE = 50;

    // Instance Variables
    private ScrabbleGame game;
    private Image[] tileImages;
    private JButton startButton;
    private int selectedRow = -1, selectedCol = -1;
    private String typedWord = "";
    private boolean horizontal = true;
    private int state; // Holds what window is drawn and when (0 = welcome, 1 = playing, 2 = exchanging)

    // Start & end button instance variables
    private Rectangle startButtonArea;
    private Rectangle endGameButtonArea;

    // Drawbag instance variables
    private Image drawBagImage;
    private Rectangle drawBagArea; // Clickable button

    // Exchange instance variables
    private String exchangeLetter = "";
    private String statusMessage = "";

    public GameView(ScrabbleGame game) {
        this.game = game;

        // Make new arraylist of tileImages to hold each tile picture and cooresponding letter
        tileImages = new Image[26];
        for (int i = 0; i < 26; i++) {
            // Convert numbers 0 - 26 to A-Z uppercase letters
            char c = (char)('A' + i);
            tileImages[i] = new ImageIcon("Resources/tile" + (i + 1) + ".png").getImage();
        }

        // Load draw bag image
        drawBagImage = new ImageIcon("Resources/drawBag.png").getImage();
        drawBagArea = new Rectangle(900, 450, 50, 100);

        // Set up JFrame window
        this.setTitle("Scrabble Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        // Initialize listeners
        this.createBufferStrategy(2);
        this.addKeyListener(this);
        this.addMouseListener(this);

        // Repaint so stuff shows up
        repaint();
    }

    public void paint(Graphics g) {
        // Use double buffering
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null) { return; }

        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        } finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void myPaint(Graphics g) {
        // Clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // State-based rendering
        if (state == 0) {
            paintWelcome(g);
        } else if (state == 1 || state == 2) {
            drawBoard(g);
            drawDrawBag(g);
            drawTiles(g);
            drawPlayerInfo(g);
            drawEndGameButton(g);
            if(state == 2) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("EXCHANGE MODE: Type letter then ENTER", 300, 60);
            }
        } else if (state == 3) {
            paintEndScreen(g);
        }
    }

    private void paintWelcome(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("WELCOME TO SCRABBLE!", 280, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Instructions:", 400, 250);
        g.drawString("- Click START to begin", 380, 280);
        g.drawString("- Type your word using the keyboard", 330, 310);
        g.drawString("- SPACE switches direction", 360, 340);
        g.drawString("- ENTER places the word", 360, 370);

        g.setColor(Color.GREEN);
        startButtonArea = new Rectangle(400, 450, 200, 60);
        g.fillRect(startButtonArea.x, startButtonArea.y, startButtonArea.width, startButtonArea.height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("START!", startButtonArea.x + 35, startButtonArea.y + 40);
    }

    private void paintEndScreen(Graphics g) {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 350, 250);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString(p1.getName() + " Score: " + p1.getScore(), 300, 350);
        g.drawString(p2.getName() + " Score: " + p2.getScore(), 300, 400);

        String winner;
        if (p1.getScore() > p2.getScore()) {
            winner = p1.getName();
        } else if (p2.getScore() > p1.getScore()) {
            winner = p2.getName();
        } else {
            winner = "It's a Tie!";
        }

        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Winner: " + winner, 320, 500);
    }

    // Draw the game board as a table of squaes to track position easily
    private void drawBoard(Graphics g) {
        Board board = game.getBoard();
        // Loop through each spot on the board (15x15)
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                // Each spot draws itself: colored square + tile image if there
                Spot spot = board.getSpot(row, col);
                spot.draw(g, CELL_SIZE, 50, 100, tileImages); // Let the spot handle all drawing
            }
        }
    }

    // Draw the draw bag
    private void drawDrawBag(Graphics g) {
        int bagWidth = 140;
        int bagHeight = 150;
        int bagX = 850;
        int bagY = 400;

        // Draw "clickable" area around the drawbag
        drawBagArea = new Rectangle(bagX, bagY, bagWidth, bagHeight);
        g.drawImage(drawBagImage, bagX, bagY, bagWidth, bagHeight, null);

        // Draw Draw Bag label
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Draw Bag", bagX + 10, bagY + bagHeight + 20);
    }

    private void drawTiles(Graphics g) {
        ArrayList<Tile> tiles = game.getCurrentPlayer().getTiles();
        int x = 50;
        int y = WINDOW_HEIGHT - 100;

        for (Tile t : tiles) {
            ImageIcon icon = t.getImageIcon(); // pull the tile's actual image
            if (icon != null) {
                g.drawImage(icon.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
            }
            x += CELL_SIZE + 10;
        }
    }

    private void drawPlayerInfo(Graphics g) {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Player current = game.getCurrentPlayer();

        g.drawString("Typed Word: " + typedWord, 50, 70);
        g.drawString("Direction: " + (horizontal ? "→ Horizontal" : "↓ Vertical"), 50, 95);

        int infoX = 815;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Scores:", infoX, 80);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(p1.getName() + ": " + p1.getScore(), infoX, 110);
        g.drawString(p2.getName() + ": " + p2.getScore(), infoX, 140);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Current Turn:", infoX, 170);
        g.drawString(current.getName(), infoX + 20, 195);

        // Add instructions below
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Instructions:", infoX, 230);
        g.drawString("- Type your word", infoX, 250);
        g.drawString("- SPACE = change direction", infoX, 270);
        g.drawString("- ENTER = place word", infoX, 290);
        g.drawString("- Click Draw Bag to exchange", infoX, 310);
        g.drawString("- Click End Game to finish", infoX, 330);
        g.drawString("- No blank tiles", infoX, 350);
        g.drawString(" - Build off words, neighboring", infoX, 370);
        g.drawString("   words don't have to be real", infoX, 390);
    }

    private void drawEndGameButton(Graphics g) {
        endGameButtonArea = new Rectangle(800, 900, 150, 50);

        g.setColor(new Color(173, 216, 230));
        g.fillRect(endGameButtonArea.x, endGameButtonArea.y, endGameButtonArea.width, endGameButtonArea.height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("END GAME", endGameButtonArea.x + 15, endGameButtonArea.y + 30);
    }

    // MouseListener Methods
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        if (state == 0) {
            if (startButtonArea != null && startButtonArea.contains(clickX, clickY)) {
                state = 1;
            }
        }
        else if (state == 1 || state == 2) {
            if (endGameButtonArea != null && endGameButtonArea.contains(clickX, clickY)) {
                state = 3; // End Game screen
            }
            else if (drawBagArea != null && drawBagArea.contains(clickX, clickY)) {
                state = 2; // Exchange Mode
                exchangeLetter = "";
                statusMessage = "Type a letter to exchange:";
            }
            else {
                int x = e.getX() - 50;
                int y = e.getY() - 100;
                selectedRow = y / CELL_SIZE;
                selectedCol = x / CELL_SIZE;
            }
        }

        repaint();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    // KeyListener Methods
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (state == 0) {
            return;
        }
        char c = e.getKeyChar();

        if (state == 1) {
            // Letter typing
            if (Character.isLetter(c)) {
                typedWord += Character.toUpperCase(c);
            }

            // Submit word
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!typedWord.isEmpty() && selectedRow >= 0 && selectedCol >= 0) {
                    game.playWord(typedWord, selectedRow, selectedCol, horizontal);
                    typedWord = "";
                    repaint();
                }
            }

            // Change direction
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                horizontal = !horizontal;
            }

            // Backspace
            else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && typedWord.length() > 0) {
                typedWord = typedWord.substring(0, typedWord.length() - 1);
            }
            repaint();
        }
        else if (state == 2) {
            if (Character.isLetter(c)) {
                exchangeLetter = ("" + Character.toUpperCase(c));
                statusMessage = "Exchange Letter: " + exchangeLetter;
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !exchangeLetter.equals("")) {
                boolean success = game.exchangeTiles(exchangeLetter.charAt(0));
                if (success) {
                    statusMessage = "Exchanged letter: " + exchangeLetter;
                } else {
                    statusMessage = "You don't have the letter: " + exchangeLetter;
                }
                exchangeLetter = "";
                state = 1; // Go back to normal playing mode
                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
}
