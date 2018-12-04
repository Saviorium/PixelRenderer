package ru.saviorium.PixelRenderer;

public class SimpleMatrixRenderer extends MatrixRenderer {

    public SimpleMatrixRenderer(int width, int height) {
        super(width, height);
    }

    @Override
    public void run() {
        for(int i = 0; i < this.getWidth(); i++) {
            for(int j = 0; j < this.getHeight(); j++) {
                this.setPixel(i*j, i, j);
            }
        }
    }
}
