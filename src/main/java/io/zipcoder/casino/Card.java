package io.zipcoder.casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Card {

    public static enum Rank {TWO, THREE, FOUR,
        FIVE, SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING, ACE}

    public static enum Suit {CLUBS, DIAMONDS,
        HEARTS, SPADES}

    public final Rank rank;
    public final Suit suit;

    public Card(Suit s, Rank r) {
        suit= s;
        rank= r;
    }


    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    static public Boolean lookupRank(String rank) {
        try {
            Card.Rank.valueOf(rank);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    static public Boolean lookupSuit(String suit) {
        try {
            Card.Suit.valueOf(suit);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static List<Card> getNewDeck(){
        List<Card> newDeck = new ArrayList<Card>();
        for(Suit s : Suit.values()){
            for(Rank r : Rank.values()){
                newDeck.add(new Card(s,r));
                Collections.shuffle(newDeck);
            }
        }
        return newDeck;
    }

    @Override
    public String toString() {
        return "Card [" +
                "rank=" + rank.name() +
                ", suit=" + suit.name() +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
