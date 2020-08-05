package io.zipcoder.casino;

import io.zipcoder.casino.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CasinoDriverTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    CasinoDriver testCasinoDriver;
    Player testPlayer;

    @BeforeEach
    public void setUp() {
        testCasinoDriver = new CasinoDriver();
        testPlayer = new Player("Lake", 1000);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void chooseSelection() {
        testCasinoDriver.chooseLoggedInSelection(true, "n/a");
        String expected = "We didn't quite catch that.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void chooseSelection2() {
        assertFalse(testCasinoDriver.chooseLoggedInSelection(true, "quit"));
    }

    @Test
    void chooseSelection3() {
        testCasinoDriver.setCurrentPlayer(testPlayer);
        testCasinoDriver.chooseLoggedInSelection(true, "get balance");
        String expected = "Your current balance is: $1000.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void chooseSelection5() {
        assertFalse(testCasinoDriver.chooseNotLoggedInSelection(true, "quit"));
    }

    @Test
    void playerLogin() {
        testCasinoDriver.setCurrentPlayer(testPlayer);
        testCasinoDriver.playerLogin();
        String expected = "Lake is already logged in.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void isReturningPlayer() {
        assertFalse(testCasinoDriver.isReturningPlayer("Lake"));
    }

    @Test
    void createPlayer() {
        Player expected = testPlayer;
        Player actual = testCasinoDriver.createPlayer("Lake", 1000);
        assertEquals(expected, actual);
    }

    @Test
    void isNameAvailable() {
        assertTrue(testCasinoDriver.isNameAvailable("Lake"));
    }

    @Test
    void setCurrentPlayer() {
        testCasinoDriver.setCurrentPlayer(testPlayer);
        Player expected = testPlayer;
        Player actual = testCasinoDriver.getCurrentPlayer();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentPlayer() {
        assertNull(testCasinoDriver.getCurrentPlayer());
    }

    @Test
    void playerLogout() {
        testCasinoDriver.setCurrentPlayer(testPlayer);
        testCasinoDriver.playerLogout();
        String expected = "You are logged out.\n";
        assertNull(testCasinoDriver.getCurrentPlayer());
        assertEquals(expected, outContent.toString());
    }

    @Test
    void playerLogout2() {
        testCasinoDriver.playerLogout();
        String expected = "Nobody is logged in...\n";
        assertNull(testCasinoDriver.getCurrentPlayer());
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printGamesList() {
        testCasinoDriver.printGamesList();
        String expected = "<Black Jack> | <Craps> | <Go Fish> | <Going to Boston> | ";
        assertEquals(expected, outContent.toString());
    }
}