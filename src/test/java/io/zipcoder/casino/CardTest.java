package io.zipcoder.casino;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.zipcoder.casino.Card.Rank.FIVE;
import static io.zipcoder.casino.Card.Rank.FOUR;
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
    @Before
    public void setUp() throws Exception {

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
    void testgetSuit() {
        Card card = new Card(HEARTS,FIVE);
        card.getSuit();
        Assert.assertEquals(HEARTS,card.getSuit());
    }

    @Test
    void testNewDeck() {
        Card card = new Card(suit,rank);
        List<Card> testNewDeck = new ArrayList<>();
        testNewDeck =card.getNewDeck();
        Assert.assertEquals(52, testNewDeck.size());
        Assert.assertNotNull(testNewDeck);
    }
}