package DataBase;

import sample.Main;

import java.io.File;
import java.sql.SQLException;

/**
 * Промежуточный класс для создания единичной копии {@link Creator#managerShell}
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Creator {
    /**
     * Объект {@link Creator}.
     * Необходим для обновления данных класса.
     */
    private static Creator creator;
    /**
     * Объект {@link ManagerShell}. Необходим, для соединения с базой данных.
     */
    private static ManagerShell managerShell;
    /**
     * Переменная необходимая для показа: создался ли каталог в {@link Creator#PATH_TO_CATALOG_SAVE}.
     * А тажке для метода {@link Creator#isDirectoriesBeenCreated()}.
     */
    private static boolean directoriesBeenCreated;
    /**
     * Название игры
     */
    public static final String GAME_NAME = "ChitSU";
    /**
     * Путь до базы данных с сохранениями.
     */
    public static final String PATH_TO_CATALOG_SAVE = System.getProperty("os.name").equals("Linux") ?
            System.getenv("HOME") + File.separator + ".config" + File.separator + GAME_NAME + File.separator:
            System.getenv("APPDATA") + File.separator + GAME_NAME + File.separator;
    /**
     * Название системы базы данных.
     * В этом случае SQLite.
     */
    public static final String NAME_SYSTEM_DB = "jdbc:sqlite:";
    /**
     * Названия файла базы данных.
     */
    public static final String NAME_DB = System.getProperty("os.name").equals("Linux") ? ".ChitSU_Save.s3db" : "Save.s3db";
    /**
     * Абсолютный путь до бызы данных.
     */
    public static final String ABSOLUTE_PATH_DB = NAME_SYSTEM_DB + PATH_TO_CATALOG_SAVE + NAME_DB;

    /**
     * Название таблицы с информацией о базе данных.
     */
    public static final String NAME_TABLE_INFORMATION_DB = "Information data base";
    /**
     * Названия и тип данных столбцов.
     * <p>
     * `id` - id идентификатор, для правильной работы базы: INTEGER.
     * <p>
     * `version` - ныняшняя версия данных. Указывает на то, в какой версии программы были сохранены данные. Нужно для переноса данных из старых версий: TEXT
     */
    public static final String[][] COLUMNS_TABLE_INFORMATION_DB = {
            {"id", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"version", "TEXT"}};
    public static final String VERSION_DB = "1.1";

    /**
     * Название таблицы с простыми сохранениями судоку.
     */
    public static final String NAME_TABLE_SAVE_SUDOKU = "Save sudoku";
    /**
     * Названия и тип данных столбцов.
     * <p>
     * `id` - id идентификатор, для правильной работы базы: INTEGER.
     * <p>
     * `name save` - Название сохранения, также это уникальный ключ: TEXT
     * <p>
     * `mode` - Режим игры: INTEGER
     * <p>
     * `size box` - Размер box в судоку: INTEGER
     * <p>
     * `Cipher string with sudoku` - Зашифрованная судоку в виде текста: TEXT
     */
    public static final String[][] COLUMNS_TABLE_SAVE_SUDOKU = {
            {"id", "INTEGER"},
            {"name save", "TEXT PRIMARY KEY"},
            {"mode", "INTEGER"},
            {"size box", "INTEGER"},
            {"Cipher string with sudoku", "TEXT"}};

    /**
     * Название таблицы с автосохранениями судоку.
     */
    public static final String NAME_TABLE_AUTOSAVE_SUDOKU = "Autosave sudoku";
    /**
     * Названия и тип данных столбцов.
     * <p>
     * `id` - id идентификатор, для правильной работы базы: INTEGER.
     * <p>
     * `mode` - Режим игры: INTEGER
     * <p>
     * `size box` - Размер box в судоку: INTEGER
     * <p>
     * `Cipher string with sudoku` - Зашифрованная судоку в виде текста: TEXT
     */
    public static final String[][] COLUMNS_TABLE_AUTOSAVE_SUDOKU = {
            {"id", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"mode", "INTEGER"},
            {"size box", "INTEGER"},
            {"Cipher string with sudoku", "TEXT"}};

    /**
     * Название таблицы с настройками игры.
     */
    public static final String NAME_TABLE_SETTING = "Setting";
    /**
     * Названия и тип данных столбцов.
     * <p>
     * `id` - id идентификатор, для правильной работы базы: INTEGER.
     * <p>
     * `Classic` - Классический режим игры: BOOLEAN
     * <p>
     * `Creative` - Творческий режим игры: BOOLEAN
     * <p>
     * `Learning` - Обучающий режим игры: BOOLEAN
     * <p>
     * `Highlighting` - Подчеркивание неправильных клеток: BOOLEAN
     * <p>
     * `Highlighting All Num` - Подчеркивание совпадающих клеток: BOOLEAN
     * <p>
     * `Show Candidates` - Показ кандидатов: BOOLEAN
     * <p>
     * `Window Stage Learning` - Показ встречающего обучаещего окна, при переходе в режим обучения: BOOLEAN
     * <p>
     * `Set Full Screen` - Запустить игру в фуллскрине: BOOLEAN
     */
    public static final String[][] COLUMNS_TABLE_SETTING = {
            {"id", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"Classic", "BOOLEAN"},
            {"Creative", "BOOLEAN"},
            {"Learning", "BOOLEAN"},
            {"Highlighting", "BOOLEAN"},
            {"Highlighting All Num", "BOOLEAN"},
            {"Show Candidates", "BOOLEAN"},
            {"Window Stage Learning", "BOOLEAN"},
            {"Set Full Screen", "BOOLEAN"}};

    /**
     * Название таблицы с цветами игры.
     */
    public static final String NAME_TABLE_COLORS = "Colors";
    /**
     * Названия и тип данных столбцов.
     * <p>
     * `id` - id идентификатор, для правильной работы базы: INTEGER.
     * <p>
     * `Color Wrong Label` - Цвет неправильной клетки: TEXT
     * <p>
     * `Color Base Label` - Цвет обычной клетки: TEXT
     * <p>
     * `Color Base Font` - Цвет шрифта обычной клетки: TEXT
     * <p>
     * `Color Coincidental Label` - Цвет совпадающей клетки: TEXT
     * <p>
     * `Color No Candidate Font` - Цвет не кандидата: TEXT
     * <p>
     * `Color Candidate Font` - Цвет кандидата: TEXT
     * <p>
     * `Disable Opacity` - Прозрачность заблокированной клетки: TEXT
     */
    public static final String[][] COLUMNS_TABLE_COLORS = {
            {"id", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"Color Wrong Label", "TEXT"},
            {"Color Base Label", "TEXT"},
            {"Color Base Font", "TEXT"},
            {"Color Coincidental Label", "TEXT"},
            {"Color No Candidate Font", "TEXT"},
            {"Color Candidate Font", "TEXT"},
            {"Disable Opacity", "TEXT"}};
    /**
     * Цвета по умолчанию.
     * <p>
     * Они соответсвуют {@link Creator#NAME_TABLE_COLORS}.
     */
    public static final String[] DEFAULT_COLORS = {
            "rgba(255,0,0,1.0)",
            "rgba(96,56,19,1.0)",
            "rgba(0,0,0,1.0)",
            "rgba(21,109,117,1.0)",
            "rgba(0,0,0,1.0)",
            "rgba(0,128,0,1.0)",
            "0.7"};
    /**
     * Настройки по умолчанию
     * <p>
     * Они соответсвуют {@link Creator#NAME_TABLE_SETTING}
     */
    public static final Boolean[] DEFAULT_SETTING = {
            false,
            true,
            false,
            true,
            true,
            true,
            true,
            true};
    /**
     * Разница в кодировке судоку.
     * Это число означает, то сколько будет прибавляться при шифровке судоку.
     */
    public static final int DIFFERENCE_CODE = 1000;


    /**
     * Подключение к базе данных. Создание всех недостающих каталогов.
     * А также создание всех необходимых, для взаимодействи таблиц, елси их не существует.
     *
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link ManagerShell#ManagerShell(String)}.
     */
    private Creator() throws SQLException, ClassNotFoundException {
        File catalogSave = new File(PATH_TO_CATALOG_SAVE + NAME_DB);
        directoriesBeenCreated = catalogSave.getParentFile().mkdirs();

        managerShell = new ManagerShell(ABSOLUTE_PATH_DB);
        if (!managerShell.checkTable(NAME_TABLE_SAVE_SUDOKU))
            managerShell.createTable(NAME_TABLE_SAVE_SUDOKU, COLUMNS_TABLE_SAVE_SUDOKU);

        if (!managerShell.checkTable(NAME_TABLE_AUTOSAVE_SUDOKU))
            managerShell.createTable(NAME_TABLE_AUTOSAVE_SUDOKU, COLUMNS_TABLE_AUTOSAVE_SUDOKU);

        if (!managerShell.checkTable(NAME_TABLE_SETTING))
            managerShell.createTable(NAME_TABLE_SETTING, COLUMNS_TABLE_SETTING);

        if (!managerShell.checkTable(NAME_TABLE_COLORS))
            managerShell.createTable(NAME_TABLE_COLORS, COLUMNS_TABLE_COLORS);

        if (!managerShell.checkTable(NAME_TABLE_INFORMATION_DB)) {
            managerShell.createTable(NAME_TABLE_INFORMATION_DB, COLUMNS_TABLE_INFORMATION_DB);
            managerShell.write(NAME_TABLE_INFORMATION_DB, new String[][]{{COLUMNS_TABLE_INFORMATION_DB[1][0], VERSION_DB}});
        }

    }

    /**
     * Получить {@link Creator#managerShell}.
     * Если его еще не существет - создать.
     *
     * @return {@link Creator#managerShell}.
     * @throws SQLException           ошибка при подключении к базе данных
     * @throws ClassNotFoundException if the class cannot be located в {@link Creator#Creator()}.
     */
    public static ManagerShell getManagerShell() throws SQLException, ClassNotFoundException {
        if (creator == null)
            creator = new Creator();
        return managerShell;
    }

    /**
     * Показывает была ли создана дериктория в {@link Creator#PATH_TO_CATALOG_SAVE}.
     * Хранится в {@link Creator#directoriesBeenCreated}.
     *
     * @return true - если была создана.
     */
    public static boolean isDirectoriesBeenCreated() {
        return directoriesBeenCreated;
    }
}
