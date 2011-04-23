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
 * MatrixAdaptor adapts a matrix to start the row and column
 * indices from 0 instead of from 1.
 *
 * MatrixAdaptor could have been implemented as an implementation of Matrix but
 * as the contract isn't the same hence the Adaptor object isn't conceptually
 * the same as the adaptee.
 *
 * Care should be taken when iterating over an adaptor and matrix object in the same scope.
 * Ideally in a scope such as a mathod or even in an entire class it is advised to use either adptor
 * objects or matrix objects. Mixing the two will lead to confusion and hence mistakes. These mistakes
 * should fail fast with an IllegalArgumentException.
 *
 * @author ppurang
 *         Created 07.02.2005 - 00:29:53
 */
public final class MatrixAdaptor {
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
        boolean result = true;
        if(obj instanceof MatrixAdaptor) {
            return this.adaptee.equals(((MatrixAdaptor) obj).adaptee);
        }
        return adaptee.equals(obj);
    }
}
