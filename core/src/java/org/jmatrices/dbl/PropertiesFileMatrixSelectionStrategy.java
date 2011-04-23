package org.jmatrices.dbl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * PropertiesFileMatrixSelectionStrategy implements a MatrixSelectionStrategy based on rules
 * defined in a properties file.
 *
 * Users can set such a file at runtime using the properties file definded by the system property
 * {@link org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy#SYSTEM_PROPERTIES_FILE}. If a file is specified
 * and there is an exception while loading it the program comes to a halt.
 *
 * If no such file is set then the library relies on the properties file
 * {@link org.jmatrices.dbl.PropertiesFileMatrixSelectionStrategy#PROPERTIES_FILE_NAME}, co-located with this class,
 * a part of the library.
 *
 * 
 *
 * @author ppurang
 *         created  25.03.2005 - 15:06:24
 */
public class PropertiesFileMatrixSelectionStrategy implements MatrixSelectionStrategy {
    private static final Logger LOGGER = Logger.getLogger("org.jmatrices.core");
    public static final String PROPERTIES_FILE_NAME = "default.selection.properties";
    public static final String SYSTEM_PROPERTIES_FILE = "override.default.properties";

    public static final String PROP_HUGE_MATRIX_DIMENSION = "matrix.mutable.huge.dim";
    public static final String PROP_HUGE_MATRIX_CLASS = "matrix.mutable.huge.class";
    public static final String PROP_DEFAULT_MATRIX_CLASS = "matrix.mutable.default.class";
    public static final String PROP_MATRIX_PRECEDENCE_LIST = "matrix.mutable.precedence.list";
    private Map map = new HashMap();

    private Properties props = new Properties();
    private int hugeDimensions = 0;

    public PropertiesFileMatrixSelectionStrategy() {
        final String propertiesFile = System.getProperty(SYSTEM_PROPERTIES_FILE);
        if (propertiesFile != null) {
            try {
                props.load(new FileInputStream(propertiesFile));
            } catch (IOException e) {
                throw new IllegalStateException(
                        "The overriding properties file wasn't found or is not a properties file: " + propertiesFile,
                        e);
            }
        } else {
            //try and load the default properties
            try {
                InputStream in = this.getClass().getResourceAsStream(PROPERTIES_FILE_NAME);
                if (in != null) {
                    props.load(in);
                } else {
                    throw new IllegalStateException("The properties file wasn't found on the classpath: "
                            + PROPERTIES_FILE_NAME);
                }
            } catch (IOException e) {
                throw new RuntimeException("Wrapped IOException in a RuntimeException", e);
            }
        }

        hugeDimensions = Integer.parseInt(props.getProperty(PROP_HUGE_MATRIX_DIMENSION));

        String[] tokens = props.getProperty(PROP_MATRIX_PRECEDENCE_LIST).split(",");
        for (int index = 0; index < tokens.length; index++) {
            String token = tokens[index].trim();
            map.put(token, Integer.valueOf(index));
        }
    }


    public Matrix selectMutableMatrix(final int rows, final int cols, final Matrix hint) {
        Matrix m = null;
        if (isHugeMatrix(rows, cols)) {
            m = getHugeMatrix(rows, cols);
        } else {
            MutableMatrixProducer producer = null;
            if(hint != null) {
                producer = MatrixFactory.getInstance().getProducer(hint.getClass().getName());
            } 
            if (producer != null) {
                m = getMutableMatrix(rows, cols, producer);
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
            MutableMatrixProducer producerA = null;
            MutableMatrixProducer producerB = null;
            if(a != null) {
                producerA = MatrixFactory.getInstance().getProducer(a.getClass().getName());
            }
            if (b != null) {
                producerB = MatrixFactory.getInstance().getProducer(b.getClass().getName());
            }
            if (producerA != null && producerB != null) {
                m = selectBetweenMutableMatrixProducers(rows, cols, producerA, producerB);
            } else if (producerA != null) {
                m = getMutableMatrix(rows, cols, producerA);
            } else if (producerB != null) {
                m = getMutableMatrix(rows, cols, producerB);
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
        } else if (aPlace != null) {
            m = getMutableMatrix(rows, cols, a);
        } else {
            throw new AssertionError("Both the matrix producers can't be null");
        }

        return m;
    }
}
