package io.zipcoder.casino.player;

public class Player implements GamblingPlayer {
    private String name;
    public Player(String name, Integer startingFunds) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
