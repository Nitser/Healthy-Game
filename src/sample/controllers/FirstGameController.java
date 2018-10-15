package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class FirstGameController implements Initializable {

    @FXML
    private ImageView pony;
    @FXML
    private Label points;
    @FXML
    private AnchorPane anchorPane;

    private static ImageView good1;
    private static ImageView good2;
    private static ImageView good3;
    private static ImageView good4;
    private static ImageView good5;
    private static ImageView good6;
    private static ImageView good7;
    private static ImageView bad1;
    private static ImageView bad2;
    private static ImageView bad3;
    private static ImageView gameover;
    private static ImageView background;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Thread threadGame;

    private double drop_left = 100, drop_top = -100, drop_right, drop_bottom, e = 10;
    private float drop_v = 200;
    private int score=0;
    private ImageView drop;
    private Random random = new Random();

    private int delay = 50;
    private boolean fall=true, game = true, is_drop;
    private double x, y;

    public FirstGameController(){
        FXMLLoader fxmlLoader = new   FXMLLoader(getClass().getResource("/sample/scenes/5_game.fxml"));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchFGController(Stage stage) throws IOException, InterruptedException {
        this.stage = stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        startGame();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//  TO PLAY WITH PONY :)
//        pony1 = new ImageView("/sample/images/pony1.png");
//        pony2 = new ImageView("/sample/images/pony2.png");
//        pony3 = new ImageView("/sample/images/pony3.png");
//        pony4 = new ImageView("/sample/images/pony4.png");
//        bigPony = new ImageView("/sample/images/pony5.png");

        good1 = new ImageView("/sample/images/watermellon.png");
        good2 = new ImageView("/sample/images/rise.png");
        good3 = new ImageView("/sample/images/cheese.png");
        good4 = new ImageView("/sample/images/chiken.png");
        good5 = new ImageView("/sample/images/egg.png");
        good6 = new ImageView("/sample/images/wok.png");
        good7 = new ImageView("/sample/images/milk.png");

        bad1 = new ImageView("/sample/images/food1.png");
        bad2 = new ImageView("/sample/images/food3.png");
        bad3 = new ImageView("/sample/images/food4.png");

        gameover = new ImageView("/sample/images/gameover.png");
        background = new ImageView("/sample/images/background.jpg");

        drop = good1;
        pony.setImage(drop.getImage());
        pony.setLayoutX(drop_left);
        pony.setLayoutY(drop_top);

        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            x = event.getX();
            y = event.getY();
            fall = false;
        });
    }

    public void startGame() throws IOException, InterruptedException {
        threadGame = new Thread(() -> {
            while (game){
                if(fall)
                    move();
                else
                    remove();
                if(drop_top > stage.getHeight()-10 && (drop != bad1 && drop != bad2 && drop != bad3)){
                    game = false;
                    pony.setImage(gameover.getImage());
                    pony.setFitHeight(anchorPane.getHeight());
                    pony.setFitWidth(anchorPane.getWidth());
                    pony.setLayoutY(70);
                    pony.setLayoutX(0);
                    if(PlayersBoard.getCurrentPlayer().getFirstGamePoints() < score)
                        PlayersBoard.getCurrentPlayer().setFirstGamePoints(score);
                    try {
                        Thread.sleep(25*delay);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Platform.runLater(() ->{
                        threadGame.stop();
                        RuleController ruleController = new RuleController();
                        ruleController.launchRuleController(stage);
                    });
                }
                else if (drop_top > stage.getHeight()-10 ){
                    changeImage();
                }
                try{
                    Thread.sleep(delay);
                } catch(Exception e){e.printStackTrace();}
            }
        });
        threadGame.start();
    }

    public void changeImage(){
        drop_top = -100;
        drop_left = (int) (Math.random() * (anchorPane.getWidth() - pony.getFitWidth()));
        drop_v += 20;
        Platform.runLater(() -> { points.setText(String.valueOf(score)); });

        switch (random.nextInt(10)) {
            case 0: drop = good1; break;
            case 1: drop = good2; break;
            case 2: drop = good3; break;
            case 3: drop = good4; break;
            case 4: drop = good5; break;
            case 5: drop = good6; break;
            case 6: drop = good7; break;
            case 7: drop = bad1; break;
            case 8: drop = bad2; break;
            case 9: drop = bad3; break;
        }
        pony.setImage(drop.getImage());
        pony.setLayoutX(drop_left);
        pony.setLayoutY(drop_top);
    }

    public void remove(){

        drop_right = drop_left + pony.getFitWidth();
        drop_bottom = drop_top + pony.getFitHeight();
        is_drop = x>=drop_left && x<=drop_right && y>=drop_top && y<=drop_bottom;

        if(is_drop) {
            if(drop == bad1 || drop == bad2 || drop == bad3)
                score-=2;
            else{
                e+=0.5;
                score++;
            }
            changeImage();
        }
        fall = true;
    }

    public void move(){
        drop_top+=e;
        pony.setLayoutY(drop_top);
    }

    public void backClick() throws IOException {
        RuleController ruleController = new RuleController();
        ruleController.launchRuleController(stage);
    }

}