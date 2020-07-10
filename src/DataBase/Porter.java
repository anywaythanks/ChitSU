package DataBase;

import Arithmetic.Equals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Класс для портирования сохранений игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Porter {
    /**
     * Объект {@link ManagerShell}, который передает сохранения.
     */
    private ManagerShell importShell;
    /**
     * Объект {@link ManagerShell}, который принимает сохранения.
     */
    private ManagerShell exportShell;


    /**
     * Создание объекта {@link Porter}.
     *
     * @param exportShell объект {@link Porter#exportShell}, который передает сохранения.
     * @param importShell объект {@link Porter#importShell}, который принимает сохранения.
     */
    public Porter(ManagerShell exportShell, ManagerShell importShell) {
        this.importShell = importShell;
        this.exportShell = exportShell;
    }

    /**
     * Портировать настройки игры.
     *
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public void portSetting() throws SQLException {
        ResultSet setting = exportShell.read(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.COLUMNS_TABLE_SETTING[0][0], "1"}});
        String[][] settingStrings = new String[Creator.COLUMNS_TABLE_SETTING.length - 1][2];

        for (int i = 1; i < Creator.COLUMNS_TABLE_SETTING.length; ++i) {
            settingStrings[i - 1][0] = Creator.COLUMNS_TABLE_SETTING[i][0];
            settingStrings[i - 1][1] = setting.getString(Creator.COLUMNS_TABLE_SETTING[i][0]);
        }

        importShell.overwrite(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.COLUMNS_TABLE_SETTING[0][0], "1"}}, settingStrings);
    }

    /**
     * Портировать цвета игры.
     *
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public void portColors() throws SQLException {
        ResultSet colors = exportShell.read(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.COLUMNS_TABLE_COLORS[0][0], "1"}});
        String[][] colorStrings = new String[Creator.COLUMNS_TABLE_COLORS.length - 1][2];

        for (int i = 1; i < Creator.COLUMNS_TABLE_COLORS.length; ++i) {
            colorStrings[i - 1][0] = Creator.COLUMNS_TABLE_COLORS[i][0];
            colorStrings[i - 1][1] = colors.getString(Creator.COLUMNS_TABLE_COLORS[i][0]);
        }

        importShell.overwrite(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.COLUMNS_TABLE_COLORS[0][0], "1"}}, colorStrings);
    }

    /**
     * Получить все совпадающие названия сохранений в базах данных.
     *
     * @return совпадающие названия сохранений в виде String[][].
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public String[] getMatchingSaveName() throws SQLException {
        ResultSet save = importShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0]);
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0]);
        ArrayList<String> arrayListSave = new ArrayList<>();
        ArrayList<String> arrayListOtherSave = new ArrayList<>();
        HashSet<String> matchingSave = new HashSet<>();

        while (save.next()) {
            arrayListSave.add(save.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0]));
        }
        while (otherSave.next()) {
            arrayListOtherSave.add(otherSave.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0]));
        }
        for (String s : arrayListSave)
            for (String os : arrayListOtherSave)
                if (s.equals(os))
                    matchingSave.add(s);

        return matchingSave.toArray(new String[0]);
    }

    /**
     * Портировать все сохранения игры.
     *
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public void portAllSave() throws SQLException {
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU);
        String[][] saveStrings = new String[Creator.COLUMNS_TABLE_SAVE_SUDOKU.length - 1][2];
        while (otherSave.next()) {
            for (int i = 1; i < Creator.COLUMNS_TABLE_SAVE_SUDOKU.length; ++i) {
                saveStrings[i - 1][0] = Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0];
                saveStrings[i - 1][1] = otherSave.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0]);
            }
            if (importShell.checkVal(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0], saveStrings[0][1]}}))
                importShell.overwrite(Creator.NAME_TABLE_SAVE_SUDOKU,
                        new String[][]{{Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0], saveStrings[0][1]}},
                        saveStrings);
            else
                importShell.write(Creator.NAME_TABLE_SAVE_SUDOKU,
                        saveStrings);
        }
    }

    /**
     * Портировать все сохранения игры, кроме некоторых.
     * <p>
     * Также стоит учитывать, что сохранения, которые уже есть в базе, будут переписаны.
     *
     * @param leaveSave эти сохранения игры ни в коем случае не должны быть портированы.
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public void portSave(String... leaveSave) throws SQLException {
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU);
        String[][] saveStrings = new String[Creator.COLUMNS_TABLE_SAVE_SUDOKU.length - 1][2];
        while (otherSave.next()) {
            for (int i = 1; i < Creator.COLUMNS_TABLE_SAVE_SUDOKU.length; ++i) {
                saveStrings[i - 1][0] = Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0];
                saveStrings[i - 1][1] = otherSave.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0]);
            }
            if (!Equals.objectEq(saveStrings[0][1], leaveSave))
                if (importShell.checkVal(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0], saveStrings[0][1]}})) {
                    importShell.overwrite(Creator.NAME_TABLE_SAVE_SUDOKU,
                            new String[][]{{Creator.COLUMNS_TABLE_SAVE_SUDOKU[1][0], saveStrings[0][1]}},
                            saveStrings);
                } else
                    importShell.write(Creator.NAME_TABLE_SAVE_SUDOKU,
                            saveStrings);
        }
    }

    /**
     * Задать значение {@link Porter#importShell}.
     *
     * @param importShell {@link Porter#importShell}.
     */
    public void setImportShell(ManagerShell importShell) {
        this.importShell = importShell;
    }

    /**
     * Задать значение {@link Porter#exportShell}.
     *
     * @param exportShell {@link Porter#exportShell}.
     */
    public void setExportShell(ManagerShell exportShell) {
        this.exportShell = exportShell;
    }

    /**
     * Получить {@link Porter#importShell}.
     *
     * @return {@link Porter#importShell}.
     */
    public ManagerShell getImportShell() {
        return importShell;
    }

    /**
     * Получить {@link Porter#exportShell}.
     *
     * @return {@link Porter#exportShell}.
     */
    public ManagerShell getExportShell() {
        return exportShell;
    }
}
