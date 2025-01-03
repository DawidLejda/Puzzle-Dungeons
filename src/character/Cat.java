package character;

import main.GamePanel;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Cat extends Character
{
    GamePanel gamePanel;
    Random rand = new Random();
    private int swapSkin = 0;
    private int swapIdle = 0;
    public boolean moving = false;
    public boolean catStart = false;
    int frame,idle_frame,time = 0;
    int randomTime = (rand.nextInt(5))+10;
    public object.ObjectImages[][] Sprite = new ObjectImages[4][4];
    public object.ObjectImages[] IdleSprite = new ObjectImages[4];

    public Cat(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getCatModel();
        direction = "down";
        x = 15;
        y = 15;
        speed = 4;
    }

    void getCatModel()
    {
        try {
            String direction;
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                {
                    direction = "down";
                }
                else if (j == 1)
                {
                    direction = "top";
                }
                else if (j == 2)
                {
                    direction = "left";
                }
                else
                {
                    direction = "right";
                }
                for (int i = 0; i < 4; i++)
                {
                    String path = "Cat/".concat(direction).concat(Integer.toString(i + 1)).concat(".png");
                    Sprite[j][i] = new ObjectImages();
                    Sprite[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                    if (j == 0)
                    {
                        String idle_path = "Cat/idle".concat(Integer.toString(i + 1)).concat(".png");
                        IdleSprite[i] = new ObjectImages();
                        IdleSprite[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(idle_path)));
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Couldn't read cat character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {
        frame++;
        idle_frame++;
        time++;
        if(moving)
        {
            if(frame > 10)
            {
                swapSkin++;
                swapIdle = 0;
                if (swapSkin >= 4)
                {
                    swapSkin = 0;
                }
                frame = 0;
            }
        }
        else
        {
            if(idle_frame >= randomTime)
            {
                swapIdle++;
                swapSkin = 0;
                if (swapIdle >= 4)
                {
                    swapIdle = 0;
                }
                idle_frame = 0;
            }
        }

        if(time>randomTime*4)
        {
            randomTime = (rand.nextInt(17))+8;
            time = 0;
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage image = null;

        if(moving)
        {
            image = switch (direction) {
                case "down" -> Sprite[0][swapSkin].image;
                case "up" -> Sprite[1][swapSkin].image;
                case "left" -> Sprite[2][swapSkin].image;
                case "right" -> Sprite[3][swapSkin].image;
                default -> image;
            };
        }

        else
        {
            image = IdleSprite[swapIdle].image;
        }

        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX + 7;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY + 10;

        if      (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))
        {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(image, centerX, centerY, 40, 40, null);

        }
    }
}
