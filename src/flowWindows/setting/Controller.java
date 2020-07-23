package flowWindows.setting;

import arithmetic.FXManipulate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

/**
 * Controller класс, для обработки кнопок.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Controller {
    /**
     * Базовый цвет шрифта элемента.
     */
    @FXML
    public ColorPicker colorBaseFont;
    /**
     * Цвет шрифта элемента, который не является кандидатом.
     */
    @FXML
    public ColorPicker colorNoCandidateFont;
    /**
     * Цвет шрифта элемента, который является кандидатом.
     */
    @FXML
    public ColorPicker colorCandidateFont;
    /**
     * стандартный цвет элемента.
     */
    @FXML
    public ColorPicker colorBaseLabel;
    /**
     * Цвет неправильного элемента.
     */
    @FXML
    public ColorPicker colorWrongLabel;
    /**
     * Цвет совпадающего элемента.
     */
    @FXML
    public ColorPicker colorCoincidentalLabel;

    /**
     * Кнопка cancel.
     */
    @FXML
    public Button cancel;
    /**
     * Кнопка сбросить.
     */
    @FXML
    public Button discharge;
    /**
     * Кнопка ок.
     */
    @FXML
    public Button ok;
    /**
     * Кнопка применить.
     */
    @FXML
    public Button apply;
    /**
     * Шрифт, для примера прозрачности.
     */
    @FXML
    Label elements;
    /**
     * {@link Slider}, который управляет прозрачностью.
     */
    @FXML
    Slider opacity;
    /**
     * Кнопка полноэкранного режима.
     */
    @FXML
    RadioButton fullScreen;
    /**
     * Флаг, который показывает, нужно ли обновлять настройки.
     */
    public boolean isApply = false;
    @FXML
    public void initialize() {
        elements.setOpacity(opacity.getValue() / 100);
        elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + FXManipulate.getRGBAModel(FXManipulate.setOpacity(colorBaseLabel.getValue(), 1)));

        opacity.setOnMouseDragged(event -> {
            elements.setOpacity(opacity.getValue() / 100);
            isApply = true;
        });
        opacity.setOnKeyPressed(event -> {
            elements.setOpacity(opacity.getValue() / 100);
            isApply = true;
        });
        fullScreen.setOnAction(event -> isApply = true);
        colorBaseFont.setOnAction(event -> isApply = true);
        colorNoCandidateFont.setOnAction(event -> isApply = true);
        colorCandidateFont.setOnAction(event -> isApply = true);
        colorBaseLabel.setOnAction(event -> {
            isApply = true;
            elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + FXManipulate.getRGBAModel(FXManipulate.setOpacity(colorBaseLabel.getValue(), 1)));
        });
        colorWrongLabel.setOnAction(event -> isApply = true);
        colorCoincidentalLabel.setOnAction(event -> isApply = true);
        cancel.setOnAction(event -> FXManipulate.getStage(cancel).close());
        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) FXManipulate.getStage(cancel).close();
        });

        ok.setFocusTraversable(true);
    }
}
