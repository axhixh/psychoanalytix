
package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;

/**
 *
 * @author ashish
 */
public class StdDevFromAvg implements Algorithm {

    @Override
    public boolean hasAnomaly(Metric m) {
        double[] values = m.getValues();
        double avg = Statistics.mean(values);
        double sd = Statistics.sd(values);
        
        double tailAvg = Statistics.tailAvg(values);
        
        return Math.abs(tailAvg - avg) > (3 * sd);
    }
    
}