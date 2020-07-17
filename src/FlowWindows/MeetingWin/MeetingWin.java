package FlowWindows.MeetingWin;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class MeetingWin implements FlowWindow {
    private Stage stage;

    @Override
    public void setStage(String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MeetingWin.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.stage);
        stage.setTitle(title);
        stage.hide();
        scene.getStylesheets().add(getClass().getResource("MeetingWin.css").toExternalForm());
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void setText(String text) {
    }

    @Override
    public void setTitle(String title) {
        stage.setTitle(title);
    }
}
