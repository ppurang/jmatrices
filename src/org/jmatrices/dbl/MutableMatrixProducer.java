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
 * MutableMatrixProducer allows creation of mutable matrices.
 * remark: introduced to make it easier to
 * a) create mutable matrices and
 * b) allow implementations a posibility of creating matrices of their type.
 * <p/>
 * Important: To check whether or not an implementation of matrix has a or is itself a  MutableMatrixProducer
 * please don't rely on <code>instanceof</code>. Instead use the method {@link MatrixFactory#getProducer(String)}.
 * <p/>
 * For a usage please see the {@link org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy}
 * <p/>
 * For an implementation where the Matrix implementation also implements this interface please
 * see {@link org.jmatrices.dbl.ArrayMatrixImpl}
 *
 * @author ppurang
 *         Created 13.12.2004 - 23:01:13
 */
public interface MutableMatrixProducer {
    /**
     * Gets the matrix with the given dimensions.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @return matrix with the given dimensions.
     */
    Matrix getMatrix(int rows, int cols);

}
