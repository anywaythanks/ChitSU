package Kernel;

import DataBase.Deleter;
import DataBase.Loader;
import DataBase.Saver;
import FlowWindows.Error.Error;
import FlowWindows.Notice.Notice;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class Main extends Application {
    public static Stage stage;
    public static Parent root;
    public static Saver saver;
    public static Loader loader;
    public static Deleter deleter;

    @Override
    public void start(Stage stage) throws Exception {
        Notice notice = new Notice("not");

        //notice.setImage(new Image(Kernel.Main.class.getResource("Pictures/sakura1.png").toExternalForm()));

        notice.setOnAnswer(actionEvent -> System.out.println((boolean) actionEvent.getSource()));
        notice.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
