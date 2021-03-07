package sudoku;

import arithmetic.ArraysCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

import static arithmetic.Graphics.createGraphics2D;

public class ShowerSudoku extends JPanel implements Cloneable {
    private int sizeBox, size;

    private float defaultStrokeCell, modStrokeCell;
    private Color colorBorder = new Color(249, 170, 170);
    private Cell[][] cells;
    Font sudokuFont;

    /**
     * @throws IllegalArgumentException sudoku и {@link ShowerSudoku#cells} имеют разные глубины
     * @throws IllegalArgumentException sudoku не является простым
     * @throws IllegalArgumentException sudoku имеет размер не равный int[{@link ShowerSudoku#size}][{@link ShowerSudoku#size}]
     */
    public ShowerSudoku(int sizeBox, int[][] sudoku) throws IllegalArgumentException {
        this(sizeBox);
        setSudoku(sudoku);
    }

    /**
     * @throws IllegalArgumentException cells и {@link ShowerSudoku#cells} имеют разные глубины
     * @throws IllegalArgumentException cells не является простым
     * @throws IllegalArgumentException cells имеет размер не равный int[{@link ShowerSudoku#size}][{@link ShowerSudoku#size}]
     */
    public ShowerSudoku(int sizeBox, Cell[][] cells) throws IllegalArgumentException {
        this(sizeBox);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                this.cells[i][j].pasteInformation(cells[i][j]);
            }
        }
    }

    public ShowerSudoku() {
        this(3);
    }


    public ShowerSudoku(int sizeBox) {
        setSizeBox(sizeBox);
        setSudokuFont(new Font("Arial", Font.PLAIN, 1));
        setLayout(null);
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
        repaint();
    }

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    /**
     * @param sudoku массив, значения которого нужно сделать
     * @throws IllegalArgumentException sudoku и {@link ShowerSudoku#cells} имеют разные глубины
     * @throws IllegalArgumentException sudoku не является простым
     * @throws IllegalArgumentException sudoku имеет размер не равный int[{@link ShowerSudoku#size}][{@link ShowerSudoku#size}]
     */
    public void setSudoku(int[][] sudoku) throws IllegalArgumentException {
        ArraysCalculator.checkEvenArrayException(sudoku, size, size);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j].setVal(sudoku[i][j]);
            }
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        reBoundsCell();
        repaint();
    }

    private Cell[][] generateNewCells(int sizeBox) {
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new Cell(sizeBox);
                cells[i][j].setBackground(new Color(0, 0, 0, 0));
                add(cells[i][j]);
            }
        }
        return cells;
    }

    public void setSizeBox(int sizeBox) {
        this.sizeBox = sizeBox;
        size = sizeBox * sizeBox;
        cells = generateNewCells(sizeBox);
        reBoundsCell();
        repaint();
    }

    public void setSudokuFont(Font sudokuFont) {
        this.sudokuFont = sudokuFont;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j].setDefaultFont(sudokuFont);
            }
        }
        reBoundsCell();
        repaint();
    }

    public Font getSudokuFont() {
        return sudokuFont;
    }

    public void setVisibleCandidates(boolean visibleCandidates) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j].setVisibleCandidates(visibleCandidates);
            }
        }
    }

    public void setVisibleValue(boolean visibleValue) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j].setVisibleValue(visibleValue);
            }
        }
    }

    @Override
    public ShowerSudoku clone() throws CloneNotSupportedException {
        super.clone();
        ShowerSudoku cloneSudoku = new ShowerSudoku(sizeBox, cells);
        cloneSudoku.setSudokuFont(getSudokuFont());
        cloneSudoku.setBackground(getBackground());
        cloneSudoku.setBounds(getBounds());
        return cloneSudoku;
    }


    private float calculateSizeAllStroke() {
        return (size - sizeBox + 2) * defaultStrokeCell + (sizeBox - 1) * modStrokeCell;
    }

    private boolean isModLine(int n) {
        return n != 0 && n != size && (n % sizeBox == 0);
    }

    private float calculateCorrectionStroke(int n) {
        return (float) (n + 2 - n / sizeBox) * defaultStrokeCell + (float) (n / sizeBox) * modStrokeCell - (n >= size ? modStrokeCell : 0);
    }

    private float calculateCorrectionStrokeCenter(int n) {
        float center;
        if (isModLine(n))
            center = modStrokeCell;
        else
            center = defaultStrokeCell;
        return calculateCorrectionStroke(n) - center;
    }

    private void reBoundsCell() {
        final float FACTOR_STROKE = 4.0F;
        defaultStrokeCell = Math.min(getHeight(), getWidth()) / (float) (size * 50);
        modStrokeCell = (FACTOR_STROKE * defaultStrokeCell);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j].setBounds(
                        (int) (i * ((getWidth() - calculateSizeAllStroke()) / size) + calculateCorrectionStroke(i)),
                        (int) (j * ((getHeight() - calculateSizeAllStroke()) / size) + calculateCorrectionStroke(j)),
                        (int) ((getWidth() - calculateSizeAllStroke()) / size),
                        (int) ((getHeight() - calculateSizeAllStroke()) / size));
            }
        }
    }

    private void drawBorder(Graphics2D g2d) {
        g2d.setColor(colorBorder);
        for (int i = 0; i < size + 1; ++i) {
            g2d.setStroke(new BasicStroke(isModLine(i) ? modStrokeCell : defaultStrokeCell, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(new Line2D.Double((i * ((getWidth() - calculateSizeAllStroke()) / size) + calculateCorrectionStrokeCenter(i)),
                    0,
                    (i * ((getWidth() - calculateSizeAllStroke()) / size) + calculateCorrectionStrokeCenter(i)),
                    getHeight()
            ));
            g2d.setStroke(new BasicStroke(isModLine(i) ? modStrokeCell : defaultStrokeCell, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(new Line2D.Double(0,
                    (i * ((getHeight() - calculateSizeAllStroke()) / size) + calculateCorrectionStrokeCenter(i)),
                    getWidth(),
                    (i * ((getHeight() - calculateSizeAllStroke()) / size) + calculateCorrectionStrokeCenter(i))
            ));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = createGraphics2D(g);
        drawBorder(g2d);
    }
}
