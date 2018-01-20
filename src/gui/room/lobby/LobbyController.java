package gui.room.lobby;

import classes.domains.IPlayer;
import classes.domains.Item;
import classes.domains.Player;
import classes.domains.User;
import gui.game.GameController;
import gui.home.HomeController;
import gui.room.search.searchroomController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.IServerManager;
import shared.ILobby;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyController {
    private User user;
    private User user2;
    private int lobbyID;
    private String roomName;
    private String roomPassword;
    private Timer timer = new Timer();
    private int playerNr;
    private int head_id;
    private int tail_id;
    private boolean allReady = false;
    private boolean guest = false;
    private boolean login = false;
    private boolean local = false;
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
    @FXML
    private Label lbl_tail;
    @FXML
    private Label lbl_head;
    @FXML
    private ComboBox<Item> cbHeadEquip;
    @FXML
    private ComboBox<Item> cbTailEquip;

    private ILobby lobby;
    private IServerManager server;

    public void setupMulti() throws SQLException, IOException, ClassNotFoundException {
        try {
            findLobby();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    try {
                        UpdateServerLobby();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000 , 50);

    }
    public void findLobby() throws SQLException, IOException, ClassNotFoundException {

        if (server.getLobby(lobbyID) == null) {
            this.lobby = server.addLobby(lobbyID,user.getUsername(),roomName,roomPassword);
            server.joinLobby(this.lobby.getId(),user);
            playerNr = 1;
        }
        else {
            lobby = server.getLobby(lobbyID);
            server.joinLobby(this.lobby.getId(),user);
            playerNr = 2;
        }
    }

    private void UpdateServerLobby() throws IOException, SQLException, ClassNotFoundException {
        this.lobby = server.getLobby(lobbyID);
        if (lobby.getStatus()){
            Platform.runLater(() -> {
                try {
                    startGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        UpdateLobby();
    }
    public void setRoom(String roomName,String roomPassword){
        this.roomName =roomName;
        this.roomPassword = roomPassword;
    }
    public void setUser2(User user2) {

        this.user2 = user2;
        this.lbl_username2.setText(this.user2.getUsername());
        this.lbl_credits2.setText(String.valueOf(this.user2.getCredits()));
    }

    public void UpdateLobby() throws SQLException, IOException, ClassNotFoundException {
        int unreadyPlayers = 0;
        List<User> users = server.getUsers(lobby.getId());
        StringBuilder msg = new StringBuilder();
        for (User u:users) {

            if (!u.getStatus()) {
                unreadyPlayers++;
            }
            msg.append(u.getUsername())
               .append(" Credits: ")
               .append(u.getCredits())
               .append(" ready: ")
               .append(u.getStatus())
               .append("\n");
        }
        if (unreadyPlayers == 0) {
            allReady = true;
        }

        Platform.runLater(() -> playerList.setText(msg.toString()));
    }
    @FXML
    private void updateStatus()
    {
        try {
            server.updateStatus(user.getId(),lobbyID);
            if (cbHeadEquip.getSelectionModel().getSelectedItem() != null) {
                Item head = cbHeadEquip.getSelectionModel().getSelectedItem();
                this.head_id = head.getID();
            } else {
                this.head_id = 3;
            }

            if (cbTailEquip.getSelectionModel().getSelectedItem() != null) {
                Item tail = cbTailEquip.getSelectionModel().getSelectedItem();
                this.tail_id = tail.getID();
            } else {
                this.tail_id = 8;
            }

            server.setCosmetics(playerNr, lobby.getId(), head_id, tail_id);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loginPlayer() throws SQLException, IOException, ClassNotFoundException {
        if (!playerName.getText().trim().isEmpty()& !playerPassword.getText().trim().isEmpty()) {
            User user2 = server.login(playerName.getText(), playerPassword.getText());
            if (user2 != null) {
                setUser2(user2);
                player2login.setVisible(false);
                player2stats.setVisible(true);
                login = true;
                local = true;
                user2.setStatus(true);
                server.joinLobby(lobby.getId(),user2);
            } else {
                System.out.println("Wrong user credentials, mate.");
                playerPassword.setText("");
            }
        }
    }
    @FXML
    public void loginGuest() throws RemoteException {
        String guestName;
        if (playerName.getText().trim().isEmpty())
        {
            guestName = "Guest";
        }
        else
        {
            guestName = playerName.getText();
        }

        setUser2(new User(0,guestName,0));
        user2.setStatus(true);

        server.joinLobby(lobby.getId(),user2);
        player2login.setVisible(false);
        player2stats.setVisible(true);
        guest = true;
        local = true;

    }
    public void startGame() throws IOException {
        if (allReady) {
            if (!server.getStatus(lobby.getId())) {

                server.setStatus(true, lobby.getId());
            }
            int headIDPlayer1 = 3;
            int tailIDPlayer1 = 8;
            int headIDPlayer2 = 3;
            int tailIDPlayer2 = 8;
            if (playerNr == 1)  {
                if (!local) {
                    try {
                        List<Integer> player1 = server.getCosmetics(1,lobby.getId());
                        if (player1.size() == 2)
                        {
                            headIDPlayer1 = player1.get(0);
                            tailIDPlayer1 = player1.get(1);
                        }
                        List<Integer> player2 = server.getCosmetics(2,lobby.getId());
                        if (player2.size() == 2)
                        {
                            headIDPlayer2 = player2.get(0);
                            tailIDPlayer2 = player2.get(1);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            timer.cancel();
            // Set the next "page" (scene) to display.
            // Note that an incorrect path will result in unexpected NullPointer exceptions!
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../game/Game.fxml"));
            Parent root = fxmlLoader.load();
            GameController controller = fxmlLoader.<GameController>getController();
            // Run the setUser() method in HomeController.
            // This is the JavaFX equivalent of sending data from one form to another in C#.
            controller.setUsers(user, user2);
            controller.setCosmetics(headIDPlayer1,tailIDPlayer1,headIDPlayer2,tailIDPlayer2);
            controller.setupMulti(playerNr, lobby, server,local);
            if (guest | login) {
                controller.setLocal();
            }

            Scene homeScreen = new Scene(root);
            Stage stage;
            stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

            stage.setScene(homeScreen);
            stage.show();
        }
    }

    @FXML
    private void toLobbySearch() throws IOException, SQLException, NotBoundException, ClassNotFoundException {
        timer.cancel();
        // More info can be found in the toHomeScreen() method
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../search/searchroom.fxml"));

        Parent root = fxmlLoader.load();
        searchroomController controller = fxmlLoader.getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);

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

        Parent root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();

        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setup(user,server);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    private void loadHeadEquip() throws SQLException, IOException, ClassNotFoundException {
            cbHeadEquip.getItems().addAll(server.getOwnedItems(user.getId(), "head"));

            Callback<ListView<Item>, ListCell<Item>> factory = lv -> new ListCell<Item>() {

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        cbHeadEquip.setCellFactory(factory);
        cbHeadEquip.setButtonCell(factory.call(null));
    }

    private void loadTailEquip() throws SQLException, IOException, ClassNotFoundException {
            cbTailEquip.getItems().addAll(server.getOwnedItems(user.getId(), "tail"));

        Callback<ListView<Item>, ListCell<Item>> factory = lv -> new ListCell<Item>() {

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        cbTailEquip.setCellFactory(factory);
        cbTailEquip.setButtonCell(factory.call(null));
    }
    public void setup(User user, IServerManager server,int lobbyID){
        this.user = user;
        this.server = server;
        this.lobbyID = lobbyID;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
        try {
            setupMulti();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        try {
            loadHeadEquip();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        try {
            loadTailEquip();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
