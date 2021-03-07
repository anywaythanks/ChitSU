package custom.UI.cloud;

import custom.UI.cloud.frame.CloudFrame;
import custom.UI.cloud.panel.CloudPanel;

import javax.swing.*;
import java.awt.*;

public class Cloud<Panel extends CloudPanel, Frame extends CloudFrame> extends JPanel {
    enum StatusTimerOpacity {
        DEACTIVATE, ACTIVATE, STOP
    }

    Panel panel;
    Frame frame;
    private int delayAnimation = 1;
    private double opacity = 1, opacityDelay = 0.1;
    private StatusTimerOpacity statusTimerOpacity = StatusTimerOpacity.STOP;

    private final Timer opacityTimer = new Timer(delayAnimation, actionEvent -> {
        switch (statusTimerOpacity) {
            case STOP:
                stopOpacityTimer();
                break;
            case ACTIVATE:
                if (getOpacity() == 1) {
                    setDisable(false);
                    stopOpacityTimer();
                } else {
                    System.out.println(getOpacity());
                    setOpacity(Math.min(1, getOpacity() + opacityDelay));
                }
                break;
            case DEACTIVATE:
                if (getOpacity() == 0) {
                    setDisable(true);
                    stopOpacityTimer();
                } else {
                    setOpacity(Math.max(0, getOpacity() - opacityDelay));
                }
                break;
        }
    });

    public Cloud(Panel panel, Frame frame) throws IllegalArgumentException {
        setPanel(panel);
        setFrame(frame);
        setLayout(null);
        setOpaque(false);
    }

    public Panel getPanel() {
        return panel;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setDelayAnimation(int delayAnimation) {
        this.delayAnimation = delayAnimation;
        opacityTimer.setDelay(delayAnimation);
    }

    public int getDelayAnimation() {
        return delayAnimation;
    }

    /**
     * @throws IllegalArgumentException {@link Cloud#panel} должна быть наследником {@link Component}.
     */
    public void setPanel(Panel panel) throws IllegalArgumentException {
        if (panel instanceof Component) {
            if (this.panel != null) {
                panel.setBounds(this.panel.getBounds());
            }
            this.panel = panel;
            add((Component) panel);
        } else {
            throw new IllegalArgumentException(panel.getClass() + " it should be extends " + Component.class);
        }
    }

    /**
     * @throws IllegalArgumentException {@link Cloud#frame} должна быть наследником {@link Component}.
     */
    public void setFrame(Frame frame) throws IllegalArgumentException {
        if (frame instanceof Component) {
            if (this.frame != null) {
                frame.setBounds(this.frame.getBounds());
            }
            this.frame = frame;
            add((Component) frame);
        } else {
            throw new IllegalArgumentException(frame.getClass() + " it should be extends " + Component.class);
        }
    }

    public void setDisable(boolean b) {
        panel.setDisable(b);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        frame.setBounds(0, 0, width, height);
        panel.setBounds(frame.getPanelZone());
    }

    public void setOpacityDelay(double opacityDelay) {
        this.opacityDelay = opacityDelay;
    }

    public void setAnimationVisible(boolean b) {
        statusTimerOpacity = b ? StatusTimerOpacity.ACTIVATE : StatusTimerOpacity.DEACTIVATE;
        opacityTimer.start();
    }

    public double getOpacityDelay() {
        return opacityDelay;
    }


    private void setOpacity(double opacity) {
        this.opacity = opacity;
        panel.setOpacity(opacity);
        frame.setOpacity(opacity);
    }

    private double getOpacity() {
        return opacity;
    }

    private void stopOpacityTimer() {
        opacityTimer.stop();
    }
}
