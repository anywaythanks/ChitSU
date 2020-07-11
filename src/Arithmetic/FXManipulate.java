package Arithmetic;

import javafx.scene.Node;
import javafx.stage.Stage;

public class FXManipulate {
    public static Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

}
