package dataBase;

import java.sql.*;

/**
 * Класс для отправки запросов в базу данных типа SQLite2.
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Manager {
    /**
     * Соединение с базой данных.
     */
    private Connection connection;

    /**
     * Подтверждение соединения с базой данных.
     */
    private Statement statement;
    /**
     * Название библиотеки, которая используется, для соединения с базой данных.
     */
    private final String NAME_OTHER_LIB = "org.sqlite.JDBC";



    /**
     * Создание объекта {@link Manager}.
     *
     * @param path полный путь до базы данных.
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link Manager#connectionDB(String)}.
     */
    public Manager(String path) throws SQLException, ClassNotFoundException {
        reconnect(path);
    }

    /**
     * Получить {@link Manager#connection}.
     *
     * @return {@link Manager#connection}.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Получить {@link Manager#statement}.
     *
     * @return {@link Manager#statement}.
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Переподключение к базе данных.
     *
     * @param path полный путь до базы данных.
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException ошибка при извлечении класса org.sqlite.JDBC в {@link Manager#connectionDB(String)}.
     */
    public void reconnect(String path) throws SQLException, ClassNotFoundException {
        connection = null;
        statement = null;
        connectionDB(path);
        connectionStatement(connection);
    }

    /**
     * Отправка данных в базу данных с помощью {@link Statement#executeQuery(String)}.
     *
     * @param request запрос в базу данных на языке sql.
     * @return a ResultSet object that contains the data produced by the given query; never null
     * @throws SQLException ошибка при запросе в базу данных.
     */
    public ResultSet interrogator(String request) throws SQLException {
        System.out.println(request);
        return statement.executeQuery(request);
    }

    /**
     * Отправка данных в базу данных с помощью {@link Statement#executeUpdate(String)}.
     *
     * @param request запрос в базу данных на языке sql.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException ошибка при запросе в базу данных.
     */
    public int updater(String request) throws SQLException {
        System.out.println(request);
        return statement.executeUpdate(request);
    }

    /**
     * Подключение к базе данных.
     *
     * @param path полный путь до базы данных({@link Manager#connection}).
     * @throws SQLException           ошибка при подключении к базе данных.
     * @throws ClassNotFoundException if the class cannot be located.
     */
    private void connectionDB(String path) throws SQLException, ClassNotFoundException {
        Class.forName(NAME_OTHER_LIB);
        connection = DriverManager.getConnection(path);
    }

    /**
     * Подключение {@link Manager#statement}.
     *
     * @param connection подключение к базе данных.
     */
    private void connectionStatement(Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
