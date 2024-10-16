package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    // Parameters ************************************************
    final int width = 640;
    final int height = 480;
    final int tileSize = 32;

    final int TARGET_FPS = 60;
    public int averageFPS;

    Thread GameThread;
    KeyHandler pressedKey = new KeyHandler();

    public int playerSpeed = 4;
    public int playerX = 100;
    public int playerY = 100;

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

        final double targetTime = 1000000000/TARGET_FPS;
        double delta = 0;
        int frameCount = 0;
        long timer = System.currentTimeMillis();
        long initialTime = System.nanoTime();

        // Game loop
        while (GameThread != null)
        {
            long currentTime = System.nanoTime();

            delta += (currentTime - initialTime) / targetTime;
            initialTime = currentTime;

            if (delta >= 1)
            {
                playerUpdate();
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

    public void playerUpdate()
    {
        if (pressedKey.left)
        {
            playerX -= playerSpeed;
        }
        else if (pressedKey.right)
        {
            playerX += playerSpeed;
        }
        else if (pressedKey.up)
        {
            playerY -= playerSpeed;
        }
        else if (pressedKey.down)
        {
            playerY += playerSpeed;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.drawString("FPS: " + averageFPS,5,10);
        g2.dispose();
    }

}
