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

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * MatrixFactory
 * remark: MatrixFactory has been split up into two classes
 * remark: MatrixFactory is now a singelton and allows better mechanism for producing matrices.
 *
 * @author purangp
 *         Created 13.12.2004 - 22:26:12
 */
public class MatrixFactory {
    public static final String MATRIX_SELECTION_STRATEGY_DEFAULT_CLASS_NAME = "org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy";
    public static final String MATRIX_SELECTION_STRATEGY_SYSTEM_PROP = "matrix.selection.strategy.default.class";
    private static final MatrixFactory factory = new MatrixFactory();
    private MatrixSelectionStrategy default_strategy;
    private Map mutableMatrixProducers = new HashMap();

    //various matrix implementations
    public static Matrix getArrayMatrix(int rows, int cols) {
        return new ArrayMatrixImpl(rows, cols);
    }

    public static Matrix getSingleValueMatrix(int rows, int cols, double scalar) {
        return new SingleValueMatrixImpl(rows, cols, scalar);
    }

    public static Matrix getSparseMatrix(int rows, int cols) {
        return new SparseMatrixImpl(rows, cols);
    }

    public static Matrix getSparseMatrixUniqueValue(int rows, int cols) {
        return new SparseMatrixUniqueValueImpl(rows, cols);
    }


    //Diagonal Matrix methods
    public static Matrix getDiagonalMatrix(int dim) {
        return new DiagonalMatrixImpl(dim);
    }

    public static Matrix getDiagonalMatrix(int rows, int cols) {
        return new DiagonalMatrixImpl(rows, cols);
    }

    public static Matrix getDiagonalMatrix(int dim, double[] diagonal) {
        Matrix m = getDiagonalMatrix(dim);
        for (int i = 0; i < diagonal.length; i++)
            m.setValue(i + 1, i + 1, diagonal[i]);
        return m;
    }

    //Spl Diagonal
    public static Matrix getScalarMatrix(int dim, double scalar) {
        return new ScalarMatrixImpl(dim, scalar);
    }

    public static Matrix getIdentityMatrix(int dim) {
        return getScalarMatrix(dim, 1);
    }

    //matrix clone
    private static Matrix getMatrixCloneNotSupported(Matrix m) {
        Matrix toReturn = MatrixFactory.getMatrix(m.rows(), m.cols(), m);
        for (int row = 1; row <= m.rows(); row++)
            for (int col = 1; col <= m.cols(); col++)
                toReturn.setValue(row, col, m.getValue(row, col));
        return toReturn;
    }

    public static Matrix getMatrixClone(Matrix m) {
        Matrix clone = null;
        try {
            clone = (Matrix) m.clone();
        } catch (CloneNotSupportedException e) {
            clone = getMatrixCloneNotSupported(m);
        }
        return clone;
    }

    //underlying implementation propogation methods
    /**
     * Gets a matrix of the asked dimensions.
     * All elements are setValue to 0.0
     *
     * @param rows number of rows in the matrix (>= 1)
     * @param cols number of columns in the matrix (>= 1)
     * @param hint acts as a hint for the right implementation to use
     * @return Matrix of the given dimensions
     */
    public static Matrix getMatrix(int rows, int cols, Matrix hint) {
        return getMatrix(rows, cols, hint, getInstance().default_strategy);
    }

    public static Matrix getMatrix(int rows, int cols, Matrix hint, MatrixSelectionStrategy strategy) {
        return strategy.selectMutableMatrix(rows, cols, hint);
    }

    public static Matrix getMatrix(int rows, int cols, Matrix a, Matrix b) {
        return getMatrix(rows, cols, a, b, getInstance().default_strategy);
    }

    /**
     * Gets a matrix of the asked dimensions.
     * All elements are setValue to 0.0
     *
     * @param rows number of rows in the matrix (>= 1)
     * @param cols number of columns in the matrix (>= 1)
     * @return Matrix of the given dimensions
     */
    public static Matrix getMatrix(int rows, int cols, Matrix a, Matrix b, MatrixSelectionStrategy strategy) {
        return strategy.selectMutableMatrix(rows, cols, a, b);
    }

    /**
     * Singelton instance getter
     * <p/>
     * todo have a getInstance(selection strategy) ?
     *
     * @return the one-and-only instance.
     */
    public static MatrixFactory getInstance() {
        return factory;
    }

    /**
     * Private constructor as it is a singelton and utility class
     */
    private MatrixFactory() {
        //todo initialize default strategy
        String className = System.getProperty(MATRIX_SELECTION_STRATEGY_SYSTEM_PROP);
        if (className != null) {
            initializeDefaultStrategy(className);
        } else {
            initializeDefaultStrategy(MATRIX_SELECTION_STRATEGY_DEFAULT_CLASS_NAME);
        }
    }

    private void initializeDefaultStrategy(String className) {
        try {
            Class clazz = Class.forName(className);
            default_strategy = (MatrixSelectionStrategy) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Wrapped a ClassNotFoundException in RuntimeException", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Wrapped a InstantiationException in RuntimeException", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Wrapped a IllegalAccessException in RuntimeException", e);
        }
    }

    /**
     * registers a matrix prosducer for a given Matrix type.
     * <br />
     * It does run a check to make sure that the producer is indeed able to produce the matrixType.
     *
     * @param matrixClassName full name of the class of matrices produced by the producer
     * @param producer        that claims to be able to produce matrices of type matrixType
     */
    public void registerMatrixProducer(String matrixClassName, final MutableMatrixProducer producer) {
        if (producer.getMatrix(1, 1).getClass().getName().equals(matrixClassName)) {
            mutableMatrixProducers.put(matrixClassName, producer);
        }
    }

    /**
     * Gets the MatrixProducer for the passed matrixType.
     *
     * @param matrixClassName type of matrices created by the sought after producer
     * @return producer or null.
     */
    public MutableMatrixProducer getProducer(String matrixClassName) {
        MutableMatrixProducer producer = (MutableMatrixProducer) mutableMatrixProducers.get(matrixClassName);
        if ( producer == null) {
            //give it a chance toregister
            try {
                Class.forName(matrixClassName,true,Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Wrapped a ClassNotFoundException", e);
            }
            //try again
            producer = (MutableMatrixProducer) mutableMatrixProducers.get(matrixClassName);
        }
        return producer;
    }

    public Map getMutableProducers() {
        return Collections.unmodifiableMap(mutableMatrixProducers);
    }
}
