package sample;

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
        stage = primaryStage;
        boolean flCrFiles = Logic.createFiles();
        primaryStage.setTitle(GAME_NAME);
        Font.loadFont(Main.class.getResource("Fonts/1.ttf").toExternalForm(), 10);
        root = FXMLLoader.load(getClass().getResource("Markup/sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Style/Default.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("Style/MainWin.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("sample/Pictures/icon.png"));
        primaryStage.show();
        if (flCrFiles) {
            Controller.stageBuf = Controller.log.setStage(Controller.stageBuf, "Добро пожаловать!", "Markup/startWin.fxml", "Style/Congr.css");
            Controller.stageBuf.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
