package sudoku;

import arithmetic.FXManipulate;
import arithmetic.Indexing;
import arithmetic.NumberSystem;
import javafx.beans.NamedArg;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Класс клетки судоку.
 *
 * @author anywaythanks
 * @version 1.0
 * @startuml Cells
 * namespace Sudoku {
 * class Sudoku.Cells {
 * - FONT_SCALE : double
 * - GRID_PANE_SCALE : double
 * - candidates : Label[]
 * - cellsVal : Label
 * - gridPane : GridPane
 * - scale : double
 * - size : int
 * - sizeBox : int
 * + Cells()
 * + activateAllCandidate()
 * + activateCandidate()
 * + deactivateAllCandidate()
 * + deactivateCandidate()
 * + getCellsVal()
 * + getScale()
 * + getSizeBox()
 * + setCellsVal()
 * + setScale()
 * + setSizeBox()
 * - getCandidateSize()
 * - getCellValSize()
 * - getGridPaneSize()
 * }
 * }
 * namespace Arithmetic {
 * class Arithmetic.Indexing {
 * {static} + coordinateMassive()
 * {static} + indexCells()
 * }
 * }
 * namespace Arithmetic {
 * class Arithmetic.NumberSystem {
 * {static} + getCharNum()
 * {static} + getNumChar()
 * }
 * }
 * Sudoku.Cells -up-|> javafx.scene.layout.GridPane
 * Sudoku.Cells -up.> Arithmetic.Indexing
 * Sudoku.Cells -up.> Arithmetic.NumberSystem
 * @enduml
 */
public class Cell extends GridPane {
    /**
     * Размер box судоку.
     */
    private int sizeBox;
    /**
     * Размер столбца или строки судоку.
     */
    private int size;


    /**
     * Кандидаты.
     */
    private Text[] candidates;
    /**
     * Значение клетки.
     */
    private Text cellVal;
    /**
     * Место, где хранятся {@link Cell#candidates}.
     */
    private GridPane gridPane;

    /**
     * Множитель увеличения.
     * <p>
     * Им можно контролировать размер клетки.
     */
    private double scale = 1;
    /**
     * То, во сколько раз расширяется модель, для решения проблем со шрифтами.
     */
    private final double RENDERING_SIZE = 20;
    /**
     * Фиксированное значение размера {@link Cell} и {@link Cell#gridPane}.
     */
    private final double GRID_PANE_SCALE = 4;
    /**
     * Размер шрифтов.
     */
    private final double FONT_SCALE = 75;

    /**
     * Создание объекта {@link Cell}
     *
     * @param sizeBox  размер box клетки.
     * @param valCells значение клетки.
     */
    public Cell(@NamedArg(value = "sizeBox", defaultValue = "3") int sizeBox, @NamedArg(value = "valCells", defaultValue = "0") int valCells) {
        setStyle(getGridPaneSize());
        setSizeBox(sizeBox);
        setCellVal(valCells);
        setColorBorder(Color.BLACK);
    }


    /**
     * Получить размер {@link Cell} и {@link Cell#gridPane}.
     *
     * @return размер {@link Cell} и {@link Cell#gridPane}.
     */
    private String getGridPaneSize() {
        return "-fx-min-width: " + GRID_PANE_SCALE * RENDERING_SIZE + "em; -fx-min-height: " + GRID_PANE_SCALE * RENDERING_SIZE + "em;";
    }

    /**
     * Получить размер {@link Cell#cellVal}.
     *
     * @return размер {@link Cell#cellVal}.
     */
    private String getCellValSize() {
        return "-fx-font-size: " + FONT_SCALE * GRID_PANE_SCALE * RENDERING_SIZE + "%; ";
    }

    /**
     * Получить размер {@link Cell#candidates}.
     *
     * @return размер {@link Cell#candidates}.
     */
    private String getCandidateSize() {
        return "-fx-font-size: " + (FONT_SCALE * GRID_PANE_SCALE) / sizeBox * RENDERING_SIZE + "%;";
    }


