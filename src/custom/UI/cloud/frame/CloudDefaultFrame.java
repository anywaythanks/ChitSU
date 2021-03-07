package custom.UI.cloud.frame;

import custom.container.BinaryContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

import static arithmetic.Graphics.createGraphics2D;
import static arithmetic.Graphics.setOpacityColor;

public class CloudDefaultFrame extends JPanel implements CloudFrame {
    public enum Side {
        LEFT, UP, RIGHT, DOWN, NULL
    }

    private static class Triangle {
        double length, downOrRight, upOrLeft;
        Point2D coordinate;

        public Triangle() {
            this(0, 0, 0, new Point(0, 0));
        }

        public Triangle(double length, double downOrRight, double upOrLeft, Point2D coordinate) {
            this.length = length;
            this.downOrRight = downOrRight;
            this.upOrLeft = upOrLeft;
            this.coordinate = coordinate;
        }

        @Override
        public String toString() {
            return "Triangle{" +
                    "length=" + length +
                    ", downOrRight=" + downOrRight +
                    ", upOrLeft=" + upOrLeft +
                    ", coordinate=" + coordinate +
                    '}';
        }
    }

    private final BinaryContainer<Dimension2D> arc = new BinaryContainer<>(new Dimension(), new Dimension());
    private final BinaryContainer<Dimension2D> separator = new BinaryContainer<>(new Dimension(), new Dimension());
    private final BinaryContainer<Triangle> triangle = new BinaryContainer<>(new Triangle(), new Triangle());
    private final BinaryContainer<Color> colorFrame;
    private Color userBackground;
    private double opacity = 1;
    private float strokeLine = 1;
    private Side side = Side.LEFT;

    public CloudDefaultFrame() {
        colorFrame = new BinaryContainer<>(Color.BLACK, Color.BLACK);
        setOpaque(false);
    }

    public void setSide(Side side) {
        if (side != null) {
            this.side = side;
        }
        recalculateAll();
        repaint();
    }

    public void setTriangleLength(double length) {
        triangle.getUserT().length = length;
        recalculateTriangle();
    }

    public void setTriangleDownOrRight(double downOrRight) {
        triangle.getUserT().downOrRight = downOrRight;
        recalculateTriangle();
    }

    public void setTriangleUpOrLeft(double upOrLeft) {
        triangle.getUserT().upOrLeft = upOrLeft;
        recalculateTriangle();
    }

    public void setTriangleCoordinate(Point2D coordinate) {
        triangle.getUserT().coordinate = coordinate;
        recalculateTriangle();
    }

    public void setSeparator(double width, double height) {
        separator.getUserT().setSize(width, height);
        recalculateSeparator();
        repaint();
    }

    public void setStrokeLine(float strokeLine) {
        this.strokeLine = strokeLine;
        recalculateAll();
        repaint();
    }

    public void setColorFrame(Color colorFrame) {
        this.colorFrame.setUserT(colorFrame);
        this.colorFrame.setAbsoluteT(setOpacityColor(colorFrame, getOpacity()));
    }

    public void setSeparator(Dimension2D separator) {
        setSeparator(separator.getWidth(), separator.getHeight());
    }

    public void setArc(double arcWidth, double arcHeight) {
        arc.getUserT().setSize(arcWidth, arcHeight);
        recalculateArc();
        repaint();
    }

    public void setArc(Dimension2D arc) {
        setArc(arc.getWidth(), arc.getHeight());
    }

    @Override
    public void setOpacity(double opacity) {
        this.opacity = opacity;
        setBackground(userBackground);
        setColorFrame(colorFrame.getUserT());
    }

    @Override
    public void setBackground(Color color) {
        userBackground = color;
        super.setBackground(setOpacityColor(color, getOpacity()));
    }

    @Override
    public double getOpacity() {
        return opacity;
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        recalculateAll();
    }

    public void setBounds(int x, int y, int width, int height, double arcWidth, double arcHeight) {
        setArc(arcWidth, arcHeight);
        setBounds(x, y, width, height);

    }

