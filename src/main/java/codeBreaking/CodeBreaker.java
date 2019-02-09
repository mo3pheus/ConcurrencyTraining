package codeBreaking;

import domain.DecodedResult;
import domain.WordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.EncodingUtil;

import java.util.concurrent.ForkJoinTask;

public class CodeBreaker extends ForkJoinTask<DecodedResult> {
    private Logger        logger        = LoggerFactory.getLogger(CodeBreaker.class);
    private WordValidator wordValidator;
    private String        message;
    private int           asciiOffset;
    private DecodedResult decodedResult = new DecodedResult();

    public CodeBreaker(WordValidator wordValidator, String message, int asciiOffset) {
        this.wordValidator = wordValidator;
        this.message = message;
        this.asciiOffset = asciiOffset;
    }

    @Override
    public DecodedResult getRawResult() {
        return decodedResult;
    }

    @Override
    protected void setRawResult(DecodedResult value) {
        this.decodedResult = value;
    }

    @Override
    protected boolean exec() {
        try {
            Thread.currentThread().setName("codeBreaker_" + asciiOffset);
            String   decodedMessage = EncodingUtil.encodeString(message, asciiOffset);
            String[] decodedWords   = decodedMessage.split(" ");
            double   totalWords     = decodedWords.length;
            double   validWords     = 0.0d;

            for (String word : decodedWords) {
                if (wordValidator.isWordValid(word)) validWords++;
            }

            double accuracy = validWords * 100.0d / totalWords;

            decodedResult.setAsciiOffset(asciiOffset);
            decodedResult.setDecodedMessage(decodedMessage);
            decodedResult.setOriginalMessage(message);
            decodedResult.setWordPercentage(accuracy);

            return true;
        } catch (Exception e) {
            logger.error("Encountered exception when decoding " + message, e);
            return false;
        }
    }
}
