package gui.room.test;

import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.IUserRepository;
import classes.repositories.RoomRepository;
import classes.repositories.SQLUserRepository;
import gui.game.GameController;
import gui.home.HomeController;
import gui.room.search.searchroomController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.IServerManager;
import shared.ILobby;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class testLobbyController {
    private User user;
    private User user2;
    private String guestName;
    private Timer timer = new Timer();
    private IRoomRepository roomRepository;
    @FXML
    private Label lbl_username;
    @FXML
    private Label lbl_credits;

    @FXML
    private Label playerList;

    private ILobby lobby;
    private Registry registry;
    private IServerManager server;
    @FXML
    public void test()
    {
        user = new User(0,"Alex",100);
        user2 = new User(0,"bob",100);
        this.registry = locateRegistry("127.0.0.1",1099);
        try {
            this.server = (IServerManager) registry.lookup("serverManager");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            findLobby();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateLobby();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 100 , 50);
    }
    public void setUser(User user) {

    }

    private void UpdateLobby() throws IOException {
        this.lobby = server.getLobby(1);
        System.out.println("count : " + lobby.getCount());
        if (lobby.getStatus()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        startGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private Registry locateRegistry(String ipAdress, int portNumber)
    {
        try
        {
            return LocateRegistry.getRegistry(ipAdress, portNumber);
        }
        catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }
    public void findLobby() throws SQLException, IOException, ClassNotFoundException {

        if (server.getLobby(1) == null) {
            this.lobby = server.addLobby(1);
            //server.addCount(this.lobby.getId());
        }
        else {
            lobby = server.getLobby(1);
            //server.addCount(this.lobby.getId());
        }
    }



    public void startGame() throws IOException {
        int playerNumber = 2;
        if (server.getStatus(lobby.getId()) == false)
        {
            playerNumber = 1;
            server.setStatus(true,lobby.getId());
        }
        timer.cancel();
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../game/Game.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUsers(user,user2);
        controller.setupMulti(playerNumber,lobby);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

}



