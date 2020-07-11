package Kernel;

import FlowWindows.MeetingWin.MeetingWin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static int n = 9;
    public static final String GAME_NAME = "ChitSU";
    public static Stage stage;
    public static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MeetingWin().setStage("test").show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
