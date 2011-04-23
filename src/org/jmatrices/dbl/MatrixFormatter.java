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
     * Same as formatMatrix(Matrix, java.text.DecimalFormat, DEFAULT_ROW_SEPERATOR, DEFAULT_COLUMN_SEPERATOR)
     *
     * @param m Matrix to be formatted
     * @param decimalFormat to be used for formatting individual decimal values
     * @return String representation of the matrix formatted as per reqirements.
     */
    public static String formatMatrix (Matrix m, DecimalFormat decimalFormat) {
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
    public static String formatMatrix (Matrix m, DecimalFormat decimalFormat, String rowSeperator, String colSeperator) {
        StringBuffer matrix = new StringBuffer(m.rows()*m.cols()*decimalFormat.getMaximumIntegerDigits()*decimalFormat.getMaximumFractionDigits());
        try {
            for (int row = 1; row <= m.rows(); row++) {
                for (int col = 1; col <= m.cols(); col++) {
                    matrix.append(decimalFormat.format(m.getValue(row, col)) + colSeperator);
                }
                matrix.append(rowSeperator);
            }
            return matrix.toString();
        } finally {
            matrix = null;
        }
    }

    private MatrixFormatter() {
    }
}
