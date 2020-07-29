package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;

import java.util.*;
public class GoingToBoston extends DiceGame{
    public static String gameName = "Going to Boston";
    ArrayList<Integer> highestRolls;
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
        highestRolls = new ArrayList<>(3);
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
        while (playing) {
            resetGame();
            playRound();
            playNPCRound();
            findWinner();
            String userInput = console.getStringInput("Play again? <Yes> | <No>");
            while(!userInput.equals("Yes")||!userInput.equals("No")) {
                console.println("We didn't quite catch that.");
                userInput = console.getStringInput("Play again? <Yes> | <No>");
            }
            if (userInput.equals("Yes")) {
                break;
            } else if (userInput.equals("No")) {
                quitGame();
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
        for (int i = 0; i < highestRolls.size(); i++) {
            Collections.max(rollDice(numOfDie));
            //highestRolls.set(i, );
            numOfDie--;
        }
        return Collections.max(highestRolls);
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
        } else {
            console.println(nPC.getName()+" scored "+nPCScores.get(nPC)+". You lost.");
        }
    }
}
