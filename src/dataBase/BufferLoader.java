package dataBase;

/**
 * Класс-хранилище. Предназначен, для передачи данных сохранений из функций {@link Loader#loadAutosave()} и {@link Loader#loadSave(String)}.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class BufferLoader {
    /**
     * Числовой массив с клеткиами судоку.
     */
    private int[][] cellsSudoku;
    /**
     * bool массив с заблокированными клетками.
     */
    private boolean[][] disableCells;
    /**
     * Режим игры.
     */
    private int mode;
    /**
     * Размер box судоку.
     */
    private int sizeBox;


    /**
     * Получить {@link BufferLoader#disableCells}.
     *
     * @return {@link BufferLoader#disableCells}.
     */
    public boolean[][] getDisableCells() {
        return disableCells;
    }

    /**
     * Получить {@link BufferLoader#mode}.
     *
     * @return {@link BufferLoader#mode}.
     */
    public int getMode() {
        return mode;
    }

    /**
     * Получить {@link BufferLoader#cellsSudoku}.
     *
     * @return {@link BufferLoader#cellsSudoku}.
     */
    public int[][] getCellsSudoku() {
        return cellsSudoku;
    }

    /**
     * Получить {@link BufferLoader#sizeBox}.
     *
     * @return {@link BufferLoader#sizeBox}.
     */
    public int getSizeBox() {
        return sizeBox;
    }


    /**
     * Установить значение {@link BufferLoader#cellsSudoku}.
     *
     * @param cellsSudoku int[][] массив судоку.
     */
    public void setCellsSudoku(int[][] cellsSudoku) {
        this.cellsSudoku = cellsSudoku;
    }

    /**
     * Установить значение {@link BufferLoader#disableCells}.
     *
     * @param disableCells массив bool[][] с заблокированными клетками судоку.
     */
    public void setDisableCells(boolean[][] disableCells) {
        this.disableCells = disableCells;
    }

    /**
     * Установить режим игры {@link BufferLoader#mode}.
     *
     * @param mode режим игры.
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Установить размер box в {@link BufferLoader#sizeBox}.
     *
     * @param sizeBox размер box судоку.
     */
    public void setSizeBox(int sizeBox) {
        this.sizeBox = sizeBox;
    }


}
