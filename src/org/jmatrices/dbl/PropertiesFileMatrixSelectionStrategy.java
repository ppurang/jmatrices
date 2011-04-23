package org.jmatrices.dbl;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * PropertiesFileMatrixSelectionStrategy.
 *
 * @author ppurang
 *         created  25.03.2005 - 15:06:24
 */
public class PropertiesFileMatrixSelectionStrategy implements MatrixSelectionStrategy {
    public static final String PROPERTIES_FILE_DEFAULT = "default.properties";
    public static final String SYSTEM_PROPERTIES_FILE = "override.default.properties";
    public static final String PROP_HUGE_MATRIX_DIMENSION = "matrix.mutable.huge.dim";
    public static final String PROP_HUGE_MATRIX_CLASS = "matrix.mutable.huge.class";
    public static final String PROP_DEFAULT_MATRIX_CLASS = "matrix.mutable.default.class";
    public static final String PROP_MATRIX_PRECEDENCE_LIST = "matrix.mutable.precedence.list";
    Map map = new HashMap();

    private Properties props = new Properties();
    private int hugeDimensions = 0;

    public PropertiesFileMatrixSelectionStrategy() {
        String props_file = System.getProperty(SYSTEM_PROPERTIES_FILE, PROPERTIES_FILE_DEFAULT);
        try {
            props.load(new FileInputStream(props_file));
        } catch (IOException e) {
            throw new RuntimeException("Wrapped IOException in a RuntimeException", e);
        }
        hugeDimensions = Integer.parseInt(props.getProperty(PROP_HUGE_MATRIX_DIMENSION));

        String[] tokens = props.getProperty(PROP_MATRIX_PRECEDENCE_LIST).split(",");
        for (int index = 0; index < tokens.length; index++) {
            String token = tokens[index].trim();
            map.put(token, new Integer(index));
        }
    }


    public Matrix selectMutableMatrix(int rows, int cols, Matrix hint) {
        Matrix m = null;
        if (isHugeMatrix(rows, cols)) {
            m = getHugeMatrix(rows, cols);
        } else {
            if (hint instanceof MutableMatrixProducer) {
                m = getMutableMatrix(rows, cols, (MutableMatrixProducer) hint);
            } else {
                m = getDefaultMatrix(rows, cols);
            }
        }
        return m;
    }

    public Matrix selectMutableMatrix(int rows, int cols, Matrix a, Matrix b) {
        Matrix m = null;
        if (isHugeMatrix(rows, cols)) {
            m = getHugeMatrix(rows, cols);
        } else {
            if (a instanceof MutableMatrixProducer && b instanceof MutableMatrixProducer) {
                m = selectBetweenMutableMatrixProducers(rows, cols, (MutableMatrixProducer) a, (MutableMatrixProducer) b);

            } else if (a instanceof MutableMatrixProducer) {
                m = getMutableMatrix(rows, cols, (MutableMatrixProducer) a);

            } else if (b instanceof MutableMatrixProducer) {
                m = getMutableMatrix(rows, cols, (MutableMatrixProducer) b);
            } else {
                m = getDefaultMatrix(rows, cols);
            }
        }

        return m;
    }

    private boolean isHugeMatrix(int rows, int cols) {
        return (rows * cols >= hugeDimensions);
    }

    private Matrix getHugeMatrix(int rows, int cols) {
        return getMatrix(rows, cols, props.getProperty(PROP_HUGE_MATRIX_CLASS));
    }

    private Matrix getDefaultMatrix(int rows, int cols) {
        return getMatrix(rows, cols, props.getProperty(PROP_DEFAULT_MATRIX_CLASS));
    }

    private Matrix getMutableMatrix(int rows, int cols, MutableMatrixProducer producer) {
        return producer.getMatrix(rows, cols);
    }

    private Matrix getMatrix(int rows, int cols, String classString) {
        MutableMatrixProducer producer = null;
        producer = MatrixFactory.getInstance().getProducer(classString);
        if (producer == null)
            throw new RuntimeException("Not Found: MutableMatrixProducer of type " + classString + " either doesn't exist or isn't registered with the MatrixFactory.");
        return producer.getMatrix(rows, cols);
    }

    private Matrix selectBetweenMutableMatrixProducers(int rows, int cols, MutableMatrixProducer a, MutableMatrixProducer b) {
        Matrix m = null;
        Integer aPlace = (Integer) map.get(a.getClass().getName());
        Integer bPlace = (Integer) map.get(b.getClass().getName());
        if (aPlace != null && bPlace != null) {
            if (aPlace.intValue() > bPlace.intValue()) {
                //b takes precedence
                m = getMutableMatrix(rows, cols, b);
            } else {
                m = getMutableMatrix(rows, cols, a);
            }
        } else if (aPlace == null && bPlace != null) {
            m = getMutableMatrix(rows, cols, b);
        } else if (aPlace != null && bPlace == null) {
            m = getMutableMatrix(rows, cols, a);
        } else {
            //both aPlace and bPlace are nulls..
            m = getMutableMatrix(rows, cols, a);
        }

        return m;
    }
}
