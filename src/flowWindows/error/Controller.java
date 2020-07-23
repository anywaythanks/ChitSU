package flowWindows.error;

import arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
     * {@link Label} элемент с текстом.
     */
    @FXML
    Label text;

    @FXML
    public void initialize() {
        ok.setOnAction(event -> FXManipulate.getStage(ok).close());
        ok.setFocusTraversable(true);
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(ok).close();
        });
    }
}
