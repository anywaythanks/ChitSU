package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Sudoku extends GridPane {


    /***************************************************************************
     *                                                                         *
     * TODO:Constructors                                                       *
     *                                                                         *
     **************************************************************************/


    public Sudoku() {
        this(9);
    }

    public Sudoku(int mBoardSize) {
        setmBoardSize(mBoardSize);
    }


    /* *************************************************************************
     *                                                                         *
     * TODO:Root Variables                                                    *
     *                                                                         *
     **************************************************************************/


    /**
     * Размер строки и столбца
     */
    private int mBoardSize;

    public void setmBoardSize(int mBoardSize) {
        mBoxSize = (int) Math.sqrt(mBoardSize);
        mBoxSubset = new int[mBoardSize][mBoardSize];
        mBoard = new int[mBoardSize][mBoardSize];
        mRowSubset = new int[mBoardSize][mBoardSize];
        mColSubset = new int[mBoardSize][mBoardSize];

        getStylesheets().add(getClass().getResource("Style/Sudoku.css").toExternalForm());
        if (this.mBoardSize != 0)
            getChildren().remove(0, getChildren().size());
        double buf = Math.sqrt(mBoardSize);
        this.mBoardSize = ((int) buf + ((buf - ((int) buf) == 0) ? 0 : 1)) * ((int) buf + ((buf - ((int) buf) == 0) ? 0 : 1));
        mBoardSize = this.mBoardSize;
        sudokuText = new Label[mBoardSize][mBoardSize];
        sudoku = new ComboBox[mBoardSize][mBoardSize];

        for (int i = 0; i < mBoardSize; ++i)
            for (int j = 0; j < mBoardSize; ++j) {
                int index = j * mBoardSize + i;
                sudoku[i][j] = new ComboBox<>();
                sudokuText[i][j] = new Label();
                sudokuText[i][j].setDisable(true);
                sudokuText[i][j].setOpacity(1);
                setHalignment(sudokuText[i][j], HPos.CENTER);
                setValignment(sudokuText[i][j], VPos.CENTER);
                sudoku[i][j].setStyle("-fx-background-color: rgba(0,0,0,0)");
                sudoku[i][j].setMaxWidth(getDimension() / mBoardSize);
                sudoku[i][j].setMinWidth(getDimension() / mBoardSize);
                sudoku[i][j].setMaxHeight(getDimension() / mBoardSize);
                sudoku[i][j].setMinHeight(getDimension() / mBoardSize);

                for (int c = 1; c < mBoardSize + 1; ++c)
                    sudoku[i][j].getItems().addAll(getCharNum(c));
                sudoku[i][j].setOnAction(event -> {
                    {
                        if (getBox(index).getValue() != null) {
                            if (getmBoard(index) == getNumChar(getBox(index).getValue())) {
                                setVal(index, 0);
                            } else {
                                setVal(index, getNumChar(getBox(index).getValue()));
                                updateLabel();
                            }
                            activatedHidden = false;
                            coordinateNewValue.setLocation(index % this.mBoardSize, index / this.mBoardSize);
                        }
                    }
                });
                sudoku[i][j].addEventHandler(ComboBox.ON_SHOWN, event -> showCandidat(index));
                sudoku[i][j].addEventHandler(ComboBox.ON_HIDDEN, event -> {
                    getBox(index).setValue(null);
                    if (isErroneousAll() && !activatedHidden) {
                        Event.fireEvent(this, new Event(ON_WIN));
                    } else if (!activatedHidden) {
                        Event.fireEvent(this, new Event(ON_NEW_VALUE));
                    }
                    activatedHidden = true;
                });
                add(sudokuText[i][j], i, j);
                add(sudoku[i][j], i, j);
                sudoku[i][j].setCursor(Cursor.HAND);
                sudoku[i][j].setOnMouseMoved(new EventHandler<MouseEvent>() {
                    int menuIndex = index;

                    @Override
                    public void handle(MouseEvent event) {
                        cellMove(menuIndex);
                    }
                });
            }
        setOnMouseMoved(event -> {
            if (highlightingAllNum) {
                int x = (int) (event.getX() / (dimension / 9));
                int y = (int) (event.getY() / (dimension / 9));
                if (x >= 9) x = 8;
                if (y >= 9) y = 8;
                int menuIndex = y * 9 + x;
                cellMove(menuIndex);
            } else {
                equalsIndex = -1;
                mouseIndex = -1;
                updateLabel();
            }
        });
        setOnMouseExited(event -> {
            equalsIndex = -1;
            mouseIndex = -1;
            updateLabel();
        });
        updateLabel();
        setBorder();
    }

    public int getmBoardSize() {
        return mBoardSize;
    }

    /**
     * Массив для ComboBox
     */
    private ComboBox<String> sudoku[][];

    private ComboBox<String> getBox(int index) {
        return sudoku[index % mBoardSize][index / mBoardSize];
    }


    /**
     * Массив для главных цифр в клетке
     */
    private Label sudokuText[][];

    private Label getText(int index) {
        return sudokuText[index % mBoardSize][index / mBoardSize];
    }

    /**
     * Файл скрипта для решения судоку.
     */
    private File fScript = new File("sudocu_monster.exe");

    public void setfScript(File fScript) {
        this.fScript = fScript;
    }


    /* *************************************************************************
     *                                                                         *
     * TODO:Logic Variables                                                    *
     *                                                                         *
     **************************************************************************/
    private int mouseIndex = -1;
    /**
     * Матрица судоку
     */
    private int[][] mBoard;

    private int getmBoard(int index) {
        return mBoard[index % mBoardSize][index / mBoardSize];
    }

    /**
     * Размер района
     */
    private int mBoxSize;

    public int getmBoxSize() {
        return mBoxSize;
    }

    /**
     * Кол-во элементов в строке
     */
    private int mRowSubset[][];

    public int getmRowSubset(int x, int val) {
        return mRowSubset[x][val];
    }

    /**
     * Кол-во элементов в стобце
     */
    private int mColSubset[][];

    public int getmColSubset(int x, int val) {
        return mColSubset[x][val];
    }

    /**
     * Кол-во элементов в районе
     */
    private int mBoxSubset[][];

    public int getmBoxSubset(int x, int val) {
        return mBoxSubset[x][val];
    }

    /**
     * Число для сравнения
     */
    private int equalsIndex = -1;

    private BufferedWriter bufferedWriter;

    private BufferedReader bufferedReader;

    /**
     * Нужно ли показывать кандидатов
     */
    private boolean showCandidates = true;

    public void setShowCandidates(boolean showCandidates) {
        this.showCandidates = showCandidates;
        updateLabel();
    }


    /**
     * Нужно ли выделять совпадающие цифры
     */
    private boolean highlightingAllNum = true;

    public void setHighlightingAllNum(boolean highlightingAllNum) {
        this.highlightingAllNum = highlightingAllNum;
        updateLabel();
    }


    /**
     * Нужно ли выделять неправильные цифры
     */
    private boolean highlighting = true;

    public void setHighlighting(boolean highlighting) {
        this.highlighting = highlighting;
        updateLabel();
    }


    /**
     * был ли активирован onHidden
     */
    private boolean activatedHidden = true;


    private boolean conclus = false;

    public void setConclus(boolean conclus) {
        this.conclus = conclus;
    }

    private Point coordinateNewValue = new Point();

    public Point getCoordinateNewValue() {
        return coordinateNewValue;
    }
    /* *************************************************************************
     *                                                                         *
     * TODO:Logic methods                                                      *
     *                                                                         *
     **************************************************************************/

    /**
     * Задает значения для {@link Sudoku#mRowSubset}, {@link Sudoku#mColSubset}, {@link Sudoku#mBoxSubset}.
     *
     * @param x    - номер строки
     * @param y    - номер столбца
     * @param val  - новое значение клетки
     * @param sign - нужно вычитать или же прибавлять. Прибавляем при true.
     */
    private void setSubsetValue(int x, int y, int val, boolean sign) {
        if (sign) {
            mRowSubset[x][val - 1]++;
            mColSubset[y][val - 1]++;
            mBoxSubset[computeBoxNo(x, y)][val - 1]++;
        } else {
            mRowSubset[x][val - 1]--;
            mColSubset[y][val - 1]--;
            mBoxSubset[computeBoxNo(x, y)][val - 1]--;
        }
    }

    /**
     * Сложный математический расчет нахождения района.
     *
     * @param i - номер строки
     * @param j - номер столбца
     * @return - номер района
     */
    private int computeBoxNo(int i, int j) {
        int boxRow = i / mBoxSize;
        int boxCol = j / mBoxSize;
        return boxRow * mBoxSize + boxCol;
    }

    private void input() {
        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void input(String s) {
        if (conclus) System.out.println(s);
        try {
            bufferedWriter.write(s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void input(int[][] abc) {
        StringBuilder s;
        for (int j = 0; j < mBoardSize; ++j) {
            s = new StringBuilder("");
            for (int i = 0; i < mBoardSize; ++i) {
                s.append(abc[i][j]);
            }
            input(s.toString());
        }
    }


    private void setBuf() {
        try {
            List<String> commands = new ArrayList<>();
            commands.add(fScript.getAbsolutePath());
            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showCandidat(int index) {
        int val = getmBoard(index);
        getBox(index).setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        setVal(index, 0);
                        super.updateItem(item, empty);
                        if (item != null) {
                            setTextFill((!showCandidates) ? colorBaseFont : isCandidates(index, getNumChar(item)) ? colorCandidateFont : colorNoCandidateFont);
                            setText(item);
                        } else {
                            setText(null);
                        }
                        setVal(index, val);
                    }
                };
            }
        });

    }

    /**
     * Является ли число кандидатом
     *
     * @param x   - номер строки
     * @param y   - номер столбца
     * @param val - значение элемента
     * @return Является ли чило кандидатом
     */
    public boolean isCandidates(int x, int y, int val) {
        return mRowSubset[x][val - 1] == 0 && mColSubset[y][val - 1] == 0 && mBoxSubset[computeBoxNo(x, y)][val - 1] == 0;
    }

    /**
     * Является ли число кандидатом
     *
     * @param index - индекс элемента
     * @param val   - значение элемента
     * @return Является ли чило кандидатом
     */
    private boolean isCandidates(int index, int val) {
        return isCandidates(index % mBoardSize, index / mBoardSize, val);
    }

    /**
     * Считает кол-во кандидатов в клетке.
     *
     * @param x - номер строки
     * @param y - номер столбца
     * @return кол-во кандидатов в клетке.
     */
    public int qCandidates(int x, int y) {
        int q = 0;
        for (int i = 1; i <= mBoardSize; ++i)
            if (isCandidates(x, y, i)) q++;
        return q;
    }

    /**
     * Очистить массив судоку, кроме заблокированных элементов
     */
    public void clear() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            if (!getBox(i).isDisable())
                setVal(i, 0);
    }


    /**
     * Очистить судоку полностью
     */

    public void clearAll() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            setVal(i, 0);
        clearDisable();
    }


    /**
     * Генерирует судоку заданной сложности
     *
     * @param complexity - то сколько клеток должны быть пустыми
     */

    public void generate(int complexity) {
        setBuf();
        String line;
        if (conclus) System.out.println("INPUT:");
        input("1");
        input(mBoxSize + "");
        input(complexity + "");
        input();
        if (conclus) System.out.println("OUTPUT:");
        try {
            int num = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (conclus) System.out.println(line);
                char[] abc;
                abc = line.toCharArray();
                for (int i = 0; i < mBoardSize; ++i) {
                    setVal(i, num, abc[i] - '0');
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Решить судоку.
     *
     * @return возвращает итог работы:
     * 0 - судоку не имеет решения
     * 1 - судоку имеет решение, но она не традиционна
     * 2 - судоку традиционна
     */
    public int solve() {
        setBuf();
        String line;
        if (conclus) System.out.println("INPUT:");
        input("0");
        input(mBoxSize + "");
        input(mBoard);
        input();
        if (conclus) System.out.println("OUTPUT:");
        int total = 0, num = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (conclus) System.out.println(line);
                switch (num) {
                    case 0:
                        total += line.charAt(0) - '0';
                        break;
                    case 1:
                        if (total == 1)
                            total += line.charAt(0) - '0';
                        break;
                    default:
                        char[] abc;
                        abc = line.toCharArray();
                        for (int i = 0; i < mBoardSize; ++i) {
                            setVal(i, num - 2, abc[i] - '0');
                        }
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public Cell nullCandidates() {
        for (int i = 0; i < mBoardSize; ++i)
            for (int j = 0; j < mBoardSize; ++j)

                if (mBoard[i][j] == 0 && qCandidates(i, j) == 0) {
                    for (int c = 1; c <= mBoardSize; ++c)
                        if (isCandidates(i, j, c))
                            return new Cell(i, j, c);
                }
        return new Cell();
    }

    public Cell impossibleCandidates(int val) {
        int q, j1, i1;
        for (int i = 0; i < mBoardSize; ++i) {
            q = 0;
            j1 = 0;
            for (int j = 0; j < mBoardSize; ++j) {
                if (mBoard[i][j] == val || (mBoard[i][j] == 0 && isCandidates(i, j, val))) {
                    q++;
                    j1 = j;
                }
            }
            if (q == 0)
                return new Cell(i, j1, val, 1);

            q = 0;
            j1 = 0;
            for (int j = 0; j < mBoardSize; ++j) {
                if (mBoard[j][i] == val || (mBoard[j][i] == 0 && isCandidates(j, i, val))) {
                    q++;
                    j1 = j;
                }
            }
            if (q == 0)
                return new Cell(j1, i, val, 0);
        }
        for (int i = 0; i < mBoxSize; ++i) {
            for (int j = 0; j < mBoxSize; ++j) {
                q = 0;
                for (int x = i * mBoxSize; x < (i + 1) * mBoxSize; ++x) {
                    for (int y = j * mBoxSize; y < (j + 1) * mBoxSize; ++y) {
                        if (mBoard[x][y] == val || (mBoard[x][y] == 0 && isCandidates(x, y, val))) {
                            q++;

                        }
                    }
                }
                if (q == 0)
                    return new Cell(i, j, val, 2);
            }
        }
        return new Cell();
    }

    public Cell lastCandidates() {
        for (int i = 0; i < mBoardSize; ++i)
            for (int j = 0; j < mBoardSize; ++j)

                if (mBoard[i][j] == 0 && qCandidates(i, j) == 1) {
                    for (int c = 1; c <= mBoardSize; ++c)
                        if (isCandidates(i, j, c))
                            return new Cell(i, j, c);
                }
        return new Cell();
    }

    /**
     * @return [0] - это х
     * [1] - это у
     * [2] - новое значение
     * [3] - строка - 0, столбец - 1, район - 2.
     */
    public Cell hiddenCandidates() {
        int q, j1, i1;
        for (int val = 1; val <= mBoardSize; ++val) {
            for (int i = 0; i < mBoardSize; ++i) {
                q = 0;
                j1 = 0;
                for (int j = 0; j < mBoardSize; ++j) {
                    if (mBoard[i][j] == 0 && isCandidates(i, j, val)) {
                        q++;
                        j1 = j;
                    }
                }
                if (q == 1)
                    return new Cell(i, j1, val, 1);

                q = 0;
                j1 = 0;
                for (int j = 0; j < mBoardSize; ++j) {
                    if (mBoard[j][i] == 0 && isCandidates(j, i, val)) {
                        q++;
                        j1 = j;
                    }
                }
                if (q == 1)
                    return new Cell(j1, i, val, 0);
            }
            for (int i = 0; i < mBoxSize; ++i) {
                for (int j = 0; j < mBoxSize; ++j) {
                    j1 = 0;
                    i1 = 0;
                    q = 0;
                    for (int x = i * mBoxSize; x < (i + 1) * mBoxSize; ++x) {
                        for (int y = j * mBoxSize; y < (j + 1) * mBoxSize; ++y) {
                            if (mBoard[x][y] == 0 && isCandidates(x, y, val)) {
                                q++;
                                j1 = y;
                                i1 = x;
                            }
                        }
                    }
                    if (q == 1)
                        return new Cell(i1, j1, val, 2);
                }
            }
        }
        return new Cell();
    }


    /**
     * Правильно ли поставлено число по координатам
     *
     * @param x - номер строки
     * @param y - номер столбца
     * @return Правильно ли поставлено число
     */
    public boolean isErroneous(int x, int y) {
        return (mBoard[x][y] != 0) && (mRowSubset[x][mBoard[x][y] - 1] >= 2 ||
                mColSubset[y][mBoard[x][y] - 1] >= 2 ||
                mBoxSubset[computeBoxNo(x, y)][mBoard[x][y] - 1] >= 2);
    }

    /**
     * Правильно ли поставлено число по индексу
     *
     * @param index - индекс клетки
     * @return Правильно ли поставлено число
     */
    public boolean isErroneous(int index) {
        return isErroneous(index % mBoardSize, index / mBoardSize);
    }

    /**
     * Правильно ли заполнена вся судоку, кроме нулей
     *
     * @return равильно ли заполнена судоку
     */
    public boolean isErroneous() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            if (isErroneous(i)) return false;
        return true;
    }

    /**
     * Правильно ли заполнена вся судоку
     *
     * @return правильно ли заполнена судоку
     */
    public boolean isErroneousAll() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            if (getmBoard(i) == 0 || isErroneous(i)) return false;
        return true;
    }


    /**
     * Проверяет судоку на традиционность
     *
     * @return традиционна ли судоку
     */
    public boolean isTradition() {
        setBuf();
        String line;
        if (conclus) System.out.println("INPUT:");
        input("0");
        input(mBoxSize + "");
        input(mBoard);
        input();
        if (conclus) System.out.println("OUTPUT:");
        int num = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (conclus) System.out.println(line);
                if (num++ == 1) {
                    return (line.charAt(0) - '0') > 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isEquals(int x, int y) {
        if (!highlightingAllNum)
            return mBoard[x][y] != 0 && y * 9 + x == equalsIndex;
        return mBoard[x][y] != 0 && mBoard[x][y] == equalsIndex;
    }

    /**
     * Заблокировать клетку по индексу
     *
     * @param index   индекс клетки
     * @param disable состояние блокирования
     */
    private void setDisable(int index, boolean disable) {
        setDisable(index % mBoardSize, index / mBoardSize, disable);
    }

    /**
     * Заблокировать клетку по координатам
     *
     * @param x       - номер строки
     * @param y       - номер столбца
     * @param disable состояние блокирования
     */
    public void setDisable(int x, int y, boolean disable) {
        sudoku[x][y].setDisable(disable);
    }

    /**
     * Очистить все блокировки
     */
    public void clearDisable() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            setDisable(i, false);
        updateLabel();
    }

    /**
     * Заблокировать все клетки, в которых есть значения.
     */
    public void allDisable() {
        for (int i = 0; i < mBoardSize * mBoardSize; ++i)
            setDisable(i, getmBoard(i) != 0);
        updateLabel();
    }


    /***************************************************************************
     *                                                                         *
     * TODO:Callbacks and Events                                               *
     *                                                                         *
     **************************************************************************/


    public static final EventType<Event> ON_WIN = new EventType<>(Event.ANY, "ON_WIN");

    public static final EventType<Event> ON_NEW_VALUE = new EventType<>(Event.ANY, "ON_NEW_VALUE");

    /* *************************************************************************
     *                                                                         *
     * TODO:private methods                                                    *
     *                                                                         *
     **************************************************************************/




    /**
     * Обратный перевод {@link Sudoku#getCharNum}
     *
     * @param s - Строка, которую нужно перевести в новую систему
     * @return От 0 до 9 обычные числа;
     * A, B, C и т.д. - 10, 11, 12 и т.д.
     */
    private int getNumChar(String s) {
        if (s.charAt(0) - '0' >= 0 && s.charAt(0) - '0' < 10)
            return s.charAt(0) - '0';
        return s.charAt(0) - 'A' + 10;
    }


    /**
     * Задает Border для клеток
     */
    private void setBorder() {
        int border = 1;
        int factor = 3;
        for (int i = 0; i < mBoardSize * mBoardSize; i++) {
            Integer index = i;
            int x = index % mBoardSize;
            int y = index / mBoardSize;
            x++;
            y = mBoardSize - y;

            getBox(i).setBorder(new Border(new BorderStroke(Color.valueOf("#f9aaaa"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                    new BorderWidths(
                            border * ((y != mBoardSize && y % (int) Math.sqrt(mBoardSize) == 0) ? factor : 1),
                            border * ((x != mBoardSize && x % (int) Math.sqrt(mBoardSize) == 0) ? factor : 1),
                            (y == 1) ? border : 0,
                            (x == 1) ? border : 0)
            )));
        }
    }


    /**
     * Получить RGBA модель {@link Color}
     *
     * @param color {@link Color} модель, которого нужно получить
     * @return RGBA модель
     */
    private String getRGBAModel(Color color) {
        return "rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + color.getOpacity() + ")";
    }


    /**
     * Задать прозрачность {@link Color}
     *
     * @param color      {@link Color}, прозрачность которого нужно изменить
     * @param newOpacity новое значение прозрачности для цвета
     * @return {@link Color} с новой прозрачностью
     */
    private Color setOpacity(Color color, double newOpacity) {
        return Color.valueOf("rgba(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + "," + newOpacity + ")");
    }

    /**
     * Поллучает оптимальный размер шрифта для {@link Sudoku#sudokuText}.
     * Чем-то вроде настройки выступает {@link Sudoku#factorFont}.
     *
     * @return оптимальный размер шрифта
     */
    private double getSizeFont() {
        return dimension / factorFont / mBoardSize;
    }


    /**
     * Подсветка клетки по индексу
     *
     * @param cellIndex - индекс клетки
     */
    private void cellMove(int cellIndex) {
        if (mouseIndex != cellIndex) {
            equalsIndex = highlightingAllNum ? getmBoard(cellIndex) : cellIndex;
            updateLabel();
            mouseIndex = cellIndex;
        }
    }


    /**
     * Обновляет цвета в {@link Sudoku#sudokuText}.
     */
    private void updateLabel() {
        String s;
        for (int i = 0; i < mBoardSize; ++i) {
            for (int j = 0; j < mBoardSize; ++j) {
                s = "-fx-font-size: " + getSizeFont() + ";-fx-text-fill:";
                if (sudoku[i][j].isDisable() && isEquals(i, j))
                    s += getRGBAModel(colorCoincidentalLabel);
                else if (isEquals(i, j))
                    s += getRGBAModel(colorCoincidentalLabel);
                else if (highlighting && sudoku[i][j].isDisable() && isErroneous(i, j))
                    s += getRGBAModel(setOpacity(colorWrongLabel, disableOpacity));
                else if (highlighting && isErroneous(i, j))
                    s += getRGBAModel(colorWrongLabel);
                else if (sudoku[i][j].isDisable())
                    s += getRGBAModel(setOpacity(colorBaseLabel, disableOpacity));
                else
                    s += getRGBAModel(colorBaseLabel);
                sudokuText[i][j].setStyle(s);
            }
        }
    }


    /* *************************************************************************
     *                                                                         *
     * TODO:public methods                                                     *
     *                                                                         *
     **************************************************************************/


    /**
     * Перевод числа в новую систему.
     *
     * @param num число, которое нужно перевести.
     * @return Итог перевода:
     * От 0 до 9 обычные числа;
     * От 10 - это A, B, C и т.д.
     */
    public String getCharNum(int num) {
        if (num < 10)
            return ((char) ('0' + num)) + "";
        return (char) ('A' + (num - 10)) + "";
    }



    public int[][] getBoardSolve() {
        setBuf();
        String line;
        if (conclus) System.out.println("INPUT:");
        input("0");
        input(mBoxSize + "");
        input(mBoard);
        input();
        if (conclus) System.out.println("OUTPUT:");
        int num = -1;
        int[][] mBoard = new int[mBoardSize][mBoardSize];
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (conclus) System.out.println(line);
                if (++num > 1) {
                    char[] abc = line.toCharArray();
                    for (int i = 0; i < mBoardSize; ++i)
                        mBoard[i][num - 2] = abc[i] - '0';
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBoard;
    }

    /**
     * Задать значение по координатам.
     *
     * @param x   - номер строки
     * @param y   - номер столбца
     * @param val - новое значение
     */
    public void setVal(int x, int y, int val) {
        if (mBoard[x][y] != 0)
            setSubsetValue(x, y, mBoard[x][y], false);
        mBoard[x][y] = val;
        if (val != 0) {
            setSubsetValue(x, y, val, true);
        }
        sudokuText[x][y].setText(val != 0 ? getCharNum(val) : "");

        updateLabel();
    }


    /**
     * Задать значение по индеку
     *
     * @param index - индекс клетки
     * @param val   - новое значение
     */
    public void setVal(int index, int val) {
        setVal(index % mBoardSize, index / mBoardSize, val);
    }


    /**
     * Задать значение массивом
     *
     * @param mas - новый массив
     */
    public void setVal(int[][] mas) {
        for (int i = 0; i < mBoardSize; ++i)
            for (int j = 0; j < mBoardSize; ++j)
                setVal(i, j, mas[i][j]);
    }

    public int getVal(int x, int y) {
        return mBoard[x][y];
    }

    public int getVal(int index) {
        return getmBoard(index);
    }

    public boolean isDisable(int x, int y) {
        return sudoku[x][y].isDisable();
    }

    public boolean isDisable(int index) {
        return getBox(index).isDisable();
    }

    public void animatedCell(int x, int y, int value) {
        equalsIndex = y * mBoardSize + x;
        boolean high = highlightingAllNum;
        highlightingAllNum = false;
        updateLabel();
        highlightingAllNum = high;
        equalsIndex = -1;
    }


    /* *************************************************************************
     *                                                                         *
     * TODO:Stylesheet Handling                                                *
     *                                                                         *
     **************************************************************************/

    /**
     * Цвет шрифта элемента, который не является кандидатом
     */
    private Color colorNoCandidateFont = Color.valueOf("rgba(0,0,0,1.0)");

    public Color getColorNoCandidateFont() {
        return colorNoCandidateFont;
    }

    public void setColorNoCandidateFont(Color colorNoCandidateFont) {
        this.colorNoCandidateFont = colorNoCandidateFont;
    }


    /**
     * Цвет шрифта элемента, который является кандидатом
     */
    private Color colorCandidateFont = Color.valueOf("rgba(0,128,0,1.0)");

    public void setColorCandidateFont(Color colorCandidateFont) {
        this.colorCandidateFont = colorCandidateFont;
    }

    public Color getColorCandidateFont() {
        return colorCandidateFont;
    }


    /**
     * Базовый цвет шрифта элемента
     */
    private Color colorBaseFont = Color.valueOf("rgba(0,0,0,1.0)");

    public void setColorBaseFont(Color colorBaseFont) {
        this.colorBaseFont = colorBaseFont;
    }

    public Color getColorBaseFont() {
        return colorBaseFont;
    }

    /**
     * Цвет неправильного элемента
     */
    private Color colorWrongLabel = Color.valueOf("rgba(255,0,0,1.0)");

    public void setColorWrongLabel(Color colorWrongLabel) {
        this.colorWrongLabel = colorWrongLabel;
        updateLabel();
    }

    public Color getColorWrongLabel() {
        return colorWrongLabel;
    }


    /**
     * Цвет совпадающего элемента
     */
    private Color colorCoincidentalLabel = Color.valueOf("rgba(21,109,117,1.0)");

    public void setColorCoincidentalLabel(Color colorCoincidentalLabel) {
        this.colorCoincidentalLabel = colorCoincidentalLabel;
        updateLabel();
    }

    public Color getColorCoincidentalLabel() {
        return colorCoincidentalLabel;
    }


    /**
     * стандартный цвет элемента
     */
    private Color colorBaseLabel = Color.valueOf("rgba(96,56,19,1.0)");

    public void setColorBaseLabel(Color colorBaseLabel) {
        this.colorBaseLabel = colorBaseLabel;
        updateLabel();
    }

    public Color getColorBaseLabel() {
        return colorBaseLabel;
    }


    /**
     * Размер поля
     */
    private int dimension = 600;

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
        updateLabel();
        for (int i = 0; i < mBoardSize; ++i)
            for (int j = 0; j < mBoardSize; ++j) {
                sudoku[i][j].setMaxWidth(getDimension() / mBoardSize);
                sudoku[i][j].setMinWidth(getDimension() / mBoardSize);
                sudoku[i][j].setMaxHeight(getDimension() / mBoardSize);
                sudoku[i][j].setMinHeight(getDimension() / mBoardSize);
            }

    }

    /**
     * Множитель шрифта
     */
    private double factorFont = 1.5;

    public void setFactorFont(double factorFont) {
        this.factorFont = factorFont;
    }


    /**
     * Прозрачность заблокированных элементов
     */
    private double disableOpacity = 0.7;

    public void setDisableOpacity(double disableOpacity) {
        this.disableOpacity = disableOpacity;
        updateLabel();
    }

    public double getDisableOpacity() {
        return disableOpacity;
    }
}