package custom.UI.cloud.panel;

import custom.UI.*;
import sudoku.ShowerSudoku;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static arithmetic.Graphics.*;

public class SavePanel extends JPanel implements CloudPanel {
    private enum Proportion {
        NAME_SAVE(2), BUTTONS(2), DESCRIPTION_SAVE(5);
        int val;

        Proportion(int val) {
            this.val = val;
        }

        public static int getSum() {
            int sum = 0;
            for (Proportion p : Proportion.values()) {
                sum += p.val;
            }
            return sum;
        }
    }

    private JButton loadButton, deleteButton, redactButton;
    private JTextField nameSave;
    private JTextArea descriptionSave;
    private final ShowerSudoku showerSudoku;
    private boolean disable, redacted;
    private Font buttonsFont, nameSaveFont, descriptionSaveFont;
    private RedactedKeyListenerTextComponent redactedKeyDescriptionSave, redactedKeyNameSave;
    private int separatorButton = 5, separator = 5;
    private double opacity;

    public SavePanel(ShowerSudoku showerSudoku, String nameSave, String descriptionSave) {
        setButtonsFont(new Font("Arial", Font.PLAIN, 1));
        setNameSaveFont(buttonsFont);
        setDescriptionSaveFont(buttonsFont);
        this.showerSudoku = showerSudoku;
        add(showerSudoku);
        createDescriptionSave(descriptionSave);
        createNameSave(nameSave);
        createLoadButtons();
        createDeleteButtons();
        createRedactButtons();
        createDefaultSettingButtons(loadButton, deleteButton, redactButton);
        setLayout(null);
        setRedacted(false);
        setOpaque(false);
    }

    public ShowerSudoku getShowerSudoku() {
        return showerSudoku;
    }

    public void setButtonsFont(Font buttonsFont) {
        this.buttonsFont = buttonsFont.deriveFont(1.0F);
    }

    public void setNameSaveFont(Font nameSaveFont) {
        this.nameSaveFont = nameSaveFont.deriveFont(1.0F);
    }

    public void setDescriptionSaveFont(Font descriptionSaveFont) {
        this.descriptionSaveFont = descriptionSaveFont.deriveFont(1.0F);
    }

    public void setSeparator(int separator) {
        this.separator = separator;
    }

    public void setSeparatorButton(int separatorButton) {
        this.separatorButton = separatorButton;
    }

    public void setNameSave(String nameSave) {
        this.nameSave.setText(nameSave);
    }

    public String getNameSave() {
        return nameSave.getText();
    }

    public void setDescriptionSave(String descriptionSave) {
        this.descriptionSave.setText(descriptionSave);
    }

    public boolean isRedacted() {
        return redacted;
    }

