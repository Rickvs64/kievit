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
    //Java RMI
    private IServerManager server;
    private Registry registry;
    //login textfields
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    //button login
    @FXML
    private Button btn_submit;
    //label register passed
    @FXML
    private Label lbl_accountCreated;

    public LoginController() throws SQLException, IOException, ClassNotFoundException, NotBoundException {
        //server push ip config because of VPN problems
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        setup();
    }
    //setup for java RMI connection
    private void setup() throws IOException, NotBoundException, SQLException, ClassNotFoundException {
        this.registry = locateRegistry();
        this.server = (IServerManager) registry.lookup("serverManager");

    }
    //locating register
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
    //??
    @FXML
    public void initialize() {
        lbl_accountCreated.setText("");
    }

    //login
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
    //register
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();
        controller.setup(user,server);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow();
        stage.setScene(homeScreen);
        stage.show();
    }

    /**
     * Navigate to the home screen while sending an instance of User to be used later in the application.
     * @throws IOException
     */
    private void toRegisterScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../register/register.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        RegisterController controller = fxmlLoader.<RegisterController>getController();
        controller.setup(server);
        Scene Screen = new Scene(root);
        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow();
        stage.setScene(Screen);
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
