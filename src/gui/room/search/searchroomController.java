package gui.room.search;

import classes.domains.HighscoreEntry;
import classes.domains.IPlayer;
import classes.domains.Room;
import classes.domains.User;
import classes.repositories.*;
import gui.home.HomeController;
import gui.room.lobby.LobbyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;
import server.IServerManager;
import shared.ILobby;
import shared.IServerSettings;
import shared.Lobby;
import shared.ServerSettings;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

public class searchroomController {

    private User user;
    @FXML
    private Label lbl_username;
    @FXML
    private TextField searchname;
    @FXML
    private Label lbl_credits;
    @FXML
    private TableColumn ID;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn count;
    @FXML
    private TableColumn player;
    @FXML
    private TableView<ILobby> lobbyList;


    private Timer timer;
    private Registry registry;
    private IServerManager server;
    private ILobby lobby;
    public searchroomController() throws SQLException, IOException, ClassNotFoundException, NotBoundException {
        setup();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateServerLobby();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 1000 , 1000);
    }
    private void setup() throws IOException, NotBoundException, SQLException, ClassNotFoundException {
        this.registry = locateRegistry();
        this.server = (IServerManager) registry.lookup("serverManager");

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
    private void UpdateServerLobby() throws RemoteException {
        lobbyList.getItems().clear();
        ID.setCellValueFactory(new PropertyValueFactory<ILobby,String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<ILobby,String>("name"));
        player.setCellValueFactory(new PropertyValueFactory<ILobby,String>("playername"));
        count.setCellValueFactory(new PropertyValueFactory<ILobby,String>("count"));
        if (!server.getAvailibleLobbys().isEmpty())
        {
            for (ILobby i:server.getAvailibleLobbys()) {
                lobbyList.getItems().add(i);
            }
        }
    }
    @FXML
    private void selectedLobby()
    {
        if (lobbyList.getSelectionModel().getSelectedItem() != null)
        {
            lobby = lobbyList.getSelectionModel().getSelectedItem();
        }
        else
        {
            lobby = null;
        }
    }

    @FXML
    private void joinSelectedLobby() throws SQLException, IOException, ClassNotFoundException {
        if (lobby != null)
        {
            toLobbyScreen(lobby.getId(),lobby.getName(),lobby.getPassword());
        }
    }
    public void setUser(User user) {
        this.user = user;
        lbl_username.setText(user.getUsername());
        lbl_credits.setText(String.valueOf(user.getCredits()));
        System.out.println(user.getUsername());
    }


    @FXML
    private void toHomeScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUser(user);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
    @FXML
    private void toLobbyScreen(int roomID, String name,String password) throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../lobby/Lobby.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        LobbyController controller = fxmlLoader.<LobbyController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setRoom(roomID,name,password);
        controller.setPlayerNr(2);
        try {
            controller.setUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
}
