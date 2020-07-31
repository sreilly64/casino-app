package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

public interface GamblingGame {

    Integer getCurrentBet();

    void bet(Integer amount);

    void bet();

    void clearBets();

    void payout(Player player, Integer amount);
}
