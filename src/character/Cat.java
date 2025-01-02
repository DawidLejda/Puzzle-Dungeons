package character;

import main.GamePanel;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Cat extends Character
{
    GamePanel gamePanel;
    private int swapSkin = 1;
    private int swapIdle = 1;
    public object.ObjectImages[][] Sprite = new ObjectImages[4][5];
    public object.ObjectImages[] IdleSprite = new ObjectImages[5];

    public Cat(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        getCatModel();
        direction = "down";

        x = 15;
        y = 13;
        speed = 4;
    }

    void getCatModel()
    {
        try
        {
            String idle_path = "Cat/cat_idle.png";
            IdleSprite[0] = new ObjectImages();
            IdleSprite[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(idle_path)));
        }
        catch (IOException e)
        {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    public void Update()
    {

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
            g2.drawImage(IdleSprite[0].image, centerX, centerY, 40, 40, null);

        }
    }
}
