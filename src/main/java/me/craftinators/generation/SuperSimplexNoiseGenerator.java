package me.craftinators.generation;

import me.craftinators.math.Double2;
import me.craftinators.math.Long2;

/**
 * 2D OpenSimplex2S/SuperSimplex noise generator.
 */
public class SuperSimplexNoiseGenerator implements Seedable {
    private static final Long2 PRIME = Long2.of(0x5205402B9270C86FL, 0x598CD327003817B5L);
    private static final long HASH_MULTIPLIER = 0x53A3F72DEEC546F5L;
    private static final double SKEW = 0.366025403784439d, UNSKEW = -0.21132486540518713d;
    private static final int N_GRADS_EXPONENT = 7;
    private static final int N_GRADS = 1 << N_GRADS_EXPONENT;
    private static final double RSQUARED = 0.66666666666666666d;

    private final long seed;

    public SuperSimplexNoiseGenerator(long seed) {
        this.seed = seed;
    }

    /**
     * 2D OpenSimplex2S/SuperSimplex noise, standard lattice orientation.
     */
    public double at(Double2 point) {
        final double skew = point.sum() * SKEW;
        return getBaseNoise(point.add(skew));
    }

    @Override
    public long getSeed() {
        return seed;
    }

    private static final double[] GRADIENTS = new double[] {
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378,
            2.3810537002291805, 18.085899875926934, 11.105002835667612, 14.472321057093197, 14.472321057093197, 11.105002835667612, 18.085899875926934, 2.3810537002291805,
            18.085899875926934, -2.3810537002291805, 14.472321057093197, -11.105002835667612, 11.105002835667612, -14.472321057093197, 2.3810537002291805, -18.085899875926934,
            -2.3810537002291805, -18.085899875926934, -11.105002835667612, -14.472321057093197, -14.472321057093197, -11.105002835667612, -18.085899875926934, -2.3810537002291805,
            -18.085899875926934, 2.3810537002291805, -14.472321057093197, 11.105002835667612, -11.105002835667612, 14.472321057093197, -2.3810537002291805, 18.085899875926934,
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378,
            2.3810537002291805, 18.085899875926934, 11.105002835667612, 14.472321057093197, 14.472321057093197, 11.105002835667612, 18.085899875926934, 2.3810537002291805,
            18.085899875926934, -2.3810537002291805, 14.472321057093197, -11.105002835667612, 11.105002835667612, -14.472321057093197, 2.3810537002291805, -18.085899875926934,
            -2.3810537002291805, -18.085899875926934, -11.105002835667612, -14.472321057093197, -14.472321057093197, -11.105002835667612, -18.085899875926934, -2.3810537002291805,
            -18.085899875926934, 2.3810537002291805, -14.472321057093197, 11.105002835667612, -11.105002835667612, 14.472321057093197, -2.3810537002291805, 18.085899875926934,
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378,
            2.3810537002291805, 18.085899875926934, 11.105002835667612, 14.472321057093197, 14.472321057093197, 11.105002835667612, 18.085899875926934, 2.3810537002291805,
            18.085899875926934, -2.3810537002291805, 14.472321057093197, -11.105002835667612, 11.105002835667612, -14.472321057093197, 2.3810537002291805, -18.085899875926934,
            -2.3810537002291805, -18.085899875926934, -11.105002835667612, -14.472321057093197, -14.472321057093197, -11.105002835667612, -18.085899875926934, -2.3810537002291805,
            -18.085899875926934, 2.3810537002291805, -14.472321057093197, 11.105002835667612, -11.105002835667612, 14.472321057093197, -2.3810537002291805, 18.085899875926934,
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378,
            2.3810537002291805, 18.085899875926934, 11.105002835667612, 14.472321057093197, 14.472321057093197, 11.105002835667612, 18.085899875926934, 2.3810537002291805,
            18.085899875926934, -2.3810537002291805, 14.472321057093197, -11.105002835667612, 11.105002835667612, -14.472321057093197, 2.3810537002291805, -18.085899875926934,
            -2.3810537002291805, -18.085899875926934, -11.105002835667612, -14.472321057093197, -14.472321057093197, -11.105002835667612, -18.085899875926934, -2.3810537002291805,
            -18.085899875926934, 2.3810537002291805, -14.472321057093197, 11.105002835667612, -11.105002835667612, 14.472321057093197, -2.3810537002291805, 18.085899875926934,
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378,
            2.3810537002291805, 18.085899875926934, 11.105002835667612, 14.472321057093197, 14.472321057093197, 11.105002835667612, 18.085899875926934, 2.3810537002291805,
            18.085899875926934, -2.3810537002291805, 14.472321057093197, -11.105002835667612, 11.105002835667612, -14.472321057093197, 2.3810537002291805, -18.085899875926934,
            -2.3810537002291805, -18.085899875926934, -11.105002835667612, -14.472321057093197, -14.472321057093197, -11.105002835667612, -18.085899875926934, -2.3810537002291805,
            -18.085899875926934, 2.3810537002291805, -14.472321057093197, 11.105002835667612, -11.105002835667612, 14.472321057093197, -2.3810537002291805, 18.085899875926934,
            6.9808964966064915, 16.853374757322378, 16.853374757322378, 6.9808964966064915, 16.853374757322378, -6.9808964966064915, 6.9808964966064915, -16.853374757322378,
            -6.9808964966064915, -16.853374757322378, -16.853374757322378, -6.9808964966064915, -16.853374757322378, 6.9808964966064915, -6.9808964966064915, 16.853374757322378
    };

