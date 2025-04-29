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
            String filename = "Resources/tile" + Character.toUpperCase(c) + ".png";
            tiles.add(new Tile(c, value, new ImageIcon(filename)));
        }
        // Add two blank tile
        tiles.add(new Tile(' ', 0, new ImageIcon("Resources/blank.png")));
        tiles.add(new Tile(' ', 0, new ImageIcon("Resources/blank.png")));


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
            // If it's not listed above, return 1 because it's a basic tile
            default: return 1;
        }
    }

    public void shuffle() {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            // Pick random index from 0 to i
            int r = (int)(Math.random() * (i + 1));
            // Swap current tile with random one
            Tile temp = tiles.get(i);
            tiles.set(i, tiles.get(r));
            tiles.set(r, temp);
        }
        // Reset count of tiles left
        tilesLeft = tiles.size();
    }

    public boolean isEmpty() {
        return tilesLeft == 0;
    }

    public Tile deal() {
        // Deal one tile from the top of the shuffled pile
        if (isEmpty()) {
            return null;
        }
        // Reduce total tles and return how many are still left
        tilesLeft--;
        return tiles.get(tilesLeft);
    }
}
