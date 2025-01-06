package character;

/**
 * This class represents a generic character in the game.
 * It serves as a base class for other specific character types,
 * such as the player or the cat.
 */
public class Character {
    public int x; // The x-coordinate of the character on the map
    public int y; // The y-coordinate of the character on the map
    public int speed; // The movement speed of the character
    public boolean collision; // Indicates if the character is colliding with an object
    public boolean visibility = true; // Indicates if the character is visible on the screen
    public String direction = "down"; // The current direction the character is facing
}