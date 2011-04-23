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
 * MatrixSelectionStrategy encapsulates a strategy to
 * decide which implementation is more suitable for a
 * matrix to hold some sort of result.
 * <p/>
 * An implementation is used by the {@link org.jmatrices.dbl.MatrixFactory} to make decisions
 * about which (mutable-)matrix implemntation to instantiate depending on the parameters provided.
 * <p/>
 * The library provides an implementation that is used by default
 * {@link org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy}
 * <p/>
 * Strategy objects can be as complex an varied as imagination and functionality allow.
 * Examples: A visual GUI selection strategy that prompts the user when decisions are due. Or
 * a composite startegy that allows user to select between various strategies to come to a decision.
 *
 * @author ppurang
 *         Created 15.12.2004 - 21:57:30
 * @see org.jmatrices.dbl.MutableMatrixProducer
 */
public interface MatrixSelectionStrategy {
    /**
     * Returns a mutable matrix with the given dimensions
     * and the implementation sought through the passed <code>hint</code>.
     *
     * @param rows number of rows in the (mutable-)matrix
     * @param cols number of cols in the (mutable-)matrix
     * @param hint matrix implementation that should be implicitly favoured if it is a mutable-matrix
     * @return (mutable-)matrix with the given dimensions
     */
    Matrix selectMutableMatrix(int rows, int cols, Matrix hint);

    /**
     * Returns a mutable matrix with the given dimensions
     * and the implementation sought through the passed hint matrices.
     * <p/>
     * If both the matrices have producers then the strategy is free to choose between the two
     * depending on implemented strategy. It might also be the case that the strategy ignores
     * the provided hints completely  or on some occasions when the context is different.
     *
     * @param rows number of rows in the (mutable-)matrix
     * @param cols number of cols in the (mutable-)matrix
     * @param a    matrix implementation that should be implicitly favoured if it is a mutable-matrix
     * @param b    matrix implementation that should provide an alternative
     *             if <code>a</code>'s implementation isn't suitable.
     * @return (mutable-)matrix with the given dimensions
     */
    Matrix selectMutableMatrix(int rows, int cols, Matrix a, Matrix b);

}
