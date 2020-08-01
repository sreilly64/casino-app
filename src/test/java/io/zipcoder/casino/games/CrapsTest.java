package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrapsTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetUpBetsMap(){
        //given
        Craps craps = new Craps();
        Player player = new Player("Bob", 1000);
        craps.initializeBetsMap();

        //when
        Map<BetType, Integer> expected = new LinkedHashMap<>();

        for(BetType bet: BetType.values()){
            expected.put(bet, 0);
        }

        Map<BetType, Integer> actual = craps.getCurrentBetsMap();

        //then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testPrintDiceRoll(){
        Craps craps = new Craps();
        Player player = new Player("Bob", 1000);

        craps.printDiceRolls();
    }
}