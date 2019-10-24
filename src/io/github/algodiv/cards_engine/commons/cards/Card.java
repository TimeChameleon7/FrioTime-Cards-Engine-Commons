package io.github.algodiv.cards_engine.commons.cards;

import java.nio.ByteBuffer;

/**
 * Represents a standard playing card.
 * <p>
 * Note: Joker cards have a {@code null} {@linkplain Suite},
 * references to Joker cards should check for the {@linkplain Name}
 * of the card.
 */
public class Card {
    /**
     * The Name of a card, such as "ACE".
     */
    public final Name name;
    /**
     * The Suite of a card, such as "SPADES".
     */
    public final Suite suite;

    /**
     * Creates a card from the input {@linkplain Name} and {@linkplain Suite}.
     * Note: If the Name of the card is {@code JOKER} then Suite will be set to null.
     *
     * @param name  The Name of the card.
     * @param suite The Suite of the card.
     */
    public Card(Name name, Suite suite) {
        this.name = name;
        if (name != Name.JOKER) {
            this.suite = suite;
        } else {
            this.suite = null;
        }
    }

    /**
     * Creates a card from the byte input, using the bit masks from {@linkplain Name} & {@linkplain Suite}.
     *
     * @param b The byte to be converted to a Card.
     */
    public Card(byte b) {
        this(Name.getInstance(b), Suite.getInstance(b));
    }

    /**
     * Returns a byte[] containing the byte representation of {@code this}.
     * <p>
     * Note: The byte[] will always have a length of 1.
     *
     * @return a byte[] containing the byte representation of {@code this}.
     */
    public byte[] toBytes() {
        if (name == Name.JOKER) {
            return new byte[]{name.mask};
        } else {
            return new byte[]{(byte) (suite.mask ^ name.mask)};
        }
    }

    /**
     * Returns {@code true} if {@code this} Card's {@linkplain Name} is equal to the input Name.
     *
     * @param name Name to check {@code this} Card's Name against.
     * @return {@code true} if {@code this} Card's Name is equal to the input Name.
     */
    public boolean is(Name name) {
        return this.name == name;
    }

    /**
     * Returns {@code true} if {@code this} Card's {@linkplain Suite} is equal to the input Suite.
     *
     * @param suite Suite to check {@code this} Card's Suite against.
     * @return {@code true} if {@code this} Card's Suite is equal to the input Suite.
     */
    public boolean is(Suite suite) {
        return this.suite == suite;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) return false;

        Card card = (Card) obj;
        return card.suite == suite && card.name == name;
    }

    @Override
    public int hashCode() {
        return ByteBuffer.allocate(Integer.BYTES).put(toBytes()).getInt();
    }

    @Override
    public String toString() {
        if (name == Name.JOKER) {
            return name.toString();
        } else {
            return name.toString() + " of " + suite.toString();
        }
    }
}
