package org.jmatrices.dbl;

/**
 * ScalarMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 13:39:12
 * </br>
 */
class ScalarMatrixImpl extends AbstractSquareMatrix {
    private double scalar;

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected ScalarMatrixImpl() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param dim dimension of the square matrix
     * @throws IllegalArgumentException if dim is less than 1
     */
    public ScalarMatrixImpl(int dim) {
        super(dim);
    }

    public ScalarMatrixImpl(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Constructor that sets the dimensions of a matrix
     * and initializes the values of the diagonal to the given scalar
     *
     * @param dim dimension of the square matrix
     * @param scalar values of the diagonal elements
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public ScalarMatrixImpl(int dim, double scalar) {
        this(dim);
        this.scalar = scalar;
    }

    /**
     * <br>Throws an UnsupportedOperationException in case the object is immutable</br>
     */
    protected void setValue0(int row, int col, double value) {
        throw new UnsupportedOperationException("Scalar Matrices don't support modifing values");
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        if (row == col)
            return scalar;
        else
            return 0;
    }


    public Object clone() {
        return new ScalarMatrixImpl(rows(), scalar);
    }

    protected Matrix createClone() {
        throw new UnsupportedOperationException("The need to call this operation should never occur.");
    }
}