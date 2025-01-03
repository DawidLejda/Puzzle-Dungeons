package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ButtonQuantumState extends Object
{
    GamePanel gamePanel;
    int frame,swapSkin = 0;
    public ObjectImages[] buttonSwitch = new ObjectImages[4];
    public Boolean traversable = false;


    public ButtonQuantumState(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getButtonModel();
        x = 25;
        y = 15;
    }

    void getButtonModel()
    {
        try{
            for (int i = 0; i < 4; i++)
            {
                String path = "objects/BridgeButtons/QuantumStateSwitch".concat(Integer.toString(i+1)).concat(".png");
                buttonSwitch[i] = new ObjectImages();
                buttonSwitch[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }

    public void Update()
    {
        frame++;
        if (frame >= 80)
        {
            swapSkin++;
            if (swapSkin >= 6)
            {
                swapSkin = 0;
                frame = 0;
            }
        }
        traversable = gamePanel.bridgeLeft.swapSkin == 4 && gamePanel.bridgeRight.swapSkin == 4;
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
                if(swapSkin < 4)
                {
                    if(gamePanel.event.buttonState)
                    {
                        g2.drawImage(buttonSwitch[1].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                    }
                    else
                    {
                        g2.drawImage(buttonSwitch[0].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                    }
                }
                else
                {
                    g2.drawImage(buttonSwitch[swapSkin-2].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                }
            }
    }

}
