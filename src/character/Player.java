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
            down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/player_down.png")));

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
        BufferedImage image = down;
        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
