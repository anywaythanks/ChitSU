package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс оболочка, для ядра запросов {@link Manager}.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class ManagerShell {
    /**
     * Объект для отправки запросов в базу данных.
     */
    private final Manager manager;

    /**
     * Создание оболочки, для {@link Manager}.
     *
     * @param path полный путь до базы данных.
     * @throws SQLException           ошибка при подключении к базе данных в {@link Manager#Manager(String)}.
     * @throws ClassNotFoundException if the class cannot be located в {@link Manager#Manager(String)}.
     */
    public ManagerShell(String path) throws SQLException, ClassNotFoundException {
        manager = new Manager(path);
    }

    /**
     * Получить {@link ManagerShell#manager}.
     *
     * @return {@link ManagerShell#manager}.
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Создать таблицу в базе данных.
     *
     * @param nameTable название таблицы.
     * @param data      данные необходимые для создания таблицы в виде массива String[][] {{NameColumn, TypeData}, ...}.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибки в data, или такое название уже существует.
     */
    public int createTable(String nameTable, String[][] data) throws SQLException {
        StringBuilder string = new StringBuilder("CREATE TABLE if not exists `" + nameTable + "` (");
        int len = data.length;

        for (int i = 0; i < len; ++i) {
            string.append("`").append(data[i][0]).append("` ").append(data[i][1]);
            if (len - 1 != i)
                string.append(", ");
        }

        return manager.updater(string.toString() + ");");
    }

    /**
     * Удалить таблицу в базе данных.
     *
     * @param nameTable название таблицы.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException таблицы с таким названием не существует.
     */
    public int deleteTable(String nameTable) throws SQLException {
        return manager.updater("DROP TABLE `" + nameTable + "`;");
    }

    /**
     * Записать данные в базу данных.
     *
     * @param nameTable название таблицы.
     * @param data      данные необходимы для записи в таблицу в виде массива String[][] {{NameColumn, Data}, ...}.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибки в data, или таблицы с таким названием не существует.
     */
    public int write(String nameTable, String[][] data) throws SQLException {
        StringBuilder stringBegin = new StringBuilder("INSERT INTO `" + nameTable + "` (");
        StringBuilder stringEnd = new StringBuilder(") VALUES (");
        int len = data.length;

        for (int i = 0; i < len; ++i) {
            stringBegin.append("`").append(data[i][0]).append("`");
            stringEnd.append("'").append(data[i][1]).append("'");
            if (len - 1 != i) {
                stringBegin.append(", ");
                stringEnd.append(", ");
            }
        }

        return manager.updater(stringBegin.toString() + stringEnd.toString() + ");");
    }

    /**
     * Удалить данные из таблицы.
     *
     * @param nameTable название таблицы.
     * @param filters   фильтры для отсеивания данных; в виде массива String[][] {{NameColumn, Data}, ...}. В sql виде "NameColumn" = "TypeData".
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибки в filters, или таблицы с таким названием не существует.
     */
    public int delete(String nameTable, String[][] filters) throws SQLException {
        StringBuilder string = new StringBuilder("DELETE FROM `" + nameTable + "` WHERE ");
        int len = filters.length;

        for (int i = 0; i < len; ++i) {
            string.append("`").append(filters[i][0]).append("` = '").append(filters[i][1]).append("'");
            if (len - 1 != i)
                string.append(", ");
        }
        return manager.updater(string.toString() + ";");
    }

    /**
     * Перезапись данных в базе.
     *
     * @param nameTable название таблицы.
     * @param filters   фильтры для отсеивания данных; в виде массива String[][] {{NameColumn, Data}, ...}. В sql виде "NameColumn" = "TypeData".
     * @param data      данные необходимы для перезаписи в таблицу в виде массива String[][] {{NameColumn, Data}, ...}.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибки в filters, или таблицы с таким названием не существует.
     */
    public int overwrite(String nameTable, String[][] filters, String[][] data) throws SQLException {
        StringBuilder string = new StringBuilder("UPDATE '" + nameTable + "' SET ");
        int len = data.length;

        for (int i = 0; i < len; ++i) {
            string.append("`").append(data[i][0]).append("` = '").append(data[i][1]).append("'");
            if (len - 1 != i)
                string.append(", ");
        }
        string.append(" WHERE ");
        len = filters.length;

        for (int i = 0; i < len; ++i) {
            string.append("`").append(filters[i][0]).append("` = '").append(filters[i][1]).append("'");
            if (len - 1 != i)
                string.append(", ");
        }
        return manager.updater(string.toString() + ";");
    }

    /**
     * Чтени данных из базы по фильтрам.
     *
     * @param nameTable название таблицы.
     * @param filters   фильтры для отсеивания данных; в виде массива String[][] {{NameColumn, Data}, ...}. В sql виде "NameColumn" = "TypeData".
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибки в filters, или таблицы с таким названием не существует.
     */
    public ResultSet read(String nameTable, String[][] filters) throws SQLException {
        StringBuilder string = new StringBuilder("SELECT * FROM '" + nameTable + "' WHERE ");
        int len = filters.length;

        for (int i = 0; i < len; ++i) {
            string.append("`").append(filters[i][0]).append("` = '").append(filters[i][1]).append("'");
            if (len - 1 != i)
                string.append(", ");
        }
        return manager.interrogator(string.toString());
    }

    /**
     * Чтение всей таблицы из базы.
     *
     * @param nameTable название таблицы.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException таблицы с таким названием не существует.
     */
    public ResultSet read(String nameTable) throws SQLException {
        return manager.interrogator("SELECT * FROM `" + nameTable + "`;");
    }

    /**
     * Чтение всей таблицы из базы, но не не всех столбцов.
     *
     * @param nameTable название таблицы.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException таблицы с таким названием не существует или таких столбцов нет.
     */
    public ResultSet read(String nameTable, String... columns) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        int len = columns.length;
        for (int i = 0; i < len; ++i) {
            stringBuilder.append("`").append(columns[i]).append("`");
            if (len - 1 != i)
                stringBuilder.append(", ");
        }
        return manager.interrogator(stringBuilder.toString() + " FROM `" + nameTable + "`;");
    }

    /**
     * Проверить на существование значения в базе данных.
     *
     * @param nameTable название таблицы
     * @param filters   фильтры для отсеивания данных; в виде массива String[][] {{NameColumn, Data}, ...}. В sql виде "NameColumn" = "TypeData".
     * @return true - значение по таким фильтрам существует, false - не существует.
     * @throws SQLException ошибки в filters, или таблицы с таким названием не существует.
     */
    public boolean checkVal(String nameTable, String[][] filters) throws SQLException {
        StringBuilder string = new StringBuilder("SELECT * FROM '" + nameTable + "' WHERE ");
        int len = filters.length;
        for (int i = 0; i < len; ++i) {
            string.append("`").append(filters[i][0]).append("` = '").append(filters[i][1]).append("'");
            if (len - 1 != i)
                string.append(", ");
        }

        return manager.interrogator(string.toString() + ";").next();
    }

    /**
     * Проверить на существование таблицу в базе данных.
     *
     * @param nameTable название таблицы
     * @return true - таблица существует, false - не существует.
     * @throws SQLException непредвиденная ошибка.
     */
    public boolean checkTable(String nameTable) throws SQLException {
        return manager.interrogator("SELECT * FROM sqlite_master WHERE type='table' AND name='" + nameTable + "';").next();
    }
}
