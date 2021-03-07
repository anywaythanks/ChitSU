package custom.UI;

import sudoku.event.NotValidText;

import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static arithmetic.Equals.isValidName;

public class RedactedKeyListenerTextComponent implements MouseListener, KeyListener {
    private final JTextComponent redactedTextComponent;
    private final int limitSymbol;
    private String oldText = "";
    private Dimension redactedSize, nonRedactedSize;
    private final Border oldBorder;
    private boolean isNowRedacted = false;
    private NotValidText validText = null;

    public RedactedKeyListenerTextComponent(JTextComponent redactedTextComponent, int limitSymbol) {
        this.redactedTextComponent = redactedTextComponent;
        oldBorder = redactedTextComponent.getBorder();
        this.limitSymbol = limitSymbol;
        redactedSize = new Dimension();
        nonRedactedSize = new Dimension();
    }

    public Dimension getNonRedactedSize() {
        return nonRedactedSize;
    }

    public void setRedactedSize(Dimension redactedSize) {
        this.redactedSize = redactedSize;
        resize();
    }

    public void setRedactedSize(int width, int height) {
        this.redactedSize.setSize(width, height);
        resize();

    }

    public Dimension getRedactedSize() {
        return redactedSize;
    }

    public void setNonRedactedSize(Dimension nonRedactedSize) {
        this.nonRedactedSize = nonRedactedSize;
        resize();
    }

    public void setNonRedactedSize(int width, int height) {
        this.nonRedactedSize.setSize(width, height);
        resize();
    }

    private void resize() {
        if (isNowRedacted && !redactedTextComponent.getSize().equals(redactedSize)) {
            redactedTextComponent.setSize(redactedSize);
        }
        if (!isNowRedacted && !redactedTextComponent.getSize().equals(nonRedactedSize)) {
            redactedTextComponent.setSize(nonRedactedSize);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    public void translateDefaultStateRedactedTextComponent(boolean redacted) {
        isNowRedacted = redacted;
        resize();
        redactedTextComponent.setSelectionStart(redactedTextComponent.getText().length());
        redactedTextComponent.setSelectionEnd(redactedTextComponent.getText().length());
        redactedTextComponent.setHighlighter(redacted ? new DefaultHighlighter() : null);
        redactedTextComponent.setEditable(redacted);
        redactedTextComponent.setBorder(redacted ? oldBorder : null);
        redactedTextComponent.setOpaque(redacted);
        redactedTextComponent.setSelectionStart(redactedTextComponent.getText().length());
        redactedTextComponent.getCaret().setVisible(redacted);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            translateDefaultStateRedactedTextComponent(false);
            if (!isValidName(redactedTextComponent.getText(), 0, limitSymbol)) {
                if (validText != null)
                    validText.notValidText(redactedTextComponent.getText());
                redactedTextComponent.setText(oldText);
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            translateDefaultStateRedactedTextComponent(false);
            redactedTextComponent.setText(oldText);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (!isNowRedacted && mouseEvent.getClickCount() >= 2) {
            translateDefaultStateRedactedTextComponent(true);
            oldText = redactedTextComponent.getText();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void addNotValidText(NotValidText validText) {
        this.validText = validText;
    }
}
