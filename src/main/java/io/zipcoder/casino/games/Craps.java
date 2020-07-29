package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import io.zipcoder.casino.utilities.Console;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comOutPhase;
    private Integer currentPoint;
    private Map<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer betAmount;
    private Player player;
    private CrapsApp ui;
    private Boolean playingGame;

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
    }

    private void setUpBetsMap() {
        Map<BetType, Integer> output = new LinkedHashMap<BetType, Integer>();
        for(BetType bet: BetType.values()){
            output.put(bet, 0);
        }
        currentBets = output;
    }

    public Boolean getGamePhase(){
        return this.comOutPhase;
    }

    public Integer getCurrentPoint(){
        return this.currentPoint;
    }

    public ArrayList<Integer> getDiceRolls() {
        return diceRolls;
    }

    public void setLastDiceRoll(){
        this.diceRolls = rollDice(2);
    }

    public Integer calculatePayoffs(){

        return null;
    }

    public BetType selectBetType(String betType){

        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public Integer getCurrentBet() {
        Integer totalBets = 0;
        for(Integer bet: this.currentBets.values()){
            totalBets += bet;
        }
        return totalBets;
    }

    public Integer getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Integer betAmount) {
        this.betAmount = betAmount;
    }

    public void bet(Integer amount) {

    }

    public void clearBets() {

    }

    public void payout(Player player, Integer amount) {

    }

    public void actionSelection(){
        if(getCurrentBet() == 0){
            System.out.println("What type of bet would you like to make?");
        }else{
            System.out.println("Do you want to roll or place further bets?");
        }
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
        setUpBetsMap();
        System.out.println("Welcome to the Craps table!");
        this.playingGame = true;
        while(this.playingGame){
            actionSelection();
        }

        /*
        //initiate javafx
        CrapsApp test = new CrapsApp();
        this.ui = test;

        test.launcher(player); */
    }
}
