package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Computer extends Object
{
    GamePanel gamePanel;
    public ObjectImages[] computerEffects = new ObjectImages[9];
    public Computer(GamePanel gamepanel)
    {
        this.gamePanel = gamepanel;
        getComputerModel();
        x = 17;
        y = 11;
    }

    void getComputerModel()
    {
        String path;
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Computer/computer1.png")));
            for(int i = 0 ; i < 9; i++)
            {
                path = "objects/Computer/computerEffects".concat(Integer.toString(i+1)).concat(".png");
                computerEffects[i] = new ObjectImages();
                computerEffects[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }

        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }


    public void Draw(Graphics2D g2)
    {

        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

        if      (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))
        {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
            if(gamePanel.teleport.teleportRender)
            {
                //renderEffect
            }
        }

    }
}
