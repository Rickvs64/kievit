package gui.home;

import classes.domains.User;
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
    }

    private void updateUserInfo() {
        lbl_username.setText(user.getUsername());
        lbl_credits.setText("★ " + user.getCredits().toString());
    }

    @FXML
    private void openLobby() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../room/lobby/lobby.fxml"));

        Parent root = (Parent)fxmlLoader.load();


        Scene lobby = new Scene(root);

        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(lobby);
        stage.show();
    }
}
