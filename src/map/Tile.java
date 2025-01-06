package map;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile in a game map.
 */
public class Tile {
    public BufferedImage image; // The image used to display the tile
    public boolean collision; // Indicates whether the tile has collision
    public String type; // The type of the tile (e.g., "grass", "wall")
}