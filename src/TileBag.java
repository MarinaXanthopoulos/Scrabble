import javax.swing.*;
import java.util.ArrayList;

public class TileBag {
    private ArrayList<Tile> tiles;
    private int tilesLeft;

    public TileBag() {
        tiles = new ArrayList<>();

        // Make a long string of letters to be used
        String letters = "EEEEEEEEEEEEAAAAAAAAAIIIIIIIIOOOOOOOONNNNNNRRRRRRTTTTTLLLLSSSSUUUUDDDDGGGBBCCMMPPFFHHVVWWYYKJXQZ";
        // Go through each letter in the long string above and make it a tile
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            int value = getPoints(c);
            String filename = "Resources/tile" + (Character.toUpperCase(c) - 'A' + 1) + ".png";
            tiles.add(new Tile(c, value, new ImageIcon(filename)));
        }

        tilesLeft = tiles.size();
        shuffle();
    }

    // Return point value based on what letter it is
    private int getPoints(char letter) {
        switch (Character.toUpperCase(letter)) {
            case 'Q': case 'Z': return 10;
            case 'J': case 'X': return 8;
            case 'K': return 5;
            case 'F': case 'H': case 'V': case 'W': case 'Y': return 4;
            case 'B': case 'C': case 'M': case 'P': return 3;
            case 'D': case 'G': return 2;
            default: return 1;
        }
    }

    public void shuffle() {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            int r = (int)(Math.random() * (i + 1));
            Tile temp = tiles.get(i);
            tiles.set(i, tiles.get(r));
            tiles.set(r, temp);
        }
        tilesLeft = tiles.size();
    }

    public boolean isEmpty() {
        return tilesLeft == 0;
    }

    public Tile deal() {
        if (isEmpty()) return null;
        tilesLeft--;
        return tiles.get(tilesLeft);
    }
}
