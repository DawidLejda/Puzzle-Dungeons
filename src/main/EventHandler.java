package main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.abs;

public class EventHandler
{
    GamePanel gamePanel;
    KeyHandler pressedKey;
    BufferedImage useImage;
    boolean renderUse;
    public EventHandler(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;

        try {
            useImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/use.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Update()
    {
        renderUse = false;
        pressedKey.UseInRange = false;
        int charX = gamePanel.player.x / gamePanel.tileSize;
        int charY = gamePanel.player.y / gamePanel.tileSize;
        if (abs(charX - gamePanel.airvent.x) <= 4 && abs(charY - gamePanel.airvent.y) <= 3) {
            int centerX = gamePanel.airvent.x + 1;
            int centerY = gamePanel.airvent.y + 1;
            if ((charX == centerX) && (charY == centerY-1))
            {
                renderUse = true;
                pressedKey.UseInRange = true;
            }

        }
    }

    public void Draw(Graphics2D g2)
    {
        int x = gamePanel.player.centerX + gamePanel.tileSize/3;
        int y = gamePanel.player.centerY - gamePanel.tileSize/2;
        if(renderUse)
        {
            g2.drawImage(useImage, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
        }
    }
}
