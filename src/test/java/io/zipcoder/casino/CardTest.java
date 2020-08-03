package io.zipcoder.casino;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.zipcoder.casino.Card.Rank.*;
import static io.zipcoder.casino.Card.Suit.CLUBS;
import static io.zipcoder.casino.Card.Suit.HEARTS;

public class CardTest {


    public static enum Rank {TWO, THREE, FOUR,
        FIVE, SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING, ACE}

    public static enum Suit {CLUBS, DIAMONDS,
        HEARTS, SPADES}

    Card.Rank rank;
    Card.Suit suit;
    Card card;

    @BeforeEach
    public void setUp() throws Exception {

       card = new Card(CLUBS, NINE);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    void testgetRank() {
        Card card = new Card(CLUBS,FOUR);
        card.getRank();
        Assert.assertEquals(FOUR,card.getRank());

    }
    @Test
    void testgetRank2() {
        Card card = new Card(CLUBS,FOUR);
        card.getRank();
        Assert.assertNotEquals(FIVE,card.getRank());

    }

    @Test
    void testgetSuit() {
        Card card = new Card(HEARTS,FIVE);
        card.getSuit();
        Assert.assertEquals(HEARTS,card.getSuit());
    }
    @Test
    void testgetSuit2() {
        Card card = new Card(HEARTS,FIVE);
        card.getSuit();
        Assert.assertNotEquals(CLUBS,card.getSuit());
    }

    @Test
    void testNewDeck() {
        Card card = new Card(suit,rank);
        List<Card> testNewDeck = new ArrayList<>();
        testNewDeck =card.getNewDeck();
        Assert.assertNotNull(testNewDeck);
    }
    @Test
    void testNewDeck2() {
        Card card = new Card(suit,rank);
        List<Card> testNewDeck1 = new ArrayList<>();
        testNewDeck1 =card.getNewDeck();
        Assert.assertEquals(52, testNewDeck1.size());
    }


    @Test
    void lookupRank() {
       Boolean result = Card.lookupRank("EIGHT");
       Assert.assertTrue(result);
    }

    @Test
    void lookupRankNotPresent() {
        Boolean result = Card.lookupRank("ELEVEN");
        Assert.assertFalse(result);
    }

    @Test
    void lookupSuitNotPresent() {
        Boolean result = Card.lookupSuit("CLUE");
        Assert.assertFalse(result);
    }

    @Test
    void lookupSuit() {
        Boolean result = Card.lookupSuit("CLUBS");
        Assert.assertTrue(result);
    }

    @Test
    void testToString() {
        Card card = new Card(CLUBS, NINE);
        String result = card.toString();
        Assert.assertNotNull(result);
    }

    @Test
    void testEquals() {

        Card compared = new Card(CLUBS, NINE);
        Boolean result = card.equals(compared);
        Assert.assertTrue(result);

        Card comparedNot = new Card(HEARTS, NINE);
        Boolean resultNot = card.equals(comparedNot);
        Assert.assertFalse(resultNot);
    }

    @Test
    void testHashCode() {
        int hashCode = card.hashCode();
        Assert.assertNotNull(Integer.valueOf(hashCode));
    }

}