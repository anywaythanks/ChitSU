package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class ControlNot {
    @FXML
    Button ok, cancel;
    @FXML
    Label text;
    public static String textLabel;
    public static int operation = 0;

    private void funCancel() {
        textLabel = null;
        if (Controller.stageBuf1 != null)
            Controller.stageBuf1.close();
        if (Controller.stageBuf != null)
            Controller.stageBuf.close();
    }

    private void funOK() {
        if (Controller.stageBuf1 != null)
            Controller.stageBuf1.close();
        if (Controller.stageBuf != null)
            Controller.stageBuf.close();
        switch (operation) {
            case 0:
                Logic.del(ControlLoad1.name);
                ControlSave2.textLabel = "Сохранение успешно удалено.";
                break;
            case 1:
                Logic.fClear(Logic.F_SAVE);
                ControlSave2.textLabel = "Сохранения успешно удалены.";
                break;
            case 2:
                Controller.clearSud = true;
                ControlSave2.textLabel = "Поле очищено.";
                break;
        }
        Controller.stageBuf = Controller.log.setStage(Controller.stageBuf, "Успех", "Markup/sucefull.fxml", "Style/Load.css");
        Controller.stageBuf.show();
    }

    @FXML
    public void initialize
            () {

        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOK();
        });
        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funCancel();
        });
        ok.setFocusTraversable(true);
        cancel.setFocusTraversable(false);
        text.setText((textLabel != null) ? textLabel : text.getText());
        ok.setOnAction(event -> funOK());
        cancel.setOnAction(event -> funCancel());
    }
}
