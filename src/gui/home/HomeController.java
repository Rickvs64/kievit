package gui.home;

import classes.domains.User;
import gui.Shop.ShopController;
import gui.room.create.RoomController;
import gui.room.lobby.LobbyController;
import gui.room.search.searchroomController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.IServerManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;

public class HomeController {
    private User user;
    private IServerManager server;
    @FXML
    private Label lbl_username;

    @FXML
    private Label lbl_credits;



    private void updateUserInfo() {
        lbl_username.setText(user.getUsername());
        lbl_credits.setText("★ " + user.getCredits().toString());
    }

    @FXML
    private void openRoom() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../room/create/Room.fxml"));
        Parent root = fxmlLoader.load();
        RoomController roomController = fxmlLoader.<RoomController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        roomController.setup(user,server);
        stage.show();
    }

    @FXML
    private void openJoinScreen() throws IOException, SQLException, NotBoundException, ClassNotFoundException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        // More info can be found in the toHomeScreen() method
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../room/search/searchroom.fxml"));
        Parent root = fxmlLoader.load();
        searchroomController controller = fxmlLoader.<searchroomController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);
        Scene searchScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.
        stage.setScene(searchScreen);
        stage.show();
    }
    @FXML
    private void openShopScreen() throws IOException, SQLException, ClassNotFoundException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../shop/Shop.fxml"));
        Parent root = fxmlLoader.load();
        ShopController shopController = fxmlLoader.getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.
        stage.setScene(homeScreen);
        shopController.setup(user,server);
        stage.show();
    }

    public void setup(User user, IServerManager server) {
        this.user = user;
        updateUserInfo();
        this.server = server;
    }
}
