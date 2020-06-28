package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

/**
 * Класс логики
 *
 * @author anywaythanks
 * @version 1.0
 */
public class Logic {
    private Sudoku sudoku;

    public Logic(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Название каталога
     */
    private static final String S_CAT = System.getenv("APPDATA") + File.separator + Main.GAME_NAME + File.separator + "saves";
    /**
     * Цвета по умолчанию
     */
    public static final String DEF_COL = "rgba(255,0,0,1.0) rgba(96,56,19,1.0) rgba(0,0,0,1.0) rgba(21,109,117,1.0) rgba(0,0,0,1.0) rgba(0,128,0,1.0) 0.7 1";
    /**
     * Настройки по умолчанию
     */
    public static final String DEFAULT_SET = "0 1 1 0 1 0 1";
    /**
     * Каталог, в котором хранятся все подкаталоги
     */
    private static final File CATALOG = new File(System.getenv("APPDATA") + File.separator + Main.GAME_NAME);
    /**
     * Подкаталог, в котором хранятся все настройки
     */
    private static final File CATALOG_SAVE = new File(S_CAT);
    /**
     * Файл, в котором хранятся все автосохранения массива
     */
    private static final File F_AUTO_SAVE_MAS = new File(S_CAT + File.separator + "autosave.txt");
    /**
     * Файл, в котором хранятся все автосохранения настроек
     */
    public static final File F_AUTO_SAVE_SET = new File(S_CAT + File.separator + "autoset.txt");
    /**
     * Файл, в котором хранятся все сохранения
     */
    public static final File F_SAVE = new File(S_CAT + File.separator + "save.txt");
    /**
     * Файл, в котором хранятся все автосохранения цветов
     */
    public static final File F_AUTO_SAVE_COLOR = new File(S_CAT + File.separator + "autocolor.txt");
    /**
     * Массив названий сохранений
     */
    public static String[] masName;
    /**
     * Вывод в консоль особых параметров
     */
    public static boolean conclus = false;

    /**
     * Создание окна в {@link Controller#stageBuf} или {@link Controller#stageBuf1}
     *
     * @param stage stage в котором и будет созданно новое окно
     * @param title title окна
     * @param fxml  Путь к fxml файлу из которого будут выгружены параметры для создания окна
     * @param css   Путь к css файлу из которого будут выгружены особые параметры для создания окна
     * @return Возвращает полностью созданное окно.
     */
    public Stage setStage
    (Stage stage, String title, String fxml, String css) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            root.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    if (Controller.stageBuf != null) Controller.stageBuf.close();
                    if (Controller.stageBuf1 != null) Controller.stageBuf1.close();

                    if (ControlNot.textLabel != null) ControlNot.textLabel = null;
                    if (ControlSave2.textLabel != null) ControlNot.textLabel = null;

                    if (Prompt.textLabel != null) Prompt.textLabel = null;
                    if (Prompt.textVBox != null) Prompt.textVBox = null;

                }
            });
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.stage);
            stage.setTitle(title);
            scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
            stage.setScene(scene);
            stage.getIcons().add(new Image("sample/Pictures/icon.png"));
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }

    public String getRGBAModel(Color color) {
        return "rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + color.getOpacity() + ")";
    }


    /**
     * Задать прозрачность {@link Color}
     *
     * @param color      {@link Color}, прозрачность которого нужно изменить
     * @param newOpacity новое значение прозрачности для цвета
     * @return {@link Color} с новой прозрачностью
     */
    public Color setOpacity(Color color, double newOpacity) {
        return Color.valueOf("rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + newOpacity + ")");
    }


    /**
     * Перевод boolean -> int
     *
     * @param bool значение boolean для превода в int
     * @return boolean значение
     */
    public int boolToInt
    (boolean bool) {
        if (bool) return 1;
        return 0;
    }

    /**
     * Перевод String -> boolean
     *
     * @param string значение String для превода в boolean
     * @return boolean значение
     */
    public boolean stringToBool
    (String string) {
        return string.charAt(0) == '1';
    }

    /**
     * Функция для очистки файла
     *
     * @param file файл, который нужно очистить
     */
    public static void fClear
    (File file) {
        if (file.delete())
            createFiles();
    }

    /**
     * Функция для написания строки в файл
     *
     * @param s    строка, которую нужно нависать
     * @param file файл в который производится запись
     * @deprecated функция не эффективна
     */
    public static void fWriteLine
    (String s, File file) {
        if (conclus) System.out.println("fWriteLine");
        if (file.exists())
            try (FileWriter writer = new FileWriter(file.getAbsolutePath(), true)) {
                writer.append(s);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    /**
     * Функция для нахождения всех названий сохранений в {@link Logic#F_SAVE}
     */
    public static void setNames
    () {
        if (conclus) System.out.println("setNames");
        int k = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(F_SAVE), "UTF-8"))) {
            int a;
            while ((a = reader.read()) != -1)
                if (a == 42)
                    k++;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        masName = new String[k];
        try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(F_SAVE), "UTF-8"))) {
            int a;
            String s2 = "";
            boolean flag = false;
            k = 0;
            while ((a = reader1.read()) != -1) {
                if (a == 42) {
                    flag = true;
                    s2 = "";
                } else if (a == 666) {
                    flag = false;
                    masName[k++] = s2;
                } else if (flag)
                    s2 += (char) a;
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    /**
     * Перезапись сохранения в {@link Logic#F_SAVE}
     * Информация для перезаписи берется в {@link Controller#sudoku}
     *
     * @param name имя сохранения, которое нужно перезаписать
     */
    public void overwrite
    (String name) {
        setNames();
        int number = 0;
        for (int i = 0; i < Logic.masName.length; ++i)
            if (Logic.masName[i].equals(name)) {
                number = i + 1;
                break;
            }
        if (conclus) System.out.println("overwrite");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(F_SAVE), "UTF-8"))) {
            StringBuilder s = new StringBuilder("");
            int num, q = 0;
            while ((num = reader.read()) != -1) {
                if (num == 42)
                    q++;
                if (q == number + 1) {
                    break;
                }
                if (q != number) {
                    s.append((char) num);
                }
            }
            s.append((char) 42);
            s.append(name);
            s.append((char) 666);
            for (int i = 0; i < Main.n; ++i)
                for (int j = 0; j < Main.n; ++j)
                    s.append((char) (sudoku.getVal(i, j) + ((sudoku.isDisable(i, j)) ? 10 : 0)));
            if (num != -1)
                s.append((char) 42);
            while ((num = reader.read()) != -1) {
                s.append((char) num);
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(F_SAVE), "UTF-8"))) {
                writer.append(s.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Сохранение в {@link Logic#F_SAVE}
     * Информация для сохранения берется в {@link Controller#sudoku}
     *
     * @param name названия сохранения
     * @return успешно ли сохранение
     */

    public boolean save
    (String name) {
        if (conclus) System.out.println("save");
        setNames();
        for (String s : masName)
            if (s.equals(name))
                return false;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(F_SAVE, true), "UTF-8"))) {
            writer.append((char) (42));
            writer.append(name);
            writer.append((char) (666));
            for (int i = 0; i < Main.n; ++i)
                for (int j = 0; j < Main.n; ++j)
                    writer.append((char) (sudoku.getVal(i, j) + ((sudoku.isDisable(i, j)) ? 10 : 0)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }


    /**
     * Сохранение в {@link Logic#F_AUTO_SAVE_MAS}
     * Информация для сохранения берется в {@link Controller#sudoku}
     */
    public void save
    () {

        if (conclus) System.out.println("save");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(F_AUTO_SAVE_MAS), "UTF-8"))) {
            for (int i = 0; i < Main.n; ++i)
                for (int j = 0; j < Main.n; ++j)
                    writer.append((char) (sudoku.getVal(i, j) + ((sudoku.isDisable(i, j)) ? 10 : 0)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Загрузка сохранений из {@link Logic#F_SAVE}
     * Загрузка происходит в {@link Controller#sudoku}
     *
     * @param name имя сохранения
     * @return режим игры сохранения
     */
    public int load
    (String name) {
        int number = 0;
        for (int i = 0; i < Logic.masName.length; ++i)
            if (Logic.masName[i].equals(name)) {
                number = i + 1;
                break;
            }
        if (conclus) System.out.println("load");
        int mode = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(F_SAVE), "UTF-8"))) {
            int num, k = 0, q = 0;
            while ((num = reader.read()) != -1) {
                if (q == number) {
                    if (num > 19)
                        break;
                    sudoku.setDisable(k / 9, k % 9, !(num < 10));
                    if (num < 10)
                        sudoku.setVal(k / 9, k % 9, num);
                    else {
                        sudoku.setVal(k / 9, k % 9, num - 10);
                        mode = 1;
                    }
                    ++k;
                }
                if (num == 666)
                    q++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mode;
    }

    /**
     * Загрузка автосохранения из {@link Logic#F_AUTO_SAVE_MAS}
     * Загрузка происходит в {@link Controller#sudoku}
     */
    public void load
    () {
        if (conclus) System.out.println("load");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(F_AUTO_SAVE_MAS), "UTF-8"))) {
            int num, k = 0;
            while ((num = reader.read()) != -1) {
                if (num > 19)
                    break;
                sudoku.setDisable(k / 9, k % 9, !(num < 10));
                if (num < 10)
                    sudoku.setVal(k / 9, k % 9, num);
                else
                    sudoku.setVal(k / 9, k % 9, num - 10);
                ++k;
            }
            if (k != 81) {
                sudoku.clearAll();
                fClear(F_AUTO_SAVE_MAS);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Функция для удаления сохранения в {@link Logic#F_SAVE} по имени
     *
     * @param name имя сохранения
     */
    public static void del
    (String name) {
        int number = 0;
        for (int i = 0; i < Logic.masName.length; ++i)
            if (Logic.masName[i].equals(name)) {
                number = i + 1;
                break;
            }
        if (conclus) System.out.println("del");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(F_SAVE), "UTF-8"))) {
            StringBuilder s = new StringBuilder("");
            int num, q = 0;
            while ((num = reader.read()) != -1) {
                if (num == 42)
                    q++;
                if (q == number + 1)
                    break;
                if (q != number)
                    s.append((char) num);
            }
            if (num != -1)
                s.append((char) 42);
            while ((num = reader.read()) != -1) {
                s.append((char) num);
            }
            try (FileWriter writer = new FileWriter(F_SAVE.getAbsolutePath())) {
                writer.append(s.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Создание
     * {@link Logic#CATALOG},
     * {@link Logic#CATALOG_SAVE},
     * {@link Logic#F_SAVE},
     * {@link Logic#F_AUTO_SAVE_MAS},
     * {@link Logic#F_AUTO_SAVE_COLOR},
     * {@link Logic#F_AUTO_SAVE_SET},
     *
     * @return Успешно ли создались файлы
     */
    public static boolean createFiles
    () {
        if (conclus) System.out.println("createFiles");
        try {
            return CATALOG.mkdir() &
                    CATALOG_SAVE.mkdir() &
                    F_SAVE.createNewFile() &
                    F_AUTO_SAVE_MAS.createNewFile() &
                    F_AUTO_SAVE_COLOR.createNewFile() &
                    F_AUTO_SAVE_SET.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Перевод int -> boolean
     *
     * @param i значение int для превода в boolean
     * @return boolean значение
     * @deprecated этот перевод, возможно, есть в основных джава классах
     */
    public static boolean intToBool
    (int i) {
        return i == 1;
    }

    /**
     * Читает одну строку из файла
     *
     * @param file   файл из которого происходит чтение
     * @param numRow номер строки для чтения
     * @return считанную строку
     * @deprecated неэффективно
     */
    public static StringBuilder fReadLine
    (File file, int numRow) {
        if (conclus) System.out.println("fReadLine");
        StringBuilder a = new StringBuilder("");
        int k = 0;
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while (k < numRow)
                if ((char) reader.read() == '\n') k++;
            char s = (char) reader.read();
            while (s != '\n') {
                a.append(s);
                s = (char) reader.read();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    /**
     * Читает несколько строк из файла
     *
     * @param file        файл из которого происходит чтение
     * @param numRowStart Номер начальной строки
     * @param numRowEnd   Номер конечной строки
     * @return считанные строки
     * @deprecated сложно применимо
     */
    public static String[] fReadLine
    (File file, int numRowStart, int numRowEnd) {
        if (conclus) System.out.println("fReadLine");
        String[] a = new String[numRowEnd - numRowStart + 1];
        int k = 0, l = 0;
        a[0] = "";
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while (k < numRowStart)
                if ((char) reader.read() == '\n') k++;
            char s = ' ';
            while (k <= numRowEnd) {
                if (s == '\n') {
                    k++;
                    if (k <= numRowEnd)
                        a[++l] = "";
                } else
                    a[l] += s;
                s = (char) reader.read();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    /**
     * Читает весь файл
     *
     * @param file файл из которого происходит чтение
     * @return весь считанный файл
     * @deprecated долго и не нужно
     */
    public static StringBuilder fReadAll
    (File file) {
        if (conclus) System.out.println("fReadAll");
        StringBuilder a = new StringBuilder("");
        int k;
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while ((k = reader.read()) >= 0)
                a.append((char) k);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

}

