package org.jmatrices.dbl.build;

import org.jmatrices.dbl.Matrix;

import java.io.File;

/**
 * ${CLASS}.
 *
 * @author ppurang
 *         created  14.03.2005 - 14:39:08
 */
public interface MatrixFeeder {
    void setFile(String pathToFile);
    void setFile(File file);

    Matrix getMatrix();
/*
    int rows();
    int cols();

    Row getRow(int index);
    Column getColumn(int index);

    //Row[] getRows(int from,int to);
    //Row[] getRows(int[] rowInices);
    


    interface Sequence {
        boolean hasNext();
        double next();
        double getValue(int index);
    }

    interface Row extends Sequence{


    }

    interface Column extends Sequence {

    }*/
}
