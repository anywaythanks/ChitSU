package FlowWindows.MeetingWin;

import FlowWindows.MeetingWin.Controller;
import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class MeetingWin extends Stage implements FlowWindow {
    private final Controller controller;

    public MeetingWin(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingWin.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        initOwner(Main.stage);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("MeetingWin.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
    }
}
