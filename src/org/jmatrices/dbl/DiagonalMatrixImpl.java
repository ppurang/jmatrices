package org.jmatrices.dbl;

/**
 * DiagonalMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 23.06.2004
 * Time: 23:13:26
 * </br>
 */
class DiagonalMatrixImpl extends AbstractSquareMatrix {
    private double[] diagonal;

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected DiagonalMatrixImpl() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param dim dimension of the square matrix
     * @throws IllegalArgumentException if dim is less than 1
     */
    public DiagonalMatrixImpl(int dim) {
        super(dim);
        diagonal = new double[cols];
    }

    public DiagonalMatrixImpl(int rows, int cols) {
        super(rows, cols);
        diagonal = new double[cols];
    }


    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    protected void setValue0(int row, int col, double value) {
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
    protected double getValue0(int row, int col) {
        if (row == col)
            return diagonal[col - 1];
        else
            return 0;
    }

    /**
     * remark we can't just let populateClone do the work as setValue will throw an IllegalArgumentException
     * @return
     */
    public Object clone() {
        DiagonalMatrixImpl toReturn = new DiagonalMatrixImpl(rows,cols);
        System.arraycopy(this.diagonal,0,toReturn.diagonal,0,this.diagonal.length);
        return toReturn;
    }

    protected Matrix createClone() {
        throw new UnsupportedOperationException("Should never be called");
    }
}
