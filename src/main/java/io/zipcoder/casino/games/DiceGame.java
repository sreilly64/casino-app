package io.zipcoder.casino.games;

import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.utilities.Console;

import java.util.ArrayList;

public abstract class DiceGame implements Game {
    Console console;
    private Integer numDice;

    protected DiceGame() {
        this.console = new Console(System.in, System.out);
    }

    public ArrayList<Integer> rollDice(Integer numDice){
        ArrayList<Integer> outcomes = new ArrayList<Integer>();

        for(int i = 1; i <= numDice; i++){
            Integer diceRoll = (int) Math.floor((Math.random()*6)+1);
            outcomes.add(diceRoll);
        }

        return outcomes;
    }

}
