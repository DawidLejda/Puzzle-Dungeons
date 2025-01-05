package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class BridgeLeft extends Object
{
    GamePanel gamePanel;
    public ObjectImages[] bridgeLeft = new ObjectImages[9];
    public int swapSkin = 5;
    public Boolean[] ElevationLevel = new Boolean[9];

    int nonQuantumClicks = 0;

    public BridgeLeft(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getBridgeModel();
        x = 20;
        y = 14;
        Arrays.fill(ElevationLevel, false);
        ElevationLevel[5] = true;
    }


    void getBridgeModel()
    {
        try{
            for (int i = 0; i < 9; i++)
            {
                String path = "objects/Bridge/bridge_left".concat(Integer.toString(i+1)).concat(".png");
                bridgeLeft[i] = new ObjectImages();
                bridgeLeft[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }

    public void Update()
    {
        int i,n;

        if(!gamePanel.event.buttonState)
        {
            if (gamePanel.event.randomBridge == 1)
            {
                if (nonQuantumClicks >= 3)
                {
                    gamePanel.event.buttonState = true;
                    nonQuantumClicks = 0;
                }

                for (i = 0, n = ElevationLevel.length; i < n; i++)
                {
                    if (gamePanel.event.elevationUp)
                    {
                        nonQuantumClicks++;
                        if (ElevationLevel[i])
                        {
                            if (swapSkin < n && swapSkin + 1 < n)
                            {
                                ElevationLevel[i] = false;
                                ElevationLevel[i + 1] = true;
                                swapSkin = i + 1;
                            }
                            break;
                        }
                    }
                    else if (gamePanel.event.elevationDown)
                    {
                        nonQuantumClicks++;
                        if (ElevationLevel[i])
                        {
                            if (swapSkin - 1 >= 0 && swapSkin  >= 0)
                            {
                                ElevationLevel[i] = false;
                                ElevationLevel[i - 1] = true;
                                swapSkin = i - 1;
                            }
                            break;
                        }
                    }
                }
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

            g2.drawImage(bridgeLeft[swapSkin].image, centerX-10, centerY, gamePanel.tileSize+10, gamePanel.tileSize+10, null);
        }
    }


}
