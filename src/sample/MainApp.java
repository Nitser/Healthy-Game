package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controllers.MainController;
import sample.controllers.PlayersBoard;
import sample.models.Player;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PlayersBoard.loadPlayers();
        MainController mainController = new MainController();
        mainController.launchMainController(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        PlayersBoard.savePlayers();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
