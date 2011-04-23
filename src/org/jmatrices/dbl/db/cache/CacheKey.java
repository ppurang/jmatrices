package org.jmatrices.dbl.db.cache;

import java.io.Serializable;


/**
 * CacheKey
 * <br>@author ppurang</br>
 * <br>
 * Date: 11.06.2004
 * Time: 16:42:06
 * </br>
 */
public final class CacheKey implements Comparable, Serializable {
    private final int row;
    private final int col;

    public CacheKey(int row, int col) {
        if (row <= 0 || col <= 0)
            throw new IllegalArgumentException("The row and col can't be less than or equal to 0");
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int compareTo(Object o) {
            CacheKey that = (CacheKey) o;
            if (this.getRow() > that.getRow()) {
                return 1;
            } else if (this.getRow() < that.getRow()) {
                return -1;
            } else if (this.getRow() == that.getRow()) {
                if (this.getCol() > that.getCol())
                    return 1;
                else if (this.getCol() < that.getCol())
                    return -1;
                else
                    return 0;
            }
            return 0;
    }

    public boolean equals(Object obj) {

        boolean toReturn = false;
        if (obj instanceof CacheKey) {
            CacheKey that = (CacheKey) obj;
            toReturn = (this.hashCode() == that.hashCode());
        }
        return  toReturn;
    }

    public int hashCode() {
        return Integer.parseInt(""+ row + col);
    }
}
