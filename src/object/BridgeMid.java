package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the middle part of a bridge object in the game.
 */
public class BridgeMid extends Object {
    GamePanel gamePanel;

    /**
     * Constructor for the BridgeMid class.
     *
     * @param gamePanel The GamePanel object associated with the bridge.
     */
    public BridgeMid(GamePanel gamePanel) {
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