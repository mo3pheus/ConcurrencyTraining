package codeBreaking;

import bootstrap.Driver;
import domain.WordValidator;
import org.junit.Ignore;
import org.junit.Test;
import util.EncodingUtil;

import java.io.IOException;

public class EncodingUtilTest {

    @Test
    public void encodeString() {
        String content        = "This is a mild encoding.";
        String encodedContent = EncodingUtil.encodeString(content, 5);
        System.out.println(encodedContent);
    }

    @Test
    @Ignore
    public void testWordValidator() {
        Driver.configureConsoleLogging(false);
        try {
            WordValidator wordValidator = new WordValidator();
            wordValidator.listAllWords();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}