package main;


import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

public class EventHandler {
    GamePanel gamePanel;
    KeyHandler pressedKey;
    BufferedImage useImage;
    boolean renderUse,airventSeen;
    public boolean bunkerStop;
    public boolean buttonState, elevationDown, elevationUp;
    public int charX,charY;
    public int catnipsCount = 0;
    public object.ObjectImages[] catnipBar = new ObjectImages[4];

    public EventHandler(GamePanel gamePanel, KeyHandler pressedKey) {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        buttonState = true;
        try
        {
            useImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/use.png")));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        getUIModel();
    }


    public void getUIModel()
    {
        try
        {
            for (int i = 0; i < 4; i++)
            {
                String path = "events/progressBar".concat(Integer.toString(i)).concat(".png");
                catnipBar[i] = new ObjectImages();
                catnipBar[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch(IOException e)
        {
            System.out.println("Couldn't read UI model");
        }
    }

    public void Update() {
        renderUse = false;
        bunkerStop = false;
        pressedKey.UseInRange = false;
        elevationDown = false;
        elevationUp = false;
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;


        catnip();


        if (abs(charX - gamePanel.airvent.x) <= 4
                && abs(charY - gamePanel.airvent.y) <= 3) {
            int centerX = gamePanel.airvent.x + 1;
            int centerY = gamePanel.airvent.y + 1;
            if ((charX == centerX) && (charY == centerY - 1)) {
                renderUse = true;
                pressedKey.UseInRange = true;
                if (pressedKey.use) {
                    bunkerStop = true;
                }
            }

        } else if (abs(charX - gamePanel.ButtonState.x) <= 1
                && abs(charY - gamePanel.ButtonState.y) <= 1) {
            if ((charX == gamePanel.ButtonState.x) && (charY == gamePanel.ButtonState.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    buttonState = !buttonState;
                    pressedKey.lastReleasedKey = null;
                }
            }
        } else if (abs(charX - gamePanel.ButtonElevationUp.x) <= 1
                && abs(charY - gamePanel.ButtonElevationUp.y) <= 1) {
            if ((charX == gamePanel.ButtonElevationUp.x) && (charY == gamePanel.ButtonElevationUp.y))
            {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use"))
                {
                    elevationUp = true;
                    pressedKey.lastReleasedKey = null;
                }
            }
        }
        else if (abs(charX - gamePanel.ButtonElevationDown.x) <= 1
                && abs(charY - gamePanel.ButtonElevationDown.y) <= 1) {
            if ((charX == gamePanel.ButtonElevationDown.x) && (charY == gamePanel.ButtonElevationDown.y))
            {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    elevationDown = true;
                    pressedKey.lastReleasedKey = null;
                }
            }
        }
    }

    public void catnip() {
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;

        if (!airventSeen && abs(charX - gamePanel.airvent.x) <= 5
                && abs(charY - gamePanel.airvent.y) <= 5) {
            airventSeen = true;
        }

        if (airventSeen)
        {
            for (int i = 0, n = gamePanel.catnips.length; i < n; i++)
            {
                gamePanel.catnips[i].isHarvesting = false;
                if(!gamePanel.catnips[i].Harvested)
                {
                    if (abs(charX - gamePanel.catnips[i].x) <= 1
                            && abs(charY - gamePanel.catnips[i].y) <= 1)
                    {
                        if ((charX == gamePanel.catnips[i].x-1 || (charX == gamePanel.catnips[i].x))
                                && (charY == gamePanel.catnips[i].y-1))
                        {
                            renderUse = true;
                            pressedKey.UseInRange = true;
                            if (pressedKey.use)
                            {
                                gamePanel.catnips[i].isHarvesting = true;
                            }
                        }
                    }
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

        if(catnipsCount > 0)
        {
            g2.drawImage(catnipBar[0].image, gamePanel.width-gamePanel.tileSize,
                    0, gamePanel.tileSize, gamePanel.tileSize, null);

            g2.drawImage(catnipBar[catnipsCount].image, gamePanel.width-gamePanel.tileSize,
                    0, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
