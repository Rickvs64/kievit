package gui.room.create;

import classes.domains.User;
import gui.game.GameController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void createRoom() throws IOException {
        if (!roomName.getText().trim().isEmpty() && !roomPassword.getText().trim().isEmpty())
        {
            System.out.println("passed");
            //toGameScreen(this.user);
        }
    }
}
