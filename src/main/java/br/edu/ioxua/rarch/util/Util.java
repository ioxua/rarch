package br.edu.ioxua.rarch.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {
    public static Long safeLongFromString(String v) {
        try {
            return Long.valueOf(v);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
