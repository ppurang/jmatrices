package org.jmatrices.dbl.db;

/**
 * CacheValueEntryImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 12.06.2004
 * Time: 20:51:31
 * </br>
 */
public class CacheValueEntryImpl implements CacheValueEntry{
    private double value;
    private int numberOfRetrievals;
    private long lastRetrievalTime;

    public CacheValueEntryImpl(double value) {
        this.value = value;
    }

    public double getValue() {
        numberOfRetrievals++;
        lastRetrievalTime = System.currentTimeMillis();
        return value;
    }

    public long getTimeSinceLastRetrival() {
        if (getNumberOfRetrievals() == 0)
            throw new IllegalStateException("This method can't be invoked if getNumberOfRetrievals() == 0");
        return System.currentTimeMillis()-lastRetrievalTime;
    }

    public int getNumberOfRetrievals() {
        return numberOfRetrievals;
    }
}
