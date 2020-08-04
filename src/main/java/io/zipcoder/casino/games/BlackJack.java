package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.zipcoder.casino.Card.Rank.ACE;
public class BlackJack extends CardGame implements GamblingGame{
    public static final String gameName = "Black Jack";
    Integer currentBet;
    ArrayList<Card> dealersHand;
    ArrayList<Card> playersHand;
    Player currentPlayer;
    Player dealer = new Player("Dealer", 0);
    Player winner;
    Boolean playing;
    String heartsCardUnicode = "\uD83C\uDCA0 \uD83C\uDCB1 \uD83C\uDCB2 \uD83C\uDCB3 \uD83C\uDCB4 \uD83C\uDCB5 \uD83C\uDCB6 \uD83C\uDCB7 " +
                                   "\uD83C\uDCB8 \uD83C\uDCBA \uD83C\uDCBB \uD83C\uDCBC \uD83C\uDCBD \uD83C\uDCBE \uD83C\uDCDF \uD83C\uDCA0 ";
    String heartsCardBorder = IntStream.range(0, 2).mapToObj(i -> heartsCardUnicode).collect(Collectors.joining(
        "\u2660 \u2665 \u2666 \u2663 "));

    public BlackJack(List<Card> currentDeck) {
        super(currentDeck);
        playing = true;
    }

    void printIntroduction() {
        console.print(heartsCardBorder+
                            "\n                    Welcome to the Black Jack table!                  \n" + //20,19
                            heartsCardBorder+
                            "\n                             How to play:                              "+ //29,30
                            "\n  \uD83C\uDCB1 The player must enter an amount to bet."+
                            "\n  \uD83C\uDCB2 Both the player and Dealer are dealt two cards to start."+
                            "\n  \uD83C\uDCB3 On their turn, the player can \"Hit\" to add a card to their hand" +
                            "\n    or \"Stand\" if the total points in their hand are greater than ten."+
                            "\n  \uD83C\uDCB4 Dealer takes their turn afterwards. " +
                            "\n  \uD83C\uDCB5 The hand score closest to 21 without a \"Bust\" (a score higher than " +
                            "21)" +
                            "\n    wins and takes the player's bet.\n");
    }

    @Override
    public void resetGame() {
        playersHand = new ArrayList<>();
        dealersHand = new ArrayList<>();
        winner = null;
        clearBets();
        dealCard(playersHand, 2);
        dealCard(dealersHand, 2);
    }

    Integer getValidBet(Integer userInt) {
        while(!(userInt >= 2 && userInt <= 500) || (userInt > currentPlayer.getCurrentFunds())) {
            userInt = console.getIntegerInput("Enter a valid bet.");
        }
        return userInt;
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
        printIntroduction();
        while (playing) {
            doWhilePlaying(inPlay);
        }
    }

    void doWhilePlaying(boolean inPlay) {
        resetGame();
        console.println(heartsCardBorder);
        int userInt = console.getIntegerInput("Your current funds are $"+currentPlayer.getCurrentFunds()+"."
        + " You can bet a minimum of $2 and a maximum of $500.\nHow much would you like to bet?");
        startRound(currentPlayer, inPlay, userInt);
        String userInput = console.getStringInput("Play again? Yes | No");
        isPlaying(userInput);
    }

    void startRound(Player player, boolean inPlay, int userInt) {
        bet(getValidBet(userInt));
        console.println(heartsCardBorder);
        if(!checkForNatural()) {
            playRound(inPlay);
            if (getScoreWithAce(playersHand)<=21) {getDealersPlay();}
            getWinner();
        }
        printWinner();
        payout(player, currentBet);
    }

    void isPlaying(String userInput) {
        while(true) {
            if (userInput.equalsIgnoreCase("Yes")) {
                break;
            } else if (userInput.equalsIgnoreCase("No")) {
                quitGame();
                break;
            } else {
                console.println("We didn't quite catch that.");
                userInput = console.getStringInput("Play again? Yes | No");
            }
        }
    }

    void playRound(boolean inPlay) {
        console.println("The dealer has one card face down and "+dealersHand.get(1).toString()+".");
        while (inPlay) {
            printPlayersHand();
            if (getScoreWithAce(playersHand) > 21) {
                console.println("\nYou bust!");
                inPlay = false;
            } else {inPlay = getNextMove();}
        }
    }

    void printPlayersHand() {
        console.print("You have: ");
        String[] cards = new String[playersHand.size()];
        int index = 0;
        for (Card card : playersHand) {
            cards[index] = card.toString();
            index++;
        }
        console.print(playersHand.stream().map(String::valueOf).collect(Collectors.joining(" | "))+".");
    }

    Boolean getNextMove() {
        printChoices();
        String userInput = console.getStringInput("\nWhat would you like to do?");
        return isStillInPlay(userInput);
    }

