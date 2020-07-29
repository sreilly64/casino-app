package io.zipcoder.casino.games;

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

    private Craps currentGame;
    //private final Craps currentGame = new Craps();

    public CrapsGUI(Craps game) {
        this.currentGame = game;
        String[] args = new String[] {"123"};
        main(args);
    }

    private Parent createContent(){
        VBox vbox = new VBox(10);
        vbox.setPrefSize(800, 600);

        //first Pane
        GridPane title = new GridPane();
        title.setAlignment(Pos.CENTER);

        Text crapsLabel = new Text("Craps Table");
        crapsLabel.setFont(Font.font("Times New Romans", FontWeight.NORMAL, 20));

        title.add(crapsLabel, 0, 0);

        //next Pane
        GridPane pucks = new GridPane();
        pucks.setAlignment(Pos.CENTER);
        pucks.setPadding(new Insets(0,0,0,0));

        TextField off = new TextField();
        off.setPrefWidth(70);
        TextField on4 = new TextField();
        on4.setPrefWidth(70);
        TextField on5 = new TextField();
        on5.setPrefWidth(70);
        TextField on6 = new TextField();
        on6.setPrefWidth(70);
        TextField on8 = new TextField();
        on8.setPrefWidth(70);
        TextField on9 = new TextField();
        on9.setPrefWidth(70);
        TextField on10 = new TextField();
        on10.setPrefWidth(70);

        pucks.add(off, 0, 0);
        pucks.add(on4, 1, 0);
        pucks.add(on5, 2, 0);
        pucks.add(on6, 3, 0);
        pucks.add(on8, 4, 0);
        pucks.add(on9, 5, 0);
        pucks.add(on10, 6, 0);


        //next
        GridPane bets = new GridPane();




        vbox.getChildren().addAll(title, pucks); //add all buttons and fields here
        return vbox;
    }

    public TextField generateTextField(){
        return null;
    }

    public Button generateButton(){
        return null;
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
