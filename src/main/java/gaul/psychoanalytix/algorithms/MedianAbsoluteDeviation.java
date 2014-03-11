package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;

/**
 * A time series is anomalous if the deviation of its latest data point with respect to the median is X times larger than the median
 * of deviations.
 *
 * @author ashish
 */
class MedianAbsoluteDeviation implements Algorithm {

    private static final int THRESHOLD = 6;

    @Override
    public boolean hasAnomaly(Metric m) {
        double[] values = m.getValues();
        double median = Statistics.median(values);
        if (Double.isNaN(median)) {
            return false;
        }

        double[] deviation = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            deviation[i] = Math.abs(values[i] - median);
        }
        double medianDeviation = Statistics.median(deviation);
        if (Double.isNaN(medianDeviation) || medianDeviation == 0) {
            return false;
        }

        double testStat = deviation[deviation.length - 1] / medianDeviation;
        return testStat > THRESHOLD;
    }

}
