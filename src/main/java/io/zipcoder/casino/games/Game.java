package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

public interface Game {

    void resetGame();

    void quitGame();

    String getGameName();

    void startGame(Player player);
}
