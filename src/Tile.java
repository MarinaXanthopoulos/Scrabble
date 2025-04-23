public class Tile {
    private char letter;
    private int pointValue;

    public Tile(char letter, int pointValue) {
        this.letter = letter;
        this.pointValue = pointValue;
    }

    public char getLetter() {
        return letter;
    }

    public int getPointValue() {
        return pointValue;
    }

    public String toString() {
        return letter + " (" + pointValue + ")";
    }
}