import java.util.ArrayList;

public class Board {
    public static final int SIZE = 15;
    public Spot[][] grid;

    public Board() {
        grid = new Spot[SIZE][SIZE];

        // Setup board with types
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String special;
                if (row == 7 && col == 7) {
                    special = "center";
                } else if ((row == 0 || row == 14 || row == 7) && (col == 0 || col == 14 || col == 7)) {
                    special = "triple word";
                } else {
                    special = "regular";
                }
                grid[row][col] = new Spot(row, col, special);
            }
        }
    }


    public Spot getSpot(int row, int col){ return grid[row][col]; }
    public void placeTile(Tile tile, int row, int col) { grid[row][col].setTile(tile); }
    public Spot[][] getSpots() { return grid; }
}
