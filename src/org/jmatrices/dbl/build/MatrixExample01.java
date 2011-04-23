package org.jmatrices.dbl.build;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.operator.MatrixOperator;

/**
 * MatrixExample01.
 *
 * @author ppurang
 *         created  20.03.2005 - 13:40:11
 */
public class MatrixExample01 {

    public static void main(String[] args) {

        /*double[][] aInitValues = new double[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix a = MatrixBuilder.getMatrixFromArray(3, 3, null, aInitValues);
        Matrix b = MatrixFactory.getIdentityMatrix(3);

        Matrix c = MatrixOperator.multiply(a, b);
        System.out.println(c);*/

        String path00 = args[0];
        MatrixFeeder feeder = new XMLMatrixFeeder();
        feeder.setFile(path00);
        Matrix matrix00 = feeder.getMatrix();
        Matrix matrix01 = MatrixBuilder.getRandomMatrix(20,20,null);
        Matrix result = MatrixOperator.multiply(matrix00, matrix01);
        System.out.println(result);
        System.out.println(result.getClass().getName());

    }
}
