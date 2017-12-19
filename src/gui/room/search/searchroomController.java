package gui.room.search;

import classes.domains.User;
import gui.home.HomeController;
import gui.room.lobby.LobbyController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import server.IServerManager;
import shared.ILobby;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

public class searchroomController {

    private User user;
    @FXML
    private Label lbl_username;
    @FXML
    private Label lbl_credits;
    @FXML
    private TableColumn<ILobby, String> ID;
    @FXML
    private TableColumn<ILobby, String> name;
    @FXML
    private TableColumn<ILobby, String> count;
    @FXML
    private TableColumn<ILobby, String> player;
    @FXML
    private TableView<ILobby> lobbyList;

    private IServerManager server;
    private ILobby lobby;
    public searchroomController() throws SQLException, IOException, ClassNotFoundException, NotBoundException {
        Timer timer = new Timer();
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
    public void setup(User user, IServerManager server) throws IOException, NotBoundException, SQLException, ClassNotFoundException {
        this.user = user;
        lbl_username.setText(user.getUsername());
        lbl_credits.setText(String.valueOf(user.getCredits()));
        this.server = server;

    }

    private void UpdateServerLobby() throws RemoteException {
        lobbyList.getItems().clear();
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        player.setCellValueFactory(new PropertyValueFactory<>("playername"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        List<ILobby> availableLobbies = server.getAvailableLobbies();
        if (!availableLobbies.isEmpty()) {
            for (ILobby i: availableLobbies) {
                lobbyList.getItems().add(i);
            }
        }
    }
    @FXML
    private void selectedLobby() {
        if (lobbyList.getSelectionModel().getSelectedItem() != null) {
            lobby = lobbyList.getSelectionModel().getSelectedItem();
        } else {

            lobby = null;
        }
    }

    @FXML
    private void joinSelectedLobby() throws SQLException, IOException, ClassNotFoundException {
        if (lobby != null) {
            toLobbyScreen(lobby.getId(),lobby.getName(),lobby.getPassword());
        }
    }

    @FXML
    private void toHomeScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../home/home.fxml"));

        Parent root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);

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
        Parent root = fxmlLoader.load();
        LobbyController controller = fxmlLoader.<LobbyController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setRoom(name,password);
        controller.setPlayerNr(2);
        controller.setup(user,server,roomID);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
}
