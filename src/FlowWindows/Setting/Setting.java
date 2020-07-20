package FlowWindows.Setting;

import Arithmetic.FXManipulate;
import FlowWindows.FlowWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Класс окна для настройки игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Setting extends Stage implements FlowWindow {
    /**
     * Класс {@link Controller}.
     * <p>
     * Необходим, для получения некоторых {@link javafx.scene.Node} в {@link Setting#Setting(String)}.
     */
    public final Controller controller;

    /**
     * ID. Необходим для идентификации событий в {@link Setting#activateReconfiguration()} и {@link Setting#activateDischarging()}.
     */
    private int id = 0;

    /**
     * Срабатывает при нажатии на кнопку {@link Controller#ok} и  {@link Controller#apply}.
     * <p>
     * Проще говоря вызывается, когда надо переписать настройки в игре.
     */
    private ActionListener reconfiguration = null;

    /**
     * Срабатывает при нажатии на кнопку {@link Controller#discharge}.
     * <p>
     * Проще говоря вызывается, когда надо сбросить настройки.
     */
    private ActionListener discharging = null;

    /**
     * Создать событие {@link Setting#discharging}.
     *
     * @param discharging новое событие.
     */
    public void setOnDischarging(ActionListener discharging) {
        this.discharging = discharging;
    }

    /**
     * Создать событие {@link Setting#reconfiguration}.
     *
     * @param reconfiguration новое событие.
     */
    public void setOnReconfiguration(ActionListener reconfiguration) {
        this.reconfiguration = reconfiguration;
    }

    /**
     * Активировать событие {@link Setting#reconfiguration}.
     */
    private void activateReconfiguration() {
        if (controller.isApply && reconfiguration != null) {
            ActionEvent actionEvent = new ActionEvent(this, id++, "Reconfiguration");
            reconfiguration.actionPerformed(actionEvent);
        }
    }

    /**
     * Активировать событие {@link Setting#discharging}.
     */
    private void activateDischarging() {
        if (reconfiguration != null) {
            ActionEvent actionEvent = new ActionEvent(this, id++, "Discharging");
            discharging.actionPerformed(actionEvent);
        }
    }


    /**
     * Создать класс {@link Setting}.
     *
     * @param title название окна.
     * @throws IOException ошибки при создании окна. Возможно нет Setting.fxml или же Setting.css.
     */
    public Setting(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Setting.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        initModality(Modality.WINDOW_MODAL);
        setTitle(title);
        hide();
        scene.getStylesheets().add(getClass().getResource("Setting.css").toExternalForm());
        setScene(scene);
        setResizable(System.getProperty("os.name").equals("Linux"));

        controller = loader.getController();
        controller.apply.setOnAction(event -> {
            activateReconfiguration();
            controller.isApply = false;
        });
        controller.ok.setOnAction(event -> {
            activateReconfiguration();
            FXManipulate.getStage(controller.ok).close();
        });
        controller.ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                activateReconfiguration();
                FXManipulate.getStage(controller.ok).close();
            }
        });
        controller.discharge.setOnAction(actionEvent -> {
            controller.isApply = true;
            activateDischarging();
        });
    }


    /**
     * Задать значение {@link Controller#colorBaseFont}.
     * @param colorBaseFont новое значение {@link Controller#colorBaseFont}.
     */
    public void setColorBaseFont(ColorPicker colorBaseFont) {
        controller.colorBaseFont = colorBaseFont;
    }

    /**
     * Задать значение {@link Controller#colorNoCandidateFont}.
     * @param colorNoCandidateFont новое значение {@link Controller#colorNoCandidateFont}.
     */
    public void setColorNoCandidateFont(ColorPicker colorNoCandidateFont) {
        controller.colorNoCandidateFont = colorNoCandidateFont;
    }

    /**
     * Задать значение {@link Controller#colorCandidateFont}.
     * @param colorCandidateFont новое значение {@link Controller#colorCandidateFont}.
     */
    public void setColorCandidateFont(ColorPicker colorCandidateFont) {
        controller.colorCandidateFont = colorCandidateFont;
    }

    /**
     * Задать значение {@link Controller#colorBaseLabel}.
     * @param colorBaseLabel новое значение {@link Controller#colorBaseLabel}.
     */
    public void setColorBaseLabel(ColorPicker colorBaseLabel) {
        controller.elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + FXManipulate.getRGBAModel(FXManipulate.setOpacity(colorBaseLabel.getValue(), 1)));
        controller.colorBaseLabel = colorBaseLabel;
    }

    /**
     * Задать значение {@link Controller#colorWrongLabel}.
     * @param colorWrongLabel новое значение {@link Controller#colorWrongLabel}.
     */
    public void setColorWrongLabel(ColorPicker colorWrongLabel) {
        controller.colorWrongLabel = colorWrongLabel;
    }

    /**
     * Задать значение {@link Controller#colorCoincidentalLabel}.
     * @param colorCoincidentalLabel новое значение {@link Controller#colorCoincidentalLabel}.
     */
    public void setColorCoincidentalLabel(ColorPicker colorCoincidentalLabel) {
        controller.colorCoincidentalLabel = colorCoincidentalLabel;
    }

    /**
     * Задать значение {@link Controller#opacity}.
     * @param opacity новое значение {@link Controller#opacity}.
     */
    public void setSliderOpacity(double opacity) {
        controller.opacity.setValue(opacity * 100);
        controller.elements.setOpacity(controller.opacity.getValue() / 100);
    }
    /**
     * Задать значение {@link Controller#fullScreen}.
     * @param fullScreen новое значение {@link Controller#fullScreen}.
     */
    public void setButtonFullScreen(boolean fullScreen) {
        controller.fullScreen.setSelected(fullScreen);
    }


    /**
     * Получить значение {@link Controller#colorBaseFont}.
     * @return {@link Controller#colorBaseFont}.
     */
    public Color getColorBaseFont() {
        return controller.colorBaseFont.getValue();
    }

    /**
     * Получить значение {@link Controller#colorNoCandidateFont}.
     * @return {@link Controller#colorNoCandidateFont}.
     */
    public Color getColorNoCandidateFont() {
        return controller.colorNoCandidateFont.getValue();
    }

    /**
     * Получить значение {@link Controller#colorCandidateFont}.
     * @return {@link Controller#colorCandidateFont}.
     */
    public Color getColorCandidateFont() {
        return controller.colorCandidateFont.getValue();
    }

    /**
     * Получить значение {@link Controller#colorBaseLabel}.
     * @return {@link Controller#colorBaseLabel}.
     */
   public Color getColorBaseLabel() {
        return controller.colorBaseLabel.getValue();

    }

    /**
     * Получить значение {@link Controller#colorWrongLabel}.
     * @return {@link Controller#colorWrongLabel}.
     */
    public Color getColorWrongLabel() {
        return controller.colorWrongLabel.getValue();
    }

    /**
     * Получить значение {@link Controller#colorCoincidentalLabel}.
     * @return {@link Controller#colorCoincidentalLabel}.
     */
    public Color getColorCoincidentalLabel() {
        return controller.colorCoincidentalLabel.getValue();
    }

    /**
     * Получить значение {@link Controller#opacity}.
     * @return {@link Controller#opacity}.
     */
    public double getSliderOpacity() {
        return controller.opacity.getValue() / 100;
    }

    /**
     * Получить значение {@link Controller#fullScreen}.
     * @return {@link Controller#fullScreen}.
     */
    public boolean getButtonFullScreen() {
        return controller.fullScreen.isSelected();
    }


    @Override
    public void setText(String text) {
    }

}
