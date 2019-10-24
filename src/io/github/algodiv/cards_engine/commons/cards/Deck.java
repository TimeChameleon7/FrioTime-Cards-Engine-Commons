package io.github.algodiv.cards_engine.commons.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * Represents a stack of {@linkplain Card}s.
 * <p>
 * Instances of this class can only contain subsets of a standard deck of cards,
 * if another card set is desired, use {@link CustomDeck}.
 * <p>
 * Instances of this class are also intended to be made as programmatic mistake safe,
 * as such, it prevents direct public access to the internal {@linkplain Card} list,
 * as well as preventing the moving of cards without using a method to specify the destination.
 * For more control over these operations, use {@linkplain CustomDeck} or extend this class.
 */
public class Deck {
    /**
     * A standard deck of cards, jokers included.
     */
    private final static Card[] fullDeck = new Card[54];

    static {
        //fullDeck loading
        Name[] names = Name.values();
        Suite[] suites = Suite.values();

        int i = 0;
        for (Suite suite : suites) {
            for (int n = 0; n < names.length - 1; n++) {
                fullDeck[i++] = new Card(names[n], suite);
            }
        }
        fullDeck[52] = new Card(Name.JOKER, null);
        fullDeck[53] = new Card(Name.JOKER, null);
    }

    /**
     * The {@linkplain Card}s contained within {@code this} Deck.
     * <p>
     * The first index is treated as the bottom of the card stack.
     */
    protected final ArrayList<Card> cards;

    /**
     * Makes a Deck with an empty cards list.
     */
    protected Deck() {
        cards = new ArrayList<>();
    }

    /**
     * Makes a deck of cards according to the {@linkplain Preset} specified.
     * <p>
     * For a higher level of deck loading control, use {@link Deck#Deck(Predicate)}.
     *
     * @param preset The Preset that decides how cards are loaded.
     */
    public Deck(Preset preset) {
        this(preset.predicate);
    }

    /**
     * Makes a deck of cards according to the {@linkplain Predicate} specified.
     * This will load the cards from {@linkplain Deck#fullDeck} into the internal {@linkplain Card} list,
     * if the predicate returns true for that card.
     *
     * @param predicate The Predicate that decides how cards are loaded.
     */
    public Deck(Predicate<Card> predicate) {
        cards = new ArrayList<>();
        for (Card card : fullDeck) {
            if (predicate.test(card)) {
                cards.add(card);
            }
        }
    }

    /**
     * Returns the current array of {@linkplain Card}s.
     *
     * @return the current array of {@linkplain Card}s.
     */
    public Card[] getCards() {
        return cards.toArray(Card[]::new);
    }

    /**
     * Shuffles the deck.
     *
     * @return {@code this}.
     */
    public Deck shuffle() {
        Collections.shuffle(cards);
        return this;
    }

    /**
     * Returns {@code true} if {@code this} deck can move {@literal count} cards.
     *
     * @param count Number of cards to check if could be moved.
     * @return {@code true} if {@code this} deck can move {@literal count} cards.
     */
    public boolean canMove(int count) {
        return cards.size() >= count;
    }

    /**
     * Moves the specified {@linkplain Card} from {@code this} Deck to the specified Deck.
     *
     * @param card The card to be removed from this deck, and added to the target deck.
     * @param deck The deck that the specified card should be added to.
     * @return {@code true} if the movement was successful.
     */
    public boolean move(Card card, Deck deck) {
        if (cards.remove(card)) {
            deck.cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * Moves the top {@linkplain Card} of {@code this} {@linkplain Card} stack (last index in array) to the
     * specified deck.
     *
     * @param deck Deck that will gain a {@linkplain Card} if the operation is successful.
     * @return {@code true} if the operation was successful.
     * Will only fail if this deck is empty.
     */
    public boolean moveTop(Deck deck) {
        if (canMove(1)) {
            moveTop0(deck);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Programmatic form of dealing one card from {@code this} Deck to each deck in the specified Deck[].
     *
     * @param decks the decks to deal to.
     * @return {@code true} if the operation was successful.
     * Will only fail if this deck has less cards than Deck[].length
     */
    public boolean deal(Deck[] decks) {
        if (canMove(decks.length)) {
            deal0(decks);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Programmatic form of dealing {@code count} cards from {@code this} Deck to each deck in the specified Deck[].
     *
     * @param decks Decks to deal to. Each will gain {@code count} cards.
     * @param count Amount of cards to deal to each deck.
     * @return {@code true} if the operation was successful.
     * Will only fail if this deck has less cards than count * Deck[].length.
     */
    public boolean deal(Deck[] decks, int count) {
        if (canMove(count * decks.length)) {
            for (int i = 0; i < count; i++) {
                deal0(decks);
            }
            return true;
        }
        return false;
    }

    /**
     * As specified in {@link Deck#deal(Deck[])} but without the size check or boolean return.
     */
    private void deal0(Deck[] decks) {
        for (Deck deck : decks) {
            moveTop0(deck);
        }
    }

    /**
     * As specified in {@link Deck#moveTop(Deck)} but without the size check or boolean return.
     */
    private void moveTop0(Deck deck) {
        Card card = cards.remove(cards.size() - 1);
        deck.cards.add(card);
    }

    @Override
    public String toString() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = this.cards.size() - 1; i >= 0; i--) {
            cards.add(this.cards.get(i));
        }
        return String.format("%s:[%s]", getClass(), cards);
    }

    /**
     * Holds pre-made {@linkplain Predicate}s for ease of Deck creation.
     */
    public enum Preset {
        FULL_DECK(card -> true),
        NO_JOKERS(card -> !card.is(Name.JOKER)),
        EMPTY(card -> false);

        final Predicate<Card> predicate;

        Preset(Predicate<Card> predicate) {
            this.predicate = predicate;
        }
    }
}
