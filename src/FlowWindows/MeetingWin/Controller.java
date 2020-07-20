package FlowWindows.MeetingWin;

import Arithmetic.FXManipulate;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

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
    public Button close;
    /**
     * Активное поле с адресом.
     */
    @FXML
    public Hyperlink address;
    /**
     * Движущаяся плашка "Скопировано!".
     */
    @FXML
    public Label copy;

    /**
     * Функция движения объектов.
     *
     * @param node объект, который будет двигаться.
     */
    private void appearance(Node node) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.opacityProperty(), node.getOpacity())),
                new KeyFrame(Duration.millis(1000), new KeyValue(node.opacityProperty(), 0)));
        timeline.setCycleCount(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-100);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        timeline.play();
        translateTransition.play();
    }

    @FXML
    public void initialize
            () {
        close.setFocusTraversable(true);
        address.setFocusTraversable(false);
        close.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                FXManipulate.getStage(close);
            }
        });
        close.setOnAction(event -> FXManipulate.getStage(close).close());
        address.setOnAction(event -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(address.getText()), null);
            copy.setVisible(true);
            if (copy.getOpacity() == 1)
                appearance(copy);
        });
    }
}
