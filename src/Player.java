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
    public ArrayList<Tile> getTiles() { return tiles; }
    public void addTile(Tile tile) { tiles.add(tile); }
    public void removeTile(Tile tile) { tiles.remove(tile); }

    // Player Methods
    public void playWord(String word, int x, int y,boolean isHorizontal, Board board){
        int wordScore = 0;
        // Set tile on board, draw it and calculate score then remove used tiles from players hand
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            Tile tile = new Tile(letter);
            board.placeTile(x + i, y, tile);
            removeTile(tile);
            wordScore += tile.getPointValue();
        }
        score += wordScore;
    }
}
