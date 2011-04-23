package org.jmatrices.dbl;

import java.text.DecimalFormat;

/**
 * MatrixFormatter is used for formatting Matrix into required string representations.
 *
 * @author purangp
 *         Created 16.10.2004 - 23:46:12
 */
public class MatrixFormatter {
    public static final String DEFAULT_COLUMN_SEPERATOR = " ";
    public static final String DEFAULT_ROW_SEPERATOR = "\n";

    /**
     * Same as {@link #formatMatrix(Matrix, java.text.DecimalFormat, DEFAULT_ROW_SEPERATOR, DEFAULT_COLUMN_SEPERATOR)}
     *
     * @param m Matrix to be formatted
     * @param decimalFormat to be used for formatting individual decimal values
     * @return String representation of the matrix formatted as per reqirements.
     */
    public String formatMatrix (Matrix m, DecimalFormat decimalFormat) {
        return formatMatrix(m, decimalFormat, DEFAULT_ROW_SEPERATOR, DEFAULT_COLUMN_SEPERATOR);
    }

    /**
     * Formats a matrix into a string as per the passed arguments.
     * @param m Matrix to be formatted
     * @param decimalFormat to be used for formatting individual decimal values
     * @param rowSeperator to be used for seperating one row from another
     * @param colSeperator to be used for seperating one column from another
     * @return String representation of the matrix formatted as per reqirements.
     */
    public String formatMatrix (Matrix m, DecimalFormat decimalFormat, String rowSeperator, String colSeperator) {
        StringBuffer matrix = new StringBuffer();
        for (int row = 1; row <= m.rows(); row++) {
            for (int col = 1; col <= m.cols(); col++) {
                matrix.append(decimalFormat.format(m.getValue(row, col)) + colSeperator);
            }
            matrix.append(rowSeperator);
        }
        return matrix.toString();
    }
}
