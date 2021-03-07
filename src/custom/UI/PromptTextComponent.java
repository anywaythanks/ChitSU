package custom.UI;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static arithmetic.Graphics.calculateStringMiddleYInContainer;

public class PromptTextComponent implements FocusListener {
    private PromptPaint promptPaint;
    private String prompt;
    private boolean hideOnFocus;
    private Color colorPrompt;

    public PromptTextComponent(String hint) {
        this(hint, false);
    }

    public PromptTextComponent(String prompt, boolean hideOnFocus) {
        this(prompt, hideOnFocus, null);
    }

    public PromptTextComponent(String hint, boolean hideOnFocus, Color colorPrompt) {
        this.prompt = hint;
        this.hideOnFocus = hideOnFocus;
        this.colorPrompt = colorPrompt;
    }

    public Color getColorPrompt() {
        return colorPrompt;
    }

    public void setColorPrompt(Color colorPrompt) {
        this.colorPrompt = colorPrompt;
        repaint();
    }

    public boolean isHideOnFocus() {
        return hideOnFocus;
    }

    public void setHideOnFocus(boolean hideOnFocus) {
        this.hideOnFocus = hideOnFocus;
        repaint();
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        repaint();
    }

    public void paint(Graphics2D g2d, JTextComponent component) {
        if (prompt != null && component.getText().length() == 0 && (!(hideOnFocus && component.hasFocus()))) {
            g2d.setColor(colorPrompt != null ? colorPrompt : new Color(87, 83, 83));
            g2d.drawString(prompt, 2.0F, calculateStringMiddleYInContainer(0, g2d, component.getHeight()));
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (hideOnFocus) {
            repaint();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (hideOnFocus) {
            repaint();
        }
    }

    public void addPromptPaint(PromptPaint promptPaint) {
        this.promptPaint = promptPaint;
    }

    private void repaint() {
        if (promptPaint != null) {
            promptPaint.repaint();
        }
    }
}
