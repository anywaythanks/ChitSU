package arithmetic;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Graphics {

    public static Graphics2D createGraphics2D(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2d;
    }

    public static Rectangle2D getVectorBounds(Graphics2D g2d, String text) {
        return g2d.getFont().createGlyphVector(g2d.getFontRenderContext(), text).getVisualBounds();
    }

    public static Rectangle2D getStringBounds(Graphics2D g2d, String text) {
        return getMetric(g2d).getStringBounds(text, g2d);
    }

    public static FontMetrics getMetric(Graphics2D g2d) {
        return g2d.getFontMetrics(g2d.getFont());
    }

    public static float calculateStringMiddleXInContainer(double zeroX, Graphics2D g2d, String text, double width) {
        return (float) (zeroX + (width - getStringBounds(g2d, text).getWidth()) / 2);
    }

    public static float calculateStringMiddleYInContainer(double zeroY, Graphics2D g2d, double height) {
        return (float) ((zeroY + (height - getMetric(g2d).getHeight()) / 2) + getMetric(g2d).getAscent());
    }

    public static void setMaxSizeFontContainer(Graphics2D g2D, float weight, float height, String text) {
        Rectangle2D maxRect = new Rectangle2D.Float(0, 0, weight, height);

        // starting with a very big font due to a high res image
        float size = 80f * 4f;
        // starting with a diff half the size of the font
        float diff = size / 2;
        Font bufferFont = g2D.getFont().deriveFont(size);
        FontMetrics fontMetrics;
        Rectangle2D stringBounds;
        while (Math.abs(diff) > 0.5F) {
            bufferFont = bufferFont.deriveFont(size);
            g2D.setFont(bufferFont);
            fontMetrics = g2D.getFontMetrics(bufferFont);
            stringBounds = fontMetrics.getStringBounds(text, g2D);
            stringBounds = new Rectangle2D.Float(0f, 0f, (float) (stringBounds.getX() + stringBounds.getWidth()), (float) (stringBounds.getHeight()));

            if (maxRect.contains(stringBounds)) {
                if (0 < diff) {
                    diff = Math.abs(diff);
                } else if (diff < 0) {
                    diff = Math.abs(diff) / 2;
                }
            } else {
                if (0 < diff) {
                    diff = -Math.abs(diff) / 2;
                } else if (diff < 0) {
                    if (size <= Math.abs(diff)) {
                        diff = -Math.abs(diff) / 2;
                    } else {
                        diff = -Math.abs(diff);
                    }
                }
            }
            size += diff;
        }
    }

    public static void reFactorFont(Graphics2D g2D, float factor) {
        g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize2D() * factor));
    }

    public static Color setOpacityColor(Color col, double opacity) {
        return new Color(col.getRed(), col.getGreen(), col.getBlue(), (int) (col.getAlpha() * opacity));
    }
}
