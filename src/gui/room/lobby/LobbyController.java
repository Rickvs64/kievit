package gui.room.lobby;

import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.IUserRepository;
import classes.repositories.RoomRepository;
import classes.repositories.SQLUserRepository;
import gui.game.GameController;
import gui.home.HomeController;
import gui.room.search.searchroomController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.IServerManager;
import shared.ILobby;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyController {
    private User user;
    private User user2;
    private int roomID;
    private boolean guest = false;
    private boolean login = false;
    private String guestName;
    private Timer timer = new Timer();
    private IRoomRepository roomRepository;
    @FXML
    private Label lbl_username;
    @FXML
    private Label lbl_credits;
    @FXML
    private Label lbl_username2;
    @FXML
    private Label lbl_credits2;
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerPassword;
    @FXML
    private Label playerList;
    @FXML
    private Group player2login;
    @FXML
    private Group player2stats;

    private ILobby lobby;
    private Registry registry;
    private IServerManager server;
    public void setUser(User user) {
        this.user = user;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
        this.roomRepository = new RoomRepository();
        //setUser2();

    }
    public void setupLocal()
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateLobby();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 100 , 1000);
    }
    public void setupMulti()
    {
        this.registry = locateRegistry("127.0.0.1",1099);
        try {
            this.server = (IServerManager) registry.lookup("serverManager");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            findLobby();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UpdateServerLobby();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 100 , 50);

    }
    public void findLobby() throws SQLException, IOException, ClassNotFoundException {

        if (server.getLobby(1) == null) {
            this.lobby = server.addLobby(1);
            server.addCount(this.lobby.getId());
        }
        else {
            lobby = server.getLobby(1);
            server.addCount(this.lobby.getId());
        }
    }

    private void UpdateServerLobby() throws IOException {
        this.lobby = server.getLobby(1);
        System.out.println("count : " + lobby.getCount());
        if (lobby.getStatus()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        startGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private Registry locateRegistry(String ipAdress, int portNumber)
    {
        try
        {
            return LocateRegistry.getRegistry(ipAdress, portNumber);
        }
        catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }
    public void setRoom(int roomID){
        this.roomID = roomID;
    }
    public void setUser2(User user2) {

        this.user2 = user2;
        this.lbl_username2.setText(this.user2.getUsername());
        this.lbl_credits2.setText(String.valueOf(this.user2.getCredits()));
    }

    public void UpdateLobby() throws SQLException, IOException, ClassNotFoundException {
        List<User> users = roomRepository.getLobby(roomID);
        if (guest)
        {
            users.add(user2);
        }
        if (login)
        {
            users.add(user2);
        }
        StringBuilder msg = new StringBuilder();
        for (User u:users
             ) {
            msg.append(u.getUsername() + " Credits: " + u.getCredits());
            msg.append("\n");
        }
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
            playerList.setText(msg.toString());
        }
    });
    }

    @FXML
    public void loginPlayer() throws SQLException, IOException, ClassNotFoundException {
        if (!playerName.getText().trim().isEmpty()& !playerPassword.getText().trim().isEmpty()) {
            User user = new User(playerName.getText(), playerPassword.getText());
            user.setCredits(640);
            IUserRepository userRepo = new SQLUserRepository();
            if (userRepo.checkUserExists(user)) {
                setUser2(user);
                player2login.setVisible(false);
                player2stats.setVisible(true);
                login = true;
            } else {
                System.out.println("Wrong user credentials, mate.");
                playerPassword.setText("");
            }
        }
    }
    @FXML
    public void loginGuest()
    {
        if (playerName.getText().trim().isEmpty())
        {
            guestName = "Guest";
        }
        else
        {
            guestName = playerName.getText();
        }
        setUser2(new User(guestName,0));
        player2login.setVisible(false);
        player2stats.setVisible(true);
        guest = true;

    }
    public void startGame() throws IOException {
        int playerNumber = 2;
        if (server.getStatus(lobby.getId()) == false)
        {
            playerNumber = 1;
            server.setStatus(true,lobby.getId());
        }
        timer.cancel();
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../game/Game.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUsers(user,user2);
        controller.setupMulti(playerNumber,lobby);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    @FXML
    private void toLobbySearch() throws IOException
    {
        timer.cancel();
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

    /**
     * Navigate to the home screen while sending an instance of User to be used later in the application.
     * @throws IOException
     */
    @FXML
    private void toHomeScreen() throws IOException {
        timer.cancel();
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUser(user);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
}
