import javax.swing.*;
import java.awt.*;

public class Spot {
    // Instance Variables
    private int row, col; // Grid location of the spot (row 0–14, col 0–14)
    private String special; // Regular, triple word, double letter, etc..
    private Tile tile;

    public Spot(int row, int col, String type){
        this.row = row;
        this.col = col;
        this.special = type;
        this.tile = null;
    }

    // Getters & setters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getSpecial() { return special; }
    public Tile getTile() { return tile; }
    public boolean isEmpty() { return tile == null; }

    public void setTile(Tile tile) {
        this.tile = tile;
        // If a tile is placed, make sure it knows its location too
        if (tile != null) {
            tile.setRow(row);
            tile.setCol(col);
        }
    }

    public void draw (Graphics g, int cellSize, int offsetX, int offsetY, Image[] tileImages) {
        // Convert the grid row/col into pixel x/y positions using offset values
        int x = offsetX + col * cellSize; // X = left offset + width of each cell * column index
        int y = offsetY + row * cellSize; // Y = top offset + height of each cell * row index

        // Change the color of the square depending on its specialty (if any)
        switch (special) {
            case "triple word": g.setColor(new Color(255, 153, 153)); break;     // light red
            case "double word": g.setColor(new Color(255, 204, 204)); break;     // light pink
            case "double letter": g.setColor(new Color(173, 216, 230)); break;   // light blue
            case "center": g.setColor(Color.PINK); break;                                 // light pink center
            default: g.setColor(new Color(210, 180, 140));                       // tan for regular
        }

        // Draw the colored background of this square
        g.fillRect(x, y, cellSize, cellSize);

        // Draw a black border around the square
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize);

        // If this spot has a tile, draw its image
        if (tile != null) {
            ImageIcon icon = tile.getImageIcon();
            if (icon != null) {
                g.drawImage(icon.getImage(), x, y, cellSize, cellSize, null);
            }
        }
    }
}
