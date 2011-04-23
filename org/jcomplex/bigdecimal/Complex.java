package org.jcomplex.bigdecimal;

import java.math.BigDecimal;

/**
 * Complex
 * <p/>
 * Author: purangp
 * </p>
 * Date: 20.03.2004
 * Time: 18:37:23
 */
public interface Complex {
    BigDecimal getReal();
    BigDecimal getImaginary();
    BigDecimal getTheta();
    BigDecimal getModulus();
}
