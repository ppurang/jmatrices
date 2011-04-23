package org.jmatrices.dbl;

/**
 * Matrices plays the same role that Collections does for collection classes
 * <br>Author: purangp</br>
 * <br>
 * Date: 18.05.2004
 * Time: 19:21:00
 * </br>
 */
public final class Matrices {

    /**
     * Gets the <code>col</code> column of the matrix as a <code>double array</code>
     * @param m
     * @param col
     * @return 1D double array
     */
    public static double[] getColumnArray(Matrix m, int col){
        return getColumnArray(m.getColumnMatrix(col));
    }
    /**
     * Gets the <code>double array</code> representation of the column vector
     * <br>
     * @param vector
     * @return 1D double array
     */
    private static double[] getColumnArray(Matrix vector) {
        double[] d = new double[vector.rows()];
        for (int row=1; row<=vector.rows();row++) {
            d[row-1] = vector.getValue(row,1);
        }
        return d;
    }
    /**
     * Gets the <code>row</code> row of the matrix as a <code>double array</code>
     * @param m
     * @param row
     * @return 1D double array
     */
    public static double[] getRowArray(Matrix m, int row){
        return getRowArray(m.getRowMatrix(row));
    }
    /**
     * Gets the <code>double array</code> representation of the row vector
     * @param vector
     * @return 1D double array
     */
    private static double[] getRowArray(Matrix vector) {
        double[] d = new double[vector.cols()];
        for (int col=1; col<=vector.cols();col++) {
            d[col-1] = vector.getValue(1,col);
        }
        return d;
    }
    /**
     * Gets all the elements of the matrix
     * @param m
     * @return 2D double array
     */
    public static double[][] getArray(Matrix m) {
        return m.getValues();
    }

    /**
     * private constructor to prohibit object creation
     */
    private Matrices() {
    }
}
