package gui.Highscores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Highscore extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Highscore.fxml"));
        primaryStage.setTitle("Highscores!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
