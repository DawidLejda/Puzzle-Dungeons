package map;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class bitmap
{
    GamePanel gamePanel;
    public BufferedImage grass1, grass2, grass3, grass4, grass5;
    public BufferedImage water1, water2, water3, water4, water5, water6;
    public BufferedImage water_deco1, water_deco2, water_deco3, water_deco4;
    public BufferedImage water_left, water_right, water_top, water_bottom;

    /*
    Brzegi wody
    26 - lewy
    27 - prawy
    28 - dolny
    29 - gorny
    */

    int[][] starting_area =
            new int[][]{
                    {11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 13, 13, 13, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {11, 11, 12, 12, 12, 11, 11, 11, 11, 11, 12, 13, 13, 13, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 13, 13, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {12, 12, 12, 13, 12, 12, 11, 11, 11, 11, 13, 13, 14, 13, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 13, 13, 14, 13, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {13, 13, 12, 13, 13, 12, 11, 11, 11, 11, 13, 29, 29, 29, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 13, 14, 14, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {14, 13, 13, 14, 13, 12, 11, 11, 11, 12, 26, 21, 21, 21, 27, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 13, 14, 15, 14, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {14, 14, 13, 14, 13, 12, 11, 11, 11, 12, 26, 21, 22, 24, 27, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 13, 14, 15, 15, 15, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {15, 14, 14, 15, 14, 12, 11, 11, 11, 13, 26, 21, 21, 21, 27, 11, 11, 11, 11, 11, 11, 11, 12, 13, 13, 14, 15, 15, 15, 14, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {15, 15, 14, 15, 14, 13, 11, 11, 11, 13, 26, 23, 21, 21, 27, 11, 11, 11, 11, 11, 11, 11, 12, 14, 14, 15, 15, 15, 14, 13, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {14, 15, 15, 11, 14, 13, 12, 11, 11, 13, 26, 21, 21, 21, 27, 12, 11, 11, 11, 11, 11, 12, 13, 14, 15, 15, 14, 14, 13, 13, 13, 12, 11, 11, 11, 11, 11, 11, 11, 11},
                    {13, 14, 15, 15, 14, 13, 12, 11, 12, 13, 14, 28, 28, 28, 13, 12, 11, 11, 11, 11, 11, 13, 14, 14, 14, 14, 14, 13, 13, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11},
                    {13, 14, 14, 14, 13, 12, 11, 11, 12, 13, 14, 14, 13, 13, 13, 12, 11, 11, 11, 11, 11, 13, 14, 14, 13, 13, 13, 13, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {12, 13, 14, 14, 13, 12, 11, 11, 12, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 11, 12, 13, 13, 13, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {12, 13, 13, 13, 13, 12, 11, 11, 12, 13, 13, 12, 12, 11, 11, 11, 11, 11, 11, 12, 13, 13, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {11, 12, 13, 13, 12, 12, 11, 11, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {11, 11, 12, 12, 12, 11, 11, 11, 11, 12, 12, 11, 11, 11, 11, 11, 11, 11, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {11, 11, 12, 12, 12, 11, 11, 11, 11, 11, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11},
                    {11, 11, 11, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11}
            };

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
}

