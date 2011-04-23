package org.jmatrices.dbl.db.cache;

import java.sql.SQLException;


/**
 * ReadCache
 * <br>Author: purangp</br>
 * <br>
 * Date: 26.06.2004
 * Time: 20:37:15
 * </br>
 */
public class ReadCache extends Cache {

    //todo size restrictions while setting values?
    //todo strategy when  
    /**
     * Creates a new cache meant for reading.
     */
    public ReadCache() {
        this.cache = new SoftHashMap();
    }

   /**
    * Gets the value either from itself or from the database.
    */
   public Double getValue(int row, int col) {
        Double value =  super.getValue(row, col);
        if (value == null) {
            try {
                value = new Double(owner.getConnection().getValue(owner.getUUIDString(),row,col));
                setValue(row,col,value.doubleValue());
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't getValue(" + owner.getUUIDString() + "," + row + "," + col + ") because of " + e.getMessage());
            }
        }
        return value;
    }


    protected void finalize() throws Throwable {
        super.finalize();
    }

}
