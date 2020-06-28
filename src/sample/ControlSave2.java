package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class ControlSave2 {
    @FXML
    Button ok;

    @FXML
    Label text;
    public static String textLabel;
    private void funOk(){
        if (Controller.stageBuf != null)
            Controller.stageBuf.close();
        if (Controller.stageBuf1 != null)
            Controller.stageBuf1.close();
        textLabel = null;
    }

    @FXML
    public void initialize
            () {
        text.setText((textLabel != null) ? textLabel : text.getText());
        ok.setOnAction(event -> funOk());
        ok.setFocusTraversable(true);
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOk();
        });
    }
}
