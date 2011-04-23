/**
 * Jmatrices - Matrix Library
 * Copyright (C) 2004  Piyush Purang
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library, see License.txt; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jmatrices.dbl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * AbstractMatrix implements all the methods in Matrix except getValue and setValue which are left to be implemented
 * by concrete classes.
 *
 * Concrete classes have to implement the follwing methods
 * <ol>
 *  <li>
 *  {@link #getValue0(int, int)}
 *  </li>
 *  <li>
 *  {@link #setValue0(int, int, double)} 
 *  </li>
 * </ol>
 *
 * @author ppurang
 *         created 07.06.2004 - 14:47:43
 */
public abstract class AbstractMatrix implements Matrix {
    /**
     * number of rows in the matrix
     */
    protected int rows;
    /**
     * number of columns in the matrix
     */
    protected int cols;

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
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Rows and/or Columns can't be less than 1 rows="
                    + rows
                    + " cols="
                    + cols);
        } else {
            this.rows = rows;
            this.cols = cols;
        }
    }

    /**
     * Gets the number of rows in the matrix. Counts from 1.
     *
     * @return number of rows in the matrix
     */
    public int rows() {
        return rows;
    }

    /**
     * Gets the number of columns in the matrix. Counts from 1.
     *
     * @return number of columns in the matrix
     */
    public int cols() {
        return cols;
    }
    /**{@inheritDoc}*/
    public final double getValue(int row, int col) {
        withinBounds(row, col);
        return getValue0(row, col);
    }

    /**
     * Abstract method to be implemented by the concrete classes.
     * This method is called by the {@link #getValue(int, int)}
     * after checking the preconditions related to the indices.
     * @param row row
     * @param col column
     * @return value
     */
    protected abstract double getValue0(int row, int col);

    /**{@inheritDoc}*/
    public final void setValue(int row, int col, double value) {
        withinBounds(row, col);
        setValue0(row, col, value);
    }

    /**
     * Abstract method to be implemented by the concrete classes.
     * This method is called by the {@link #setValue(int, int, double)} 
     * after checking the preconditions related to the indices.
     * @param row row
     * @param col column
     * @param value value to set at the given <code>row</code> and <code>col</code>
     */
    protected abstract void setValue0(int row, int col, double value);

    /**
     * Checks to see if row and column combination are within bounds.
     *
     * @param row row
     * @param col column
     */
    protected void withinBounds(int row, int col) {
        if (row < 1 || col < 1 || row > rows() || col > cols()) {
            throw new IndexOutOfBoundsException("row and col parameters must satisfy 0 <"
                    + " row(" + row + ")/col(" + col + ")"
                    + " <= rows(" + rows() + ")/cols(" + cols() + ")."
                    );
        }
    }

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
        return MatrixFormatter.formatMatrix(this, format);
    }

    /**
     * remark template pattern method
     * Sad that a cast will have to be done to convert into a Matrix object
     *
     * @return deep copy of the matrix
     */
    public Object clone() {         //question move clone into Matrix or drop it?
        Matrix m = createNascentClone();
        return populateClone(m);
    }

    /**
     * remark make sure this exists and doesn't depend on any other class to do this operation.
     * remark should do defensive copying
     *
     * @return matrix that shares implementation class with this matrix
     */
    protected abstract Matrix createNascentClone();

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
                m.setValue(row, col, getValue(row, col));
            }
        }
        return m;
    }

    /**
     * Tests for equality with the object passed as the argument.
     *
     * @param obj Object to be compared to.
     * @return <code>true</code> iff obj is instance of Matrix and all corresponding elements are equal.
     */
    public boolean equals(Object obj) {
        boolean toReturn = false;
        if (obj instanceof Matrix) {
            Matrix that = (Matrix) obj;
            notequal: {    //remark usage of a label.
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

    /**
     * todo think about caching hashCodes.
     * todo rethink implementation
     *
     * @return int representing the hashCode.
     */
    public int hashCode() {
        int hashCode = Integer.parseInt("" + rows + cols); //this will be unique for 3,2 and 2,3 and 6,1 and 1,6
        for (int row = 1; row <= rows(); row++) {
            for (int col = 1; col <= cols(); col++) {
                hashCode += Integer.parseInt("" + row + col) + (int) this.getValue(row, col);
            }
        }
        return hashCode;
    }

}
