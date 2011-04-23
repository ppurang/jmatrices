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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * MatrixFactory is a factory for creating matrix objects through static factory methods.
 * <p/>
 * As a singleton object it is also responsible for implementing the infrastructure for
 * coming up with valid implementation of result matrices. For this functionality it also relies on
 * {@link org.jmatrices.dbl.MutableMatrixProducer} and {@link org.jmatrices.dbl.MatrixSelectionStrategy}
 * <p/>
 * remark: MatrixFactory has been split up into two classes MtarixFactory and MatrixBuilder which
 * is now part of the builder component. 
 * <p/>
 * remark: MatrixFactory is now a singelton and allows better mechanism for producing matrices.
 * By default it relies on the {@link org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy}.
 * Users can provide alternate strategies by providing a value for the
 * property {@link #MATRIX_SELECTION_STRATEGY_SYSTEM_PROP}.
 *
 * @author ppurang
 *         Created 13.12.2004 - 22:26:12
 */
public final class MatrixFactory {
    public static final String MATRIX_SELECTION_STRATEGY_DEFAULT_CLASS_NAME = "org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy";
    public static final String MATRIX_SELECTION_STRATEGY_SYSTEM_PROP = "matrix.selection.strategy.default.class";
    private static final MatrixFactory factory = new MatrixFactory();
    private MatrixSelectionStrategy defaultStrategy;
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

    public static Matrix getMutableMatrixClone(Matrix m) {
        Matrix toReturn = MatrixFactory.getMatrix(m.rows(), m.cols(), m);
        for (int row = 1; row <= m.rows(); row++)
            for (int col = 1; col <= m.cols(); col++)
                toReturn.setValue(row, col, m.getValue(row, col));
        return toReturn;
    }

    /**
     * Gets the clone (deep copy) of the matrix if possible.
     * <p/>
     * Note: If the matrix being cloned is immutable or partially immutable then
     * the object returned has the same characteristics.
     * So don't rely on this method if you want to modify the clone.
     *
     * @param m matrix whos clone is required.
     * @return a cloned matrix with the same characteristics as the matrix cloned.
     */
    public static Matrix getMatrixClone(Matrix m) {
        Matrix clone = null;
        try {
            clone = (Matrix) m.clone();
        } catch (CloneNotSupportedException e) {
            clone = getMutableMatrixClone(m);
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
        return getMatrix(rows, cols, hint, getInstance().defaultStrategy);
    }

    public static Matrix getMatrix(int rows, int cols, Matrix hint, MatrixSelectionStrategy strategy) {
        return strategy.selectMutableMatrix(rows, cols, hint);
    }

    public static Matrix getMatrix(int rows, int cols, Matrix a, Matrix b) {
        return getMatrix(rows, cols, a, b, getInstance().defaultStrategy);
    }

    /**
     * Gets a matrix of the asked dimensions.
     * All elements are setValue to 0.0
     *
     * @param rows     number of rows in the matrix (>= 1)
     * @param cols     number of columns in the matrix (>= 1)
     * @param a        first matrix
     * @param b        second matrix
     * @param strategy strategy object for decision making support
     * @return Matrix of the given dimensions
     */
    public static Matrix getMatrix(int rows, int cols, Matrix a, Matrix b, MatrixSelectionStrategy strategy) {
        return strategy.selectMutableMatrix(rows, cols, a, b);
    }

    /**
     * Singelton instance getter
     * <p/>
     *
     * @return the one-and-only instance.
     */
    public static MatrixFactory getInstance() {
        return factory;
    }

    /**
     * Singelton instance that changes the startegy object used and then returns the singelton.
     *
     * @throws IllegalArgumentException if the <code>aSelectionStrategy</code> is null
     * @return the one-and-only instance.
     */
    public static MatrixFactory getInstance(MatrixSelectionStrategy aSelectionStrategy) {
        if(aSelectionStrategy != null) {
            factory.defaultStrategy = aSelectionStrategy;
        } else {
            throw new IllegalArgumentException("The provided MatrixSelectionStrategy object can't be null");
        }
        return factory;
    }


    /**
     * Private constructor as it is a singelton and utility class
     */
    private MatrixFactory() {
        String className = System.getProperty(MATRIX_SELECTION_STRATEGY_SYSTEM_PROP);
        if (className != null && className.trim().length() > 0) {
            initializeDefaultStrategy(className);
        } else {
            initializeDefaultStrategy(MATRIX_SELECTION_STRATEGY_DEFAULT_CLASS_NAME);
        }
    }

    private void initializeDefaultStrategy(String className) {
        try {
            Class clazz = Class.forName(className);
            defaultStrategy = (MatrixSelectionStrategy) clazz.newInstance();
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
     * <p/>
     * Returns a null to signify that a producer wasn't found which in turn implies
     * that maybe the matrix class specified by <code>matrixClassName</code> isn't mutable.
     *
     * @param matrixClassName type of matrices created by the sought after producer
     * @return producer or null.
     */
    public MutableMatrixProducer getProducer(String matrixClassName) {
        MutableMatrixProducer producer = (MutableMatrixProducer) mutableMatrixProducers.get(matrixClassName);
        if (producer == null) {
            //give it a chance toregister
            try {
                Class.forName(matrixClassName, true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Wrapped a ClassNotFoundException", e);
            }
            //try again
            producer = (MutableMatrixProducer) mutableMatrixProducers.get(matrixClassName);
        }
        return producer;
    }

    private Map getMutableProducers() {
        return Collections.unmodifiableMap(mutableMatrixProducers);
    }
}
