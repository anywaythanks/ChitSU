package FlowWindows;

import javafx.stage.Stage;

import java.io.IOException;

public interface FlowWindow {
    Stage setStage(String title) throws IOException;

    void setText(String text);

    void setTitle(String title);
}
