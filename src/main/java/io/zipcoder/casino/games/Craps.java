package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
public class Craps extends DiceGame implements GamblingGame{
    public static String gameName = "Craps";

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

    @Override
    public Integer getCurrentBet() {
        return null;
    }

    @Override
    public void bet(Integer amount) {

    }

    @Override
    public void clearBets() {

    }

    @Override
    public void payout(Player player, Integer amount) {

    }
}
