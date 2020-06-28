package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ControlSet {
    @FXML
    private ColorPicker colorBaseFont,
            colorNoCandidateFont,
            colorCandidateFont,
            colorBaseLabel,
            colorWrongLabel,
            colorCoincidentalLabel;
    private Sudoku sudCopy;

    @FXML
    public Button cancel,
            discharge,
            ok,
            apply;
    private static boolean flag = false;
    @FXML
    Label elements;
    @FXML
    Slider opacity;
    @FXML
    RadioButton fullScreen;

    private void
    setApply() {
        if (flag) {
            sudCopy.setColorBaseFont(colorBaseFont.getValue());
            sudCopy.setColorNoCandidateFont(colorNoCandidateFont.getValue());
            sudCopy.setColorCandidateFont(colorCandidateFont.getValue());
            sudCopy.setColorBaseLabel(colorBaseLabel.getValue());
            sudCopy.setColorWrongLabel(colorWrongLabel.getValue());
            sudCopy.setColorCoincidentalLabel(colorCoincidentalLabel.getValue());
            sudCopy.setDisableOpacity(opacity.getValue() / 100.0);
            Main.stage.setFullScreen(fullScreen.isSelected());
            flag = false;
        }
    }

    @FXML
    public void initialize
            () {
        AnchorPane fon = (AnchorPane) Main.root.getChildrenUnmodifiable().get(0);
        GridPane grid = (GridPane) fon.getChildrenUnmodifiable().get(2);
        fon = (AnchorPane) grid.getChildrenUnmodifiable().get(0);
        sudCopy = (Sudoku) fon.getChildrenUnmodifiable().get(0);

        colorBaseFont.setValue(sudCopy.getColorBaseFont());
        colorNoCandidateFont.setValue(sudCopy.getColorNoCandidateFont());
        colorCandidateFont.setValue(sudCopy.getColorCandidateFont());
        colorBaseLabel.setValue(sudCopy.getColorBaseLabel());
        colorWrongLabel.setValue(sudCopy.getColorWrongLabel());
        colorCoincidentalLabel.setValue(sudCopy.getColorCoincidentalLabel());
        fullScreen.setSelected(Main.stage.isFullScreen());
        elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + Controller.log.getRGBAModel(Controller.log.setOpacity(colorBaseLabel.getValue(), 1)));
        elements.setOpacity(sudCopy.getDisableOpacity());
        opacity.setValue(sudCopy.getDisableOpacity() * 100);

        opacity.setOnMouseDragged(event -> {
            elements.setOpacity(opacity.getValue() / 100);
            flag = true;
        });
        fullScreen.setOnAction(event -> flag = true);
        colorBaseFont.setOnAction(event -> flag = true);
        colorNoCandidateFont.setOnAction(event -> flag = true);
        colorCandidateFont.setOnAction(event -> flag = true);
        colorBaseLabel.setOnAction(event -> {
            flag = true;
            elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + Controller.log.getRGBAModel(Controller.log.setOpacity(colorBaseLabel.getValue(), 1)));
        });
        colorWrongLabel.setOnAction(event -> flag = true);
        colorCoincidentalLabel.setOnAction(event -> flag = true);

        discharge.setOnAction(event -> {
            flag = true;

            String[] ser = Logic.DEF_COL.split(" ");

            colorWrongLabel.setValue(Color.valueOf(ser[0]));
            colorBaseLabel.setValue(Color.valueOf(ser[1]));
            colorCoincidentalLabel.setValue(Color.valueOf(ser[3]));
            colorBaseFont.setValue(Color.valueOf(ser[2]));
            colorNoCandidateFont.setValue(Color.valueOf(ser[4]));
            colorCandidateFont.setValue(Color.valueOf(ser[5]));
            opacity.setValue(Double.valueOf(ser[6]) * 100);
            fullScreen.setSelected(Controller.log.stringToBool(ser[7]));
            elements.setStyle("-fx-font-size: 30; -fx-text-fill: " + Controller.log.getRGBAModel(Controller.log.setOpacity(colorBaseLabel.getValue(), 1)));
            elements.setOpacity(opacity.getValue() / 100.0);
        });
        ok.setOnAction(event -> {
            setApply();
            Controller.stageBuf.close();
        });
        cancel.setOnAction(event -> Controller.stageBuf.close());
        apply.setOnAction(event -> setApply());

        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Controller.stageBuf.close();
        });

        ok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setApply();
                Controller.stageBuf.close();
            }
        });
        cancel.setFocusTraversable(false);
        ok.setFocusTraversable(true);
        discharge.setFocusTraversable(false);
        apply.setFocusTraversable(false);

    }
}
