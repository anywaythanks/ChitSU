package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class ControlLoad1 {
    @FXML
    Button ok, cancel, delete, deleteAll;
    @FXML
    ComboBox<String> saves;
    public static String name;


    private void setSaves() {
        for (int i = 0; i < Logic.masName.length; ++i)
            saves.getItems().addAll(Logic.masName[i]);

        saves.setValue(Logic.masName[0]);
        saves.setId(Logic.masName[0]);
        saves.setPromptText(Logic.masName[0]);

    }

    private void funOK() {
        ControlSave2.textLabel = "Успешно загружено.";
        name = saves.getValue();
        Controller.loading = true;
        Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Успех", "Markup/sucefull.fxml", "Style/Load.css");
        Controller.stageBuf1.show();
        Controller.stageBuf.close();

    }

    @FXML
    public void initialize
            () {
        setSaves();
        ok.setOnAction(event -> funOK());
        cancel.setOnAction(event -> Controller.stageBuf.close());
        delete.setOnAction(event -> {
            name = saves.getValue();
            ControlNot.textLabel = "Вы точно хотите удалить это сохранение?";
            Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Удаление", "Markup/notice.fxml", "Style/Load.css");
            Controller.stageBuf1.show();
            Controller.stageBuf.close();
        });
        deleteAll.setOnAction(event -> {
            name = saves.getValue();
            ControlNot.operation = 1;
            ControlNot.textLabel = "Вы точно хотите удалить все сохранения?";
            Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Удаление", "Markup/notice.fxml", "Style/Load.css");
            Controller.stageBuf1.show();
            Controller.stageBuf.close();
        });

        cancel.setFocusTraversable(false);
        ok.setFocusTraversable(true);
        delete.setFocusTraversable(false);
        delete.setFocusTraversable(false);
        saves.setFocusTraversable(false);
        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Controller.stageBuf.close();
        });
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOK();
        });
    }
}