    /**
     * Задать значение для {@link Cell#sizeBox}.
     *
     * @param sizeBox новое значение для {@link Cell#sizeBox}.
     */
    public void setSizeBox(int sizeBox) {
        this.sizeBox = sizeBox;
        size = sizeBox * sizeBox;
        if (getChildren().size() != 0)
            getChildren().remove(0, getChildren().size());

        gridPane = new GridPane();
        gridPane.setStyle(getGridPaneSize());
        for (int i = 0; i < sizeBox; i++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().add(columnConstraints);

            final RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        add(gridPane, 0, 0);
        setHalignment(gridPane, HPos.CENTER);
        setValignment(gridPane, VPos.CENTER);
        candidates = new Text[size];
        for (int i = 0; i < size; ++i) {
            candidates[i] = new Text(NumberSystem.getCharNum(i + 1));
            setHalignment(candidates[i], HPos.CENTER);
            setValignment(candidates[i], VPos.CENTER);
            candidates[i].setOpacity(0);
            candidates[i].setStyle(getCandidateSize());
            gridPane.add(candidates[i], Indexing.coordinateMassive(i, sizeBox)[1], Indexing.coordinateMassive(i, sizeBox)[0]);
        }

        cellVal = new Text(NumberSystem.getCharNum(0));
        cellVal.setStyle(getCellValSize());

        setHalignment(cellVal, HPos.CENTER);
        setValignment(cellVal, VPos.CENTER);
        add(cellVal, 0, 0);
    }

    /**
     * Задать значение для {@link Cell#cellVal}.
     *
     * @param cellVal новое значение для {@link Cell#cellVal}.
     */
    public void setCellVal(int cellVal) {
        this.cellVal.setText(NumberSystem.getCharNum(cellVal));
    }

    /**
     * Задать значение для {@link Cell#scale}.
     *
     * @param scale новое значение для {@link Cell#scale}.
     */
    public void setScale(double scale) {
        this.scale = scale;
        setScaleX(1 / (RENDERING_SIZE / scale));
        setScaleY(1 / (RENDERING_SIZE / scale));
    }

    /**
     * Получить значение {@link Cell#sizeBox}.
     *
     * @return значение {@link Cell#sizeBox}.
     */
    public int getSizeBox() {
        return sizeBox;
    }

    /**
     * Получить значение {@link Cell#cellVal}.
     *
     * @return значение {@link Cell#cellVal}.
     */
    public int getCellVal() {
        return NumberSystem.getNumChar(cellVal.getText());
    }

    /**
     * Получить значение {@link Cell#scale}.
     *
     * @return значение {@link Cell#scale}.
     */
    public double getScale() {
        return scale;
    }


    /**
     * Активировать(Сделать видимыми) {@link Cell#candidates}.
     *
     * @param ids id {@link Cell#candidates}, которые нужно активировать.
     */
    public void activateCandidate(int... ids) {
        for (int id : ids)
            candidates[id].setOpacity(1);
    }

    /**
     * Деактивировать(Сделать невидимыми) {@link Cell#candidates}.
     *
     * @param ids id {@link Cell#candidates}, которые нужно деактивировать.
     */
    public void deactivateCandidate(int... ids) {
        for (int id : ids)
            candidates[id].setOpacity(0);

    }

    /**
     * Активировать(Сделать видимыми) все {@link Cell#candidates}.
     */
    public void deactivateAllCandidate() {
        for (Text candidate : candidates)
            candidate.setOpacity(0);

    }

    /**
     * Деактивировать(Сделать невидимыми) все {@link Cell#candidates}.
     */
    public void activateAllCandidate() {
        for (Text candidate : candidates)
            candidate.setOpacity(1);

    }

    public void setColorCellVal(Color colorCellVal) {
        cellVal.setStyle(getCellValSize() + "-fx-fill: " + FXManipulate.getRGBAModel(colorCellVal) + ";");
    }

    public void setColorBorder(Color colorBorder) {
        setBorder(new Border(new BorderStroke(colorBorder, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(RENDERING_SIZE, RENDERING_SIZE, RENDERING_SIZE, RENDERING_SIZE))));
    }

    public void setColorCandidates(Color... colorCandidates) {
        for (int i = 0; i < colorCandidates.length; ++i)
            candidates[i].setStyle(getCandidateSize() + "-fx-fill: " + FXManipulate.getRGBAModel(colorCandidates[i]) + ";");
    }

    public void setColorCandidate(int id, Color colorCandidate) {
        candidates[id].setStyle(getCandidateSize() + "-fx-fill: " + FXManipulate.getRGBAModel(colorCandidate) + ";");
    }
}

