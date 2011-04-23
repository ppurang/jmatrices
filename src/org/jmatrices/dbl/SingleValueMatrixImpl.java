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
        throw new UnsupportedOperationException("SingleValue Matrices don't support modifying values");
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
        return new SingleValueMatrixImpl(rows, cols, scalar);
    }

    protected Matrix createClone() {
        throw new UnsupportedOperationException("The need to call this operation should never occur.");
    }
}
