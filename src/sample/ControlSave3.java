package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;


public class ControlSave3 {
    @FXML
    Button ok, cancel;

    public static String name;

    private void funOK() {
        Controller.stageBuf1.close();
        Controller.log.overwrite(name);
        ControlSave2.textLabel = "Успешно перезаписано.";
        Controller.stageBuf = Controller.log.setStage(Controller.stageBuf, "Успех", "Markup/sucefull.fxml", "Style/Save.css");
        Controller.stageBuf.show();
    }

    @FXML
    public void initialize
            () {
        ok.setOnAction(event -> funOK());
        cancel.setOnAction(event -> Controller.stageBuf1.close());
        ok.setFocusTraversable(true);
        cancel.setFocusTraversable(false);
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOK();
        });
        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Controller.stageBuf1.close();
        });
    }
}
