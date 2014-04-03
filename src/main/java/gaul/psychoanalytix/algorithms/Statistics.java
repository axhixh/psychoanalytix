package gaul.psychoanalytix.algorithms;

import java.util.Arrays;

/**
 *
 * @author shresa
 */
abstract class Statistics {

    static double median(double[] values) {
        if (values.length == 0) {
            return 0.0;
        }
        Arrays.sort(values);
        int lhs = (values.length - 1) / 2;
        int rhs = (values.length) / 2;
        if (lhs == rhs) {
            return values[lhs];
        }
        return (values[lhs] + values[rhs]) / 2.0;
    }

    static double mean(double[] values) {
        if (values.length == 0) {
            return Double.NaN;
        }

        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    static double sd(double[] values) {
        if (values.length == 0 || values.length == 1) {
            return Double.NaN;
        }
        double mean = mean(values);
        double total = 0;
        for (double v : values) {
            total += Math.pow((v - mean), 2);
        }
        return Math.sqrt(total / (values.length - 1));
    }
    
    static double tailAvg(double[] values) {
        if (values.length < 3) {
            return 0;
        }
        double sum = 0;
        for (int i = 1; i <= 3; i++) {
            sum += values[values.length - i];
        }
        return sum / 3;
    }
}
