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

import java.util.Map;
import java.util.TreeMap;

/**
 * SparseMatrixImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 20:25:04
 * </br>
 */
class SparseMatrixImpl extends AbstractMatrix implements MutableMatrixProducer {
    protected Map elements = new TreeMap();

    private static final SparseMatrixImpl producer = new SparseMatrixImpl();
    static {
        MatrixFactory.getInstance().registerMatrixProducer(producer.getClass().getName(), producer);
    }

    /**
     * Constructor to allow correct creation of Matrix objects
     */
    protected SparseMatrixImpl() {
        super();
    }

    /**
     * Constructor that sets the dimensions of a matrix
     *
     * @param rows
     * @param cols
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public SparseMatrixImpl(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be setValue
     */
    protected void setValue0(int row, int col, double value) {
        Key key = new Key(row, col);
        //if the value being passed is zero
        // perhaps it is setting an already set element
        // in that case we need to remove that key and value combo
        // setting the key's value to zero
        // will be unnecessarily storage expensive
        if (value == 0)
            elements.remove(key);
        else
            elements.put(key, new Double(value));
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        Key key = new Key(row, col);
        if (elements.containsKey(key)) {
            return ((Double) elements.get(key)).doubleValue();
        } else
            return 0;
    }
     /**
     * Key represents an element position in a Matrix
     */
    protected static class Key implements Comparable {
        private int row;
        private int col;
        public Key(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int compareTo(Object o) {
            Key that = (Key) o;
            if (this.getRow() > that.getRow()) {
                return 1;
            } else if (this.getRow() < that.getRow()) {
                return -1;
            } else if (this.getRow() == that.getRow()) {
                if (this.getCol() > that.getCol())
                    return 1;
                else if (this.getCol() < that.getCol())
                    return -1;
                else
                    return 0;
            }
            return 0;
        }

        public int hashCode() {
            //todo not very convinced ..
            //return row * 10 + col;
            return Integer.valueOf("" + row + col).intValue();
        }
    }

    protected Matrix createClone() {
        return new SparseMatrixImpl(rows(),cols());
    }

    public Matrix getMatrix(int rows, int cols) {
        return new SparseMatrixImpl(rows,cols);
    }
}
