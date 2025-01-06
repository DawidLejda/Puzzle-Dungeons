package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a generic object in the game. This class serves as a base class for
 * other specific object types.
 */
public class Object {
    public BufferedImage image; // The image representing the object
    public String name; // The name of the object
    public int x, y; // The x and y coordinates of the object on the game map
    public boolean collision = false; // Indicates if the object has collision

    /**
     * Draws the object on the screen.
     *
     * @param g2        The Graphics2D object used for drawing.
     * @param gamePanel The GamePanel object associated with the object.
     */
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int centerX, centerY;
        if (x < 200) {
            centerX = x * gamePanel.tileSize - gamePanel.player.x + gamePanel.player.centerX;
            centerY = y * gamePanel.tileSize - gamePanel.player.y + gamePanel.player.centerY;
        } else {
            centerX = x - gamePanel.player.x + gamePanel.player.centerX;
            centerY = y - gamePanel.player.y + gamePanel.player.centerY;
        }
        if (((x * gamePanel.tileSize) < (gamePanel.player.x + gamePanel.player.centerX + gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) < (gamePanel.player.y + gamePanel.player.centerY + gamePanel.tileSize)) &&
                ((x * gamePanel.tileSize) > (gamePanel.player.x - gamePanel.player.centerX - gamePanel.tileSize)) &&
                ((y * gamePanel.tileSize) > (gamePanel.player.y - gamePanel.player.centerY - gamePanel.tileSize))) {
            if (x == 21 && y == 14) {
                g2.drawImage(image, centerX, centerY, gamePanel.tileSize + 10, gamePanel.tileSize + 10, null);
            } else {
                g2.drawImage(image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}