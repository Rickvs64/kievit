package gui.game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private DirectionEnum direction;
    private Timer playerTimer;
    @FXML
    private ImageView Player;
    @FXML
    private ImageView grid;
    @FXML
    private AnchorPane gridTemp;

    private boolean first = true;
    public GameController() {

        direction = DirectionEnum.UP;
        playerTimer = new Timer();
        playerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdatePlayer();
            }
        }, 1000 , 30);
    }

        @FXML
        private void handle (KeyEvent event){
            KeyCode keyCode = event.getCode();
            switch( keyCode ) {
                case UP:
                    direction = DirectionEnum.UP;
                    break;
                case DOWN:
                    direction = DirectionEnum.DOWN;
                    break;
                case LEFT:
                    direction = DirectionEnum.LEFT;
                    break;
                case RIGHT:
                    direction = DirectionEnum.RIGHT;
                    break;
            }
    }
    private void UpdatePlayer()
    {
if (first)
{
    gridTemp.getScene().setOnKeyPressed(this::handle);
    first = false;
}
        switch (direction){
            case UP:
                Player.setY(Player.getY() - 1);
                break;
            case DOWN:
                Player.setY(Player.getY() + 1);
                break;
            case LEFT:
                Player.setX(Player.getX() - 1);
                break;
            case RIGHT:
                Player.setX(Player.getX() + 1);
                break;
        }


    }
}
