package FlowWindows.Error;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс окна ошибки.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Error extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Error#setText(String)}.
     */
    private final Controller controller;

    /**
     * Создание объекта {@link Error}.
     *
     * @param title название окна.
     */
    public Error(String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Error.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Errors");
            error.setContentText(e.getMessage());
            error.showAndWait();
            System.exit(-1);
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Error.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }

}
