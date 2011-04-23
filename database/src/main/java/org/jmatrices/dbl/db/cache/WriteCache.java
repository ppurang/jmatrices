package org.jmatrices.dbl.db.cache;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * WriteCache
 * <br>@author ppurang</br>
 * <br>
 * Date: 26.06.2004
 * Time: 20:37:23
 * </br>
 */
public class WriteCache extends Cache {
    private boolean hasUncommittedValues;
    private Committer committer;
    private boolean exit = false;


    public WriteCache() {
        cache = new TreeMap();
        committer = new Committer();
        Thread thread = new Thread(committer);
        thread.start();
    }


    /**
     * Sets the value.
     */
    public void setValue(int row, int col, double value) {
        super.setValue(row, col, value);
        hasUncommittedValues = true;
    }

    protected void finalize() throws Throwable {
        hasUncommittedValues = false;
        exit = true;
        committer = null;
        super.finalize();
    }


    private class Committer implements Runnable {
        public void run() {
            while (!exit) {
                if (hasUncommittedValues) {
                    commitAll();
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        //ignore

                    }
                }
            }

        }

        private void commitAll() {
            synchronized (this) {
                //todo
                boolean exceptionThrown = false;
                for (Iterator iter = cache.keySet().iterator(); iter.hasNext();) {
                    CacheKey cacheKey = (CacheKey) iter.next();
                    CacheValue cacheValue = (CacheValue) cache.get(cacheKey);
                    if (cacheValue == null) {
                        throw new IllegalStateException("CacheValue associated with a CacheKey should never be null!");
                    }
                    Double value = cacheValue.getValue();
                    if (value == null) {
                        throw new IllegalStateException("Value to be entered in the database is undefined!");
                    }

                    try {
                        owner.getConnection().setValue(owner.getUUIDString(), cacheKey.getRow(), cacheKey.getCol(), value.doubleValue());
                        invalidate(cacheKey.getRow(), cacheKey.getCol());
                    } catch (SQLException e) {
                        exceptionThrown = true;
                        throw new RuntimeException("Couldn't setValue(" + cacheKey.getRow() + "," + cacheKey.getCol() + "," + value + ") because of " + e.getMessage());
                    }

                }
                hasUncommittedValues = exceptionThrown;
            }
        }
    }

}
