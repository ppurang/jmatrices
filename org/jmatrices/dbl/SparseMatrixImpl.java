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
class SparseMatrixImpl extends AbstractMatrix implements Matrix {
    protected Map elements = new TreeMap();

    protected SparseMatrixImpl() {
        super();
    }

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
    public void setValue(int row, int col, double value) {
        withinBounds(row,col);
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
    public double getValue(int row, int col) {
        withinBounds(row,col);
        Key key = new Key(row, col);
        if (elements.containsKey(key)) {
            return ((Double) elements.get(key)).doubleValue();
        } else
            return 0;
    }

    /**
     * Key represents an element position in a Matrix
     */
    protected class Key implements Comparable {
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

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.<p>
         * <p/>
         * In the foregoing description, the notation
         * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
         * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
         * <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i>
         * is negative, zero or positive.
         * <p/>
         * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
         * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
         * <tt>y.compareTo(x)</tt> throws an exception.)<p>
         * <p/>
         * The implementor must also ensure that the relation is transitive:
         * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
         * <tt>x.compareTo(z)&gt;0</tt>.<p>
         * <p/>
         * Finally, the implementer must ensure that <tt>x.compareTo(y)==0</tt>
         * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
         * all <tt>z</tt>.<p>
         * <p/>
         * It is strongly recommended, but <i>not</i> strictly required that
         * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
         * class that implements the <tt>Comparable</tt> interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         *
         * @param o the Object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         *         is less than, equal to, or greater than the specified object.
         * @throws ClassCastException if the specified object's type prevents it
         *                            from being compared to this Object.
         */
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

        /**
         * Returns a hash code value for the object. This method is
         * supported for the benefit of hashtables such as those provided by
         * <code>java.util.Hashtable</code>.
         * <p/>
         * The general contract of <code>hashCode</code> is:
         * <ul>
         * <li>Whenever it is invoked on the same object more than once during
         * an execution of a Java application, the <tt>hashCode</tt> method
         * must consistently return the same integer, provided no information
         * used in <tt>equals</tt> comparisons on the object is modified.
         * This integer need not remain consistent from one execution of an
         * application to another execution of the same application.
         * <li>If two objects are equal according to the <tt>equals(Object)</tt>
         * method, then calling the <code>hashCode</code> method on each of
         * the two objects must produce the same integer result.
         * <li>It is <em>not</em> required that if two objects are unequal
         * according to the {@link Object#equals(Object)}
         * method, then calling the <tt>hashCode</tt> method on each of the
         * two objects must produce distinct integer results.  However, the
         * programmer should be aware that producing distinct integer results
         * for unequal objects may improve the performance of hashtables.
         * </ul>
         * <p/>
         * As much as is reasonably practical, the hashCode method defined by
         * class <tt>Object</tt> does return distinct integers for distinct
         * objects. (This is typically implemented by converting the internal
         * address of the object into an integer, but this implementation
         * technique is not required by the
         * Java<font size="-2"><sup>TM</sup></font> programming language.)
         *
         * @return a hash code value for this object.
         * @see Object#equals(Object)
         * @see java.util.Hashtable
         */
        public int hashCode() {
            return row * 10 + col;
        }

    }
}
