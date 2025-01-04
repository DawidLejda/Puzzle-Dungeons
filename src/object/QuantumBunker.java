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
    public ObjectImages[] bunker = new ObjectImages[12];
    Random rand = new Random();
    public boolean renderBunker,materialize;
    private int frame,reverseFrame,teleport_frame = 0;
    public int swapTeleport = 2;
    boolean incorrectGround,reverse;
    public int []last_coordinates = new int[2];
    public QuantumBunker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getBunkerModel();
        name = "bunker";
        collision = true;
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
                bunker[i] = new ObjectImages();
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
        incorrectGround = false;
        do {
            x = (rand.nextInt(17) + 4);
            y = (rand.nextInt(15) + 15);
            if(gamePanel.map.starting_area[y][x] == 11 && gamePanel.map.starting_area[y][x + 1] == 11)
            {
                if((x != gamePanel.airvent.x && y != gamePanel.airvent.y) &&
                        (x != gamePanel.airvent.x+1 && y != gamePanel.airvent.y+1)&&
                        (x != gamePanel.airvent.x+2 && y != gamePanel.airvent.y+2))
                {
                    incorrectGround = true;
                }
                for (int i = 0, n = gamePanel.trees[0].length; i < n; i++)
                {
                    incorrectGround = x != gamePanel.trees[0][i].x && y != gamePanel.trees[0][i].y;
                }
            }
        } while (!incorrectGround );

        renderBunker =
                gamePanel.map.starting_area[y][x] == 11 &&
                gamePanel.map.starting_area[y][x + 1] == 11 &&
                abs(gamePanel.player.x / gamePanel.tileSize - x) > 2 &&
                abs(gamePanel.player.x / gamePanel.tileSize - (x + 1)) > 2 &&
                abs(gamePanel.player.y / gamePanel.tileSize - y) > 2;
    }

    public void Update()
    {
        if(!materialize)
        {
            frame++;
            if (frame >= 50 && !gamePanel.event.bunkerStop)
            {

                getPseudoRandomCoordinates();
                frame = 0;
                swapTeleport = 2;
            }

            if (frame > 15) {
                teleport_frame++;
                if (teleport_frame >= 4) {
                    if (swapTeleport >= 10) {
                        swapTeleport = 0;
                    }
                    if (swapTeleport != 0) {
                        swapTeleport += 2;
                    }

                    teleport_frame = 0;
                }
            }
        }
        else
        {
            if(reverse)
            {
                reverseFrame++;
                if (reverseFrame > 15)
                {
                    swapTeleport--;
                    reverseFrame = 0;
                    if (swapTeleport <= 0)
                    {
                        reverse = false;
                    }
                }
            }
            if (x != last_coordinates[0])
            {
                frame = 0;
                renderBunker = true;
                x = last_coordinates[0];
                y = last_coordinates[1];
            }
        }
    }


    public void DrawStanding(Graphics2D g2, GamePanel gamePanel)
    {
        if(renderBunker)
        {
            int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
            int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

            if      (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                    ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                    ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                    ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)))
            {
                if(!materialize)
                {
                    if (frame <= 15 || gamePanel.event.bunkerStop)
                    {
                        last_coordinates[0] = x;
                        last_coordinates[1] = y;

                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                        g2.drawImage(bunker[0].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                        g2.drawImage(bunker[1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                    } else {
                        if (swapTeleport != 0) {
                            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                            g2.drawImage(bunker[swapTeleport].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                            g2.drawImage(bunker[swapTeleport + 1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                        }
                    }
                }
                else
                {
                    if(swapTeleport > 0)
                    {
                        reverse = true;
                    }
                    g2.drawImage(bunker[swapTeleport].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                    g2.drawImage(bunker[swapTeleport + 1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
        }
    }
}
