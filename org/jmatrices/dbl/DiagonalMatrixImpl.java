package org.jmatrices.dbl;

/**
 * DiagonalMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 23.06.2004
 * Time: 23:13:26
 * </br>
 */
public class DiagonalMatrixImpl extends AbstractMatrix implements Matrix {
    private double[] diagonal;

    protected DiagonalMatrixImpl() {
        super();
    }

    public DiagonalMatrixImpl(int rows, int cols) {
        super(rows, cols);
        if (rows != cols)
            throw new IllegalArgumentException("Diagonal matrix has to be square");
        diagonal = new double[cols];

    }

    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    public void setValue(int row, int col, double value) {
        if (row == col)
            diagonal[col - 1] = value;
        else
            throw new IllegalArgumentException("DiagonalMatrix - Only diagonal elements may be set");
    }

    /**
     * Gets the value of the element at the given row and column
     * <br>May throw an UnsupportedOperationException in case the object is immutable</br>
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public double getValue(int row, int col) {
        if (row == col)
            return diagonal[col - 1];
        else
            return 0;
    }
}
