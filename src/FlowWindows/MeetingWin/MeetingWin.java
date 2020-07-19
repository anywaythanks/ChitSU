package FlowWindows.MeetingWin;

import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс первоначального окна.
 * <p>
 * Встречает пользователя при первом входе игру.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class MeetingWin extends Stage implements FlowWindow {
    /**
     * Создание объекта {@link MeetingWin}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Meeting.fxml или же в нем содержатся ошибки.
     */
    public MeetingWin(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingWin.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("MeetingWin.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));
    }

    @Override
    public void setText(String text) {
    }
}
