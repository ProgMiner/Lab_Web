package ru.byprogminer.Lab3_Web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QueryTest {

    private static final BigDecimal ONE_DIVIDE_BY_TEN = BigDecimal.ONE.divide(BigDecimal.TEN, 1, RoundingMode.HALF_UP);

    @Test
    public void testWhenCenterPoint_thenResultTrue() {
        Assertions.assertTrue(new Query(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(1e-100), null).getResult());
    }

    @Test
    public void testWhenRZero_thenResultFalse() {
        Assertions.assertFalse(new Query(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null).getResult());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.25, 0.5, 1, 2, 3, 4})
    public void testWidthIsDoubleR(double r) {
        final BigDecimal R = BigDecimal.valueOf(r);

        Assertions.assertFalse(new Query(BigDecimal.ZERO.subtract(R).subtract(ONE_DIVIDE_BY_TEN), BigDecimal.ZERO, R, null).getResult());
        Assertions.assertTrue(new Query(BigDecimal.ZERO.subtract(R), BigDecimal.ZERO, R, null).getResult());

        Assertions.assertFalse(new Query(R.add(ONE_DIVIDE_BY_TEN), BigDecimal.ZERO, R, null).getResult());
        Assertions.assertTrue(new Query(R, BigDecimal.ZERO, R, null).getResult());
    }
}
