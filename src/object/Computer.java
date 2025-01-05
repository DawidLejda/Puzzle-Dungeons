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
    public ObjectImages[] textWindow = new ObjectImages[6];
    public int computerInteraction = 0;
    int frame, frameEffects, swapSkin, swapEffect = 0;
    boolean stopEffect,finishedEffect = false;
    public boolean initializeTeleportation = false;
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
            for(int i = 0; i < 6; i++)
            {
                if(i < 2)
                {
                    path = "objects/Computer/textWindow".concat(Integer.toString(i+1)).concat(".png");
                    textWindow[i] = new ObjectImages();
                    textWindow[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                }
                else
                {
                    path = "objects/Computer/textWindowEffect".concat(Integer.toString(i-1)).concat(".png");

                }
                textWindow[i] = new ObjectImages();
                textWindow[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
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

            if(swapSkin >= 4)
            {
                swapSkin = 0;
            }
        }

        if(!finishedEffect && gamePanel.event.teleportMinigame)
        {
            frameEffects ++;
            if(frameEffects >30)
            {
                if (!stopEffect)
                {

                    swapEffect++;
                    if (swapEffect >= 6)
                    {
                        swapEffect = 0;
                    }
                }
                else
                {
                    swapEffect++;
                    if (swapEffect >= 3)
                    {
                        finishedEffect = true;
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
            g2.drawImage(image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
            if(!gamePanel.event.minigameFinished && gamePanel.event.teleportMinigame && gamePanel.event.renderMinigame)
            {
                g2.drawImage(computerEffects[swapEffect].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
            }
            else if(gamePanel.teleport.teleportRender && !finishedEffect)
            {
                stopEffect = true;
                g2.drawImage(computerEffects[8-swapEffect].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
            }
            else if(finishedEffect)
            {
                g2.drawImage(computerEffects[8].image, centerX, centerY, gamePanel.tileSize,gamePanel.tileSize, null);
            }

            if(!initializeTeleportation)
            {
                if(computerInteraction == 1)
                {
                    g2.drawImage(textWindow[swapSkin+2].image, centerX - 96, centerY - 128,gamePanel.tileSize*4,gamePanel.tileSize*2, null);
                    g2.drawImage(textWindow[0].image, centerX - 96, centerY - 128 ,gamePanel.tileSize*4,gamePanel.tileSize*2, null);
                }
                else if(computerInteraction > 1)
                {
                    g2.drawImage(textWindow[swapSkin+2].image, centerX - 96, centerY - 128,gamePanel.tileSize*4,gamePanel.tileSize*2, null);
                    g2.drawImage(textWindow[1].image, centerX - 96, centerY - 128 ,gamePanel.tileSize*4,gamePanel.tileSize*2, null);
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    if(computerInteraction == 2)
                    {
                        g2.drawString("True teleportation remains impossible.", centerX - 86, centerY - 100);
                        g2.drawString("The process transfers quantum data,", centerX - 86, centerY - 84);
                        g2.drawString("not physical matter, resulting in ", centerX - 86, centerY - 68);
                        g2.drawString("the body's disintegration. ", centerX - 86, centerY - 52);
                    }
                    else if(computerInteraction == 3)
                    {
                        g2.drawString("Only a copy of your ", centerX - 86, centerY - 100);
                        g2.drawString("consciousness is transmitted,", centerX - 86, centerY - 84);
                        g2.drawString("leaving the original lost forever.", centerX - 86, centerY - 68);
                        g2.drawString("Project failed, facility abandoned. ", centerX - 86, centerY - 52);
                    }
                    else
                    {
                        initializeTeleportation = true;
                    }
                }
            }

        }

    }
}
