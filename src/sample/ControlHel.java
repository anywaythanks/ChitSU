package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ControlHel {
    @FXML
    public Button Close;
    @FXML
    public Hyperlink address;
    @FXML
    public Label copy;

    private void appearance(Label cloud) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(cloud.opacityProperty(), cloud.getOpacity())),
                new KeyFrame(Duration.millis(1000), new KeyValue(cloud.opacityProperty(), 0)));
        timeline.setCycleCount(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), cloud);
        translateTransition.setFromY(0);
        translateTransition.setToY(-100);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        timeline.play();
        translateTransition.play();
    }

    @FXML
    public void initialize
            () {
        Close.setFocusTraversable(true);
        address.setFocusTraversable(false);
        Close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Controller.stageBuf.close();
        });
        Close.setOnAction(event -> Controller.stageBuf.close());
        address.setOnAction(event -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(address.getText()), null);
            copy.setVisible(true);
            if (copy.getOpacity() == 1)
                appearance(copy);
        });
    }
}
