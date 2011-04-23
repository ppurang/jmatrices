/**
 * Jmatrices - Matrix Library
 * Copyright (C) 2005  Piyush Purang
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
package org.jmatrices.dbl.syntax;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.testng.annotations.Test;

/**
 * MatrixSyntaxTest
 *
 * @author ppurang
 *         Created 08.02.2005 - 00:12:43
 */
@Test(groups = {"jmatrices.syntax"})
public class MatlabSyntaxTest {

    //2. implement tests as public methods
    @Test
    public void testCreate() {
        String testString = "   [     -2,3.5,6;   7,8, 9.0; 10,-11,12    ]   ";
        Matrix result = MatrixFactory.getMatrix(3, 3, null);
        result.setValue(1, 1, -2);
        result.setValue(1, 2, 3.5);
        result.setValue(1, 3, 6);
        result.setValue(2, 1, 7);
        result.setValue(2, 2, 8);
        result.setValue(2, 3, 9);
        result.setValue(3, 1, 10);
        result.setValue(3, 2, -11);
        result.setValue(3, 3, 12);
        Matrix test = MatlabSyntax.create(testString);
        assert result.equals(test);

    }
}
