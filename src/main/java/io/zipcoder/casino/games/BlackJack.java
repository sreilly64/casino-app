package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.List;
public class BlackJack extends CardGame implements GamblingGame{
    public static String gameName = "Black Jack";

    public BlackJack(List<Card> currentDeck) {
        super(currentDeck);
    }

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
    void dealCards(Integer numOfCards) {

    }

    @Override
    public void getWinner() {

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
