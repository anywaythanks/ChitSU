package dataBase;

import java.sql.SQLException;

/**
 * Класс для перезаписи сохранений игры.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Overwriter {
    /**
     * Объект {@link ManagerShell}, для работы с базой данных.
     */
    private final ManagerShell managerShell;

    /**
     * Создание объекта {@link Overwriter}.
     *
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link Creator#getManagerShell()}.
     */
    public Overwriter() throws SQLException, ClassNotFoundException {
        managerShell = Creator.getManagerShell();
    }

    /**
     * Создание объекта {@link Overwriter}.
     *
     * @param otherDB Подключение {@link Overwriter#managerShell} с особыми настройками.
     */
    public Overwriter(ManagerShell otherDB) {
        managerShell = otherDB;
    }

    /**
     * Перезапись сохранения.
     *
     * @param nameSudoku  название судоку.
     * @param sizeBox     размер box судоку.
     * @param mode        режим игры.
     * @param sudoku      массив судоку.
     * @param disableCell массив заблокированных клеток.
     * @throws SQLException разные размеры sizeBox и sudoku, название судоку уже существует.
     */
    public void overwriteSave(String nameSudoku, int sizeBox, int mode, int[][] sudoku, boolean[][] disableCell) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sudoku.length; ++i)
            for (int j = 0; j < sudoku.length; ++j)
                stringBuilder.append((char) (sudoku[i][j] + (disableCell[i][j] ? Creator.DIFFERENCE_CODE : 0)));

        managerShell.overwrite(Creator.NAME_TABLE_SAVE_SUDOKU,
                new String[][]{
                        {Creator.NAME_SAVE, nameSudoku}},
                new String[][]{
                        {Creator.MODE, mode + ""},
                        {Creator.SIZE_BOX, sizeBox + ""},
                        {Creator.CIPHER_SUDOKU, stringBuilder.toString()}
                });
    }

    /**
     * Получить {@link Overwriter#managerShell}.
     *
     * @return {@link Overwriter#managerShell}.
     */
    public ManagerShell getManagerShell() {
        return managerShell;
    }
}
