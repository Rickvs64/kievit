package gui.login;

import classes.User;
import gui.home.HomeController;
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


        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#
        controller.setUser(test);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow();

        stage.setScene(homeScreen);
        stage.show();
    }
}
