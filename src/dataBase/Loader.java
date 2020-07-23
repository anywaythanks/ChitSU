package dataBase;

import arithmetic.Indexing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для загрузки сохранений игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Loader {
    /**
     * Объект {@link ManagerShell}, для работы с базой данных.
     */
    private final ManagerShell managerShell;
    /**
     * Предназначен, для передачи данных сохранений из функций {@link Loader#loadAutosave()} и {@link Loader#loadSave(String)}.
     */
    private BufferLoader bufferLoader;


    /**
     * Создание объекта {@link Loader}.
     *
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link Creator#getManagerShell()}.
     */
    public Loader() throws SQLException, ClassNotFoundException {
        managerShell = Creator.getManagerShell();
        bufferLoader = new BufferLoader();
    }

    /**
     * Создание объекта {@link Loader}.
     *
     * @param otherDB Подключение {@link Loader#managerShell} с особыми настройками.
     */
    public Loader(ManagerShell otherDB) {
        managerShell = otherDB;
    }

    /**
     * Получить {@link Loader#managerShell}.
     *
     * @return {@link Loader#managerShell}.
     */
    public ManagerShell getManagerShell() {
        return managerShell;
    }

    /**
     * Загрузить сохранение.
     *
     * @param nameSave имя сохранения.
     * @return режим игры, размер box, массив судоку, заблокированные клетки в виде объекта {@link BufferLoader}.
     * @throws SQLException нет таблицы {@link Creator#NAME_TABLE_SAVE_SUDOKU}, или сохранения с таким именем.
     */
    public BufferLoader loadSave(String nameSave) throws SQLException {
        ResultSet save = managerShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.NAME_SAVE, nameSave}});
        String stringSudoku = save.getString(Creator.CIPHER_SUDOKU);
        int sizeBox = save.getInt(Creator.SIZE_BOX);
        int mode = save.getInt(Creator.MODE);
        int size = sizeBox * sizeBox;
        int[][] cellsSudoku = new int[size][size];
        boolean[][] disableCells = new boolean[size][size];
        int a;
        int[] coordinate;

        for (int i = 0; i < stringSudoku.length(); ++i) {
            a = stringSudoku.charAt(i);
            coordinate = Indexing.coordinateMassive(i, size);
            disableCells[coordinate[0]][coordinate[1]] = a >= Creator.DIFFERENCE_CODE;
            cellsSudoku[coordinate[0]][coordinate[1]] = (a >= Creator.DIFFERENCE_CODE) ? a - Creator.DIFFERENCE_CODE : a;
        }

        bufferLoader.setCellsSudoku(cellsSudoku);
        bufferLoader.setDisableCells(disableCells);
        bufferLoader.setMode(mode);
        bufferLoader.setSizeBox(sizeBox);
        return bufferLoader;
    }

    /**
     * Загрузить все имена сохранений.
     *
     * @return имена сохранений.
     * @throws SQLException нет таблицы {@link Creator#NAME_TABLE_SAVE_SUDOKU}.
     */
    public String[] loadAllNameSave() throws SQLException {
        ResultSet save = managerShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, Creator.NAME_SAVE);
        ArrayList<String> arrays = new ArrayList<>();

        while (save.next()) {
            arrays.add(save.getString(Creator.NAME_SAVE));
        }

        return arrays.toArray(new String[0]);
    }

    /**
     * Загрузить автосохранение.
     *
     * @return режим игры, размер box, массив судоку, заблокированные клетки в виде объекта {@link BufferLoader}.
     * @throws SQLException нет таблицы {@link Creator#NAME_TABLE_AUTOSAVE_SUDOKU}.
     */
    public BufferLoader loadAutosave() throws SQLException {
        ResultSet save = managerShell.read(Creator.NAME_TABLE_AUTOSAVE_SUDOKU, new String[][]{{Creator.ID, "1"}});
        String stringSudoku = save.getString(Creator.CIPHER_SUDOKU);
        int sizeBox = save.getInt(Creator.SIZE_BOX);
        int mode = save.getInt(Creator.MODE);
        int size = sizeBox * sizeBox;

        int[][] cellsSudoku = new int[size][size];
        boolean[][] disableCells = new boolean[size][size];
        int a;
        int[] coordinate;

        for (int i = 0; i < stringSudoku.length(); ++i) {
            a = stringSudoku.charAt(i);
            coordinate = Indexing.coordinateMassive(i, size);
            disableCells[coordinate[0]][coordinate[1]] = a >= Creator.DIFFERENCE_CODE;
            cellsSudoku[coordinate[0]][coordinate[1]] = (a >= Creator.DIFFERENCE_CODE) ? a - Creator.DIFFERENCE_CODE : a;
        }

        bufferLoader.setCellsSudoku(cellsSudoku);
        bufferLoader.setDisableCells(disableCells);
        bufferLoader.setMode(mode);
        bufferLoader.setSizeBox(sizeBox);
        return bufferLoader;
    }

    /**
     * Загрузить настройки игры.
     *
     * @return настройки игры в виде bool[][].
     * @throws SQLException нет таблицы {@link Creator#NAME_TABLE_SETTING}.
     */
    public boolean[] loadSetting() throws SQLException {
        ResultSet setting = managerShell.read(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.ID, "1"}});
        boolean[] settingBool = new boolean[Creator.COLUMNS_TABLE_SETTING.length - 1];

        for (int i = 0; i < Creator.COLUMNS_TABLE_SETTING.length - 1; ++i)
            settingBool[i] = setting.getBoolean(Creator.COLUMNS_TABLE_SETTING[i + 1][0]);

        return settingBool;
    }

    /**
     * Загрузить цвета игры.
     *
     * @return цвета игры в виде String[].
     * @throws SQLException нет таблицы {@link Creator#NAME_TABLE_COLORS}.
     */
    public String[] loadColors() throws SQLException {
        ResultSet colors = managerShell.read(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.ID, "1"}});
        String[] colorsString = new String[Creator.COLUMNS_TABLE_COLORS.length - 1];

        for (int i = 0; i < Creator.COLUMNS_TABLE_COLORS.length - 1; ++i)
            colorsString[i] = colors.getString(Creator.COLUMNS_TABLE_COLORS[i + 1][0]);

        return colorsString;
    }

    /**
     * Есть ли такое сохранение в {@link Creator#NAME_TABLE_SAVE_SUDOKU}.
     * @param nameSave название сохранения.
     * @return true, если есть.
     * @throws SQLException  нет таблицы {@link Creator#NAME_TABLE_SAVE_SUDOKU}.
     */
    public boolean checkSave(String nameSave) throws SQLException {
        return managerShell.checkVal(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.NAME_SAVE, nameSave}});
    }
}
