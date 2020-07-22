package Sudoku;

import Arithmetic.Indexing;
import Arithmetic.NumberSystem;
import javafx.beans.NamedArg;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Класс клетки судоку.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Cells extends GridPane {
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
    private Label[] candidates;
    /**
     * Значение клетки.
     */
    private Label cellsVal;
    /**
     * Место, где хранятся {@link Cells#candidates}.
     */
    private GridPane gridPane;

    /**
     * Множитель увеличения.
     * <p>
     * Им можно контролировать размер клетки.
     */
    private double scale = 1;
    /**
     * Фиксированное значение размера {@link Cells} и {@link Cells#gridPane}.
     */
    private final double GRID_PANE_SCALE = 4;
    /**
     * Размер шрифтов.
     */
    private final double FONT_SCALE = 80;

    /**
     * Создание объекта {@link Cells}
     *
     * @param sizeBox  размер box клетки.
     * @param valCells значение клетки.
     */
    public Cells(@NamedArg(value = "sizeBox", defaultValue = "3") int sizeBox, @NamedArg(value = "valCells", defaultValue = "0") int valCells) {
        setStyle(getGridPaneSize());
        setSizeBox(sizeBox);
    }


    /**
     * Получить размер {@link Cells} и {@link Cells#gridPane}.
     *
     * @return размер {@link Cells} и {@link Cells#gridPane}.
     */
    private String getGridPaneSize() {
        return "-fx-min-width: " + scale * GRID_PANE_SCALE + "em; -fx-min-height: " + scale * GRID_PANE_SCALE + "em;";
    }

    /**
     * Получить размер {@link Cells#cellsVal}.
     *
     * @return размер {@link Cells#cellsVal}.
     */
    private String getCellValSize() {
        return "-fx-font-size: " + FONT_SCALE * GRID_PANE_SCALE * scale + "%;";
    }

    /**
     * Получить размер {@link Cells#candidates}.
     *
     * @return размер {@link Cells#candidates}.
     */
    private String getCandidateSize() {
        return "-fx-font-size: " + (FONT_SCALE * GRID_PANE_SCALE) / sizeBox * scale + "%;";
    }

    /**
     * Задать значение для {@link Cells#sizeBox}.
     *
     * @param sizeBox новое значение для {@link Cells#sizeBox}.
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
        candidates = new Label[size];
        for (int i = 0; i < size; ++i) {
            candidates[i] = new Label(NumberSystem.getCharNum(i + 1));
            setHalignment(candidates[i], HPos.CENTER);
            setValignment(candidates[i], VPos.CENTER);
            candidates[i].setOpacity(0);
            candidates[i].setStyle(getCandidateSize());
            gridPane.add(candidates[i], Indexing.coordinateMassive(i, sizeBox)[1], Indexing.coordinateMassive(i, sizeBox)[0]);
        }

        cellsVal = new Label(NumberSystem.getCharNum(0));
        cellsVal.setStyle(getCellValSize());
        setBorder(new Border(new BorderStroke(Color.valueOf("#f9aaaa"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        setHalignment(cellsVal, HPos.CENTER);
        setValignment(cellsVal, VPos.CENTER);
        add(cellsVal, 0, 0);
    }

    /**
     * Задать значение для {@link Cells#cellsVal}.
     *
     * @param cellsVal новое значение для {@link Cells#cellsVal}.
     */
    public void setCellsVal(int cellsVal) {
        this.cellsVal.setText(NumberSystem.getCharNum(cellsVal));
    }

    /**
     * Задать значение для {@link Cells#scale}.
     *
     * @param scale новое значение для {@link Cells#scale}.
     */
    public void setScale(double scale) {
        this.scale = scale;
        setStyle(getGridPaneSize());
        gridPane.setStyle(getGridPaneSize());
        for (int i = 0; i < size; ++i)
            candidates[i].setStyle(getCandidateSize());

        cellsVal.setStyle(getCellValSize());
    }

    /**
     * Получить значение {@link Cells#sizeBox}.
     *
     * @return значение {@link Cells#sizeBox}.
     */
    public int getSizeBox() {
        return sizeBox;
    }

    /**
     * Получить значение {@link Cells#cellsVal}.
     *
     * @return значение {@link Cells#cellsVal}.
     */
    public int getCellsVal() {
        return NumberSystem.getNumChar(cellsVal.getText());
    }

    /**
     * Получить значение {@link Cells#scale}.
     *
     * @return значение {@link Cells#scale}.
     */
    public double getScale() {
        return scale;
    }

    /**
     * Активировать(Сделать видимыми) {@link Cells#candidates}.
     *
     * @param ids id {@link Cells#candidates}, которые нужно активировать.
     */
    public void activateCandidate(int... ids) {
        for (int id : ids)
            candidates[id].setOpacity(1);
    }

    /**
     * Деактивировать(Сделать невидимыми) {@link Cells#candidates}.
     *
     * @param ids id {@link Cells#candidates}, которые нужно деактивировать.
     */
    public void deactivateCandidate(int... ids) {
        for (int id : ids)
            candidates[id].setOpacity(0);

    }

    /**
     * Активировать(Сделать видимыми) все {@link Cells#candidates}.
     */
    public void deactivateAllCandidate() {
        for (Label candidate : candidates)
            candidate.setOpacity(0);

    }

    /**
     * Деактивировать(Сделать невидимыми) все {@link Cells#candidates}.
     */
    public void activateAllCandidate() {
        for (Label candidate : candidates)
            candidate.setOpacity(1);

    }


}