    public JButton getRedactButton() {
        return redactButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
        nameSave.addKeyListener(redacted ? redactedKeyNameSave : null);
        nameSave.addMouseListener(redacted ? redactedKeyNameSave : null);
        nameSave.setCursor(new Cursor(redacted ? Cursor.TEXT_CURSOR : Cursor.DEFAULT_CURSOR));

        descriptionSave.addKeyListener(redacted ? redactedKeyDescriptionSave : null);
        descriptionSave.addMouseListener(redacted ? redactedKeyDescriptionSave : null);
        descriptionSave.setCursor(new Cursor(redacted ? Cursor.TEXT_CURSOR : Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    @Override
    public double getOpacity() {
        return opacity;
    }

    @Override
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @Override
    public boolean isDisable() {
        return disable;
    }

    private void createDefaultSettingButtons(JButton... buttons) {
        for (JButton button : buttons) {
            button.setUI(new MinimalisticButtonUI());
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(null);
            button.setOpaque(false);
            button.setForeground(Color.white);
            add(button);
        }

    }

    private void createLoadButtons() {
        loadButton = new JButton();
        loadButton.setBackground(new Color(0x4BB34B));
        loadButton.setText("Загрузить");
    }

    private void createDeleteButtons() {
        deleteButton = new JButton();
        deleteButton.setBackground(new Color(0xFF3347));
        deleteButton.setText("Удалить");
    }

    private void createRedactButtons() {
        redactButton = new JButton();
        redactButton.setBackground(new Color(0x5181B8));
        redactButton.setText("Изменить");
    }

    private void createDescriptionSave(String descriptionSave) {
        final int LIMIT_SYMBOL = 250;
        this.descriptionSave = new JTextArea(new JTextComponentLimit(LIMIT_SYMBOL), descriptionSave, LIMIT_SYMBOL / 10, LIMIT_SYMBOL);
        this.descriptionSave.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        this.descriptionSave.setUI(new MinimalisticTextArea("Напишите описание сохранения"));
        this.descriptionSave.setLineWrap(true);
        this.descriptionSave.setForeground(new Color(0, 0, 0));
        this.descriptionSave.setSelectionColor(new Color(28, 70, 191, 154));
        this.descriptionSave.setSelectedTextColor(new Color(0, 0, 0));
        add(this.descriptionSave);

        redactedKeyDescriptionSave = new RedactedKeyListenerTextComponent(this.descriptionSave, LIMIT_SYMBOL);
        redactedKeyDescriptionSave.translateDefaultStateRedactedTextComponent(false);
    }

    private void createNameSave(String nameSave) {
        final int LIMIT_SYMBOL = 20;
        this.nameSave = new JTextField(new JTextComponentLimit(LIMIT_SYMBOL), nameSave, LIMIT_SYMBOL);
        this.nameSave.setUI(new MinimalisticTextFieldUI("Напишите имя сохранения"));
        this.nameSave.setSelectionColor(new Color(28, 70, 191, 154));
        this.nameSave.setCaretColor(Color.BLACK);
        this.nameSave.setSelectedTextColor(Color.BLACK);
        this.nameSave.setForeground(Color.BLACK);
        this.nameSave.setHorizontalAlignment(JTextField.CENTER);

        add(this.nameSave);

        redactedKeyNameSave = new RedactedKeyListenerTextComponent(this.nameSave, LIMIT_SYMBOL);
        redactedKeyNameSave.translateDefaultStateRedactedTextComponent(false);
    }

    private int calculateHeight() {
        return getHeight() - getWidth() - Proportion.values().length * separator;
    }

    private int calculateProportion(Proportion p) {
        return (calculateHeight() / Proportion.getSum()) * p.val;
    }

    private void setBoundsButtons(int y, int height, JButton... buttons) {
        int width = (getWidth() - separatorButton * (buttons.length - 1)) / buttons.length;
        for (int index = 0; index < buttons.length; ++index) {
            buttons[index].setBounds(index * width + index * separatorButton, y, width, height);
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        redactedKeyNameSave.setRedactedSize(width, calculateProportion(Proportion.NAME_SAVE));
        redactedKeyNameSave.setNonRedactedSize(redactedKeyNameSave.getRedactedSize());
        nameSave.setLocation(0, 0);

        redactedKeyDescriptionSave.setRedactedSize(width, calculateProportion(Proportion.DESCRIPTION_SAVE));
        redactedKeyDescriptionSave.setNonRedactedSize(redactedKeyDescriptionSave.getRedactedSize());
        descriptionSave.setLocation(0, calculateProportion(Proportion.NAME_SAVE) + separator);

        int qElementAboveSudoku = 2;
        showerSudoku.setBounds(0, calculateProportion(Proportion.NAME_SAVE) + calculateProportion(Proportion.DESCRIPTION_SAVE) + qElementAboveSudoku * separator, width, width);

        setBoundsButtons(getHeight() - calculateProportion(Proportion.BUTTONS), calculateProportion(Proportion.BUTTONS), loadButton, redactButton, deleteButton);

        repaint();
    }

    private void setSizeFontButtons(JButton... buttons) {
        for (JButton button : buttons) {
            button.setFont(buttonsFont);
        }
    }

    private void setSizeFont(Graphics2D g2d) {
        setSizeFontButtons(loadButton, redactButton, deleteButton);
        g2d.setFont(nameSaveFont);
        setMaxSizeFontContainer(g2d, nameSave.getWidth(), nameSave.getHeight(), nameSave.getText());
        reFactorFont(g2d, 0.8F);
        if (!g2d.getFont().equals(nameSave.getFont())) {
            nameSave.setFont(g2d.getFont());
        }
        g2d.setFont(descriptionSaveFont);
        setMaxSizeFontContainer(g2d, descriptionSave.getWidth(), descriptionSave.getHeight(), "XXXXXXXXXXXXXXXXXXXXXXXXXX");
        reFactorFont(g2d, 0.5F);
        if (!g2d.getFont().equals(descriptionSave.getFont())) {
            descriptionSave.setFont(g2d.getFont());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = createGraphics2D(g);
        setSizeFont(g2d);

    }
}
