package Client.gui.room.create;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Room extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        primaryStage.setTitle("Room");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
