// Spot

import javax.swing.*;
import java.awt.*;

public class Spot {
    // Instance Variables
    private int row, col; // Grid location of the spot (row 0–14, col 0–14)
    private String special; // Regular, triple word, double letter, etc..
    private Tile tile;

    public Spot(int row, int col, String type) {
        this.row = row;
        this.col = col;
        this.special = type;
        this.tile = null;
    }

    // Getters & setters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getSpecial() {
        return special;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isEmpty() {
        return tile == null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        // If a tile is placed, make sure it knows its location too
        if (tile != null) {
            tile.setRow(row);
            tile.setCol(col);
        }
    }

    public void draw(Graphics g, int cellSize, int offsetX, int offsetY, Image[] tileImages) {
        // Convert the grid row/col into pixel x/y positions using offset values
        int x = offsetX + col * cellSize; // X = left offset + width of each cell * column index
        int y = offsetY + row * cellSize; // Y = top offset + height of each cell * row index

        // Change the color of the square depending on its specialty (if any)
        switch (special) {
            case "tw": // Triple word
                g.setColor(new Color(222, 104, 104)); // dark red
                break;
            case "dw": // Double word
                g.setColor(new Color(255, 204, 204)); // light pink
                break;
            case "tl": // Triple letter
                g.setColor(new Color(73, 146, 186)); // dark blue
                break;
            case "dl": // Double letter
                g.setColor(new Color(204, 242, 255)); // light blue
                break;
            case "center":
                g.setColor(new Color(255, 192, 203)); // pink with star
                break;
            default:
                g.setColor(new Color(210, 180, 140)); // tan for regular
                break;
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

        // Assign labels to the square and write them
        if (tile == null) {
            String line1 = "", line2 = "";

            switch (special) {
                case "tw":
                    line1 = "Triple";
                    line2 = "Word";
                    break;
                case "dw":
                    line1 = "Double";
                    line2 = "Word";
                    break;
                case "tl":
                    line1 = "Triple";
                    line2 = "Letter";
                    break;
                case "dl":
                    line1 = "Double";
                    line2 = "Letter";
                    break;
                case "center":
                    line1 = "★";
                    break;
            }

            if (!line1.isEmpty()) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 10));
                // Call fontMetrics to help with sizing of words to not calculate it manually
                FontMetrics fm = g.getFontMetrics();

                if (!line2.isEmpty()) {
                    int textY = y + (cellSize - fm.getHeight() * 2) / 2 + fm.getAscent();
                    int textX1 = x + (cellSize - fm.stringWidth(line1)) / 2;
                    int textX2 = x + (cellSize - fm.stringWidth(line2)) / 2;
                    g.drawString(line1, textX1, textY);
                    g.drawString(line2, textX2, textY + fm.getHeight());
                } else {
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    int textX = x + (cellSize - fm.stringWidth(line1)) / 2;
                    g.drawString(line1, textX, textY);
                }
            }
        }
    }
}
