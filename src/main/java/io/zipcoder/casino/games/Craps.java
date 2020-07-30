package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.TreeMap;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comOutPhase;
    private Integer currentPoint;
    private TreeMap<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer betAmount;
    private Player player;
    private CrapsApp ui;
    private Boolean playingGame;
    private ArrayList<BetType> oddsBetsList = new ArrayList<>();
    private ArrayList<BetType> comeOutBetsList = new ArrayList<>();
    private ArrayList<BetType> pointPhaseBetsList = new ArrayList<>();
    private ArrayList<BetType> buyBetsList = new ArrayList<>();

    public static String gameName = "Craps";

    public Craps() {
        this.comOutPhase = true;
        this.currentPoint = 0;
    }

    public void setUpBetsMap() {
        TreeMap<BetType, Integer> output = new TreeMap<BetType, Integer>();
        for(BetType bet: BetType.values()){
            output.put(bet, 0);
        }
        currentBets = output;
    }

    public TreeMap<BetType, Integer> getCurrentBetsMap(){
        return currentBets;
    }

    public Boolean getGamePhase(){
        return this.comOutPhase;
    }

    public Integer getCurrentPoint(){
        return this.currentPoint;
    }

    public void setCurrentPoint(Integer newPoint){
        this.currentPoint = newPoint;
    }

    public Integer getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Integer betAmount) {
        this.betAmount = betAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
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

    public BetType selectBetType(String input){
        String betType = input.toLowerCase();
        //doesnt currently include come_bet_# types since those cannot be placed directly
        switch(betType){
            case "pass":
            case "pass line":
                return BetType.PASS;
            case "pass odds":
            case "pass line odds":
                return BetType.PASS_ODDS;
            case "dont pass":
            case "don't pass":
            case "dont pass line":
            case "don't pass line":
                return BetType.DONT_PASS;
            case "dont pass odds":
            case "don't pass odds":
                return BetType.DONT_PASS_ODDS;
            case "come":
            case "come bet":
                return BetType.COME;
            case "come odds 4":
                return BetType.COME_ODDS_4;
            case "come odds 5":
                return BetType.COME_ODDS_5;
            case "come odds 6":
                return BetType.COME_ODDS_6;
            case "come odds 8":
                return BetType.COME_ODDS_8;
            case "come odds 9":
                return BetType.COME_ODDS_9;
            case "come odds 10":
                return BetType.COME_ODDS_10;
            case "dont come":
            case "don't come":
            case "dont come bet":
            case "don't come bet":
                return BetType.DONT_COME;
            case "dont come odds 4":
            case "don't come odds 4":
                return BetType.DONT_COME_ODDS_4;
            case "dont come odds 5":
            case "don't come odds 5":
                return BetType.DONT_COME_ODDS_5;
            case "dont come odds 6":
            case "don't come odds 6":
                return BetType.DONT_COME_ODDS_6;
            case "dont come odds 8":
            case "don't come odds 8":
                return BetType.DONT_COME_ODDS_8;
            case "dont come odds 9":
            case "don't come odds 9":
                return BetType.DONT_COME_ODDS_9;
            case "dont come odds 10":
            case "don't come odds 10":
                return BetType.DONT_COME_ODDS_10;
            case "place 4":
            case "place win 4":
                return BetType.PLACE_WIN_4;
            case "place 5":
            case "place win 5":
                return BetType.PLACE_WIN_5;
            case "place 6":
            case "place win 6":
                return BetType.PLACE_WIN_6;
            case "place 8":
            case "place win 8":
                return BetType.PLACE_WIN_8;
            case "place 9":
            case "place win 9":
                return BetType.PLACE_WIN_9;
            case "place 10":
            case "place win 10":
                return BetType.PLACE_WIN_10;
            case "place lose 4":
                return BetType.PLACE_LOSE_4;
            case "place lose 5":
                return BetType.PLACE_LOSE_5;
            case "place lose 6":
                return BetType.PLACE_LOSE_6;
            case "place lose 8":
                return BetType.PLACE_LOSE_8;
            case "place lose 9":
                return BetType.PLACE_LOSE_9;
            case "place lose 10":
                return BetType.PLACE_LOSE_10;
            case "buy 4":
                return BetType.BUY_4;
            case "buy 5":
                return BetType.BUY_5;
            case "buy 6":
                return BetType.BUY_6;
            case "buy 8":
                return BetType.BUY_8;
            case "buy 9":
                return BetType.BUY_9;
            case "buy 10":
                return BetType.BUY_10;
            case "lay 4":
                return BetType.LAY_4;
            case "lay 5":
                return BetType.LAY_5;
            case "lay 6":
                return BetType.LAY_6;
            case "lay 8":
                return BetType.LAY_8;
            case "lay 9":
                return BetType.LAY_9;
            case "lay 10":
                return BetType.LAY_10;
            case "big 6":
            case "big6":
                return BetType.BIG_6;
            case "big 8":
            case "big8":
                return BetType.BIG_8;
            case "hard 4":
                return BetType.HARD_4;
            case "hard 6":
                return BetType.HARD_6;
            case "hard 8":
                return BetType.HARD_8;
            case "hard 10":
                return BetType.HARD_10;
            case "field":
            case "field bet":
                return BetType.FIELD;
            case "craps":
            case "any craps":
                return BetType.ANY_CRAPS;
            case "craps 2":
            case "2 craps":
                return BetType.CRAPS_2;
            case "craps 3":
            case "3 craps":
                return BetType.CRAPS_3;
            case "craps 12":
            case "12 craps":
                return BetType.CRAPS_12;
            case "seven":
            case "any seven":
                return BetType.ANY_7;
            case "eleven":
            case "any eleven":
                return BetType.ANY_11;
        }
        return null;
    }

    @Override
    public Integer getCurrentBet() {
        Integer totalBets = 0;
        for(Integer bet: this.currentBets.values()){
            totalBets += bet;
        }
        return totalBets;
    }

    public void bet(Integer amount) {

    }

    public void bet(BetType betType, Integer amount){

    }

    public void clearBets() {

    }

    public void payout(Player player, Integer amount) {

    }

    public void actionSelection(){
        if(getCurrentBet() == 0){
            String inputBetType = this.console.getStringInput("What type of bet would you like to make?");
            BetType chosenBetType = selectBetType(inputBetType);
            if(chosenBetType != null){
                if(isBetAvailable(chosenBetType)){
                    Integer betAmount = null;
                    while(betAmount == null){
                        Integer inputBetAmount = this.console.getIntegerInput("How much would you like to bet?\n" +
                                "Bet must be a multiple of " + getBetMultiple(chosenBetType) + "\n" +
                                "Minimum bet is 5.\n" +
                                "Max bet is " + getMaxBet(chosenBetType) + ".");
                        if(validateBetAmount(chosenBetType, inputBetAmount)){
                            betAmount = inputBetAmount;
                            //place bet
                        }else{
                            System.out.println("Bet amount did not meet requirements.");
                        }
                    }

                }else{
                    System.out.println("This bet is not available at this time.");
                }
            }else{
                System.out.println("Invalid bet type");
            }
        }else{
            String userInput = this.console.getStringInput("Do you want to roll or place further bets?");

        }
    }

    private Integer getBetMultiple(BetType chosenBetType) {
        ArrayList<BetType> passLineOddsComeBetOddsBuy = new ArrayList<>();

        if(this.currentPoint == 4 || this.currentPoint == 10){

        }


        /*
        //returns for Odds bets
        for(BetType oddsBet: this.oddsBetsList){
            if(chosenBetType.equals(oddsBet)){
                if(this.currentPoint == 5 || this.currentPoint == 9){
                    return 2;
                }else if(this.currentPoint == 6 || this.currentPoint == 8){
                    return 5;
                }
            }
        }
        for(BetType oddsBet: this.oddsBetsList){
            if(chosenBetType.equals(oddsBet)){
                if(this.currentPoint == 5 || this.currentPoint == 9){
                    return 2;
                }else if(this.currentPoint == 6 || this.currentPoint == 8){
                    return 5;
                }
            }
        }
        */
        return 1;
    }

    private Integer getMaxBet(BetType betType) {
        for(BetType oddsBet : oddsBetsList){
            if(betType.equals(oddsBet)){
                Integer output = this.currentBets.lowerEntry(betType).getValue() * 2;
                return output;
            }
        }
        return 1000;
    }

    private Boolean isBetAvailable(BetType betType) {
        if(getGamePhase()){ //if we are in the Come Out Phase
            for(BetType comeOutBet : comeOutBetsList){
                if(betType.equals(comeOutBet)){
                    return true;
                }
            }
            for(BetType pointPhaseBet: pointPhaseBetsList){
                if(betType.equals(pointPhaseBet)){
                    return false;
                }
            }
            for(BetType oddsBet : this.oddsBetsList){
                if(betType.equals(oddsBet)){
                    if(getMaxBet(betType) == 0){
                        return false;
                    }else{
                        return true;
                    }
                }
            }
        }else{ //if we are in Point Phase
            for(BetType pointPhaseBet: pointPhaseBetsList){
                if(betType.equals(pointPhaseBet)){
                    return true;
                }
            }
            for(BetType comeOutBet : comeOutBetsList){
                if(betType.equals(comeOutBet)){
                    return false;
                }
            }
            for(BetType oddsBet : this.oddsBetsList){
                if(betType.equals(oddsBet)){
                    if(getMaxBet(betType) == 0){
                        return false;
                    }else{
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private Boolean validateBetAmount(BetType chosenBetType, Integer chosenBetAmount) {
        Integer amount = this.console.getIntegerInput("How much would you like to bet?");
        Integer limit = getMaxBet(chosenBetType);

        if(amount <= player.getCurrentFunds() && amount <= limit){
            bet(chosenBetType, amount);
        }else{
            System.out.println("Insufficient funds!");
        }
    }

    public void resetGame() {

    }

    public void quitGame() {
        clearBets();
        this.playingGame = false;
    }

    public String getGameName() {
        return "Craps";
    }

    public void startGame(Player player) {
        setPlayer(player);
        setUpBetsMap();
        oddsBetsList.add(BetType.PASS_ODDS);
        oddsBetsList.add(BetType.DONT_PASS_ODDS);
        oddsBetsList.add(BetType.COME_ODDS_4);
        oddsBetsList.add(BetType.COME_ODDS_5);
        oddsBetsList.add(BetType.COME_ODDS_6);
        oddsBetsList.add(BetType.COME_ODDS_8);
        oddsBetsList.add(BetType.COME_ODDS_9);
        oddsBetsList.add(BetType.COME_ODDS_10);
        oddsBetsList.add(BetType.DONT_COME_ODDS_4);
        oddsBetsList.add(BetType.DONT_COME_ODDS_5);
        oddsBetsList.add(BetType.DONT_COME_ODDS_6);
        oddsBetsList.add(BetType.DONT_COME_ODDS_8);
        oddsBetsList.add(BetType.DONT_COME_ODDS_9);
        oddsBetsList.add(BetType.DONT_COME_ODDS_10);

        comeOutBetsList.add(BetType.PASS);
        comeOutBetsList.add(BetType.FIELD);
        comeOutBetsList.add(BetType.DONT_PASS);

        pointPhaseBetsList.add(BetType.COME);
        pointPhaseBetsList.add(BetType.DONT_COME);
        pointPhaseBetsList.add(BetType.BIG_6);
        pointPhaseBetsList.add(BetType.BIG_8);
        pointPhaseBetsList.add(BetType.PASS_ODDS);
        pointPhaseBetsList.add(BetType.DONT_PASS_ODDS);

        buyBetsList.add(BetType.BUY_4);
        buyBetsList.add(BetType.BUY_5);
        buyBetsList.add(BetType.BUY_6);
        buyBetsList.add(BetType.BUY_8);
        buyBetsList.add(BetType.BUY_9);
        buyBetsList.add(BetType.BUY_10);


        this.playingGame = true;

        System.out.println("Welcome to the Craps table!");
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
