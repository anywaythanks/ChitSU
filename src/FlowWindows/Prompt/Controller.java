package FlowWindows.Prompt;

import Arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Кнопка ок.
     */
    @FXML
    Button ok;
    /**
     * Заголовок окна.
     */
    @FXML
    Label text;
    /**
     * Основной контент окна.
     */
    @FXML
    VBox content;

    @FXML
    public void initialize
            () {
        ok.setOnAction(event -> FXManipulate.getStage(ok).close());
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(ok).close();
        });

        ok.setFocusTraversable(true);
    }
}
