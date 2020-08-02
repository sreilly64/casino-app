package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class Craps extends DiceGame implements GamblingGame{

    private Boolean comeOutPhase;
    private Integer currentPoint;
    private TreeMap<BetType, Integer> currentBets;
    private ArrayList<Integer> diceRolls;
    private Integer diceTotal;
    private Integer minimumBet;
    private Player player;
    private Boolean playingGame;
    private ArrayList<BetType> oddsBetsList;
    private ArrayList<BetType> comeOutBetsList;
    private ArrayList<BetType> pointPhaseBetsList;
    private ArrayList<BetType> buyBetsList;
    private ArrayList<BetType> dontPassOddsDontComeOddsLay;
    private ArrayList<BetType> placeBetsList;
    private ArrayList<BetType> activeBets;
    private ArrayList<Integer> pointPhaseValues;
    private ArrayList<BetType> comeOddsBetsList;
    private ArrayList<BetType> dontComeOddsList;
    private ArrayList<BetType> layBetsList;
    protected ArrayList<BetType> comeBetsList;
    private ArrayList<BetType> oneToOneBetsList;
    private ArrayList<BetType> dontComeBetsList;
    private ArrayList<BetType> betsWithMultipleOfSix;
    private ArrayList<BetType> betsWithMultipleOfFive;
    private ArrayList<BetType> betsWithMultipleOfThree;
    private ArrayList<BetType> betsWithMultipleOfTwo;
    private HashMap<BetType, Runnable> betProcessingMap;
    private HashMap<BetType, Double> payoffMap;
    public static String gameName = "Craps";


    public Craps() {

    }

    public void initializeBetsMap() {
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

    public void setGamePhase(Boolean input){
        this.comeOutPhase = input;
    }

    public Integer getCurrentPoint(){
        return this.currentPoint;
    }

    public void setCurrentPoint(Integer newPoint){
        this.currentPoint = newPoint;
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

    public void setDiceRolls(ArrayList<Integer> diceRolls) {
        this.diceRolls = diceRolls;
    }

    public Integer getDiceTotal() {
        return diceTotal;
    }

    public void setDiceTotal(Integer diceTotal) {
        this.diceTotal = diceTotal;
    }

    public Boolean getPlayingGame() {
        return playingGame;
    }

    public Integer getMinimumBet() {
        return minimumBet;
    }

    public HashMap<BetType, Runnable> getBetProcessingMap() {
        return betProcessingMap;
    }

    public HashMap<BetType, Double> getPayoffMap() {
        return payoffMap;
    }

    public void rollTheDice(){
        this.diceRolls = rollDice(2);
        this.diceTotal = this.diceRolls.get(0) + this.diceRolls.get(1);
        printDiceRolls();
    }

    private void printDiceRolls() {
        String output = "Die 1: " + this.diceRolls.get(0) + "\n" +
                "Die 2: " + this.diceRolls.get(1) + "\n" +
                "Total: " + diceTotal;
        System.out.println(output);
    }

    public ArrayList<BetType> getActiveBets(){
        return this.activeBets;
    }

    public void setActiveBets() {
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
            betProcessingMap.get(betType).run();
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
        if(this.diceTotal == calculateTargetPoint(betType, layBetsList)){
            betLoses(betType);
        }else if(this.diceTotal == 7){
            betWins(betType);
        }
    }

    protected void processBuyBets(BetType betType) {
        if(!this.comeOutPhase){ //only "on" during the Point phase
            if(this.diceTotal == calculateTargetPoint(betType, buyBetsList)){
                betWins(betType);
            }else if(this.diceTotal == 7){
                betLoses(betType);
            }
        }
    }

    protected void processPlaceBets(BetType betType) {
        if(!this.comeOutPhase){ //only "on" during the Point phase
            if(this.diceTotal == calculateTargetPoint(betType, placeBetsList)){
                betWins(betType);
            }else if(this.diceTotal == 7){
                betLoses(betType);
            }
        }
    }

    protected void processDontComeOddsBets(BetType betType) {
        if(this.diceTotal == calculateTargetPoint(betType, dontComeOddsList)){ //if bet loses
            betLoses(betType);
        }else if(this.diceTotal == 7){ //if bet wins
            betWins(betType);
        }
    }

    protected void processDontComePointBets(BetType betType) {
        if(this.diceTotal == calculateTargetPoint(betType, dontComeBetsList)){ //if bet loses
            betLoses(betType);
        }else if(this.diceTotal == 7){ //if bet wins
            betWins(betType);
        }
    }

    protected void processComeOddsBets(BetType betType) {
        if(this.diceTotal == calculateTargetPoint(betType, comeOddsBetsList)){ //if bet wins
            betWins(betType);
        }else if(this.diceTotal == 7){ //if bet loses
            betLoses(betType);
        }
    }

    protected void processComePointBets(BetType betType) {
        if(this.diceTotal == calculateTargetPoint(betType, comeBetsList)){ //if bet wins
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
        Double payoffAmount = 0d;
        if(payoffMap.containsKey(betType)){
            payoffAmount = this.currentBets.get(betType) * payoffMap.get(betType);
        } else if(betType.equals(BetType.FIELD)){
            payoffAmount = getFieldBetPayoff();
        } else if(betType.equals(BetType.PASS_ODDS)){
            payoffAmount = getPassOddsPayout();
        } else if(betType.equals(BetType.DONT_PASS_ODDS)){
            payoffAmount = getDontPassOddsPayout();
        }

        Integer output = (int) Math.round(payoffAmount);
        System.out.println("You've won $" + output + " on your " + betType + " bet!");
        return output;
    }

    public Double getDontPassOddsPayout() {
        Double payoffAmount = 0d;
        if(this.currentPoint == 4 || this.currentPoint == 10){
            payoffAmount = this.currentBets.get(BetType.DONT_PASS_ODDS) * 3d / 2d;
        }else if(this.currentPoint == 5 || this.currentPoint == 9){
            payoffAmount = this.currentBets.get(BetType.DONT_PASS_ODDS) * 5d / 3d;
        }else if(this.currentPoint == 6 || this.currentPoint == 8){
            payoffAmount = this.currentBets.get(BetType.DONT_PASS_ODDS) * 11d / 6d;
        }
        return payoffAmount;
    }

    public Double getPassOddsPayout() {
        Double payoffAmount = 0d;
        if(this.diceTotal == 4 || this.diceTotal == 10){
            payoffAmount = this.currentBets.get(BetType.PASS_ODDS) * 3d;
        }else if(this.diceTotal == 5 || this.diceTotal == 9){
            payoffAmount = this.currentBets.get(BetType.PASS_ODDS) * 5d / 2d;
        }else if(this.diceTotal == 6 || this.diceTotal == 8){
            payoffAmount = this.currentBets.get(BetType.PASS_ODDS) * 11d / 5d;
        }
        return payoffAmount;
    }

    public Double getFieldBetPayoff() {
        Double payoffAmount;
        if(this.diceTotal == 2 || this.diceTotal == 12){
            payoffAmount = this.currentBets.get(BetType.FIELD) * 3d;
        }else{
            payoffAmount = this.currentBets.get(BetType.FIELD) * 2d;
        }
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

    private void rollOrBet() {
        String userInput = this.console.getStringInput("Do you want to roll or place further bets?");
        checkForGameQuit(userInput);
        if(playingGame){ //this is to prevent the msg from displaying if the user inputs "quit"
            processBetOrRoll(userInput);
        }
    }

    public void processBetOrRoll(String userInput) {
        if(userInput.equalsIgnoreCase("roll")){
            setActiveBets();
            rollTheDice();
            checkIfBetsAreAffected();
            assessGamePhase();
        }else if(userInput.equalsIgnoreCase("bet")){
            getUserInputBetType();
        }else{
            System.out.println("Clarify, roll or bet?");
            rollOrBet();
        }
    }

    protected void printCurrentGamePhase() {
        if(this.comeOutPhase){
            System.out.println("Come Out roll");
        }else{
            System.out.println("The Point is ON: " + this.currentPoint);
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
        if(betsWithMultipleOfSix.contains(chosenBetType)){
            return 6;
        }else if(betsWithMultipleOfFive.contains(chosenBetType)){
            return 5;
        }else if(betsWithMultipleOfThree.contains(chosenBetType)){
            return 3;
        }else if(betsWithMultipleOfTwo.contains(chosenBetType)){
            return 2;
        }else if(oddsBetsList.contains(chosenBetType)){
            return getMultipleForOdds(chosenBetType);
        }else{
            return 1;
        }
    }

    public Integer getMultipleForOdds(BetType chosenBetType) {
        if(this.currentPoint == 4 || this.currentPoint == 10){
            if(chosenBetType.equals(BetType.PASS_ODDS) || comeOddsBetsList.contains(chosenBetType)){
                return 1;
            }else{
                return 2;
            }
        }else if(this.currentPoint == 5 || this.currentPoint == 9){
            if(chosenBetType.equals(BetType.PASS_ODDS) || comeOddsBetsList.contains(chosenBetType)){
                return 2;
            }else{
                return 3;
            }
        }else if(this.currentPoint == 6|| this.currentPoint == 8) {
            if(chosenBetType.equals(BetType.PASS_ODDS) || comeOddsBetsList.contains(chosenBetType)){
                return 5;
            }else{
                return 6;
            }
        }else{
            return 1;
        }
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
        initializeBetsMap();
        initializeBetArrayLists();
        initializeFields();
        initializeBetProcessingMap();
        initializePayoffMap();

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

    public void initializeBetArrayLists() {
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

        betsWithMultipleOfSix = new ArrayList<>();
        betsWithMultipleOfSix.add(BetType.PLACE_WIN_6);
        betsWithMultipleOfSix.add(BetType.PLACE_WIN_8);
        betsWithMultipleOfSix.add(BetType.LAY_6);
        betsWithMultipleOfSix.add(BetType.LAY_8);

        betsWithMultipleOfFive = new ArrayList<>();
        betsWithMultipleOfFive.add(BetType.PLACE_WIN_5);
        betsWithMultipleOfFive.add(BetType.PLACE_WIN_9);
        betsWithMultipleOfFive.add(BetType.PLACE_WIN_10);
        betsWithMultipleOfFive.add(BetType.PLACE_WIN_4);
        betsWithMultipleOfFive.add(BetType.BUY_6);
        betsWithMultipleOfFive.add(BetType.BUY_8);

        betsWithMultipleOfThree = new ArrayList<>();
        betsWithMultipleOfThree.add(BetType.LAY_5);
        betsWithMultipleOfThree.add(BetType.LAY_9);

        betsWithMultipleOfTwo = new ArrayList<>();
        betsWithMultipleOfTwo.add(BetType.BUY_5);
        betsWithMultipleOfTwo.add(BetType.BUY_5);
        betsWithMultipleOfTwo.add(BetType.LAY_4);
        betsWithMultipleOfTwo.add(BetType.LAY_10);

    }

    public void initializeBetProcessingMap(){
        betProcessingMap = new HashMap<>();
        betProcessingMap.put(BetType.PASS, () -> processPassBets(BetType.PASS));
        betProcessingMap.put(BetType.PASS_ODDS, () -> processPassBets(BetType.PASS_ODDS));
        betProcessingMap.put(BetType.DONT_PASS, () -> processDontPassBets(BetType.DONT_PASS));
        betProcessingMap.put(BetType.DONT_PASS_ODDS, () -> processDontPassBets(BetType.DONT_PASS_ODDS));
        betProcessingMap.put(BetType.COME, () -> processComeBets(BetType.COME));
        betProcessingMap.put(BetType.DONT_COME, () -> processDontComeBets(BetType.DONT_COME));
        betProcessingMap.put(BetType.COME_4, () -> processComePointBets(BetType.COME_4));
        betProcessingMap.put(BetType.COME_5, () -> processComePointBets(BetType.COME_5));
        betProcessingMap.put(BetType.COME_6, () -> processComePointBets(BetType.COME_6));
        betProcessingMap.put(BetType.COME_8, () -> processComePointBets(BetType.COME_8));
        betProcessingMap.put(BetType.COME_9, () -> processComePointBets(BetType.COME_9));
        betProcessingMap.put(BetType.COME_10, () -> processComePointBets(BetType.COME_10));
        betProcessingMap.put(BetType.COME_ODDS_4, () -> processComeOddsBets(BetType.COME_ODDS_4));
        betProcessingMap.put(BetType.COME_ODDS_5, () -> processComeOddsBets(BetType.COME_ODDS_5));
        betProcessingMap.put(BetType.COME_ODDS_6, () -> processComeOddsBets(BetType.COME_ODDS_6));
        betProcessingMap.put(BetType.COME_ODDS_8, () -> processComeOddsBets(BetType.COME_ODDS_8));
        betProcessingMap.put(BetType.COME_ODDS_9, () -> processComeOddsBets(BetType.COME_ODDS_9));
        betProcessingMap.put(BetType.COME_ODDS_10, () -> processComeOddsBets(BetType.COME_ODDS_10));
        betProcessingMap.put(BetType.DONT_COME_4, () -> processDontComePointBets(BetType.DONT_COME_4));
        betProcessingMap.put(BetType.DONT_COME_5, () -> processDontComePointBets(BetType.DONT_COME_5));
        betProcessingMap.put(BetType.DONT_COME_6, () -> processDontComePointBets(BetType.DONT_COME_6));
        betProcessingMap.put(BetType.DONT_COME_8, () -> processDontComePointBets(BetType.DONT_COME_8));
        betProcessingMap.put(BetType.DONT_COME_9, () -> processDontComePointBets(BetType.DONT_COME_9));
        betProcessingMap.put(BetType.DONT_COME_10, () -> processDontComePointBets(BetType.DONT_COME_10));
        betProcessingMap.put(BetType.DONT_COME_ODDS_4, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_4));
        betProcessingMap.put(BetType.DONT_COME_ODDS_5, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_5));
        betProcessingMap.put(BetType.DONT_COME_ODDS_6, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_6));
        betProcessingMap.put(BetType.DONT_COME_ODDS_8, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_8));
        betProcessingMap.put(BetType.DONT_COME_ODDS_9, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_9));
        betProcessingMap.put(BetType.DONT_COME_ODDS_10, () -> processDontComeOddsBets(BetType.DONT_COME_ODDS_10));
        betProcessingMap.put(BetType.PLACE_WIN_4, () -> processPlaceBets(BetType.PLACE_WIN_4));
        betProcessingMap.put(BetType.PLACE_WIN_5, () -> processPlaceBets(BetType.PLACE_WIN_5));
        betProcessingMap.put(BetType.PLACE_WIN_6, () -> processPlaceBets(BetType.PLACE_WIN_6));
        betProcessingMap.put(BetType.PLACE_WIN_8, () -> processPlaceBets(BetType.PLACE_WIN_8));
        betProcessingMap.put(BetType.PLACE_WIN_9, () -> processPlaceBets(BetType.PLACE_WIN_9));
        betProcessingMap.put(BetType.PLACE_WIN_10, () -> processPlaceBets(BetType.PLACE_WIN_10));
        betProcessingMap.put(BetType.BUY_4, () -> processBuyBets(BetType.BUY_4));
        betProcessingMap.put(BetType.BUY_5, () -> processBuyBets(BetType.BUY_5));
        betProcessingMap.put(BetType.BUY_6, () -> processBuyBets(BetType.BUY_6));
        betProcessingMap.put(BetType.BUY_8, () -> processBuyBets(BetType.BUY_8));
        betProcessingMap.put(BetType.BUY_9, () -> processBuyBets(BetType.BUY_9));
        betProcessingMap.put(BetType.BUY_10, () -> processBuyBets(BetType.BUY_10));
        betProcessingMap.put(BetType.LAY_4, () -> processLayBets(BetType.LAY_4));
        betProcessingMap.put(BetType.LAY_5, () -> processLayBets(BetType.LAY_5));
        betProcessingMap.put(BetType.LAY_6, () -> processLayBets(BetType.LAY_6));
        betProcessingMap.put(BetType.LAY_8, () -> processLayBets(BetType.LAY_8));
        betProcessingMap.put(BetType.LAY_9, () -> processLayBets(BetType.LAY_9));
        betProcessingMap.put(BetType.LAY_10, () -> processLayBets(BetType.LAY_10));
        betProcessingMap.put(BetType.BIG_6, () -> processBigBets(BetType.BIG_6, 6));
        betProcessingMap.put(BetType.BIG_8, () -> processBigBets(BetType.BIG_8, 8));
        betProcessingMap.put(BetType.HARD_4, () -> processHardBets(BetType.HARD_4, 4));
        betProcessingMap.put(BetType.HARD_6, () -> processHardBets(BetType.HARD_6, 6));
        betProcessingMap.put(BetType.HARD_8, () -> processHardBets(BetType.HARD_8, 8));
        betProcessingMap.put(BetType.HARD_10, () -> processHardBets(BetType.HARD_10, 10));
        betProcessingMap.put(BetType.FIELD, () -> processSingleRollBets(BetType.FIELD, 2,3,4,9,10,11,12));
        betProcessingMap.put(BetType.ANY_CRAPS, () -> processSingleRollBets(BetType.ANY_CRAPS, 2, 3, 12));
        betProcessingMap.put(BetType.CRAPS_2, () -> processSingleRollBets(BetType.CRAPS_2, 2));
        betProcessingMap.put(BetType.CRAPS_3, () -> processSingleRollBets(BetType.CRAPS_3, 3));
        betProcessingMap.put(BetType.CRAPS_12, () -> processSingleRollBets(BetType.CRAPS_12, 12));
        betProcessingMap.put(BetType.ANY_7, () -> processSingleRollBets(BetType.ANY_7, 7));
        betProcessingMap.put(BetType.ANY_11, () -> processSingleRollBets(BetType.ANY_11, 11));
    }

    public void initializePayoffMap(){
        payoffMap = new HashMap<>();
        payoffMap.put(BetType.PASS, 2d);
        payoffMap.put(BetType.COME, 2d);
        payoffMap.put(BetType.DONT_PASS, 2d);
        payoffMap.put(BetType.DONT_COME, 2d);
        payoffMap.put(BetType.BIG_6, 2d);
        payoffMap.put(BetType.BIG_8, 2d);
        payoffMap.put(BetType.COME_4, 2d);
        payoffMap.put(BetType.COME_5, 2d);
        payoffMap.put(BetType.COME_6, 2d);
        payoffMap.put(BetType.COME_8, 2d);
        payoffMap.put(BetType.COME_9, 2d);
        payoffMap.put(BetType.COME_10, 2d);
        payoffMap.put(BetType.DONT_COME_4, 2d);
        payoffMap.put(BetType.DONT_COME_5, 2d);
        payoffMap.put(BetType.DONT_COME_6, 2d);
        payoffMap.put(BetType.DONT_COME_8, 2d);
        payoffMap.put(BetType.DONT_COME_9, 2d);
        payoffMap.put(BetType.DONT_COME_10, 2d);
        payoffMap.put(BetType.ANY_CRAPS, 8d);
        payoffMap.put(BetType.CRAPS_2, 31d);
        payoffMap.put(BetType.CRAPS_12, 31d);
        payoffMap.put(BetType.CRAPS_3, 16d);
        payoffMap.put(BetType.ANY_11, 16d);
        payoffMap.put(BetType.ANY_7, 5d);
        payoffMap.put(BetType.COME_ODDS_4, 3d);
        payoffMap.put(BetType.COME_ODDS_10, 3d);
        payoffMap.put(BetType.COME_ODDS_5, (5d/2d));
        payoffMap.put(BetType.COME_ODDS_9, (5d/2d));
        payoffMap.put(BetType.COME_ODDS_6, (11d/5d));
        payoffMap.put(BetType.COME_ODDS_8, (11d/5d));
        payoffMap.put(BetType.DONT_COME_ODDS_4, (3d/2d));
        payoffMap.put(BetType.DONT_COME_ODDS_10, (3d/2d));
        payoffMap.put(BetType.DONT_COME_ODDS_5, (5d/3d));
        payoffMap.put(BetType.DONT_COME_ODDS_9, (5d/3d));
        payoffMap.put(BetType.DONT_COME_ODDS_6, (11d/6d));
        payoffMap.put(BetType.DONT_COME_ODDS_8, (11d/6d));
        payoffMap.put(BetType.PLACE_WIN_6, (13d/6d));
        payoffMap.put(BetType.PLACE_WIN_8, (13d/6d));
        payoffMap.put(BetType.PLACE_WIN_5, (12d/5d));
        payoffMap.put(BetType.PLACE_WIN_9, (12d/5d));
        payoffMap.put(BetType.PLACE_WIN_4, (14d/5d));
        payoffMap.put(BetType.PLACE_WIN_10, (14d/5d));
        payoffMap.put(BetType.BUY_6, (11d/5d));
        payoffMap.put(BetType.BUY_8, (11d/5d));
        payoffMap.put(BetType.BUY_5, (5d/2d));
        payoffMap.put(BetType.BUY_9, (5d/2d));
        payoffMap.put(BetType.BUY_4, 3d);
        payoffMap.put(BetType.BUY_10, 3d);
        payoffMap.put(BetType.LAY_6, (11d/6d));
        payoffMap.put(BetType.LAY_8, (11d/6d));
        payoffMap.put(BetType.LAY_5, (5d/3d));
        payoffMap.put(BetType.LAY_9, (5d/3d));
        payoffMap.put(BetType.LAY_4, (3d/2d));
        payoffMap.put(BetType.LAY_10, (3d/2d));
        payoffMap.put(BetType.HARD_4, 8d);
        payoffMap.put(BetType.HARD_10, 8d);
        payoffMap.put(BetType.HARD_6, 10d);
        payoffMap.put(BetType.HARD_8, 10d);
    }
}