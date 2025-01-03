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

    public Tile[] tile = new Tile[100];

    public Map(GamePanel gamePanel, Player player)
    {
        this.gamePanel = gamePanel;
        this.player = player;
        getTileSet();
    }

    public void getTileSet()
    {
        loadTileSet(11, "grass", "grass1", false);
        loadTileSet(12, "grass", "grass_left", true);
        loadTileSet(13, "grass", "grass_right", true);
        loadTileSet(14, "grass", "grass_top", true);
        loadTileSet(15, "grass", "grass_bottom", true);
        loadTileSet(16, "grass", "grass_corner_top_right", true);
        loadTileSet(17, "grass", "grass_corner_top_left", true);
        loadTileSet(18, "grass", "grass_corner_bottom_right", true);
        loadTileSet(19, "grass", "grass_corner_bottom_left", true);

        //
        loadTileSet(20,"water", "water1", true);
        loadTileSet(21,"water", "water1", true);

        loadTileSet(1, "water", "water1", true);
        loadTileSet(2, "water", "water2", true);
        loadTileSet(3, "water", "water3", true);
        loadTileSet(4, "water", "water4", true);
        loadTileSet(5, "water", "water5", true);
        loadTileSet(6, "water", "water6", true);
        loadTileSet(7, "water", "water7", true);
        //

        loadTileSet(22, "water", "water_deco1", true);
        loadTileSet(23, "water", "water_deco2", true);
        loadTileSet(24, "water", "water_deco3", true);
        loadTileSet(25, "water", "water_deco4", true);

        loadTileSet(26, "water", "water_right", true);
        loadTileSet(27, "water", "water_left", true);
        loadTileSet(28, "water", "water_bottom", true);
        loadTileSet(29, "water", "water_top", true);

        loadTileSet(31, "water", "water_corner_top_left", true);
        loadTileSet(32, "water", "water_corner_top_right", true);
        loadTileSet(33, "water", "water_corner_bottom_left", true);
        loadTileSet(34, "water", "water_corner_bottom_right", true);

        loadTileSet(46, "water", "island_right", true);
        loadTileSet(47, "water", "island_left", true);
        loadTileSet(48, "water", "island_bottom", true);
        loadTileSet(49, "water", "island_top", true);

        loadTileSet(41, "water", "island_corner_top_left", true);
        loadTileSet(42, "water", "island_corner_top_right", true);
        loadTileSet(43, "water", "island_corner_bottom_left", true);
        loadTileSet(44, "water", "island_corner_bottom_right", true);

        loadTileSet(53, "rock", "rock1", true);
        loadTileSet(54, "rock", "rock2", true);


    }
    public void loadTileSet(int index, String directory, String name, boolean collision)
    {
        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/"+ directory + "/" + name +".png")));
            tile[index].collision = collision;
            tile[index].type = directory;
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
                randomWave = rand.nextInt(10) + 10;
                chanceForWave = rand.nextInt(2);
                swapWater = 1;
            }
            animationFrame = 0;
        }
    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage render_tile;
        int scale = gamePanel.tileSize;
        for (int y = 0; y < mapHeight; y++)
        {
            for (int x = 0; x < mapWidth; x++)
            {
                int map_index = starting_area[y][x];
                int centerX = x * gamePanel.tileSize - player.x + player.centerX;
                int centerY = y * gamePanel.tileSize - player.y + player.centerY;

                if (map_index == 21)
                {
                    try
                    {
                        if(starting_area[y][x+chanceForWave] == 21)
                        {
                            starting_area[y][x+chanceForWave] = 20;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {

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

                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                        g2.drawImage(render_tile, centerX, centerY, scale,scale, null);
                    }

            }
        }
    }
}
