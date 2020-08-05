package io.zipcoder.casino.player;

public interface GamblingPlayer {

    Integer getCurrentFunds();

    void addToCurrentFunds(Integer amount);

    void subtractFromCurrentFunds(Integer amount);
}
