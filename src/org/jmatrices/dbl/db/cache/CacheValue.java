package org.jmatrices.dbl.db.cache;

import java.io.Serializable;


/**
 * CacheValue
 * <br>@author ppurang</br>
 * <br>
 * Date: 12.06.2004
 * Time: 20:51:31
 * </br>
 */
public final class CacheValue implements Serializable {
    private final Double value;
    private int numberOfRetrievals;
    private long lastRetrievalTime;

    public CacheValue(double value) {
        this.value = new Double(value);
    }

    public Double getValue() {
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
