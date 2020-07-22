package FlowWindows.Success;

import Arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

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
    Label text;

    @FXML
    public void initialize
            () {
        ok.setOnAction(event -> FXManipulate.getStage(ok).close());
        ok.setFocusTraversable(true);
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(ok).close();
        });
    }
}
