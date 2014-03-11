package gaul.psychoanalytix;

import gaul.psychoanalytix.algorithms.Analyzer;
import gaul.psychoanalytix.cacofonix.Cacofonix;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Psychoanalytix {

    private static final Logger logger = LogManager.getLogger("psychoanalytix");

    public static void main(String[] args) {
        logger.info("Psychoanalytix");

        AppConfig config = ConfigFactory.create(AppConfig.class,
                System.getProperties(), System.getenv());

        final Cacofonix cacofonix = new Cacofonix(config.cacofonixUrl());
        final Analyzer analyzer = new Analyzer();
        boolean quit = false;
        while (!quit) {
            long start = System.currentTimeMillis();
            analyzer.analyze(cacofonix.getMetrics());
            long timeTaken = System.currentTimeMillis() - start;
            logger.info("Time taken for the run {}", timeTaken);
            long sleep = timeTaken - config.frequency();
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {

                }
            }
        }
    }
}
