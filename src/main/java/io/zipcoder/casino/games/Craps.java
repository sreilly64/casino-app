package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comeOutPhase;
    private Integer currentPoint;
    private TreeMap<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer diceTotal;
    private Integer betAmount;
    private Integer minimumBet;
    private Player player;
    private Boolean playingGame;
    private ArrayList<BetType> oddsBetsList;
    private ArrayList<BetType> comeOutBetsList;
    private ArrayList<BetType> pointPhaseBetsList;
    private ArrayList<BetType> buyBetsList;
    private ArrayList<BetType> passLineOddsComeBetOddsBuy;
    private ArrayList<BetType> dontPassOddsDontComeOddsLay;
    private ArrayList<BetType> placeBetsList;
    private ArrayList<BetType> activeBets;
    private ArrayList<Integer> pointPhaseValues;
    private ArrayList<BetType> comeOddsBetsList;
    private ArrayList<BetType> dontComeOddsList;
    private ArrayList<BetType> layBetsList;
    private ArrayList<BetType> comeBetsList;
    private ArrayList<BetType> oneToOneBetsList;
    private ArrayList<BetType> dontComeBetsList;
    public static String gameName = "Craps";


    public Craps() {

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
        return this.comeOutPhase;
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

    public void rollTheDice(){
        this.diceRolls = rollDice(2);
        this.diceTotal = this.diceRolls.get(0) + this.diceRolls.get(1);
        printDiceRolls();
        setActiveBets();
        checkIfBetsAreAffected();
        assessGamePhase();
    }

    private void printDiceRolls() {
        System.out.println("Die 1: " + this.diceRolls.get(0) + "\n" +
                               "Die 2: " + this.diceRolls.get(1) + "\n" +
                               "Total: " + diceTotal);
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

    private void checkIfBetsAreAffected() {
        for(BetType betType : this.activeBets){
            processBets(betType);
        }
    }

    private void processBets(BetType betType) {
        if(betType.equals(BetType.PASS) || betType.equals(BetType.PASS_ODDS)){ //Pass Line Bet
            if(this.comeOutPhase){ //if on Come Out phase
                if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet wins
                    betWins(betType);
                }else if(this.diceTotal == 2 || this.diceTotal == 3 || this.diceTotal == 12){ //if bet loses
                    betLoses(betType);
                }
            }else{ //if on Point Phase
                if(this.diceTotal.equals(this.currentPoint)){ //if bet wins
                    betWins(betType);
                }else if(this.diceTotal == 7){ //if bet loses
                    betLoses(betType);
                }
            }
        }
        else if(betType.equals(BetType.DONT_PASS) || betType.equals(BetType.DONT_PASS_ODDS)){
            if(this.comeOutPhase){ //if on Come Out phase
                if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet loses
                    betLoses(betType);
                }else if(this.diceTotal == 2 || this.diceTotal == 3){ //if bet wins
                    betWins(betType);
                }
            }else{ //if on Point Phase
                if(this.diceTotal.equals(this.currentPoint)){ //if bet loses
                    betLoses(betType);
                }else if(this.diceTotal == 7){ //if bet wins
                    betWins(betType);
                }
            }
        }
        else if(betType.equals(BetType.COME)){ //Come bet
                if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet wins
                    betWins(betType);
                }else if(this.diceTotal == 2 || this.diceTotal == 3 || this.diceTotal == 12){ //if bet loses
                    betLoses(betType);
                }else{ //if neither, transfer come bet to appropriate point
                    Integer indexOfNewComeBet = pointPhaseValues.indexOf(this.diceTotal);
                    BetType newComeBet = this.comeBetsList.get(indexOfNewComeBet);
                    Integer amountBetOnCome = this.currentBets.get(betType);
                    this.currentBets.replace(newComeBet, amountBetOnCome);
                    clearBet(betType);
                }
            }
            else if(betType.equals(BetType.DONT_COME)){ //Dont Come bet
                    if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet loses
                        betLoses(betType);
                    }else if(this.diceTotal == 2 || this.diceTotal == 3){ //if bet wins
                        betWins(betType);
                    }else if(this.diceTotal == 12){
                        //do nothing because it is considered a tie
                    }else{ //move Dont Come bet to appropriate point bet
                        Integer indexOfNewDontComeBet = pointPhaseValues.indexOf(this.diceTotal);
                        BetType newDontComeBet = this.dontComeBetsList.get(indexOfNewDontComeBet);
                        Integer amountBetOnDontCome = this.currentBets.get(betType);
                        this.currentBets.replace(newDontComeBet, amountBetOnDontCome);
                        clearBet(betType);
                    }
                }
                else if(comeBetsList.contains(betType)) { //all Come point bets
                        Integer indexOfWinningPoint = comeBetsList.indexOf(betType);
                        Integer winningPoint = this.pointPhaseValues.get(indexOfWinningPoint);
                        if(this.diceTotal == winningPoint){ //if bet wins
                            betWins(betType);
                        }else if(this.diceTotal == 7){ //if bet loses
                            betLoses(betType);
                        }
                    }
                    else if(comeOddsBetsList.contains(betType)) { //all Come bet odds
                            Integer indexOfWinningPoint = comeOddsBetsList.indexOf(betType);
                            Integer winningPoint = this.pointPhaseValues.get(indexOfWinningPoint);
                            if(this.diceTotal == winningPoint){ //if bet wins
                                betWins(betType);
                            }else if(this.diceTotal == 7){ //if bet loses
                                betLoses(betType);
                            }
                        }
                        else if(dontComeBetsList.contains(betType)){ //all Dont Come point bets
                                Integer indexOfLosingPoint = dontComeBetsList.indexOf(betType);
                                Integer losingPoint = this.pointPhaseValues.get(indexOfLosingPoint);
                                if(this.diceTotal == losingPoint){ //if bet loses
                                    betLoses(betType);
                                }else if(this.diceTotal == 7){ //if bet wins
                                    betWins(betType);
                                }
                            }
                            else if(dontComeOddsList.contains(betType)){ //all Dont Come point Odds
                                    Integer indexOfLosingPoint = dontComeOddsList.indexOf(betType);
                                    Integer losingPoint = this.pointPhaseValues.get(indexOfLosingPoint);
                                    if(this.diceTotal == losingPoint){ //if bet loses
                                        betLoses(betType);
                                    }else if(this.diceTotal == 7){ //if bet wins
                                        betWins(betType);
                                    }
                                }
                                else if(placeBetsList.contains(betType)){
                                        if(!this.comeOutPhase){ //only "on" during the Point phase
                                            Integer indexOfWinningPoint = placeBetsList.indexOf(betType);
                                            Integer winningPoint = this.pointPhaseValues.get(indexOfWinningPoint);
                                            if(this.diceTotal == winningPoint){
                                                betWins(betType);
                                            }else if(this.diceTotal == 7){
                                                betLoses(betType);
                                            }
                                        }
                                    }
                                    else if(buyBetsList.contains(betType)){
                                            Integer indexOfWinningPoint = buyBetsList.indexOf(betType);
                                            Integer winningPoint = this.pointPhaseValues.get(indexOfWinningPoint);
                                            if(this.diceTotal == winningPoint){
                                                betWins(betType);
                                            }else if(this.diceTotal == 7){
                                                betLoses(betType);
                                            }
                                        }
                                        else if(layBetsList.contains(betType)){
                                                Integer indexOfLosingPoint = layBetsList.indexOf(betType);
                                                Integer losingPoint = this.pointPhaseValues.get(indexOfLosingPoint);
                                                if(this.diceTotal == losingPoint){
                                                    betLoses(betType);
                                                }else if(this.diceTotal == 7){
                                                    betWins(betType);
                                                }
                                            }
                                            else if(betType.equals(BetType.BIG_6)){
                                                    if(this.diceTotal == 6){
                                                        betWins(betType);
                                                    }else if(this.diceTotal == 7){
                                                        betLoses(betType);
                                                    }
                                                }
                                                else if(betType.equals(BetType.BIG_8)){
                                                        if(this.diceTotal == 8){
                                                            betWins(betType);
                                                        }else if(this.diceTotal == 7){
                                                            betLoses(betType);
                                                        }
                                                    }
                                                    else if(betType.equals(BetType.HARD_4)){
                                                            ArrayList<Integer> targetRoll = new ArrayList<>(Arrays.asList(2,2));
                                                            if(this.diceRolls.equals(targetRoll)){
                                                                betWins(betType);
                                                            }else if(this.diceTotal == 4){
                                                                betLoses(betType);
                                                            }
                                                        }
                                                        else if(betType.equals(BetType.HARD_6)){
                                                                ArrayList<Integer> targetRoll = new ArrayList<>(Arrays.asList(3,3));
                                                                if(this.diceRolls.equals(targetRoll)){
                                                                    betWins(betType);
                                                                }else if(this.diceTotal == 6){
                                                                    betLoses(betType);
                                                                }
                                                            }
                                                            else if(betType.equals(BetType.HARD_8)){
                                                                    ArrayList<Integer> targetRoll = new ArrayList<>(Arrays.asList(4,4));
                                                                    if(this.diceRolls.equals(targetRoll)){
                                                                        betWins(betType);
                                                                    }else if(this.diceTotal == 8){
                                                                        betLoses(betType);
                                                                    }
                                                                }
                                                                else if(betType.equals(BetType.HARD_10)){
                                                                        ArrayList<Integer> targetRoll = new ArrayList<>(Arrays.asList(5,5));
                                                                        if(this.diceRolls.equals(targetRoll)){
                                                                            betWins(betType);
                                                                        }else if(this.diceTotal == 10){
                                                                            betLoses(betType);
                                                                        }
                                                                    }
                                                                    else if(betType.equals(BetType.FIELD)){
                                                                            ArrayList<Integer> targetRolls = new ArrayList<>(Arrays.asList(2,3,4,9,10,11,12));
                                                                            if(targetRolls.contains(this.diceTotal)){
                                                                                betWins(betType);
                                                                            }else{
                                                                                betLoses(betType);
                                                                            }
                                                                        }
                                                                        else if(betType.equals(BetType.ANY_CRAPS)){
                                                                                ArrayList<Integer> targetRolls = new ArrayList<>(Arrays.asList(2,3,12));
                                                                                if(targetRolls.contains(this.diceTotal)){
                                                                                    betWins(betType);
                                                                                }else{
                                                                                    betLoses(betType);
                                                                                }
                                                                            }
                                                                            else if(betType.equals(BetType.CRAPS_2)){
                                                                                    if(this.diceTotal == 2){
                                                                                        betWins(betType);
                                                                                    }else{
                                                                                        betLoses(betType);
                                                                                    }
                                                                                }
                                                                                else if(betType.equals(BetType.CRAPS_3)){
                                                                                        if(this.diceTotal == 3){
                                                                                            betWins(betType);
                                                                                        }else{
                                                                                            betLoses(betType);
                                                                                        }
                                                                                    }
                                                                                    else if(betType.equals(BetType.CRAPS_12)){
                                                                                            if(this.diceTotal == 12){
                                                                                                betWins(betType);
                                                                                            }else{
                                                                                                betLoses(betType);
                                                                                            }
                                                                                        }
                                                                                        else if(betType.equals(BetType.ANY_7)){
                                                                                                if(this.diceTotal == 7){
                                                                                                    betWins(betType);
                                                                                                }else{
                                                                                                    betLoses(betType);
                                                                                                }
                                                                                            }
                                                                                            else if(betType.equals(BetType.ANY_11)){
                                                                                                    if(this.diceTotal == 11){
                                                                                                        betWins(betType);
                                                                                                    }else{
                                                                                                        betLoses(betType);
                                                                                                    }
                                                                                                }
    }

    public Integer calculatePayoff(BetType betType){
        Integer payoffAmount = 0;
        if(oneToOneBetsList.contains(betType)){ //all 1:1 bets
            payoffAmount = this.currentBets.get(betType) * 2;
        }else if(betType.equals(BetType.FIELD)){
            if(this.diceTotal == 2 || this.diceTotal == 12){
                payoffAmount = this.currentBets.get(betType) * 3;
            }else{
                payoffAmount = this.currentBets.get(betType) * 2;
            }
        }else if(betType.equals(BetType.ANY_CRAPS)){
            payoffAmount = this.currentBets.get(betType) * 8;
        }else if(betType.equals(BetType.CRAPS_2) || betType.equals(BetType.CRAPS_12)){
            payoffAmount = this.currentBets.get(betType) * 31;
        }else if(betType.equals(BetType.CRAPS_3) || betType.equals(BetType.ANY_11)){
            payoffAmount = this.currentBets.get(betType) * 16;
        }else if(betType.equals(BetType.ANY_7)){
            payoffAmount = this.currentBets.get(betType) * 5;
        }else if(betType.equals(BetType.PASS_ODDS)){
            if(this.diceTotal == 4 || this.diceTotal == 10){
                payoffAmount = this.currentBets.get(betType) * 3;
            }else if(this.diceTotal == 5 || this.diceTotal == 9){
                payoffAmount = this.currentBets.get(betType) * 5 / 2;
            }else if(this.diceTotal == 6 || this.diceTotal == 8){
                payoffAmount = this.currentBets.get(betType) * 11 / 5;
            }
        }else if(betType.equals(BetType.COME_ODDS_4) || betType.equals(BetType.COME_ODDS_10)){
            payoffAmount = this.currentBets.get(betType) * 3;
        }else if(betType.equals(BetType.COME_ODDS_5) || betType.equals(BetType.COME_ODDS_9)){
            payoffAmount = this.currentBets.get(betType) * 5 / 2;
        }else if(betType.equals(BetType.COME_ODDS_6) || betType.equals(BetType.COME_ODDS_8)){
            payoffAmount = this.currentBets.get(betType) * 11 / 5;
        }else if(betType.equals(BetType.DONT_PASS_ODDS)){
            if(this.currentPoint == 4 || this.currentPoint == 10){
                payoffAmount = this.currentBets.get(betType) * 3 / 2;
            }else if(this.currentPoint == 5 || this.currentPoint == 9){
                payoffAmount = this.currentBets.get(betType) * 5 / 3 ;
            }else if(this.currentPoint == 6 || this.currentPoint == 8){
                payoffAmount = this.currentBets.get(betType) * 11 / 6;
            }
        }else if(betType.equals(BetType.DONT_COME_ODDS_4) || betType.equals(BetType.DONT_COME_ODDS_10)){
            payoffAmount = this.currentBets.get(betType) * 3 / 2;
        }else if(betType.equals(BetType.DONT_COME_ODDS_5) || betType.equals(BetType.DONT_COME_ODDS_9)){
            payoffAmount = this.currentBets.get(betType) * 5 / 3 ;
        }else if(betType.equals(BetType.DONT_COME_ODDS_6) || betType.equals(BetType.DONT_COME_ODDS_8)){
            payoffAmount = this.currentBets.get(betType) * 11 / 6;
        }else if(betType.equals(BetType.PLACE_WIN_6) || betType.equals(BetType.PLACE_WIN_8)){
            payoffAmount = this.currentBets.get(betType) * 13 / 6;
        }else if(betType.equals(BetType.PLACE_WIN_5) || betType.equals(BetType.PLACE_WIN_9)){
            payoffAmount = this.currentBets.get(betType) * 12 / 5;
        }else if(betType.equals(BetType.PLACE_WIN_4) || betType.equals(BetType.PLACE_WIN_10)){
            payoffAmount = this.currentBets.get(betType) * 14 / 5;
        }else if(betType.equals(BetType.BUY_6) || betType.equals(BetType.BUY_8)){
            payoffAmount = this.currentBets.get(betType) * 11 / 5;
        }else if(betType.equals(BetType.BUY_5) || betType.equals(BetType.BUY_9)){
            payoffAmount = this.currentBets.get(betType) * 5 / 2;
        }else if(betType.equals(BetType.BUY_4) || betType.equals(BetType.BUY_10)){
            payoffAmount = this.currentBets.get(betType) * 3;
        }else if(betType.equals(BetType.LAY_6) || betType.equals(BetType.LAY_8)){
            payoffAmount = this.currentBets.get(betType) * 11 / 6;
        }else if(betType.equals(BetType.LAY_5) || betType.equals(BetType.LAY_9)){
            payoffAmount = this.currentBets.get(betType) * 5 / 3;
        }else if(betType.equals(BetType.LAY_4) || betType.equals(BetType.LAY_10)){
            payoffAmount = this.currentBets.get(betType) * 3 / 2;
        }else if(betType.equals(BetType.HARD_4) || betType.equals(BetType.HARD_10)){
            payoffAmount = this.currentBets.get(betType) * 8;
        }else if(betType.equals(BetType.HARD_6) || betType.equals(BetType.HARD_8)){
            payoffAmount = this.currentBets.get(betType) * 10;
        }

        System.out.println("You've won $" + payoffAmount + " on your " + betType + " bet!");
        return payoffAmount;
    }

    private void betLoses(BetType betType) {
        clearBet(betType);
        System.out.println("You have lost your " + betType + " bet.");
    }

    private void betWins(BetType betType) {
        payout(this.player, calculatePayoff(betType));
        clearBet(betType);
    }

    private void clearBet(BetType betType) {
        this.currentBets.replace(betType, 0);
    }

    private void assessGamePhase() {
        if(getGamePhase()){ //if you are in Come Out phase
            for(Integer possiblePoint : this.pointPhaseValues){
                if (this.diceTotal.equals(possiblePoint)) {
                    this.comeOutPhase = false;
                    this.currentPoint = this.diceTotal;
                    break;
                }
            }
        }else{ //if you are in the Point Phase
            if(this.diceTotal == 7 || this.diceTotal.equals(this.currentPoint)){
                this.comeOutPhase = true;
                this.currentPoint = 0;
            }
        }
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
        for(BetType betType : BetType.values()){
            this.currentBets.replace(betType, 0);
        }
    }

    public void payout(Player player, Integer amount) {
        player.addToCurrentFunds(amount);
    }

    public void actionSelection(){
        printCurrentGamePhase();
        if(getCurrentBet() == 0){ //if there are no bets placed, do the following
            gatherBetInformation();

        }else{ //if a bet has been placed
            printCurrentBets();
            String userInput = this.console.getStringInput("Do you want to roll or place further bets?");
            checkForGameQuit(userInput);
            if(userInput.equalsIgnoreCase("roll")){
                this.rollTheDice();
            }else if(userInput.equalsIgnoreCase("bet")){
                gatherBetInformation();
            }else{
                if(playingGame){//this is to prevent the msg from displaying if the user inputs "quit"
                    System.out.println("Clarify, roll or bet?");
                }
            }
        }
    }

    private void printCurrentGamePhase() {
        if(this.comeOutPhase){
            System.out.println("Come Out roll");
        }else{
            System.out.println("The Point is ON:" + this.currentPoint);
        }
    }

    private void gatherBetInformation() {
        System.out.println("Balance: $" + this.player.getCurrentFunds());
        String inputBetType = this.console.getStringInput("What type of bet would you like to make?");
        checkForGameQuit(inputBetType);
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

    private void checkForGameQuit(String input) {
        if(input.equalsIgnoreCase("quit")){
            quitGame();
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
            if(playingGame){ //this prevents msg from displaying if the user inputs "quit"
                System.out.println("Invalid bet type");
            }
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
            if(betType.equals(oddsBet)){ //if betting Odds, can only bet 3x the original bet
                return this.currentBets.lowerEntry(betType).getValue() * 3;
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
                    return getMaxBet(betType) != 0;
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
                    return getMaxBet(betType) != 0;
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

        //if chosenBetAMount is less than balance, less than limit, greater than minimum bet, and is a correct multiple, return true
        return chosenBetAmount <= player.getCurrentFunds() && totalBet <= limit && totalBet >= this.minimumBet && isMultiple;
    }

    public void resetGame() {

    }

    public void quitGame() {
        clearBets();
        System.out.println("Thanks for playing!");
        this.playingGame = false;
    }

    public String getGameName() {
        return "Craps";
    }

    public void startGame(Player player) {
        setPlayer(player);
        setUpBetsMap();
        minimumBet = 5;
        activeBets = new ArrayList<>();

        comeOutBetsList = new ArrayList<>();
        comeOutBetsList.add(BetType.PASS);
        comeOutBetsList.add(BetType.FIELD);
        comeOutBetsList.add(BetType.DONT_PASS);

        placeBetsList = new ArrayList<>();
        placeBetsList.add(BetType.PLACE_WIN_4);
        placeBetsList.add(BetType.PLACE_WIN_5);
        placeBetsList.add(BetType.PLACE_WIN_6);
        placeBetsList.add(BetType.PLACE_WIN_8);
        placeBetsList.add(BetType.PLACE_WIN_9);
        placeBetsList.add(BetType.PLACE_WIN_10);

        pointPhaseBetsList = new ArrayList<>();
        pointPhaseBetsList.add(BetType.COME);
        pointPhaseBetsList.add(BetType.DONT_COME);
        pointPhaseBetsList.add(BetType.PASS_ODDS);
        pointPhaseBetsList.add(BetType.DONT_PASS_ODDS);

        comeBetsList = new ArrayList<>();
        comeBetsList.add(BetType.COME_4);
        comeBetsList.add(BetType.COME_5);
        comeBetsList.add(BetType.COME_6);
        comeBetsList.add(BetType.COME_8);
        comeBetsList.add(BetType.COME_9);
        comeBetsList.add(BetType.COME_10);

        comeOddsBetsList = new ArrayList<>();
        comeOddsBetsList.add(BetType.COME_ODDS_4);
        comeOddsBetsList.add(BetType.COME_ODDS_5);
        comeOddsBetsList.add(BetType.COME_ODDS_6);
        comeOddsBetsList.add(BetType.COME_ODDS_8);
        comeOddsBetsList.add(BetType.COME_ODDS_9);
        comeOddsBetsList.add(BetType.COME_ODDS_10);

        dontComeBetsList = new ArrayList<>();
        dontComeBetsList.add(BetType.DONT_COME_4);
        dontComeBetsList.add(BetType.DONT_COME_5);
        dontComeBetsList.add(BetType.DONT_COME_6);
        dontComeBetsList.add(BetType.DONT_COME_8);
        dontComeBetsList.add(BetType.DONT_COME_9);
        dontComeBetsList.add(BetType.DONT_COME_10);

        dontComeOddsList = new ArrayList<>();
        dontComeOddsList.add(BetType.DONT_COME_ODDS_4);
        dontComeOddsList.add(BetType.DONT_COME_ODDS_5);
        dontComeOddsList.add(BetType.DONT_COME_ODDS_6);
        dontComeOddsList.add(BetType.DONT_COME_ODDS_8);
        dontComeOddsList.add(BetType.DONT_COME_ODDS_9);
        dontComeOddsList.add(BetType.DONT_COME_ODDS_10);

        buyBetsList = new ArrayList<>();
        buyBetsList.add(BetType.BUY_4);
        buyBetsList.add(BetType.BUY_5);
        buyBetsList.add(BetType.BUY_6);
        buyBetsList.add(BetType.BUY_8);
        buyBetsList.add(BetType.BUY_9);
        buyBetsList.add(BetType.BUY_10);

        layBetsList = new ArrayList<>();
        layBetsList.add(BetType.LAY_4);
        layBetsList.add(BetType.LAY_5);
        layBetsList.add(BetType.LAY_6);
        layBetsList.add(BetType.LAY_8);
        layBetsList.add(BetType.LAY_9);
        layBetsList.add(BetType.LAY_10);

        oddsBetsList = new ArrayList<>();
        oddsBetsList.add(BetType.PASS_ODDS);
        oddsBetsList.add(BetType.DONT_PASS_ODDS);
        oddsBetsList.addAll(comeOddsBetsList);
        oddsBetsList.addAll(dontComeOddsList);

        passLineOddsComeBetOddsBuy = new ArrayList<>();
        passLineOddsComeBetOddsBuy.add(BetType.PASS_ODDS);
        passLineOddsComeBetOddsBuy.addAll(comeOddsBetsList);
        passLineOddsComeBetOddsBuy.addAll(buyBetsList);

        dontPassOddsDontComeOddsLay = new ArrayList<>();
        dontPassOddsDontComeOddsLay.addAll(dontComeOddsList);
        dontPassOddsDontComeOddsLay.addAll(layBetsList);

        oneToOneBetsList = new ArrayList<>();
        oneToOneBetsList.add(BetType.PASS);
        oneToOneBetsList.add(BetType.COME);
        oneToOneBetsList.add(BetType.DONT_PASS);
        oneToOneBetsList.add(BetType.DONT_COME);
        oneToOneBetsList.add(BetType.BIG_6);
        oneToOneBetsList.add(BetType.BIG_8);
        oneToOneBetsList.addAll(comeBetsList);
        oneToOneBetsList.addAll(dontComeBetsList);

        pointPhaseValues = new ArrayList<>();
        pointPhaseValues.add(4);
        pointPhaseValues.add(5);
        pointPhaseValues.add(6);
        pointPhaseValues.add(8);
        pointPhaseValues.add(9);
        pointPhaseValues.add(10);

        this.playingGame = true;
        this.comeOutPhase = true;
        this.currentPoint = 0;

        System.out.println("Welcome to the Craps table!");
        while(this.playingGame){
            actionSelection();
        }
    }
}