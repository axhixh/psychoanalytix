package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;

/**
 *
 * @author shresa
 */
class Grubbs implements Algorithm {

    @Override
    public boolean hasAnomaly(Metric m) {
        double[] values = m.getValues();
        double stdDev = Statistics.sd(values);
        if (Double.isNaN(stdDev) || stdDev == 0.0) {
            return false;
        }
        double mean = Statistics.mean(values);
        double tailAvg = 0;

        // http://en.wikipedia.org/wiki/Grubbs'_test_for_outliers
        // G = (Y - Mean(Y)) / stdDev(Y)
        double zScore = (tailAvg - mean) / stdDev;
        long length = values.length;
	// scipy.stats.t.isf(.05 / (2 * lenSeries) , lenSeries - 2)
        // when lenSeries is big, it eq stats.ZInvCDFFor(1-t)
        double threshold = studentsTQtl(length - 2, 1 - 0.05 / (2.0 * length));
        double thresholdSquared = threshold * threshold;
        double grubbsScore = (double) (length - 1) / Math.sqrt((double) length)
                * Math.sqrt(thresholdSquared / (length - 2 + thresholdSquared));
        return zScore > grubbsScore;
    }

    private double studentsTQtl(double d1, double d2) {
        return 0;
    }
}
