package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class CatnipTrail extends Object
{
    GamePanel gamePanel;
    public int [] catnipPathX = new int[50];
    public int [] catnipPathY = new int[50];
    public int catnipSteps = 0;
    public int last_index = 0;
    public boolean stopTrailPlacement = false;
    Random rand = new Random();
    public ObjectImages [] trailsIMG = new ObjectImages[2];
    public ObjectImages [] trailing = new ObjectImages[3];
    public int [] randomTrail = new int[50];

    int frame,swapSkin = 0;
    public CatnipTrail(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        catTrailSetup();
    }


    public void catTrailSetup()
    {
        Arrays.fill(catnipPathX,0);
        Arrays.fill(catnipPathY,0);

        for(int i = 0; i < 50; i++)
        {
            randomTrail[i] = (rand.nextInt(2));
        }
        try
        {
            trailsIMG[0] = new ObjectImages();
            trailsIMG[1] = new ObjectImages();
            trailsIMG[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Catnip/trail1.png")));
            trailsIMG[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Catnip/trail2.png")));

            for(int i = 0; i < 3; i++)
            {
                String path = "objects/Catnip/trailing".concat(Integer.toString(i + 1)).concat(".png");
                trailing[i] = new ObjectImages();
                trailing[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public void Update()
    {
        if(!stopTrailPlacement)
        {
            for (int i = 0; i < catnipSteps; i++)
            {
                if (gamePanel.catnipTrails[i] != null)
                {
                    if(catnipPathX[i] == 14 && catnipPathY[i] == 29)
                    {
                        stopTrailPlacement = true;
                        catnipPathX[i+1] = 0;
                        catnipPathY[i+1] = 0;
                        last_index = i;
                        break;
                    }
                }
            }
        }

        if((catnipPathY[0] != 0 && gamePanel.event.catnipsCount > 0 && !stopTrailPlacement))
        {
            frame++;
            if (frame >=30)
            {
                swapSkin++;
                if(swapSkin >= 3)
                {
                    swapSkin = 0;
                }
                frame = 0;
            }
        }

    }


    public void Draw(Graphics2D g2)
    {
        int posX = gamePanel.player.centerX + gamePanel.tileSize/3;
        int posY = gamePanel.player.centerY - gamePanel.tileSize/2;
        if((catnipPathY[0] != 0 && gamePanel.event.catnipsCount > 0 && !stopTrailPlacement))
        {
            g2.drawImage(trailing[swapSkin].image, posX, posY, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
        }
    }
}
