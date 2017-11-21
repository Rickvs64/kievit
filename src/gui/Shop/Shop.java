package gui.Shop;

import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Shop extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Shop.fxml"));
        primaryStage.setTitle("Shop");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
