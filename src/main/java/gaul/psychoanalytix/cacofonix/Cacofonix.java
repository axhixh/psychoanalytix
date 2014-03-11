
package gaul.psychoanalytix.cacofonix;

import gaul.psychoanalytix.Metric;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ashish
 */
public class Cacofonix {
    private static final Logger logger = LogManager.getLogger("psychoanalytix.cacofonix");
    private final String url;
    
    public Cacofonix(String url) {
        logger.info("Connecting to Cacofonix server at " + url);
        this.url = url;
    }
    
    public Collection<Metric> getMetrics() {
        Collection<String> ignoredMetrics = Collections.emptySet();
        return getMetrics(ignoredMetrics);
    }
    
    public Collection<Metric> getMetrics(Collection<String> ignored) {
        
        String metricsUrl = url + "/api/metrics/";
        try {
            String content = HttpUtil.get(metricsUrl);
            String[] metricNames = content.split("\n");
            List<Metric> metrics = new ArrayList<>(metricNames.length);
            for (String name : metricNames) {
                if (isIgnored(name, ignored)) {
                    continue;
                }
                Metric metric = load(name);
                metrics.add(metric);
            }
            return metrics;
        } catch (IOException err) {
            logger.warn("Unable to get metrics from cacofonix server.", err);
            return Collections.emptyList();
        }
    }
    
    private boolean isIgnored(String name, Collection<String> ignored) {
        return ignored.contains(name); // TODO do regex match
    }
    
    private Metric load(String name) {
        long end = System.currentTimeMillis();
        long start = end - 30 * 24 * 60 * 60 * 1000L;
        String dataUrl = String.format("%s/api/metrics/%s?start=%d&end=%d", 
                url, HttpUtil.escape(name), start, end);
        try {
            String data = HttpUtil.get(dataUrl);
            String[] lines = data.split("\n");
            double[] values = new double[lines.length];
            long[] times = new long[lines.length];
            int count = 0;
            for (String line : lines) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    try {
                        long t = Long.parseLong(parts[0]);
                        double v = Double.parseDouble(parts[1]);
                        values[count] = v;
                        times[count] = t;
                        count++;
                        
                    } catch (NumberFormatException err) {
                        
                    }
                }
            }
            if (count == 0) {
                return new Metric(name, new long[0], new double[0]);
            }
            double[] v = new double[count];
            System.arraycopy(values, 0, v, 0, count);
            long[] t = new long[count];
            System.arraycopy(times, 0, t, 0, count);
            
            return new Metric(name, t, v);
        } catch (IOException err) {
            logger.warn("Unable to get data points for the metric {}", name);
            return new Metric(name, new long[0], new double[0]);
        }
    }
}
 