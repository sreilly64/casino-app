package io.zipcoder.casino;

import io.zipcoder.casino.games.*;
import io.zipcoder.casino.player.Player;
import io.zipcoder.casino.utilities.Console;

import java.util.*;

public class CasinoDriver {
    final Console console;
    final Map<String, Player> playersList;
    Player currentPlayer;
    ArrayList<Game> gamesList;

    CasinoDriver() {
        console = new Console(System.in, System.out);
        playersList = new HashMap<>();

    }

    void startCasino() {
        boolean inSession = true;
        console.println("Welcome to Ocean's Three Casino~");
        String userInput;
        while (inSession) {
            if(getCurrentPlayer() == null){
                userInput = console.getStringInput("Make a selection: <Login> | <Quit>");
                inSession = chooseNotLoggedInSelection(inSession, userInput.toLowerCase());
            }else{
                userInput = console.getStringInput("Make a selection: <Choose Game> | <Get Balance> | <Logout> |  <Quit>");
                inSession = chooseLoggedInSelection(inSession, userInput.toLowerCase());
            }
        }
    }

    Boolean chooseNotLoggedInSelection(boolean inSession, String userInput){
        switch (userInput) {
            case "login":
                playerLogin();
                break;
            case "quit":
                console.println("Come again soon!");
                inSession = false;
                break;
            default:
                console.print("We didn't quite catch that.\n");
        }
        return inSession;
    }

    Boolean chooseLoggedInSelection(boolean inSession, String userInput) {
        switch (userInput) {
            case "logout":
                playerLogout();
                break;
            case "choose game":
                chooseGame();
                break;
            case "get balance":
                getBalance();
                break;
            case "quit":
                console.println("Come again soon!");
                inSession = false;
                break;
            default:
                console.print("We didn't quite catch that.\n");
        }
        return inSession;
    }

    public void getBalance() {
        System.out.println("Your current balance is: $" + this.currentPlayer.getCurrentFunds() + ".");
    }

    void playerLogin() {
        if (getCurrentPlayer() == null) {
            userLogin();
        } else {
            console.println(getCurrentPlayer().getName()+" is already logged in.");
        }
    }

    void userLogin() {
        String userInput = console.getStringInput("What's your name?");
        if(isReturningPlayer(userInput)) {
            setCurrentPlayer(playersList.get(userInput));
        } else {
            createAccount(userInput);
        }
    }

    Boolean isReturningPlayer(String name) {
        if (playersList.containsKey(name)) {
            console.print("Welcome back "+name+". Your current funds are $"+playersList.get(name).getCurrentFunds()+
                              ".\n");
            return true;
        } else {
            console.print("Oh, you're new here. Let's make an account for you. ");
            return false;
        }
    }

    void createAccount(String userInput) {
        Integer startingFunds = console.getIntegerInput("How much money do you want to start with?");
        setCurrentPlayer(createPlayer(userInput, startingFunds));
        console.println("You are all set, "+currentPlayer.getName()+"! Your current funds are $"+
                            currentPlayer.getCurrentFunds()+".");
    }

    Player createPlayer(String name, Integer startingFunds) {
        while(!isNameAvailable(name)) {
            name = console.getStringInput("Unfortunately, there is already an account with the name "
                + name + ". Please enter a different name.");
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
            console.print("Nobody is logged in...\n");
        } else {
            this.currentPlayer = null;
            console.print("You are logged out.\n");
        }
    }

    void createGameList() {
        gamesList = new ArrayList<>();
        gamesList.add(new BlackJack(Card.getNewDeck()));
        gamesList.add(new Craps());
        gamesList.add(new GoFish(Card.getNewDeck()));
        gamesList.add(new GoingToBoston());
    }

    void printGamesList() {
        createGameList();
        for (Game game : gamesList) {
            console.print("<"+game.getGameName()+"> | ");
        }
    }

    void chooseGame() {
        printGamesList();
        if (currentPlayer != null) {
            askGameChoice();
        } else {
            console.println("Sorry, but you must be logged in to play.");
        }
    }

    void askGameChoice() {
        String gameName = console.getStringInput("Which game do you want to play?");
        Boolean isValidGame = isGameChosen(false, gameName);
        choseValidGame(isValidGame);
    }

    Boolean isGameChosen(Boolean isGame, String gameName) {
        for(Game game : gamesList) {
            if(gameName.equalsIgnoreCase(game.getGameName())) {
                game.startGame(currentPlayer);
                isGame = true;
                break;
            }
        }
        return isGame;
    }

    void choseValidGame(Boolean isGame) {
        String gameName;
        while(!isGame) {
            console.println("We didn't quite catch that.");
            printGamesList();
            gameName = console.getStringInput("Which game did you want to play?");
            isGame = isGameChosen(isGame, gameName);
        }
    }
}
