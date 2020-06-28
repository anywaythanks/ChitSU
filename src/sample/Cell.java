package sample;

import java.awt.*;

public class Cell extends Point {
    public int val;

    public int location;

    public Cell(Point p, int val) {
        this(p.x, p.y, val, -1);
    }

    public Cell() {
        this(0, 0, -1, -1);
    }

    public Cell(Cell c) {
        this(c.x, c.y, c.val, -1);
    }

    public Cell(int x, int y, int val) {
        this(x, y, val, -1);
    }

    public Cell(int x, int y, int val, int location) {
        this.x = x;
        this.y = y;
        this.val = val;
        this.location = location;
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",val=" + val + ",location=" + location + "]";
    }
}
