package map;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Map extends bitmap
{
    GamePanel gamePanel;
    KeyHandler pressedKey;
    public Map(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
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
        }
        catch (IOException e)
        {
        System.out.println("Couldn't read tileset");
        gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {

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

                g2.drawImage(tile, x * gamePanel.tileSize, y * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
