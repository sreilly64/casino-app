package io.zipcoder.casino;

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


    @BeforeEach
    public void setUp() {
        testCasinoDriver = new CasinoDriver();
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
    public void test() {
        String expected = "";
        //testCasinoDriver.();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void startCasino() {
    }

    @Test
    void playerLogin() {
    }

    @Test
    void isReturningPlayer() {
    }

    @Test
    void createPlayer() {
    }

    @Test
    void isNameAvailable() {
    }

    @Test
    void setCurrentPlayer() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void playerLogout() {
    }

    @Test
    void printGamesList() {
    }

    @Test
    void chooseGame() {
    }
}