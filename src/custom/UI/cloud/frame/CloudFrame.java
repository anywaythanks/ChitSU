package custom.UI.cloud.frame;

import java.awt.*;

public interface CloudFrame {
    void setOpacity(double opacity);

    double getOpacity();

    void setBounds(int x, int y, int width, int height);

    void setBounds(Rectangle r);

    Rectangle getBounds();

    void setSize(Dimension d);

    void setSize(int width, int height);

    void setLocation(int x, int y);

    void setLocation(Point p);

    Rectangle getPanelZone();
}
