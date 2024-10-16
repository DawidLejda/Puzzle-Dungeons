package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    final int width = 640;
    final int height = 480;
    final int tileSize = 32;

    Thread GameThread;
    KeyHandler pressedKey = new KeyHandler();


    public int playerSpeed = 4;
    public int playerX = 100;
    public int playerY = 100;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
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
        while (GameThread != null)
        {
            playerMovement();
            repaint();
        }
    }

    public void playerMovement()
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
        g2.setColor(Color.white);
        g2.fillRect(100,100, tileSize, tileSize);
        g2.dispose();
    }

}
