package FlowWindows.Save;

import Arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import sample.ControlSave3;


public class Controller {
    @FXML
    Button ok, cancel;
    @FXML
    TextField textField;

    @FXML
    public void initialize() {
        cancel.setOnAction(event -> FXManipulate.getStage(cancel).close());
        textField.setFocusTraversable(true);
    }
}
