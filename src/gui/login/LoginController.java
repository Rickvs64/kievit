package gui.login;

import classes.domains.*;
import gui.home.HomeController;
import gui.register.RegisterController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.IServerManager;
import shared.IServerSettings;
import shared.ServerSettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginController {
    private IServerManager server;
    private Registry registry;
    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    private Button btn_submit;

    @FXML
    private Label lbl_accountCreated;

    public LoginController() throws SQLException, IOException, ClassNotFoundException, NotBoundException {
        setup();
    }

    private void setup() throws IOException, NotBoundException, SQLException, ClassNotFoundException {
        this.registry = locateRegistry();
        this.server = (IServerManager) registry.lookup("serverManager");

    }
    private Registry locateRegistry() throws SQLException, IOException, ClassNotFoundException {
        IServerSettings serverSettings = new ServerSettings();
        try
        {
            return LocateRegistry.getRegistry(serverSettings.getIp(), serverSettings.getPort());
        }
        catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }
    @FXML
    public void initialize() {
        lbl_accountCreated.setText("");
    }
    @FXML
    private void login(ActionEvent event) throws IOException {
        User user = server.login(txt_username.getText().toLowerCase(), txt_password.getText());
        if (user != null) {

            toHomeScreen(user);
        }
        else
        {
            System.out.println("Wrong user credentials, mate.");
        }

    }

    @FXML
    private void register() throws IOException {
        toRegisterScreen();
    }

    /**
     * Navigate to the home screen while sending an instance of User to be used later in the application.
     * @param user
     * @throws IOException
     */
    private void toHomeScreen(User user) throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);
        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    /**
     * Navigate to the home screen while sending an instance of User to be used later in the application.
     * @throws IOException
     */
    private void toRegisterScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../register/register.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        RegisterController controller = fxmlLoader.<RegisterController>getController();

        // There's no additional data required by the newly opened form.
        Scene registerScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(registerScreen);
        stage.show();
    }

    /**
     * Display a confirmation label stating a new account has successfully been created.
     * @param username New username to display in the label.
     */
    public void accountCreated(String username) {
        System.out.println("Successfully created new account.");
        showMessageDialog(null, "Account \"" + username + "\" has been created successfully.");
        lbl_accountCreated.setText("Account \"" + username + "\" has been created successfully.");
    }
}
