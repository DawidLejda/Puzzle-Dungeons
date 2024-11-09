package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object
{
    public BufferedImage image;
    public String name;
    public int x,y;
    public boolean collision = false;

    public void draw(Graphics2D g2, GamePanel gamePanel)
    {
        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

        if      (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))
        {
            g2.drawImage(image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
        }
    }

}
