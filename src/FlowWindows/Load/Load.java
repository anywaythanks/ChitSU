package FlowWindows.Load;

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
 * Класс окна для загрузки сохранений.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Load extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Load#Load(String)}.
     */
    public final Controller controller;

    /**
     * ID. Необходим для индентификации событий в {@link Load#activateAllDelete()}, {@link Load#activateDelete(String)} и {@link Load#activateLoad(String)}.
     */
    private int id = 0;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#delete}.
     */
    private ActionListener delete = null;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#deleteAll}.
     */
    private ActionListener allDelete = null;
    /**
     * Срабатывает при нажатии на кнопку {@link Controller#ok}.
     */
    private ActionListener load = null;

    /**
     * Создать событие {@link Load#allDelete}.
     *
     * @param allDelete новое событие.
     */
    public void setAllDelete(ActionListener allDelete) {
        this.allDelete = allDelete;
    }

    /**
     * Создать событие {@link Load#delete}.
     * <p>
     * В source возвращает название сохранения, которое нужно удалить.
     *
     * @param delete новое событие.
     */
    public void setDelete(ActionListener delete) {
        this.delete = delete;
    }

    /**
     * Создать событие {@link Load#load}.
     * <p>
     * В source возвращает название сохранения, которое нужно загрузить.
     *
     * @param load новое событие.
     */
    public void setLoad(ActionListener load) {
        this.load = load;
    }

    /**
     * Активировать событие {@link Load#allDelete}.
     */
    private void activateAllDelete() {
        if (allDelete != null) {
            ActionEvent actionEvent = new ActionEvent(this, id++, "All delete");
            allDelete.actionPerformed(actionEvent);
        }
    }

    /**
     * Активировать событие {@link Load#delete}.
     *
     * @param nameSave название сохранения.
     */
    private void activateDelete(String nameSave) {
        if (delete != null) {
            ActionEvent actionEvent = new ActionEvent(nameSave, id++, "Delete");
            delete.actionPerformed(actionEvent);
        }
    }

    /**
     * Активировать событие {@link Load#load}.
     *
     * @param nameSave название сохранения.
     */
    private void activateLoad(String nameSave) {
        if (load != null) {
            ActionEvent actionEvent = new ActionEvent(nameSave, id++, "Load");
            load.actionPerformed(actionEvent);
        }
    }


    /**
     * Создать класс {@link Load}.
     *
     * @param title название окна.
     * @throws IOException ошибки при создании окна. Возможно нет Load.fxml или же Load.css.
     */
    public Load(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Load.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Load.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
        controller.delete.setOnAction(actionEvent -> {
            System.out.println(controller.saves.getItems().size());
            boolean isClose = controller.saves.getItems().size() <= 1;
            activateDelete(controller.saves.getValue());

            if (isClose)
                FXManipulate.getStage(controller.delete).close();
        });
        controller.deleteAll.setOnAction(actionEvent -> {
            activateAllDelete();
            FXManipulate.getStage(controller.deleteAll).close();
        });
        controller.ok.setOnAction(actionEvent -> {
            activateLoad(controller.saves.getValue());
            FXManipulate.getStage(controller.ok).close();
        });
        controller.ok.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                activateLoad(controller.saves.getValue());
                FXManipulate.getStage(controller.ok).close();
            }
        });
        controller.saves.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                activateLoad(controller.saves.getValue());
                FXManipulate.getStage(controller.saves).close();
            }
            if (actionEvent.getCode() == KeyCode.DELETE) {
                activateDelete(controller.saves.getValue());
                FXManipulate.getStage(controller.saves).close();
            }
        });
    }

    @Override
    public void setText(String text) {
    }

    /**
     * Удалить сохранение в {@link Controller#saves}.
     *
     * @param save сохранение, которое нужно удалить.
     */
    public void removeSave(String save) {
        controller.saves.getItems().removeAll(save);
        if (controller.saves.getItems().size() != 0) {
            controller.saves.setValue(controller.saves.getItems().get(0));
            controller.saves.setId(controller.saves.getItems().get(0));
            controller.saves.setPromptText(controller.saves.getItems().get(0));
        }

    }

    /**
     * Создать сохранения в {@link Controller#saves}.
     *
     * @param saves массив сохранений.
     */
    public void setSaves(String[] saves) {
        for (String save : saves)
            controller.saves.getItems().addAll(save);

        if (saves.length != 0) {
            controller.saves.setValue(saves[0]);
            controller.saves.setId(saves[0]);
            controller.saves.setPromptText(saves[0]);
        }
    }
}
