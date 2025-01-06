package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a button that controls the quantum state of a bridge in the game.
 */
public class ButtonQuantumState extends Object {
    GamePanel gamePanel;
    int frame, swapSkin = 0;
    public ObjectImages[] buttonSwitch = new ObjectImages[4];
    public Boolean traversable = false;

    /**
     * Constructor for the ButtonQuantumState class.
     *
     * @param gamePanel The GamePanel object associated with the button.
     */
    public ButtonQuantumState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getButtonModel();
        x = 25;
        y = 15;
    }

    /**
     * Loads the button model images.
     */
    void getButtonModel() {
        try {
            for (int i = 0; i < 4; i++) {
                String path = "objects/BridgeButtons/QuantumStateSwitch".concat(Integer.toString(i + 1)).concat(".png");
                buttonSwitch[i] = new ObjectImages();
                buttonSwitch[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        } catch (IOException e) {
            System.out.println("Couldn't read tileset");
        }
    }

    /**
     * Updates the animation frame of the button and checks if the bridge is traversable.
     */
    public void Update() {
        frame++;
        if (frame >= 80) {
            swapSkin++;
            if (swapSkin >= 6) {
                swapSkin = 0;
                frame = 0;
            }
        }
        // Check if the bridge is in the traversable state (both left and right parts are at the correct elevation)
        traversable = gamePanel.bridgeLeft.swapSkin == 4 && gamePanel.bridgeRight.swapSkin == 4;
    }

    /**
     * Draws the button on the screen.
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
            if (swapSkin < 4) {
                if (gamePanel.event.buttonState) {
                    g2.drawImage(buttonSwitch[1].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                } else {
                    g2.drawImage(buttonSwitch[0].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            } else {
                g2.drawImage(buttonSwitch[swapSkin - 2].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}