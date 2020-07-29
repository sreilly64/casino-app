package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
public class BlackJack extends CardGame implements GamblingGame{
    public static String gameName = "Black Jack";
    @Override
    public void resetGame() {

    }

    @Override
    public void quitGame() {

    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public void startGame(Player player) {

    }
}
