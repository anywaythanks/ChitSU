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
     * Портировать настройки игры.
     *
     * @param exportShell объект {@link Porter}, который передает сохранения.
     * @param importShell объект {@link Porter}, который принимает сохранения.
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public static void portSetting(ManagerShell exportShell, ManagerShell importShell) throws SQLException {
        ResultSet setting = exportShell.read(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.ID, "1"}});
        String[][] settingStrings = new String[Creator.COLUMNS_TABLE_SETTING.length - 1][2];

        for (int i = 1; i < Creator.COLUMNS_TABLE_SETTING.length; ++i) {
            settingStrings[i - 1][0] = Creator.COLUMNS_TABLE_SETTING[i][0];
            settingStrings[i - 1][1] = setting.getString(Creator.COLUMNS_TABLE_SETTING[i][0]);
        }

        importShell.overwrite(Creator.NAME_TABLE_SETTING, new String[][]{{Creator.ID, "1"}}, settingStrings);
    }

    /**
     * Портировать цвета игры.
     *
     * @param exportShell объект {@link Porter}, который передает сохранения.
     * @param importShell объект {@link Porter}, который принимает сохранения.
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public static void portColors(ManagerShell exportShell, ManagerShell importShell) throws SQLException {
        ResultSet colors = exportShell.read(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.ID, "1"}});
        String[][] colorStrings = new String[Creator.COLUMNS_TABLE_COLORS.length - 1][2];

        for (int i = 1; i < Creator.COLUMNS_TABLE_COLORS.length; ++i) {
            colorStrings[i - 1][0] = Creator.COLUMNS_TABLE_COLORS[i][0];
            colorStrings[i - 1][1] = colors.getString(Creator.COLUMNS_TABLE_COLORS[i][0]);
        }

        importShell.overwrite(Creator.NAME_TABLE_COLORS, new String[][]{{Creator.ID, "1"}}, colorStrings);
    }

    /**
     * Получить все совпадающие названия сохранений в базах данных.
     *
     * @param exportShell объект {@link Porter}, который передает сохранения.
     * @param importShell объект {@link Porter}, который принимает сохранения.
     * @return совпадающие названия сохранений в виде String[][].
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public static String[] getMatchingSaveName(ManagerShell exportShell, ManagerShell importShell) throws SQLException {
        ResultSet save = importShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, Creator.NAME_SAVE);
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU, Creator.NAME_SAVE);
        ArrayList<String> arrayListSave = new ArrayList<>();
        ArrayList<String> arrayListOtherSave = new ArrayList<>();
        HashSet<String> matchingSave = new HashSet<>();

        while (save.next()) {
            arrayListSave.add(save.getString(Creator.NAME_SAVE));
        }
        while (otherSave.next()) {
            arrayListOtherSave.add(otherSave.getString(Creator.NAME_SAVE));
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
     * @param exportShell объект {@link Porter}, который передает сохранения.
     * @param importShell объект {@link Porter}, который принимает сохранения.
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public static void portAllSave(ManagerShell exportShell, ManagerShell importShell) throws SQLException {
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU);
        String[][] saveStrings = new String[Creator.COLUMNS_TABLE_SAVE_SUDOKU.length - 1][2];
        while (otherSave.next()) {
            for (int i = 1; i < Creator.COLUMNS_TABLE_SAVE_SUDOKU.length; ++i) {
                saveStrings[i - 1][0] = Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0];
                saveStrings[i - 1][1] = otherSave.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0]);
            }
            if (importShell.checkVal(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.NAME_SAVE, saveStrings[0][1]}}))
                importShell.overwrite(Creator.NAME_TABLE_SAVE_SUDOKU,
                        new String[][]{{Creator.NAME_SAVE, saveStrings[0][1]}},
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
     * @param exportShell объект {@link Porter}, который передает сохранения.
     * @param importShell объект {@link Porter}, который принимает сохранения.
     * @param leaveSave   эти сохранения игры ни в коем случае не должны быть портированы.
     * @throws SQLException нет таблиц или столбцов в базах данных.
     */
    public static void portSave(ManagerShell exportShell, ManagerShell importShell, String... leaveSave) throws SQLException {
        ResultSet otherSave = exportShell.read(Creator.NAME_TABLE_SAVE_SUDOKU);
        String[][] saveStrings = new String[Creator.COLUMNS_TABLE_SAVE_SUDOKU.length - 1][2];
        while (otherSave.next()) {
            for (int i = 1; i < Creator.COLUMNS_TABLE_SAVE_SUDOKU.length; ++i) {
                saveStrings[i - 1][0] = Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0];
                saveStrings[i - 1][1] = otherSave.getString(Creator.COLUMNS_TABLE_SAVE_SUDOKU[i][0]);
            }
            if (!Equals.objectEq(saveStrings[0][1], (Object) leaveSave))
                if (importShell.checkVal(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.NAME_SAVE, saveStrings[0][1]}})) {
                    importShell.overwrite(Creator.NAME_TABLE_SAVE_SUDOKU,
                            new String[][]{{Creator.NAME_SAVE, saveStrings[0][1]}},
                            saveStrings);
                } else
                    importShell.write(Creator.NAME_TABLE_SAVE_SUDOKU,
                            saveStrings);
        }
    }
}
