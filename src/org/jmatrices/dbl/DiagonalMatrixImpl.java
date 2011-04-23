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
     *
     * @return
     */
    public Object clone() {
        DiagonalMatrixImpl toReturn = new DiagonalMatrixImpl(rows, cols);
        System.arraycopy(this.diagonal, 0, toReturn.diagonal, 0, this.diagonal.length);
        return toReturn;
    }

    protected Matrix createClone() {
        throw new UnsupportedOperationException("Should never be called");
    }
}
