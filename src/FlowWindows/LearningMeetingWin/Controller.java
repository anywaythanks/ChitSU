package FlowWindows.LearningMeetingWin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class Controller {
    @FXML
    public Button Close;
    @FXML
    public RadioButton confirmation;

    @FXML
    public void initialize() {
        Close.setFocusTraversable(true);
    }
}
