package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

public class testMain {

    public static void main(String[] args){
        //Craps test = new Craps();
        Player testPlayer = new Player("Bob", 1000);
        CrapsApp test = new CrapsApp();

        test.launcher(testPlayer);
    }
}
