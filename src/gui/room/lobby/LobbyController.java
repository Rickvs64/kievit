package gui.room.lobby;

import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.RoomRepository;
import gui.game.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyController {
    private User user;
    private User user2;
    private int roomID;
    private boolean guest = false;
    private String guestName;
    private Timer timer;
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
    public void setUser(User user) {
        this.user = user;
        this.lbl_username.setText(this.user.getUsername());
        this.lbl_credits.setText(String.valueOf(this.user.getCredits()));
        this.roomRepository = new RoomRepository();
        //setUser2();
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
        StringBuilder msg = new StringBuilder();
        for (User u:users
             ) {
            msg.append(u.getUsername() + " Credits: " + u.getCredits());
            msg.append("\n");
        }
        //TODO : uitvogelen of het echt moet op deze manier(anders Error)
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
            playerList.setText(msg.toString());
        }
    });
    }

    @FXML
    public void loginPlayer()
    {

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
        timer.cancel();
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/game/Game.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();
        // Run the setUser() method in HomeController.
        // This is the JavaFX equivalent of sending data from one form to another in C#.
        controller.setUsers(user,user2);
        Scene homeScreen = new Scene(root);
        Stage stage;
        stage = (Stage) lbl_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }
}
