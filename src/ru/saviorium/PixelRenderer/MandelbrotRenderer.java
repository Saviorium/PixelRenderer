package ru.saviorium.PixelRenderer;

import java.awt.geom.Point2D;

public class MandelbrotRenderer extends MatrixRenderer {
    private final int MAX_ITERATIONS = 32;
    private Point2D pos = new Point2D.Double(0d, 0d);
    private double zoom = 2d;

    public MandelbrotRenderer(int width, int height) {
        super(width, height);
    }

    @Override
    public void run() {
        int step = 32;
        while(step > 0) {
            int x = 0, y = 0;
            for(int i = x; i < this.getWidth(); i+=step) {
                for (int j = x; j < y + this.getHeight(); j+=step) {
                    int value = calculate(i, j);
                    fill(value, i, j, step);
                }
            }
            step >>>= 1;
        }
    }

    private int calculate(int x, int y) {
        Point2D c = getFromScreenCoord(x, y);
        double xnew = c.getX(), ynew = c.getY();
        double xprev = xnew, yprev = ynew;
        for(int i = 1; i < MAX_ITERATIONS; i++) {
            xnew = xprev * xprev - yprev * yprev + c.getX();
            ynew = 2 * xprev * yprev + c.getY();
            xprev = xnew;
            yprev = ynew;
            if(xnew > 10) return i;
        }
        return 256;
    }

    private Point2D getFromScreenCoord(int x, int y) {
        double unitsPerPixel = (zoom*2)/this.getHeight();
        double posX = (pos.getX() - zoom * ((double)this.getWidth()/this.getHeight())) + (x * unitsPerPixel);
        double posY = (pos.getY() - zoom) + (y * unitsPerPixel);
        return new Point2D.Double(posX, posY);
    }
}
