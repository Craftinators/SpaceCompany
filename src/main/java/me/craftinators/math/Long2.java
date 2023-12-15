package me.craftinators.math;

/**
 * Represents an immutable 2D vector with long precision.
 */
public final class Long2 {
    private final long x, y;

    /**
     * Constructs a new Long2 with the given x and y components.
     *
     * @param x The x component
     * @param y The y component
     */
    public Long2(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Long2 with the given x and y components.
     *
     * @param x The x component
     * @param y The y component
     * @return The new Long2
     */
    public static Long2 of(long x, long y) {
        return new Long2(x, y);
    }

    /**
     * Represents the zero vector.
     */
    public static Long2 ZERO = of(0, 0);

    /**
     * Represents the one vector.
     */
    public static Long2 ONE = of(1, 1);

    /**
     * Represents the unit y vector.
     */
    public static Long2 UP = of(0, 1);

    /**
     * Represents the negative unit y vector.
     */
    public static Long2 DOWN = of(0, -1);

    /**
     * Represents the negative unit x vector.
     */
    public static Long2 LEFT = of(-1, 0);

    /**
     * Represents the unit x vector.
     */
    public static Long2 RIGHT = of(1, 0);

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Long2 other)) return false;
        return getX() == other.getX() && getY() == other.getY();
    }

    public Long2 add(Long2 other) {
        return of(getX() + other.getX(), getY() + other.getY());
    }

    public Long2 add(long scalar) {
        return of(getX() + scalar, getY() + scalar);
    }

    public Long2 add(long scalarX, long scalarY) {
        return of(getX() + scalarX, getY() + scalarY);
    }

    public Long2 subtract(Long2 other) {
        return of(getX() - other.getX(), getY() - other.getY());
    }

    public Long2 subtract(long scalar) {
        return of(getX() - scalar, getY() - scalar);
    }

    public Long2 subtract(long scalarX, long scalarY) {
        return of(getX() - scalarX, getY() - scalarY);
    }

    public Long2 multiply(long scalar) {
        return of(getX() * scalar, getY() * scalar);
    }

    public Long2 divide(long scalar) {
        return of(getX() / scalar, getY() / scalar);
    }

    public long dot(Long2 other) {
        return getX() * other.getX() + getY() * other.getY();
    }

    public double magnitude() {
        return Math.sqrt(squareMagnitude());
    }

    public long squareMagnitude() {
        return dot(this);
    }

    public double distance(Long2 other) {
        return Math.sqrt(squareDistance(other));
    }

    public long squareDistance(Long2 other) {
        return subtract(other).squareMagnitude();
    }

    public double getAngle() {
        return Math.atan2(getY(), getX());
    }

    public double angleBetween(Long2 other) {
        return Math.atan2(getY() - other.getY(), getX() - other.getX());
    }

    public double sum() {
        return getX() + getY();
    }

    public double difference() {
        return getX() - getY();
    }

    public Long2 hadamard(Long2 other) {
        return of(getX() * other.getX(), getY() * other.getY());
    }
}
