package FlowWindows.LearningMeetingWin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class Controller {
    /**
     * кнопка OK.
     */
    @FXML
    public Button close;
    /**
     * {@link RadioButton} больше не показывать.
     */
    @FXML
    public RadioButton confirmation;

    @FXML
    public void initialize() {
        close.setFocusTraversable(true);
    }
}
