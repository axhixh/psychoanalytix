
package gaul.psychoanalytix;

/**
 *
 * @author ashish
 */
public class Metric {
    private final String name;
    private final long[] timestamps;
    private final double[] values;

    public Metric(String name, long[] timestamps, double[] values) {
        assert (timestamps.length == values.length);
        this.name = name;
        this.timestamps = timestamps;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public long[] getTimestamps() {
        return timestamps;
    }

    public double[] getValues() {
        return values;
    }

}
