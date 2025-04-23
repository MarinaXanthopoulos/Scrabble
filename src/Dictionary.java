import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private ArrayList<String> wordList;

    public Dictionary() {
        wordList = new ArrayList<>();
        loadWords();
    }

    private void loadWords() {
        try {
            File file = new File("Resources/dictionary.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find dictionary file.");
        }
    }

    public boolean isValidWord(String word) {
        return wordList.contains(word.toLowerCase());
    }
}