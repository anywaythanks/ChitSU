package FlowWindows.LearningMeetingWin;

import Arithmetic.FXManipulate;
import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Класс окна.
 * <p>
 * Появляется при переходе в учебный режим игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class LearningMeetingWin extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link LearningMeetingWin#LearningMeetingWin(String)}.
     */
    public final Controller controller;


    /**
     * ID. Необходим для индентификации событий в {@link LearningMeetingWin#activateClosed(boolean)}.
     */
    private int id = 0;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#Close}.
     */
    private ActionListener closed = null;


    /**
     * Создать событие {@link LearningMeetingWin#closed}.
     * <p>
     * В source значение {@link Controller#confirmation}.
     *
     * @param answer новое событие.
     */
    public void setOnClosed(ActionListener answer) {
        this.closed = answer;
    }


    /**
     * Активировать событие {@link LearningMeetingWin#closed}.
     *
     * @param button значение {@link Controller#confirmation}.
     */
    private void activateClosed(boolean button) {
        if (closed != null) {
            ActionEvent actionEvent = new ActionEvent(button, id++, "Closed");
            closed.actionPerformed(actionEvent);
        }
    }

    /**
     * Создание объекта {@link LearningMeetingWin}.
     *
     * @param title название окна.
     * @throws IOException возможно нет LearningMeetingWin.fxml или же в нем содержатся ошибки.
     */
    public LearningMeetingWin(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LearningMeetingWin.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("LearningMeetingWin.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
        controller.Close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateClosed(controller.confirmation.isSelected());
                FXManipulate.getStage(controller.Close).close();
            }
        });
        controller.confirmation.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateClosed(controller.confirmation.isSelected());
                FXManipulate.getStage(controller.Close).close();
            }
        });
        controller.Close.setOnAction(event -> {
            activateClosed(controller.confirmation.isSelected());
            FXManipulate.getStage(controller.Close).close();
        });
    }

    @Override
    public void setText(String text) {
    }
}
