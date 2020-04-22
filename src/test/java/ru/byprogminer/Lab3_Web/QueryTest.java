package ru.byprogminer.Lab3_Web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class QueryTest {

    private static final BigDecimal ONE_DIVIDE_BY_TEN = BigDecimal.ONE.divide(BigDecimal.TEN, 1, RoundingMode.HALF_UP);

    private static final BigDecimal[] X_BPS = new BigDecimal[21];
    private static final BigDecimal[] Y_BPS = new BigDecimal[11];

    private static final boolean[][] POINT_RESULTS = new boolean[][] {
            new boolean[] {false, false, false, false, false,  true, false, false, false, false, false},
            new boolean[] {false, false, false,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false,  true,  true,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false, false,  true,  true,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true,  true,  true, false},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false, false,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false, false, false,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false, false, false, false,  true,  true,  true,  true, false, false, false},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false,  true,  true,  true,  true,  true,  true,  true,  true,  true},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false, false, false,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false, false, false,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false, false, false,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false,  true,  true,  true,  true,  true,  true,  true,  true,  true, false},
            new boolean[] {false, false,  true,  true,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false, false,  true,  true,  true,  true,  true,  true,  true, false, false},
            new boolean[] {false, false, false,  true,  true,  true,  true,  true, false, false, false},
            new boolean[] {false, false, false, false, false,  true, false, false, false, false, false}
    };

    private final Random random = new Random();

    static {
        BigDecimal x = BigDecimal.ONE.negate();
        for (int i = 0; i < 21; ++i, x = x.add(ONE_DIVIDE_BY_TEN)) {
            X_BPS[i] = x;
        }

        BigDecimal y = BigDecimal.valueOf(0.5);
        for (int i = 0; i < 11; ++i, y = y.subtract(ONE_DIVIDE_BY_TEN)) {
            Y_BPS[i] = y;
        }
    }

    @Test
    public void testWhenRZero_thenResultFalse() {
        Assertions.assertFalse(new Query(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null).getResult());
    }

    @MethodSource("pointProvider")
    @DisplayName("{index} => ({0}, {1})")
    @ParameterizedTest(name = "{index} => ({0}, {1})")
    public void testGrid(int xbp, int ybp) {
        final BigDecimal r = BigDecimal.valueOf(5 - random.nextDouble() * 5).stripTrailingZeros();
        final BigDecimal x = X_BPS[xbp].multiply(r).stripTrailingZeros();
        final BigDecimal y = Y_BPS[ybp].multiply(r).stripTrailingZeros();

        if (POINT_RESULTS[xbp][ybp]) {
            Assertions.assertTrue(new Query(x, y, r, null).getResult(), () ->
                    String.format("for (%sR, %sR), r = %s", X_BPS[xbp], Y_BPS[ybp], r.toPlainString()));
        } else {
            Assertions.assertFalse(new Query(x, y, r, null).getResult(), () ->
                    String.format("for (%sR, %sR), r = %s", X_BPS[xbp], Y_BPS[ybp], r.toPlainString()));
        }
    }

    private static Stream<Arguments> pointProvider() {
        return IntStream.range(0, X_BPS.length)
                .mapToObj(x -> IntStream.range(0, Y_BPS.length)
                        .mapToObj(y -> Arguments.of(x, y)))
                .flatMap(st -> st);
    }
}