    @Override
    public Rectangle getPanelZone() {

        switch (side) {
            case LEFT:
                return new Rectangle((int) (triangle.getAbsoluteT().length + separator.getAbsoluteT().getWidth()),
                        (int) (separator.getAbsoluteT().getHeight()),
                        (int) (getWidth() - (triangle.getAbsoluteT().length + separator.getAbsoluteT().getWidth() * 2)),
                        (int) (getHeight() - separator.getAbsoluteT().getHeight() * 2));
            case RIGHT:
                return new Rectangle((int) (separator.getAbsoluteT().getWidth()),
                        (int) (separator.getAbsoluteT().getHeight()),
                        (int) (getWidth() - (triangle.getAbsoluteT().length + separator.getAbsoluteT().getWidth() * 2)),
                        (int) (getHeight() - separator.getAbsoluteT().getHeight() * 2));
            case UP:
                return new Rectangle((int) (separator.getAbsoluteT().getWidth()),
                        (int) (triangle.getAbsoluteT().length + separator.getAbsoluteT().getHeight()),
                        (int) (getWidth() - (separator.getAbsoluteT().getWidth() * 2)),
                        (int) (getHeight() - triangle.getAbsoluteT().length - separator.getAbsoluteT().getHeight() * 2));
            case DOWN:
                return new Rectangle((int) (separator.getAbsoluteT().getWidth()),
                        (int) (separator.getAbsoluteT().getHeight()),
                        (int) (getWidth() - (separator.getAbsoluteT().getWidth() * 2)),
                        (int) (getHeight() - triangle.getAbsoluteT().length - separator.getAbsoluteT().getHeight() * 2));
            default:
                return new Rectangle((int) (separator.getAbsoluteT().getWidth()),
                        (int) (separator.getAbsoluteT().getHeight()),
                        (int) (getWidth() - (separator.getAbsoluteT().getWidth() * 2)),
                        (int) (getHeight() - separator.getAbsoluteT().getHeight() * 2));
        }

    }


    private void recalculateArc() {
        arc.getAbsoluteT().setSize(
                Math.min(getWidth() - strokeLine * 2, Math.max(arc.getUserT().getWidth(), strokeLine)),
                Math.min(getHeight() - strokeLine * 2, Math.max(arc.getUserT().getHeight(), strokeLine))
        );
    }

    private void recalculateSeparator() {
        separator.getAbsoluteT().setSize(
                Math.min(getWidth() / 2.0, Math.max(separator.getUserT().getWidth(), strokeLine)),
                Math.min(getHeight() / 2.0, Math.max(separator.getUserT().getHeight(), strokeLine))
        );
    }

    private void recalculateTriangle() {
        switch (side) {
            case DOWN:
            case UP:
                triangle.getAbsoluteT().coordinate.setLocation(
                        Math.max(arc.getAbsoluteT().getWidth() - strokeLine, Math.min(getWidth() - arc.getAbsoluteT().getWidth() / 2, triangle.getUserT().coordinate.getX())),
                        triangle.getUserT().coordinate.getY()
                );
                triangle.getAbsoluteT().upOrLeft = Math.min(triangle.getAbsoluteT().coordinate.getX(), Math.max(0, triangle.getUserT().upOrLeft));
                triangle.getAbsoluteT().downOrRight = Math.min(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine - triangle.getAbsoluteT().coordinate.getX(), Math.max(0, triangle.getUserT().downOrRight));
                triangle.getAbsoluteT().length = Math.min(getHeight() - arc.getAbsoluteT().getHeight() - strokeLine * 2, Math.max(0, triangle.getUserT().length));
                break;
            case RIGHT:
            case LEFT:
                triangle.getAbsoluteT().coordinate.setLocation(
                        triangle.getUserT().coordinate.getX(),
                        Math.max(arc.getAbsoluteT().getHeight() / 2 + strokeLine * 2, Math.min(getHeight() - arc.getAbsoluteT().getHeight() / 2, triangle.getUserT().coordinate.getY()))
                );
                triangle.getAbsoluteT().upOrLeft = Math.min(triangle.getAbsoluteT().coordinate.getY(), Math.max(0, triangle.getUserT().upOrLeft));
                triangle.getAbsoluteT().downOrRight = Math.min(getHeight() - arc.getAbsoluteT().getHeight() - strokeLine - triangle.getAbsoluteT().coordinate.getY(), Math.max(0, triangle.getUserT().downOrRight));
                triangle.getAbsoluteT().length = Math.min(getWidth() - arc.getAbsoluteT().getWidth() - strokeLine * 2, Math.max(0, triangle.getUserT().length));
                break;
        }
    }

