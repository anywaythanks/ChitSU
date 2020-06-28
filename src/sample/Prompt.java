package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Prompt {

    @FXML
    Button ok;

    @FXML
    Label text;

    @FXML
    VBox cont;

    public static String textLabel;
    public static String[] textVBox;

    private void funOk() {
        if (Controller.stageBuf != null)
            Controller.stageBuf.close();
        if (Controller.stageBuf1 != null)
            Controller.stageBuf1.close();
        textLabel = null;
        textVBox = null;
    }

    @FXML
    public void initialize
            () {
        if (textLabel != null)
            text.setText(textLabel);
        if (textVBox != null)
            for (String s : textVBox) {
                Label label = new Label(s);
                label.setWrapText(true);
                label.setStyle("-fx-font-style: italic");
                label.setFont(Font.font(14));
                label.setMaxSize(1000, 1000);
                cont.getChildren().addAll(label);

            }

        ok.setOnAction(event -> funOk());
        ok.setFocusTraversable(true);
        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) funOk();
        });
    }
}
