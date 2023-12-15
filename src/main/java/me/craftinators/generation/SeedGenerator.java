package me.craftinators.generation;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates random seeds, as well as seeds based on a <code>String</code>. Uses same long manipulation as seen in {@link java.util.Random}.
 */
public final class SeedGenerator {
    private SeedGenerator() {}

    private static final long multiplier = 0x5DEECE66DL;
    private static final long mask = (1L << 48) - 1;
    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);

    /**
     * Generates a random seed.
     * @return a random seed
     */
    public static long generate() {
        return seedUniquifier() ^ System.nanoTime();
    }

    /**
     * Generates a seed based on a given string.
     * @param seed the string to generate a seed from
     * @return a seed based on the given string
     */
    public static long generate(String seed) {
        return initialScramble(seed.hashCode());
    }

    private static long seedUniquifier() {
        for (;;) {
            long current = seedUniquifier.get();
            long next = current * 1181783497276652981L;
            if (seedUniquifier.compareAndSet(current, next)) return next;
        }
    }

    private static long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }
}
