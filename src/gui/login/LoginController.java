package gui.login;

import classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    private Button btn_submit;

    /**
     * Temporary test method. Credential validation is skipped - user object is created and sent straight to home screen.
     * @param event
     */
    @FXML
    private void testSkipLogin(ActionEvent event) throws IOException {
        User test = new User();
        test.setUsername(txt_username.getText());
        test.setCredits(550);

        Stage stage;
        Parent root;
        stage = (Stage) txt_username.getScene().getWindow();

        // Make SURE the path to the fxml page is correctly set up.
        // A NullPointer ("location is required") exception will occur otherwise.
        root = FXMLLoader.load(getClass().getResource("../home/home.fxml"));

        

        // Create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
