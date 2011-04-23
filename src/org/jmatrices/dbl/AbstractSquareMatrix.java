package org.jmatrices.dbl;

/**
 * AbstractSquareMatrix
 * <br>Author: purangp</br>
 * <br>
 * Date: 25.06.2004
 * Time: 19:20:42
 * </br>
 */
public abstract class AbstractSquareMatrix extends AbstractMatrix {
    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected AbstractSquareMatrix() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param dim dimension of the square matrix
     * @throws IllegalArgumentException if dim is less than 1
     */
    public AbstractSquareMatrix(int dim) {
        this(dim, dim);
    }
    /**
     * Constructor that allows construction of the object iff (rows==cols)
     * @param rows
     * @param cols
     */
    public AbstractSquareMatrix(int rows, int cols) {
        super(rows, cols);
        if (rows != cols)
            throw new IllegalArgumentException("For a SquareMatrix the number of rows must equal number of cols.");
    }
}
