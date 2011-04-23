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

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected AbstractMatrix() {
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param rows
     * @param cols
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
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

    public final double getValue(int row, int col) {
        withinBounds(row,col);
        return getValue0(row,col);
    }

    protected abstract double getValue0(int row, int col);

    public final void setValue(int row, int col, double value) {
        withinBounds(row,col);
        setValue0(row, col, value);
    }

    protected abstract void setValue0(int row, int col, double value);

    /**
     * Checks to see if row and column combination are within bounds.
     *
     * @param row
     * @param col
     */
    protected void withinBounds(int row, int col) {
        if (row < 1 || col < 1 || row > rows() || col > cols())
            throw new IndexOutOfBoundsException("row and col parameters must satisfy 0 < row/col <= rows()/cols()");
    }

    //todo    hashCode  clone ?
    /**
     * Returns a string representation of the matrix object.
     * <br/>
     * Uses " " as the column seperator and "\n" as the row seperator.
     * <br/>
     * Uses the following <code>DecimalFormat</code> to format numbers.
     * <pre>
     * DecimalFormat format = new DecimalFormat();
     * format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
     * format.setMinimumIntegerDigits(1);
     * format.setMaximumFractionDigits(8);
     * format.setMinimumFractionDigits(2);
     * format.setGroupingUsed(false);
     * </pre>
     * For custom formatting please use {@link MatrixFormatter}
     *
     * @return String representation of this matrix
     * @see MatrixFormatter
     */
    public String toString() {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(25);
        format.setMinimumFractionDigits(2);
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

    /**
     * remark template pattern method
     * Sad that a cast will have to be done to convert into a Matrix object
     * @return deep copy of the matrix
     */
    public Object clone() {         //question move clone into Matrix or drop it?
        Matrix m = createClone();
        return populateClone(m);
    }

    /**
     * remark make sure this exists and doesn't depend on any other class to do this operation.
     *
     * @return matrix that shares implementation class with this matrix
     */
    protected abstract Matrix createClone();

    /**
     * Used for populating a matrix that is supposed to be a clone.
     * <br/>
     * Will throw any of the exceptions that may be thrown while setting a value.
     *
     * @param m The matrix to be populated
     * @return Matrix with the same elements as this matrix
     */
    protected Matrix populateClone(Matrix m) {
        for (int row = 1; row <= rows(); row++) {
            for (int col = 1; col <= cols(); col++) {
                m.setValue(row,col,getValue(row,col));
            }
        }
        return m;
    }

    /**
     * Tests for equality with the object passed as the argument.
     *
     * @param obj Object to be compared to.
     * @return <code>true</code> iff obj is a matrix with same dimensions and
     */
    public boolean equals(Object obj) {
        boolean toReturn = false;
        if (obj instanceof Matrix) {
            Matrix that = (Matrix) obj;
            notequal: {
                if (this.rows() == that.rows() && this.cols() == that.cols()) {
                    for (int row = 1; row <= rows(); row++) {
                        for (int col = 1; col <= cols(); col++) {
                            if (this.getValue(row, col) != that.getValue(row, col))
                                break notequal;
                        }
                    }
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }

    public int hashCode() {
        int hashCode = Integer.parseInt("" + rows + cols); //this will be unique for 3,2 and 2,3 and 6,1 and 1,6
        for (int row = 1; row <= rows(); row++) {
            for (int col = 1; col <= cols(); col++) {
                hashCode += Integer.parseInt("" + row + col) + (int) this.getValue(row,col) ;
            }
        }
        return hashCode;
    }

}
