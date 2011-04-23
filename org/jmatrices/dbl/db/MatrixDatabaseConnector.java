package org.jmatrices.dbl.db;

/**
 * MatrixDatabaseConnector
 * <br>Author: purangp</br>
 * <br>
 * Date: 10.06.2004
 * Time: 23:19:39
 * </br>
 */
public interface MatrixDatabaseConnector {
    /**
     * Sets a value in the databse
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be set
     */
    public void setValue(int row, int col, double value) ;

    /**
     * Gets the value of the element at the given row and column from the database
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public double getValue(int row, int col);
}
