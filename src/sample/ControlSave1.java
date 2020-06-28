package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class ControlSave1 {
    @FXML
    Button ok, cancel;
    @FXML
    TextField textField;

    private void funOk() {
        String s = textField.getText();
        Controller.stageBuf.close();
        if (s != null && s.length() > 0 &&
                s.length() < 30 &&
                s.lastIndexOf((char) 42) == -1 &&
                s.lastIndexOf((char) 666) == -1) {
            boolean t = true;
            Controller.log.save(s);

            if (t) {
                Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Успех", "Markup/sucefull.fxml", "Style/Save.css");
                Controller.stageBuf1.show();
            } else {
                ControlSave3.name = s;
                Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Перезапись", "Markup/save4.fxml", "Style/Save.css");
                Controller.stageBuf1.show();
            }
        } else {
            Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Ошибка", "Markup/eror.fxml", "Style/Save.css");
            Controller.stageBuf1.show();
        }
    }

    @FXML
    public void initialize
            () {
        ok.setOnAction(event -> funOk());
        cancel.setOnAction(event -> Controller.stageBuf.close());
        ok.setFocusTraversable(false);
        cancel.setFocusTraversable(false);
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOk();
        });
    }
}
