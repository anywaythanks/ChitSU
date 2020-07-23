package flowWindows.save;

import arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {
    /**
     * Кнопка ок.
     */
    @FXML
    Button ok;
    /**
     * Кнопка cancel.
     */
    @FXML
    Button cancel;
    /**
     * Окно, для ввода имени сохранения.
     */
    @FXML
    TextField textField;

    @FXML
    public void initialize() {
        cancel.setOnAction(event -> FXManipulate.getStage(cancel).close());

        textField.setFocusTraversable(true);
    }
}
