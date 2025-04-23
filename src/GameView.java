import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame implements MouseListener, KeyListener {
    // Window Constants
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 850;
    private final int TILE_SIZE = 23;
    private final int CELL_SIZE = 50;

    // Instance Variables
    private ScrabbleGame game;
    private Image[] tileImages;
    private JButton startButton;
    private int selectedRow = -1, selectedCol = -1;
    private String typedWord = "";
    private boolean horizontal = true;
    private int state;

    public GameView(ScrabbleGame game) {
        this.game = game;

        // Make new arraylist of tileImages to hold each tile picture and cooresponding letter
        tileImages = new Image[26];
        for (int i = 0; i < 26; i++) {
            // Convert numbers 0 - 26 to A-Z uppercase letters
            char c = (char)('A' + i);
            tileImages[i] = new ImageIcon("Resources/tile" + (i + 1) + ".png").getImage();
        }

        // Set up JFrame window
        this.setTitle("Scrabble Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.createBufferStrategy(2);

        // Initialize listeners
        this.addKeyListener(this);
        this.addMouseListener(this);
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
            paintInstructions(g);
        } else if (state == 1) {
            drawBoard(g);
            drawTiles(g);
            drawPlayerInfo(g);
        }
    }

    private void paintInstructions(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("WELCOME TO SCRABBLE", 350, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Click anywhere to START", 370, 240);
        g.drawString("Use arrow keys to type your word.", 340, 270);
        g.drawString("Press SPACE to change direction.", 340, 300);
        g.drawString("Press ENTER to place the word.", 340, 330);
    }

    // Draw the game board as a table of squaes to track position easily
    private void drawBoard(Graphics g) {
        Board board = game.getBoard();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                int x = 50 + col * CELL_SIZE;
                int y = 100 + row * CELL_SIZE;

                // Color square based on special tile
                String status = board.getSpecialStatus(row, col);
                switch (status) {
                    case "triple word": g.setColor(new Color(255, 153, 153)); break;
                    case "double word": g.setColor(new Color(255, 204, 204)); break;
                    case "double letter": g.setColor(new Color(173, 216, 230)); break;
                    case "center": g.setColor(Color.PINK); break;
                    default: g.setColor(new Color(210, 180, 140));
                }

                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                Tile tile = board.getGrid()[row][col];
                if (tile != null) {
                    char letter = tile.getLetter();
                    g.drawImage(tileImages[Character.toUpperCase(letter) - 'A'], x, y, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }
    }

    private void drawTiles(Graphics g) {
        ArrayList<Tile> tiles = game.getCurrentPlayer().getTiles();
        int x = 50;
        int y = WINDOW_HEIGHT - 50;

        for (Tile t : tiles) {
            char c = t.getLetter();
            g.drawImage(tileImages[Character.toUpperCase(c) - 'A'], x, y, CELL_SIZE, CELL_SIZE, null);
            x += CELL_SIZE + 10;
        }
    }

    private void drawPlayerInfo(Graphics g) {
        Player current = game.getCurrentPlayer();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Current Player: " + current.getName(), 700, 50);
        g.drawString("Score: " + current.getScore(), 700, 75);
        g.drawString("Typed Word: " + typedWord, 50, 70);
        g.drawString("Direction: " + (horizontal ? "→ Horizontal" : "↓ Vertical"), 50, 95);
    }

    // MouseListener Methods
    public void mouseClicked(MouseEvent e) {
        if (state == 0) {
            state = 1; // Start game
        } else {
            int x = e.getX() - 50;
            int y = e.getY() - 100;
            selectedRow = y / CELL_SIZE;
            selectedCol = x / CELL_SIZE;
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
        if (state != 1) return;

        char c = e.getKeyChar();

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

    public void keyReleased(KeyEvent e) {}
}
