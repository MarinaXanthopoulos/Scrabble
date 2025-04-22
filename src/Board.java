import java.util.ArrayList;

public class Board {
    private Tile[][] grid;
    private int[][] scoreGrid;

    public Board() {
        grid = new Tile[15][15];
        scoreGrid = new int[15][15];
        initializeScoreGrid();
    }

    private void initializeScoreGrid(){
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                scoreGrid[i][j] = 1;
            }
        }
    }
    public void renderBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (grid[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(grid[i][j].getLetter() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isValidMove(String word, int x, int y, boolean isHorizontal) {
        // Implement validation
        return true;
    }

    public void placeTile(int x, int y, Tile tile) {
        grid[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    public int getScoreMultiplier(int x, int y) {
        return scoreGrid[x][y];
    }
}
