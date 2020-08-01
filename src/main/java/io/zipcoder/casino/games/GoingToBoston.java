package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;
public class GoingToBoston extends DiceGame{
    public static String gameName = "Going to Boston";
    int[] highestRolls;
    Map<Player, Integer> nPCScores;
    String[] nPCNames = new String[]{"Jyothi", "Lake", "Shane"};
    Player currentPlayer;
    int playerScore;
    Boolean playing;

    public GoingToBoston() {
        super();
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
        int userInput = console.getIntegerInput("Would you like to face 1, 2, or 3 opponents?");
        int numOfOpponents = getNumberOfOpponents(userInput);
        createNPCs(numOfOpponents);
    }

    int getNumberOfOpponents(Integer userInput) {
        while(!(userInput >= 1 && userInput <= 3)) {
            userInput = console.getIntegerInput("Choose 1, 2, or 3 opponents to face.");
        }
        return userInput;
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
        printIntroduction();
        while (playing) {
            playGame();
            String userInput = console.getStringInput("Play again? <Yes> | <No>");
            isStillPlaying(userInput);
        }
    }

    void playGame() {
        resetGame();
        playerScore = playRound(currentPlayer);
        playNPCRound();
        printScores();
        findWinner();
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

    void createNPCs(Integer numOfNPCs) {
        nPCScores = new LinkedHashMap<>(numOfNPCs);
        for (int i = 0; i < numOfNPCs; i++) {
            nPCScores.put(new Player(nPCNames[i], 0), null);
        }
    }

    int playRound(@NotNull Player player) {
        highestRolls = new int[3];
        int numOfDie = 3;
        String name = player.equals(currentPlayer) ? "You" : player.getName();
        getRolls(numOfDie, name);
        return IntStream.of(highestRolls).sum();
    }

    void getRolls(int numOfDie, String name) {
        console.println("< | > | < > | < | > | < > | < | > | < > | < | > | < >");
        for (int i = 0; i < highestRolls.length; i++) {
            ArrayList<Integer> rolls = rollDice(numOfDie);
            printRolls(numOfDie, name, rolls);
            addHighestRoll(i, rolls);
            numOfDie--;
        }
    }

    void printRolls(int numOfDie, String name, ArrayList<Integer> rolls) {
        console.print("Roll "+ numOfDie +" | "+ name +" rolled");
        for (Integer roll : rolls) {
            console.print(" "+roll);
        }
    }

    void addHighestRoll(int i, ArrayList<Integer> rolls) {
        int highest = Collections.max(rolls);
        console.println(" | "+highest+" is the highest.");
        highestRolls[i] = highest;
    }

    void playNPCRound() {
        Set<Player> players = nPCScores.keySet();
        for (Player nPC : players) {
            nPCScores.put(nPC, playRound(nPC));
        }
    }

    void printScores() {
        console.println("< | > | < > | < | > | < > | < | > | < > | < | > | < >");
        console.println("You scored "+playerScore+". ");
        nPCScores.forEach ((npc, score) -> console.println(npc.getName()+" scored "+score+". "));
    }

    void findWinner() {
        Player nPC = Collections.max(nPCScores.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        printWinner(nPC);
    }

    void printWinner(Player nPC) {
        if (playerScore > nPCScores.get(nPC)) {
            console.println("You won!");
        } else if (playerScore == nPCScores.get(nPC)) {
            console.println("You tied!");
        } else {
            console.println("You lost...");
        }
    }
}
