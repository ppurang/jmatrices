package org.jmatrices.dbl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * AbstractMatrix implements all the methods in Matrix except getValue and setValue which are left to be implemented
 * by concrete classes
 * <br>Author: purangp</br>
 * <br>
 * Date: 07.06.2004
 * Time: 14:47:43
 * </br>
 */
public abstract class AbstractMatrix implements Matrix {
    protected int rows, cols;

    protected AbstractMatrix() {
    }

    public AbstractMatrix(int rows, int cols) {
        if (rows < 1 || cols < 1)
            throw new IllegalArgumentException("Rows and/or Columns can't be less than 1");
        else {
            this.rows = rows;
            this.cols = cols;
        }
    }

    /**
     * Gets the number of rows in the matrix
     * <p/>
     * Counts from 1
     *
     * @return number of rows in the matrix
     */
    public int rows() {
        return rows;
    }

    /**
     * Gets the number of columns in the matrix
     * <p/>
     * counts from 1
     *
     * @return number of columns in the matrix
     */
    public int cols() {
        return cols;
    }

    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    public abstract void setValue(int row, int col, double value);

    /**
     * Gets the value of the element at the given row and column
     * <br>May throw an UnsupportedOperationException in case the object is immutable</br>
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public abstract double getValue(int row, int col);

    /**
     * Gets the entire row as a matrix
     *
     * @param row row asked for
     * @return Matrix containing the row
     */
    public Matrix getRowMatrix(int row) {
        Matrix m = MatrixFactory.getMatrix(1, cols(), this);
        for (int col = 1; col <= cols(); col++) {
            m.setValue(1, col, this.getValue(row, col));
        }
        return m;
    }

    /**
     * Gets the entire column as a matrix
     *
     * @param col column asked for
     * @return Matrix containing the column
     */
    public Matrix getColumnMatrix(int col) {
        Matrix m = MatrixFactory.getMatrix(rows(), 1, this);
        for (int row = 1; row <= rows(); row++) {
            m.setValue(row, 1, this.getValue(row, col));
        }
        return m;
    }

    /**
     * Gets a <strong>copy</strong> of the elements as a 2D array.
     * <p/>
     * Copy signifies the fact that any modifications made on the copy will not affect the Source matrix!
     *
     * @return copy of all elements as a 2D array
     */
    public double[][] getValues() {
        double[][] toReturn = new double[rows()][cols()];
        for (int row = 1; row <= rows(); row++) {
            for (int col = 1; col <= cols(); col++) {
                toReturn[row - 1][col - 1] = getValue(row, col);
            }

        }
        return toReturn;
    }

    /**
     * Get a submatrix.
     *
     * @param rowI Initial row index
     * @param colI Initial column index
     * @param rowF Final row index
     * @param colF Final column index
     * @return A(rowI:rowF,colI:colF)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matrix getSubMatrix(int rowI, int colI, int rowF, int colF) {
        return getSubMatrix(MatrixFactory.getMatrix(rowF - rowI + 1, colF - colI + 1, this), rowI, colI, rowF, colF);
    }
    //The reason we implement this way so as to allow each matrix implementation to use themeselves as the matrices
    //and yet we can share the code between subclasses
    protected Matrix getSubMatrix(Matrix m, int rowI, int colI, int rowF, int colF) {
        int rows_m = m.rows(), cols_m = m.cols();
        if (rows_m > rows() || cols_m > cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            //for (int row = rowI, rowm = 1; row <= rowF; row++, rowm++) {
            for (int row = rowI; row <= rowF; row++) {
                //for (int col = colI, colm = 1; col <= colF; col++, colm++) {
                for (int col = colI; col <= colF; col++) {
                    //m.setValue(rowm,colm,getValue(row,col));
                    m.setValue(row - rowI + 1, col - colI + 1, getValue(row, col));
                }
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @return A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matrix getSubMatrix(int[] r, int[] c) {
        return getSubMatrix(MatrixFactory.getMatrix(r.length, c.length, this), r, c);
    }

    protected Matrix getSubMatrix(Matrix m, int[] r, int[] c) {
        for (int row = 1; row <= r.length; row++) {
            for (int col = 1; col <= c.length; col++) {
                m.setValue(row, col, getValue(r[row - 1], c[col - 1]));
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param rowI Initial row index
     * @param rowF Final row index
     * @param c    Array of column indices.
     * @return A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matrix getSubMatrix(int rowI, int rowF, int[] c) {
        return getSubMatrix(MatrixFactory.getMatrix(rowF - rowI + 1, c.length, this), rowI, rowF, c);
    }

    protected Matrix getSubMatrix(Matrix m, int rowI, int rowF, int[] c) {
        if (m.rows() > rows())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = rowI; row <= rowF; row++) {
                for (int col = 1; col <= c.length; col++) {
                    m.setValue(row - rowI + 1, col, getValue(row, c[col - 1]));
                }
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param r    Array of row indices.
     * @param colI Initial column index
     * @param colF Final column index
     * @return A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matrix getSubMatrix(int[] r, int colI, int colF) {
        return getSubMatrix(MatrixFactory.getMatrix(r.length, colF - colI + 1, this), r, colI, colF);
    }

    protected Matrix getSubMatrix(Matrix m, int[] r, int colI, int colF) {
        if (m.cols() > cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = 1; row <= r.length; row++) {
                for (int col = colI; col <= colF; col++) {
                    m.setValue(row, col - colI + 1, getValue(r[row - 1], col));
                }
            }
        }
        return m;
    }

    protected void withinBounds(int row, int col) {
        if (row < 1 || col < 1 || row > rows() || col > cols())
            throw new IllegalArgumentException("row and col parameters must satisfy 0 < row/col <= rows()/cols()");
    }

    //todo    hashCode  equals clone ?
    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        //todo make this configurable!!
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US)); // make this configurable!!
        format.setMinimumIntegerDigits(1);   // make this configurable!!
        format.setMaximumFractionDigits(8);  // make this configurable!!
        format.setMinimumFractionDigits(2);  // make this configurable!!
        format.setGroupingUsed(false);
        StringBuffer matrix = new StringBuffer();
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                matrix.append(format.format(this.getValue(row, col)) + " ");
            }
            matrix.append("\n");
        }
        return matrix.toString();
    }

}
