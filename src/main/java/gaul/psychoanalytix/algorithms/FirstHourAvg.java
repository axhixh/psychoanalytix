package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;

/**
 * Calculate the simple average over in the first hour of the time series.
 * A time series is anomalous if the average of the last three data points
 * are outside of three standard deviations of this value.
 * 
 * @author ashish
 */
public class FirstHourAvg implements Algorithm {

    @Override
    public boolean hasAnomaly(Metric m) {
        long[] times = m.getTimestamps();
        long threshold = times[0] + 3600 * 1000;
        int maxIndex = 0;
        while (times[maxIndex] < threshold) {
            maxIndex++;
        }
        double[] values = m.getValues();
        double[] filtered = new double[maxIndex];
        System.arraycopy(values, 0, filtered, 0, maxIndex);
        double avg = Statistics.mean(filtered);
        double sd = Statistics.sd(filtered);
        double tailAvg = Statistics.mean(values);
        
        return Math.abs(tailAvg - avg) > 3 * sd;
    }
}