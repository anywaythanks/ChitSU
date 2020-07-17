package FlowWindows;

import javafx.stage.Stage;

import java.io.IOException;

public interface FlowWindow {
    void setStage(String title) throws IOException;

    void setText(String text);

    void setTitle(String title);

    Stage getStage();
}
