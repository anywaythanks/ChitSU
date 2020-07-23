package arithmetic;

import javafx.scene.Node;
import javafx.scene.paint.Color;
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
     *
     * @param node {@link Node}, из которого нужно получить {@link Stage}.
     * @return {@link Stage}.
     */
    public static Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

    /**
     * Получить RedGreenBlueAlpha модель {@link Color}.
     *
     * @param color {@link Color}, модель которого нужно изменить.
     * @return RedGreenBlueAlpha модель в виде {@link String}.
     */
    public static String getRGBAModel(Color color) {
        return "rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + color.getOpacity() + ")";
    }


    /**
     * Задать прозрачность {@link Color}
     *
     * @param color      {@link Color}, прозрачность которого нужно изменить
     * @param newOpacity новое значение прозрачности для цвета
     * @return {@link Color} с новой прозрачностью
     */
    public static Color setOpacity(Color color, double newOpacity) {
        return Color.valueOf("rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + newOpacity + ")");
    }

}
