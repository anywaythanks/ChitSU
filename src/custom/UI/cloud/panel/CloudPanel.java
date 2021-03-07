package custom.UI.cloud.panel;

import java.awt.*;

public interface CloudPanel {
    void setDisable(boolean disable);

    boolean isDisable();

    void setOpacity(double opacity);

    double getOpacity();

    void setBounds(int x, int y, int width, int height);

    void setBounds(Rectangle r);

    void setSize(Dimension d);

    Rectangle getBounds();

    void setSize(int width, int height);

    void setLocation(int x, int y);

    void setLocation(Point p);
}
