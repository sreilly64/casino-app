package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

public class CrapsTest {

    private Craps craps;
    private Player player;

    @BeforeEach
    void setUp() {
        craps = new Craps();
        player = new Player("Mr. Money Bags", 10000);
        craps.setPlayer(player);
        craps.initializeBetsMap();
        craps.initializeBetArrayLists();
        craps.initializeFields();
        craps.initializeBetProcessingMap();
        craps.initializePayoffMap();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSetUpBetsMap(){
        //given
        craps.initializeBetsMap();
        Map<BetType, Integer> expected = new LinkedHashMap<>();
        //when
        for(BetType bet: BetType.values()){
            expected.put(bet, 0);
        }
        Map<BetType, Integer> actual = craps.getCurrentBetsMap();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void initializeBetsMap() {
        //given
        craps.initializeBetsMap();
        TreeMap<BetType, Integer> expected = new TreeMap<>();
        for(BetType bet: BetType.values()){
            expected.put(bet, 0);
        }
        //when
        TreeMap<BetType, Integer> actual= craps.getCurrentBetsMap();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getCurrentBetsMap() {
        //given
        TreeMap<BetType, Integer> expected = new TreeMap<>();
        for(BetType bet: BetType.values()){
            expected.put(bet, 0);
        }
        //when
        TreeMap<BetType, Integer> actual= craps.getCurrentBetsMap();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getGamePhase() {
        //given
        Boolean expected = true;
        //when
        Boolean actual = craps.getGamePhase();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setGamePhase() {
        //given
        Boolean expected = false;
        //when
        craps.setGamePhase(false);
        Boolean actual = craps.getGamePhase();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getCurrentPoint() {
        //given
        Integer expected = 0;
        //when
        Integer actual = craps.getCurrentPoint();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setCurrentPoint() {
        //given
        Integer expected = 4;
        //when
        craps.setCurrentPoint(4);
        Integer actual = craps.getCurrentPoint();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getPlayer() {
        //given
        Player expected = player;
        //when
        Player actual = craps.getPlayer();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setPlayer() {
        //given
        Player expected = new Player("Bob", 100);
        //when
        craps.setPlayer(expected);
        Player actual = craps.getPlayer();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getDiceRolls() {
        //given
        Integer[] dieRolls = {4,3};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(dieRolls));
        //when
        craps.setDiceRolls(expected);
        ArrayList<Integer> actual = craps.getDiceRolls();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setDiceRolls() {
        //given
        Integer[] dieRolls = {4,3};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(dieRolls));
        //when
        craps.setDiceRolls(expected);
        ArrayList<Integer> actual = craps.getDiceRolls();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void rollTheDice() {
        //given

        //when
        craps.rollTheDice();
        ArrayList<Integer> actual = craps.getDiceRolls();
        //then
        Assert.assertNotEquals(null, actual);
    }

    @org.junit.jupiter.api.Test
    void getActiveBets() {
        //given
        ArrayList<BetType> expected = new ArrayList<>();
        //when
        ArrayList<BetType> actual = craps.getActiveBets();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setActiveBets() {
        //given
        ArrayList<BetType> expected = new ArrayList<>(Arrays.asList(BetType.PASS));
        //when
        craps.bet(BetType.PASS, 100);
        craps.setActiveBets();
        ArrayList<BetType> actual = craps.getActiveBets();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setDiceTotal() {
        //given
        Integer expected = 7;
        //when
        craps.setDiceTotal(7);
        Integer actual = craps.getDiceTotal();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getDiceTotal() {
        //given
        Integer expected = 7;
        //when
        craps.setDiceTotal(7);
        Integer actual = craps.getDiceTotal();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void checkIfBetsAreAffected() {
        //given
        Integer expected = 0;
        craps.setDiceTotal(7);
        craps.bet(BetType.PASS, 100);
        craps.setActiveBets();
        //when
        craps.checkIfBetsAreAffected();
        Integer actual = craps.getCurrentBetsMap().get(BetType.PASS);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processSingleRollBetsWins() {
        //given
        Integer expected = player.getCurrentFunds() + 700;
        craps.setDiceTotal(2);
        craps.validateBetAmount(BetType.ANY_CRAPS, 100, true, true, true);
        //when
        craps.processSingleRollBets(BetType.ANY_CRAPS, 2,3,12);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processSingleRollBetsLoses() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(5);
        craps.validateBetAmount(BetType.ANY_CRAPS, 100, true, true, true);
        //when
        craps.processSingleRollBets(BetType.ANY_CRAPS, 2,3,12);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processHardBetsWins() {
        //given
        Integer expected = player.getCurrentFunds() +700;
        Integer[] dieRolls = {2,2};
        craps.setDiceRolls(new ArrayList<>(Arrays.asList(dieRolls)));
        craps.setDiceTotal(4);
        //when
        craps.validateBetAmount(BetType.HARD_4, 100, true, true, true);
        craps.processHardBets(BetType.HARD_4, 4);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processHardBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        Integer[] dieRolls = {3,1};
        craps.setDiceRolls(new ArrayList<>(Arrays.asList(dieRolls)));
        craps.setDiceTotal(4);
        //when
        craps.validateBetAmount(BetType.HARD_4, 100, true, true, true);
        craps.processHardBets(BetType.HARD_4, 4);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processBigBetsWins() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(6);
        //when
        craps.validateBetAmount(BetType.BIG_6, 100, true, true, true);
        craps.processBigBets(BetType.BIG_6, 6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processBigBetsLoses() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        //when
        craps.validateBetAmount(BetType.BIG_6, 100, true, true, true);
        craps.processBigBets(BetType.BIG_6, 6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processLayBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +50;
        craps.setDiceTotal(7);
        //when
        craps.validateBetAmount(BetType.LAY_4, 100, true, true, true);
        craps.processLayBets(BetType.LAY_4);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processLayBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(4);
        //when
        craps.validateBetAmount(BetType.LAY_4, 100, true, true, true);
        craps.processLayBets(BetType.LAY_4);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processBuyBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +150;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.BUY_5, 100, true, true, true);
        craps.processBuyBets(BetType.BUY_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processBuyBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.BUY_5, 100, true, true, true);
        craps.processBuyBets(BetType.BUY_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPlaceBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +140;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.PLACE_WIN_5, 100, true, true, true);
        craps.processPlaceBets(BetType.PLACE_WIN_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPlaceBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.PLACE_WIN_5, 100, true, true, true);
        craps.processPlaceBets(BetType.PLACE_WIN_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComeOddsBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +60;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME_ODDS_5, 90, true, true, true);
        craps.processDontComeOddsBets(BetType.DONT_COME_ODDS_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComeOddsBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -90;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME_ODDS_5, 90, true, true, true);
        craps.processDontComeOddsBets(BetType.DONT_COME_ODDS_5);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComePointBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME_6, 100, true, true, true);
        craps.processDontComePointBets(BetType.DONT_COME_6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComePointBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(6);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME_6, 100, true, true, true);
        craps.processDontComePointBets(BetType.DONT_COME_6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComeOddsBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +120;
        craps.setDiceTotal(6);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME_ODDS_6, 100, true, true, true);
        craps.processComeOddsBets(BetType.COME_ODDS_6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComeOddsBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME_ODDS_6, 100, true, true, true);
        craps.processComeOddsBets(BetType.COME_ODDS_6);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComePointBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(10);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME_10, 100, true, true, true);
        craps.processComePointBets(BetType.COME_10);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComePointBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME_10, 100, true, true, true);
        craps.processComePointBets(BetType.COME_10);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculateTargetPoint() {
        //given
        Integer expected = 4;
        //when
        Integer actual = craps.calculateTargetPoint(BetType.COME_4, craps.comeBetsList);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComeBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(2);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME, 100, true, true, true);
        craps.processDontComeBets(BetType.DONT_COME);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComeBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME, 100, true, true, true);
        craps.processDontComeBets(BetType.DONT_COME);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontComeBetsToPoint() {
        //given
        Integer expected = 100;
        craps.setDiceTotal(4);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_COME, 100, true, true, true);
        craps.processDontComeBets(BetType.DONT_COME);
        Integer actual = craps.getCurrentBetsMap().get(BetType.DONT_COME_4);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComeBetsWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(11);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME, 100, true, true, true);
        craps.processComeBets(BetType.COME);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComeBetsLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(12);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME, 100, true, true, true);
        craps.processComeBets(BetType.COME);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processComeBetsToPoint() {
        //given
        Integer expected = 100;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME, 100, true, true, true);
        craps.processComeBets(BetType.COME);
        Integer actual = craps.getCurrentBetsMap().get(BetType.COME_5);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void transferBetToPoint() {
        //given
        Integer expected = 100;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.COME, 100, true, true, true);
        craps.transferBetToPoint(BetType.COME, craps.comeBetsList);
        Integer actual = craps.getCurrentBetsMap().get(BetType.COME_5);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontPassBetsComeOutWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(2);
        //when
        craps.validateBetAmount(BetType.DONT_PASS, 100, true, true, true);
        craps.processDontPassBets(BetType.DONT_PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontPassBetsComeOutLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        //when
        craps.validateBetAmount(BetType.DONT_PASS, 100, true, true, true);
        craps.processDontPassBets(BetType.DONT_PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontPassBetsPointWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.validateBetAmount(BetType.DONT_PASS, 100, true, true, true);
        craps.processDontPassBets(BetType.DONT_PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processDontPassBetsPointLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(6);
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        //when
        craps.validateBetAmount(BetType.DONT_PASS, 100, true, true, true);
        craps.processDontPassBets(BetType.DONT_PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPassBets() {
    }

    @org.junit.jupiter.api.Test
    void calculatePayoff() {
    }

    @org.junit.jupiter.api.Test
    void getDontPassOddsPayout() {
    }

    @org.junit.jupiter.api.Test
    void getPassOddsPayout() {
    }

    @org.junit.jupiter.api.Test
    void getFieldBetPayoff() {
    }

    @org.junit.jupiter.api.Test
    void betLoses() {
    }

    @org.junit.jupiter.api.Test
    void betWins() {
    }

    @org.junit.jupiter.api.Test
    void clearBet() {
    }

    @org.junit.jupiter.api.Test
    void assessGamePhase() {
    }

    @org.junit.jupiter.api.Test
    void retrieveBetEnum() {
    }

    @org.junit.jupiter.api.Test
    void getCurrentBet() {
    }

    @org.junit.jupiter.api.Test
    void bet() {
    }

    @org.junit.jupiter.api.Test
    void testBet() {
    }

    @org.junit.jupiter.api.Test
    void clearBets() {
    }

    @org.junit.jupiter.api.Test
    void payout() {
    }

    @org.junit.jupiter.api.Test
    void actionSelection() {
    }

    @org.junit.jupiter.api.Test
    void rollOrBet() {
    }

    @org.junit.jupiter.api.Test
    void clarifyInput() {
    }

    @org.junit.jupiter.api.Test
    void printCurrentGamePhase() {
    }

    @org.junit.jupiter.api.Test
    void getUserInputBetType() {
    }

    @org.junit.jupiter.api.Test
    void getUserInputBetAmount() {
    }

    @org.junit.jupiter.api.Test
    void printPlayerBalance() {
    }

    @org.junit.jupiter.api.Test
    void checkForGameQuit() {
    }

    @org.junit.jupiter.api.Test
    void printCurrentBets() {
    }

    @org.junit.jupiter.api.Test
    void validateBetType() {
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple() {
    }

    @org.junit.jupiter.api.Test
    void get5Or9Ratios() {
    }

    @org.junit.jupiter.api.Test
    void get4Or10Ratios() {
    }

    @org.junit.jupiter.api.Test
    void getMaxBet() {
    }

    @org.junit.jupiter.api.Test
    void isBetAvailable() {
    }

    @org.junit.jupiter.api.Test
    void calculateBetReqs() {
    }

    @org.junit.jupiter.api.Test
    void validateBetAmount() {
    }

    @org.junit.jupiter.api.Test
    void resetGame() {
    }

    @org.junit.jupiter.api.Test
    void quitGame() {
    }

    @org.junit.jupiter.api.Test
    void getGameName() {
    }


    @org.junit.jupiter.api.Test
    void startGame() {
    }

    @org.junit.jupiter.api.Test
    void initializeFields() {
    }

    @org.junit.jupiter.api.Test
    void initializeBetProcessingMap() {
    }

    @org.junit.jupiter.api.Test
    void initializePayoffMap() {
    }
}