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

    protected void printDiceRolls() {
        System.out.println("Die 1: " + this.diceRolls.get(0) + "\n" +
                "Die 2: " + this.diceRolls.get(1) + "\n" +
                "Total: " + diceTotal);
    }

    protected void setActiveBets() {
        //find and add affected bets to list
        ArrayList<BetType> output = new ArrayList<>();
        for(BetType betType: BetType.values()){
            if(this.currentBets.get(betType) > 0){
                output.add(betType);
            }
        }
        activeBets = output;
    }

    protected void checkIfBetsAreAffected() {
        for(BetType betType : this.activeBets){
            processBet(betType);
        }
    }

    protected void processBet(BetType betType) {
        if(betType.equals(BetType.PASS) || betType.equals(BetType.PASS_ODDS)){ //Pass Line Bet
            processPassBets(betType);
        }
        else if(betType.equals(BetType.DONT_PASS) || betType.equals(BetType.DONT_PASS_ODDS)){
            processDontPassBets(betType);
        }
        else if(betType.equals(BetType.COME)){ //Come bet
            processComeBets(betType);
        }
        else if(betType.equals(BetType.DONT_COME)){ //Dont Come bet
            processDontComeBets(betType);
        }
        else if(comeBetsList.contains(betType)) { //all Come point bets
            processComePointBets(betType);
        }
        else if(comeOddsBetsList.contains(betType)) { //all Come bet odds
            processComeOddsBets(betType);
        }
        else if(dontComeBetsList.contains(betType)){ //all Dont Come point bets
            processDontComePointBets(betType);
        }
        else if(dontComeOddsList.contains(betType)){ //all Dont Come point Odds
            processDontComeOddsBets(betType);
        }
        else if(placeBetsList.contains(betType)){
            processPlaceBets(betType);
        }
        else if(buyBetsList.contains(betType)){
            processBuyBets(betType);
        }
        else if(layBetsList.contains(betType)){
            processLayBets(betType);
        }
        else if(betType.equals(BetType.BIG_6)){
            processBigBets(betType, 6);
        }
        else if(betType.equals(BetType.BIG_8)){
            processBigBets(betType, 8);
        }
        else if(betType.equals(BetType.HARD_4)){
            processHardBets(betType, 4);
        }
        else if(betType.equals(BetType.HARD_6)){
            processHardBets(betType, 6);
        }
        else if(betType.equals(BetType.HARD_8)){
            processHardBets(betType, 8);
        }
        else if(betType.equals(BetType.HARD_10)){
            processHardBets(betType, 10);
        }
        else if(betType.equals(BetType.FIELD)){
            processSingleRollBets(betType, 2,3,4,9,10,11,12);
        }
        else if(betType.equals(BetType.ANY_CRAPS)){
            processSingleRollBets(betType, 2, 3, 12);
        }
        else if(betType.equals(BetType.CRAPS_2)){
            processSingleRollBets(betType, 2);
        }
        else if(betType.equals(BetType.CRAPS_3)){
            processSingleRollBets(betType, 3);
        }
        else if(betType.equals(BetType.CRAPS_12)){
            processSingleRollBets(betType, 12);
        }
        else if(betType.equals(BetType.ANY_7)){
            processSingleRollBets(betType, 7);
        }
        else if(betType.equals(BetType.ANY_11)){
            processSingleRollBets(betType, 11);
        }
    }

    protected void processSingleRollBets(BetType betType, Integer ... args) {
        ArrayList<Integer> targetRolls = new ArrayList<>(Arrays.asList(args));
        if (targetRolls.contains(this.diceTotal)) {
            betWins(betType);
        } else {
            betLoses(betType);
        }
    }

    protected void processHardBets(BetType betType, Integer hardRoll) {
        ArrayList<Integer> targetRoll = new ArrayList<>(Arrays.asList(hardRoll / 2, hardRoll / 2));
        if(this.diceRolls.equals(targetRoll)){
            betWins(betType);
        }else if(this.diceTotal == hardRoll){
            betLoses(betType);
        }
    }

    protected void processBigBets(BetType betType, int bigNumber) {
        if (this.diceTotal == bigNumber) {
            betWins(betType);
        } else if (this.diceTotal == 7) {
            betLoses(betType);
        }
    }

    protected void processLayBets(BetType betType) {
        Integer indexOfLosingPoint = layBetsList.indexOf(betType);
        Integer losingPoint = this.pointPhaseValues.get(indexOfLosingPoint);
        if(this.diceTotal == losingPoint){
            betLoses(betType);
        }else if(this.diceTotal == 7){
            betWins(betType);
        }
    }

    protected void processBuyBets(BetType betType) {
        Integer indexOfWinningPoint = buyBetsList.indexOf(betType);
        Integer winningPoint = this.pointPhaseValues.get(indexOfWinningPoint);
        if(this.diceTotal == winningPoint){
            betWins(betType);
        }else if(this.diceTotal == 7){
            betLoses(betType);
        }
    }

    protected void processPlaceBets(BetType betType) {
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

    protected void processDontComeOddsBets(BetType betType) {
        if(this.diceTotal.equals(calculateTargetPoint(betType, dontComeOddsList))){ //if bet loses
            betLoses(betType);
        }else if(this.diceTotal == 7){ //if bet wins
            betWins(betType);
        }
    }

    protected void processDontComePointBets(BetType betType) {
        if(this.diceTotal.equals(calculateTargetPoint(betType, dontComeBetsList))){ //if bet loses
            betLoses(betType);
        }else if(this.diceTotal == 7){ //if bet wins
            betWins(betType);
        }
    }

    protected void processComeOddsBets(BetType betType) {
        if(this.diceTotal.equals(calculateTargetPoint(betType, comeOddsBetsList))){ //if bet wins
            betWins(betType);
        }else if(this.diceTotal == 7){ //if bet loses
            betLoses(betType);
        }
    }

    protected void processComePointBets(BetType betType) {
        if(this.diceTotal.equals(calculateTargetPoint(betType, comeBetsList))){ //if bet wins
            betWins(betType);
        }else if(this.diceTotal == 7){ //if bet loses
            betLoses(betType);
        }
    }

    protected Integer calculateTargetPoint(BetType betType, ArrayList<BetType> betsList) {
        Integer indexOfWinningPoint = betsList.indexOf(betType);
        return this.pointPhaseValues.get(indexOfWinningPoint);
    }

    protected void processDontComeBets(BetType betType) {
        if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet loses
            betLoses(betType);
        }else if(this.diceTotal == 2 || this.diceTotal == 3){ //if bet wins
            betWins(betType);
        }else if(this.diceTotal == 12){
            //do nothing because it is considered a tie
        }else{ //move Dont Come bet to appropriate point bet
            transferBetToPoint(betType, this.dontComeBetsList);
        }
    }

    protected void processComeBets(BetType betType) {
        if(this.diceTotal == 7 || this.diceTotal == 11){ //if bet wins
            betWins(betType);
        }else if(this.diceTotal == 2 || this.diceTotal == 3 || this.diceTotal == 12){ //if bet loses
            betLoses(betType);
        }else{ //if neither, transfer come bet to appropriate point
            transferBetToPoint(betType, this.comeBetsList);
        }
    }

    protected void transferBetToPoint(BetType betType, ArrayList<BetType> targetBets) {
        Integer indexOfNewComeBet = pointPhaseValues.indexOf(this.diceTotal);
        BetType newComeBet = targetBets.get(indexOfNewComeBet);
        Integer amountBetOnCome = this.currentBets.get(betType);
        this.currentBets.replace(newComeBet, amountBetOnCome);
        clearBet(betType);
    }

    protected void processDontPassBets(BetType betType) {
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

    protected void processPassBets(BetType betType) {
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

    protected void betLoses(BetType betType) {
        clearBet(betType);
        System.out.println("You have lost your " + betType + " bet.");
    }

    protected void betWins(BetType betType) {
        payout(this.player, calculatePayoff(betType));
        clearBet(betType);
    }

    protected void clearBet(BetType betType) {
        this.currentBets.replace(betType, 0);
    }

    protected void assessGamePhase() {
        if(this.comeOutPhase){ //if you are in Come Out phase
            if(this.pointPhaseValues.contains(this.diceTotal)){
                this.comeOutPhase = false;
                this.currentPoint = this.diceTotal;
            }
        }else{ //if you are in the Point Phase
            if(this.diceTotal == 7 || this.diceTotal.equals(this.currentPoint)){
                this.comeOutPhase = true;
                this.currentPoint = 0;
            }
        }
    }

    public BetType retrieveBetEnum(String betType){
        for(BetType bet : BetType.values()){
            if(bet.getName().equalsIgnoreCase(betType)){
                return bet;
            }
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
        printPlayerBalance();
        if(getCurrentBet() == 0){ //if there are no bets placed, do the following
            getUserInputBetType();
        }else{ //if a bet has been placed
            printCurrentBets();
            rollOrBet();
        }
    }

    public void rollOrBet() {
        String userInput = this.console.getStringInput("Do you want to roll or place further bets?");
        checkForGameQuit(userInput);

        if(userInput.equalsIgnoreCase("roll")){
            this.rollTheDice();
        }else if(userInput.equalsIgnoreCase("bet")){
            getUserInputBetType();
        }else{
            clarifyInput();
        }
    }

    public void clarifyInput() {
        if(playingGame){//this is to prevent the msg from displaying if the user inputs "quit"
            System.out.println("Clarify, roll or bet?");
            rollOrBet();
        }
    }

    protected void printCurrentGamePhase() {
        if(this.comeOutPhase){
            System.out.println("Come Out roll");
        }else{
            System.out.println("The Point is ON:" + this.currentPoint);
        }
    }

    protected void getUserInputBetType() {
        String inputBetType = this.console.getStringInput("What type of bet would you like to make?");
        checkForGameQuit(inputBetType);
        BetType chosenBetType = retrieveBetEnum(inputBetType);
        validateBetType(chosenBetType);
    }

    public void getUserInputBetAmount(BetType chosenBetType) {
        Integer inputBetAmount = this.console.getIntegerInput("How much would you like to bet?\n" +
                "Bet must be a multiple of " + getBetMultiple(chosenBetType) + "\n" +
                "Minimum bet is 5.\n" +
                "Max total bet is " + getMaxBet(chosenBetType) + ".");
        calculateBetReqs(chosenBetType, inputBetAmount);
    }

    public void printPlayerBalance() {
        System.out.println("Balance: $" + this.player.getCurrentFunds());
    }

    protected void checkForGameQuit(String input) {
        if(input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")){
            quitGame();
        }
    }

    protected void printCurrentBets() {
        System.out.println("Currently placed bets:");
        for(BetType betType: BetType.values()){
            if(this.currentBets.get(betType) != 0){
                System.out.println(betType.toString() + " = $" + this.currentBets.get(betType).toString());
            }
        }
    }

    protected void validateBetType(BetType chosenBetType) {
        if(chosenBetType != null){
            if(isBetAvailable(chosenBetType)){
                getUserInputBetAmount(chosenBetType);
            }else{
                System.out.println("This bet is not available at this time.");
            }
        }else{
            if(playingGame){ //this prevents msg from displaying if the user inputs "quit"
                System.out.println("Invalid bet type");
            }
        }
    }

    protected Integer getBetMultiple(BetType chosenBetType) {
        if(this.currentPoint == 4 || this.currentPoint == 10){
            return get4Or10Ratios(chosenBetType);
        }else if(this.currentPoint == 5 || this.currentPoint == 9){
            return get5Or9Ratios(chosenBetType);
        }else if(this.currentPoint == 6|| this.currentPoint == 8){
            return get6Or8Ratios(chosenBetType);
        }
        return 1;
    }

    private Integer get6Or8Ratios(BetType chosenBetType) {
        if(passLineOddsComeBetOddsBuy.contains(chosenBetType)){
            return 5;
        }else if(dontPassOddsDontComeOddsLay.contains(chosenBetType)){
            return 6;
        }else if(placeBetsList.contains(chosenBetType)){
            return 6;
        }
        return 1;
    }

    protected Integer get5Or9Ratios(BetType chosenBetType) {
        if(passLineOddsComeBetOddsBuy.contains(chosenBetType)){
            return 2;
        }else if(dontPassOddsDontComeOddsLay.contains(chosenBetType)){
            return 3;
        }else if(placeBetsList.contains(chosenBetType)){
            return 5;
        }
        return 1;
    }

    protected Integer get4Or10Ratios(BetType chosenBetType) {
        if (dontPassOddsDontComeOddsLay.contains(chosenBetType)) {
            return 2;
        } else if (placeBetsList.contains(chosenBetType)) {
            return 5;
        }
        return 1;
    }

    protected Integer getMaxBet(BetType betType) {
        if(oddsBetsList.contains(betType)){
            return this.currentBets.lowerEntry(betType).getValue() * 3;
        }
        return 1000;
    }

    protected Boolean isBetAvailable(BetType betType) {
        if(oddsBetsList.contains(betType)){
            return getMaxBet(betType) != 0;
        }else if(comeOutBetsList.contains(betType)){
            return this.comeOutPhase;
        }else if(pointPhaseBetsList.contains(betType)){
            return !this.comeOutPhase;
        }
        return true;
    }

    protected void calculateBetReqs(BetType chosenBetType, Integer inputBetAmount) {
        Integer newTotalBet = inputBetAmount + this.currentBets.get(chosenBetType);

        Boolean isMultiple = newTotalBet % getBetMultiple(chosenBetType) == 0;
        Boolean isWithinLimits = newTotalBet <= getMaxBet(chosenBetType) && newTotalBet >= this.minimumBet;
        Boolean playerHasEnoughFunds = inputBetAmount <= player.getCurrentFunds();

        validateBetAmount(chosenBetType, inputBetAmount, isMultiple, isWithinLimits, playerHasEnoughFunds);
    }

    protected void validateBetAmount(BetType chosenBetType, Integer inputBetAmount, Boolean isMultiple, Boolean isWithinLimits, Boolean playerHasEnoughFunds) {
        if(playerHasEnoughFunds && isWithinLimits && isMultiple){
            bet(chosenBetType, inputBetAmount);
            this.player.subtractFromCurrentFunds(inputBetAmount);
        }else{
            System.out.println("Bet amount did not meet requirements.");
        }
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
        initializeBetArrayLists();
        initializeFields();

        System.out.println("Welcome to the Craps table!");
        while(this.playingGame){
            actionSelection();
        }
    }

    public void initializeFields() {
        this.minimumBet = 5;
        this.playingGame = true;
        this.comeOutPhase = true;
        this.currentPoint = 0;
    }

    private void initializeBetArrayLists() {
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
    }
}