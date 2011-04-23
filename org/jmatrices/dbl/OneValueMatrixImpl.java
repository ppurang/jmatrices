package org.jmatrices.dbl;

/**
 * OneValueMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 14:06:23
 * </br>
 */
public class OneValueMatrixImpl extends AbstractMatrix implements Matrix{
    private double scalar;

    protected OneValueMatrixImpl() {
        super();
    }

    public OneValueMatrixImpl(int rows, int cols) {
        super(rows, cols);
    }

    public OneValueMatrixImpl(int rows, int cols, double scalar) {
        super(rows, cols);
        this.scalar = scalar;
    }

    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    public void setValue(int row, int col, double value) {
        throw new UnsupportedOperationException("OneValue Matrices don't support modifing values");
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public double getValue(int row, int col) {
        withinBounds(row,col);
        return scalar;
    }
}
