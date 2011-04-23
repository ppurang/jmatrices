/**
 * Jmatrices - Matrix Library
 * Copyright (C) 2005  Piyush Purang
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
package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;

/**
 * MatrixAdaptor
 *
 * @author purangp
 *         Created 07.02.2005 - 00:29:53
 */
public class MatrixAdaptor implements Matrix {
    private Matrix adaptee;

    public MatrixAdaptor(Matrix delegate) {
        this.adaptee = delegate;
    }

    public int rows() {
        return adaptee.rows();
    }

    public int cols() {
        return adaptee.cols();
    }

    public void setValue(int row, int col, double value) {
        adaptee.setValue(row+1, col+1, value);
    }

    public double getValue(int row, int col) {
        return adaptee.getValue(row+1, col+1);
    }

    public Matrix getAdaptee() {
        return adaptee;
    }


    public String toString() {
        return adaptee.toString();
    }


    public Object clone() throws CloneNotSupportedException {
        return adaptee.clone();
    }

    public int hashCode() {
        return adaptee.hashCode();
    }

    public boolean equals(Object obj) {
        return adaptee.equals(obj);
    }

}
