package io.zipcoder.casino;

import io.zipcoder.casino.games.BlackJack;
import io.zipcoder.casino.games.GoingToBoston;
import io.zipcoder.casino.player.Player;

public class Casino {
    public static void main(String[] args) {
        // write your tests before you start
        //CasinoDriver test = new CasinoDriver();
        BlackJack bj = new BlackJack(Card.getNewDeck());
        bj.startGame(new Player("Lake", 1000));
//        GoingToBoston gtb = new GoingToBoston();
//        gtb.startGame(new Player("Lake", 1000));
    }
}
