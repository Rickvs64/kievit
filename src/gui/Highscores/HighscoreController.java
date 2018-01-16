package gui.Highscores;

import classes.domains.HighscoreEntry;
import classes.domains.User;
import classes.repositories.HighscoreRepository;
import classes.repositories.IHighscoreRepository;
import gui.home.HomeController;
import javafx.application.Platform;
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
import server.IServerManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    private User user;
    @FXML
    private TableView<HighscoreEntry> highscoreTable;

    @FXML
    private TableColumn<HighscoreEntry, Integer> userid;

    @FXML
    private TableColumn<HighscoreEntry, String> username;

    @FXML
    private TableColumn<HighscoreEntry, Integer> score;

    private ObservableList<HighscoreEntry> data = FXCollections.observableArrayList();
    private IServerManager server;

    public HighscoreController() throws SQLException, IOException, ClassNotFoundException {
        LoadHighscores();
    }
    public void setup(User user,IServerManager server) {
        this.user = user;
        this.server = server;
    }

    public void LoadHighscores() throws SQLException, IOException, ClassNotFoundException {
        IHighscoreRepository HighscoreController = new HighscoreRepository();
        data.clear();
        data.addAll(HighscoreController.getHighscores());
        Platform.runLater(new Runnable() {
            @Override public void run() {
                TableColumn<HighscoreEntry, ?> highscoreEntryTableColumn = highscoreTable.getColumns().get(2);
                highscoreEntryTableColumn.setSortType(TableColumn.SortType.DESCENDING);
                highscoreTable.getSortOrder().add(highscoreEntryTableColumn);
            }
        });
    }
    public void toHomeScreen() throws IOException {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../home/home.fxml"));

        Parent root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        controller.setup(user,server);

        Scene homeScreen = new Scene(root);

        Stage stage;
        stage = (Stage) highscoreTable.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(homeScreen);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        highscoreTable.setItems(data);

    }
}
