package gui.room.search;

import classes.domains.HighscoreEntry;
import classes.domains.Room;
import classes.domains.User;
import classes.repositories.HighscoreRepository;
import classes.repositories.IHighscoreRepository;
import classes.repositories.IRoomRepository;
import classes.repositories.RoomRepository;
import gui.home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class searchroomController implements Initializable {

    private User user;
    @FXML
    private Label lbl_username;
    @FXML
    private TextField searchname;
    @FXML
    private Label lbl_credits;
    @FXML
    private TableView<Room> roomlist;
    @FXML
    private TableColumn<Room, String> name;
    @FXML
    private TableColumn<Room, Integer> players;
    @FXML
    private TableColumn<Room, Integer> ID;
    ObservableList<Room> data = FXCollections.observableArrayList();

    public searchroomController() throws SQLException, IOException, ClassNotFoundException {
        IRoomRepository RoomController = new RoomRepository();
        data.clear();
        List<Room> roomlist = RoomController.getAvaiableRooms();
        data.addAll(roomlist);
    }
    public void setUser(User user) {
        this.user = user;
        lbl_username.setText(user.getUsername());
        lbl_credits.setText(String.valueOf(user.getCredits()));
        System.out.println(user.getUsername());
    }
    @FXML
    public void LoadHighscores() throws SQLException, IOException, ClassNotFoundException {
        IRoomRepository RoomController = new RoomRepository();
        data.clear();
        List<Room> roomlist = RoomController.getAvaiableRooms();
        System.out.println(searchname.getText());
        if(searchname.getText() == "")
        {
           data.addAll(RoomController.getAvaiableRooms());
        }
        else{
            for (Room e: roomlist
                 ) {
                if(e.getName().toLowerCase().contains(searchname.getText().toLowerCase())){
                    data.add(e);
                }
            }
        }


    }
//    public void LoadHighscores(String filter) throws  SQLException, IOException, ClassNotFoundException{
//        IRoomRepository RoomController = new RoomRepository();
//        List<Room> roomlistall = RoomController.getAvaiableRooms();
//        data.clear();
//
//        data.addAll(RoomController.getAvaiableRooms());
//    }
    @FXML
    private void toHomeScreen() throws IOException {
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
        players.setCellValueFactory(new PropertyValueFactory<Room, Integer>("players"));
        ID.setCellValueFactory(new PropertyValueFactory<Room, Integer>("ID"));
        roomlist.setItems(data);
    }
}
