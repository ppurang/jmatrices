package org.jmatrices.dbl;
/**
 * Matrix represents a <strong>structurally immutable</strong> matrix of numbers(double) with <strong>index begining at 1</strong>.
 *
 * The only way to change the elements in a matrix is through {@link #setValue(int, int, double)}
 * </p><p>
 * A good convention to follow is to use the following as indices while looping over matrix elements.
 * <pre>
 * row=1; row&lt;=rows(); row++
 * col=1; col&lt;=cols(); coll++
 * </pre>
 *
 * </p><p>
 * And use the normal <code>i,j,k</code> etc. to iterate over arrays!
 * </p><p>
 * Two matrices are equal iff they have same dimensions and each corresponding element is equal.
 * </p><p>
 * Matrix's clone must be a deep copy.
 * </p>
 * @author purangp
 *
 * created 07.03.2004 - 15:52:02
 */
public interface Matrix extends java.io.Serializable, Cloneable {
    /**
     * Gets the number of rows in the matrix
     * <p/>
     * Counts from 1
     * <br/>
     * Can't be less than 1
     *
     * @return number of rows in the matrix
     */
    int rows();

    /**
     * Gets the number of columns in the matrix
     * <p/>
     * counts from 1
     * <br/>
     * Can't be less than 1
     *
     * @return number of columns in the matrix
     */
    int cols();

    // GETS AND SETS
    /**
     * Sets an element at the given position to a new value
     * <br>May throw an {@link UnsupportedOperationException} in case the object is immutable. See {@link ScalarMatrixImpl}</br>
     * <br>May throw an {@link IllegalArgumentException} in case the object is in parts muttable. See {@link DiagonalMatrixImpl} </br>
     * <br>Will throw IndexOutOfBoundsException if index bounds are violated</br>
     * <pre>
     * Valid bounds are:
     *  1 &lt;= row &lt;= this.rows()
     *  1 &lt;= col &lt;= this.cols()
     * </pre>
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be set
     */
    void setValue(int row, int col, double value);

    /**
     * Gets the value of the element at the given row and column
     * <br>Will throw IndexOutOfBoundsException if index bounds are violated</br>
     *
     * <pre>
     * Valid bounds are:
     *  1 &lt;= row &lt;= this.rows()
     *  1 &lt;= col &lt;= this.cols()
     * </pre>
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    double getValue(int row, int col);


    boolean equals(Object obj);
}

/**
 *  Jmatrices - Matrix Library
    Copyright (C) 2004  Piyush Purang

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library, see License.txt; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */