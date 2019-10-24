package io.github.algodiv.cards_engine.commons.cards;

/**
 * Holds the possible Suites of a playing card (ex: SPADES).
 */
public enum Suite {
    SPADES((byte) 0b00000000),
    HEARTS((byte) 0b00010000),
    DIAMONDS((byte) 0b00100000),
    CLUBS((byte) 0b00110000);

    /**
     * Used for storing the card possessing this Suite.
     */
    final byte mask;
    /**
     * String value for the properly capitalized name (ex: Spades).
     */
    private final String properName;

    Suite(byte mask) {
        this.mask = mask;

        String actualName = super.toString();
        properName = actualName.substring(0, 1) + actualName.substring(1).toLowerCase();
    }

    /**
     * Returns a Suite that the input byte has a matching bit mask for.
     *
     * @param b Determines which Suite will be returned.
     * @return a Suite that the input byte has a matching bit mask for.
     */
    static Suite getInstance(byte b) {
        Suite[] suites = Suite.values();
        for (int i = suites.length - 1; i >= 0; i--) {
            if (suites[i].matchesMask(b)) {
                return suites[i];
            }
        }
        throw new IllegalArgumentException("No matching masks " + b);
    }

    private boolean matchesMask(byte b) {
        return (mask & b) == mask;
    }

    @Override
    public String toString() {
        return properName;
    }
}
