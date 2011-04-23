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
 * MutableMatrixProducer allows production of matrices.
 * remark: introduced to make it easier to a) produce matrices and b) let the matrices themselves have the responsibility to create their instances. @see ArrayMatrixImpl 
 * @author purangp
 *         Created 13.12.2004 - 23:01:13
 */
public interface MutableMatrixProducer {
    /**
     * Gets the matrix with the given dimensions.
     * @param rows number of rows
     * @param cols number of columns
     * @return matrix with the given dimensions.
     */
     Matrix getMatrix(int rows, int cols);
    
}
