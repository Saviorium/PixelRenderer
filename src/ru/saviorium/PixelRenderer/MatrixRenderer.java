package ru.saviorium.PixelRenderer;

public class MatrixRenderer implements Runnable {
    private int width, height;
    private int[][] matrix;
    private Thread renderThread;
    private boolean terminating;
    private boolean fullImageDelivered;

    public MatrixRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        setSize(width, height);

        renderThread = new Thread(this);
    }

    public void setSize(int width, int height) {
        System.out.println("Resizing to " + width + "x" + height);
        if(matrix != null && width == this.width && height == this.height) {
            return;
        }

        width = (width>0) ? width : 1;
        height = (height>0) ? height : 1;
        int[][] newMatrix = new int[width][height];

        if(matrix != null) {
            int minWidth = (width < this.width) ? width : this.width;
            int minHeight = (height < this.height) ? height : this.height;
            for (int i = 0; i < minWidth; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, minHeight);
            }
        }
        this.width = width;
        this.height = height;
        matrix = newMatrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixel(int x, int y) {
        if(x >= width)  { x = width - 1;  }
        if(y >= height) { y = height - 1; }
        if(x < 0)       { x = 0; }
        if(y < 0)       { y = 0; }
        return matrix[x][y];
    }

    public boolean setPixel(int value, int x, int y) {
        if(x >= this.width || x < 0 || y >= this.height || y < 0) {
            return false;
        }
        matrix[x][y] = value;
        return true;
    }

    public void fill(int value, int x, int y, int size) {
        for(int i = x; i < x+size; i++) {
            for(int j = y; j < y+size; j++) {
                if((i < this.getWidth()) && (j < this.getHeight()))
                    matrix[i][j] = value;
            }
        }
    }

    public void startRender() {
        fullImageDelivered = false;
        if(renderThread.getState() != Thread.State.NEW) {
            try {
                terminating = true;
                renderThread.join();
                terminating = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            renderThread = new Thread(this);
        }
        renderThread.start();
    }

    public boolean isTerminating() {
        return terminating;
    }

    public boolean isRunning() {
        if(renderThread.getState() != Thread.State.TERMINATED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isImageChanged() {
        if(fullImageDelivered) {
            return false;
        }
        if(!isRunning()) {
            fullImageDelivered = true;
            return true;
        }
        return true;
    }

    @Override
    public void run() {

    }
}
