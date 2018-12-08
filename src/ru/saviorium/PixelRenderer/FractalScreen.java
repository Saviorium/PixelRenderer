package ru.saviorium.PixelRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class FractalScreen extends JPanel implements Runnable, ComponentListener {
    private boolean running = false;
    private Thread renderer;
    private MatrixRenderer fractal;

    private Graphics graphics;
    private Image image = null;
    private boolean resized = false;

    private Point mouseFrom;

    public FractalScreen() {
        fractal = new MandelbrotRenderer(this.getWidth(), this.getHeight());
    }

    void startRendering() {
        renderer = new Thread(this);
        renderer.start();
    }

    @Override
    public void run() {
        running = true;
        long pauseTime;
        while(running) {
            screenRender();
            paintScreen();
            pauseTime = fractal.isRunning() ? 100 : 100;
            try {
                Thread.sleep(pauseTime); //TODO: add proper time checking
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    private void screenRender()
    {
        if(!fractal.isImageChanged())
            return;
        if (image == null || resized){
            image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            resized = false;
            if (image == null) {
                System.out.println("dbImage is null");
                return;
            }
            else
                graphics = image.getGraphics();
        }
        graphics.setColor(Color.ORANGE);

        graphics.fillRect (0, 0, this.getWidth(), this.getHeight());
        for(int i = 0; i < fractal.getWidth(); i++) {
            for(int j = 0; j < fractal.getHeight(); j++) {
                graphics.setColor(Color.getHSBColor(fractal.getPixel(i, j)/256f, 1f, 0.8f));
                graphics.fillRect(i, j,1,1);
            }
        }
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();  // get the panel’s graphic context
            if ((g != null) && (image != null))
                g.drawImage(image, 0, 0, null);
            g.dispose();
        }
        catch (Exception e)
        { System.out.println("Graphics context error: " + e);  }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resized = true;
        fractal.setSize(this.getWidth(), this.getHeight());
        fractal.startRender();
        paintScreen();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        mouseFrom = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        fractal.move(mouseFrom, e.getPoint());
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        fractal.zoom(e.getPoint(), e.getWheelRotation());
    }
}
