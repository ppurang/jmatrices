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
class SparseMatrixImpl extends AbstractMatrix  {
    protected Map elements = new TreeMap();

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
        //if the value being passed is zero perhaps it is setting an already set element
        // in that case we need to remove that key and value combo
        //setting the key's value to zero will be unnecessarily storage expensive
        if (value == 0)
            elements.remove(key);
        else
            elements.put(key, new Double(value));
        System.out.println(elements.size());
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

    protected Matrix createClone() {
        return new SparseMatrixImpl(rows(),cols());
    }

    /**
     * Key represents an element position in a Matrix
     */
    protected static class Key implements Comparable {
        private int row;
        private int col;

        public Key(int row, int col) {
            if (row <= 0 || col <= 0)
                throw new IllegalArgumentException("The row and col can't be less than or equal to 0");
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
}
