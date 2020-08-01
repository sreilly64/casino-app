package io.zipcoder.casino;

import io.zipcoder.casino.games.BlackJack;

public class Casino {
    public static void main(String[] args) {
        // write your tests before you start
        //CasinoDriver test = new CasinoDriver();
        BlackJack bj = new BlackJack(Card.getNewDeck());
        boolean inPlay = true;
        bj.resetGame();
        bj.playRound(inPlay);
        //bj.getWinner();
    }
}
