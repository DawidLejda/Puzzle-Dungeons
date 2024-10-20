package map;

import character.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Map extends bitmap
{
    GamePanel gamePanel;
    Player player;

    private int animationFrame = 1;
    private int swapWater = 1;
    Random rand = new Random();
    int randomWave = 16;
    int chanceForWave = 0;
    int waveTime = 0;

    Tile[] tile = new Tile[50];

    public Map(GamePanel gamePanel, Player player)
    {
        this.gamePanel = gamePanel;
        this.player = player;
        getTileSet();
    }

    public void getTileSet()
    {
        loadTileSet(11, "grass", "grass1");
        loadTileSet(12, "grass", "grass2");
        loadTileSet(13, "grass", "grass3");
        loadTileSet(14, "grass", "grass4");
        loadTileSet(15, "grass", "grass5");

        // 21 index initializing water animation
        loadTileSet(1,"water", "water1");
        loadTileSet(2, "water", "water2");
        loadTileSet(3, "water", "water3");
        loadTileSet(4, "water", "water4");
        loadTileSet(5, "water", "water5");
        loadTileSet(6, "water", "water6");
        loadTileSet(7, "water", "water7");

        loadTileSet(22, "water", "water_deco1");
        loadTileSet(23, "water", "water_deco2");
        loadTileSet(24, "water", "water_deco3");
        loadTileSet(25, "water", "water_deco4");

        loadTileSet(26, "water", "water_left");
        loadTileSet(27, "water", "water_right");
        loadTileSet(28, "water", "water_bottom");
        loadTileSet(29, "water", "water_top");

        loadTileSet(31, "water", "water_corner1");
        loadTileSet(32, "water", "water_corner2");
        loadTileSet(33, "water", "water_corner3");
        loadTileSet(34, "water", "water_corner4");

    }
    public void loadTileSet(int index, String directory, String name)
    {
        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/"+ directory + "/" + name +".png")));
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
        if (animationFrame >= randomWave)
        {
            swapWater++;
            if (swapWater > 7)
            {
                randomWave = rand.nextInt(15) + 8;
                chanceForWave = rand.nextInt(2);
                swapWater = 1;
            }
            animationFrame = 0;
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage render_tile;
        for (int y = 0; y < mapHeight; y++)
        {
            for (int x = 0; x < mapWidth; x++)
            {
                int map_index = starting_area[y][x];
                int centerX = x * gamePanel.tileSize - player.x + player.centerX;
                int centerY = y * gamePanel.tileSize - player.y + player.centerY;

                if (map_index == 21)
                {
                    if(starting_area[y][x+chanceForWave] == 21)
                    {
                        starting_area[y][x+chanceForWave] = 20;
                    }
                    if (chanceForWave != 0 )
                    {
                        if (swapWater == 1) {
                            render_tile = tile[1].image;
                        } else if (swapWater == 2) {
                            render_tile = tile[2].image;
                        } else if (swapWater == 3) {
                            render_tile = tile[3].image;
                        } else if (swapWater == 4) {
                            render_tile = tile[4].image;
                        } else if (swapWater == 5) {
                            render_tile = tile[5].image;
                        } else if (swapWater == 6) {
                            render_tile = tile[6].image;
                        } else {
                            render_tile = tile[7].image;
                        }
                    }
                    else {
                        render_tile = tile[7].image;
                    }

                }
                else if(map_index == 20)
                {
                    render_tile = tile[7].image;
                    waveTime++;
                    if (waveTime > 160)
                    {
                        waveTime = 0;
                        starting_area[y][x] = 21;
                    }
                }
                else
                {
                    render_tile = tile[map_index].image;
                }
                if (((x * gamePanel.tileSize) < (player.x + player.centerX + gamePanel.tileSize)) &&
                   ((y * gamePanel.tileSize) < (player.y + player.centerY + gamePanel.tileSize)) &&
                   ((x * gamePanel.tileSize) > (player.x - player.centerX - gamePanel.tileSize)) &&
                   ((y * gamePanel.tileSize) > (player.y - player.centerY - gamePanel.tileSize)))

                    {
                        g2.drawImage(render_tile, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                    }

            }
        }
    }
}
