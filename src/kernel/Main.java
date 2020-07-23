package kernel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("GAME_NAME");
        Font.loadFont(sample.Main.class.getResource("Fonts/1.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("sample/Pictures/icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
