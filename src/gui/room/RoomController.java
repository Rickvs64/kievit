package gui.room;

import classes.domains.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public void createRoom()
    {
        if (!roomName.getText().trim().isEmpty() && !roomPassword.getText().trim().isEmpty())
        {
            System.out.println("passed");
        }
    }
}
