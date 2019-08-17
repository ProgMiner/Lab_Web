package ru.byprogminer.Lab2_Web.utility;

public final class Utility {

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
}
