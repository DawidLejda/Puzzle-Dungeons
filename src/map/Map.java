package map;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Map extends bitmap
{
    GamePanel gamePanel;
    private int animationFrame = 1;
    private int swapWater = 1;

    public Map(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getTileSet();
    }

    public void getTileSet()
    {
        try
        {
            grass1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/grass1.png")));
            grass2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/grass2.png")));
            grass3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/grass3.png")));
            grass4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/grass4.png")));
            grass5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/grass5.png")));

            water1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water1.png")));
            water2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water2.png")));
            water3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water3.png")));
            water4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water4.png")));
            water5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water5.png")));
            water6 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water6.png")));

            water_left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_left.png")));
            water_right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_right.png")));
            water_top = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_top.png")));
            water_bottom = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_bottom.png")));

            water_deco1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_deco1.png")));
            water_deco2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_deco2.png")));
            water_deco3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_deco3.png")));
            water_deco4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/water/water_deco4.png")));


        }
        catch (IOException e)
        {
        System.out.println("Couldn't read tileset");
        gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {
        animationFrame++;
        if (animationFrame >= 16)
        {
            swapWater++;
            if (swapWater > 6)
            {
                swapWater = 1;
            }
            animationFrame = 0;
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage tile = null;
        int length_Y = gamePanel.height / gamePanel.tileSize;
        int length_X = gamePanel.width / gamePanel.tileSize;

        for (int y = 0; y < length_Y; y++)
        {
            for (int x = 0; x < length_X; x++)
            {
                if (starting_area[y][x] >= 10 && starting_area[y][x] < 20)
                {
                    if (starting_area[y][x] == 11)
                    {
                        tile = grass1;
                    }
                    else if (starting_area[y][x] == 12)
                    {
                        tile = grass2;
                    }
                    else if (starting_area[y][x] == 13)
                    {
                        tile = grass3;
                    }
                    else if (starting_area[y][x] == 14)
                    {
                        tile = grass4;
                    }
                    else if (starting_area[y][x] == 15)
                    {
                        tile = grass5;
                    }
                }
                else if (starting_area[y][x] >= 20)
                {
                    switch (starting_area[y][x])
                    {
                        case 21:
                            // Animating water
                            for (int i = 0; i < 6; i++)
                            {
                                if (swapWater == 1)
                                {
                                    tile = water1;
                                }
                                else if (swapWater == 2)
                                {
                                    tile = water2;
                                }
                                else if (swapWater == 3)
                                {
                                    tile = water3;
                                }
                                else if (swapWater == 4)
                                {
                                    tile = water4;
                                }
                                else if (swapWater == 5)
                                {
                                    tile = water5;
                                }
                                else
                                {
                                    tile = water6;
                                }
                            }
                            break;
                        case 22:
                            tile = water_deco1;
                            break;
                        case 23:
                            tile = water_deco2;
                            break;
                        case 24:
                            tile = water_deco3;
                            break;
                        case 25:
                            tile = water_deco4;
                            break;
                        case 26:
                            tile = water_left;
                            break;
                        case 27:
                            tile = water_right;
                            break;
                        case 28:
                            tile = water_bottom;
                            break;
                        case 29:
                            tile = water_top;
                            break;
                    }



                }

                g2.drawImage(tile, x * gamePanel.tileSize, y * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
