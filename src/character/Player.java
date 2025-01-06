package character;

import main.GamePanel;
import main.KeyHandler;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the player character in the game.
 */
public class Player extends Character {
    GamePanel gamePanel;
    KeyHandler pressedKey;
    public boolean playerMoving; // Indicates if the player is currently moving
    private int swapSkin = 1; // Index for the walking animation sprite sheet
    private int swapIdle = 1; // Index for the idle animation sprite sheet
    private int animationFrame = 1; // Counter for walking animation frames
    private int animationIdle = 1; // Counter for idle animation frames
    public ObjectImages[][] Sprite = new ObjectImages[4][6]; // Stores the walking animation sprites
    public ObjectImages[][] IdleSprite = new ObjectImages[4][5]; // Stores the idle animation sprites
    public final int centerX; // X-coordinate of the player's center on the screen
    public final int centerY; // Y-coordinate of the player's center on the screen

    /**
     * Constructor for the Player class.
     *
     * @param gamePanel The GamePanel object associated with the player.
     * @param pressedKey The KeyHandler object used for player input.
     */
    public Player(GamePanel gamePanel, KeyHandler pressedKey) {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();
        direction = "down";
        centerX = (gamePanel.width / 2) - (gamePanel.tileSize / 2);
        centerY = (gamePanel.height / 2) - (gamePanel.tileSize / 2);

        x = 35 * gamePanel.tileSize;
        y = 12 * gamePanel.tileSize;
        speed = 4;
    }

    /**
     * Loads the player character model images.
     */
    public void getPlayerModel() {
        try {
            String direction;
            for (int j = 0; j < 4; j++) {
                direction = switch (j) {
                    case 0 -> "down";
                    case 1 -> "up";
                    case 2 -> "left";
                    default -> "right";
                };
                for (int i = 0; i < 6; i++) {
                    String path = "player/move/".concat(direction).concat(Integer.toString(i + 1)).concat(".png");
                    Sprite[j][i] = new ObjectImages();
                    Sprite[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                    if (i < 5) {
                        String idle_path = "player/idle/idle_".concat(direction).concat(Integer.toString(i + 1)).concat(".png");
                        IdleSprite[j][i] = new ObjectImages();
                        IdleSprite[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(idle_path)));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }

    /**
     * Checks for collisions with tiles and objects in the game.
     */
    void CollisionCheck() {
        if (!gamePanel.teleport.teleportReady) {
            visibility = true;
            collision = false;
            gamePanel.collisionChecker.CheckBridgeCollision(this);
            gamePanel.collisionChecker.CheckTileCollision(this);
            gamePanel.collisionChecker.CheckObjectVisibility(this);
        }
    }

    /**
     * Updates the player's state, including movement, animation, and collision checks.
     */
    public void Update() {
        if (pressedKey.left) {
            direction = "left";
            animationFrame++;
            playerMoving = true;
        } else if (pressedKey.right) {
            direction = "right";
            animationFrame++;
            playerMoving = true;
        } else if (pressedKey.up) {
            direction = "up";
            animationFrame++;
            playerMoving = true;
        } else if (pressedKey.down) {
            direction = "down";
            animationFrame++;
            playerMoving = true;
        } else if (pressedKey.use) {
            direction = pressedKey.previousKey;
            playerMoving = false;
            animationIdle++;
        } else {
            animationIdle++;
            playerMoving = false;
        }

        CollisionCheck();

        // Move the player if there is no collision
        if (!collision) {
            if (pressedKey.left) {
                x -= speed;
            } else if (pressedKey.right) {
                x += speed;
            } else if (pressedKey.up) {
                y -= speed;
            } else if (pressedKey.down) {
                y += speed;
            }
        }

        // Update walking animation
        if (playerMoving) {
            if (animationFrame >= 5) {
                swapSkin++;
                if (swapSkin > 5) {
                    swapSkin = 1;
                }
                animationFrame = 0;
            }
        } else {
            // Update idle animation
            if (animationIdle >= 12) {
                swapIdle++;
                if (swapIdle > 4) {
                    swapIdle = 1;
                }
                animationIdle = 0;
            }
        }
    }

    /**
     * Draws the player on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void Draw(Graphics2D g2) {
        BufferedImage image = null;

        if (playerMoving) {
            // Select the appropriate walking animation sprite based on the direction
            image = switch (pressedKey.lastPressedKey) {
                case "down" -> Sprite[0][swapSkin].image;
                case "up" -> Sprite[1][swapSkin].image;
                case "left" -> Sprite[2][swapSkin].image;
                case "right" -> Sprite[3][swapSkin].image;
                default -> image;
            };
        } else {
            // Select the appropriate idle animation sprite based on the last pressed or released key
            if (pressedKey.lastReleasedKey != null) {
                image = switch (pressedKey.lastReleasedKey) {
                    case "down" -> IdleSprite[0][swapIdle].image;
                    case "up" -> IdleSprite[1][swapIdle].image;
                    case "use" -> IdleSprite[1][swapIdle].image;
                    case "left" -> IdleSprite[2][swapIdle].image;
                    case "right" -> IdleSprite[3][swapIdle].image;
                    default -> image;
                };
            } else if (pressedKey.previousKey != null) {
                image = switch (pressedKey.previousKey) {
                    case "down" -> IdleSprite[0][swapIdle].image;
                    case "up" -> IdleSprite[1][swapIdle].image;
                    case "use" -> IdleSprite[1][swapIdle].image;
                    case "left" -> IdleSprite[2][swapIdle].image;
                    case "right" -> IdleSprite[3][swapIdle].image;
                    default -> image;
                };
            } else {
                image = IdleSprite[0][swapIdle].image;
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}