package main;


import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

public class EventHandler {
    GamePanel gamePanel;
    KeyHandler pressedKey;

    boolean renderUse,airventSeen,renderUseCatnip;
    public boolean bunkerStop,renderThrow;
    public boolean trailStart,catStart = false;
    public boolean buttonState, elevationDown, elevationUp;
    public int charX,charY;
    public int catnipsCount = 3;
    public object.ObjectImages[] catnipBar = new ObjectImages[4];
    public object.ObjectImages[] use = new ObjectImages[3];
    public int randomBridge;
    Random rand = new Random();

    public EventHandler(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        buttonState = true;
        getUIModel();
    }


    public void getUIModel()
    {
        try
        {
            use[0] = new ObjectImages();
            use[1] = new ObjectImages();
            use[2] = new ObjectImages();
            use[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/use.png")));
            use[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/useCatnip.png")));
            use[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/throw.png")));
            for (int i = 0; i < 4; i++)
            {
                String path = "events/progressBar".concat(Integer.toString(i)).concat(".png");
                catnipBar[i] = new ObjectImages();
                catnipBar[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void Update() {
        renderUse = false;
        bunkerStop = false;
        pressedKey.UseInRange = false;
        elevationDown = false;
        elevationUp = false;
        renderUseCatnip = false;
        renderThrow = false;
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;

        catnip();
        catnipTrail();

        if(!catStart && gamePanel.trail.catnipSteps  > 4)
        {
            catStart = true;
        }

        if (abs(charX - gamePanel.airvent.x) <= 4
                && abs(charY - gamePanel.airvent.y) <= 3)
        {
            int centerX = gamePanel.airvent.x + 1;
            int centerY = gamePanel.airvent.y + 1;
            if ((charX == centerX) && (charY == centerY - 1))
            {

                pressedKey.UseInRange = true;
                if(gamePanel.cat.stop)
                {
                    renderThrow = true;
                    if (Objects.equals(pressedKey.lastReleasedKey, "use"))
                    {
                        gamePanel.bunker.materialize = true;
                        pressedKey.lastReleasedKey = null;
                    }
                }
                else
                {
                    renderUse = true;
                    if (pressedKey.use)
                    {
                        bunkerStop = true;
                    }
                }
            }

        } else if (abs(charX - gamePanel.ButtonState.x) <= 1
                && abs(charY - gamePanel.ButtonState.y) <= 1) {
            if ((charX == gamePanel.ButtonState.x) && (charY == gamePanel.ButtonState.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    randomBridge =  rand.nextInt(2);
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
                && abs(charY - gamePanel.ButtonElevationDown.y) <= 1)
        {
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

        else if(gamePanel.bunker.materialize &&
                (abs(charX - gamePanel.bunker.x) <= 2
                && abs(charY - gamePanel.bunker.y) <= 2))
        {
            if ((charX == gamePanel.bunker.x) && (charY == gamePanel.bunker.y ))
            {
                renderUse = true;
                pressedKey.UseInRange = true;
                if (Objects.equals(pressedKey.lastReleasedKey, "use"))
                {
                    System.out.println("wejscie do bunkra");
                    pressedKey.lastReleasedKey = null;
                }
            }
        }


    }
    public void catnipTrail()
    {
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;
        if(!trailStart && catnipsCount == 3 &&
            (abs(charX - gamePanel.cat.x/gamePanel.tileSize) <= 1 && abs(charY - gamePanel.cat.y/gamePanel.tileSize) <= 1))
        {
            renderUseCatnip = true;
            pressedKey.UseInRange = true;
            if (Objects.equals(pressedKey.lastReleasedKey, "use"))
            {
                trailStart = true;
                pressedKey.lastReleasedKey = null;
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
            g2.drawImage(use[0].image, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
        }
        else if(renderUseCatnip)
        {
            g2.drawImage(use[1].image, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
        }
        else if(renderThrow)
        {
            g2.drawImage(use[2].image, x-20, y-25, gamePanel.tileSize, gamePanel.tileSize, null);
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
