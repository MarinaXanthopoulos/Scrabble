import java.awt.*;

public class Spot {
    // Instance Variables
    private int row, col;
    private String type;
    private Tile tile;

    public Spot(int row, int col, String type){
        this.row = row;
        this.col = col;
        this.type = type;
        this.tile = null;
    }

    // Getters & setters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getType() { return type; }
    public Tile getTile() { return tile; }
    public boolean isEmpty() {
        return tile == null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        if (tile != null) {
            tile.setRow(row);
            tile.setCol(col);
        }
    }

}
