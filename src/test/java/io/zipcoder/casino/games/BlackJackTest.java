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

    private BlackJack testBlackJack;
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testBlackJack = new BlackJack(Card.getNewDeck());
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
        testBlackJack.printIntroduction();
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
        assertNull(testBlackJack.playersHand);
        testBlackJack.resetGame();
        assertNotNull(testBlackJack.playersHand);
    }

    @Test
    void resetGame_PlayersHand_Size() {
        testBlackJack.resetGame();
        Integer expected = 2;
        Integer actual = testBlackJack.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void resetGame_DealersHand_NotNull() {
        assertNull(testBlackJack.dealersHand);
        testBlackJack.resetGame();
        assertNotNull(testBlackJack.dealersHand);
    }

    @Test
    void resetGame_DealersHand_Size() {
        testBlackJack.resetGame();
        Integer expected = 2;
        Integer actual = testBlackJack.dealersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void resetGame_CurrentBet_Null() {
        testBlackJack.bet(10);
        testBlackJack.resetGame();
        assertNull(testBlackJack.currentBet);
    }

    @Test
    void resetGame_Winner_Null() {
        testBlackJack.winner = testPlayer;
        testBlackJack.resetGame();
        assertNull(testBlackJack.winner);
    }

    @Test
    void quitGame() {
        testBlackJack.quitGame();
        assertFalse(testBlackJack.playing);
    }

    @Test
    void getGameName() {
        String expected = "Black Jack";
        String actual = testBlackJack.getGameName();
        assertEquals(expected, actual);
    }

    @Test
    void startPlay() {
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(HEARTS, THREE)));
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, JACK), new Card(HEARTS, QUEEN),
            new Card(HEARTS, KING)));
        testBlackJack.playRound(true);
        String expected = "The dealer has one card face down and THREE of HEARTS.\nYou have: JACK of HEARTS | QUEEN " +
                              "of HEARTS | KING of HEARTS.\nYou bust!\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void isStillPlaying_NoInput() {
        testBlackJack.isPlaying("No");
        assertFalse(testBlackJack.playing);
    }

    @Test
    void isStillPlaying_YesInput() {
        testBlackJack.isPlaying("Yes");
        assertTrue(testBlackJack.playing);
    }

    @Test
    void dealCard() {
        testBlackJack.resetGame();
        testBlackJack.dealCard(testBlackJack.playersHand, 1);
        Integer expected = 3;
        Integer actual = testBlackJack.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void bet() {
        testBlackJack.bet(10);
        Integer expected = 10;
        Integer actual = testBlackJack.getCurrentBet();
        assertEquals(expected, actual);
    }

    @Test
    void stand() {
        testBlackJack.stand();
        String expected = "You stand.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void payout_Winner() {
        testBlackJack.resetGame();
        testBlackJack.winner = testPlayer;
        testBlackJack.payout(testPlayer, 100);
        Integer expected = 1100;
        Integer actual = testPlayer.getCurrentFunds();
        assertEquals(expected, actual);
    }

    @Test
    void payout_Winner_Print() {
        testBlackJack.resetGame();
        testBlackJack.winner = testPlayer;
        testBlackJack.payout(testPlayer, 100);
        String expected = "You won $100. Your current funds are $1100.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void payout_Loser() {
        testBlackJack.resetGame();
        testBlackJack.winner = testBlackJack.dealer;
        testBlackJack.payout(testPlayer, 100);
        Integer expected = 900;
        Integer actual = testPlayer.getCurrentFunds();
        assertEquals(expected, actual);
    }

    @Test
    void payout_Loser_Print() {
        testBlackJack.resetGame();
        testBlackJack.winner = testBlackJack.dealer;
        testBlackJack.payout(testPlayer, 100);
        String expected = "You lost $100. Your current funds are $900.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_Player() {
        testBlackJack.resetGame();
        testBlackJack.winner = testPlayer;
        testBlackJack.printWinner();
        String expected = "Lake wins.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_Dealer() {
        testBlackJack.resetGame();
        testBlackJack.winner = testBlackJack.dealer;
        testBlackJack.printWinner();
        String expected = "Dealer wins.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printWinner_NoOne() {
        testBlackJack.resetGame();
        testBlackJack.winner = new Player("No one", 0);
        testBlackJack.printWinner();
        String expected = "No one wins.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getCurrentBet() {
        testBlackJack.bet(10);
        Integer expected = 10;
        Integer actual = testBlackJack.getCurrentBet();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentBet_CurrentBet_Null() {
        assertNull(testBlackJack.getCurrentBet());
    }

    @Test
    void clearBets() {
        testBlackJack.bet(10);
        testBlackJack.clearBets();
        assertNull(testBlackJack.currentBet);
    }

    @Test
    void printPlayersHand() {
        testBlackJack.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, FOUR)));
        testBlackJack.printPlayersHand();
        String expected = "You have: FOUR of HEARTS.";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getScore() {
        testBlackJack.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, ACE)));
        Integer expected = 11;
        Integer actual = testBlackJack.getScore(testBlackJack.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void hit() {
        testBlackJack.resetGame();
        testBlackJack.hit(testBlackJack.playersHand);
        Integer expected = 3;
        Integer actual = testBlackJack.playersHand.size();
        assertEquals(expected, actual);
    }

    @Test
    void isStillInPlay_Stand() {
        testBlackJack.resetGame();
        assertFalse(testBlackJack.isStillInPlay("Stand"));
    }

    @Test
    void isStillInPlay_Hit() {
        testBlackJack.resetGame();
        assertTrue(testBlackJack.isStillInPlay("Hit"));
    }

    @Test
    void getScoreWithAce_One() {
        testBlackJack.playersHand = new ArrayList<>(Collections.singletonList(new Card(HEARTS, ACE)));
        Integer expected = 11;
        Integer actual = testBlackJack.getScoreWithAce(testBlackJack.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void getScoreWithAce_Two() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(DIAMONDS, ACE),
            new Card(HEARTS, TWO)));
        Integer expected = 14;
        Integer actual = testBlackJack.getScoreWithAce(testBlackJack.playersHand);
        assertEquals(expected, actual);
    }

    @Test
    void checkForNatural_Player() {
        testBlackJack.currentPlayer = testPlayer;
        testBlackJack.currentBet = 100;
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, SIX), new Card(HEARTS, NINE)));
        assertTrue(testBlackJack.checkForNatural());
        String expected = "You have: ACE of HEARTS | TEN of HEARTS.\nDealer has: SIX of DIAMONDS | NINE of HEARTS." +
                              "\n\nYou have Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertSame(testBlackJack.winner, testPlayer);
    }

    @Test
    void checkForNatural_Dealer() {
        testBlackJack.currentPlayer = testPlayer;
        testBlackJack.currentBet = 100;
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, SEVEN), new Card(HEARTS, NINE)));
        assertTrue(testBlackJack.checkForNatural());
        String expected = "You have: SEVEN of DIAMONDS | NINE of HEARTS.\nDealer has: ACE of HEARTS | TEN of HEARTS" +
                              ".\n\nDealer has Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertSame(testBlackJack.winner, testBlackJack.dealer);
    }

    @Test
    void checkForNatural_Both() {
        testBlackJack.currentPlayer = testPlayer;
        testBlackJack.currentBet = 100;
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, TEN)));
        assertTrue(testBlackJack.checkForNatural());
        String expected = "You have: ACE of DIAMONDS | TEN of DIAMONDS.\nDealer has: ACE of HEARTS | TEN of HEARTS." +
                              "\n\nYou and Dealer have Black Jack!\n";
        assertEquals(expected, outContent.toString());
        assertEquals(testBlackJack.winner, new Player("No one", 0));
    }

    @Test
    void checkForNatural_Neither() {
        testBlackJack.currentPlayer = testPlayer;
        testBlackJack.currentBet = 100;
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, NINE)));
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        assertFalse(testBlackJack.checkForNatural());
        assertNull(testBlackJack.winner);
    }

    @Test
    void checkHand() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        assertTrue(testBlackJack.checkHand(testBlackJack.playersHand));
    }

    @Test
    void checkAce() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, NINE), new Card(HEARTS, TEN)));
        assertFalse(testBlackJack.checkAce(testBlackJack.playersHand, 0));
        assertFalse(testBlackJack.checkAce(testBlackJack.playersHand, 1));
    }

    @Test
    void checkTen() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, TEN)));
        assertFalse(testBlackJack.checkTen(testBlackJack.playersHand, 0));
        assertTrue(testBlackJack.checkTen(testBlackJack.playersHand, 1));
    }

    @Test
    void getDealersPlay_Bust() {
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, JACK), new Card(HEARTS, TEN),
            new Card(HEARTS, NINE)));
        testBlackJack.getDealersPlay();
        String expected = "🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 ♠ ♥ ♦ ♣ 🂠 🂱 🂲 🂳 🂴 🂵 🂶 🂷 🂸 🂺 🂻 🂼 🂽 🂾 🃟 🂠 \nDealer bust.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void getWinner_Player() {
        testBlackJack.currentPlayer = testPlayer;
        testBlackJack.dealersHand = new ArrayList<>(Arrays.asList(new Card(HEARTS, ACE), new Card(HEARTS, EIGHT)));
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        testBlackJack.getWinner();
        assertSame(testBlackJack.winner, testPlayer);
    }

    @Test
    void getChoices_Hit() {
        String expected = "Hit.";
        String actual = testBlackJack.getChoices(10);
        assertEquals(expected, actual);
    }

    @Test
    void getChoices_HitAndStand() {
        String expected = "Hit | Stand.";
        String actual = testBlackJack.getChoices(12);
        assertEquals(expected, actual);
    }

    @Test
    void printChoices_Hit() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE)));
        testBlackJack.printChoices();
        String expected = "\nYou can: Hit.";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void printChoices_HitAndStand() {
        testBlackJack.playersHand = new ArrayList<>(Arrays.asList(new Card(DIAMONDS, ACE), new Card(DIAMONDS, NINE)));
        testBlackJack.printChoices();
        String expected = "\nYou can: Hit | Stand.";
        assertEquals(expected, outContent.toString());
    }
}