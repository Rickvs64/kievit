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
        this.registry = locateRegistry("127.0.0.1",1099);
        try {
            this.server = (IServerManager) registry.lookup("serverManager");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        //setUser2();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateLobby();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 100 , 1000);
    }
    public void setUser(User user) {
        this.user = user;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
        this.roomRepository = new RoomRepository();
        this.registry = locateRegistry("127.0.0.1",1099);
        try {
            this.server = (IServerManager) registry.lookup("serverManager");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        //setUser2();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateLobby();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 100 , 1000);
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
    public void UpdateLobby() throws SQLException, IOException, ClassNotFoundException {
        if (server.getLobby(1) != null) {
            this.lobby = server.getLobby(1);
           //
           //StringBuilder msg = new StringBuilder();
           //for (User u : users
           //        ) {
           //    msg.append(u.getUsername() + " Credits: " + u.getCredits());
           //    msg.append("\n");
           //}
           //Platform.runLater(new Runnable() {
           //    @Override
           //    public void run() {
           //        playerList.setText(msg.toString());
           //    }
           //});
            if (this.lobby.getStatus() == true)
            {
                startGame();
            }
        }
        else {
            server.addLobby(1);
        }
    }



    public void startGame() throws IOException {
        int playerNumber = 2;
        if (this.lobby.getStatus() == false)
        {
            playerNumber = 1;
            this.lobby.setStatus(true);
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



