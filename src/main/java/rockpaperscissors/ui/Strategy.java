package rockpaperscissors.ui;

import rockpaperscissors.logic.Player;

import java.util.function.Supplier;

/**
 * A strategy for a rock-paper-scissors game, consisting of a player factory and a human-readable description.
 *
 * @implSpec This class is part of the user interface and not intended to be accessed by external code.
 */
class Strategy {

    private final Supplier<Player> playerFactory;
    private final String descriptionKey;

    Strategy(Supplier<Player> createPlayer, String descriptionKey) {
        this.playerFactory = createPlayer;
        this.descriptionKey = descriptionKey;
    }

    Player createPlayer() {
        return playerFactory.get();
    }

    String getDescriptionKey() {
        return descriptionKey;
    }
}
