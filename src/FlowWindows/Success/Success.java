package FlowWindows.Success;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс окна помощи.
 * <p>
 * Показывает пользователю помощь по игре.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Success extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Success#setText(String)}.
     */
    public final Controller controller;
    /**
     * Создание объекта {@link Success}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Success.fxml или же в нем содержатся ошибки.
     */
    public Success(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Success.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        setAlwaysOnTop(true);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Success.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }
}
