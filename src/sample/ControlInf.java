package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ControlInf {
    @FXML
    public Button Close;
    @FXML
    Label text1, text2, text3, text4, textB1, textB2;
    @FXML
    AnchorPane cloud1, cloud2, cloud3, cloud4;

    private void appearance(AnchorPane cloud, boolean disable, boolean up) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(cloud.opacityProperty(), cloud.getOpacity())),
                new KeyFrame(Duration.millis(300), new KeyValue(cloud.opacityProperty(), disable ? 0 : 1)));
        timeline.setCycleCount(1);
        timeline.play();
        cloud.setDisable(disable);
        if(up){
            text1.setDisable(!disable);
            text2.setDisable(!disable);
        }
    }

    @FXML
    public void initialize
            () {
        textB1.setOpacity(1);
        textB2.setOpacity(1);
        text1.setOnMouseMoved(event -> {
            if (cloud1.isDisable())
                appearance(cloud1, false,false);
        });
        text1.setOnMouseExited(event -> {
            if (event.getY() > 0)
                appearance(cloud1, true,false);
        });
        cloud1.setOnMouseExited(event -> appearance(cloud1, true,false));
        text2.setOnMouseMoved(event -> {
            if (cloud2.isDisable())
                appearance(cloud2, false,false);
        });
        text2.setOnMouseExited(event -> {
            if (event.getY() > 0)
                appearance(cloud2, true,false);
        });
        cloud2.setOnMouseExited(event -> appearance(cloud2, true,false));
        text3.setOnMouseMoved(event -> {
            if (cloud3.isDisable())
                appearance(cloud3, false,true);
        });
        text3.setOnMouseExited(event -> {
            if (event.getY() < text3.getHeight())
                appearance(cloud3, true,true);
        });
        cloud3.setOnMouseExited(event -> appearance(cloud3, true,true));
        text4.setOnMouseMoved(event -> {
            if (cloud4.isDisable())
                appearance(cloud4, false,true);
        });
        text4.setOnMouseExited(event -> {
            if (event.getY() < text4.getHeight())
                appearance(cloud4, true,true);
        });
        cloud4.setOnMouseExited(event -> appearance(cloud4, true,true));

        Close.setFocusTraversable(true);
        Close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Controller.stageBuf.close();
        });
        Close.setOnAction(event -> Controller.stageBuf.close());
    }
}
