package Arithmetic;

import javafx.scene.Node;
import javafx.stage.Stage;
/**
 * Класс для манипуляций с JavaFX, в частности с {@link Node}.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class FXManipulate {
    /**
     * Получить {@link Stage} из любого {@link Node}.
     * @param node {@link Node}, из которого нужно получить {@link Stage}.
     * @return {@link Stage}.
     */
    public static Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

}
