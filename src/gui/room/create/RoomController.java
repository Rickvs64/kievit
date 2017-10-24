package gui.room.create;

import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.RoomRepository;
import gui.game.GameController;
import gui.room.lobby.Lobby;
import gui.room.lobby.LobbyController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RoomController {
    private User user;
    @FXML
    private Label lbl_username;
    @FXML
    private Label lbl_credits;
    @FXML
    private TextField roomName;
    @FXML
    private TextField roomPassword;
    public void setUser(User user) {
       this.user = user;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
    }
    @FXML
    public void createRoom() throws IOException, SQLException, ClassNotFoundException {
        if (!roomName.getText().trim().isEmpty() && !roomPassword.getText().trim().isEmpty())
        {
            System.out.println("passed");
            IRoomRepository roomRepository = new RoomRepository();
            int roomID = roomRepository.createRoom(1,roomName.getText(),roomPassword.getText(),50);
            toLobbyScreen(roomID);
        }
    }
    private void toLobbyScreen(int roomID) throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../lobby/Lobby.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        LobbyController controller = fxmlLoader.<LobbyController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUser(user);
        controller.setRoom(roomID);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
}
