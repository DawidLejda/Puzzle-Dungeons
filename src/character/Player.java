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

    public Player(GamePanel gamePanel, KeyHandler pressedKey) {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();

        x = 500;
        y = 100;
        speed = 4;
        getPlayerModel();
    }

    public void getPlayerModel() {
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down4.png")));
            down5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down5.png")));
            down6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/down6.png")));
            idle_down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down1.png")));
            idle_down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down2.png")));
            idle_down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down3.png")));
            idle_down4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down4.png")));
            idle_down5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_down5.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up5.png")));
            up6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/up6.png")));
            idle_up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_up1.png")));
            idle_up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_up2.png")));
            idle_up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_up3.png")));
            idle_up4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_up4.png")));
            idle_up5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_up5.png")));

        } catch (IOException e) {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update() {
        if (pressedKey.left)
        {
            x -= speed;
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.right)
        {
            x += speed;
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.up)
        {
            y -= speed;
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.down)
        {
            y += speed;
            animationFrame++;
            playerMoving = true;
        }
        else
        {
            animationIdle++;
            playerMoving = false;
        }

        // 8 frames of pressing key per animation change
        if (playerMoving)
        {
            if (animationFrame >= 8)
            {
                swapSkin++;
                if (swapSkin > 6)
                {
                    swapSkin = 1;
                }
                animationFrame = 0;
            }
        }
        else
        {
            if (animationIdle >= 12)
            {
                swapIdle++;
                if (swapIdle > 5)
                {
                    swapIdle = 1;
                }
                animationIdle = 0;
            }
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage image = null;

        if(playerMoving)
        {
            switch (pressedKey.lastPressedKey)
            {
                case "down":
                    if (swapSkin == 1)
                    {
                        image = down1;
                    }
                    else if (swapSkin == 2)
                    {
                        image = down2;
                    }
                    else if (swapSkin == 3)
                    {
                        image = down3;
                    }
                    else if (swapSkin == 4)
                    {
                        image = down4;
                    }
                    else if (swapSkin == 5)
                    {
                        image = down5;
                    }
                    else if (swapSkin == 6)
                    {
                        image = down6;
                    }
                    break;

                case "up":
                    if (swapSkin == 1)
                    {
                        image = up1;
                    }
                    else if (swapSkin == 2)
                    {
                        image = up2;
                    }
                    else if (swapSkin == 3)
                    {
                        image = up3;
                    }
                    else if (swapSkin == 4)
                    {
                        image = up4;
                    }
                    else if (swapSkin == 5)
                    {
                        image = up5;
                    }
                    else if (swapSkin == 6)
                    {
                        image = up6;
                    }
                    break;

                case "left":
                    image = down1;
                    break;

                case "right":
                    image = down2;
                    break;
            }
        }

        else
        {
            if (pressedKey.lastReleasedKey != null)
            {
                switch (pressedKey.lastReleasedKey)
                {
                    case "down":
                        if (swapIdle == 1)
                        {
                            image = idle_down1;
                        }
                        else if (swapIdle == 2)
                        {
                            image = idle_down2;
                        }
                        else if (swapIdle == 3)
                        {
                            image = idle_down3;
                        }
                        else if (swapIdle == 4)
                        {
                            image = idle_down4;
                        }
                        else if (swapIdle == 5)
                        {
                            image = idle_down5;
                        }
                        break;

                    case "up":
                        if (swapIdle == 1)
                        {
                            image = idle_up1;
                        }
                        else if (swapIdle == 2)
                        {
                            image = idle_up2;
                        }
                        else if (swapIdle == 3)
                        {
                            image = idle_up3;
                        }
                        else if (swapIdle == 4)
                        {
                            image = idle_up4;
                        }
                        else if (swapIdle == 5)
                        {
                            image = idle_up5;
                        }
                        break;
                }
            }

        }



        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}