package io.zipcoder.casino.player;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetName(){
        //given
        Player alpha = new Player("Albert", 1000);

        //when
        String expected = "Albert";
        String actual = alpha.getName();

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentFunds(){
        //given
        Player alpha = new Player("Albert", 1000);

        //when
        Integer expected = 1000;
        Integer actual = alpha.getCurrentFunds();

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddToCurrentFunds(){
        //given
        Player alpha = new Player("Albert", 1000);

        //when
        Integer expected = 1500;
        alpha.addToCurrentFunds(500);
        Integer actual = alpha.getCurrentFunds();

        //then
        Assert.assertEquals(expected, actual);
    }

}