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
    public boolean bunkerStop;
    public boolean buttonState, elevationDown, elevationUp;
    public EventHandler(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        buttonState = true;
        try {
            useImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/use.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Update()
    {
        renderUse = false;
        bunkerStop = false;
        pressedKey.UseInRange = false;
        elevationDown = false;
        elevationUp = false;

        int charX = gamePanel.player.x / gamePanel.tileSize;
        int charY = gamePanel.player.y / gamePanel.tileSize;
        if (abs(charX - gamePanel.airvent.x) <= 4
                && abs(charY - gamePanel.airvent.y) <= 3)
        {
            int centerX = gamePanel.airvent.x + 1;
            int centerY = gamePanel.airvent.y + 1;
            if ((charX == centerX) && (charY == centerY-1))
            {
                renderUse = true;
                pressedKey.UseInRange = true;
                if(pressedKey.use)
                {
                    bunkerStop = true;
                }
            }

        }
        else if(abs(charX - gamePanel.ButtonState.x) <= 1
                && abs(charY - gamePanel.ButtonState.y) <= 1)
        {
            if ((charX == gamePanel.ButtonState.x) && (charY == gamePanel.ButtonState.y))
            {
                renderUse = true;
                pressedKey.UseInRange = true;

                if(Objects.equals(pressedKey.lastReleasedKey, "use"))
                {
                    buttonState = !buttonState;
                    pressedKey.lastReleasedKey = null;
                }
            }
        }


        else if(abs(charX - gamePanel.ButtonElevationUp.x) <= 1
                && abs(charY - gamePanel.ButtonElevationUp.y) <= 1)
        {
            if ((charX == gamePanel.ButtonElevationUp.x) && (charY == gamePanel.ButtonElevationUp.y))
            {
                renderUse = true;
                pressedKey.UseInRange = true;

                if(Objects.equals(pressedKey.lastReleasedKey, "use"))
                {
                    elevationUp = true;
                    pressedKey.lastReleasedKey = null;
                }
            }
        }

        else if(abs(charX - gamePanel.ButtonElevationDown.x) <= 1
                && abs(charY - gamePanel.ButtonElevationDown.y) <= 1)
        {
            if ((charX == gamePanel.ButtonElevationDown.x) && (charY == gamePanel.ButtonElevationDown.y))
            {
                renderUse = true;
                pressedKey.UseInRange = true;

                if(Objects.equals(pressedKey.lastReleasedKey, "use"))
                {
                    elevationDown = true;
                    pressedKey.lastReleasedKey = null;
                }
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
