/* $Id$
 *
 * This computer code is copyright 2013 EMC Corporation
 * All rights reserved
 */

package gaul.psychoanalytix.algorithms;

/**
 *
 * @author shresa
 */
abstract class AlgorithmList {

    static Algorithm[] getAll() {
        return new Algorithm[]{new MedianAbsoluteDeviation(), new Grubbs()};
    }
}
