package ru.byprogminer.Lab2_Web.utility;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Utility {

    private static final int MIN_PRECISION = 15;

    private Utility() {
        throw new UnsupportedOperationException();
    }

    public static Boolean parseBoolean(String str, Boolean defaultValue) {
        if (str == null) {
            return defaultValue;
        }

        switch (str) {
            case "false":
            case "off":
            case "no":
                return false;

            case "true":
            case "yes":
            case "on":
                return true;
        }

        return defaultValue;
    }

    public static String colorToCss(Color color) {
        return String.format("rgba(%d, %d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b, Math.max(a.scale(), MIN_PRECISION), RoundingMode.HALF_UP);
    }
}
