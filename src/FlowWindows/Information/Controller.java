package FlowWindows.Information;

import Arithmetic.FXManipulate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Кнопка close.
     */
    @FXML
    public Button Close;
    /**
     * {@link Label} элемент, при наведении появляется {@link Controller#cloud_NurSultan}.
     */
    @FXML
    public Label text_NurSultan;
    /**
     * {@link Label} элемент, при наведении появляется {@link Controller#cloud_gellert17}.
     */
    @FXML
    public Label text_gellert17;
    /**
     * {@link Label} элемент, при наведении появляется {@link Controller#cloud_anywaythanks}.
     */
    @FXML
    public Label text_anywaythanks;
    /**
     * {@link Label} элемент, при наведении появляется {@link Controller#cloud_hexi2003}.
     */
    @FXML
    public Label text_hexi2003;
    /**
     * Буферный {@link Label} элемент, необходим чтобы текст был всегда снизу.
     */
    @FXML
    public Label textBuffer_NurSultan, textBuffer_gellert17;
    /**
     * Авторское облако Nur Sultan.
     */
    @FXML
    public AnchorPane cloud_NurSultan;
    /**
     * Авторское облако gellert17.
     */
    @FXML
    public AnchorPane cloud_gellert17;
    /**
     * Авторское облако anywaythanks.
     */
    @FXML
    public AnchorPane cloud_anywaythanks;
    /**
     * Авторское облако hexi2003.
     */
    @FXML
    public AnchorPane cloud_hexi2003;


    /**
     * Анимация появления {@link Node}.
     * @param node элемент, который нужно анимировать.
     * @param disable нужно ли блокировать {@link Controller#text_gellert17} и {@link Controller#text_NurSultan}.
     * @param up Сверху ли {@link Controller#text_gellert17} и {@link Controller#text_NurSultan}.
     */
    private void appearance(Node node, boolean disable, boolean up) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.opacityProperty(), node.getOpacity())),
                new KeyFrame(Duration.millis(300), new KeyValue(node.opacityProperty(), disable ? 0 : 1)));
        timeline.setCycleCount(1);
        timeline.play();
        node.setDisable(disable);
        if (up) {
            text_NurSultan.setDisable(!disable);
            text_gellert17.setDisable(!disable);
        }
    }

    @FXML
    public void initialize() {
        textBuffer_NurSultan.setOpacity(1);
        textBuffer_gellert17.setOpacity(1);

        text_NurSultan.setOnMouseMoved(event -> {
            if (cloud_NurSultan.isDisable())
                appearance(cloud_NurSultan, false, false);
        });
        text_NurSultan.setOnMouseExited(event -> {
            if (event.getY() > 0)
                appearance(cloud_NurSultan, true, false);
        });
        cloud_NurSultan.setOnMouseExited(event -> appearance(cloud_NurSultan, true, false));
        text_gellert17.setOnMouseMoved(event -> {
            if (cloud_gellert17.isDisable())
                appearance(cloud_gellert17, false, false);
        });
        text_gellert17.setOnMouseExited(event -> {
            if (event.getY() > 0)
                appearance(cloud_gellert17, true, false);
        });
        cloud_gellert17.setOnMouseExited(event -> appearance(cloud_gellert17, true, false));
        text_anywaythanks.setOnMouseMoved(event -> {
            if (cloud_anywaythanks.isDisable())
                appearance(cloud_anywaythanks, false, true);
        });
        text_anywaythanks.setOnMouseExited(event -> {
            if (event.getY() < text_anywaythanks.getHeight())
                appearance(cloud_anywaythanks, true, true);
        });
        cloud_anywaythanks.setOnMouseExited(event -> appearance(cloud_anywaythanks, true, true));
        text_hexi2003.setOnMouseMoved(event -> {
            if (cloud_hexi2003.isDisable())
                appearance(cloud_hexi2003, false, true);
        });
        text_hexi2003.setOnMouseExited(event -> {
            if (event.getY() < text_hexi2003.getHeight())
                appearance(cloud_hexi2003, true, true);
        });
        cloud_hexi2003.setOnMouseExited(event -> appearance(cloud_hexi2003, true, true));

        Close.setFocusTraversable(true);
        Close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(Close).close();
        });
        Close.setOnAction(event -> FXManipulate.getStage(Close).close());
    }
}
