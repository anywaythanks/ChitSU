package sample;

import java.util.Random;

class LearningPrompt {
    private Sudoku sudoku;
    private Cell cell;
    public static int boxSize =-1;
    private Cell[][] cellVBox;

    LearningPrompt(Sudoku sudoku) {
        this.sudoku = sudoku;
        boxSize = sudoku.getmBoxSize();
        cell = sudoku.lastCandidates();
        if (cell.val != -1) {
            sudoku.setVal(cell.x, cell.y, cell.val);
            sudoku.animatedCell(cell.x, cell.y, cell.val);
            cell.x++;
            cell.y++;
            viewWin1();
        } else {
            cell = sudoku.hiddenCandidates();
            if (cell.val != -1) {
                int q = 0;
                switch (cell.location) {
                    case 0:
                        for (int i = 0; i < sudoku.getmBoardSize(); ++i) if (sudoku.getVal(i, cell.y) == 0) q++;
                        cellVBox = new Cell[q][2];
                        q = 0;
                        for (int i = 0; i < sudoku.getmBoardSize(); ++i)
                            if (sudoku.getVal(i, cell.y) == 0) {
                                cellVBox[q][0] = new Cell(i, cell.y, -1);
                                cellVBox[q][1] = getErroneousCoordinate(sudoku, new Cell(i, cell.y, cell.val));
                                cellVBox[q][0].translate(1, 1);
                                cellVBox[q][1].translate(1, 1);
                                q++;
                            }
                        break;
                    case 1:
                        for (int i = 0; i < sudoku.getmBoardSize(); ++i) if (sudoku.getVal(cell.x, i) == 0) q++;
                        cellVBox = new Cell[q][2];
                        q = 0;
                        for (int i = 0; i < sudoku.getmBoardSize(); ++i)
                            if (sudoku.getVal(cell.x, i) == 0) {
                                cellVBox[q][0] = new Cell(cell.x, i, -1, cell.location);
                                cellVBox[q][1] = getErroneousCoordinate(sudoku, new Cell(cell.x, i, cell.val));
                                cellVBox[q][0].translate(1, 1);
                                cellVBox[q][1].translate(1, 1);
                                q++;
                            }
                        break;
                    default:
                        for (int i = cell.x / sudoku.getmBoxSize() * sudoku.getmBoxSize();
                             i < (cell.x / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++i)
                            for (int j = cell.y / sudoku.getmBoxSize() * sudoku.getmBoxSize();
                                 j < (cell.y / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++j)
                                if (sudoku.getVal(i, j) == 0) q++;

                        cellVBox = new Cell[q][2];
                        q = 0;

                        for (int i = cell.x / sudoku.getmBoxSize() * sudoku.getmBoxSize();
                             i < (cell.x / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++i)
                            for (int j = cell.y / sudoku.getmBoxSize() * sudoku.getmBoxSize();
                                 j < (cell.y / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++j)
                                if (sudoku.getVal(i, j) == 0) {
                                    cellVBox[q][0] = new Cell(i, j, -1, cell.location);
                                    cellVBox[q][1] = getErroneousCoordinate(sudoku, new Cell(i, j, cell.val));
                                    cellVBox[q][0].translate(1, 1);
                                    cellVBox[q][1].translate(1, 1);
                                    q++;
                                }
                        break;
                }
                sudoku.setVal(cell.x, cell.y, cell.val);
                sudoku.animatedCell(cell.x, cell.y, cell.val);
                cell.x++;
                cell.y++;
                viewWin2();
            }
        }
        Controller.log.save();
    }

    public static String getTextNum(int rez, int n) {
        switch (rez) {
            case 0:
                switch (n) {
                    case 1:
                        return "первой";
                    case 2:
                        return "второй";
                    case 3:
                        return "третьей";
                    case 4:
                        return "четвертой";
                    case 5:
                        return "пятой";
                    case 6:
                        return "шестой";
                    case 7:
                        return "седьмой";
                    case 8:
                        return "восьмой";
                    default:
                        return "девятой";
                }
            case 1:
                switch (n) {
                    case 1:
                        return "первая";
                    case 2:
                        return "вторая";
                    case 3:
                        return "третья";
                    case 4:
                        return "четвертая";
                    case 5:
                        return "пятая";
                    case 6:
                        return "шестая";
                    case 7:
                        return "седьмая";
                    case 8:
                        return "восьмая";
                    default:
                        return "девятая";
                }
            case 2:
                switch (n) {
                    case 1:
                        return "одному";
                    case 2:
                        return "двум";
                    case 3:
                        return "трем";
                    case 4:
                        return "четырем";
                    case 5:
                        return "пяти";
                    case 6:
                        return "шести";
                    case 7:
                        return "семи";
                    case 8:
                        return "восьми";
                    default:
                        return "девяти";
                }
            case 3:
                switch (n) {
                    case 1:
                        return "один";
                    case 2:
                        return "два";
                    case 3:
                        return "три";
                    case 4:
                        return "четыре";
                    case 5:
                        return "пять";
                    case 6:
                        return "шесть";
                    case 7:
                        return "семь";
                    case 8:
                        return "восьмь";
                    default:
                        return "девять";
                }
            default:
                switch (n) {
                    case 1:
                        return "первом";
                    case 2:
                        return "втором";
                    case 3:
                        return "третьем";
                    case 4:
                        return "четвертом";
                    case 5:
                        return "пятом";
                    case 6:
                        return "шестом";
                    case 7:
                        return "седьмом";
                    case 8:
                        return "восьмом";
                    default:
                        return "девятом";
                }
        }

    }

    private Cell getErroneousCoordinate(Sudoku sudoku, Cell cell) {
        for (int i = 0; i < sudoku.getmBoardSize(); ++i) {
            if (sudoku.getVal(i, cell.y) == cell.val)
                return new Cell(i, cell.y, cell.val, 0);
            if (sudoku.getVal(cell.x, i) == cell.val)
                return new Cell(cell.x, i, cell.val, 1);
        }
        for (int i = cell.x / sudoku.getmBoxSize() * sudoku.getmBoxSize();
             i < (cell.x / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++i) {

            for (int j = cell.y / sudoku.getmBoxSize() * sudoku.getmBoxSize();
                 j < (cell.y / sudoku.getmBoxSize() + 1) * sudoku.getmBoxSize(); ++j) {

                if (sudoku.getVal(i, j) == cell.val)
                    return new Cell(i, j, 2, 2);
            }
        }
        return new Cell();
    }

    private void viewWin1() {
        Prompt.textLabel = "Клетка " +
                getCoordinate(cell) +
                " может быть равна только \"" +
                cell.val +
                "\", так как все другие символы уже использованы.";
        Controller.stageBuf = Controller.log.setStage(Controller.stageBuf, "Подсказка", "Markup/prompt.fxml", "Style/prompt.css");
        Controller.stageBuf.show();
    }

    public static String getCoordinateRow(Cell cell) {
        return ((char) ('A' + cell.y - 1)) + "";
    }

    public static String getCoordinateCol(Cell cell) {
        return cell.x + "";
    }

    public static String getCoordinate(Cell cell) {
        return getCoordinateRow(cell) + getCoordinateCol(cell);
    }

    public static  int computeBoxNo(Cell cell) {
        int boxRow = cell.x / boxSize;
        int boxCol = cell.y / boxSize;
        return boxRow * boxSize + boxCol;
    }

    private Random random = new Random();

    private String getRandomFraze(String originalCell, String symbol, Cell cell) {
        boolean bunch = originalCell.split(" ").length > 1;
        String s = getCoordinate(cell);
        switch (random.nextInt(3)) {
            case 0:
                s = originalCell + " " + (bunch ? " недопустимы " : " недопустима ") + "ввиду наличия данного символа в клетке " + s + ", которая находится в " + ((cell.location == 0) ? "той же " : "том же ") + ((cell.location == 0) ? "строке " : (cell.location == 1) ? "столбце " : "районе ");
                break;
            case 1:
                s = originalCell + " " + (bunch ? "недопустимы " : "недопустима ") + ", так как в " + ((cell.location == 0) ? "данной " : "данном ") + ((cell.location == 0) ? "строке " : (cell.location == 1) ? "столбце " : "районе ") + "\"" + symbol + "\" стоит на клетке " + s;
                break;
            default:
                s = originalCell + " " + (bunch ? "запрещены " : "запрещена ") + "для выбора, так как " + ((((cell.location == 2 && computeBoxNo(cell) + 1 == 2) || (cell.location == 1 && cell.x == 2)) ? "во " : "в ") + ((cell.location == 2) ? (computeBoxNo(cell) + 1) + " " : (cell.location == 1) ? getCoordinateCol(cell) + " " : "") + ((cell.location == 0) ? "строке " : (cell.location == 1) ? "столбце " : "районе ") + ((cell.location == 0) ? getCoordinateRow(cell) + " " : "")) + "уже есть \"" + symbol + "\"(клетка " + getCoordinate(cell) + ")";
                break;
        }
        return s;
    }

    private void viewWin2() {
        Prompt.textLabel = "Докажем корректность постановки символа \"" + cell.val + "\" в клетку " + getCoordinate(cell) +
                ((((cell.location == 2 && computeBoxNo(cell) + 1 == 2) || (cell.location == 1 && cell.x == 2)) ? ". Во " : ". В ") + ((cell.location == 2) ? (computeBoxNo(cell) + 1) + " " : (cell.location == 1) ? getCoordinateCol(cell) + " " : "") + ((cell.location == 0) ? "строке " : (cell.location == 1) ? "столбце " : "районе ") + ((cell.location == 0) ? getCoordinateRow(cell) + " " : "")) +
                "этот символ среди уже поставленных отсутствует. Проанализируем оставшиеся незаполнеными клетки:";
        if (cellVBox != null) {
            String tr = "подходит.";
            Prompt.textVBox = new String[cellVBox.length];
            for (int i = 0; i < cellVBox.length; ++i) {
                Prompt.textVBox[i] = "• " + ((cellVBox[i][1].val == -1 ? tr : getCoordinate(cellVBox[i][1])).equals(tr) ? getCoordinate(cellVBox[i][0]) + " - " + tr :
                        getRandomFraze(getCoordinate(cellVBox[i][0]), sudoku.getCharNum(cell.val), cellVBox[i][1]));
            }
        }
        Controller.stageBuf = Controller.log.setStage(Controller.stageBuf, "Подсказка", "Markup/prompt.fxml", "Style/prompt.css");
        Controller.stageBuf.show();
    }
}
