package Client.gui.Highscores;

import classes.domains.HighscoreEntry;
import classes.domains.User;
import classes.repositories.HighscoreRepository;
import classes.repositories.IHighscoreRepository;
import Client.gui.home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    private User user;
    @FXML
    private TableView<HighscoreEntry> HighscoreTable;

    @FXML
    private TableColumn<HighscoreEntry, Integer> userid;

    @FXML
    private TableColumn<HighscoreEntry, String> username;

    @FXML
    private TableColumn<HighscoreEntry, Integer> score;
    // video: https://www.youtube.com/watch?v=oOhW_oHf7bM

    ObservableList<HighscoreEntry> data = FXCollections.observableArrayList();
    public HighscoreController() throws SQLException, IOException, ClassNotFoundException {
        LoadHighscores();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void LoadHighscores() throws SQLException, IOException, ClassNotFoundException {
        IHighscoreRepository HighscoreController = new HighscoreRepository();
        data.clear();
        data.addAll(HighscoreController.getHighscores());
    }
    public void toHomeScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        HomeController controller = fxmlLoader.<HomeController>getController();
        controller.setUser(user);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) HighscoreTable.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userid.setCellValueFactory(new PropertyValueFactory<HighscoreEntry, Integer>("userid"));
        username.setCellValueFactory(new PropertyValueFactory<HighscoreEntry, String>("username"));
        score.setCellValueFactory(new PropertyValueFactory<HighscoreEntry, Integer>("score"));
        HighscoreTable.setItems(data);

    }
}
