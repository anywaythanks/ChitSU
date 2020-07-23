package flowWindows.load;

import arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Кнопка ok.
     */
    @FXML
    Button ok;
    /**
     * Кнопка cancel.
     */
    @FXML
    Button cancel;
    /**
     * Кнопка удалить.
     */
    @FXML
    Button delete;
    /**
     * Кнопка удалить всё.
     */
    @FXML
    Button deleteAll;

    /**
     * Объект, в котором выбирается сохранение, для загрузки.
     */
    @FXML
    ComboBox<String> saves;
    public static String name;

    @FXML
    public void initialize
            () {
        cancel.setOnAction(event -> FXManipulate.getStage(cancel).close());
        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(cancel).close();
        });

        ok.setFocusTraversable(true);
    }
}