    Boolean isStillInPlay(String userInput) {
        switch(userInput) {
            case "Hit":
            case "hit":
                hit(playersHand);
                break;
            case "Stand":
            case "stand":
                stand();
                return false;
            default:
        }
        return true;
    }

    void printChoices() {
        console.print("\nYou can: "+getChoices(getScoreWithAce(playersHand)));
    }

    String getChoices(Integer currentScore) {
        if (currentScore <= 11) {
            return "Hit.";
        } else if (currentScore <= 21) {
            return "Hit | Stand.";
        }
        return null;
    }

    Integer getScore(ArrayList<Card> hand) {
        int currentScore = 0;
        for (Card card : hand) {
            switch(card.getRank()) {
                case ACE:
                    currentScore+=11;
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
        console.println(heartsCardBorder);
        int playerScore = getScoreWithAce(playersHand);
        int dealerScore = getScoreWithAce(dealersHand);
        setWinner(playerScore, dealerScore);
    }

    void setWinner(int playerScore, int dealerScore) {
        printBothHands();
        if (playerScore > 21) {
            winner = dealer;
        } else if (dealerScore > 21) {
            winner = currentPlayer;
        } else if (playerScore > dealerScore) {
            winner = currentPlayer;
        } else if (playerScore < dealerScore) {
            winner = dealer;
        } else {
            winner = new Player("No one", 0);
        }
    }

    void printWinner() {
        console.println(winner.getName()+" wins.");
    }

    Integer getScoreWithAce(ArrayList<Card> hand) {
        int score = getScore(hand);
        for (Card card : hand) {
            if (card.getRank() == ACE) {
                score = processScoreWithAce(score);
            }
        }
        return score;
    }

    Integer processScoreWithAce(int score) {
        int scoreWithAce = score - 10;
        if (score > 21 && scoreWithAce <= 21) {
            score = scoreWithAce;
        }
        return score;
    }

    @Override
    public void payout(Player player, Integer amount) {
        if (winner == player) {
            player.addToCurrentFunds(amount);
            console.println("You won $"+amount+". Your current funds are $"+player.getCurrentFunds()+".");
        } else if (winner == dealer) {
            player.subtractFromCurrentFunds(amount);
            console.println("You lost $"+amount+". Your current funds are $"+player.getCurrentFunds()+".");
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

    void getDealersPlay() {
        console.println(heartsCardBorder);
        int index = 2;
        getDealerHit(index);
        if (getScore(dealersHand) > 21) {
            console.println(dealer.getName()+" bust.");
        } else if (getScore(dealersHand) >= 17) {
            console.println(dealer.getName()+" stands.");
        }
    }

    void getDealerHit(int index) {
        while (getScore(dealersHand) < 17) {
            dealCard(dealersHand, 1);
            console.println(dealer.getName()+" hit and drew "+dealersHand.get(index).toString()+".");
            index++;
        }

    }

    Boolean checkForNatural() {
        if (checkHand(playersHand) && !checkHand(dealersHand)) {
            return checkForPlayerNatural();
        } else if (!checkHand(playersHand) && checkHand(dealersHand)) {
            return checkForDealerNatural();
        } else if (checkHand(playersHand) && checkHand(dealersHand)) {
            return checkForBothNatural();
        } else {
            return false;
        }
    }

    Boolean checkForPlayerNatural() {
        winner = currentPlayer;
        currentBet = currentBet * 2;
        printBothHands();
        console.println("\nYou have Black Jack!");
        return true;
    }

    Boolean checkForDealerNatural() {
        winner = dealer;
        printBothHands();
        console.println("\n"+dealer.getName()+" has Black Jack!");
        return true;
    }

    Boolean checkForBothNatural() {
        winner = new Player("No one", 0);
        printBothHands();
        console.println("\n"+"You and "+dealer.getName()+" have Black Jack!");
        return true;
    }

    void printDealersHand() {
        console.print("\n" + dealer.getName() + " has: ");
        String[] cards = new String[dealersHand.size()];
        int index = 0;
        for (Card card : dealersHand) {
            cards[index] = card.toString();
            index++;
        }
        console.println(dealersHand.stream().map(String::valueOf).collect(Collectors.joining(" | "))+".");
    }

    Boolean checkHand(ArrayList<Card> hand) {
        return checkAce(hand, 0) && checkTen(hand, 1) ||
                checkAce(hand, 1) && checkTen(hand, 0);
    }

    Boolean checkAce(ArrayList<Card> hand, Integer index) {
        return hand.get(index).getRank() == ACE;
    }

    Boolean checkTen(ArrayList<Card> hand, Integer index) {
        switch(hand.get(index).getRank()) {
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                return true;
            default:
                return false;
        }
    }

    void printBothHands() {
        printPlayersHand();
        printDealersHand();
    }

    @Override
    void dealCards(Integer numOfCards) {}
}
