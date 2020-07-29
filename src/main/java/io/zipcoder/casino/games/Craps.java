package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.Map;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comOutPhase;
    private Integer currentPoint;
    private Map<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer betAmount;
    private Player player;
    private CrapsGUI ui;

    public enum BetType {PASS, PASS_ODDS, DONT_PASS, DONT_PASS_ODDS, COME,COME_4, COME_5, COME_6, COME_8, COME_9,
        COME_10, COME_ODDS_4, COME_ODDS_5, COME_ODDS_6, COME_ODDS_8, COME_ODDS_9, COME_ODDS_10, DONT_COME, DONT_COME_4,
        DONT_COME_5, DONT_COME_6, DONT_COME_8, DONT_COME_9, DONT_COME_10, DONT_COME_ODDS_4, DONT_COME_ODDS_5,
        DONT_COME_ODDS_6, DONT_COME_ODDS_8, DONT_COME_ODDS_9, DONT_COME_ODDS_10, PLACE_WIN_4, PLACE_WIN_5, PLACE_WIN_6,
        PLACE_WIN_8, PLACE_WIN_9, PLACE_WIN_10, PLACE_LOSE_4, PLACE_LOSE_5, PLACE_LOSE_6, PLACE_LOSE_8, PLACE_LOSE_9,
        PLACE_LOSE_10, BUY_4, BUY_5, BUY_6, BUY_8, BUY_9, BUY_10, LAY_4, LAY_5, LAY_6, LAY_8, LAY_9, LAY_10, BIG_6,
        BIG_8, HARD_4, HARD_6, HARD_8, HARD_10, FIELD, ANY_CRAPS, CRAPS_2, CRAPS_3, CRAPS_12, ANY_7, ANY_11};

    public Craps() {
        this.comOutPhase = true;
        this.currentPoint = 0;
        //this.ui = new CrapsGUI(this);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Integer getCurrentBet() {
        return null;
    }

    public void bet(Integer amount) {

    }

    public void clearBets() {

    }

    public void payout(Player player, Integer amount) {

    }

    public void resetGame() {

    }

    public void quitGame() {
        clearBets();
        //shutdown javafx
    }

    public String getGameName() {
        return "Craps";
    }

    public void startGame(Player player) {
        setPlayer(player);
        //initiate javafx
        this.ui = new CrapsGUI(this);
    }
}
