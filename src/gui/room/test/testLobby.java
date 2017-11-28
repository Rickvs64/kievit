package gui.room.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testLobby extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("testLobby.fxml"));
        primaryStage.setTitle("testLobby");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}