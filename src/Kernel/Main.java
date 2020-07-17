package Kernel;

import FlowWindows.Error.Error;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String GAME_NAME = "ChitSU";
    public static Stage stage;
    public static Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        Error error = new Error();
        error.setStage("Ошибочка");

        error.getStage().show();

        error.setText("84328744273");
        //error.getStage().setResizable(false);
        /*stage.setTitle(GAME_NAME);
        Font.loadFont(Kernel.Main.class.getResource("Fonts" + File.separator +"HanZi.ttf").toExternalForm(), 10);
        root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
        Scene scene = new Scene(root);
       scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("Pictures/icon.png"));
        stage.show();
        Main.stage = stage;*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
