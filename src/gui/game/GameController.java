package gui.game;

import classes.domains.Direction;
import classes.domains.JavaFXPaintable;
import classes.domains.Player;
import classes.domains.User;
import gui.home.HomeController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private Player player1;
    private Player player2;
    private User user;
    private User user2;
    private AnimationTimer animationTimer;
    private Timer playerTimer;
    @FXML
    private Canvas grid;
    @FXML
    private AnchorPane gridTemp;
    @FXML
    private Text txtPoints;
    private JavaFXPaintable paintable;
    private boolean first = true;
    private Integer points = 0;
    private boolean gameOver = false;

    public GameController() {
        player1 = new Player(950, 600, Direction.UP, 1, 0);
        player2 = new Player(0, 600, Direction.UP, 2, 0);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateCanvas();
                //TODO maybe new timer for intersection?
                if ( player1.intersects(player2) ||  player1.hitsGrid()) {
                    animationTimer.stop();
                    playerTimer.cancel();
                    txtPoints.setText("Player 2 wint! Aantal punten: " + points / 40 + "\n Press any key to go back");
                    gameOver = true;
                } else if(player2.intersects(player1) || player2.hitsGrid()) {
                    animationTimer.stop();
                    playerTimer.cancel();
                    txtPoints.setText("Player 1 wint! Aantal punten: " + points / 40 + "\n Press any key to go back");
                    gameOver = true;
                }

                points++;
            }
        };
        animationTimer.start();
        playerTimer = new Timer();
        playerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdatePlayer();
            }
        }, 1000 , 50);
    }

    public void setUsers(User user,User user2) {
        this.user = user;
        this.user2 = user2;
    }

    @FXML
    private void ChangeDirection(KeyEvent event){
        if (gameOver) {
            try {
                toHomeScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        KeyCode keyCode = event.getCode();
        switch( keyCode ) {
            case UP:
                if (player1.getCurrentDirection() != Direction.DOWN) {
                    player1.setCurrentDirection(Direction.UP);
                }
                break;
            case DOWN:
                if (player1.getCurrentDirection() != Direction.UP) {
                    player1.setCurrentDirection(Direction.DOWN);
                }
                break;
            case LEFT:
                if (player1.getCurrentDirection() != Direction.RIGHT) {
                    player1.setCurrentDirection(Direction.LEFT);
                }
                break;
            case RIGHT:
                if (player1.getCurrentDirection() != Direction.LEFT) {
                    player1.setCurrentDirection(Direction.RIGHT);
                }
                break;
            case W:
                if (player2.getCurrentDirection() != Direction.DOWN) {
                    player2.setCurrentDirection(Direction.UP);
                }
                break;
            case S:
                if (player2.getCurrentDirection() != Direction.UP) {
                    player2.setCurrentDirection(Direction.DOWN);
                }
                break;
            case A:
                if (player2.getCurrentDirection() != Direction.RIGHT) {
                    player2.setCurrentDirection(Direction.LEFT);
                }
                break;
            case D:
                if (player2.getCurrentDirection() != Direction.LEFT) {
                    player2.setCurrentDirection(Direction.RIGHT);
                }
                break;
        }
    }

    /**
     * Navigate to the home screen while sending an instance of User to be used later in the application.
     * @throws IOException
     */
    @FXML
    private void toHomeScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUser(user);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) grid.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    private void updateCanvas() {
        if (first) {
            paintable = new JavaFXPaintable(this.grid);
            gridTemp.getScene().setOnKeyPressed(this::ChangeDirection);
            first = false;
        }
        paintable.draw(player1,player2);
    }

    private void UpdatePlayer() {
        player1.move();
        player2.move();
    }

}

