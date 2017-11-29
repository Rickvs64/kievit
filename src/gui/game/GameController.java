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
import shared.ILobby;
import shared.IServerSettings;
import shared.ServerSettings;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
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
    private Timer updatePlayersTimer;
    private Registry registry;
    private IServerManager server;
    private int opponementId;


    public GameController() throws RemoteException {
        player1 = new Player(50, 600, Direction.UP, 1, 1);
        player2 = new Player(950, 600, Direction.UP, 2, 2);
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
                    }
                    else if(player2.intersects(player1) || player2.hitsGrid()) {

                        animationTimer.stop();
                        playerTimer.cancel();
                        txtPoints.setText("Player 1 wint! Aantal punten: " + points / 40 + "\n Press the enter to go back");
                        gameOver = true;
                    }
                } catch (RemoteException e) {
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
        }, 1000 , 50);
        try {
            this.registry = locateRegistry();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.server = (IServerManager) registry.lookup("serverManager");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        //updatePlayersTimer = new Timer();
        //updatePlayersTimer.schedule(new TimerTask() {
        //    @Override
        //    public void run() {
        //        UpdatePlayerServer();
        //    }
        //}, 1000 , 25);
    }

    private void UpdatePlayerServer() {
        if (playerNumber == 1)
        {
            try {

                server.updateDirection(player1.getCurrentDirection(),lobby.getId(),player1.getUserID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
           try {
              player2.setCurrentDirection(server.getDirection(player2.getUserID(),lobby.getId()));
           } catch (RemoteException e) {
               e.printStackTrace();
           }
        }
        else
        {
            try {
                server.updateDirection(player2.getCurrentDirection(),lobby.getId(),player2.getUserID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
           try {
               player1.setCurrentDirection(server.getDirection(player1.getUserID(),lobby.getId()));
           } catch (RemoteException e) {
               e.printStackTrace();
           }
        }
    }
    private Registry locateRegistry() throws SQLException, IOException, ClassNotFoundException {
        IServerSettings serverSettings = new ServerSettings();
        try
        {
            return LocateRegistry.getRegistry(serverSettings.getIp(), serverSettings.getPort());
        }
        catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }
    public void setUsers(User user,User user2) {
        this.user = user;
        this.user2 = user2;
    }
    public void setupMulti(int playerNumber,ILobby lobby)
    {
        this.playerNumber = playerNumber;
        this.lobby = lobby;
        if (playerNumber ==1)
        {
            opponementId = 2;
        }
        else {
            opponementId = 1;
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
            if (playerNumber ==1) {
                switch (keyCode) {
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
                UpdatePlayerServer();
            }
            else {
                switch (keyCode) {
                    case W:
                        try {
                            if (player2.getCurrentDirection() != Direction.DOWN) {
                                player2.setCurrentDirection(Direction.UP);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case S:
                        try {
                            if (player2.getCurrentDirection() != Direction.UP) {
                                player2.setCurrentDirection(Direction.DOWN);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case A:
                        try {
                            if (player2.getCurrentDirection() != Direction.RIGHT) {
                                player2.setCurrentDirection(Direction.LEFT);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case D:
                        try {
                            if (player2.getCurrentDirection() != Direction.LEFT) {
                                player2.setCurrentDirection(Direction.RIGHT);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                UpdatePlayerServer();
            }
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

    private void updateCanvas() throws RemoteException {
        if (first) {
            paintable = new JavaFXPaintable(this.grid);
            gridTemp.getScene().setOnKeyPressed(this::ChangeDirection);
            first = false;
        }
        paintable.draw(player1,player2);
    }

    private void UpdatePlayer() throws RemoteException {
        UpdatePlayerServer();
        player1.move();
        player2.move();
    }

}

