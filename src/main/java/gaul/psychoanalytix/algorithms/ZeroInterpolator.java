package gaul.psychoanalytix.algorithms;

import gaul.psychoanalytix.Metric;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ashish
 */
public class ZeroInterpolator implements Interpolator {

    @Override
    public Metric interpolate(Metric m) {
        long[] timestamps = m.getTimestamps();
        if (timestamps.length < 2) {
            return m;
        }

        long period = findPeriod(timestamps);
        boolean modified = false;
        double[] values = m.getValues();
        List<Long> ts = new LinkedList<>();
        List<Double> val = new LinkedList<>();
        for (int i = 0; i < timestamps.length - 1; i++) {
            ts.add(timestamps[i]);
            val.add(values[i]);
            long gap = (timestamps[i + 1] - timestamps[1]);
            long count = gap / period;
            if (count > 1) {
                for (int j = 0; j < count; j++) {
                    ts.add(j * period + timestamps[i]);
                    val.add(0.0);
                }
                modified = true;
            }
        }
        if (!modified) {
            return m;
        }
        ts.add(timestamps[timestamps.length - 1]);
        val.add(values[values.length - 1]);
        long[] newTimestamps = new long[ts.size()];
        double[] newValues = new double[val.size()];
        int i = 0;
        for (long t : ts) {
            newTimestamps[i++] = t;
        }
        i = 0;
        for (double d : val) {
            newValues[i++] = d;
        }
        return new Metric(m.getName(), newTimestamps, newValues);
    }

    private long findPeriod(long[] timestamps) {
        long period = timestamps[1] - timestamps[0];
        for (int i = 1; i < timestamps.length - 1; i++) {
            period = Math.min(period, timestamps[i + 1] - timestamps[i]);
        }
        return period;
    }
}
