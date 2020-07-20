package Kernel;

import DataBase.Deleter;
import DataBase.Loader;
import DataBase.Saver;
import FlowWindows.Error.Error;
import FlowWindows.Notice.Notice;
import javafx.application.Application;
import javafx.scene.Parent;
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
        Font.loadFont(Kernel.Main.class.getResource("Fonts" + File.separator + "HanZi.ttf").toExternalForm(), 10);
        /*
        if (!loader.checkSave("save1"))
            saver.saveSudoku("save1", 3, 0, new int[9][9], new boolean[9][9]);

        if (!loader.checkSave("save2"))
            saver.saveSudoku("save2", 3, 0, new int[9][9], new boolean[9][9]);

        if (!loader.checkSave("save3"))
            saver.saveSudoku("save3", 3, 0, new int[9][9], new boolean[9][9]);
        */
        Notice notice = new Notice("not");
        notice.setOnAnswer(actionEvent -> System.out.println((boolean) actionEvent.getSource()));
        notice.show();

        /*
        error.getStage().setResizable(false);
        stage.setTitle(GAME_NAME);
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
