package custom.UI;

import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static arithmetic.Graphics.*;

public class MinimalisticTextArea extends BasicTextAreaUI implements FocusListener {
    private final PromptTextComponent promptTextComponent;

    public MinimalisticTextArea(String hint) {
        promptTextComponent = new PromptTextComponent(hint);
        promptTextComponent.addPromptPaint(this::repaint);
    }

    public MinimalisticTextArea(String prompt, boolean hideOnFocus) {
        promptTextComponent = new PromptTextComponent(prompt, hideOnFocus);
        promptTextComponent.addPromptPaint(this::repaint);
    }

    public MinimalisticTextArea(String hint, boolean hideOnFocus, Color colorPrompt) {
        promptTextComponent = new PromptTextComponent(hint, hideOnFocus, colorPrompt);
        promptTextComponent.addPromptPaint(this::repaint);
    }

    public PromptTextComponent getPromptTextComponent() {
        return promptTextComponent;
    }

    private void repaint() {
        if (getComponent() != null) {
            getComponent().repaint();
        }
    }

    @Override
    protected void paintSafely(Graphics g) {
        Graphics2D g2d = createGraphics2D(g);
        JTextComponent component = getComponent();
        g2d.setFont(component.getFont());
        super.paintSafely(g2d);
        promptTextComponent.paint(g2d, component);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        promptTextComponent.focusGained(focusEvent);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        promptTextComponent.focusLost(focusEvent);
    }
}