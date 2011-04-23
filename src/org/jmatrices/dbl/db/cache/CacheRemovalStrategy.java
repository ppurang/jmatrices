package org.jmatrices.dbl.db.cache;

/**
 * CacheRemovalStrategy
 *
 * @author ppurang
 *         Created 28.11.2004 - 22:59:34
 */
public interface CacheRemovalStrategy {
    public void processCacheValue(Cache cache, CacheKey key);
}
