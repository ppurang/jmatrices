package org.jmatrices.dbl.db;

import java.io.Serializable;

/**
 * CacheIndexEntry
 * <br>Author: purangp</br>
 * <br>
 * Date: 11.06.2004
 * Time: 16:40:01
 * </br>
 */
public interface CacheIndexEntry extends Comparable, Serializable{
    int getRow();
    int getCol();
}
