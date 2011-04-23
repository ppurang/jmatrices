package org.jmatrices.dbl.db.cache;

import org.jmatrices.dbl.db.DatabaseMatrix;

import java.util.Map;

/**
 * Cache
 *
 * @author ppurang
 *         Created 21.11.2004 - 22:28:47
 */
public class Cache {
    protected Map cache;
    protected DatabaseMatrix owner;

    protected Cache() {

    }

    public void setOwner(DatabaseMatrix matrix) {
        if (owner != null)
            owner = matrix;
    }

    /**
     * Gets the value.
     * Returns null if value is not found in the cache.
     *
     * @param row
     * @param col
     * @return
     */
    public Double getValue(int row, int col) {
        Double valueToReturn = null;
        CacheKey cacheKey = new CacheKey(row, col);

        if (cache.containsKey(cacheKey)) {
            CacheValue cacheValue = (CacheValue) cache.get(cacheKey);
            if (cacheValue != null)
                valueToReturn = cacheValue.getValue();
        }

        return valueToReturn;
    }

    /**
     * Invalidates the cache entry for the given row,col combination.
     *
     * @param row row in the matrix
     * @param col column in the matrix
     */
    public void invalidate(int row, int col) {
        cache.remove(new CacheKey(row, col));
    }

    /**
     * invalidates all cache entries.
     */
    public void invalidateAll() {
        cache.clear();
    }

    public void setValue(int row, int col, double value) {
        cache.put(new CacheKey(row, col), new CacheValue(value));
    }



    protected void finalize() throws Throwable {
        cache = null;
        owner = null;
        super.finalize();
    }
}
