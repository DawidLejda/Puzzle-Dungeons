package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Teleport extends Object
{
    GamePanel gamePanel;
    public ObjectImages[] center = new ObjectImages[6];
    public ObjectImages[][] module = new ObjectImages[2][4];
    public ObjectImages[][] binaryBits= new ObjectImages[4][2];
    public boolean teleportRender = false;

    public Teleport(GamePanel gamepanel)
    {
        this.gamePanel = gamepanel;
        getTeleportModel();
        x = 11;
        y = 12;
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

        }
        catch(IOException e){
            System.out.println("Couldn't read tileset");
        }
    }



    public void Update()
    {

    }


    public void Draw(Graphics2D g2)
    {
        int bitSwitch = 0;

        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

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
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(center[0].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);

            g2.drawImage(module[bitSwitch][1].image, centerX + gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawImage(module[bitSwitch][0].image, centerX - gamePanel.tileSize, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawImage(module[bitSwitch][2].image, centerX, centerY - gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawImage(module[bitSwitch][3].image, centerX, centerY + gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
