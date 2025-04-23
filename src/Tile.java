import javax.swing.*;

public class Tile {
    private char letter;
    private int pointValue;
    private ImageIcon image;
    private int row, col;

    public Tile(char letter, int pointValue, ImageIcon imageIcon) {
        this.letter = letter;
        this.pointValue = pointValue;
        this.image = image;
    }

    public char getLetter() { return letter; }
    public int getPointValue() { return pointValue; }

    // Make sure Tiles have a location saved in row/col
    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }

    public String toString() { return letter + " (" + pointValue + ")"; }
}