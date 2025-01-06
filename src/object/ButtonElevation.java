package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a button that controls the elevation of a bridge in the game.
 */
public class ButtonElevation extends Object {
    GamePanel gamePanel;
    public ObjectImages[] buttonElevation = new ObjectImages[4];
    int frame, swapSkin = 0;

    /**
     * Constructor for the ButtonElevation class.
     *
     * @param gamePanel The GamePanel object associated with the button.
     * @param posX      The x-coordinate of the button on the game map.
     */
    public ButtonElevation(GamePanel gamePanel, int posX) {
        this.gamePanel = gamePanel;
        getButtonModel();
        y = 12;
        x = posX;
    }

    /**
     * Loads the button model images.
     */
    void getButtonModel() {
        String path;
        try {
            path = "objects/BridgeButtons/BridgeDown.png";
            buttonElevation[0] = new ObjectImages();
            buttonElevation[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));

            path = "objects/BridgeButtons/BridgeUp.png";
            buttonElevation[1] = new ObjectImages();
            buttonElevation[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));

            path = "objects/BridgeButtons/QuantumStateSwitch3.png";
            buttonElevation[2] = new ObjectImages();
            buttonElevation[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));

            path = "objects/BridgeButtons/QuantumStateSwitch4.png";
            buttonElevation[3] = new ObjectImages();
            buttonElevation[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
        } catch (IOException e) {
            System.out.println("Couldn't read tileset");
        }
    }

    /**
     * Updates the animation frame of the button.
     */
    public void Update() {
        frame++;
        if (frame >= 100) {
            swapSkin++;
            if (swapSkin >= 6) {
                swapSkin = 0;
                frame = 0;
            }
        }
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
                if (x == 26) {
                    g2.drawImage(buttonElevation[1].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                } else {
                    g2.drawImage(buttonElevation[0].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            } else {
                g2.drawImage(buttonElevation[swapSkin - 2].image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

        }
    }
}