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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * SparseMatrixUniqueValueImpl implements a sparse matrix
 * with an additional storage benefit of storing each unique value just once.
 * Hence for a matrix
 * <pre>
 * 0 1 2 0
 * 3 0 1 0
 * 0 0 0 1
 * </pre>
 * Only the values 1, 2 and 3 will be stored.
 * <br>@author ppurang</br>
 * <br>
 * Date: 16.06.2004
 * Time: 21:58:09
 * </br>
 */
class SparseMatrixUniqueValueImpl extends SparseMatrixImpl {
    private List uniqueValues = new ArrayList();

    private static final SparseMatrixUniqueValueImpl producer = new SparseMatrixUniqueValueImpl();

    static {
        MatrixFactory.getInstance().registerMatrixProducer(producer.getClass().getName(), producer);
    }

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
    public void setValue0(int row, int col, double value) {
        Key key = new Key(row, col);

        //todo we need to remove stale values but present implementtaion isn't correct
        /*if (elements.containsKey(key)) {
            removeStaleValues(); // if the key already existed, some unique value might become stale
        }*/
        if (value == 0) {
            elements.remove(key);   // if value is being set to zero just remove the entry
        }
        Double dblValue = new Double(value);
        if (!uniqueValues.contains(dblValue))
            uniqueValues.add(dblValue);
        Integer index = Integer.valueOf(uniqueValues.indexOf(dblValue));
        elements.put(key, index);
    }

    private synchronized void removeStaleValues() {
        List tmpList = new ArrayList();
        Collection collection = elements.values();
        Iterator iter = collection.iterator();
        while (iter.hasNext()) {
            int index = ((Integer) iter.next()).intValue();
            tmpList.add(index, uniqueValues.get(index));
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
    public double getValue0(int row, int col) {
        double returnValue = 0;
        if (elements.containsKey(new Key(row, col))) {
            returnValue = ((Double) uniqueValues.get((
                    (Integer) elements.get(new Key(row, col))
            ).intValue())
            ).doubleValue();
        }

        return returnValue;
    }

    public Matrix getMatrix(int rows, int cols) {
        return new SparseMatrixUniqueValueImpl(rows, cols);
    }
}
