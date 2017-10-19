package gui.game;

import classes.domains.Direction;
import classes.domains.JavaFXPaintable;
import classes.domains.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private Player player1;
    private Player player2;
    private AnimationTimer animationTimer;
    private Timer playerTimer;
    @FXML
    private Canvas grid;
    @FXML
    private AnchorPane gridTemp;
    private JavaFXPaintable paintable;
    private boolean first = true;
    public GameController() {
        player1 = new Player(50, 50, Direction.DOWN, 1, 0);
        player2 = new Player(950, 950, Direction.UP, 2, 0);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateCanvas();
                //TODO maybe new timer for intersection?
                if (player1.intersects(player2) || player2.intersects(player1))
                {
                    animationTimer.stop();
                    playerTimer.cancel();
                }
            }
        };
        animationTimer.start();
        playerTimer = new Timer();
        playerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdatePlayer();
            }
        }, 0 , 50);
    }

        @FXML
        private void handle (KeyEvent event){
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
    private void updateCanvas()
    {
        if (first)
        {
            paintable = new JavaFXPaintable(this.grid);
            gridTemp.getScene().setOnKeyPressed(this::handle);
            first = false;
        }
        paintable.draw(player1,player2);
    }
    private void UpdatePlayer()
    {
        //if (first)
        //{
        //    paintable = new JavaFXPaintable(this.grid);
        //    gridTemp.getScene().setOnKeyPressed(this::handle);
        //    first = false;
//
        //}
        player1.move();
        player2.move();
    }

}
