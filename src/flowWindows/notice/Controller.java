package flowWindows.notice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Кнопка ok.
     */
    @FXML
    Button ok;
    /**
     * Кнопка cancel.
     */
    @FXML
    Button cancel;
    /**
     * {@link Label} элемент с текстом.
     */
    @FXML
    Label text;
    /**
     * {@link Image} элемент.
     */
    @FXML
    ImageView image;
    @FXML
    public void initialize() {
        ok.setFocusTraversable(true);
    }
}
