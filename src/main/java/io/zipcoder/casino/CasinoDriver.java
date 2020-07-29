package io.zipcoder.casino;

import io.zipcoder.casino.games.*;
import io.zipcoder.casino.player.Player;
import io.zipcoder.casino.utilities.Console;

import java.util.*;

public class CasinoDriver {
    Console console;
    Map<String, Player> playersList;
    Player currentPlayer;
    ArrayList<Game> gamesList;

    public CasinoDriver() {
        console = new Console(System.in, System.out);
        playersList = new HashMap<>();
        gamesList = new ArrayList<>();

        gamesList.add(new BlackJack());
        gamesList.add(new Craps());
        gamesList.add(new GoFish(Card.getNewDeck()));
        gamesList.add(new GoingToBoston());
        startCasino();
    }

    void startCasino() {
        boolean inSession = true;
        console.println("Welcome to Ocean's Three Casino. Login to start.");
        while (inSession) {
            String userInput = console.getStringInput("Make a selection:\n<Login>\n<Logout>\n<Choose Game>\n<Quit>");

            switch (userInput) {
                case "Login":
                    playerLogin();
                    break;
                case "Logout":
                    playerLogout();
                    break;
                case "Choose Game":
                    chooseGame();
                    break;
                case "Quit":
                    console.println("Come again soon!");
                    inSession = false;
                default:
                    console.print("We didn't quite catch that. ");
            }
        }
    }

    void playerLogin() {
        if (currentPlayer == null) {
            String userInput = console.getStringInput("What's your name?");
            if(returningPlayer(userInput)) {
                setCurrentPlayer(playersList.get(userInput));
            } else {
                Integer startingFunds = console.getIntegerInput("How much money do you want to start with?");
                setCurrentPlayer(createPlayer(userInput, startingFunds));
                console.print("You are all set, "+currentPlayer.getName()+". Your current funds are $"+
                                  currentPlayer.getCurrentFunds()+".");
            }
        } else {
            console.println(getCurrentPlayer().getName()+" is already logged in.");
        }
    }

    Boolean returningPlayer(String name) {
        if (playersList.containsKey(name)) {
            console.print("Welcome back "+name+". Your current funds are $"+playersList.get(name).getCurrentFunds()+".");
            return true;
        } else {
            console.print("Oh, you're new here. Let's make an account for you. ");
            return false;
        }
    }

    Player createPlayer(String name, Integer startingFunds) {
        while(!checkNameAvailability(name)) {
            String newName = console.getStringInput("Unfortunately, there is already an account with the name "
                + name + ". Please enter a different name.");
            name = newName;
        }
        return playersList.put(name, new Player(name, startingFunds));
    }

    Boolean checkNameAvailability(String name) {
        return (!playersList.containsKey(name)); //return true if name is not already in playersList
    }

    void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    void playerLogout() {
        if (currentPlayer == null) {
            console.println("Nobody is logged in.");
        } else {
            this.currentPlayer = null;
        }
    }

    String getGamesList() {
        StringBuilder games = new StringBuilder();
        for (Game game : gamesList) {
            games.append("\n<"+game.getGameName()+">");
        }
        return games.toString();
    }

    void chooseGame() {
        String userInput = console.getStringInput("Select a game to play:", getGamesList());
        if (currentPlayer != null) {
            while(true) {
                for (Game game : gamesList) {
                    if (userInput.equals(game.getGameName())) {
                        game.startGame(currentPlayer);
                        break;
                    }
                }
                userInput = console.getStringInput("We didn't quite catch that. Select a game to play:", getGamesList());
            }
        } else {
            console.println("Sorry, but you must be logged in to play.");
        }
    }
}
