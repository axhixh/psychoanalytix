package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ashish
 */
public class Analyzer {
    private static final Logger logger = LogManager.getLogger("psychoanalytix.analyzer");
    private static final int MIN_NUM_POINTS = Integer.getInteger("psycho.min_num_points",12); // assuming most stats are 5 mins we want at least 1 hr
    
    public void analyze(Collection<Metric> metrics) {
        for (Metric m : metrics) {
            
            analyze(m);
        }
    }

    public void analyze(Metric m) {
        logger.info("Analyzing {}", m.getName());
        double[] values = m.getValues();
        if (values.length < MIN_NUM_POINTS) {
            logger.info("Skipping {} because number of data points is less than minimum threshold", m.getName());
        }
        final Algorithm[] algos = AlgorithmList.getAll();
        for (Algorithm algo : algos) {
            int count = 0;
            if (algo.hasAnomaly(m)) {
                logger.info("{} has an anomaly based on {}", m.getName(), algo.getClass().getSimpleName());
                count++;
            }
            
            if (count > 0.6 * algos.length) {
                logger.info("FOUND ANOMALY");
            }
        }
    }
}
