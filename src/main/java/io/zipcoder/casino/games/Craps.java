package io.zipcoder.casino.games;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.TreeMap;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comOutPhase;
    private Integer currentPoint;
    private TreeMap<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer betAmount;
    private Integer minimumBet = 5;
    private Player player;
    private CrapsApp ui;
    private Boolean playingGame;
    private ArrayList<BetType> oddsBetsList = new ArrayList<>();
    private ArrayList<BetType> comeOutBetsList = new ArrayList<>();
    private ArrayList<BetType> pointPhaseBetsList = new ArrayList<>();
    private ArrayList<BetType> buyBetsList = new ArrayList<>();
    private ArrayList<BetType> passLineOddsComeBetOddsBuy = new ArrayList<>();
    private ArrayList<BetType> dontPassOddsDontComeOddsLay = new ArrayList<>();
    private ArrayList<BetType> placeBetsList = new ArrayList<>();
    private ArrayList<BetType> activeBets = new ArrayList<>();


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
        printDiceRolls();
        setActiveBets();
        checkBetWinConditions();
        checkBetLoseConditions();
        assessGamePhase();
        checkIfWinOrLose();
    }

    private void printDiceRolls() {
        Integer sum = this.diceRolls.get(0) + this.diceRolls.get(1);
        System.out.println("Die 1: " + this.diceRolls.get(0) + "\n" +
                "Die 2: " + this.diceRolls.get(1) + "\n" +
                "Total: " + sum);
    }

    private void setActiveBets() {
        //find and add affected bets to list
        ArrayList<BetType> output = new ArrayList<>();
        for(BetType betType: BetType.values()){
            if(this.currentBets.get(betType) > 0){
                output.add(betType);
            }
        }
        activeBets = output;
    }

    private void checkBetWinConditions() {

    }

    private void checkBetLoseConditions() {

    }

    private void assessGamePhase() {

    }

    private void checkIfWinOrLose() {

        for(BetType betType : activeBets){

        }

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
        Integer currentBetOfThisType = this.currentBets.get(betType);
        this.currentBets.replace(betType, amount + currentBetOfThisType);
    }

    public void clearBets() {

    }

    public void payout(Player player, Integer amount) {

    }

    public void actionSelection(){
        if(getCurrentBet() == 0){ //if there are no bets placed, do the following
            gatherBetInformation();

        }else{ //if a bet has been placed
            printCurrentBets();
            String userInput = this.console.getStringInput("Do you want to roll or place further bets?");
            if(userInput.equalsIgnoreCase("roll")){
                this.setLastDiceRoll();
            }else if(userInput.equalsIgnoreCase("bet")){
                gatherBetInformation();
            }else{
                System.out.println("Clarify, roll or bet?");
            }
        }
    }

    private void gatherBetInformation() {
        System.out.println("Balance: $" + this.player.getCurrentFunds());
        String inputBetType = this.console.getStringInput("What type of bet would you like to make?");
        BetType chosenBetType = selectBetType(inputBetType);

        if(isValidBetType(chosenBetType)){
            Integer betAmount = null;
            while(betAmount == null){
                Integer inputBetAmount = this.console.getIntegerInput("How much would you like to bet?\n" +
                        "Bet must be a multiple of " + getBetMultiple(chosenBetType) + "\n" +
                        "Minimum bet is 5.\n" +
                        "Max total bet is " + getMaxBet(chosenBetType) + ".");
                if(isValidBetAmount(chosenBetType, inputBetAmount)){
                    betAmount = inputBetAmount;
                    //place bet
                    bet(chosenBetType, betAmount);
                    this.player.subtractFromCurrentFunds(betAmount);
                }else{
                    System.out.println("Bet amount did not meet requirements.");
                }
            }
        }
    }

    private void printCurrentBets() {
        System.out.println("Balance: $" + this.player.getCurrentFunds());
        System.out.println("Currently placed bets:");
        for(BetType betType: BetType.values()){
            if(this.currentBets.get(betType) != 0){
                System.out.println(betType.toString() + " = $" + this.currentBets.get(betType).toString());
            }
        }
    }

    private boolean isValidBetType(BetType chosenBetType) {
        if(chosenBetType != null){
            if(isBetAvailable(chosenBetType)){
                return true;
            }else{
                System.out.println("This bet is not available at this time.");
                return false;
            }
        }else{
            System.out.println("Invalid bet type");
            return false;
        }
    }

    private Integer getBetMultiple(BetType chosenBetType) {
        //check these arrays
        //ArrayList<BetType> passLineOddsComeBetOddsBuy = new ArrayList<>();
        //ArrayList<BetType> dontPassOddsDontComeOddsLay = new ArrayList<>();
        //ArrayList<BetType> placeBetsList = new ArrayList<>();

        if(this.currentPoint == 4 || this.currentPoint == 10){
            if(dontPassOddsDontComeOddsLay.contains(chosenBetType)){
                return 2;
            }else if(placeBetsList.contains(chosenBetType)){
                return 5;
            }
        }else if(this.currentPoint == 5 || this.currentPoint == 9){
            if(passLineOddsComeBetOddsBuy.contains(chosenBetType)){
                return 2;
            }else if(dontPassOddsDontComeOddsLay.contains(chosenBetType)){
                return 3;
            }else if(placeBetsList.contains(chosenBetType)){
                return 5;
            }
        }else if(this.currentPoint == 6|| this.currentPoint == 8){
            if(passLineOddsComeBetOddsBuy.contains(chosenBetType)){
                return 5;
            }else if(dontPassOddsDontComeOddsLay.contains(chosenBetType)){
                return 6;
            }else if(placeBetsList.contains(chosenBetType)){
                return 6;
            }
        }

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

    private Boolean isValidBetAmount(BetType chosenBetType, Integer chosenBetAmount) {
        Integer limit = getMaxBet(chosenBetType);
        Integer currentBetOfThisType = this.currentBets.get(chosenBetType);
        Integer totalBet = currentBetOfThisType + chosenBetAmount;
        Boolean isMultiple = totalBet % getBetMultiple(chosenBetType) == 0;

        if(chosenBetAmount <= player.getCurrentFunds() && totalBet <= limit && totalBet >= this.minimumBet && isMultiple){
            return true;
        }else{
            return false;
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

        passLineOddsComeBetOddsBuy.add(BetType.PASS_ODDS);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_4);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_5);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_6);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_8);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_9);
        passLineOddsComeBetOddsBuy.add(BetType.COME_ODDS_10);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_4);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_5);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_6);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_8);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_9);
        passLineOddsComeBetOddsBuy.add(BetType.BUY_10);

        dontPassOddsDontComeOddsLay.add(BetType.DONT_PASS_ODDS);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_4);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_5);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_6);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_8);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_9);
        dontPassOddsDontComeOddsLay.add(BetType.DONT_COME_ODDS_10);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_4);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_5);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_6);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_8);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_9);
        dontPassOddsDontComeOddsLay.add(BetType.LAY_10);

        placeBetsList.add(BetType.PLACE_WIN_4);
        placeBetsList.add(BetType.PLACE_WIN_5);
        placeBetsList.add(BetType.PLACE_WIN_6);
        placeBetsList.add(BetType.PLACE_WIN_8);
        placeBetsList.add(BetType.PLACE_WIN_9);
        placeBetsList.add(BetType.PLACE_WIN_10);
        placeBetsList.add(BetType.PLACE_LOSE_4);
        placeBetsList.add(BetType.PLACE_LOSE_5);
        placeBetsList.add(BetType.PLACE_LOSE_6);
        placeBetsList.add(BetType.PLACE_LOSE_8);
        placeBetsList.add(BetType.PLACE_LOSE_9);
        placeBetsList.add(BetType.PLACE_LOSE_10);

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
