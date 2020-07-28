package io.zipcoder.casino.player;

public class Player implements GamblingPlayer {

    private String name;
    private Integer currentFunds;

    public Player(String name, Integer initialFunds) {
        this.name = name;
        this.currentFunds = initialFunds;
    }

    public String getName(){
        return this.name;
    }

    public Integer getCurrentFunds() {
        return this.currentFunds;
    }

    public void addToCurrentFunds(Integer amount) {
        this.currentFunds += amount;
    }
}
