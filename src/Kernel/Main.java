package Kernel;

import FlowWindows.Error.Error;
import FlowWindows.MeetingWin.MeetingWin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    public static final String GAME_NAME = "ChitSU";
    public static Stage stage;
    public static Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        MeetingWin meetingWin = new MeetingWin("Встреча");
        meetingWin.show();


/*
        //error.getStage().setResizable(false);
        stage.setTitle(GAME_NAME);
        Font.loadFont(Kernel.Main.class.getResource("Fonts" + File.separator + "HanZi.ttf").toExternalForm(), 10);
        root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("Pictures/icon.png"));
        stage.show();
        Main.stage = stage;
*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
