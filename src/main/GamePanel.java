package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    final int width = 640;
    final int height = 480;
    final int tileSize = 32;
    final int maxScreenCol = width / tileSize; // 640 pixels width / 32 tileSize = 20
    final int maxScreenRow = height / tileSize; // 480 pixels height / 32 tileSize = 15

    Thread GameThread;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread()
    {
        GameThread = new Thread(this);
        GameThread.start();
    }

    @Override
    public void run()
    {

    }
}
