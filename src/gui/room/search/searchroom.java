package gui.room.search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class searchroom extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("searchroom.fxml"));
        primaryStage.setTitle("Search Room");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
