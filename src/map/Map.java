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

    public boolean mapSwap = true;
    private int animationFrame = 1;
    private int swapWater = 1;
    Random rand = new Random();
    int randomWave = 16;
    int chanceForWave = 0;
    int waveTime = 0;

    public Tile[] islandTile = new Tile[60];
    public Tile[] bunkerTile = new Tile[20];

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

        //***********************************************************************************************

        loadBunkerTile(0,"ground",false);
        loadBunkerTile(1,"wallLeft",true);
        loadBunkerTile(2,"wallRight",true);
        loadBunkerTile(3,"wallTop",true);
        loadBunkerTile(4,"wallBottom",true);
        loadBunkerTile(5,"wallMaterial",true);

        loadBunkerTile(6,"ground_catnip",false);
        loadBunkerTile(7,"wall_vent",true);
        loadBunkerTile(8,"wallMaterial2",true);
        //1l dolny 2p dolny 3l gorny
        loadBunkerTile(9,"corner1",true);
        loadBunkerTile(10,"corner2",true);
        loadBunkerTile(11,"corner3",true);
        loadBunkerTile(12,"corner4",true);
        loadBunkerTile(13,"corner5",true);
        loadBunkerTile(14,"corner6",true);
    }


    public void loadTileSet(int index, String directory, String name, boolean collision)
    {
        try
        {
            islandTile[index] = new Tile();
            islandTile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/"+ directory + "/" + name +".png")));
            islandTile[index].collision = collision;
            islandTile[index].type = directory;
        }
        catch (IOException e)
        {
            System.out.println("Couldn't read tileset");
            gamePanel.gameRunning = false;
        }
    }
    public void loadBunkerTile(int index, String name, boolean collision)
    {
        try
        {
            bunkerTile[index] = new Tile();
            bunkerTile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tileset/bunker/" + name +".png")));
            bunkerTile[index].collision = collision;
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

        if(!mapSwap)
        {
            for (int y = 0; y < islandHeight; y++)
            {
                for (int x = 0; x < islandWidth; x++)
                {
                    int map_index = island[y][x];
                    int centerX = x * gamePanel.tileSize - player.x + player.centerX;
                    int centerY = y * gamePanel.tileSize - player.y + player.centerY;

                    if (map_index == 21)
                    {
                        try
                        {
                            if (island[y][x + chanceForWave] == 21)
                            {
                                island[y][x + chanceForWave] = 20;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored)
                        {}


                        if (chanceForWave != 0)
                        {
                            if (swapWater == 1)
                            {
                                render_tile = islandTile[1].image;
                            }
                            else if (swapWater == 2)
                            {
                                render_tile = islandTile[2].image;
                            }
                            else if (swapWater == 3)
                            {
                                render_tile = islandTile[3].image;
                            }
                            else if (swapWater == 4)
                            {
                                render_tile = islandTile[4].image;
                            } else if (swapWater == 5)
                            {
                                render_tile = islandTile[5].image;
                            }
                            else if (swapWater == 6)
                            {
                                render_tile = islandTile[6].image;
                            }
                            else
                            {
                                render_tile = islandTile[7].image;
                            }
                        }
                        else
                        {
                            render_tile = islandTile[7].image;
                        }

                    }
                    else if (map_index == 20)
                    {
                        render_tile = islandTile[7].image;
                        waveTime++;
                        if (waveTime > 160)
                        {
                            waveTime = 0;
                            island[y][x] = 21;
                        }
                    }
                    else
                    {
                        render_tile = islandTile[map_index].image;
                    }

                    if (((x * gamePanel.tileSize) < (player.x + player.centerX + gamePanel.tileSize)) &&
                            ((y * gamePanel.tileSize) < (player.y + player.centerY + gamePanel.tileSize)) &&
                            ((x * gamePanel.tileSize) > (player.x - player.centerX - gamePanel.tileSize)) &&
                            ((y * gamePanel.tileSize) > (player.y - player.centerY - gamePanel.tileSize))) {
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                        g2.drawImage(render_tile, centerX, centerY, scale, scale, null);
                    }

                }
            }
        }
        else
        {
            for (int y = 0; y < bunkerHeight; y++)
            {
                for (int x = 0; x < bunkerWidth; x++)
                {
                    int map_index = bunker[y][x];
                    int centerX = x * gamePanel.tileSize - player.x + player.centerX;
                    int centerY = y * gamePanel.tileSize - player.y + player.centerY;
                    render_tile = bunkerTile[map_index].image;

                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                    g2.drawImage(render_tile, centerX, centerY, scale, scale, null);
                }
            }
        }
    }
}
