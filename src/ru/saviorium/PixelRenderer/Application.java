package ru.saviorium.PixelRenderer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Application extends JFrame {
    private FractalScreen game;
    private int width = 1000, height = 1000;

    Application() {
        game = new FractalScreen();
        add(game);
        game.addComponentListener(game);
        game.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                game.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                game.mouseReleased(e);
            }
        });
        game.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                game.mouseWheelMoved(e);
            }
        });
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        game.startRendering();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {Application ex = new Application();});
    }
}
