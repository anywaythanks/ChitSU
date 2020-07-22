package FlowWindows.Wining;

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
public class Wining extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Wining#setText(String)}.
     */
    public final Controller controller;

    /**
     * Создание объекта {@link Wining}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Help.fxml или же в нем содержатся ошибки.
     */
    public Wining(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Wining.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Wining.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }
}
