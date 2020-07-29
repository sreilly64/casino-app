package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.List;

public class GoFish extends CardGame {
    public static String gameName = "Go Fish";
    public GoFish(List<Card> currentDeck) {
        super(currentDeck);
    }

    void dealCards(Integer numOfCards) {
    }

    @Override
    public void getWinner() {
    }

    @Override
    public void resetGame() {
    }

    public void quitGame() {
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    public void startGame(Player player) {

    }
}
