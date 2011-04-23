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
 * ArrayMatrixImpl implements a matrix based on a two dimensional array that stores elements row wise.
 *
 * @author ppurang
 * Date: 07.03.2004
 * Time: 16:06:06
 */
class ArrayMatrixImpl extends AbstractMatrix implements MutableMatrixProducer {
    protected double[][] rowView;

    //remark: Code at the heart of matrix creations.
    private static final ArrayMatrixImpl producer = new ArrayMatrixImpl();
    static {
        MatrixFactory.getInstance().registerMatrixProducer(producer.getClass().getName(), producer);
    }

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected ArrayMatrixImpl() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix and initializes the 2d array
     *
     * @param cols columns
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public ArrayMatrixImpl(int rows, int cols) {
        super(rows, cols);
        rowView = new double[rows][cols];
    }

    /**
     * Sets an elnkement at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    protected void setValue0(int row, int col, double value) {
        rowView[row - 1][col - 1] = value;
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        return rowView[row - 1][col - 1];
    }
    /**{@inheritDoc}*/
    protected Matrix createNascentClone() {
        return new ArrayMatrixImpl(rows, cols);
    }
    /**{@inheritDoc}*/
    public Matrix getMatrix(int rows, int cols) {
        return new ArrayMatrixImpl(rows,cols);
    }

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