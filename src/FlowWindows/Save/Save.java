package FlowWindows.Save;

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
 * Класс окна помощи.
 * <p>
 * Показывает пользователю помощь по игре.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Save extends Stage implements FlowWindow {

    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Save#Save(String)}.
     */
    public final Controller controller;


    /**
     * ID. Необходим для идентификации событий в {@link Save#activateSave(String)}.
     */
    private int id = 0;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#ok}.
     */
    private ActionListener save = null;


    /**
     * Создать событие {@link Save#save}.
     * <p>
     * В source {@link String} сохранения.
     *
     * @param answer новое событие.
     */
    public void setOnSave(ActionListener answer) {
        this.save = answer;
    }


    /**
     * Активировать событие {@link Save#save}.
     */
    private void activateSave(String saveSt) {
        if (save != null) {
            ActionEvent actionEvent = new ActionEvent(saveSt, id++, "Save");
            save.actionPerformed(actionEvent);
        }
    }

    /**
     * Создание объекта {@link Save}.
     *
     * @param title название окна.
     * @throws IOException возможно нет Help.fxml или же в нем содержатся ошибки.
     */
    public Save(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Save.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Save.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
        controller.ok.setOnAction(event -> {
            activateSave(controller.textField.getText());
            FXManipulate.getStage(controller.ok).close();
        });
        controller.textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateSave(controller.textField.getText());
                FXManipulate.getStage(controller.ok).close();
            }
        });
    }

    @Override
    public void setText(String text) {
    }
}