    @SuppressWarnings("DuplicatedCode") // TODO Remove duplication
    private double getBaseNoise(Double2 point) {
        // Get base points and offsets.
        Long2 flooredPoint = point.floor();
        Double2 decimalPoint = point.subtract(flooredPoint);

        // Prime pre-multiplication for hash.
        Long2 hashedPoint = flooredPoint.hadamard(PRIME);

        double offset = decimalPoint.sum() * UNSKEW;
        Double2 delta = decimalPoint.add(offset);

        // First vertex.
        double firstVertex = RSQUARED - Math.pow(delta.getX(), 2) - Math.pow(delta.getY(), 2);
        double value = Math.pow(firstVertex, 4) * getGradient(hashedPoint, delta);

        // Second vertex.
        double secondVertex = (2 * (1 + 2 * UNSKEW) * (1 / UNSKEW + 2)) * offset + ((-2 * (1 + 2 * UNSKEW) * (1 + 2 * UNSKEW)) + firstVertex);
        Double2 delta1 = delta.subtract(1 + 2 * UNSKEW);
        value += Math.pow(secondVertex, 4) * getGradient(hashedPoint.add(PRIME), delta1);

        // Third and fourth vertices.
        // Nested conditionals were faster than compact bit logic/arithmetic.
        double decimalDifference = decimalPoint.difference();
        if (offset < UNSKEW) {
            if (decimalPoint.getX() + decimalDifference > 1) {
                Double2 delta2 = delta.subtract(3 * UNSKEW + 2, 3 * UNSKEW + 1);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.add(PRIME.getX() << 1, PRIME.getY()), delta2);
            } else {
                Double2 delta2 = delta.subtract(UNSKEW, UNSKEW + 1);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.add(0, PRIME.getY()), delta2);
            }
            if (decimalPoint.getY() - decimalDifference > 1) {
                Double2 delta3 = delta.subtract(3 * UNSKEW + 1, 3 * UNSKEW + 2);
                double fourthVertex = RSQUARED - Math.pow(delta3.getX(), 2) - Math.pow(delta3.getY(), 2);
                if (fourthVertex > 0) value += Math.pow(fourthVertex, 4) * getGradient(hashedPoint.add(PRIME.getX(), PRIME.getY() << 1), delta3);
            } else {
                Double2 delta3 = delta.subtract(UNSKEW + 1, UNSKEW);
                double fourthVertex = RSQUARED - Math.pow(delta3.getX(), 2) - Math.pow(delta3.getY(), 2);
                if (fourthVertex > 0) value += Math.pow(fourthVertex, 4) * getGradient(hashedPoint.add(PRIME.getX(), 0), delta3);
            }
        } else {
            if (decimalPoint.getX() + decimalDifference < 0) {
                Double2 delta2 = delta.add(1 + UNSKEW, UNSKEW);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.subtract(PRIME.getX(), 0), delta2);
            } else {
                Double2 delta2 = delta.subtract(UNSKEW + 1, UNSKEW);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.add(PRIME.getX(), 0), delta2);
            }
            if (decimalPoint.getY() < decimalDifference) {
                Double2 delta2 = delta.add(UNSKEW, UNSKEW + 1);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.subtract(0, PRIME.getY()), delta2);
            } else {
                Double2 delta2 = delta.subtract(UNSKEW, UNSKEW + 1);
                double thirdVertex = RSQUARED - Math.pow(delta2.getX(), 2) - Math.pow(delta2.getY(), 2);
                if (thirdVertex > 0) value += Math.pow(thirdVertex, 4) * getGradient(hashedPoint.add(0, PRIME.getY()), delta2);
            }
        }
        return value;
    }

    private double getGradient(Long2 point, Double2 delta) {
        long hash = getSeed() ^ point.getX() ^ point.getY();
        hash *= HASH_MULTIPLIER;
        hash ^= hash >> (64 - N_GRADS_EXPONENT + 1);
        int gradient = (int) hash & ((N_GRADS - 1) << 1);
        return GRADIENTS[gradient] * delta.getX() + GRADIENTS[gradient | 1] * delta.getY();
    }
}
