package character;

import main.GamePanel;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import static java.lang.Math.abs;

public class Cat extends Character
{
    GamePanel gamePanel;
    Random rand = new Random();
    private int swapSkin = 0;
    private int swapIdle = 0;
    public boolean moving,throwAction,INBUNKER = false;
    public boolean [] visited = new boolean[50];
    public boolean stop = false;
    int frame,idle_frame,time = 0;
    int randomTime = (rand.nextInt(5))+10;
    public object.ObjectImages[][] Sprite = new ObjectImages[4][4];
    public object.ObjectImages[] IdleSprite = new ObjectImages[4];
    String previous_direction;
    public int [] materializeCoordinates = new int [2];

    public Cat(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getCatModel();
        x = 15 * gamePanel.tileSize;
        y = 15 * gamePanel.tileSize;
        speed = 3;
        Arrays.fill(visited,false);
        materializeCoordinates[0] = 18;
        materializeCoordinates[1] = 60;
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
        if(!throwAction)
        {
            if (!moving && direction == null) {
                if (x / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    stop = true;
                }
                if ((x - speed) / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    x -= speed;
                    stop = true;
                } else if ((x + speed) / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    x += speed;
                    stop = true;
                } else if (x / gamePanel.tileSize == 14 && (y - speed) / gamePanel.tileSize == 29) {
                    y += speed;
                    stop = true;
                } else if (x / gamePanel.tileSize == 14 && (y + speed) / gamePanel.tileSize == 29) {
                    y -= speed;
                    stop = true;
                } else if (y == 1852) {
                    y += 4;
                    stop = true;
                }
            }
        }
        else
        {
            playerThrowAction();
        }


        if(gamePanel.event.catStart && !stop || throwAction)
        {
            if(!throwAction)
            {
                direction();
            }

            if(direction != null && !waitForPlayer())
            {
                switch (direction)
                {
                    case "left":
                        x -= speed;
                        moving = true;
                        frame++;
                        break;
                    case "right":
                        x += speed;
                        moving = true;
                        frame++;
                        break;
                    case "up":
                        y -= speed;
                        moving = true;
                        frame++;
                        break;
                    case "down":
                        y += speed;
                        moving = true;
                        frame++;
                        break;
                }
            }
        }


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
            idle_frame++;
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


    void direction()
    {

        for(int i = 0; i < 50; i++)
        {
            int nextX = gamePanel.trail.catnipPathX[i];
            int nextY = gamePanel.trail.catnipPathY[i];

            if(!visited[i])
            {
                if (x / gamePanel.tileSize + 1 == nextX)
                {
                    direction = "right";
                    break;
                }
                else if (x / gamePanel.tileSize - 1 == nextX)
                {
                    direction = "left";
                    break;
                }
                else if (y / gamePanel.tileSize - 1 == nextY)
                {
                    direction = "up";
                    break;
                }
                else if (y / gamePanel.tileSize + 1 == nextY)
                {
                    direction = "down";
                    break;
                }
                else
                {
                    visited[i] = true;
                }
            }

            else
            {
                previous_direction = direction;
                direction = null;
                moving = false;
            }

        }
    }



    boolean waitForPlayer()
    {
        return abs(x / gamePanel.tileSize - gamePanel.player.x / gamePanel.tileSize) <= 1 &&
                abs(y / gamePanel.tileSize - gamePanel.player.y / gamePanel.tileSize) <= 1;
    }


    void playerThrowAction()
    {
        direction = null;
        moving = false;
        if(gamePanel.bunker.materialize)
        {
            if(materializeCoordinates[0] > 0)
            {
                moving = true;
                direction = "right";
                materializeCoordinates[0] -= speed;
                x += speed;
            }
            else if (materializeCoordinates[1] > 0)
            {
                moving = true;
                direction = "up";
                materializeCoordinates[1] -= speed;
                y -= speed;
            }
        }
    }

    public void Draw(Graphics2D g2)
    {

        BufferedImage image = null;
        if(materializeCoordinates[1] >= 0)
        {
            if (moving)
            {
                image = switch (direction)
                {
                    case "up" -> Sprite[0][swapSkin].image;
                    case "down" -> Sprite[1][swapSkin].image;
                    case "left" -> Sprite[2][swapSkin].image;
                    case "right" -> Sprite[3][swapSkin].image;
                    default -> image;
                };
            } else
            {
                image = IdleSprite[swapIdle].image;
            }

            int centerX = x - gamePanel.player.x + gamePanel.player.centerX + 7;
            int centerY = y - gamePanel.player.y + gamePanel.player.centerY + 10;

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(image, centerX, centerY, 40, 40, null);
        }
    }
}
