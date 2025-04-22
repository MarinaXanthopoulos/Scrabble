import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private ArrayList<String> words;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        while (s.hasNextLine()) {
            words.add(s.nextLine().toLowerCase());
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}