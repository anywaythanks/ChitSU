package sudoku;

import arithmetic.ArraysCalculator;
import sudoku.solver.DancingLinksAlgorithm;

import java.util.Random;

public class LogicSudoku {
    /**
     * Размер судоку.
     */
    int size;
    /**
     * Размер box.
     */
    int sizeBox;
    /**
     * Судоку.
     */
    int[][] sudoku;

    /**
     * Кол-во элементов в строке
     */
    private int rowSubset[][];
    /**
     * Кол-во элементов в стобце
     */
    private int colSubset[][];
    /**
     * Кол-во элементов в районе
     */
    private int boxSubset[][];

    public LogicSudoku(int sizeBox) {
        this(sizeBox, new int[sizeBox * sizeBox][sizeBox * sizeBox]);
    }

    public LogicSudoku(int sizeBox, int[][] sudoku) {
        this.sudoku = new int[sizeBox * sizeBox][sizeBox * sizeBox];

        setSudoku(sizeBox, sudoku);
    }

    public void setSudoku(int sizeBox, int[][] sudoku) {
        this.sizeBox = sizeBox;
        size = sizeBox * sizeBox;
        rowSubset = new int[size][size];
        colSubset = new int[size][size];
        boxSubset = new int[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                setVal(i, j, sudoku[i][j]);
    }

    /**
     * Задать значение по координатам.
     *
     * @param x   номер строки
     * @param y   номер столбца
     * @param val новое значение
     */
    public void setVal(int x, int y, int val) {
        setSubsetValue(x, y, val, sudoku[x][y]);
        sudoku[x][y] = val;
    }

    /**
     * После полученного решения нам необходимо получить задачу (именно в такой последовательности мы можем гарантировать однозначность решения). И это самая сложная часть. Какое количество можно убрать, чтобы гарантировать однозначность решения? Это один из важных факторов, от которого зависит сложность Судоку. Всего в Судоку 81 клетка, обычно считают лёгким когда на поле есть 30-35 «подсказок», средним — 25-30, и сложным — 20-25. Это данные большого набора реальных примеров. Нет никаких законов для сложности. Можно сделать 30-клеточный неразрешимый вариант и 22 клеточный «лёгкий».
     * <p>
     * - **Случайный подход** — можно попробовать выкинуть 50-60 клеток наугад, но где вероятность что Судоку можно будет решить? Например, если заполнены 3 строки ( = 27 клеток)
     * - **Случайно с простым ограничением** — для примера можно взять некое число N в качестве предела, так что N строк и столбцов могут быть пустыми. Принимая N = 0 — для лёгких уровней, N=1 — средний, N=2 — сложный
     * <p>
     * Итак, приступим к вычёркиванию ячеек (все варианты равнозначны, поэтому у нас 81 ячейка, которую можно вычеркнуть, поэтому проверим все перебором):
     * <p>
     * 1. Выбрать случайную ячейку N
     * 2. Отметить N просмотренной
     * 3. Удалить N
     * 4. Посчитать решения. Если оно не единственное, то вернуть N обратно
     * 5. Проверить diif.
     *
     * @param difficult оценивает сложность — количество оставшихся элементов.
     * @return самая сложная из возможных вариантов Судоку для данного перемешивания.
     * @code if (diff <= difficult)
     * break;
     * @endcode
     */
    public int[][] generateSudoku(int difficult) {
        Random random = new Random();
        DancingLinksAlgorithm solver;
        int[][] solveSudoku;
        boolean[][] viewed;
        int x, y, buffVal, diff;

        do {
            solver = new DancingLinksAlgorithm(sizeBox);
            solveSudoku = mixSudoku(solver.solve(new int[size][size], random.nextInt(100) + 1));
            viewed = new boolean[size][size];
            diff = size * size;
            if (diff == difficult)
                break;
            for (int i = 0; i < size * size * size; ++i) {
                x = random.nextInt(size);
                y = random.nextInt(size);
                if (!viewed[x][y]) {
                    viewed[x][y] = true;
                    buffVal = solveSudoku[x][y];
                    solveSudoku[x][y] = 0;
                    diff--;

                    if (solver.getQuantitySolve(solveSudoku, 3) != 1) {
                        solveSudoku[x][y] = buffVal;
                        diff++;
                    }
                    if (diff <= difficult)
                        break;
                }

            }
        } while (diff > difficult);
        return solveSudoku;
    }

    /**
     * Перемешивание судоку с помощью:
     * <p>
     * {@link LogicSudoku#swapColumnsArea(int[][])}
     * <p>
     * {@link LogicSudoku#swapColumnsSmall(int[][])}
     * <p>
     * {@link LogicSudoku#swapRowsArea(int[][])}
     * <p>
     * {@link LogicSudoku#swapRowsSmall(int[][])}
     * <p>
     * {@link LogicSudoku#transporting(int[][])}
     *
     * @param sudoku таблица судоку.
     * @return новая судоку.
     */
    private int[][] mixSudoku(int[][] sudoku) {
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            switch (random.nextInt(5)) {
                case 0:
                    sudoku = swapRowsSmall(sudoku).clone();
                    break;
                case 1:
                    sudoku = swapColumnsSmall(sudoku).clone();
                    break;
                case 2:
                    sudoku = swapRowsArea(sudoku).clone();
                    break;
                case 3:
                    sudoku = swapColumnsArea(sudoku).clone();
                    break;
                default:
                    sudoku = transporting(sudoku).clone();
                    break;
            }
        }
        return sudoku;
    }

