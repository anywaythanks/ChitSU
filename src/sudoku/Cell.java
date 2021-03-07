package sudoku;

import arithmetic.ArraysCalculator;

import javax.swing.*;
import java.awt.*;

import static arithmetic.Graphics.*;
import static arithmetic.NumberSystem.getCharNum;

public class Cell extends JPanel implements Cloneable {
    private int val, sizeBox;
    private int[][] candidates;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 1);
    private Color[][] candidatesColor;
    private Color valColor = Color.BLACK;
    private boolean visibleCandidates = true, visibleValue = true;


    public Cell(int sizeBox) {
        this(sizeBox, 0);
    }

    public Cell(int sizeBox, int val) {
        setSizeBox(sizeBox);
        setVal(val);
    }

    /**
     * @throws IllegalArgumentException candidates и {@link Cell#candidates} имеют разные глубины
     * @throws IllegalArgumentException candidates не является простым
     * @throws IllegalArgumentException candidates имеет размер не равный int[{@link Cell#sizeBox}][{@link Cell#sizeBox}]
     */
    public Cell(int sizeBox, int val, int[][] candidates) throws IllegalArgumentException {
        this(sizeBox, val);
        setCandidates(candidates);
    }

    public void setDefaultFont(Font defaultFont) {
        this.defaultFont = defaultFont.deriveFont(1.0F);
        repaint();
    }

    public void setVal(int val) {
        this.val = val;
        repaint();
    }

    public int getVal() {
        return val;
    }

    /**
     * @param candidates новые кандидаты клетки
     * @throws IllegalArgumentException candidates и {@link Cell#candidates} имеют разные глубины
     * @throws IllegalArgumentException candidates не является простым
     * @throws IllegalArgumentException candidates имеет размер не равный int[{@link Cell#sizeBox}][{@link Cell#sizeBox}]
     */
    public void setCandidates(int[][] candidates) throws IllegalArgumentException {
        ArraysCalculator.checkEvenArrayException(candidates, sizeBox, sizeBox);

        for (int i = 0; i < sizeBox; ++i) {
            System.arraycopy(candidates[i], 0, this.candidates[i], 0, sizeBox);
        }
        repaint();
    }

    public void setVisibleCandidates(boolean visibleCandidates) {
        this.visibleCandidates = visibleCandidates;
        repaint();
    }

    public boolean isVisibleCandidates() {
        return visibleCandidates;
    }

    public void setVisibleValue(boolean visibleValue) {
        this.visibleValue = visibleValue;
        repaint();
    }

    public boolean isVisibleValue() {
        return visibleValue;
    }

    public int[][] getCandidates() {
        return candidates;
    }

    public void setCandidate(int val, int i, int j) {
        candidates[i][j] = val;
        repaint();
    }

    public int getCandidate(int i, int j) {
        return candidates[i][j];
    }

    public void setSizeBox(int sizeBox) {
        this.sizeBox = sizeBox;
        candidates = new int[sizeBox][sizeBox];
        candidatesColor = new Color[sizeBox][sizeBox];
        for (int i = 0; i < sizeBox; ++i) {
            for (int j = 0; j < sizeBox; ++j) {
                candidatesColor[i][j] = Color.BLACK;
            }
        }
        repaint();
    }

    public int getSizeBox() {
        return sizeBox;
    }

    /**
     * @param candidatesColor новые цвета для всех кандидатов
     * @throws IllegalArgumentException candidatesColor и {@link Cell#candidatesColor} имеют разные глубины
     * @throws IllegalArgumentException candidatesColor не является простым
     * @throws IllegalArgumentException candidatesColor имеет размер не равный Color[{@link Cell#sizeBox}][{@link Cell#sizeBox}]
     */
    public void setCandidatesColor(Color[][] candidatesColor) throws IllegalArgumentException {
        ArraysCalculator.checkEvenArrayException(candidates, sizeBox, sizeBox);

        for (int i = 0; i < sizeBox; ++i) {
            System.arraycopy(candidatesColor[i], 0, this.candidatesColor[i], 0, sizeBox);
        }
        repaint();
    }

    public Color[][] getCandidatesColor() {
        return candidatesColor;
    }

    public void setCandidateColor(Color candidateColor, int i, int j) {
        this.candidatesColor[i][j] = candidateColor;
        repaint();
    }

    public Color getCandidateColor(int i, int j) {
        return candidatesColor[i][j];
    }

    public void setValColor(Color valColor) {
        this.valColor = valColor;
        repaint();
    }

    public Color getValColor() {
        return valColor;
    }

    /**
     * @param cell клетка, информацию с которой нужно скопировать
     * @throws IllegalArgumentException разные {@link Cell#sizeBox}
     */
    public void pasteInformation(Cell cell) throws IllegalArgumentException {
        setBackground(cell.getBackground());
        setVal(cell.getVal());
        setValColor(cell.getValColor());
        setCandidates(cell.getCandidates());
        setVisibleValue(cell.isVisibleValue());
        setVisibleCandidates(cell.isVisibleCandidates());
        setCandidatesColor(cell.getCandidatesColor());
    }

    @Override
    public Cell clone() throws CloneNotSupportedException {
        super.clone();
        Cell cloneCell = new Cell(sizeBox);
        try {
            cloneCell.pasteInformation(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        cloneCell.setBounds(getBounds());
        return cloneCell;
    }

    private void setMaxSizeFont(Graphics2D g2d, int sizeBox) {
        setMaxSizeFontContainer(g2d, getWidth() / (float) sizeBox, getHeight() / (float) sizeBox, "0");
    }

    private float calculateX(double zeroX, Graphics2D g2d, String text, int sizeBox) {
        return calculateStringMiddleXInContainer(zeroX, g2d, text, getWidth() / (double) sizeBox);
    }

    private float calculateY(double zeroY, Graphics2D g2d, int sizeBox) {
        return calculateStringMiddleYInContainer(zeroY, g2d, getHeight() / (double) sizeBox);
    }

    private void drawCandidates(Graphics2D g2d) {
        g2d.setFont(defaultFont);
        setMaxSizeFont(g2d, sizeBox);

        for (int i = 0; i < sizeBox; ++i)
            for (int j = 0; j < sizeBox; ++j) {
                g2d.setColor(candidatesColor[j][i]);
                g2d.drawString(getCharNum(candidates[j][i]),
                        calculateX(i * (getWidth() / (double) sizeBox), g2d, getCharNum(candidates[j][i]), sizeBox),
                        calculateY(j * (getHeight() / (double) sizeBox), g2d, sizeBox));
            }
    }

    private void drawVal(Graphics2D g2d) {
        g2d.setFont(defaultFont);
        setMaxSizeFont(g2d, 1);
        g2d.setColor(valColor);
        g2d.drawString(getCharNum(val),
                calculateX(0, g2d, getCharNum(val), 1),
                calculateY(0, g2d, 1));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = createGraphics2D(g);
        if (visibleCandidates) {
            drawCandidates(g2d);
        }
        if (visibleValue) {
            drawVal(g2d);
        }
    }
}
