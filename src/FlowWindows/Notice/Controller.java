package FlowWindows.Notice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    @FXML
    public void initialize() {
        ok.setFocusTraversable(true);
    }
}
