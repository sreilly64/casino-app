package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.List;

import static io.zipcoder.casino.Card.Rank.ACE;
public class BlackJack extends CardGame implements GamblingGame{
    public static String gameName = "Black Jack";
    Integer currentBet;
    Integer miniBet;
    ArrayList<Card> dealersHand;
    ArrayList<Card> playersHand;
    Player currentPlayer;
    Player dealer = new Player("Dealer", 0);
    Player winner;
    Boolean playing;

    public BlackJack(List<Card> currentDeck) {
        super(currentDeck);
        playing = true;
    }

    void printIntroduction() {
        console.println(
            "Welcome to Going to Boston!\nHow to play: Each player has three turns.\nEach turn, six-sided dice are " +
                "rolled and the number of the highest \ndie is added to the player's total.\nOn the first turn, the " +
                "player rolls three dice. On the second turn, the player rolls two and on the last turn, \nthe " +
                "player rolls only one.\nThe player with the highest total wins!\n< | > | < > | < | > | < > | < | > | < > | < | > | < >");
    }

    @Override
    public void resetGame() {
        playersHand = new ArrayList<>();
        dealersHand = new ArrayList<>();
        clearBets();
        dealCard(playersHand, 2);
        dealCard(dealersHand, 1);
    }

    int getValidBet(Integer userInput) {
        while(!(userInput >= 2 && userInput <= 500) && currentPlayer.getCurrentFunds() > userInput) {
            userInput = console.getIntegerInput("Enter a valid bet.");
        }
        return userInput;
    }

    @Override
    public void bet(Integer amount) {
        this.currentBet = amount;
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
        boolean inPlay = true;
        while (playing) {
            resetGame();
            int userInt = console.getIntegerInput("You can bet a minimum of $2 and a maximum of $500. How much would " +
                                                        "you like to bet?");
            bet(getValidBet(userInt));
            startPlay(inPlay);
            getDealersPlay();
            getWinner();
            payout(player, currentBet);

            String userInput = console.getStringInput("Play again? <Yes> | <No>");
            isStillPlaying(userInput);

        }
    }

    void isStillPlaying(String userInput) {
        while(true) {
            if (userInput.equalsIgnoreCase("Yes")) {
                break;
            } else if (userInput.equalsIgnoreCase("No")) {
                quitGame();
                break;
            } else {
                console.println("We didn't quite catch that.");
                userInput = console.getStringInput("Play again? <Yes> | <No>");
            }
        }
    }

    public void startPlay(boolean inPlay) {
        while (inPlay) {
            printPlayersHand();
            if (getScore(playersHand) > 21) {
                console.println("\nYou bust!");
                inPlay = false;
            } else {
                inPlay = getNextMove();
            }
        }
    }

    void printPlayersHand() {
        console.print("You have: ");
        for (Card card : playersHand) {
            console.print(card.toString()+" | ");
        }
    }

    Boolean getNextMove() {
        printChoices();
        String userInput = console.getStringInput("\nWhat would you like to do?");
        return isStillInPlay(userInput);
    }

    Boolean isStillInPlay(String userInput) {
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

    void printChoices() {
        console.print("\nYou can: ");
        for (String choice : getChoices(getScore(playersHand))) {
            console.print(choice+" | ");
        }
    }

    List<String> getChoices(Integer currentScore) {
        List<String> choices = new ArrayList<>();
        if (currentScore <= 21) {
            choices.add("Hit");
            choices.add("Stand");
        } //else if () { }
        return choices;
    }

    Integer getScore(ArrayList<Card> hand) {
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

    void dealCard(ArrayList<Card> hand, Integer numOfCards) {
        for(int i = 0; i < numOfCards; i++) {
            hand.add(drawTopCard());
        }
    }

    void hit(ArrayList<Card> hand) {
        console.println("You hit.");
        dealCard(hand, 1);
    }

    void stand() {
        console.println("You stand.");
    }

    @Override
    public void getWinner() {
        int playerScore = getScoreWithAce(playersHand);
        int dealerScore = getScoreWithAce(dealersHand);
        if (playerScore > 21) {

        }

    }

    void printWinner() {
        console.println(winner.getName()+" wins");
    }

    Integer getScoreWithAce(ArrayList<Card> hand) {
        int score = getScore(hand);
        for (Card card : hand) {
            if (card.getRank() == ACE) {
                int scoreWithAce = score + 10;
                if (scoreWithAce > score && scoreWithAce <= 21) {
                    score = scoreWithAce;
                }
            }
        }
        return score;
    }

    @Override
    public void payout(Player player, Integer amount) {
        if (winner == player) {
            player.addToCurrentFunds(amount);
        } else {
            player.subtractFromCurrentFunds(amount);
        }
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
    void dealCards(Integer numOfCards) {}

    Boolean checkDealerForBlackJack() {
        return false;
    }

    void getDealersPlay() {
        if (getScore(dealersHand) < 16) {
            hit(dealersHand);
        }
    }
}
