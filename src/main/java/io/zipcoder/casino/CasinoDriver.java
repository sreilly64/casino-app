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

        gamesList.add(new BlackJack(Card.getNewDeck()));
        gamesList.add(new Craps());
        gamesList.add(new GoFish(Card.getNewDeck()));
        gamesList.add(new GoingToBoston());
        startCasino();
    }

    void startCasino() {
        boolean inSession = true;
        console.println("Welcome to Ocean's Three Casino~");
        while (inSession) {
            String userInput = console.getStringInput("Make a selection: <Login> | <Logout> | <Choose Game> | " +
                                                          "<Quit>");
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
                    break;
                default:
                    console.print("We didn't quite catch that. ");
            }
        }
    }

    void playerLogin() {
        if (getCurrentPlayer() == null) {
            String userInput = console.getStringInput("What's your name?");
            if(isReturningPlayer(userInput)) {
                setCurrentPlayer(playersList.get(userInput));
            } else {
                Integer startingFunds = console.getIntegerInput("How much money do you want to start with?");
                setCurrentPlayer(createPlayer(userInput, startingFunds));
                console.println("You are all set, "+currentPlayer.getName()+"! Your current funds are $"+
                                  currentPlayer.getCurrentFunds()+".");
            }
        } else {
            console.println(getCurrentPlayer().getName()+" is already logged in.");
        }
    }

    Boolean isReturningPlayer(String name) {
        if (playersList.containsKey(name)) {
            console.print("Welcome back "+name+". Your current funds are $"+playersList.get(name).getCurrentFunds()+
                              ". ");
            return true;
        } else {
            console.print("Oh, you're new here. Let's make an account for you. ");
            return false;
        }
    }

    Player createPlayer(String name, Integer startingFunds) {
        while(!isNameAvailable(name)) {
            String newName = console.getStringInput("Unfortunately, there is already an account with the name "
                + name + ". Please enter a different name.");
            name = newName;
        }
        playersList.put(name, new Player(name, startingFunds));
        return playersList.get(name);
    }

    Boolean isNameAvailable(String name) {
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
            console.print("Nobody is logged in... ");
        } else {
            this.currentPlayer = null;
            console.print("You are logged out. ");
        }
    }

    void printGamesList() {
        for (Game game : gamesList) {
            console.print("<"+game.getGameName()+"> | ");
        }
    }

    void chooseGame() {
        printGamesList();
        if (currentPlayer != null) {
            String userInput = console.getStringInput("Which game do you want to play?");
            Boolean choosingGame = choseGame(true, userInput);
            while(choosingGame) {
                console.println("We didn't quite catch that.");
                printGamesList();
                userInput = console.getStringInput("Which game did you want to play?");
                choosingGame = choseGame(choosingGame, userInput);
            }
        } else {
            console.println("Sorry, but you must be logged in to play.");
        }
    }

    private Boolean choseGame(Boolean choosingGame, String gameName) {
        for(Game game : gamesList) {
            if(gameName.equals(game.getGameName())) {
                game.startGame(currentPlayer);
                choosingGame = false;
                break;
            }
        }
        return choosingGame;
    }
}
