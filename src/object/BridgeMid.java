package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BridgeMid extends Object{
    GamePanel gamePanel;

    public BridgeMid(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        x = 21;
        y = 14;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Bridge/bridge_mid.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
