package gui.home;

import classes.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    private User user;

    @FXML
    private Label lbl_username;

    @FXML
    private Label lbl_credits;

    public void setUser(User user) {
        this.user = user;
        updateUserInfo();
    }

    private void updateUserInfo() {
        lbl_username.setText(user.getUsername());
        lbl_credits.setText("★ " + user.getCredits().toString());
    }
}
