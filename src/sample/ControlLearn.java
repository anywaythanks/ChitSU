package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ControlLearn {
    @FXML
    public Button Close;
    @FXML
    public RadioButton confirmation;

    private void funCancel() {
        Controller.winStL = !confirmation.isSelected();
        Controller.stageBuf.close();
    }

    @FXML
    public void initialize
            () {
        Close.setFocusTraversable(true);
        Close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funCancel();
        });
        Close.setOnAction(event -> funCancel());
    }
}
