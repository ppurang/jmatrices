package org.jmatrices.dbl;

/**
 * SingleValueMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 14:06:23
 * </br>
 */
class SingleValueMatrixImpl extends AbstractMatrix {
    private double scalar;

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected SingleValueMatrixImpl() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param rows
     * @param cols
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public SingleValueMatrixImpl(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Constructor that sets the dimensions of a matrix
     * and initializes the values to the given scalar
     *
     * @param rows
     * @param cols
     * @param scalar
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public SingleValueMatrixImpl(int rows, int cols, double scalar) {
        this(rows, cols);
        this.scalar = scalar;
    }

    /**
     * <br>Throws an UnsupportedOperationException in case the object is immutable</br>
     */
    protected void setValue0(int row, int col, double value) {
        throw new UnsupportedOperationException("OneValue Matrices don't support modifing values");
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        return scalar;
    }

    public Object clone() {
        return new SingleValueMatrixImpl(rows,cols,scalar);
    }

    protected Matrix createClone() {
        throw new UnsupportedOperationException("The need to call this operation should never occur.");
    }
}
