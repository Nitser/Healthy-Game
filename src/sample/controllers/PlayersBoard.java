package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PlayersBoard {

    private static Player currentPlayer = null;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private static int size = 50;
    static List<Player> players = new ArrayList<>();

    @FXML
    private TextField name;
    @FXML
    private ListView<String> playerList = new ListView<>();

    private Label label;
    private Button button;
    private Button button2;

    public PlayersBoard(Label label, Button button, Button button2) throws FileNotFoundException {
        this.label = label;
        this.button = button;
        this.button2 = button2;
        FXMLLoader fxmlLoader = new   FXMLLoader(getClass().getResource("/sample/scenes/4_addPlayer.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayers()  {
        try {
            Scanner scanner = new Scanner(new File("players.txt"));
            while (scanner.hasNext()) {
                if (players.size() < size)
                    players.add(new Player(scanner.next(), Long.parseLong(scanner.next())));
            }
            players.sort((Player a, Player b) -> Long.compare(b.getFirstGamePoints(), a.getFirstGamePoints()));
        }catch (FileNotFoundException e){
            players.add(new Player("NoName", 0));
        }
    }

    public static void savePlayers() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
        for(Player player : players){
            writer.println(player.getName() + " " + player.getFirstGamePoints());
        }
        writer.close();
    }

    @FXML
    public void okClick(ActionEvent actionEvent) {

        if (currentPlayer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Choose your player to continue!");
            alert.showAndWait();
        }
        else{
            label.setText("Hello, " + PlayersBoard.getCurrentPlayer().getName() + "!");
            MainController.inFlag = false;
            button.setDisable(false);
            button2.setText("CHANGE PLAYER!");
            stage.close();
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        players.remove(currentPlayer);
        playerList.getItems().clear();
        for(Player player: players){
            playerList.getItems().add(0, player.getName());
            playerList.edit(playerList.getItems().size()-1);
        }
        currentPlayer = null;
    }

    @FXML
    public void addClick(ActionEvent actionEvent) {
        boolean exist = false;
        if(players.size() < size) {
            Player player = new Player(name.getText());
            for(Player newPlayer : players){
                if(Objects.equals(newPlayer.getName(), player.getName()))
                    exist = true;
            }
            if(exist){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("This player already exists!");
                alert.showAndWait();
            }
            else {
                players.add(player);
                playerList.getItems().add(0, player.getName());
                playerList.edit(playerList.getItems().size() - 1);
            }
        }
    }

    public void launchPalyersBoard(Stage parentStage){
        for(Player player: players){
            playerList.getItems().add(0, player.getName());
            playerList.edit(playerList.getItems().size()-1);
        }

        MultipleSelectionModel langsSelectionModel = playerList.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                for(Player player: players){
                    if(player.getName() == newValue) {
                        currentPlayer = player;
                        break;
                    }
                }
            }
        });

        stage = new Stage();
        stage.setTitle("Players");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setX(parentStage.getX() + 200);
        stage.setY(parentStage.getY() + 100);
        stage.show();
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        PlayersBoard.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

}
