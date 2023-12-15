package me.craftinators.math;

public final class MathUtil {
    private MathUtil() {}

    public static long fastFloor(double number) {
        long floored = (long) number;
        return number < floored ? floored - 1 : floored;
    }
}
