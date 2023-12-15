package me.craftinators;

import me.craftinators.generation.Seedable;
import me.craftinators.generation.SeedGenerator;

/**
 * Represents the game.
 */
public final class Game implements Seedable {
    private final long seed;

    public Game(String seed) {
        this.seed = generateSeed(seed);
    }

    private static long generateSeed(String seed) {
        // If seed is null or empty, generate a random seed
        if (seed == null || seed.trim().isEmpty()) return SeedGenerator.generate();
        try {
            // If seed is a number, use it as seed
            return Long.parseLong(seed);
        } catch (NumberFormatException exception) {
            // If seed is not a number, generate a seed from it
            return SeedGenerator.generate(seed.trim());
        }
    }

    @Override
    public long getSeed() {
        return seed;
    }
}
