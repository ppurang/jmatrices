package org.jmatrices.dbl;

/**
 * Matrices plays the same role that Collections does for collection classes
 * <br>Author: purangp</br>
 * <br>
 * Date: 18.05.2004
 * Time: 19:21:00
 * </br>
 */
public final class Matrices {
    /**
     * Gets the entire row as a Matrix.
     *
     * @param m   Matrix who's row is required
     * @param row the row in the Matrix m
     * @return one-row Matrix (row Vector)
     */
    public static Matrix getRowMatrix(Matrix m, int row) {
        Matrix n = MatrixFactory.getMatrix(1, m.cols(), m);
        for (int col = 1; col <= m.cols(); col++) {
            n.setValue(1, col, m.getValue(row, col));
        }
        return n;
    }

    /**
     * Gets the entire column as a Matrix.
     *
     * @param m   Matrix who's column is required
     * @param col the column in the Matrix m
     * @return one-column Matrix (column Vector)
     */
    public static Matrix getColumnMatrix(Matrix m, int col) {
        Matrix n = MatrixFactory.getMatrix(m.rows(), 1, m);
        for (int row = 1; row <= m.rows(); row++) {
            n.setValue(row, 1, m.getValue(row, col));
        }
        return n;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param rowI Initial row index
     * @param colI Initial column index
     * @param rowF Final row index
     * @param colF Final column index
     * @return A(rowI:rowF,colI:colF)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int rowI, int colI, int rowF, int colF) {
        Matrix m = MatrixFactory.getMatrix(rowF - rowI + 1, colF - colI + 1, n);
        int rows_m = m.rows(), cols_m = m.cols();
        if (rows_m > n.rows() || cols_m > n.cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            //for (int row = rowI, rowm = 1; row <= rowF; row++, rowm++) {
            for (int row = rowI; row <= rowF; row++) {
                //for (int col = colI, colm = 1; col <= colF; col++, colm++) {
                for (int col = colI; col <= colF; col++) {
                    //m.setValue(rowm,colm,getValue(row,col));
                    m.setValue(row - rowI + 1, col - colI + 1, n.getValue(row, col));
                }
            }
        }
        return m;

    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @return A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int[] r, int[] c) {
        Matrix m = MatrixFactory.getMatrix(r.length, c.length, n);
        for (int row = 1; row <= r.length; row++) {
            for (int col = 1; col <= c.length; col++) {
                m.setValue(row, col, n.getValue(r[row - 1], c[col - 1]));
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param rowI Initial row index
     * @param rowF Final row index
     * @param c    Array of column indices.
     * @return A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int rowI, int rowF, int[] c) {
        Matrix m = MatrixFactory.getMatrix(rowF - rowI + 1, c.length, n);
        if (m.rows() > n.rows())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = rowI; row <= rowF; row++) {
                for (int col = 1; col <= c.length; col++) {
                    m.setValue(row - rowI + 1, col, n.getValue(row, c[col - 1]));
                }
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param r    Array of row indices.
     * @param colI Initial column index
     * @param colF Final column index
     * @return A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int[] r, int colI, int colF) {
        Matrix m = MatrixFactory.getMatrix(r.length, colF - colI + 1, n);
        if (m.cols() > n.cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = 1; row <= r.length; row++) {
                for (int col = colI; col <= colF; col++) {
                    m.setValue(row, col - colI + 1, n.getValue(r[row - 1], col));
                }
            }
        }
        return m;
    }

    /**
     * Gets the <code>col</code> column of the matrix as a <code>double array</code>
     *
     * @param m
     * @param col
     * @return 1D double array
     */
    public static double[] getColumnArray(Matrix m, int col) {
        //todo
        //return getColumnArray(m.getColumnMatrix(col));
        double[] d = new double[m.rows()];
        for (int row = 1; row <= m.rows(); row++) {
            d[row - 1] = m.getValue(row, col);
        }
        return d;
    }

    /**
     * Gets the <code>row</code> row of the matrix as a <code>double array</code>
     *
     * @param m
     * @param row
     * @return 1D double array
     */
    public static double[] getRowArray(Matrix m, int row) {
        //todo
        //return getRowArray(m.getRowMatrix(row));
        double[] d = new double[m.cols()];
        for (int col = 1; col <= m.cols(); col++) {
            d[col - 1] = m.getValue(row, col);
        }
        return d;
    }


    /**
     * Gets all the elements of the matrix as two-dimensional array
     *
     * @param m
     * @return 2D double array
     */
    public static double[][] getArray(Matrix m) {
        double[][] toReturn = new double[m.rows()][m.cols()];
        for (int row = 1; row <= m.rows(); row++) {
            for (int col = 1; col <= m.cols(); col++) {
                toReturn[row - 1][col - 1] = m.getValue(row, col);
            }
        }
        return toReturn;
    }

    /**
     * private constructor to prohibit object creation
     */
    private Matrices() {
    }
}
