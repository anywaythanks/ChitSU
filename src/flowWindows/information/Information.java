package flowWindows.information;

import flowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс окна информации.
 * <p>
 * Показывает пользователю информацию о программе.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Information extends Stage implements FlowWindow {
    /**
     * Создание объекта {@link Information}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Information.fxml или же в нем содержатся ошибки.
     */
    public Information(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Information.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Information.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));
    }

    @Override
    public void setText(String text) {
    }
}
