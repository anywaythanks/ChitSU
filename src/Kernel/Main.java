package Kernel;

import DataBase.Deleter;
import DataBase.Loader;
import DataBase.Saver;
import FlowWindows.Error.Error;
import FlowWindows.Load.Load;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    public static Stage stage;
    public static Parent root;
    public static Saver saver;
    public static Loader loader;
    public static Deleter deleter;

    @Override
    public void start(Stage stage) throws Exception {
        //Создание сохранений
        if (!loader.checkSave("save1"))
            saver.saveSudoku("save1", 3, 0, new int[9][9], new boolean[9][9]);

        if (!loader.checkSave("save2"))
            saver.saveSudoku("save2", 3, 0, new int[9][9], new boolean[9][9]);

        if (!loader.checkSave("save3"))
            saver.saveSudoku("save3", 3, 0, new int[9][9], new boolean[9][9]);

        /**
         * Создание модуля {@link Load}.
         */
        Load load = new Load("123");
        load.setSaves(loader.loadAllNameSave());
        load.setOnDelete(actionEvent -> {
            try {
                deleter.deleteSave(actionEvent.getSource().toString());
                load.removeSave(actionEvent.getSource().toString());
            } catch (SQLException throwables) {
                Error error = new Error("Ошибка");
                error.setText( throwables.getMessage());
                error.showAndWait();
                System.exit(-1);
            }
        });
        load.setOnLoad(actionEvent -> System.out.println(actionEvent.getActionCommand() + " " + actionEvent.getSource().toString()));
        load.show();

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
        //Создание объектов, для работы с бд.

        try {
            saver = new Saver();
            loader = new Loader();
            deleter = new Deleter();
        } catch (SQLException | ClassNotFoundException throwables) {
            Error error = new Error("Ошибка");
            error.setText( throwables.getMessage());
            error.showAndWait();
            System.exit(-1);
        }
        launch(args);
    }
}
