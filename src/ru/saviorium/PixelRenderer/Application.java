package ru.saviorium.PixelRenderer;

import javax.swing.*;

public class Application extends JFrame {
    private FractalScreen game;
    private int width = 1000, height = 1000;

    Application() {
        game = new FractalScreen();
        add(game);
        game.addComponentListener(game);
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
