package io.github.algodiv.cards_engine.commons.cards;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * A more customizable version of {@linkplain Deck}.
 */
public class CustomDeck extends Deck {

    /**
     * Makes a CustomDeck with an empty cards list.
     */
    public CustomDeck() {
        super();
    }

    /**
     * Makes a CustomDeck according to the {@linkplain io.github.algodiv.cards_engine.commons.cards.Deck.Preset} specified.
     *
     * For a higher level of deck loading control, use {@link CustomDeck#CustomDeck(Predicate)}.
     *
     * @param preset The Preset that decides how cards are loaded.
     */
    public CustomDeck(Preset preset) {
        super(preset);
    }

    /**
     * Makes a CustomDeck according to the {@linkplain Predicate} specified.
     *
     * @see Deck#Deck(Predicate) for more documentation
     * @param predicate The Predicate that decides how cards are loaded.
     */
    public CustomDeck(Predicate<Card> predicate) {
        super(predicate);
    }

    /**
     * Returns the card list within {@code this}.
     *
     * @return the card list within {@code this}.
     */
    public ArrayList<Card> cards() {
        return cards;
    }

    /**
     * Removes and returns the top card (last index) {@linkplain Card} from the deck.
     *
     * @return The top card (last index) {@linkplain Card} from the deck.
     */
    public Card draw() {
        return cards.remove(cards.size() - 1);
    }

    /**
     * Puts the specified {@linkplain Card} on top (last) in the deck.
     *
     * @param card The {@linkplain Card} to add.
     * @return {@code this}.
     */
    public CustomDeck putOnTop(Card card) {
        cards.add(card);
        return this;
    }

    /**
     * Puts the specified {@linkplain Card} on bottom (first) in the deck.
     * Note: the computation cost of this scales linearly with the deck size.
     *
     * @param card The {@linkplain Card} to add.
     * @return {@code this}.
     */
    public CustomDeck putOnBottom(Card card) {
        cards.add(0, card);
        return this;
    }
}
