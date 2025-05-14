// Board
import java.util.ArrayList;

public class Board {
    // Instance Variables
    public static final int SIZE = 15;
    public Spot[][] grid;

    public Board() {
        grid = new Spot[SIZE][SIZE];

        // Make a 2d array to hold the value of each special thing in the board
        String[][] layout = {
                {"tw", "",  "",  "dl", "",  "",  "",  "tw", "",  "",  "",  "dl", "",  "",  "tw"},
                {"",  "dw", "",  "",  "",  "tl", "",  "",  "",  "tl", "",  "",  "",  "dw", ""},
                {"",  "",  "dw", "",  "",  "",  "dl", "",  "dl", "",  "",  "",  "dw", "",  ""},
                {"dl", "",  "",  "dw", "",  "",  "",  "dl", "",  "",  "",  "dw", "",  "",  "dl"},
                {"",  "",  "",  "",  "dw", "",  "",  "",  "",  "",  "dw", "",  "",  "",  ""},
                {"",  "tl", "",  "",  "",  "tl", "",  "",  "",  "tl", "",  "",  "",  "tl", ""},
                {"",  "",  "dl", "",  "",  "",  "dl", "",  "dl", "",  "",  "",  "dl", "",  ""},
                {"tw", "",  "",  "dl", "",  "",  "",  "center", "",  "",  "",  "dl", "",  "",  "tw"},
                {"",  "",  "dl", "",  "",  "",  "dl", "",  "dl", "",  "",  "",  "dl", "",  ""},
                {"",  "tl", "",  "",  "",  "tl", "",  "",  "",  "tl", "",  "",  "",  "tl", ""},
                {"",  "",  "",  "",  "dw", "",  "",  "",  "",  "",  "dw", "",  "",  "",  ""},
                {"dl", "",  "",  "dw", "",  "",  "",  "dl", "",  "",  "",  "dw", "",  "",  "dl"},
                {"",  "",  "dw", "",  "",  "",  "dl", "",  "dl", "",  "",  "",  "dw", "",  ""},
                {"",  "dw", "",  "",  "",  "tl", "",  "",  "",  "tl", "",  "",  "",  "dw", ""},
                {"tw", "",  "",  "dl", "",  "",  "",  "tw", "",  "",  "",  "dl", "",  "",  "tw"},
        };

        // Setup board with special types
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String type = layout[row][col];
                if (type.equals("")) {
                    type = "regular";
                }
                grid[row][col] = new Spot(row, col, type);
            }
        }
    }

    public Spot getSpot(int row, int col){ return grid[row][col]; }
    public void placeTile(Tile tile, int row, int col) { grid[row][col].setTile(tile); }
    public Spot[][] getSpots() { return grid; }
}
