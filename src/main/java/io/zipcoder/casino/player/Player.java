package io.zipcoder.casino.player;

import java.util.Objects;

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

    @Override
    public void subtractFromCurrentFunds(Integer amount) {
        this.currentFunds -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) { return true; }
        if(o == null || getClass() != o.getClass()) { return false; }
        Player player = (Player) o;
        return name.equals(player.name) && currentFunds.equals(player.currentFunds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currentFunds);
    }
}