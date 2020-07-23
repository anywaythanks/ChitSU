package dataBase;

import java.sql.SQLException;

/**
 * Класс для отправки запросов в базу данных типа SQLite2.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Deleter {
    /**
     * Объект {@link ManagerShell}. Необходим, для соединения с базой данных.
     */
    private final ManagerShell managerShell;

    /**
     * Создание объекта {@link Deleter}.
     *
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link ManagerShell#ManagerShell(String)}.
     */
    public Deleter() throws SQLException, ClassNotFoundException {
        managerShell = Creator.getManagerShell();
    }

    /**
     * Создание объекта {@link Deleter}.
     *
     * @param otherDB Подключение {@link Deleter#managerShell} с особыми настройками.
     */
    public Deleter(ManagerShell otherDB) {
        managerShell = otherDB;
    }

    /**
     * Получить {@link Deleter#managerShell}.
     *
     * @return {@link Deleter#managerShell}.
     */
    public ManagerShell getManagerShell() {
        return managerShell;
    }

    /**
     * Удалить сохранение.
     *
     * @param nameSave название сохранения.
     * @throws SQLException такого названия нет. Или нет таблицы  {@link Creator#NAME_TABLE_SAVE_SUDOKU}.
     */
    public void deleteSave(String nameSave) throws SQLException {
        managerShell.delete(Creator.NAME_TABLE_SAVE_SUDOKU, new String[][]{{Creator.NAME_SAVE, nameSave}});
    }
}
