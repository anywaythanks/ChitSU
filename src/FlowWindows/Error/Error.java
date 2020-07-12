package FlowWindows.Error;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Error implements FlowWindow {
    private Stage stage;
    private Controller controller;

    @Override
    public Stage setStage(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Error.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.stage);
        stage.setTitle(title);
        stage.hide();
        scene.getStylesheets().add(getClass().getResource("Error.css").toExternalForm());
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        controller = loader.getController();

        return stage;
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }

    @Override
    public void setTitle(String title) {
        stage.setTitle(title);
    }
}
