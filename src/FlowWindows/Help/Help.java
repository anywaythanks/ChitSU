package FlowWindows.Help;

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
public class Help extends Stage implements FlowWindow {
    /**
     * Создание объекта {@link Help}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Help.fxml или же в нем содержатся ошибки.
     */
    public Help(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Help.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));
    }

    @Override
    public void setText(String text) {
    }
}
