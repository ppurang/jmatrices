package org.jmatrices.dbl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * ArrayMatrixImpl
 * <p>
 * Author: purangp
 * </p>
 * Date: 07.03.2004
 * Time: 16:06:06
 */
class ArrayMatrixImpl extends AbstractMatrix implements Matrix {
    protected int rows, cols;
    protected double[][] rowView;

    protected ArrayMatrixImpl() {
    }

    public ArrayMatrixImpl(int rows, int cols) {
            super(rows,cols);
            rowView = new double[rows][cols];
    }

    /**
     * @param row
     * @param col
     * @param value
     */
    public void setValue(int row, int col, double value) {
        rowView[row - 1][col - 1] = value;
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public double getValue(int row, int col) {
        return rowView[row - 1][col - 1];
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