package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static io.zipcoder.casino.Card.Rank.*;
import static io.zipcoder.casino.Card.Suit.DIAMONDS;
import static io.zipcoder.casino.Card.Suit.HEARTS;
import static org.junit.Assert.*;
class BlackJackTest {
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
    void printIntroduction() {
        testBJ.printIntroduction();
        String expected = "🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 ♠ ♥ ♦ ♣ 🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 \n"+
                              "                    Welcome to the Black Jack table!                  \n"+
                              "🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 ♠ ♥ ♦ ♣ 🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 \n"+
                              "                             How to play:                              \n"+
                              "  🂱 The player must enter an amount to bet.\n"+
                              "  🂲 Both the player and Dealer are dealt two cards to start.\n"+
                              "  🂳 On their turn, the player can \"Hit\" to add a card to their hand\n"+
                              "    or \"Stand\" if the total points in their hand are greater than ten.\n"+
                              "  🂴 Dealer takes their turn afterwards. \n"+
                              "  🂵 The hand score closest to 21 without a \"Bust\" (a score higher than 21)\n"+
                              "    wins and takes the player's bet.\n";
        assertEquals(expected, outContent.toString());
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
        Integer expected = 2;
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
    void resetGame_Winner_Null() {
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
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(HEARTS, THREE)));
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, JACK), new Card(HEARTS, QUEEN),
            new Card(HEARTS, KING)));
        testBJ.playRound(true);
        String expected = "The dealer has one card faced down and THREE of HEARTS.\nYou have: JACK of HEARTS | QUEEN " +
                              "of HEARTS | KING of HEARTS.\nYou bust!\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void isStillPlaying_NoInput() {
        testBJ.isPlaying("No");
        assertFalse(testBJ.playing);
    }

    @Test
    void isStillPlaying_YesInput() {
        testBJ.isPlaying("Yes");
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
    void payout_Winner_Print() {
        testBJ.resetGame();
        testBJ.winner = testPlayer;
        testBJ.payout(testPlayer, 100);
        String expected = "You won $100. Your current funds are $1100.\n";
        assertEquals(expected, outContent.toString());
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
    void payout_Loser_Print() {
        testBJ.resetGame();
        testBJ.winner = testBJ.dealer;
        testBJ.payout(testPlayer, 100);
        String expected = "You lost $100. Your current funds are $900.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_Player() {
        testBJ.resetGame();
        testBJ.winner = testPlayer;
        testBJ.printWinner();
        String expected = "Lake wins.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_Dealer() {
        testBJ.resetGame();
        testBJ.winner = testBJ.dealer;
        testBJ.printWinner();
        String expected = "Dealer wins.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_NoOne() {
        testBJ.resetGame();
        testBJ.winner = new Player("No one", 0);
        testBJ.printWinner();
        String expected = "No one wins.\n";
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
        testBJ.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, FOUR)));
        testBJ.printPlayersHand();
        String expected = "You have: FOUR of HEARTS.";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getScore() {
        testBJ.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, ACE)));
        Integer expected = 11;
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
        testBJ.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, ACE)));
        Integer expected = 11;
        Integer actual = testBJ.getScoreWithAce(testBJ.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void getScoreWithAce_Two() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(DIAMONDS, ACE),
            new Card(HEARTS, TWO)));
        Integer expected = 14;
        Integer actual = testBJ.getScoreWithAce(testBJ.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void checkForNatural_Player() {
        testBJ.currentPlayer = testPlayer;
        testBJ.currentBet = 100;
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, SIX), new Card(HEARTS, NINE)));
        assertTrue(testBJ.checkForNatural());
        String expected = "You have: ACE of HEARTS | TEN of HEARTS.\nDealer has: SIX of DIAMONDS | NINE of HEARTS." +
                              "\nYou have Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertSame(testBJ.winner, testPlayer);
    }

    @Test
    void checkForNatural_Dealer() {
        testBJ.currentPlayer = testPlayer;
        testBJ.currentBet = 100;
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, SEVEN), new Card(HEARTS, NINE)));
        assertTrue(testBJ.checkForNatural());
        String expected = "You have: SEVEN of DIAMONDS | NINE of HEARTS.\nDealer has: ACE of HEARTS | TEN of HEARTS" +
                              ".\nDealer has Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertSame(testBJ.winner, testBJ.dealer);
    }

    @Test
    void checkForNatural_Both() {
        testBJ.currentPlayer = testPlayer;
        testBJ.currentBet = 100;
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, TEN)));
        assertTrue(testBJ.checkForNatural());
        String expected = "You have: ACE of DIAMONDS | TEN of DIAMONDS.\nDealer has: ACE of HEARTS | TEN of HEARTS." +
                              "\nYou and Dealer have Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertEquals(testBJ.winner, new Player("No one", 0));
    }

    @Test
    void checkForNatural_Neither() {
        testBJ.currentPlayer = testPlayer;
        testBJ.currentBet = 100;
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, NINE)));
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        assertFalse(testBJ.checkForNatural());
        assertNull(testBJ.winner);
    }

    @Test
    void checkHand() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        assertTrue(testBJ.checkHand(testBJ.playersHand));
    }

    @Test
    void checkAce() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, NINE), new Card(HEARTS, TEN)));
        assertFalse(testBJ.checkAce(testBJ.playersHand, 0));
        assertFalse(testBJ.checkAce(testBJ.playersHand, 1));
    }

    @Test
    void checkTen() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        assertFalse(testBJ.checkTen(testBJ.playersHand, 0));
        assertTrue(testBJ.checkTen(testBJ.playersHand, 1));
    }

    @Test
    void getDealersPlay_Bust() {
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, JACK), new Card(HEARTS, TEN),
            new Card(HEARTS, NINE)));
        testBJ.getDealersPlay();
        String expected = "🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 ♠ ♥ ♦ ♣ 🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 \nDealer bust.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getWinner_Player() {
        testBJ.currentPlayer = testPlayer;
        testBJ.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, EIGHT)));
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        testBJ.getWinner();
        assertSame(testBJ.winner, testPlayer);
    }

    @Test
    void getChoices_Hit() {
        String expected = "Hit.";
        String actual = testBJ.getChoices(10);
        assertEquals(expected, actual);
    }

    @Test
    void getChoices_HitAndStand() {
        String expected = "Hit | Stand.";
        String actual = testBJ.getChoices(12);
        assertEquals(expected, actual);
    }

    @Test
    void printChoices_Hit() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE)));
        testBJ.printChoices();
        String expected = "\nYou can: Hit.";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printChoices_HitAndStand() {
        testBJ.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        testBJ.printChoices();
        String expected = "\nYou can: Hit | Stand.";
        assertEquals(expected, outContent.toString());
    }
}