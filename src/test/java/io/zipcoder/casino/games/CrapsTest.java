package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class CrapsTest {

    private Craps craps;
    private Player player;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
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
    void getCurrentBetsMapTest() {
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
    void processPassBetsComeOutWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(7);
        //when
        craps.validateBetAmount(BetType.PASS, 100, true, true, true);
        craps.processPassBets(BetType.PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPassBetsComeOutLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(12);
        //when
        craps.validateBetAmount(BetType.PASS, 100, true, true, true);
        craps.processPassBets(BetType.PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPassBetsPointWin() {
        //given
        Integer expected = player.getCurrentFunds() +100;
        craps.setDiceTotal(6);
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        //when
        craps.validateBetAmount(BetType.PASS, 100, true, true, true);
        craps.processPassBets(BetType.PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void processPassBetsPointLose() {
        //given
        Integer expected = player.getCurrentFunds() -100;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        //when
        craps.validateBetAmount(BetType.PASS, 100, true, true, true);
        craps.processPassBets(BetType.PASS);
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculatePayoffField() {
        //given
        Integer expected = player.getCurrentFunds() + 200;
        craps.setDiceTotal(2);
        craps.validateBetAmount(BetType.FIELD, 100, true, true, true);
        //when
        craps.payout(craps.getPlayer(), craps.calculatePayoff(BetType.FIELD));
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculatePayoffPassOdds() {
        //given
        Integer expected = player.getCurrentFunds() + 200;
        craps.setDiceTotal(4);
        craps.setGamePhase(false);
        craps.validateBetAmount(BetType.PASS_ODDS, 100, true, true, true);
        //when
        craps.payout(craps.getPlayer(), craps.calculatePayoff(BetType.PASS_ODDS));
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculatePayoffDontPassOdds() {
        //given
        Integer expected = player.getCurrentFunds() + 50;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        craps.setCurrentPoint(10);
        craps.validateBetAmount(BetType.DONT_PASS_ODDS, 100, true, true, true);
        //when
        craps.payout(craps.getPlayer(), craps.calculatePayoff(BetType.DONT_PASS_ODDS));
        Integer actual = player.getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getDontPassOddsPayout() {
        //given
        Double expected = 50d;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        craps.setCurrentPoint(5);
        craps.validateBetAmount(BetType.DONT_PASS_ODDS, 30, true, true, true);
        //when
        Double actual = craps.getDontPassOddsPayout();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getDontPassOddsPayout2() {
        //given
        Double expected = 55d;
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        craps.validateBetAmount(BetType.DONT_PASS_ODDS, 30, true, true, true);
        //when
        Double actual = craps.getDontPassOddsPayout();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getPassOddsPayout() {
        //given
        Double expected = 250d;
        craps.setDiceTotal(5);
        craps.setGamePhase(false);
        craps.setCurrentPoint(5);
        craps.validateBetAmount(BetType.PASS_ODDS, 100, true, true, true);
        //when
        Double actual = craps.getPassOddsPayout();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getPassOddsPayout2() {
        //given
        Double expected = 220d;
        craps.setDiceTotal(6);
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        craps.validateBetAmount(BetType.PASS_ODDS, 100, true, true, true);
        //when
        Double actual = craps.getPassOddsPayout();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getFieldBetPayoff() {
        //given
        Double expected = 200d;
        craps.setDiceTotal(3);
        craps.validateBetAmount(BetType.FIELD, 100, true, true, true);
        //when
        Double actual = craps.getFieldBetPayoff();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void betLoses() {
        //given
        Integer expected = 0;
        craps.bet(BetType.PASS, 100);
        //when
        craps.betLoses(BetType.PASS);
        Integer actual = craps.getCurrentBetsMap().get(BetType.PASS);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void betWins() {
        //given
        Integer expected = craps.getPlayer().getCurrentFunds() + 100;
        craps.bet(BetType.PASS, 100);
        craps.getPlayer().subtractFromCurrentFunds(100);
        //when
        craps.betWins(BetType.PASS);
        Integer actual = craps.getPlayer().getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void clearBet() {
        //given
        Integer expected = 0;
        craps.bet(BetType.PASS, 100);
        //when
        craps.clearBet(BetType.PASS);
        Integer actual = craps.getCurrentBetsMap().get(BetType.PASS);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void assessGamePhaseComeOut() {
        //given
        craps.setDiceTotal(4);
        //when
        craps.assessGamePhase();
        Boolean actual = craps.getGamePhase();
        //then
        Assert.assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void assessGamePhasePoint() {
        //given
        craps.setDiceTotal(7);
        craps.setGamePhase(false);
        //when
        craps.assessGamePhase();
        Boolean actual = craps.getGamePhase();
        //then
        Assert.assertTrue(actual);
    }

    @org.junit.jupiter.api.Test
    void retrieveBetEnum() {
        //given
        BetType expected = BetType.PASS;
        //when
        BetType actual = craps.retrieveBetEnum("pass");
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getCurrentBetEmpty() {
        //given
        Integer expected = 0;
        //when
        Integer actual = craps.getCurrentBet();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getCurrentBetWithBets() {
        //given
        Integer expected = 200;
        craps.bet(BetType.PASS, 100);
        craps.bet(BetType.FIELD, 100);
        //when
        Integer actual = craps.getCurrentBet();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void clearBets() {
        //given
        Integer expected = 0;
        craps.bet(BetType.PASS, 100);
        craps.bet(BetType.FIELD, 100);
        //when
        craps.clearBets();
        Integer actual = craps.getCurrentBet();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void payout() {
        //given
        Integer expected = craps.getPlayer().getCurrentFunds() + 100;
        //when
        craps.payout(craps.getPlayer(), 100);
        Integer actual = craps.getPlayer().getCurrentFunds();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void actionSelection() {
    }

    @org.junit.jupiter.api.Test
    void rollOrBet() {
    }

    @org.junit.jupiter.api.Test
    void processBetOrRoll() {
        //given
        String userInput = "roll";
        //when
        craps.processBetOrRoll(userInput);
        Integer actual = craps.getDiceTotal();
        //then
        Assert.assertNotEquals(null, actual);
    }

    @org.junit.jupiter.api.Test
    void printCurrentGamePhaseComeOut() {
        //given
        String expected = "Come Out roll\n";
        //when
        craps.printCurrentGamePhase();
        //then
        Assert.assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void printCurrentGamePhasePoint() {
        //given
        String expected = "The Point is ON: 4\n";
        craps.setGamePhase(false);
        craps.setCurrentPoint(4);
        //when
        craps.printCurrentGamePhase();
        //then
        Assert.assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void getUserInputBetType() {
    }

    @org.junit.jupiter.api.Test
    void getUserInputBetAmount() {
    }

    @org.junit.jupiter.api.Test
    void printPlayerBalance() {
        //given
        String expected = "Balance: $10000\n";
        //when
        craps.printPlayerBalance();
        //then
        Assert.assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void getPlayingGame() {
        //given
        Boolean expected = true;
        //when
        Boolean actual = craps.getPlayingGame();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void checkForGameQuit() {
        //given
        String userInput = "quit";
        //when
        craps.checkForGameQuit(userInput);
        //then
        Assert.assertFalse(craps.getPlayingGame());
    }

    @org.junit.jupiter.api.Test
    void printCurrentBets() {
        //given
        String expected = "Currently placed bets:\nPASS = $100\n";
        //when
        craps.bet(BetType.PASS, 100);
        craps.printCurrentBets();
        String actual = outContent.toString();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateBetTypeNotAvailable() {
        //given
        String expected = "This bet is not available at this time.\n";
        //when
        craps.validateBetType(BetType.COME);
        String actual = outContent.toString();
        //then
        Assert.assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void validateBetTypeInvalid() {
        //given
        String expected = "Invalid bet type\n";
        //when
        craps.validateBetType(null);
        String actual = outContent.toString();
        //then
        Assert.assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple6() {
        //given
        Integer expected = 6;
        BetType betType = BetType.PLACE_WIN_6;
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple5() {
        //given
        Integer expected = 5;
        BetType betType = BetType.PLACE_WIN_9;
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple3() {
        //given
        Integer expected = 3;
        BetType betType = BetType.LAY_5;
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple2() {
        //given
        Integer expected = 2;
        BetType betType = BetType.BUY_5;
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultiple1() {
        //given
        Integer expected = 1;
        BetType betType = BetType.PASS;
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetMultipleOdds() {
        //given
        Integer expected = 1;
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(4);
        //when
        Integer actual = craps.getBetMultiple(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsPass4() {
        //given
        Integer expected = 1;
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(4);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsDontPass4() {
        //given
        Integer expected = 2;
        BetType betType = BetType.DONT_PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(4);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsPass5() {
        //given
        Integer expected = 2;
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(5);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsDontPass5() {
        //given
        Integer expected = 3;
        BetType betType = BetType.DONT_PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(5);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsPass6() {
        //given
        Integer expected = 5;
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMultipleForOddsDontPass6() {
        //given
        Integer expected = 6;
        BetType betType = BetType.DONT_PASS_ODDS;
        craps.setGamePhase(false);
        craps.setCurrentPoint(6);
        //when
        Integer actual = craps.getMultipleForOdds(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMaxBet() {
        //given
        Integer expected = 1000;
        BetType betType = BetType.PASS;
        //when
        Integer actual = craps.getMaxBet(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMaxBetOdds() {
        //given
        Integer expected = 300;
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.bet(BetType.PASS, 100);
        //when
        Integer actual = craps.getMaxBet(betType);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void isBetAvailableOdds() {
        //given
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(false);
        craps.bet(BetType.PASS, 100);
        //when
        Boolean actual = craps.isBetAvailable(betType);
        //then
        Assert.assertTrue(actual);
    }

    @org.junit.jupiter.api.Test
    void isBetAvailableComeOut() {
        //given
        BetType betType = BetType.PASS_ODDS;
        craps.setGamePhase(true);
        craps.bet(BetType.PASS, 100);
        //when
        Boolean actual = craps.isBetAvailable(betType);
        //then
        Assert.assertTrue(actual);
    }

    @org.junit.jupiter.api.Test
    void calculateBetReqsPasses() {
        //given
        Integer expected = 100;
        //when
        craps.calculateBetReqs(BetType.PASS, 100);
        Integer actual = craps.getCurrentBetsMap().get(BetType.PASS);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculateBetReqsFails() {
        //given
        String expected = "Bet amount did not meet requirements.\n";
        //when
        craps.calculateBetReqs(BetType.PASS, 10000);
        String actual = outContent.toString();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateBetAmount() {
        //given
        Integer expected = 100;
        //when
        craps.validateBetAmount(BetType.PASS, 100,true,true,true);
        Integer actual = craps.getCurrentBetsMap().get(BetType.PASS);
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void resetGame() {
    }

    @org.junit.jupiter.api.Test
    void quitGame() {
        //given

        //when
        craps.quitGame();
        Boolean actual = craps.getPlayingGame();
        //then
        Assert.assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void getGameName() {
        //given
        String expected = "Craps";
        //when
        String actual = craps.getGameName();
        //then
        Assert.assertEquals(expected, actual);
    }


    @org.junit.jupiter.api.Test
    void startGame() {
    }

    @org.junit.jupiter.api.Test
    void initializeFieldsPoint() {
        //given
        Integer expected = 0;
        //when
        craps.initializeFields();
        Integer actual = craps.getCurrentPoint();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void initializeFieldsMinBet() {
        //given
        Integer expected = 5;
        //when
        craps.initializeFields();
        Integer actual = craps.getMinimumBet();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void initializeFieldsPlayingGame() {
        //given
        Boolean expected = true;
        //when
        craps.initializeFields();
        Boolean actual = craps.getPlayingGame();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void initializeFieldsGamePhase() {
        //given
        Boolean expected = true;
        //when
        craps.initializeFields();
        Boolean actual = craps.getGamePhase();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getMinimumBet() {
        //given
        Integer expected = 5;
        //when
        craps.initializeFields();
        Integer actual = craps.getMinimumBet();
        //then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getBetProcessingMap() {
        //given
        craps.initializeBetProcessingMap();
        //when
        craps.getBetProcessingMap();
        //then
        Assert.assertNotEquals(null, craps.getBetProcessingMap());
    }

    @org.junit.jupiter.api.Test
    void initializeBetProcessingMap() {
        //when
        craps.initializeBetProcessingMap();
        //then
        Assert.assertNotEquals(null, craps.getBetProcessingMap());
    }

    @org.junit.jupiter.api.Test
    void getPayoffMap() {
        //when
        craps.initializePayoffMap();
        //then
        Assert.assertNotEquals(null, craps.getPayoffMap());
    }

    @org.junit.jupiter.api.Test
    void initializePayoffMap() {
        //given
        craps.initializePayoffMap();
        //when
        craps.getPayoffMap();
        //then
        Assert.assertNotEquals(null, craps.getPayoffMap());
    }
}