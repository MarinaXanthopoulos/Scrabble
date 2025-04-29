import java.util.ArrayList;

public class Player {
    // Instance variable
    private String name;
    private int score;
    private ArrayList<Tile> tiles;

    // Constructor
    public Player(String name){
        this.name = name;
        this.score = 0;
        this.tiles = new ArrayList<Tile>();
    }

    // Getters & Setters
    public String getName(){ return name; }
    public int getScore(){ return score; }
    public void addScore(int points) { score += points; }
    public ArrayList<Tile> getTiles() { return tiles; }
    public void addTile(Tile tile) { tiles.add(tile); }
    public void removeTile(Tile tile) { tiles.remove(tile); }
}
