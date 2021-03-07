package sudoku;

import custom.UI.JTextComponentLimit;
import custom.UI.MinimalisticTextFieldUI;
import custom.UI.RedactedKeyListenerTextComponent;
import sudoku.event.NotValidText;

import javax.swing.*;
import java.awt.*;

import static arithmetic.Graphics.*;

public class WidgetSudoku extends JPanel {
    private final ShowerSudoku showerSudoku;
    private String mod;
    private Font nameSaveFont, modFont;
    private boolean redacted;
    private final int SEPARATOR = 5;
    private JTextField nameSaveTextField;
    private RedactedKeyListenerTextComponent redactedKey;
    private final Dimension newSize = new Dimension();

    public WidgetSudoku(ShowerSudoku showerSudoku, String nameSave, String mod) {
        setLayout(null);
        createNameSaveTextField();
        nameSaveTextField.setText(nameSave);
        this.showerSudoku = showerSudoku;
        this.mod = mod;
        setNameSaveFont(new Font("Arial", Font.PLAIN, 1));
        setModFont(new Font("Arial", Font.PLAIN, 1));
        add(showerSudoku);
    }

    public void setNameSaveFont(Font nameSaveFont) {
        this.nameSaveFont = nameSaveFont.deriveFont(1.0F);
        repaint();
    }

    public Font getNameSaveFont() {
        return nameSaveFont;
    }

    public void setModFont(Font modFont) {
        this.modFont = modFont.deriveFont(1.0F);
        repaint();
    }

    public Font getModFont() {
        return modFont;
    }

    public void setNameSave(String nameSave) {
        nameSaveTextField.setText(nameSave);
    }

    public String getNameSave() {
        return nameSaveTextField.getText();
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getMod() {
        return mod;
    }

    public ShowerSudoku getShowerSudoku() {
        return showerSudoku;
    }

    public void addNotValidText(NotValidText validText) {
        redactedKey.addNotValidText(validText);
    }

    public boolean isRedacted() {
        return redacted;
    }


    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
        nameSaveTextField.addKeyListener(redacted ? redactedKey : null);
        nameSaveTextField.addMouseListener(redacted ? redactedKey : null);
        nameSaveTextField.setCursor(new Cursor(redacted ? Cursor.TEXT_CURSOR : Cursor.DEFAULT_CURSOR));
    }


    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        nameSaveTextField.setLocation(0, width);
        redactedKey.setRedactedSize(width, (height - width- SEPARATOR) / 2);
        showerSudoku.setBounds(0, 0, width, width);
    }

    private void createNameSaveTextField() {
        nameSaveTextField = new JTextField();
        final int LIMIT_SYMBOL = 20;
        nameSaveTextField.setUI(new MinimalisticTextFieldUI("Напишите имя сохранения"));
        redactedKey = new RedactedKeyListenerTextComponent(nameSaveTextField, LIMIT_SYMBOL);
        nameSaveTextField.setDocument(new JTextComponentLimit(LIMIT_SYMBOL));
        nameSaveTextField.setCaretColor(Color.BLACK);
        nameSaveTextField.setSelectedTextColor(Color.BLACK);
        nameSaveTextField.setForeground(Color.BLACK);
        nameSaveTextField.setSelectionColor(new Color(28, 70, 191, 154));
        setRedacted(false);
        redactedKey.translateDefaultStateRedactedTextComponent(false);
        add(nameSaveTextField);
    }

    private void setWidthNameSaveTextField(Graphics2D g2d) {
        g2d.setFont(nameSaveFont);
        setMaxSizeFontContainer(g2d, getWidth(), (getHeight() - getWidth() -  SEPARATOR) / 2.0F, nameSaveTextField.getText());
        reFactorFont(g2d, 0.8F);
        newSize.setSize((int) getStringBounds(g2d, nameSaveTextField.getText()).getWidth() + SEPARATOR, getMetric(g2d).getHeight());
        if (!redactedKey.getNonRedactedSize().equals(newSize)) {
            redactedKey.setNonRedactedSize((Dimension) newSize.clone());
            nameSaveTextField.setFont(g2d.getFont());
        }
    }

    private void paintText(Graphics2D g2d) {
        g2d.setColor(new Color(94, 92, 92));
        g2d.setFont(modFont);
        setMaxSizeFontContainer(g2d, getWidth(), (getHeight() - getWidth() -  SEPARATOR) / 2.0F, mod);
        reFactorFont(g2d, 0.5F);
        g2d.drawString(mod,
                0,
                getWidth() + getMetric(g2d).getHeight() + getMetric(g2d).getHeight() + SEPARATOR
        );
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = createGraphics2D(g);
        paintText(g2d);
        setWidthNameSaveTextField(g2d);
    }
}