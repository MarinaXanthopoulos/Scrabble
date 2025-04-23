import java.util.ArrayList;

public class Board {
    private Tile[][] grid;
    private int[][] scoreGrid;
    private ArrayList<String> special;
    public static final int SIZE = 15;

    public Board() {
        grid = new Tile[SIZE][SIZE];
        scoreGrid = new int[SIZE][SIZE];
        special = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (row == 7 && col == 7) {
                    special.add("center");
                } else if ((row == 0 || row == 14 || row == 7) && (col == 0 || col == 14 || col == 7)) {
                    special.add("triple word");
                } else {
                    special.add("regular");
                }
            }
        }
    }


    public Tile[][] getGrid() {
        return grid;
    }

    public void placeTile(Tile tile, int row, int col) {
        grid[row][col] = tile;
    }

    public String getSpecialStatus(int row, int col) {
        return special.get(row * SIZE + col);
    }
}
