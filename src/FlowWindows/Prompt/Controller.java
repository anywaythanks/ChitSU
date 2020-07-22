package FlowWindows.Prompt;

import Arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    Button ok;

    @FXML
    Label text;

    @FXML
    VBox cont;

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
