package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.utilities.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CardGame implements Game {
    Console console;
    List<Card> currentDeck;

    public CardGame(List<Card> currentDeck) {
        this.console = new Console(System.in, System.out);
        this.currentDeck = currentDeck;
    }

     public Card drawTopCard() {
         Card topCard=null;
         if(currentDeck != null && !currentDeck.isEmpty()){
            topCard = currentDeck.remove(0);
         }
         return topCard;
     }

     abstract void dealCards(Integer numOfCards);

     abstract public void getWinner();

}