    private void recalculateAll() {
        recalculateArc();
        recalculateSeparator();
        recalculateTriangle();
    }

    private void drawBackground(Graphics2D g2D) {
        g2D.setColor(getBackground());
        switch (side) {
            case LEFT:
                g2D.fill(new RoundRectangle2D.Double(triangle.getAbsoluteT().length + strokeLine, strokeLine, getWidth() - triangle.getAbsoluteT().length - strokeLine * 2, getHeight() - strokeLine * 2, arc.getAbsoluteT().getWidth() - strokeLine * 1.5, arc.getAbsoluteT().getHeight() - strokeLine * 1.5));
                g2D.fillPolygon(new Polygon(
                        new int[]{(int) (strokeLine), (int) (triangle.getAbsoluteT().length + strokeLine), (int) (triangle.getAbsoluteT().length + strokeLine)},
                        new int[]{(int) triangle.getAbsoluteT().coordinate.getY(), (int) (triangle.getAbsoluteT().coordinate.getY() - triangle.getAbsoluteT().upOrLeft + strokeLine / 4), (int) (triangle.getAbsoluteT().coordinate.getY() + triangle.getAbsoluteT().downOrRight - strokeLine / 4)},
                        3));
                break;
            case DOWN:
                g2D.fill(new RoundRectangle2D.Double(strokeLine, strokeLine, getWidth() - strokeLine * 2, getHeight() - triangle.getAbsoluteT().length - strokeLine * 2, arc.getAbsoluteT().getWidth() - strokeLine * 1.5, arc.getAbsoluteT().getHeight() - strokeLine * 1.5));
                g2D.fillPolygon(new Polygon(
                        new int[]{(int) (triangle.getAbsoluteT().coordinate.getX() + strokeLine / 4), (int) (triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft), (int) (triangle.getAbsoluteT().coordinate.getX() - strokeLine / 4 + triangle.getAbsoluteT().downOrRight + triangle.getAbsoluteT().upOrLeft)},
                        new int[]{(int) (getHeight() - strokeLine - triangle.getAbsoluteT().length), (int) (getHeight() - strokeLine), (int) (getHeight() - triangle.getAbsoluteT().length - strokeLine)},
                        3));
                break;
            case RIGHT:
                g2D.fill(new RoundRectangle2D.Double(strokeLine, strokeLine, getWidth() - triangle.getAbsoluteT().length - strokeLine * 2, getHeight() - strokeLine * 2, arc.getAbsoluteT().getWidth() - strokeLine * 1.5, arc.getAbsoluteT().getHeight() - strokeLine * 1.5));
                g2D.fillPolygon(new Polygon(
                        new int[]{(int) (getWidth() - strokeLine), (int) (getWidth() - triangle.getAbsoluteT().length - strokeLine), (int) (getWidth() - triangle.getAbsoluteT().length - strokeLine)},
                        new int[]{(int) triangle.getAbsoluteT().coordinate.getY(), (int) (triangle.getAbsoluteT().coordinate.getY() - triangle.getAbsoluteT().upOrLeft + strokeLine / 4), (int) (triangle.getAbsoluteT().coordinate.getY() + triangle.getAbsoluteT().downOrRight - strokeLine / 4)},
                        3));
                break;
            case UP:
                g2D.fill(new RoundRectangle2D.Double(strokeLine, triangle.getAbsoluteT().length + strokeLine, getWidth() - strokeLine * 2, getHeight() - strokeLine * 2 - triangle.getAbsoluteT().length, arc.getAbsoluteT().getWidth() - strokeLine * 1.5, arc.getAbsoluteT().getHeight() - strokeLine * 1.5));
                g2D.fillPolygon(new Polygon(
                        new int[]{(int) (triangle.getAbsoluteT().coordinate.getX() + strokeLine / 4), (int) (triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft), (int) (triangle.getAbsoluteT().coordinate.getX() - strokeLine / 4 + triangle.getAbsoluteT().downOrRight + triangle.getAbsoluteT().upOrLeft)},
                        new int[]{(int) (triangle.getAbsoluteT().length + strokeLine), (int) (strokeLine), (int) (triangle.getAbsoluteT().length + strokeLine)},
                        3));
                break;
            case NULL:
                g2D.fill(new RoundRectangle2D.Double(strokeLine, strokeLine, getWidth() - strokeLine * 2, getHeight() - strokeLine * 2, arc.getAbsoluteT().getWidth() - strokeLine * 1.5, arc.getAbsoluteT().getHeight() - strokeLine * 1.5));
                break;
        }

    }

