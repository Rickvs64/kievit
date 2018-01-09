package gui.game;

import classes.domains.*;
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
import server.IServerManager;
import shared.IListener;
import shared.ILobby;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;


public class GameController extends UnicastRemoteObject implements IListener{
    private IPlayer player1;
    private IPlayer player2;
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
    //multiplayer parameters
    private boolean local = false;
    private int playerNumber;
    private ILobby lobby;
    private IServerManager server;
    private int opponentId;


    public GameController() throws RemoteException {
        player1 = new Player(50, 600, Direction.UP, 1, 1);
        player1.setHeadID(3);
        player1.setTailID(8);
        player2 = new Player(950, 600, Direction.UP, 2, 2);
        player2.setHeadID(3);
        player2.setTailID(8);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    updateCanvas();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    if ( player1.intersects(player2) ||  player1.hitsGrid()) {
                        animationTimer.stop();
                        playerTimer.cancel();
                        txtPoints.setText("Player 2 wint! Aantal punten: " + points / 40 + "\n Press the enter key to go back");
                        gameOver = true;
                        if(opponentId == 1){
                            server.updateHighscore(user.getId(), points/40);
                        }
                    }
                    else if(player2.intersects(player1) || player2.hitsGrid()) {

                        animationTimer.stop();
                        playerTimer.cancel();
                        txtPoints.setText("Player 1 wint! Aantal punten: " + points / 40 + "\n Press the enter to go back");
                        gameOver = true;
                    }
                } catch (ClassNotFoundException | IOException | SQLException e) {
                    e.printStackTrace();
                }

                points++;
            }
        };
        animationTimer.start();
        playerTimer = new Timer();
        playerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdatePlayer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 3000 , 100);

    }
    private void UpdatePlayerServer() {
        if (playerNumber == 1)
        {
            try {
                server.updateDirection(player1.getCurrentDirection(),lobby.getId(),playerNumber);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                server.updateDirection(player2.getCurrentDirection(),lobby.getId(),playerNumber);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUsers(User user,User user2) {
        this.user = user;
        this.user2 = user2;
    }
    public void setupMulti(int playerNumber,ILobby lobby, IServerManager server)
    {
        this.server = server;
        try {
            server.addListener(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.playerNumber = playerNumber;
        this.lobby = lobby;
        if (playerNumber == 1)  {
            opponentId = 2;
        }
        else {
            opponentId = 1;
        }
    }
    @FXML
    private void ChangeDirection(KeyEvent event)  {
        if (gameOver && event.getCode() == KeyCode.ENTER) {
            try {
                toHomeScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (local) {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    try {
                        if (player2.getCurrentDirection() != Direction.DOWN) {
                            player2.setCurrentDirection(Direction.UP);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case DOWN:
                    try {
                        if (player2.getCurrentDirection() != Direction.UP) {
                            player2.setCurrentDirection(Direction.DOWN);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case LEFT:
                    try {
                        if (player2.getCurrentDirection() != Direction.RIGHT) {
                            player2.setCurrentDirection(Direction.LEFT);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case RIGHT:
                    try {
                        if (player2.getCurrentDirection() != Direction.LEFT) {
                            try {
                                player2.setCurrentDirection(Direction.RIGHT);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case W:
                    try {
                        if (player1.getCurrentDirection() != Direction.DOWN) {
                            player1.setCurrentDirection(Direction.UP);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case S:
                    try {
                        if (player1.getCurrentDirection() != Direction.UP) {
                            player1.setCurrentDirection(Direction.DOWN);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case A:
                    try {
                        if (player1.getCurrentDirection() != Direction.RIGHT) {
                            player1.setCurrentDirection(Direction.LEFT);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case D:
                    try {
                        if (player1.getCurrentDirection() != Direction.LEFT) {
                            player1.setCurrentDirection(Direction.RIGHT);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else {
            KeyCode keyCode = event.getCode();
            IPlayer player;
            if (playerNumber == 1) {
                player = player1;
            } else {
                player = player2;
            }

            switch (keyCode) {
                case W:
                    try {
                        if (player.getCurrentDirection() != Direction.DOWN) {
                            player.setCurrentDirection(Direction.UP);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case S:
                    try {
                        if (player.getCurrentDirection() != Direction.UP) {
                            player.setCurrentDirection(Direction.DOWN);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case A:
                    try {
                        if (player.getCurrentDirection() != Direction.RIGHT) {
                            player.setCurrentDirection(Direction.LEFT);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case D:
                    try {
                        if (player.getCurrentDirection() != Direction.LEFT) {
                            player.setCurrentDirection(Direction.RIGHT);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            UpdatePlayerServer();
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

        Parent root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) grid.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    private void updateCanvas() throws RemoteException {
        if (first) {
            paintable = new JavaFXPaintable(this.grid);
            gridTemp.getScene().setOnKeyPressed(this::ChangeDirection);
            first = false;
        }
        paintable.draw(player1,player2);
    }

    private void UpdatePlayer() throws RemoteException {
        Direction dir = server.getDirection(opponentId,lobby.getId());
        if (playerNumber == 1) {
            player2.setCurrentDirection(dir);
        }
        else {
            player1.setCurrentDirection(dir);
        }
        player1.move();
        player2.move();
    }

    @Override
    public void setDirectionOtherPlayer(Direction direction) throws RemoteException {
        if (playerNumber == 1) {
            this.player2.setCurrentDirection(direction);
        }
        else {
            this.player1.setCurrentDirection(direction);
        }
    }

    @Override
    public int getLobbyID() throws RemoteException {
        return lobby.getId();
    }

    @Override
    public int getUserID() throws RemoteException {
        return playerNumber;
    }

    public void setLocal() {
        this.local = true;
    }
}

