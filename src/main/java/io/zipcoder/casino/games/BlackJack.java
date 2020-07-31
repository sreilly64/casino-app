package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.List;

import static io.zipcoder.casino.Card.Rank.ACE;
public class BlackJack extends CardGame implements GamblingGame{
    public static String gameName = "Black Jack";
    private Integer currentBet;
    Integer miniBet;
    private ArrayList<Card> dealersHand;
    private ArrayList<Card> playersHand;
    private Player currentPlayer;
    private Boolean playing;

    public BlackJack(List<Card> currentDeck) {
        super(currentDeck);
    }

    @Override
    public void resetGame() {
        playersHand = new ArrayList<>();
        dealersHand = new ArrayList<>();
        clearBets();
        bet();

        dealCard(playersHand, 2);
        dealCard(dealersHand, 1);
    }

    @Override
    public void quitGame() {
        playing = false;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public void startGame(Player player) {
        currentPlayer = player;
        playing = true;
        boolean inPlay = true;
        boolean stillPlaying = true;
        while (playing) {
            resetGame();
            startPlay(inPlay);
            getWinner();


            String userInput = console.getStringInput("Play again? <Yes> | <No>");
            while(stillPlaying) {
                if (userInput.equals("Yes")) {
                    stillPlaying = false;
                } else if (userInput.equals("No")) {
                    quitGame();
                    stillPlaying = false;
                } else {
                    console.println("We didn't quite catch that.");
                    userInput = console.getStringInput("Play again? <Yes> | <No>");
                }
            }

        }
    }

    public Boolean startPlay(boolean inPlay) {
        while (inPlay) {
            getCurrentHand();
            if (getScore(playersHand) > 21) {
                console.println("You bust!");
                inPlay = false;
            } else {
                inPlay = getNextMove();
            }
        }
        return false;
    }

    private void getCurrentHand() {
        console.print("You have: ");
        for (Card card : playersHand) {
            console.print(card.toString()+" | ");
        }
    }

    public void dealCard(ArrayList<Card> hand, Integer numOfCards) {
        for(int i = 0; i < numOfCards; i++) {
            hand.add(drawTopCard());
        }
    }

    private Boolean getNextMove() {
        console.print("\nYou can: ");
        for (String choice : getChoices(getScore(playersHand))) {
            console.print(choice+" | ");
        }
        String userInput = console.getStringInput("\nWhat would you like to do?");
        switch(userInput) {
            case "Hit":
                hit(playersHand);
                break;
            case "Stand":
                stand();
                return false;
        }
        return true;
    }

    private List<String> getChoices(Integer currentScore) {
        List<String> choices = new ArrayList<>();
        if (currentScore <= 21) {
            choices.add("Hit");
            choices.add("Stand");
        } //else if () { }
        return choices;
    }

    @Override
    public void bet() {
        int userInput = console.getIntegerInput("You can bet a minimum of $2 and a maximum of $500. How much would " +
                                                    "you like to bet?");
        while(!(userInput >= 2 && userInput <= 500)) {
            userInput = console.getIntegerInput("Enter a valid bet.");
        }
        this.currentBet = userInput;
    }

    private Integer getScore(ArrayList<Card> hand) {
        int currentScore = 0;
        for (Card card : hand) {
            switch(card.getRank()) {
                case ACE:
                    currentScore+=1;
                    break;
                case TWO:
                    currentScore+=2;
                    break;
                case THREE:
                    currentScore+=3;
                    break;
                case FOUR:
                    currentScore+=4;
                    break;
                case FIVE:
                    currentScore+=5;
                    break;
                case SIX:
                    currentScore+=6;
                    break;
                case SEVEN:
                    currentScore+=7;
                    break;
                case EIGHT:
                    currentScore+=8;
                    break;
                case NINE:
                    currentScore+=9;
                    break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    currentScore+=10;
                    break;
            }

        }
    return currentScore;
    }

    private void hit(ArrayList<Card> hand) {
        console.println("You hit.");
        dealCard(hand, 1);
    }

    public void stand() {
        console.println("You stand.");
    }

    public Boolean checkDealerForBlackJack() {
        return false;
    }

    public void getDealersPlay() {

    }

    @Override
    public void getWinner() {
        int playerScore = getScore(playersHand);
        if (playersHand.contains(ACE)) {
            int playerScoreWithAce = getScore(playersHand) + 10;
            if (playerScoreWithAce > playerScore && playerScoreWithAce <= 21) {
                playerScore = playerScoreWithAce;
            }
        }


    }

    @Override
    public void payout(Player player, Integer amount) {

    }

    @Override
    public Integer getCurrentBet() {
        return currentBet;
    }

    @Override
    public void clearBets() {
        currentBet = null;
    }

    @Override
    public void bet(Integer amount) {}
    @Override
    void dealCards(Integer numOfCards) {}
}
