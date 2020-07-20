package FlowWindows.Notice;

import Arithmetic.FXManipulate;
import FlowWindows.FlowWindow;
import FlowWindows.Load.Load;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Класс вопросительного окна.
 * <p>
 * Задаёт пользователю общие вопросы. А также получает на них ответы.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Notice extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Notice#Notice(String)}.
     */
    public final Controller controller;


    /**
     * ID. Необходим для индентификации событий в {@link Notice#activateAnswer(boolean)}.
     */
    private int id = 0;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#ok} или же {@link Controller#cancel}.
     */
    private ActionListener answer = null;


    /**
     * Создать событие {@link Notice#answer}.
     * <p>
     * В source true(Нажата кнопка {@link Controller#ok}) или false(Нажата кнопка {@link Controller#cancel}).
     *
     * @param answer новое событие.
     */
    public void setOnAnswer(ActionListener answer) {
        this.answer = answer;
    }


    /**
     * Активировать событие {@link Notice#answer}.
     *
     * @param button true(Нажата кнопка {@link Controller#ok}) или false(Нажата кнопка {@link Controller#cancel}).
     */
    private void activateAnswer(boolean button) {
        if (answer != null) {
            ActionEvent actionEvent = new ActionEvent(button, id++, "Delete");
            answer.actionPerformed(actionEvent);
        }
    }

    /**
     * Создание объекта {@link Notice}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Notice.fxml или же в нем содержатся ошибки.
     */
    public Notice(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Notice.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Notice.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
        controller.ok.setOnAction(actionEvent -> {
            activateAnswer(true);
            FXManipulate.getStage(controller.ok).close();
        });
        controller.cancel.setOnAction(actionEvent -> {
            activateAnswer(false);
            FXManipulate.getStage(controller.cancel).close();
        });
        controller.ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateAnswer(true);
                FXManipulate.getStage(controller.ok).close();
            }
        });
        controller.cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateAnswer(false);
                FXManipulate.getStage(controller.cancel).close();
            }
        });
    }

    @Override
    public void setText(String text) {
        controller.text.setText(text);
    }

    public void setImage(Image image) {
        controller.image.setImage(image);
    }
}
