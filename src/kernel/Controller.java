package kernel;

import sudoku.Cells;
import javafx.fxml.FXML;

public class Controller {
    @FXML
    Cells sudoku;

    @FXML
    public void initialize() {
        sudoku.setSizeBox(3);
        //sudoku.setCellsVal(12);
        sudoku.activateAllCandidate();
        //sudoku.setScale(5);

    }
}
