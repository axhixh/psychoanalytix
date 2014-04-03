package gaul.psychoanalytix.algorithms;

/**
 *
 * @author shresa
 */
abstract class AlgorithmList {

    static Algorithm[] getAll() {
        return new Algorithm[]{new MedianAbsoluteDeviation(), 
            new Grubbs(), 
            new StdDevFromAvg(),
            new FirstHourAvg()
        };
    }
}
