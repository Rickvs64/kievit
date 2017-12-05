package gui.room.lobby;

import classes.domains.Item;
import classes.domains.User;
import classes.repositories.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import server.IServerManager;
import shared.ILobby;
import shared.IServerSettings;
import shared.ServerSettings;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyController {
    private User user;
    private User user2;
    private int roomID;
    private String roomName;
    private String roomPassword;
    private boolean guest = false;
    private boolean login = false;
    private String guestName;
    private Timer timer = new Timer();
    private IRoomRepository roomRepository;
    private IShopRepository shopRepository;
    private int playerNr;
    private int head_id;
    private int tail_id;

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
    private ComboBox cbHeadEquip;
    @FXML
    private ComboBox cbTailEquip;

    private ILobby lobby;
    private Registry registry;
    private IServerManager server;
    public void setUser(User user) throws SQLException, IOException, ClassNotFoundException {
        this.user = user;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
        this.roomRepository = new RoomRepository();
        this.shopRepository = new ShopRepository();
        setupMulti();
        loadHeadEquip();
        loadTailEquip();
    }

    public void setupMulti() throws SQLException, IOException, ClassNotFoundException {
        this.registry = locateRegistry();
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
                    try {
                        UpdateServerLobby();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000 , 50);

    }
    public void findLobby() throws SQLException, IOException, ClassNotFoundException {

        if (server.getLobby(roomID) == null) {
            this.lobby = server.addLobby(roomID,user.getUsername(),roomName,roomPassword);
            server.joinLobby(this.lobby.getId(),user);
        }
        else {
            lobby = server.getLobby(roomID);
            server.joinLobby(this.lobby.getId(),user);
        }
    }

    private void UpdateServerLobby() throws IOException, SQLException, ClassNotFoundException {
        this.lobby = server.getLobby(roomID);
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
    public void setRoom(int roomID,String roomName,String roomPassword){
        this.roomID = roomID;
        this.roomName =roomName;
        this.roomPassword = roomPassword;
    }
    public void setUser2(User user2) {

        this.user2 = user2;
        this.lbl_username2.setText(this.user2.getUsername());
        this.lbl_credits2.setText(String.valueOf(this.user2.getCredits()));
    }

    public void UpdateLobby() throws SQLException, IOException, ClassNotFoundException {
        List<User> users = server.getUsers(lobby.getId());
        StringBuilder msg = new StringBuilder();
        for (User u:users
             ) {
            msg.append(u.getUsername() + " Credits: " + u.getCredits());
            msg.append("\n");
        }
        Platform.runLater(() -> playerList.setText(msg.toString()));
    }

    @FXML
    public void loginPlayer() throws SQLException, IOException, ClassNotFoundException {
        if (!playerName.getText().trim().isEmpty()& !playerPassword.getText().trim().isEmpty()) {
            User user = new User(playerName.getText(), playerPassword.getText());
            IUserRepository userRepo = new SQLUserRepository();
            if (userRepo.login(playerName.getText(), playerPassword.getText()) != null) {
                setUser2(user);
                player2login.setVisible(false);
                player2stats.setVisible(true);
                login = true;
                server.joinLobby(lobby.getId(),user2);
            } else {
                System.out.println("Wrong user credentials, mate.");
                playerPassword.setText("");
            }
        }
    }
    @FXML
    public void loginGuest() throws RemoteException {
        if (playerName.getText().trim().isEmpty())
        {
            guestName = "Guest";
        }
        else
        {
            guestName = playerName.getText();
        }
        setUser2(new User(0,guestName,0));
        server.joinLobby(lobby.getId(),user2);
        player2login.setVisible(false);
        player2stats.setVisible(true);
        guest = true;

    }
    public void startGame() throws IOException {

        if (server.getStatus(lobby.getId()) == false)
        {

            server.setStatus(true,lobby.getId());
        }

        if(cbHeadEquip.getSelectionModel().getSelectedItem() != null){
            Item head = (Item)cbHeadEquip.getSelectionModel().getSelectedItem();
            this.head_id = head.getID();
        }else{
            this.head_id = 3;
        }

        if(cbTailEquip.getSelectionModel().getSelectedItem() != null){
            Item tail = (Item)cbTailEquip.getSelectionModel().getSelectedItem();
            this.tail_id = tail.getID();
        }else{
            this.tail_id = 8;
        }

        server.setCosmetics(playerNr, lobby.getId(), head_id, tail_id);

        timer.cancel();
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../game/Game.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUsers(user,user2);
        controller.setupMulti(playerNr,lobby);

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

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    private void loadHeadEquip() throws SQLException, IOException, ClassNotFoundException {
            cbHeadEquip.getItems().addAll(shopRepository.getOwnedItems(user.getId(), "head"));

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
            cbTailEquip.getItems().addAll(shopRepository.getOwnedItems(user.getId(), "tail"));

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
}