    /**
     * Обмен двух строк в пределах одного района
     *
     * @param sudoku таблица судоку.
     * @return новая судоку.
     * @image html swap.jpg
     */
    private int[][] swapRowsSmall(int[][] sudoku) {
        Random random = new Random();

        int box = random.nextInt(sizeBox);
        int line1 = random.nextInt(sizeBox);
        int line2 = random.nextInt(sizeBox);
        while (line1 == line2)
            line2 = random.nextInt(sizeBox);

        line1 += box * sizeBox;
        line2 += box * sizeBox;

        int buffInt;
        for (int i = 0; i < sudoku.length; ++i) {
            buffInt = sudoku[line1][i];
            sudoku[line1][i] = sudoku[line2][i];
            sudoku[line2][i] = buffInt;
        }
        return sudoku;
    }

    /**
     * Обмен двух столбцов в пределах одного район
     *
     * @param sudoku таблица судоку.
     * @return новая судоку.
     * @image html swap.jpg
     */
    private int[][] swapColumnsSmall(int[][] sudoku) {
        Random random = new Random();

        int box = random.nextInt(sizeBox);
        int column1 = random.nextInt(sizeBox);
        int column2 = random.nextInt(sizeBox);
        while (column1 == column2)
            column2 = random.nextInt(sizeBox);

        column1 += box * sizeBox;
        column2 += box * sizeBox;

        int buffInt;
        for (int i = 0; i < sudoku.length; ++i) {
            buffInt = sudoku[i][column1];
            sudoku[i][column1] = sudoku[i][column2];
            sudoku[i][column2] = buffInt;
        }
        return sudoku;
    }

    /**
     * Обмен двух столбцов в пределах одного район
     *
     * @param sudoku таблица судоку.
     * @return новая судоку.
     * @image html swapArea.jpg
     */
    private int[][] swapRowsArea(int[][] sudoku) {
        Random random = new Random();

        int box1 = random.nextInt(sizeBox) * sizeBox;
        int box2 = random.nextInt(sizeBox) * sizeBox;
        while (box1 == box2)
            box2 = random.nextInt(sizeBox) * sizeBox;

        int buffInt;
        for (int i = 0; i < sizeBox; ++i) {
            for (int j = 0; j < sudoku.length; ++j) {
                buffInt = sudoku[box1 + i][j];
                sudoku[box1 + i][j] = sudoku[box2 + i][j];
                sudoku[box2 + i][j] = buffInt;
            }
        }
        return sudoku;
    }

    /**
     * Обмен двух столбцов в пределах одного район
     *
     * @param sudoku таблица судоку.
     * @return новая судоку.
     * @image html swapArea.jpg
     */
    private int[][] swapColumnsArea(int[][] sudoku) {
        Random random = new Random();

        int box1 = random.nextInt(sizeBox) * sizeBox;
        int box2 = random.nextInt(sizeBox) * sizeBox;
        while (box1 == box2)
            box2 = random.nextInt(sizeBox) * sizeBox;

        int buffInt;
        for (int i = 0; i < sizeBox; ++i) {
            for (int j = 0; j < sudoku.length; ++j) {
                buffInt = sudoku[j][box1 + i];
                sudoku[j][box1 + i] = sudoku[j][box2 + i];
                sudoku[j][box2 + i] = buffInt;
            }
        }
        return sudoku;
    }

    /**
     * Транспонирование всей таблицы — столбцы становятся строками и наоборот
     *
     * @param sudoku таблица судоку.
     * @return трансопртированная судоку.
     * @image html transporting.jpg
     */
    private int[][] transporting(int[][] sudoku) {
        int[][] newSudoku = new int[sudoku.length][sudoku.length];
        for (int i = 0; i < sudoku.length; ++i) {
            for (int j = 0; j < sudoku.length; ++j) {
                newSudoku[i][j] = sudoku[j][i];
            }
        }
        return newSudoku;
    }

    /**
     * Задает значения для {@link LogicSudoku#rowSubset}, {@link LogicSudoku#colSubset}, {@link LogicSudoku#boxSubset}.
     *
     * @param x      номер строки
     * @param y      номер столбца
     * @param newVal новое значение клетки
     * @param oldVal старое значение клетки
     */
    private void setSubsetValue(int x, int y, int newVal, int oldVal) {
        if (newVal != 0) {
            rowSubset[x][newVal - 1]++;
            colSubset[y][newVal - 1]++;
            boxSubset[computeBoxNo(x, y)][newVal - 1]++;
        }
        if (oldVal != 0) {
            rowSubset[x][oldVal - 1]--;
            colSubset[y][oldVal - 1]--;
            boxSubset[computeBoxNo(x, y)][oldVal - 1]--;
        }
    }

    /**
     * Сложный математический расчет нахождения района.
     *
     * @param i номер строки
     * @param j номер столбца
     * @return номер района
     */
    private int computeBoxNo(int i, int j) {
        int boxRow = i / sizeBox;
        int boxCol = j / sizeBox;
        return boxRow * sizeBox + boxCol;
    }

    public int[][] getCandidates(int x, int y) {
        int[][] candidates = new int[sizeBox][sizeBox];
        for (int i = 0; i < size; ++i) {
            if (rowSubset[x][i] == 0 && colSubset[y][i] == 0 && boxSubset[computeBoxNo(x, y)][i] == 0) {
                candidates[ArraysCalculator.coordinateMassive(i, sizeBox)[0]][ArraysCalculator.coordinateMassive(i, sizeBox)[1]] = i + 1;
            }
        }
        return candidates;
    }
}
