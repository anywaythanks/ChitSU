package sample;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

/**
 * @author anywaythanks
 * @version 1.0
 */

public class Controller {
    /**
     * Создание класса логики, для использования функций
     */
    public static Logic log;
    /**
     * Буфер, для создания окон после основного.
     */
    public static Stage stageBuf;
    /**
     * Буфер, для создания окон после нулевого буфера.
     */
    public static Stage stageBuf1;
    /**
     * Нужна ли загрузка сохранения
     */
    public static boolean loading = false;
    /**
     * Нужно ли выводить окно в обучающем режиме
     */
    public static boolean winStL = true;
    public static boolean clearSud = false;
    /**
     * Таблица с ComboBox
     */
    @FXML
    private Sudoku sudoku;
    /**
     * Кнопка загрузки сохранений
     */
    @FXML
    private MenuItem load;
    /**
     * Кнопка для сохраниния
     */
    @FXML
    private MenuItem save;
    /**
     * Кнопка помощи
     */
    @FXML
    private MenuItem help;
    /**
     * Кнопка получения основной информации о программе
     */
    @FXML
    private MenuItem information;
    /**
     * Кнопка для решения судоку
     */
    @FXML
    private MenuItem resh;
    /**
     * Кнопка очистки поля
     */
    @FXML
    private MenuItem clear;
    /**
     * Кнопка настройки цветов
     */
    @FXML
    private MenuItem ColorSetting;
    /**
     * Кнопка легкой генерации
     */

    @FXML
    private MenuItem generationE;
    /**
     * Кнопка средней генерации
     */
    @FXML
    private MenuItem generationM;
    /**
     * Кнопка сложной генерации
     */
    @FXML
    private MenuItem generationH;

    @FXML
    private MenuItem exit;
    /**
     * Кнопка перехода в классический режим игры
     */
    @FXML
    public RadioMenuItem classic;
    /**
     * Кнопка перехода в творческий режим игры
     */
    @FXML
    public RadioMenuItem creative;
    /**
     * Кнопка подсветки неправильных клеток
     */
    @FXML
    public RadioMenuItem highlighting;
    /**
     * Кнопка для показа кандидатов
     */
    @FXML
    public RadioMenuItem showCandidates;
    /**
     * Кнопка обучающего режима
     */
    @FXML
    public RadioMenuItem learning;
    /**
     * Кнопка подсветки одинаковых цифр
     */
    @FXML
    public RadioMenuItem highlightingAllNum;

    @FXML
    AnchorPane Fon;
    @FXML
    GridPane container;

    private int[][] bufBoard;

