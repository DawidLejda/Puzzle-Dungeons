package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the right part of a bridge object in the game.
 */
public class BridgeRight extends Object {
    GamePanel gamePanel;
    public ObjectImages[] bridgeRight = new ObjectImages[9];
    public int swapSkin = 2;
    public Boolean[] ElevationLevel = new Boolean[9];
    int nonQuantumClicks = 0;

    /**
     * Constructor for the BridgeRight class.
     *
     * @param gamePanel The GamePanel object associated with the bridge.
     */
    public BridgeRight(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getBridgeModel();
        x = 22;
        y = 14;
        Arrays.fill(ElevationLevel, false);
        ElevationLevel[2] = true;
    }

    /**
     * Loads the bridge model images.
     */
    void getBridgeModel() {
        try {
            for (int i = 0; i < 9; i++) {
                String path = "objects/Bridge/bridge_right".concat(Integer.toString(i + 1)).concat(".png");
                bridgeRight[i] = new ObjectImages();
                bridgeRight[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        } catch (IOException e) {
            System.out.println("Couldn't read tileset");
        }
    }

    /**
     * Updates the elevation level of the bridge.
     */
    public void Update() {
        int i, n;
        if (gamePanel.event.buttonState) {
            for (i = 0, n = ElevationLevel.length; i < n; i++) {
                if (gamePanel.event.elevationUp) {
                    if (ElevationLevel[i]) {
                        if (swapSkin + 1 != n &&
                                gamePanel.bridgeLeft.swapSkin + 1 != n) {

                            ElevationLevel[i] = false;
                            ElevationLevel[swapSkin + 1] = true;
                            swapSkin = swapSkin + 1;
                            gamePanel.bridgeLeft.swapSkin += 1;
                        }
                        break;
                    }
                } else if (gamePanel.event.elevationDown) {
                    if (ElevationLevel[i]) {
                        if (swapSkin - 1 >= 0 &&
                                gamePanel.bridgeLeft.swapSkin - 1 >= 0) {

                            ElevationLevel[i] = false;
                            ElevationLevel[swapSkin - 1] = true;
                            swapSkin = swapSkin - 1;
                            gamePanel.bridgeLeft.swapSkin -= 1;
                        }
                        break;
                    }
                }
            }
        } else {
            if (gamePanel.event.randomBridge == 0) {
                if (nonQuantumClicks >= 3) {
                    gamePanel.event.buttonState = true;
                    nonQuantumClicks = 0;
                }

                for (i = 0, n = ElevationLevel.length; i < n; i++) {
                    if (gamePanel.event.elevationUp) {
                        nonQuantumClicks++;
                        if (ElevationLevel[i]) {
                            if (swapSkin < n && swapSkin + 1 < n) {
                                ElevationLevel[i] = false;
                                ElevationLevel[swapSkin + 1] = true;
                                swapSkin += 1;
                            }
                            break;
                        }
                    } else if (gamePanel.event.elevationDown) {
                        nonQuantumClicks++;
                        if (ElevationLevel[i]) {
                            if (swapSkin - 1 >= 0) {
                                ElevationLevel[i] = false;
                                ElevationLevel[swapSkin - 1] = true;
                                swapSkin -= 1;
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * Draws the bridge on the screen.
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

            g2.drawImage(bridgeRight[swapSkin].image, centerX + 10, centerY, gamePanel.tileSize + 10, gamePanel.tileSize + 10, null);
        }
    }
}