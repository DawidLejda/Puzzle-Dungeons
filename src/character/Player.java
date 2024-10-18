package character;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Character
{

    GamePanel gamePanel;
    KeyHandler pressedKey;
    private boolean playerMoving;
    private int swapSkin = 1;
    private int swapIdle = 1;
    private int animationFrame = 1;
    private int animationIdle = 1;

    public Player(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();

        x = 100;
        y = 100;
        speed = 4;
        getPlayerModel();
    }

    public void getPlayerModel()
    {
        try
        {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/player_down.1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/player_down.2.png")));
            idle_down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down1.png")));
            idle_down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down2.png")));
            idle_down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down3.png")));
            idle_down4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down4.png")));

        } catch (IOException e) {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {
        if (pressedKey.left)
        {
            x -= speed;
            animationFrame++;
            animationIdle = 0;
            playerMoving = true;
        }
        else if(!pressedKey.left)
        {
            animationFrame = 0;
            animationIdle++;
            playerMoving = false;
        }
        else if (pressedKey.right)
        {
            x += speed;
            animationFrame++;
            animationIdle = 0;
            playerMoving = true;
        }
        else if(!pressedKey.right)
        {
            animationFrame = 0;
            animationIdle++;
            playerMoving = false;
        }
        else if (pressedKey.up)
        {
            y -= speed;
            animationFrame++;
            animationIdle = 0;
            playerMoving = true;
        }
        else if(!pressedKey.up)
        {
            animationFrame = 0;
            animationIdle++;
            playerMoving = false;
        }
        else if (pressedKey.down)
        {
            y += speed;
            animationFrame++;
            animationIdle = 0;
            playerMoving = true;
        }
        else if(!pressedKey.down)
        {
            animationFrame = 0;
            animationIdle++;
            playerMoving = false;
        }



        // 8 frames of pressing key per animation change
        if(playerMoving)
        {
            if (animationFrame >= 8)
            {
                if (swapSkin > 2)
                {
                    swapSkin = 1;
                }

                swapSkin++;
                animationFrame = 0;
            }
        }
        else
        {
            if (animationIdle >= 8)
            {
                if (swapIdle > 4)
                {
                    swapIdle = 1;
                }

                swapIdle++;
                animationIdle = 0;
            }
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage image = null;

        if (playerMoving)
        {
            if(swapSkin == 1)
            {
                image = down1;
            }
            else
            {
                image = down2;
            }
        }

        else
        {
            if (pressedKey.lastPressedKey != null)
            {
                switch (pressedKey.lastPressedKey)
                {
                    case "down":
                        if(swapIdle == 1)
                        {
                            image = idle_down1;
                        }
                        else if(swapIdle == 2)
                        {
                            image = idle_down2;
                        }
                        else if(swapIdle == 3)
                        {
                            image = idle_down3;
                        }
                        else if(swapIdle == 4)
                        {
                            image = idle_down4;
                        }
                }
            }
        }



        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