    private void drawArcFrame(Graphics2D g2D) {
        switch (side) {
            case LEFT:
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 0, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 90, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 180, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 270, 90, Arc2D.OPEN));
                break;
            case DOWN:
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 0, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 90, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2 - triangle.getAbsoluteT().length, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 180, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2 - triangle.getAbsoluteT().length, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 270, 90, Arc2D.OPEN));
                break;
            case RIGHT:
                g2D.draw(new Arc2D.Double(getWidth() - triangle.getAbsoluteT().length - arc.getAbsoluteT().getWidth() + strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 0, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 90, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 180, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(getWidth() - triangle.getAbsoluteT().length - arc.getAbsoluteT().getWidth() + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 270, 90, Arc2D.OPEN));
                break;
            case UP:
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, triangle.getAbsoluteT().length + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 0, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, strokeLine / 2 + triangle.getAbsoluteT().length, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 90, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 180, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 270, 90, Arc2D.OPEN));
                break;
            case NULL:
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 0, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 90, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 180, 90, Arc2D.OPEN));
                g2D.draw(new Arc2D.Double(getWidth() - arc.getAbsoluteT().getWidth() + strokeLine / 2, getHeight() - arc.getAbsoluteT().getHeight() + strokeLine / 2, arc.getAbsoluteT().getWidth() - strokeLine, arc.getAbsoluteT().getHeight() - strokeLine, 270, 90, Arc2D.OPEN));
                break;
        }
    }

    private void drawWallFrame(Graphics2D g2D) {
        switch (side) {
            case LEFT:
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() - triangle.getAbsoluteT().upOrLeft, strokeLine / 2, triangle.getAbsoluteT().coordinate.getY()));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() + triangle.getAbsoluteT().downOrRight, strokeLine / 2, triangle.getAbsoluteT().coordinate.getY()));


                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() - strokeLine - triangle.getAbsoluteT().upOrLeft));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() + strokeLine + triangle.getAbsoluteT().downOrRight, triangle.getAbsoluteT().length + strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));

                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + arc.getAbsoluteT().getWidth() / 2 + strokeLine, getHeight() - strokeLine / 2, getWidth() - arc.getAbsoluteT().getWidth() / 2 - strokeLine, getHeight() - strokeLine / 2));
                g2D.draw(new Line2D.Double(getWidth() - strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, getWidth() - strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().length + arc.getAbsoluteT().getWidth() / 2 + strokeLine, strokeLine / 2, getWidth() - arc.getAbsoluteT().getWidth() / 2 - strokeLine, strokeLine / 2));
                break;
            case DOWN:
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX(), getHeight() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft, getHeight() - strokeLine / 2));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft + triangle.getAbsoluteT().downOrRight, getHeight() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft, getHeight() - strokeLine / 2));


                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, getHeight() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() - strokeLine, getHeight() - triangle.getAbsoluteT().length - strokeLine / 2));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX() + strokeLine + triangle.getAbsoluteT().upOrLeft + triangle.getAbsoluteT().downOrRight, getHeight() - triangle.getAbsoluteT().length - strokeLine / 2, getWidth() - strokeLine - arc.getAbsoluteT().getWidth() / 2, getHeight() - triangle.getAbsoluteT().length - strokeLine / 2));

                g2D.draw(new Line2D.Double(strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, strokeLine / 2, getHeight() - strokeLine - triangle.getAbsoluteT().length - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(getWidth() - strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, getWidth() - strokeLine / 2, getHeight() - strokeLine - triangle.getAbsoluteT().length - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, strokeLine / 2, getWidth() - strokeLine - arc.getAbsoluteT().getWidth() / 2, strokeLine / 2));
                break;
            case RIGHT:
                g2D.draw(new Line2D.Double(getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() - triangle.getAbsoluteT().upOrLeft, getWidth() - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY()));
                g2D.draw(new Line2D.Double(getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() + triangle.getAbsoluteT().downOrRight, getWidth() - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY()));


                g2D.draw(new Line2D.Double(getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() - triangle.getAbsoluteT().upOrLeft - strokeLine));
                g2D.draw(new Line2D.Double(getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, triangle.getAbsoluteT().coordinate.getY() + triangle.getAbsoluteT().downOrRight + strokeLine, getWidth() - triangle.getAbsoluteT().length - strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));

                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, getHeight() - strokeLine / 2, getWidth() - triangle.getAbsoluteT().length - strokeLine - arc.getAbsoluteT().getWidth() / 2, getHeight() - strokeLine / 2));
                g2D.draw(new Line2D.Double(strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, strokeLine / 2, getWidth() - strokeLine - triangle.getAbsoluteT().length - arc.getAbsoluteT().getWidth() / 2, strokeLine / 2));
                break;
            case UP:
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX(), triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft, strokeLine / 2));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft + triangle.getAbsoluteT().downOrRight, triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft, strokeLine / 2));


                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, triangle.getAbsoluteT().length + strokeLine / 2, triangle.getAbsoluteT().coordinate.getX() - strokeLine, triangle.getAbsoluteT().length + strokeLine / 2));
                g2D.draw(new Line2D.Double(triangle.getAbsoluteT().coordinate.getX() + triangle.getAbsoluteT().upOrLeft + triangle.getAbsoluteT().downOrRight + strokeLine, triangle.getAbsoluteT().length + strokeLine / 2, getWidth() - strokeLine - arc.getAbsoluteT().getWidth() / 2, triangle.getAbsoluteT().length + strokeLine / 2));

                g2D.draw(new Line2D.Double(strokeLine / 2, triangle.getAbsoluteT().length + strokeLine + arc.getAbsoluteT().getHeight() / 2, strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(getWidth() - strokeLine / 2, triangle.getAbsoluteT().length + strokeLine + arc.getAbsoluteT().getHeight() / 2, getWidth() - strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, getHeight() - strokeLine / 2, getWidth() - arc.getAbsoluteT().getWidth() / 2 - strokeLine, getHeight() - strokeLine / 2));
                break;
            case NULL:
                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, strokeLine / 2, getWidth() - arc.getAbsoluteT().getWidth() / 2 - strokeLine, strokeLine / 2));
                g2D.draw(new Line2D.Double(strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(getWidth() - strokeLine / 2, arc.getAbsoluteT().getHeight() / 2 + strokeLine, getWidth() - strokeLine / 2, getHeight() - strokeLine - arc.getAbsoluteT().getHeight() / 2));
                g2D.draw(new Line2D.Double(arc.getAbsoluteT().getWidth() / 2 + strokeLine, getHeight() - strokeLine / 2, getWidth() - arc.getAbsoluteT().getWidth() / 2 - strokeLine, getHeight() - strokeLine / 2));
                break;
        }
    }

    private void drawFrame(Graphics2D g2D) {
        g2D.setColor(colorFrame.getAbsoluteT());

        g2D.setStroke(new BasicStroke(strokeLine, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        drawArcFrame(g2D);
        drawWallFrame(g2D);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = createGraphics2D(g);
        super.paintComponent(g);
        drawBackground(g2D);
        drawFrame(g2D);
    }
}
