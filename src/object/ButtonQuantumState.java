package object;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ButtonQuantumState extends Object
{
    GamePanel gamePanel;
    KeyHandler pressedKey;
    int frame,swapSkin;
    public ObjectImages[] buttonSwitch = new ObjectImages[4];

    public ButtonQuantumState(GamePanel gamePanel,KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getButtonModel();
        x = 25;
        y = 11;
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
        if (frame >= 15)
        {
            swapSkin++;
            if (swapSkin >= 4)
            {
                swapSkin = 0;
                frame = 0;
            }
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
                if(swapSkin == 0)
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
                    g2.drawImage(buttonSwitch[swapSkin].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                }

            }

    }

}
