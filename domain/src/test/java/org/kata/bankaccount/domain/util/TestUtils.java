package org.kata.bankaccount.domain.util;

import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

    /**
     * Generates a random int
     *
     * @param min The minimum int to generate (inclusive)
     * @param max The maximum int to generate (exclusive)
     * @return The random int
     */
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
