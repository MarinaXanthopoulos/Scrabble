import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame {
    private Image boardImage;
    private Image[] tileImages;
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;
    private final int TILE_SIZE = 40;
    private ScrabbleGame game;

    public GameView(ScrabbleGame game) {
        this.boardImage = new ImageIcon("Resources/scrabble_board.png").getImage();
        this.tileImages = new Image[26]; // A-Z tiles
        for (int i = 0; i < 26; i++) {
            this.tileImages[i] = new ImageIcon("Resources/tiles/" + (char) ('A' + i) + ".png").getImage();
        }
        this.game = game;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Scrabble Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);  // Double buffering
    }

    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    private void myPaint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (boardImage != null) {
            g.drawImage(boardImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        }

        if (game != null && game.getBoard() != null) {
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    Tile tile = game.getBoard().getTile(i, j);
                    if (tile != null) {
                        drawTile(g, tile, i, j);
                    }
                }
            }
        }
    }

    private void drawTile(Graphics g, Tile tile, int x, int y) {
        char letter = tile.getLetter();
        int letterIndex = letter - 'A';
        Image tileImage = tileImages[letterIndex];

        if (tileImage != null) {
            int xPos = x * TILE_SIZE;
            int yPos = y * TILE_SIZE;
            g.drawImage(tileImage, xPos, yPos, TILE_SIZE, TILE_SIZE, null);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(tile.getPointValue()), xPos + 10, yPos + TILE_SIZE - 10);
        }
    }
}
