package io.zipcoder.casino.games;

import io.zipcoder.casino.player.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class CrapsGUI extends Application {

    private Craps currentGame = new Craps();
    //private Craps currentGame = new Craps();


    private TextField createPuckField(){
        TextField output = new TextField();
        output.setPrefWidth(70);
        return output;
    }

    private Button createThinButton(){
        Button output = new Button("");
        output.setPrefWidth(70);
        return output;
    }

    private Button createComeOddsButton(){
        Button output = new Button("");
        output.setPrefWidth(70);
        output.setPrefHeight(70);
        output.setDisable(true);
        return output;
    }

    private Button createSideBetButton(String label){
        Button output = new Button(label);
        output.setPrefWidth(100);
        return output;
    }

    private Parent createContent(){
        VBox vbox = new VBox(10);
        vbox.setPrefSize(900, 600);

        //first Pane
        GridPane title = new GridPane();
        title.setAlignment(Pos.CENTER);

        Text crapsLabel = new Text("Craps Table");
        crapsLabel.setFont(Font.font("Times New Romans", FontWeight.NORMAL, 20));

        title.add(crapsLabel, 0, 0);

        //second Pane, most of craps board
        GridPane mainBoard = new GridPane();
        mainBoard.setAlignment(Pos.CENTER);
        mainBoard.setPadding(new Insets(0,0,0,0));

        //create puck fields and assign starting text
        TextField off = createPuckField();
        if(currentGame.getCurrentPoint() == 0){
            off.setText("OFF");
        }
        TextField on4 = createPuckField();
        if(currentGame.getCurrentPoint() == 4){
            on4.setText("ON");
        }
        TextField on5 = createPuckField();
        if(currentGame.getCurrentPoint() == 5){
            on5.setText("ON");
        }
        TextField on6 = createPuckField();
        if(currentGame.getCurrentPoint() == 6){
            on6.setText("ON");
        }
        TextField on8 = createPuckField();
        if(currentGame.getCurrentPoint() == 8){
            on8.setText("ON");
        }
        TextField on9 = createPuckField();
        if(currentGame.getCurrentPoint() == 9){
            on9.setText("ON");
        }
        TextField on10 = createPuckField();
        if(currentGame.getCurrentPoint() == 10){
            on10.setText("ON");
        }

        //add all of the puck fields to the grid
        mainBoard.add(off, 2, 0);
        mainBoard.add(on4, 3, 0);
        mainBoard.add(on5, 4, 0);
        mainBoard.add(on6, 5, 0);
        mainBoard.add(on8, 6, 0);
        mainBoard.add(on9, 7, 0);
        mainBoard.add(on10, 8, 0);

        //create first row of betting fields
        Button dontPassOddsButton = new Button("Don't\nPass\nOdds");
        dontPassOddsButton.setDisable(true);
        dontPassOddsButton.setPrefWidth(70);
        dontPassOddsButton.setPrefHeight(225);

        Button dontPassLineButton = new Button ("Don't\nPass\nBar");
        dontPassLineButton.setPrefWidth(70);
        dontPassLineButton.setPrefHeight(225);

        Button dontComeButton = new Button("Don't\nCome\nBar");
        dontComeButton.setDisable(true);
        dontComeButton.setPrefWidth(70);
        dontComeButton.setPrefHeight(180);


        Button lay4Button = createThinButton();
        Button lay5Button = createThinButton();
        Button lay6Button = createThinButton();
        Button lay8Button = createThinButton();
        Button lay9Button = createThinButton();
        Button lay10Button = createThinButton();

        //add first row of buttons to grid
        mainBoard.add(dontPassOddsButton, 0,1,1,6);
        mainBoard.add(dontPassLineButton, 1,1,1,6);
        mainBoard.add(dontComeButton, 2,1,1,5);
        mainBoard.add(lay4Button, 3,1);
        mainBoard.add(lay5Button, 4,1);
        mainBoard.add(lay6Button, 5,1);
        mainBoard.add(lay8Button, 6,1);
        mainBoard.add(lay9Button, 7,1);
        mainBoard.add(lay10Button, 8,1);

        //create second row of betting fields
        Button placeLose4Button = createThinButton();
        Button placeLose5Button = createThinButton();
        Button placeLose6Button = createThinButton();
        Button placeLose8Button = createThinButton();
        Button placeLose9Button = createThinButton();
        Button placeLose10Button = createThinButton();

        //add second row of buttons to grid, Place Lose bets
        mainBoard.add(placeLose4Button, 3,2);
        mainBoard.add(placeLose5Button, 4,2);
        mainBoard.add(placeLose6Button, 5,2);
        mainBoard.add(placeLose8Button, 6,2);
        mainBoard.add(placeLose9Button, 7,2);
        mainBoard.add(placeLose10Button, 8,2);

        //create third row of bettering fields
        Button comeOdds4 = createComeOddsButton();
        Button comeOdds5 = createComeOddsButton();
        Button comeOdds6 = createComeOddsButton();
        Button comeOdds8 = createComeOddsButton();
        Button comeOdds9 = createComeOddsButton();
        Button comeOdds10 = createComeOddsButton();

        //add third row of buttons to the grid, Come Bet Odds
        mainBoard.add(comeOdds4, 3,3);
        mainBoard.add(comeOdds5, 4,3);
        mainBoard.add(comeOdds6, 5,3);
        mainBoard.add(comeOdds8, 6,3);
        mainBoard.add(comeOdds9, 7,3);
        mainBoard.add(comeOdds10, 8,3);

        //create fourth row of betting fields
        Button place4Button = createThinButton();
        Button place5Button = createThinButton();
        Button place6Button = createThinButton();
        Button place8Button = createThinButton();
        Button place9Button = createThinButton();
        Button place10Button = createThinButton();
        Button anySevenButton = createSideBetButton("Seven");
        Button anyCrapsButton = createSideBetButton("Any Craps");

        //add fourth row of buttons to grid, Place bets
        mainBoard.add(place4Button, 3,4);
        mainBoard.add(place5Button, 4,4);
        mainBoard.add(place6Button, 5,4);
        mainBoard.add(place8Button, 6,4);
        mainBoard.add(place9Button, 7,4);
        mainBoard.add(place10Button, 8,4);
        mainBoard.add(anySevenButton,9,4);
        mainBoard.add(anyCrapsButton,10,4);

        //create fifth row of betting fields, Buy bets
        Button buy4Button = createThinButton();
        Button buy5Button = createThinButton();
        Button buy6Button = createThinButton();
        Button buy8Button = createThinButton();
        Button buy9Button = createThinButton();
        Button buy10Button = createThinButton();
        Button hard4Button = createSideBetButton("Hard 4");
        Button hard10Button = createSideBetButton("Hard 10");

        //add fifth row of buttons to grid, Buy bets
        mainBoard.add(buy4Button, 3,5);
        mainBoard.add(buy5Button, 4,5);
        mainBoard.add(buy6Button, 5,5);
        mainBoard.add(buy8Button, 6,5);
        mainBoard.add(buy9Button, 7,5);
        mainBoard.add(buy10Button, 8,5);
        mainBoard.add(hard4Button,9,5);
        mainBoard.add(hard10Button,10,5);

        //create sixth row, come bet
        Button comeBetButton = new Button("Come");
        comeBetButton.setDisable(true);
        comeBetButton.setPrefWidth(490);
        comeBetButton.setPrefHeight(50);

        Button hard6Button = createSideBetButton("Hard 6");
        Button hard8Button = createSideBetButton("Hard 8");

        //add sixth tow. come bets
        mainBoard.add(comeBetButton, 2,6,7,1);
        mainBoard.add(hard6Button, 9,6);
        mainBoard.add(hard8Button, 10, 6);

        //create seventh row of Field Bet and Big 6 + Big 8
        Button big6Button = new Button("Big 6");
        big6Button.setPrefHeight(50);
        big6Button.setPrefWidth(70);
        big6Button.setDisable(true);

        Button big8Button = new Button("Big 8");
        big8Button.setPrefHeight(50);
        big8Button.setPrefWidth(70);
        big8Button.setDisable(true);

        Button fieldBetsButton = new Button("Field: 2 3 4 9 10 11 12");
        fieldBetsButton.setPrefHeight(50);
        fieldBetsButton.setPrefWidth(490);

        Button craps3Button = createSideBetButton("Craps 3");
        Button elevenButton = createSideBetButton("Eleven");

        //add seventh tow of bets
        mainBoard.add(big6Button,0,7);
        mainBoard.add(big8Button, 1, 7);
        mainBoard.add(fieldBetsButton, 2,7,7,1);
        mainBoard.add(craps3Button, 9, 7);
        mainBoard.add(elevenButton, 10, 7);

        //create eighth row of bets, Pass Line
        Button passLineButton = new Button("Pass Line");
        passLineButton.setPrefWidth(630);
        passLineButton.setPrefHeight(50);

        Button craps2Button = createSideBetButton("Craps 2");
        Button craps12Button = createSideBetButton("Craps 12");

        //add eighth row to grid pane
        mainBoard.add(passLineButton, 0,8,9,1);
        mainBoard.add(craps2Button, 9, 8);
        mainBoard.add(craps12Button, 10, 8);

        //create ninth row of bets, Pass Line Odds
        Button passLineOddsButton = new Button("Pass Line Odds");
        passLineOddsButton.setPrefWidth(630);
        passLineOddsButton.setPrefHeight(40);
        passLineOddsButton.setDisable(true);

        //add ninth row to grid pane, Pass Line Odds
        mainBoard.add(passLineOddsButton, 0,9,9,1);

        vbox.getChildren().addAll(title, mainBoard); //add all buttons and fields here
        return vbox;
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public void launcher(Player player){
        currentGame.setPlayer(player);
        CrapsGUI.main(null);
    }

    public static void main(String[] args){
        launch(args);
    }

}
