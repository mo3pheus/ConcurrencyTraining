package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class WordValidator {
    private Set<String> validWords = new HashSet<>();
    private Logger      logger     = LoggerFactory.getLogger(WordValidator.class);

    public WordValidator() throws IOException {
        URL url = ClassLoader.getSystemResource("src/main/resources/words.txt");
        for (String word : Files.readAllLines(Paths.get("/home/sanket/Documents/workspace/ConcurrencyTraining/src/main/resources/words.txt"))) {
            validWords.add(word);
        }
    }

    public void listAllWords() {
        for (String word : validWords) {
            logger.info(word);
        }
    }

    public boolean isWordValid(String word) {
        return validWords.contains(word);
    }
}
