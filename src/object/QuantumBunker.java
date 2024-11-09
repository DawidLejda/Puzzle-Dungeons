package object;


import main.GamePanel;

import java.awt.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.abs;

public class QuantumBunker extends Object
{
    GamePanel gamePanel;
    public Object[] bunker = new Object[12];
    Random rand = new Random();
    public boolean renderBunker;
    private int frame,teleport_frame = 0;
    public int swapTeleport = 2;


    public QuantumBunker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getBunkerModel();
        name = "bunker";
    }

    public void getBunkerModel()
    {
        try{
            int set = 0;
            int pair = 0;
            for (int i = 0; i < 12; i++)
            {
                pair++;
                String path = "objects/QuantumBunker/teleportBunker".concat(Integer.toString(set)).concat("_").concat(Integer.toString(pair)).concat(".png");
                bunker[i] = new Object();
                bunker[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                if(pair >= 2)
                {
                    set++;
                    pair = 0;
                }
            }
        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }

    public void getPseudoRandomCoordinates()
    {
        do {
            x = (rand.nextInt(20) + 3);
            y = (rand.nextInt(20) + 3);
        } while (gamePanel.map.starting_area[y][x] != 11 && gamePanel.map.starting_area[y][x + 1] != 11);

        renderBunker =
                gamePanel.map.starting_area[y][x] == 11 &&
                gamePanel.map.starting_area[y][x + 1] == 11 &&
                abs(gamePanel.player.x / gamePanel.tileSize - x) > 2 &&
                abs(gamePanel.player.x / gamePanel.tileSize - (x + 1)) > 2 &&
                abs(gamePanel.player.y / gamePanel.tileSize - y) > 2;
    }

    public void Update()
    {
        frame++;
        if(frame >= 50)
        {
            getPseudoRandomCoordinates();
            frame = 0;
            swapTeleport = 2;
        }

        if(frame > 15){
            teleport_frame++;
            if(teleport_frame >= 4)
            {
                if (swapTeleport >= 10)
                {
                    swapTeleport = 0;
                }
                if(swapTeleport != 0)
                {
                    swapTeleport += 2;
                }

                teleport_frame = 0;
            }
        }
    }

    public void DrawStanding(Graphics2D g2, GamePanel gamePanel) {
        if(renderBunker)
        {
            int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
            int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

            if      (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                    ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                    ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                    ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))
            {

                if(frame <= 15)
                {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                    g2.drawImage(bunker[0].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                    g2.drawImage(bunker[1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                }
                else
                {
                    if(swapTeleport != 0)
                    {
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                        g2.drawImage(bunker[swapTeleport].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                        g2.drawImage(bunker[swapTeleport + 1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
                    }
                }
            }
        }
    }
}
