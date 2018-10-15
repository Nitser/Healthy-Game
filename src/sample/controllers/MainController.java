package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import sample.models.Player;


import java.io.IOException;

public class MainController {

    @FXML
    private Label hello;
    @FXML
    private Button resumeButton;
    @FXML
    private Button newGameButton;

    private ObservableList<Player> playersList;
    private Stage primaryStage;
    private Parent root;
    private Scene scene;
    private PlayingFieldController playingFieldController;

    static boolean inFlag = true;

    public MainController(){
        FXMLLoader fxmlLoader = new   FXMLLoader(getClass().getResource("/sample/scenes/1_menu.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            scene = new Scene(root, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resumeClick(ActionEvent event) throws IOException {
        RuleController ruleController = new RuleController();
        ruleController.launchRuleController(primaryStage);
    }

    @FXML
    private void newGameClick(ActionEvent event) throws IOException {
        PlayersBoard playersBoard = new PlayersBoard(hello, resumeButton, newGameButton);
        playersBoard.launchPalyersBoard(primaryStage);
    }

    public void launchMainController(Stage stage){
        primaryStage = stage;
        primaryStage.setTitle("Very healthy game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        if(inFlag){
            hello.setText("Choose your player!");
            resumeButton.setDisable(true);
        }
        else {
            hello.setText("Hello, " + PlayersBoard.getCurrentPlayer().getName() + "!");
            resumeButton.setDisable(false);
            newGameButton.setText("CHANGE PLAYER!");
        }

    }

}