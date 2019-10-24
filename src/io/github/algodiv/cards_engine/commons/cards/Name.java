package io.github.algodiv.cards_engine.commons.cards;

/**
 * Holds the possible Names of a playing card (ex: ACE).
 */
public enum Name {
    ACE((byte) 0b00000000),
    TWO((byte) 0b00000001),
    THREE((byte) 0b00000010),
    FOUR((byte) 0b00000011),
    FIVE((byte) 0b00000100),
    SIX((byte) 0b00000101),
    SEVEN((byte) 0b00000110),
    EIGHT((byte) 0b00000111),
    NINE((byte) 0b00001000),
    TEN((byte) 0b00001001),
    JACK((byte) 0b00001010),
    QUEEN((byte) 0b00001011),
    KING((byte) 0b00001100),
    JOKER((byte) 0b00001101);

    /**
     * Used for storing the card possessing this Name.
     */
    final byte mask;
    /**
     * String value for the properly capitalized name (ex: Ace).
     */
    private final String properName;

    Name(byte mask) {
        this.mask = mask;

        String actualName = super.toString();
        properName = actualName.substring(0, 1) + actualName.substring(1).toLowerCase();
    }

    /**
     * Returns a Name that the input byte has a matching bit mask for.
     *
     * @param b Determines which Name will be returned.
     * @return a Name that the input byte has a matching bit mask for.
     */
    static Name getInstance(byte b) {
        Name[] names = Name.values();
        for (int i = names.length - 1; i >= 0; i--) {
            if (names[i].matchesMask(b)) {
                return names[i];
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
