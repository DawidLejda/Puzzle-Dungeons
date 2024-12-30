package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AirVent extends Object
{
    GamePanel gamePanel;
    public ObjectImages[] airvent_left = new ObjectImages[9];
    public ObjectImages[] airvent_middle = new ObjectImages[6];
    public ObjectImages[] airvent_right = new ObjectImages[1];
    private int frame,swapSkin = 0;

    public AirVent(GamePanel gamepanel)
    {
        this.gamePanel = gamepanel;
        getAirventModel();
        name = "airvent";
        x = 13;
        y = 28;
    }

    void getAirventModel()
    {
        int n = 9;
        try{
            for (int j = 0; j < 2; j++)
            {
                for (int i = 0; i < n; i++)
                {
                    String path = "objects/airvent/vent".concat(Integer.toString(j+1)).concat("_").concat(Integer.toString(i+1)).concat(".png");
                    if(j == 0)
                    {
                        airvent_left[i] = new ObjectImages();
                        airvent_left[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                    }
                    else
                    {
                        airvent_middle[i] = new ObjectImages();
                        airvent_middle[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                    }
                }
                n-=3;
            }
            airvent_right[0] = new ObjectImages();
            airvent_right[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/airvent/vent3.png")));
        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }

    public void Update()
    {
        frame++;
        if (frame >= 10)
        {
            swapSkin++;
            if (swapSkin >= 9)
            {
                swapSkin = 0;
            }
            frame = 0;
        }

    }

    public void Draw(Graphics2D g2, GamePanel gamePanel)
    {
        BufferedImage left = airvent_left[swapSkin].image;
        BufferedImage middle = airvent_middle[0].image;
        BufferedImage right = airvent_right[0].image;

        if(swapSkin >= 4)
        {
            middle = airvent_middle[swapSkin - 4].image;
        }

        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

        if      ((((x+2) * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                (((x+2)  * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)) ||

                (((x) * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                (((x)  * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))

        {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(left, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawImage(middle, centerX+ gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawImage(right, centerX + 2* gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
