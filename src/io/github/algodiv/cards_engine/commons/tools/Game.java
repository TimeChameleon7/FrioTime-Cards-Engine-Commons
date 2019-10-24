package io.github.algodiv.cards_engine.commons.tools;

/**
 * Represents a card game, such as Poker.
 */
public interface Game {

    /**
     * Initializes all objects for the game, this will be called if a player selects this game to play.
     */
    void init();

    /**
     * Returns the name of the game, will always be called first by the Game Engine.
     * @return The game's name.
     */
    String getName();
}
