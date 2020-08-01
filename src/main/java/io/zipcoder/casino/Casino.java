package io.zipcoder.casino;

import io.zipcoder.casino.games.BlackJack;
import io.zipcoder.casino.utilities.Console;

import java.util.ArrayList;
import java.util.List;

public class Casino {
//    static Console console = new Console(System.in, System.out);
//    private static ArrayList<Card> playersHand= new ArrayList<>();
//    private static List<Card> testDeck = Card.getNewDeck();
//
//    private static Integer getScore(ArrayList<Card> hand) {
//        int currentScore = 0;
//        for (Card card : hand) {
//            switch(card.getRank()) {
//                case ACE:
//                    currentScore+=1;
//                    break;
//                case TWO:
//                    currentScore+=2;
//                    break;
//                case THREE:
//                    currentScore+=3;
//                    break;
//                case FOUR:
//                    currentScore+=4;
//                    break;
//                case FIVE:
//                    currentScore+=5;
//                    break;
//                case SIX:
//                    currentScore+=6;
//                    break;
//                case SEVEN:
//                    currentScore+=7;
//                    break;
//                case EIGHT:
//                    currentScore+=8;
//                    break;
//                case NINE:
//                    currentScore+=9;
//                    break;
//                case TEN:
//                case JACK:
//                case QUEEN:
//                case KING:
//                    currentScore+=10;
//                    break;
//            }
//        }
//        return currentScore;
//    }
//
//    private static void dealCard(ArrayList<Card> hand, Integer numOfCards) {
//        for(int i = 0; i < numOfCards; i++) {
//            hand.add(drawTopCard());
//        }
//    }
//
//    public static Card drawTopCard() {
//        Card topCard=null;
//        if(testDeck != null && !testDeck.isEmpty()){
//            topCard = testDeck.remove(0);
//        }
//        return topCard;
//    }
//
//    private static void getCurrentHand() {
//        console.print("You have:");
//        for (Card card : playersHand) {
//            console.print(" "+card.toString());
//        }
//    }

    public static void main(String[] args) {
        // write your tests before you start
        //CasinoDriver test = new CasinoDriver();
//        dealCard(playersHand, 2);
//        getCurrentHand();
//        console.print("\n"+getScore(playersHand).toString());
        BlackJack bj = new BlackJack(Card.getNewDeck());
        boolean inPlay = true;
        bj.resetGame();
        bj.startPlay(inPlay);
        //bj.getWinner();
    }
}
