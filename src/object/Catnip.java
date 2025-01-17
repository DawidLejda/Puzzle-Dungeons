package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a catnip object in the game.
 */
public class Catnip extends Object {
    GamePanel gamePanel;
    public ObjectImages[] sprite = new ObjectImages[2];
    public boolean Harvested, isHarvesting = false;
    int frame = 0;

    /**
     * Constructor for the Catnip class.
     *
     * @param gamePanel The GamePanel object associated with the catnip.
     */
    public Catnip(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getCatnipModel();
    }

    /**
     * Loads the catnip model images.
     */
    void getCatnipModel() {
        try {
            sprite[0] = new ObjectImages();
            sprite[1] = new ObjectImages();
            sprite[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Catnip/catnip.png")));
            sprite[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Catnip/catnip_flower.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the harvesting state of the catnip.
     */
    public void Update() {
        if (!Harvested) {
            if (isHarvesting) {
                frame++;
                if (frame >= 150) {
                    Harvested = true;
                    gamePanel.event.catnipsCount++;
                }
            } else {
                frame = 0;
            }
        }
    }

    /**
     * Draws the catnip on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void Draw(Graphics2D g2) {
        int centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
        int centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;

        if (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize))) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(sprite[0].image, centerX + 40, centerY, 40, 40, null);
            if (!Harvested) {
                g2.drawImage(sprite[1].image, centerX + 40, centerY, 40, 40, null);
            }
        }
    }
}