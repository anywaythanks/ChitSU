package Kernel;

import Sudoku.Cells;
import javafx.fxml.FXML;

public class Controller {
    @FXML
    Cells sudoku;

    @FXML
    public void initialize() {
        sudoku.setSizeBox(3);
        //sudoku.setCellsVal(12);
        sudoku.activateAllCandidate();
        sudoku.setScale(0.5);

    }
}
