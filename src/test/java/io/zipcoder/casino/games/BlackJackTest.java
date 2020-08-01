package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static io.zipcoder.casino.Card.Rank.ACE;
import static io.zipcoder.casino.Card.Suit.HEARTS;
import static org.junit.Assert.*;
public class BlackJackTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private BlackJack testBJ;
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testBJ = new BlackJack(Card.getNewDeck());
        testPlayer = new Player("Lake", 1000);
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));

    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

//    @Test
//    void drawTopCard() {
//    }
//
//    @Test
//    void resetGame() {
//    }

    @Test
    void quitGame() {
        testBJ.quitGame();
        assertFalse(testBJ.playing);
    }

    @Test
    void getGameName() {
        String expected = "Black Jack";
        String actual = testBJ.getGameName();
        assertEquals(expected, actual);
    }

//    @Test
//    void startGame() {
//    }
//
//    @Test
//    void startPlay() {
//    }

    @Test
    void isStillPlaying() {
        testBJ.isStillPlaying("No");
        assertFalse(testBJ.playing);
    }

    @Test
    void isStillPlaying2() {
        testBJ.isStillPlaying("Yes");
        assertTrue(testBJ.playing);
    }


//    @Test
//    void dealCard() {
//    }

    @Test
    void bet() {
        testBJ.bet(10);
        Integer expected = 10;
        Integer actual = testBJ.getCurrentBet();
        assertEquals(expected, actual);
    }

//    @Test
//    void stand() {
//    }
//
//    @Test
//    void checkDealerForBlackJack() {
//    }
//
//    @Test
//    void getDealersPlay() {
//    }
//
//    @Test
//    void getWinner() {
//    }
//
//    @Test
//    void payout() {
//    }

    @Test
    void getCurrentBet() {
        testBJ.bet(10);
        Integer expected = 10;
        Integer actual = testBJ.getCurrentBet();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentBet2() {
        assertNull(testBJ.getCurrentBet());
    }

    @Test
    void clearBets() {
        testBJ.bet(10);
        testBJ.clearBets();
        assertNull(testBJ.currentBet);
    }

//    @Test
//    void dealCards() {
//    }
//
//    @Test
//    void printIntroduction() {
//    }

//    @Test
//    void getCurrentHand() {
//    }
//
//    @Test
//    void getNextMove() {
//    }
//
//    @Test
//    void getChoices() {
//    }
//
    @Test
    void getScore() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, ACE));
        Integer expected = 1;
        Integer actual = testBJ.getScore(testBJ.playersHand);
        assertEquals(expected, actual);
    }
//
//    @Test
//    void hit() {
//    }

}