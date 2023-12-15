package me.craftinators.math;

/**
 * Represents an immutable 2D vector with double precision.
 */
public final class Double2 {
    private final double x, y;

    /**
     * Constructs a new Double2 with the given x and y components.
     * @param x The x component
     * @param y The y component
     */
    public Double2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Double2 with the given x and y components.
     * @param x The x component
     * @param y The y component
     * @return The new Double2
     */
    public static Double2 of(double x, double y) {
        return new Double2(x, y);
    }

    /**
     * Represents the zero vector.
     */
    public static Double2 ZERO = of(0, 0);

    /**
     * Represents the one vector.
     */
    public static Double2 ONE = of(1, 1);

    /**
     * Represents the unit y vector.
     */
    public static Double2 UP = of(0, 1);

    /**
     * Represents the negative unit y vector.
     */
    public static Double2 DOWN = of(0, -1);

    /**
     * Represents the negative unit x vector.
     */
    public static Double2 LEFT = of(-1, 0);

    /**
     * Represents the unit x vector.
     */
    public static Double2 RIGHT = of(1, 0);

    public static Double2 fromPolar(double radius, double angle) {
        return of(Math.cos(angle) * radius, Math.sin(angle) * radius);
    }

    /**
     * Generates a random Double2 with the given minimum and maximum values.
     * @param min The minimum value
     * @param max The maximum value
     * @return The random Double2
     */
    public static Double2 random(double min, double max) {
        // TODO Incorporate seed
        return of(Math.random() * (max - min) + min, Math.random() * (max - min) + min);
    }

    /**
     * Generates a random Double2 inside a circle with the given radius.
     * @param radius The radius of the circle
     * @return The random Double2
     */
    public static Double2 randomInCircle(double radius) {
        // TODO Incorporate seed
        double angle = Math.random() * Math.PI * 2; // TODO Cache this
        double distance = Math.random() * radius;
        return fromPolar(distance, angle);
    }

    /**
     * Generates a random Double2 on a circle with the given radius.
     * @param radius The radius of the circle
     * @return The random Double2
     */
    public static Double2 randomOnCircle(double radius) {
        // TODO Incorporate seed
        double angle = Math.random() * Math.PI * 2; // TODO Cache this
        return fromPolar(radius, angle);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Double2 other)) return false;
        return getX() == other.getX() && getY() == other.getY();
    }

    public Double2 add(Double2 other) {
        return of(getX() + other.getX(), getY() + other.getY());
    }

    public Double2 add(double scalar) {
        return of(getX() + scalar, getY() + scalar);
    }

    public Double2 add(double scalarX, double scalarY) {
        return of(getX() + scalarX, getY() + scalarY);
    }

    public Double2 subtract(Double2 other) {
        return of(getX() - other.getX(), getY() - other.getY());
    }

    public Double2 subtract(Long2 other) {
        return of(getX() - other.getX(), getY() - other.getY());
    }

    public Double2 subtract(double scalar) {
        return of(getX() - scalar, getY() - scalar);
    }

    public Double2 subtract(double scalarX, double scalarY) {
        return of(getX() - scalarX, getY() - scalarY);
    }

    public Double2 multiply(double scalar) {
        return of(getX() * scalar, getY() * scalar);
    }

    public Double2 divide(double scalar) {
        return of(getX() / scalar, getY() / scalar);
    }

    public double dot(Double2 other) {
        return getX() * other.getX() + getY() * other.getY();
    }

    public double magnitude() {
        return Math.sqrt(squareMagnitude());
    }

    public double squareMagnitude() {
        return dot(this);
    }

    public Double2 normalized() {
        return divide(magnitude());
    }

    public double distance(Double2 other) {
        return Math.sqrt(squareDistance(other));
    }

    public double squareDistance(Double2 other) {
        return subtract(other).squareMagnitude();
    }

    public double getAngle() {
        return Math.atan2(getY(), getX());
    }

    public double angleBetween(Double2 other) {
        return Math.atan2(getY() - other.getY(), getX() - other.getX());
    }

    public Double2 rotate(double angle) {
        return of(getX() * Math.cos(angle) - getY() * Math.sin(angle), getX() * Math.sin(angle) + getY() * Math.cos(angle));
    }

    public Double2 rotateAround(Double2 other, double angle) {
        return subtract(other).rotate(angle).add(other);
    }

    public Double2 lerp(Double2 other, double t) {
        return of(getX() + (other.getX() - getX()) * t, getY() + (other.getY() - getY()) * t);
    }

    public Long2 floor() {
        return Long2.of(MathUtil.fastFloor(getX()), MathUtil.fastFloor(getY()));
    }

    public double sum() {
        return getX() + getY();
    }

    public double difference() {
        return getX() - getY();
    }

    public Double2 hadamard(Double2 other) {
        return of(getX() * other.getX(), getY() * other.getY());
    }
}
