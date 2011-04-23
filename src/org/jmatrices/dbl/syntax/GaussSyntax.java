package org.jmatrices.dbl.syntax;

import org.jmatrices.dbl.Matrix;

/**
 * GaussSyntax  todo
 * <p>Author: purangp</p>
 * Date: 30.04.2004
 * Time: 14:52:28
 */
public class GaussSyntax {
    /**
     * Creation  using a string like
     * Matrix symmetricMatrix = GaussSyntax.create("{1 5 6, 5 2 0, 6 0 -4}");
     */
    public static Matrix create(String src) {
        return MatrixParser.parseGaussMatrix(src);
    }
}
