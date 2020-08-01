package io.zipcoder.casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return "Card{" +
                "rank=" + rank.name() +
                ", suit=" + suit.name() +
                '}';
    }
}
