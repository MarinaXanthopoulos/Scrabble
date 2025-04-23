import javax.swing.*;

public class Tile {
    private char letter;
    private int pointValue;
    private ImageIcon image;

    public Tile(char letter, int pointValue, ImageIcon imageIcon) {
        this.letter = letter;
        this.pointValue = pointValue;
        this.image = image;
    }

    public char getLetter() { return letter; }
    public int getPointValue() { return pointValue; }
    public String toString() { return letter + " (" + pointValue + ")"; }
}