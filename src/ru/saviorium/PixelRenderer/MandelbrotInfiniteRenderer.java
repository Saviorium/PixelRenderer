package ru.saviorium.PixelRenderer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.MathContext;

public class MandelbrotInfiniteRenderer extends MandelbrotRenderer{
    private final int MAX_ITERATIONS = 32;
    private PointBigDecimal pos = new PointBigDecimal();

    private double unitsPerPixel;
    private PointBigDecimal screenOrigin = new PointBigDecimal();


    public MandelbrotInfiniteRenderer(int width, int height) {
        super(width, height);
    }

    @Override
    protected int calculate(int x, int y) {
        PointBigDecimal c = getBigDecimalFromScreenCoord(x, y);
        BigDecimal xnew = c.getX(), ynew = c.getY();
        BigDecimal xprev = xnew, yprev = ynew;
        for(int i = 1; i < MAX_ITERATIONS; i++) {
            xnew = xprev.multiply(xprev, MathContext.DECIMAL128).subtract(yprev.multiply(yprev, MathContext.DECIMAL128)).add(c.getX());
            ynew = xprev.multiply(yprev, MathContext.DECIMAL128).multiply(BigDecimal.valueOf(2L)).add(c.getY());
            xprev = xnew;
            yprev = ynew;
            if((xnew.abs().add(ynew.abs())).compareTo(BigDecimal.TEN) > 0) return i;
        }
        return 256;
    }

    protected PointBigDecimal getBigDecimalFromScreenCoord(int x, int y) {
        BigDecimal posX = screenOrigin.getX().add(BigDecimal.valueOf(x * unitsPerPixel));
        BigDecimal posY = screenOrigin.getY().add(BigDecimal.valueOf(y * unitsPerPixel));
        return new PointBigDecimal(posX, posY);
    }

    @Override
    public void move(Point from, Point to) {
        PointBigDecimal fromD = getBigDecimalFromScreenCoord(from.x, from.y);
        PointBigDecimal toD = getBigDecimalFromScreenCoord(to.x, to.y);
        pos.setLocation(pos.getX().subtract(toD.getX()).add(fromD.getX()),
                        pos.getY().subtract(toD.getY()).add(fromD.getY()));
        this.startRender();
    }

    @Override
    public void zoom(Point screenPosition, int direction) {
        double zoomUnit = 0.1;
        PointBigDecimal mousePos = getBigDecimalFromScreenCoord(screenPosition.x, screenPosition.y);
        pos.setLocation(
                pos.getX().multiply(BigDecimal.valueOf(1D-zoomUnit* -direction))
                        .add(mousePos.getX().multiply(BigDecimal.valueOf(zoomUnit* -direction))),
                pos.getY().multiply(BigDecimal.valueOf(1D-zoomUnit* -direction))
                        .add(mousePos.getY().multiply(BigDecimal.valueOf(zoomUnit* -direction)))
        );
        zoom *= 1 + direction * zoomUnit;
        this.startRender();
    }

    @Override
    protected void updateCache() {
        if(this.getHeight() == 0 || this.getWidth() == 0) return;
        unitsPerPixel = (zoom*2)/this.getHeight();
        BigDecimal x = pos.getX().subtract(new BigDecimal(zoom))
                .multiply(BigDecimal.valueOf((double)this.getWidth()/this.getHeight()));
        BigDecimal y = pos.getY().subtract(new BigDecimal(zoom));
        screenOrigin.setLocation(x, y);
    }
}

class PointBigDecimal {
    private BigDecimal x, y;

    public PointBigDecimal() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public PointBigDecimal(double x, double y) {
        this(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
    }

    public PointBigDecimal(BigDecimal x, BigDecimal y) {
        setLocation(x, y);
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setLocation(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }
}
