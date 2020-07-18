package FlowWindows.Load;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Load extends Stage implements FlowWindow {
    private final Controller controller;

    public Load(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Load.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        initOwner(Main.stage);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Load.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
    }

}