    private void importSet
            () {
        int a, k = 0;
        int qSet = 7, QCol = 8;
        String s[] = new String[qSet];

        try (FileReader reader = new FileReader(Logic.F_AUTO_SAVE_SET.getAbsolutePath())) {
            while ((a = reader.read()) != -1)
                if (k != qSet)
                    s[k++] = ((char) (a)) + "";
                else {
                    k = 42;
                    break;
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (k != qSet)
            s = Logic.DEFAULT_SET.split(" ");
        classic.setSelected(log.stringToBool(s[0]));
        creative.setSelected(log.stringToBool(s[1]));
        highlighting.setSelected(log.stringToBool(s[2]));
        highlightingAllNum.setSelected(log.stringToBool(s[3]));
        showCandidates.setSelected(log.stringToBool(s[4]));
        learning.setSelected(log.stringToBool(s[5]));
        sudoku.setHighlightingAllNum(highlightingAllNum.isSelected());
        sudoku.setHighlighting(highlighting.isSelected());
        sudoku.setShowCandidates(showCandidates.isSelected());
        winStL = log.stringToBool(s[6]);
        StringBuilder s1 = new StringBuilder("");
        if (Logic.F_AUTO_SAVE_COLOR.exists())
            try (FileReader reader = new FileReader(Logic.F_AUTO_SAVE_COLOR.getAbsolutePath())) {
                while ((a = reader.read()) != -1)
                    s1.append((char) a);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        String[] ser = s1.toString().split(" ");
        if (ser.length != QCol)
            ser = Logic.DEF_COL.split(" ");
        sudoku.setColorWrongLabel(Color.valueOf(ser[0]));
        sudoku.setColorBaseLabel(Color.valueOf(ser[1]));
        sudoku.setColorBaseFont(Color.valueOf(ser[2]));
        sudoku.setColorCoincidentalLabel(Color.valueOf(ser[3]));
        sudoku.setColorNoCandidateFont(Color.valueOf(ser[4]));
        sudoku.setColorCandidateFont(Color.valueOf(ser[5]));
        sudoku.setDisableOpacity(Double.valueOf(ser[6]));
        Main.stage.setFullScreen(log.stringToBool(ser[7]));
        log.load();
        exportSet();

    }

    /**
     * Экпорт настроек в {@link Logic#F_AUTO_SAVE_SET} и {@link Logic#F_AUTO_SAVE_COLOR}
     */
    private void exportSet
    () {
        String s = "" + log.boolToInt(classic.isSelected()) + log.boolToInt(creative.isSelected())
                + log.boolToInt(highlighting.isSelected()) + log.boolToInt(highlightingAllNum.isSelected()) + log.boolToInt(showCandidates.isSelected()) + log.boolToInt(learning.isSelected()) + log.boolToInt(winStL);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Logic.F_AUTO_SAVE_SET), "UTF-8"))) {
            writer.append(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String s2 = log.getRGBAModel(sudoku.getColorWrongLabel()) + " "
                + log.getRGBAModel(sudoku.getColorBaseLabel()) + " "
                + log.getRGBAModel(sudoku.getColorBaseFont()) + " "
                + log.getRGBAModel(sudoku.getColorCoincidentalLabel()) + " "
                + log.getRGBAModel(sudoku.getColorNoCandidateFont()) + " "
                + log.getRGBAModel(sudoku.getColorCandidateFont()) + " "
                + sudoku.getDisableOpacity() + " "
                + log.boolToInt(Main.stage.isFullScreen());
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Logic.F_AUTO_SAVE_COLOR), "UTF-8"))) {
            writer.append(s2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Переключение {@link Controller#classic}, {@link Controller#creative} и {@link Controller#learning}, на тот номер, который выбран
     *
     * @param mode номер режима
     */
    private void setSelMod
    (int mode) {
        creative.setSelected(mode == 0);
        classic.setSelected(mode == 1);
        learning.setSelected(mode == 2);
        exportSet();
        resh.setText(learning.isSelected() ? "Подсказка" : "Решить");
        if (learning.isSelected())
            bufBoard = sudoku.getBoardSolve();
        save.setDisable(learning.isSelected());
        load.setDisable(learning.isSelected());
    }

    /**
     * Генерация рандомной традиционной судоку. В определенном рандомном промежутке
     *
     * @param Min Минимальное кол-во элементов,которые в {@link Controller#sudoku} будут равны нулю.
     * @param Max Максимальное кол-во элементов,которые в {@link Controller#sudoku} будут равны нулю.
     */
    private void generation
    (int Min, int Max) {
        Max = Math.max(Max, Min);
        Min = Math.min(Max, Min);
        Random r = new Random();
        int rand = r.nextInt(Max);
        while (rand < Min)
            rand = r.nextInt(Max);
        sudoku.generate(rand);
        sudoku.allDisable();
        log.save();
        setSelMod((!learning.isSelected()) ? 1 : 2);
        exportSet();
        ControlSave2.textLabel = "Успешно сгенерированоツ";
        stageBuf = log.setStage(stageBuf, "Генерация", "Markup/sucefull.fxml", "Style/Save.css");
        Controller.stageBuf.show();
    }

    private int computeBoxNo(int i, int j) {
        int boxRow = i / sudoku.getmBoxSize();
        int boxCol = j / sudoku.getmBoxSize();
        return boxRow * sudoku.getmBoxSize() + boxCol;
    }

    private int width, height;


    @FXML
    public void initialize
            () {
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        resh.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        generationE.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        generationM.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
        generationH.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));

        ColorSetting.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
        highlightingAllNum.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        highlighting.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
        showCandidates.setAccelerator(KeyCombination.keyCombination("Ctrl+K"));

        help.setAccelerator(KeyCombination.keyCombination("F1"));

        container.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.intValue() - 100;
            sudoku.setDimension(Math.min(width, height));
        });
        container.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.intValue() - 100;
            sudoku.setDimension(Math.min(width, height));
        });
        log = new Logic(sudoku);
        importSet();

        exit.setOnAction(event -> Main.stage.close());
        sudoku.addEventHandler(Sudoku.ON_WIN, event -> {
            if (classic.isSelected()) {
                ControlSave2.textLabel = "Поздравляем!\nВы решили судоку!";
                stageBuf = log.setStage(stageBuf, "Поздравляем", "Markup/congratulations.fxml", "Style/Congr.css");
                stageBuf.show();
            }
        });
        sudoku.addEventHandler(Sudoku.ON_NEW_VALUE, event -> {
            if (LearningPrompt.boxSize == -1) LearningPrompt.boxSize = sudoku.getmBoxSize();
            if (learning.isSelected()) {
                Cell cellVal = new Cell(sudoku.getCoordinateNewValue(), sudoku.getVal(sudoku.getCoordinateNewValue().x, sudoku.getCoordinateNewValue().y));
                if (!sudoku.isErroneous(cellVal.x, cellVal.y)) {
                    if (!(cellVal.val == 0 ||
                            bufBoard[cellVal.x][cellVal.y] == cellVal.val)) {
                        Cell cellError = sudoku.nullCandidates();
                        String s = "Вы допустили логическую ошибку!";
                        if (cellError.val != -1) {
                            cellError.translate(1, 1);
                            s = "После такого хода клетка " +
                                    LearningPrompt.getCoordinate(cellError) +
                                    " не имеет кандидатов.";
                        } else {
                            cellError = sudoku.impossibleCandidates(cellVal.val);
                            cellError.translate(1, 1);
                            if (cellError.val != -1)
                                s = "После такого хода " +
                                        ((((cellError.location == 2 && LearningPrompt.computeBoxNo(cellError) + 1 == 2) || (cellError.location == 1 && cellError.x == 2)) ? "во " : "в ") + ((cellError.location == 2) ? (LearningPrompt.computeBoxNo(cellError) + 1) + " " : (cellError.location == 1) ? LearningPrompt.getCoordinateCol(cellError) + " " : "") + ((cellError.location == 0) ? "строке " : (cellError.location == 1) ? "столбце " : "районе ") + ((cellError.location == 0) ? LearningPrompt.getCoordinateRow(cellError) + " " : "")) +
                                        " нельзя поставить \"" + cellVal.val + "\".";
                        }
                        sudoku.setVal(cellVal.x, cellVal.y, 0);
                        ControlSave2.textLabel = s;
                        stageBuf = log.setStage(stageBuf, "Ошибка", "Markup/eror.fxml", "Style/Save.css");
                        stageBuf.show();
                    }
                } else {
                    String s = "";
                    if (sudoku.getmColSubset(cellVal.y, cellVal.val - 1) >= 2)
                        s = "строке ";
                    else if (sudoku.getmRowSubset(cellVal.x, cellVal.val - 1) >= 2)
                        s = "столбце ";
                    else if (sudoku.getmBoxSubset(computeBoxNo(cellVal.x, cellVal.y), cellVal.val - 1) >= 2)
                        s = "районе ";
                    sudoku.setVal(cellVal.x, cellVal.y, 0);
                    ControlSave2.textLabel = "В " + s + "уже имеется такой символ!";
                    stageBuf = log.setStage(stageBuf, "Ошибка", "Markup/eror.fxml", "Style/Save.css");
                    stageBuf.show();
                }
            }
            log.save();
        });
        showCandidates.setOnAction(event -> {
            sudoku.setShowCandidates(showCandidates.isSelected());
            exportSet();
        });
        highlightingAllNum.setOnAction(event -> {
            sudoku.setHighlightingAllNum(highlightingAllNum.isSelected());
            exportSet();
        });
        highlighting.setOnAction(event -> {
            sudoku.setHighlighting(highlighting.isSelected());
            exportSet();
        });
        save.setOnAction(event -> {
            stageBuf = log.setStage(stageBuf, "Сохранить", "Markup/save1.fxml", "Style/Save.css");
            stageBuf.show();
        });
        information.setOnAction(event -> {
            stageBuf = log.setStage(stageBuf, "О программе", "Markup/Information.fxml", "Style/InfStyle.css");
            stageBuf.show();
        });
        help.setOnAction(event -> {
            stageBuf = log.setStage(stageBuf, "Помощь", "Markup/Help.fxml", "Style/HelpStyle.css");
            stageBuf.setResizable(true);
            stageBuf.show();
        });
        ColorSetting.setOnAction(event -> {
            stageBuf = log.setStage(stageBuf, "Настройки", "Markup/setting.fxml", "Style/SettingStyle.css");
            stageBuf.showAndWait();
            exportSet();
        });

        generationE.setOnAction(event -> generation(10, 20));
        generationM.setOnAction(event -> generation(30, 40));
        generationH.setOnAction(event -> generation(70, 80));

        creative.setOnAction(event -> {
            if (creative.isSelected()) {
                sudoku.clearDisable();
                log.save();
            }
            setSelMod(0);
        });
        classic.setOnAction(event -> {
            if (classic.isSelected()) {
                if (creative.isSelected())
                    sudoku.allDisable();
                log.save();
            }
            setSelMod(1);
        });
        resh.setOnAction(event -> {
            if (!learning.isSelected()) {
                boolean alertType;
                String messange;
                if (sudoku.isErroneous()) {
                    int rez = sudoku.solve();
                    switch (rez) {
                        case 2:
                            alertType = true;
                            messange = "Успешно решеноツ\nСудоку традиционная.";
                            break;
                        case 1:
                            alertType = true;
                            messange = "Успешно решеноツ\nСудоку не традиционная.";
                            break;
                        default:
                            alertType = false;
                            messange = "Судоку не имеет решения или уже решена.";
                            break;
                    }
                } else {
                    alertType = false;
                    messange = "Судоку содержит ошибки.";
                }
                exportSet();
                log.save();
                ControlSave2.textLabel = messange;
                stageBuf = log.setStage(stageBuf, "Решение", alertType ? "Markup/sucefull.fxml" : "Markup/eror.fxml", "Style/Save.css");
                stageBuf.show();
            } else {
                new LearningPrompt(sudoku);
            }
        });
        clear.setOnAction(event -> {
            ControlNot.operation = 2;
            ControlNot.textLabel = "Вы точно хотите очистить поле?";
            Controller.stageBuf1 = Controller.log.setStage(Controller.stageBuf1, "Очистка", "Markup/notice.fxml", "Style/Load.css");
            Controller.stageBuf1.showAndWait();
            if (clearSud) {
                if (creative.isSelected())
                    sudoku.clearAll();
                else
                    sudoku.clear();
                log.save();
                clearSud = false;
            }
        });
        load.setOnAction(event -> {
            Logic.setNames();

            if (Logic.masName.length == 0) {
                ControlSave2.textLabel = "Нет сохранений!";
            }
            stageBuf = log.setStage(stageBuf,
                    (Logic.masName.length > 0) ? "Загрузить" : "Ошибка",
                    (Logic.masName.length > 0) ? "Markup/load1.fxml" : "Markup/eror.fxml",
                    "Style/Load.css");
            stageBuf.showAndWait();
            if (loading) {
                loading = false;
                String name = ControlLoad1.name;
                setSelMod(log.load(name));
                log.save();
            }
        });
        learning.setOnAction(event -> {
            boolean incidents = false;
            if (learning.isSelected()) {
                if (!(sudoku.isErroneous() && sudoku.isTradition())) {
                    ControlSave2.textLabel = "Ваша судоку не является традиционной!";
                    stageBuf = log.setStage(stageBuf, "Ошибка", "Markup/eror.fxml", "Style/Save.css");
                    stageBuf.show();
                    incidents = true;
                } else {
                    if (creative.isSelected())
                        sudoku.allDisable();
                    log.save();
                    if (winStL) {
                        stageBuf = log.setStage(stageBuf,
                                "Добро пожаловать",
                                "Markup/startWinLearning.fxml",
                                "Style/stWL.css");
                        stageBuf.showAndWait();
                    }

                }
            }
            if (!incidents)
                setSelMod(2);
            else
                learning.setSelected(!learning.isSelected());
        });
        setSelMod(creative.isSelected() ? 0 : classic.isSelected() ? 1 : 2);
    }
}