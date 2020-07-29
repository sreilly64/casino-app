package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.*;
import java.util.stream.IntStream;
public class GoingToBoston extends DiceGame{
    public static String gameName = "Going to Boston";
    int[] highestRolls;
    Map<Player, Integer> nPCScores;
    Player currentPlayer;
    Boolean playing;

    public GoingToBoston() {
        super();
    }

    @Override
    public void resetGame() {
        int userInput = console.getIntegerInput("Do you want to face 1, 2, or 3 opponents?");
        while(!(userInput >= 1 && userInput <= 3)) {
            userInput = console.getIntegerInput("Choose 1, 2, or 3 opponents.");
        }
        highestRolls = new int[3];
        nPCScores = new LinkedHashMap<>(userInput);
        createNPCs(userInput);
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
        boolean stillPlaying = true;
        while (playing) {
            resetGame();
            playRound();
            playNPCRound();
            findWinner();
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

    private void createNPCs(Integer numOfNPCs) {
        for (int i = 1; i <= numOfNPCs; i++) {
            String name = "npc"+i;
            nPCScores.put(new Player(name, null), null);
        }
    }

    private int playRound() {
        int numOfDie = 3;
        for (int i = 0; i < highestRolls.length; i++) {
            highestRolls[i] = Collections.max(rollDice(numOfDie));
            numOfDie--;
        }
        return IntStream.of(highestRolls).sum();
    }

    private void playNPCRound() {
        Set<Player> players = nPCScores.keySet();
        for (Player nPC : players) {
            nPCScores.put(nPC, playRound());
        }
    }

    private void findWinner() {
        Player nPC = Collections.max(nPCScores.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        console.print("You scored "+playRound()+". ");
        if (playRound() >= nPCScores.get(nPC)) {
            console.println("You won!");
        } else if (playRound() == nPCScores.get(nPC)) {
            console.println(nPC.getName()+" scored "+nPCScores.get(nPC)+". You tied!");
        } else {
            console.println(nPC.getName()+" scored "+nPCScores.get(nPC)+". You lost.");
        }
    }
}
