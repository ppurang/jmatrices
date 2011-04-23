package org.jmatrices.dbl.db;

import org.jmatrices.dbl.AbstractMatrix;
import org.jmatrices.dbl.Matrix;

/**
 * DatabaseMatrix
 * <br>Author: purangp</br>
 * <br>
 * Date: 10.06.2004
 * Time: 23:18:43
 * </br>
 */
public class DatabaseMatrix extends AbstractMatrix implements Matrix {
    private MatrixDatabaseConnector connector;

    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be set
     */
    public void setValue(int row, int col, double value) {
        connector.setValue(row,col,value);
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public double getValue(int row, int col) {
        return connector.getValue(row,col);
    }

}
