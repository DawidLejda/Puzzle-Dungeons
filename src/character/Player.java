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

    public final int centerX;
    public final int centerY;

    public Player(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();

        centerX = (gamePanel.width / 2) - (gamePanel.tileSize / 2);
        centerY = (gamePanel.height / 2) - (gamePanel.tileSize / 2);

        // Coordinates of player starting position
        x = gamePanel.tileSize * 18;
        y = gamePanel.tileSize * 10;
        speed = 4;
    }

    public void getPlayerModel()
    {
        try
        {
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

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left4.png")));
            left5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left5.png")));
            left6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/left6.png")));
            idle_left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_left1.png")));
            idle_left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_left2.png")));
            idle_left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_left3.png")));
            idle_left4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_left4.png")));
            idle_left5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_left5.png")));


            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right4.png")));
            right5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right5.png")));
            right6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/move/right6.png")));
            idle_right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_right1.png")));
            idle_right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_right2.png")));
            idle_right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_right3.png")));
            idle_right4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_right4.png")));
            idle_right5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/idle/idle_right5.png")));


        }
        catch (IOException e)
        {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update() {
        if (pressedKey.left)
        {
            direction = "left";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.right)
        {
            direction = "right";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.up)
        {
            direction = "up";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.down)
        {
            direction = "down";
            animationFrame++;
            playerMoving = true;
        }
        else
        {
            animationIdle++;
            playerMoving = false;
        }

        collision = false;
        gamePanel.collisionChecker.CheckTileCollision(this);
        if(!collision)
        {
            if (pressedKey.left)
            {
                x -= speed;
            }
            else if (pressedKey.right)
            {
                x += speed;
            }
            else if (pressedKey.up)
            {
                y -= speed;
            }
            else if (pressedKey.down)
            {
                y += speed;
            }
        }

        // 8 frames of pressing key per animation change
        if (playerMoving)
        {
            if (animationFrame >= 5)
            {
                swapSkin++;
                if (swapSkin > 7)
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
                    else
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
                    else
                    {
                        image = up6;
                    }
                    break;


                case "left":
                    if (swapSkin == 1)
                    {
                        image = left1;
                    }
                    else if (swapSkin == 2)
                    {
                        image = left2;
                    }
                    else if (swapSkin == 3)
                    {
                        image = left3;
                    }
                    else if (swapSkin == 4)
                    {
                        image = left4;
                    }
                    else if (swapSkin == 5)
                    {
                        image = left5;
                    }
                    else
                    {
                        image = left6;
                    }
                    break;

                case "right":
                    if (swapSkin == 1)
                    {
                        image = right1;
                    }
                    else if (swapSkin == 2)
                    {
                        image = right2;
                    }
                    else if (swapSkin == 3)
                    {
                        image = right3;
                    }
                    else if (swapSkin == 4)
                    {
                        image = right4;
                    }
                    else if (swapSkin == 5)
                    {
                        image = right5;
                    }
                    else
                    {
                        image = right6;
                    }
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
                    case "left":
                        if (swapIdle == 1)
                        {
                            image = idle_left1;
                        }
                        else if (swapIdle == 2)
                        {
                            image = idle_left2;
                        }
                        else if (swapIdle == 3)
                        {
                            image = idle_left3;
                        }
                        else if (swapIdle == 4)
                        {
                            image = idle_left4;
                        }
                        else if (swapIdle == 5)
                        {
                            image = idle_left5;
                        }
                        break;
                    case "right":
                        if (swapIdle == 1)
                        {
                            image = idle_right1;
                        }
                        else if (swapIdle == 2)
                        {
                            image = idle_right2;
                        }
                        else if (swapIdle == 3)
                        {
                            image = idle_right3;
                        }
                        else if (swapIdle == 4)
                        {
                            image = idle_right4;
                        }
                        else if (swapIdle == 5)
                        {
                            image = idle_right5;
                        }
                        break;
                }
            }

            else
            {
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
            }
        }



        g2.drawImage(image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}