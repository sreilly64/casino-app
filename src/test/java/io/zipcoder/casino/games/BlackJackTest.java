package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static io.zipcoder.casino.Card.Rank.*;
import static io.zipcoder.casino.Card.Suit.DIAMONDS;
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

    @Test
    void resetGame_PlayersHand_NotNull() {
        assertNull(testBJ.playersHand);
        testBJ.resetGame();
        assertNotNull(testBJ.playersHand);
    }

    @Test
    void resetGame_PlayersHand_Size() {
        testBJ.resetGame();
        Integer expected = 2;
        Integer actual = testBJ.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void resetGame_DealersHand_NotNull() {
        assertNull(testBJ.dealersHand);
        testBJ.resetGame();
        assertNotNull(testBJ.dealersHand);
    }

    @Test
    void resetGame_DealersHand_Size() {
        testBJ.resetGame();
        Integer expected = 1;
        Integer actual = testBJ.dealersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void resetGame_CurrentBet_Null() {
        testBJ.bet(10);
        testBJ.resetGame();
        assertNull(testBJ.currentBet);
    }

    @Test
    void resetGame_Winne_Null() {
        testBJ.winner = testPlayer;
        testBJ.resetGame();
        assertNull(testBJ.winner);
    }

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

    @Test
    void startPlay() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, JACK));
        testBJ.playersHand.add(new Card(HEARTS, QUEEN));
        testBJ.playersHand.add(new Card(HEARTS, KING));
        testBJ.playRound(true);
        String expected = "You have: JACK of HEARTS | QUEEN of HEARTS | KING of HEARTS | \nYou bust!\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void isStillPlaying_NoInput() {
        testBJ.isStillPlaying("No");
        assertFalse(testBJ.playing);
    }

    @Test
    void isStillPlaying_YesInput() {
        testBJ.isStillPlaying("Yes");
        assertTrue(testBJ.playing);
    }

    @Test
    void dealCard() {
        testBJ.resetGame();
        testBJ.dealCard(testBJ.playersHand, 1);
        Integer expected = 3;
        Integer actual = testBJ.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void bet() {
        testBJ.bet(10);
        Integer expected = 10;
        Integer actual = testBJ.getCurrentBet();
        assertEquals(expected, actual);
    }

    @Test
    void stand() {
        testBJ.stand();
        String expected = "You stand.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void payout_Winner() {
        testBJ.resetGame();
        testBJ.winner = testPlayer;
        testBJ.payout(testPlayer, 100);
        Integer expected = 1100;
        Integer actual = testPlayer.getCurrentFunds();
        assertEquals(expected, actual);
    }

    @Test
    void payout_Loser() {
        testBJ.resetGame();
        testBJ.winner = testBJ.dealer;
        testBJ.payout(testPlayer, 100);
        Integer expected = 900;
        Integer actual = testPlayer.getCurrentFunds();
        assertEquals(expected, actual);
    }

    @Test
    void printWinner_Player() {
        testBJ.resetGame();
        testBJ.winner = testPlayer;
        testBJ.printWinner();
        String expected = "Lake wins\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_Dealer() {
        testBJ.resetGame();
        testBJ.winner = testBJ.dealer;
        testBJ.printWinner();
        String expected = "Dealer wins\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_NoOne() {
        testBJ.resetGame();
        testBJ.winner = new Player("No one", 0);
        testBJ.printWinner();
        String expected = "No one wins\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getCurrentBet() {
        testBJ.bet(10);
        Integer expected = 10;
        Integer actual = testBJ.getCurrentBet();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentBet_CurrentBet_Null() {
        assertNull(testBJ.getCurrentBet());
    }

    @Test
    void clearBets() {
        testBJ.bet(10);
        testBJ.clearBets();
        assertNull(testBJ.currentBet);
    }

    @Test
    void printPlayersHand() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, ACE));
        testBJ.printPlayersHand();
        String expected = "You have: ACE of HEARTS | ";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getScore() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, ACE));
        Integer expected = 1;
        Integer actual = testBJ.getScore(testBJ.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void hit() {
        testBJ.resetGame();
        testBJ.hit(testBJ.playersHand);
        Integer expected = 3;
        Integer actual = testBJ.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void isStillInPlay_Stand() {
        testBJ.resetGame();
        assertFalse(testBJ.isStillInPlay("Stand"));
    }

    @Test
    void isStillInPlay_Hit() {
        testBJ.resetGame();
        assertTrue(testBJ.isStillInPlay("Hit"));
    }

    @Test
    void getScoreWithAce_One() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, ACE));
        Integer expected = 11;
        Integer actual = testBJ.getScoreWithAce(testBJ.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void getScoreWithAce_Two() {
        testBJ.playersHand = new ArrayList<>();
        testBJ.playersHand.add(new Card(HEARTS, ACE));
        testBJ.playersHand.add(new Card(DIAMONDS, ACE));
        testBJ.playersHand.add(new Card(HEARTS, TWO));
        Integer expected = 14;
        Integer actual = testBJ.getScoreWithAce(testBJ.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void checkForNatural_Player() {

    }

    @Test
    void checkForNatural_Dealer() {

    }

    @Test
    void checkForNatural_BothAndNeither() {

    }

    @Test
    void checkHand() {

    }

    @Test
    void checkAce() {

    }

    @Test
    void checkTen() {

    }

    @Test
    void printIntroduction() {
    }

    @Test
    void getDealersPlay() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void getChoices() {
    }

    @Test
    void printChoices() {
    }
}