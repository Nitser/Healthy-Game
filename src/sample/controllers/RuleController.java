package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.Player;

import java.io.IOException;

public class RuleController {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Player> playersTable;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, Long> pointsColumn;



    public RuleController(){
        FXMLLoader fxmlLoader = new   FXMLLoader(getClass().getResource("/sample/scenes/3_rules1.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGameClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        FirstGameController firstGameController = new FirstGameController();
        firstGameController.launchFGController(stage);
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        MainController mainController = new MainController();
        mainController.launchMainController(stage);
    }

    public void launchRuleController(Stage stage) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("firstGamePoints"));

        playersTable.setItems(getUserList());
        this.stage = stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private ObservableList<Player> getUserList() {
        ObservableList<Player> players = FXCollections.observableArrayList(  PlayersBoard.players);
        players.sort((Player a, Player b) -> Long.compare(b.getFirstGamePoints(), a.getFirstGamePoints()));
        return players;
    }
}
