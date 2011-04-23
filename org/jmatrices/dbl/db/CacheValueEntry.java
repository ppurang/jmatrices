package org.jmatrices.dbl.db;

/**
 * CacheValueEntry
 * <br>Author: purangp</br>
 * <br>
 * Date: 11.06.2004
 * Time: 16:41:35
 * </br>
 */
public interface CacheValueEntry {
    /**
     * Gets the value stored in this entry
     * <br>
     * Should increment the number of retrievals and last retrieval time
     * </br>
     * @return value stored
     */
    double getValue();

    /**
     * Gets the number of times this cache value has been accessed.
     * <br>Returns 0 if the value was never accessed.</br>
     * @return number of times this cache value has been accessed
     */
    int getNumberOfRetrievals();
    /**
     * Gets the time passed in milliseconds since the last access to this value.
     * <br><b>Note</b>Makes sense iff <code>getNumberOfRetrievals > 0</code> if accessed when <code>getNumberOfRetrievals == 0</code>
     * it will throw an IllegalStateException
     * </br>
     * @return time passed in milliseconds since the last access
     */
    long getTimeSinceLastRetrival();
}
