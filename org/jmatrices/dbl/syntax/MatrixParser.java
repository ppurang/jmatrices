package org.jmatrices.dbl.syntax;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MatrixParser
 * <p>Author: purangp</p>
 * Date: 30.04.2004
 * Time: 15:02:25
 */
class MatrixParser {
    public static final MatrixStringParser matlabMatrixParser = new MatrixStringParser("\\[", "\\]", ";", ",");
    public static final MatrixStringParser gaussMatrixParser = new MatrixStringParser("\\{", "\\}", ",", "\\s");

    public static Matrix parseMatrix(String src, MatrixStringParser parser) {
        return parser.parse(src);
    }

    public static final Matrix parseMatlabMatrix(String src) {
        return parseMatrix(src, matlabMatrixParser);
    }

    public static final Matrix parseGaussMatrix(String src) {
        return parseMatrix(src, gaussMatrixParser);
    }


    public static class MatrixStringParser {
        private String startString;
        private String endString;
        private String rowSeperator;
        private String elementSeperator;

        public MatrixStringParser(String startString, String endString, String rowSeperator, String elementSeperator) {
            this.startString = startString;
            this.endString = endString;
            this.rowSeperator = rowSeperator;
            this.elementSeperator = elementSeperator;
        }

        private Matrix parse(String src) {
            src = removeDuplicateWhitespace(src);
            double[][] values = divideIntoElements(divideIntoRows(discardStartEnd(src)));
            return MatrixFactory.getMatrix(values.length, values[0].length, null, values);
        }

        private String discardStartEnd(String src) {
            src = src.split(startString)[1];
            src = src.split(endString)[0];
            return src;
        }

        private String[] divideIntoRows(String src) {
            return src.split(rowSeperator);
        }

        private double[][] divideIntoElements(String[] rows) {
            double[][] values = new double[rows.length][];
            for (int row = 0; row < rows.length; row++) {
                values = divideIntoElements(values, row, removeDuplicateWhitespace(rows[row]));
            }
            return values;
        }

        private double[][] divideIntoElements(double[][] values, int row, String rowStr) {
            String[] strElements = rowStr.trim().split(elementSeperator);
            values[row] = new double[strElements.length];
            for (int element = 0; element < strElements.length; element++) {
                values[row][element] = Double.parseDouble(removeDuplicateWhitespace(strElements[element]));
            }
            return values;
        }

        private static String removeDuplicateWhitespace(String inputStr) {
            String patternStr = "\\s+";
            String replaceStr = " ";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.replaceAll(replaceStr);
        }

    }

    public static void main(String[] args) {
        System.out.println(MatrixParser.parseMatlabMatrix("   [     -2,3.5,6;   7,8, 9.0; 10,-11,12    ]   "));
        System.out.println(MatrixParser.parseGaussMatrix("{ 2 -3.5 6 , 7 -8 9.0, 10 11 12}"));
    }


}


/**
 *  Jmatrices - Matrix Library
    Copyright (C) 2004  Piyush Purang

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library, see License.txt; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
