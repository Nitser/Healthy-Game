package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class PlayingFieldController  {

    @FXML
    public ImageView game1;
    @FXML
    private AnchorPane anchorPane;

    private Stage stage;
    private Parent root;
    private Scene scene;

    public PlayingFieldController(){
        FXMLLoader fxmlLoader = new   FXMLLoader(getClass().getResource("/sample/scenes/2_checkLevel.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            scene = new Scene(root, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backClick(ActionEvent actionEvent) throws IOException {
        MainController mainController = new MainController();
        mainController.launchMainController(stage);
    }

    public void game1Click(MouseEvent mouseEvent) throws IOException {
        RuleController ruleController = new RuleController();
        ruleController.launchRuleController(stage);
    }

    public void gameWhite(MouseEvent mouseEvent) {
        game1.setImage(new Image("/sample/images/pic1.png"));
    }

    public void gameBack(MouseEvent mouseEvent) {
        game1.setImage(new Image("/sample/images/pic2.png"));
    }

    public void launchPFController(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
