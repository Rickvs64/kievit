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

import java.io.IOException;

public class HomeController {
    private User user;

    @FXML
    private Label lbl_username;

    @FXML
    private Label lbl_credits;

    public void setUser(User user) {
        this.user = user;
        updateUserInfo();
        System.out.println(user.getUsername());
    }

    private void updateUserInfo() {
        lbl_username.setText(user.getUsername());
        lbl_credits.setText("★ " + user.getCredits().toString());
    }

    @FXML
    private void openRoom() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../room/create/Room.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        RoomController roomController = fxmlLoader.<RoomController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        roomController.setUser(user);
        stage.show();
    }

    @FXML
    private void openJoinScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        // More info can be found in the toHomeScreen() method
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../search/searchroom.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        searchroomController controller = fxmlLoader.<searchroomController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUser(user);
        Scene searchScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.
        stage.setScene(searchScreen);
        stage.show();
    }
    @FXML
    private void openShopScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../shop/Shop.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ShopController shopController = fxmlLoader.getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        shopController.setUser(user);
        stage.show();
    }
}
