package custom.UI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

import static arithmetic.Graphics.*;

public class MinimalisticButtonUI extends BasicButtonUI {
    private final static MinimalisticButtonUI MINIMALISTIC_BUTTON_UI = new MinimalisticButtonUI();
    int aWeight = -1, aHeight = -1;

    public static ComponentUI createUI(JComponent c) {
        return MINIMALISTIC_BUTTON_UI;
    }

    @Override
    public void installUI(JComponent component) {
        super.installUI(component);
    }

    @Override
    public void uninstallUI(JComponent component) {
        super.uninstallUI(component);
    }

    private void paintBackground(Graphics2D g2d, AbstractButton button) {
        g2d.setColor(button.getModel().isRollover() ? button.getBackground().brighter() : button.getBackground());
        g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), aWeight != -1 ? aWeight : Math.min(button.getWidth(), button.getHeight()) / 5, Math.min(button.getWidth(), button.getHeight()) / 5);
    }

    private void paintText(Graphics2D g2d, AbstractButton button, float moveY) {
        g2d.setColor(button.getForeground());
        setMaxSizeFontContainer(g2d, button.getWidth(), button.getHeight(), "xxxxxxxxxx");
        reFactorFont(g2d, 0.7F);
        g2d.drawString(button.getText(),
                calculateStringMiddleXInContainer(0, g2d, button.getText(), button.getWidth()),
                calculateStringMiddleYInContainer(0, g2d, button.getHeight()) + moveY);
    }

    @Override
    public void paint(Graphics g, JComponent component) {
        Graphics2D g2d = createGraphics2D(g);
        AbstractButton button = (AbstractButton) component;

        paintBackground(g2d, button);

        paintText(g2d, button, button.getModel().isPressed() ? 1 : 0);
    }
}
