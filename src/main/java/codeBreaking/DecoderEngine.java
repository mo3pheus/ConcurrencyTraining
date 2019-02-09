package codeBreaking;

import domain.DecodedResult;
import domain.WordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class DecoderEngine {
    public static final String                      PAGE_BREAKER       = "=================================================================";
    public static final double                      PASSING_PERCENTAGE = 20.0d;
    private             Logger                      logger             = LoggerFactory.getLogger(DecoderEngine.class);
    private             WordValidator               wordValidator;
    private             String                      message;
    private             List<Future<DecodedResult>> resultFutures      = new ArrayList<>();
    private             List<DecodedResult>         decodedResults     = new ArrayList<>();
    private             DecodedResult               bestResult         = null;
    private             ForkJoinPool                forkJoinPool       = null;
    private             int                         offsetLowerBound   = 0;
    private             int                         offsetUpperBound   = 1;

    public DecoderEngine(String message, int offsetLowerBound, int offsetUpperBound) {
        this.message = message;
        int numProcessors = Runtime.getRuntime().availableProcessors();
        forkJoinPool = new ForkJoinPool(numProcessors,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        this.offsetLowerBound = offsetLowerBound;
        this.offsetUpperBound = offsetUpperBound;

        try {
            this.wordValidator = new WordValidator();
        } catch (IOException io) {
            logger.error("IOException in initializing word validation/ english dictionary");
        }
        initializeBreakerTasks();
        concludeAnalysis();
    }

    private void initializeBreakerTasks() {
        for (int offset = offsetLowerBound; offset < offsetUpperBound; offset++) {
            resultFutures.add(forkJoinPool.submit(new CodeBreaker(wordValidator, message, offset)));
        }

        boolean done = false;
        while (!done) {
            for (int i = 0; i < resultFutures.size(); i++) {
                Future<DecodedResult> resultFuture = resultFutures.get(i);
                //non blocking call
                if (resultFuture.isDone()) {
                    try {
                        //blocking call
                        DecodedResult decodedResult = resultFuture.get();
                        if (decodedResult.getWordPercentage() > PASSING_PERCENTAGE) {
                            logger.info("Message decoded with " + decodedResult.getWordPercentage() + "% accuracy");
                            logger.info(PAGE_BREAKER);
                            logger.info("Original Message => " + decodedResult.getOriginalMessage());
                            logger.info(PAGE_BREAKER);
                            logger.info("Decoded message => " + decodedResult.getDecodedMessage());
                            logger.info(PAGE_BREAKER);
                            logger.info("Decoding ascii => " + decodedResult.getAsciiOffset());

                            bestResult = decodedResult;
                            done = true;
                            break;
                        }
                    } catch (InterruptedException ie) {
                        logger.error("Encountered interrupted exception ", ie);
                    } catch (ExecutionException ee) {
                        logger.error("Encountered execution exception ", ee);
                    }
                }
            }

            if (resultFutures.size() == decodedResults.size()) {
                done = true;
            }
        }
        logger.info("Breaker tasks initialized");
    }

    private void concludeAnalysis() {
        if (bestResult == null) {
            Collections.sort(decodedResults);
            bestResult = decodedResults.get(0);
        }

        logger.info(bestResult.toString());
    }
}
