package character;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Character
{

    GamePanel gamePanel;
    KeyHandler pressedKey;
    boolean swapSkin = false;

    public Player(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();

        x = 100;
        y = 100;
        speed = 4;
        getPlayerModel();
    }

    public void getPlayerModel()
    {
        try
        {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/player_down.1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/player_down.2.png")));

        } catch (IOException e) {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {
        if (pressedKey.left)
        {
            x -= speed;
        }
        else if (pressedKey.right)
        {
            x += speed;
        }
        else if (pressedKey.up)
        {
            y -= speed;
        }
        else if (pressedKey.down)
        {
            y += speed;
        }

    }

    public void Draw(Graphics2D g2)
    {
        BufferedImage image = null;

        // Indicates how often skin is changed
        if (gamePanel.frameCount % 15 == 0)
        {
            if(swapSkin)
            {
                swapSkin = false;
            }
            else
            {
                swapSkin = true;
            }
        }

        if(swapSkin)
        {
            image = down2;
        }
        else
        {
            image = down1;
        }


        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
