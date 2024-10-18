package main;

import character.Player;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    // Parameters ************************************************
    final int width = 1280;
    final int height = 720;
    public final int tileSize = 64;
    final int TARGET_FPS = 60;
    public int averageFPS;
    public boolean gameRunning = true;
    public int frameCount = 0;

    Thread GameThread;
    KeyHandler pressedKey = new KeyHandler();
    Player player = new Player(this,pressedKey);

    // ********************************************************
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setDoubleBuffered(true);
        this.addKeyListener(pressedKey);
        this.setFocusable(true);
    }

    public void startGameThread()
    {
        GameThread = new Thread(this);
        GameThread.start();
    }

    @Override
    public void run()
    {

        final double targetTime = 1000000000.0 / TARGET_FPS;
        double delta = 0;

        long timer = System.currentTimeMillis();
        long initialTime = System.nanoTime();

        // Game loop
        while (gameRunning)
        {
            long currentTime = System.nanoTime();

            delta += (currentTime - initialTime) / targetTime;
            initialTime = currentTime;

            if (delta >= 1)
            {
                Update();
                repaint();
                delta--;
                frameCount++;
            }

            if (System.currentTimeMillis() - timer > 1000)
            {
                averageFPS = frameCount;
                timer += 1000;
                frameCount = 0;
            }
        }
    }

    public void Update()
    {
        player.Update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.Draw(g2);

        g2.drawString("FPS: " + averageFPS,3,12);
        g2.dispose();
    }

}
