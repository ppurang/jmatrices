package org.jmatrices.dbl;

import java.util.*;

/**
 * SparseMatrixUniqueValueImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 21:58:09
 * </br>
 */
public class SparseMatrixUniqueValueImpl extends SparseMatrixImpl {
    private List uniqueValues = new ArrayList();

    protected SparseMatrixUniqueValueImpl() {
        super();
    }

    public SparseMatrixUniqueValueImpl(int rows, int cols) {
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
        withinBounds(row, col);
        Key key = new Key(row, col);
        if (elements.containsKey(key)) {
            removeStaleValues(); // if the key already existed, some unique value might become stale
        }
        if (value == 0) {
            elements.remove(key);   // if value is being set to zero just remove the entry
        }
        Double dblValue = new Double(value);
        if (!uniqueValues.contains(dblValue))
            uniqueValues.add(dblValue);
        Integer index = new Integer(uniqueValues.indexOf(dblValue));
        elements.put(key, index);
        System.out.println("no of elements" + elements.size());
        System.out.println("no of unique values " + uniqueValues.size());
    }

    private synchronized void removeStaleValues() {
        List tmpList = new ArrayList();
        Collection collection = elements.values();
        Iterator iter  = collection.iterator();
        while(iter.hasNext()) {
            int index = ((Integer) iter.next()).intValue();
            tmpList.add(index,uniqueValues.get(index));
        }
        uniqueValues = tmpList;
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    public double getValue(int row, int col) {
        withinBounds(row, col);
        return ((Double) uniqueValues.get((
                (Integer) elements.get(new Key(row, col))
                ).intValue())
                ).doubleValue();
    }
}
