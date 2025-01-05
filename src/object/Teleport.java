package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Teleport extends Object
{
    GamePanel gamePanel;
    Random rand = new Random();
    public ObjectImages[] center = new ObjectImages[6];
    public ObjectImages[] desintegrateIMG = new ObjectImages[7];
    public ObjectImages[][] module = new ObjectImages[2][4];
    public ObjectImages[][] binaryBits= new ObjectImages[4][2];
    int [][] randomBits = new int[4][20];
    int [] bitSwitch = new int [4];
    public boolean teleportRender,teleportReady, playerDesintegration,gameEND = false;
    int frame, wait, frameCenter, swapSkin, desintegrationSkin, swapCenter = 0;

    public Teleport(GamePanel gamepanel)
    {
        this.gamePanel = gamepanel;
        getTeleportModel();
        x = 11;
        y = 12;

        for (int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                randomBits[i][j] = rand.nextInt(3);
            }
        }
    }

    void getTeleportModel()
    {
        String path;
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/airvent/thrown.png")));
            //  j0 left, j1 right, j2 top, j3 down
            for (int j = 0; j < 4; j++)
            {
                for (int i = 0; i < 2; i++)
                {
                    path = "objects/teleport/binary/binary".concat(Integer.toString(j)).concat("_").concat(Integer.toString(i)).concat(".png");
                    binaryBits[j][i] = new ObjectImages();
                    binaryBits[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                }
            }
            for (int j = 0; j < 2; j++)
            {
                for (int i = 0; i < 4; i++)
                {
                    path = "objects/teleport/teleportModules/module".concat(Integer.toString(j)).concat("_").concat(Integer.toString(i+1)).concat(".png");
                    module[j][i] = new ObjectImages();
                    module[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                }
            }

            center[0] = new ObjectImages();
            center[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/teleport/center.png")));
            for(int i = 0 ; i < 5; i++)
            {
                path = "objects/teleport/renderTeleport".concat(Integer.toString(i+1)).concat(".png");
                center[i+1] = new ObjectImages();
                center[i+1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }

            for(int i = 0; i < 7; i++)
            {
                path = "player/desintegrate/desintegrate".concat(Integer.toString(i+1)).concat(".png");
                desintegrateIMG[i] = new ObjectImages();
                desintegrateIMG[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }



    public void Update()
    {
        frame++;
        if(frame > 10)
        {
            frame = 0;
            swapSkin++;

            if(swapSkin >= 19)
            {
                swapSkin = 0;
            }
        }

        if(!teleportReady)
        {
            if((gamePanel.player.x+30)/gamePanel.tileSize == x &&
            gamePanel.player.y/gamePanel.tileSize == y-1)
            {
                frameCenter++;
                if (frameCenter > 150)
                {
                    teleportReady = true;
                    frameCenter = 0;
                }
            }
            else
            {
                frameCenter = 0;
            }
        }
        else
        {
            gamePanel.player.collision = true;

            frameCenter++;
            if(frameCenter >= 7)
            {
                frameCenter = 0;
                swapCenter++;
                if(swapCenter >= 5)
                {
                    swapCenter = 0;
                }
            }

            if(!playerDesintegration)
            {
                wait++;
                if(wait > 120)
                {
                    playerDesintegration = true;
                    desintegrationSkin = 0;
                    wait = 0;
                }
            }
            else
            {
                Desintegrate();
            }
        }
    }

    void Desintegrate()
    {
        wait++;
        if (wait > 18)
        {
            wait = 0;
            desintegrationSkin++;
            if(desintegrationSkin >= 7)
            {
                gameEND = true;
            }
        }
    }


    public void Draw(Graphics2D g2)
    {
        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);



        if      ((((x+2) * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                (((x+2)  * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                (((y) * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)) ||

                (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                        (((y+2) * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                        ((x  * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                        (((y+2) * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize)) ||

                (((x) * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                        (((y) * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                        (((x)  * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                        (((y) * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize))))

        {

            g2.drawImage(center[0].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);

            if(!teleportRender)
            {
                g2.drawImage(module[0][1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[0][0].image, centerX - gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[0][2].image, centerX, centerY - gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[0][3].image, centerX, centerY + gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            else
            {
                Arrays.fill(bitSwitch,0);

                for(int i = 0; i < 4; i++)
                {
                    if(randomBits[i][swapSkin] != 2)
                    {
                        bitSwitch[i] = 1;
                    }
                }
                g2.drawImage(module[bitSwitch[1]][1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[bitSwitch[0]][0].image, centerX - gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[bitSwitch[2]][2].image, centerX, centerY - gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
                g2.drawImage(module[bitSwitch[3]][3].image, centerX, centerY + gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

                if(randomBits[1][swapSkin] != 2)
                {
                    g2.drawImage(binaryBits[1][randomBits[1][swapSkin]].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
                if(randomBits[0][swapSkin] != 2)
                {
                    g2.drawImage(binaryBits[0][randomBits[0][swapSkin]].image, centerX - gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
                if(randomBits[2][swapSkin] != 2)
                {
                    g2.drawImage(binaryBits[2][randomBits[2][swapSkin]].image, centerX, centerY - gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
                }
                if(randomBits[3][swapSkin] != 2)
                {
                    g2.drawImage(binaryBits[3][randomBits[3][swapSkin]].image, centerX, centerY + gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
                }

                if(teleportReady)
                {
                    g2.drawImage(center[swapCenter+1].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
            if(playerDesintegration && desintegrationSkin < 7)
            {
                g2.drawImage(desintegrateIMG[desintegrationSkin].image, gamePanel.player.centerX, gamePanel.player.centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            if(gameEND)
            {
                g2.setColor(Color.white);
                g2.setFont(new Font("Arial", Font.BOLD, 70));
                g2.drawString("Game Over", gamePanel.player.centerX-180, gamePanel.player.centerY);
                g2.setFont(new Font("Arial", Font.PLAIN, 15));
                g2.drawString("press space space to return to main menu", gamePanel.player.centerX-100, gamePanel.player.centerY+60);
            }
        }
    }
}
