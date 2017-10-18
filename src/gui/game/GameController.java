package gui.game;

import classes.domains.Direction;
import classes.domains.JavaFXPaintable;
import classes.domains.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private Player player;
    private Timer playerTimer;
    @FXML
    private Canvas grid;
    @FXML
    private AnchorPane gridTemp;
    private JavaFXPaintable paintable;
    private boolean first = true;
    public GameController() {
        player = new Player();

        playerTimer = new Timer();
        playerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdatePlayer();
            }
        }, 1000 , 50);
    }

        @FXML
        private void handle (KeyEvent event){
            KeyCode keyCode = event.getCode();
            System.out.println("testing");
            switch( keyCode ) {
                case UP:
                    if (player.getCurrentDirection() != Direction.DOWN) {
                        player.setCurrentDirection(Direction.UP);
                    }
                    break;
                case DOWN:
                    if (player.getCurrentDirection() != Direction.UP) {
                        player.setCurrentDirection(Direction.DOWN);
                    }
                    break;
                case LEFT:
                    if (player.getCurrentDirection() != Direction.RIGHT) {
                        player.setCurrentDirection(Direction.LEFT);
                    }
                    break;
                case RIGHT:
                    if (player.getCurrentDirection() != Direction.LEFT) {
                        player.setCurrentDirection(Direction.RIGHT);
                    }
                    break;
            }
    }
    private void UpdatePlayer()
    {
        if (first)
        {
            paintable = new JavaFXPaintable(this.grid);
            gridTemp.getScene().setOnKeyPressed(this::handle);
            first = false;
        }
        player.move();
        paintable.drawPlayer(player);
    }

}
