package ru.saviorium.PixelRenderer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatrixRendererTest {

    @Before
    public void setUp() {
    }

    @Test
    public void setBiggerSize() {
        MatrixRenderer matrix = new MatrixRenderer(100, 100);
        matrix.setPixel(1, 99, 99);
        matrix.setSize(200, 200);
        matrix.setPixel(2, 199,199);
        Assert.assertEquals("Pixel unchanged after resize", 1, matrix.getPixel(99, 99));
        Assert.assertEquals("Pixel readable after resize", 2, matrix.getPixel(199, 199));
        Assert.assertEquals("Check initial state after resize", 0, matrix.getPixel(198, 199));
    }

    @Test
    public void setSmallerSize() {
        MatrixRenderer matrix = new MatrixRenderer(100, 100);
        matrix.setPixel(1, 49, 49);
        matrix.setSize(50, 50);
        Assert.assertEquals("Pixel unchanged after resize", 1, matrix.getPixel(49, 49));
        matrix.setPixel(2, 49, 49);
        Assert.assertEquals("Pixel writable after resize", 2, matrix.getPixel(49, 49));
        matrix.setSize(200, 200);
        matrix.setPixel(3, 199, 199);
        Assert.assertEquals("Pixel writable after resize", 3, matrix.getPixel(199, 199));
    }

    @Test
    public void set8KSize() {
        MatrixRenderer matrix = new MatrixRenderer(100, 100);
        matrix.setSize(7680, 4320);
        matrix.setSize(7681, 4321);
    }

    @Test
    public void setNegativeSize() {
        MatrixRenderer matrix = new MatrixRenderer(-100, 0);
        Assert.assertEquals("Set size to negative value", 1, matrix.getWidth());
        Assert.assertEquals("Set size to 0",1, matrix.getHeight());
    }

    @Test
    public void setAndCheckPixel() {
        MatrixRenderer matrix = new MatrixRenderer(100, 100);
        int x = 10, y = 10;
        Assert.assertEquals("Check pixel initial state", 0, matrix.getPixel(x, y));
        matrix.setPixel(100, x, y);
        Assert.assertEquals("Set and read a pixel", 100, matrix.getPixel(x, y));
        matrix.setPixel(-1, x, y);
        Assert.assertEquals("Set pixel twice", -1, matrix.getPixel(x, y));
    }

    @Test
    public void readPixelsOutOfBounds() {
        MatrixRenderer matrix = new MatrixRenderer(100, 100);
        matrix.setPixel(1, 99, 50);
        Assert.assertEquals("Get left out of bounds pixel", 1, matrix.getPixel(100, 50));
        matrix.setPixel(-1, 0, 0);
        Assert.assertEquals("Get -1 -1 pixel", -1, matrix.getPixel(-1, -1));
    }
}