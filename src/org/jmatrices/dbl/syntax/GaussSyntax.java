package org.jmatrices.dbl.syntax;

import org.jmatrices.dbl.Matrix;

/**
 * GaussSyntax  todo
 * <p>@author ppurang</p>
 * Date: 30.04.2004
 * Time: 14:52:28
 */
public final class GaussSyntax {
    /**
     * Creation  using a string like
     * Matrix symmetricMatrix = GaussSyntax.create("{1 5 6, 5 2 0, 6 0 -4}");
     * Matrix symmetricMatrix = GaussSyntax.create("{ 2 -3.5 6 , 7 -8 9.0, 10 11 12}");
     */
    public static Matrix create(String src) {
        return MatrixParser.parseGaussMatrix(src);
    }
}
