package DataBase;

import Arithmetic.Logic;

import java.sql.SQLException;

/**
 * Класс для обработки сохранений игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Saver {
    /**
     * Объект {@link ManagerShell}, для работы с базой данных.
     */
    private final ManagerShell managerShell;

    /**
     * Создание объекта {@link Saver}.
     *
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link Creator#getManagerShell()}.
     */
    public Saver() throws SQLException, ClassNotFoundException {
        managerShell = Creator.getManagerShell();
        checkSave();
    }

    /**
     * Создание объекта {@link Saver}.
     *
     * @param otherDB Подключение  {@link Saver#managerShell} с особыми настройками.
     */
    public Saver(ManagerShell otherDB) {
        managerShell = otherDB;
    }

    /**
     * Получить {@link Saver#managerShell}.
     *
     * @return {@link Saver#managerShell}.
     */
    public ManagerShell getManagerShell() {
        return managerShell;
    }

    /**
     * Сохранение судоку.
     *
     * @param nameSudoku  название судоку.
     * @param sizeBox     размер box судоку.
     * @param mode        режим игры.
     * @param sudoku      массив судоку.
     * @param disableCell массив заблокированных клеток.
     * @throws SQLException разные размеры sizeBox и sudoku, название судоку уже существует.
     */
    public void saveSudoku(String nameSudoku, int sizeBox, int mode, int[][] sudoku, boolean[][] disableCell) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sudoku.length; ++i)
            for (int j = 0; j < sudoku.length; ++j)
                stringBuilder.append((char) (sudoku[i][j] + (disableCell[i][j] ? Creator.DIFFERENCE_CODE : 0)));

        managerShell.write(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{
                {Creator.NAME_SAVE, nameSudoku},
                {Creator.MODE, mode + ""},
                {Creator.SIZE_BOX, sizeBox + ""},
                {Creator.CIPHER_SUDOKU, stringBuilder.toString()}
        });
    }

    /**
     * Создание чистого {@link Saver#autosaveSudoku(int, int, int[][], boolean[][])}.
     *
     * @throws SQLException в бд нет {@link Creator#NAME_TABLE_AUTOSAVE_SUDOKU}. Или же таблица неверно составлена.
     */
    public void clearAutoSave() throws SQLException {
        autosaveSudoku(0, 3, new int[9][9], new boolean[9][9]);
    }

    /**
     * Автосохранение судоку.
     *
     * @param sizeBox     размер box судоку.
     * @param mode        режим игры.
     * @param sudoku      массив судоку.
     * @param disableCell массив заблокированных клеток.
     * @throws SQLException разные размеры sizeBox и sudoku. Нет таблицы {@link Creator#NAME_TABLE_AUTOSAVE_SUDOKU}, и пр.
     */
    public void autosaveSudoku(int mode, int sizeBox, int[][] sudoku, boolean[][] disableCell) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sudoku.length; ++i)
            for (int j = 0; j < sudoku.length; ++j)
                stringBuilder.append((char) (sudoku[i][j] + (disableCell[i][j] ? Creator.DIFFERENCE_CODE : 0)));

        if (!managerShell.checkVal(Creator.NAME_TABLE_AUTOSAVE_SUDOKU, new String[][]{{Creator.ID, "1"}}))
            managerShell.write(Creator.NAME_TABLE_AUTOSAVE_SUDOKU, new String[][]{
                    {Creator.MODE, mode + ""},
                    {Creator.SIZE_BOX, sizeBox + ""},
                    {Creator.CIPHER_SUDOKU, stringBuilder.toString()}
            });
        else
            managerShell.overwrite(Creator.NAME_TABLE_AUTOSAVE_SUDOKU,
                    new String[][]{
                            {Creator.ID, "1"}},
                    new String[][]{
                            {Creator.MODE, mode + ""},
                            {Creator.SIZE_BOX, ((int) Math.sqrt(sudoku.length)) + ""},
                            {Creator.CIPHER_SUDOKU, stringBuilder.toString()}});
    }

    /**
     * Создание чистого {@link Saver#saveSetting(Boolean...)}.
     *
     * @throws SQLException в бд нет {@link Creator#NAME_TABLE_SETTING}. Или же таблица неверно составлена.
     */
    public void clearSaveSetting() throws SQLException {
        saveSetting(Creator.DEFAULT_SETTING);
    }

    /**
     * Сохранение настроек игры.
     *
     * @param settings настроки игры в виде массива bool.
     * @throws SQLException в бд нет {@link Creator#NAME_TABLE_SETTING}. Или же таблица неверно составлена.
     */
    public void saveSetting(Boolean... settings) throws SQLException {
        String[][] setting = new String[settings.length][2];
        for (int i = 0; i < setting.length; ++i) {
            setting[i][0] = Creator.COLUMNS_TABLE_SETTING[i + 1][0];
            setting[i][1] = Logic.booleanToInt(settings[i]) + "";
        }
        if (!managerShell.checkVal(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.ID, "1"}}))
            managerShell.write(Creator.NAME_TABLE_SETTING, setting);
        else
            managerShell.overwrite(
                    Creator.NAME_TABLE_SETTING,
                    new String[][]{{Creator.ID, "1"}},
                    setting);
    }

    /**
     * Создание чистого {@link Saver#saveColors(String...)}.
     *
     * @throws SQLException в бд нет {@link Creator#NAME_TABLE_COLORS}. Или же таблица неверно составлена.
     */
    public void clearColors() throws SQLException {
        saveColors(Creator.DEFAULT_COLORS);
    }

    /**
     * Сохранение цветов игры.
     *
     * @param colors цвета, задаются в виде текста в rgba() формате. Прим: {@link Creator#DEFAULT_COLORS}
     * @throws SQLException в бд нет {@link Creator#NAME_TABLE_COLORS}. Или же таблица неверно составлена.
     */
    public void saveColors(String... colors) throws SQLException {
        String[][] setting = new String[colors.length][2];
        for (int i = 0; i < setting.length; ++i) {
            setting[i][0] = Creator.COLUMNS_TABLE_COLORS[i + 1][0];
            setting[i][1] = colors[i];
        }
        if (!managerShell.checkVal(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.ID, "1"}}))
            managerShell.write(Creator.NAME_TABLE_COLORS, setting);
        else
            managerShell.overwrite(
                    Creator.NAME_TABLE_COLORS,
                    new String[][]{{Creator.ID, "1"}},
                    setting);
    }

    /**
     * Проверка данных в таблицах.
     * Если их нет автоматически создает.
     * Также эта функция запускается при создании класса {@link Saver#Saver()}.
     *
     * @throws SQLException только если не существует таблиц({@link Creator#NAME_TABLE_AUTOSAVE_SUDOKU}, {@link Creator#NAME_TABLE_SETTING}, {@link Creator#NAME_TABLE_COLORS}), или же они неверно заполнены.
     */
    public void checkSave() throws SQLException {
        if (!managerShell.checkVal(Creator.NAME_TABLE_AUTOSAVE_SUDOKU, new String[][]{{Creator.ID, "1"}}))
            clearAutoSave();
        if (!managerShell.checkVal(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.ID, "1"}}))
            clearSaveSetting();
        if (!managerShell.checkVal(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.ID, "1"}}))
            clearColors();
    }

}
