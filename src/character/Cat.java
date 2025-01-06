package character;

import main.GamePanel;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Represents the cat character in the game.
 */
public class Cat extends Character {
    GamePanel gamePanel;
    Random rand = new Random();
    private int swapSkin = 0; // Index for the walking animation sprite sheet
    private int swapIdle = 0; // Index for the idle animation sprite sheet
    public boolean moving, throwAction, INBUNKER = false;
    public boolean[] visited = new boolean[50]; // Keeps track of visited positions in the catnip trail
    public boolean stop = false; // Flag to stop the cat's movement
    int frame, idle_frame, time = 0;
    int randomTime = (rand.nextInt(5)) + 10; // Randomizes the idle animation speed
    public ObjectImages[][] Sprite = new ObjectImages[4][4]; // Stores the walking animation sprites
    public ObjectImages[] IdleSprite = new ObjectImages[4]; // Stores the idle animation sprites
    String previous_direction; // Stores the previous direction of movement
    public int[] materializeCoordinates = new int[2]; // Coordinates for materialization animation

    /**
     * Constructor for the Cat class.
     *
     * @param gamePanel The GamePanel object associated with the cat.
     */
    public Cat(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getCatModel();
        x = 15 * gamePanel.tileSize;
        y = 15 * gamePanel.tileSize;
        speed = 3;
        Arrays.fill(visited, false);
        materializeCoordinates[0] = 18;
        materializeCoordinates[1] = 60;
    }

    /**
     * Loads the cat character model images.
     */
    void getCatModel() {
        try {
            String direction;
            for (int j = 0; j < 4; j++) {
                direction = switch (j) {
                    case 0 -> "down";
                    case 1 -> "top";
                    case 2 -> "left";
                    default -> "right";
                };
                for (int i = 0; i < 4; i++) {
                    String path = "Cat/".concat(direction).concat(Integer.toString(i + 1)).concat(".png");
                    Sprite[j][i] = new ObjectImages();
                    Sprite[j][i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
                    if (j == 0) {
                        String idle_path = "Cat/idle".concat(Integer.toString(i + 1)).concat(".png");
                        IdleSprite[i] = new ObjectImages();
                        IdleSprite[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(idle_path)));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read cat character model file");
            gamePanel.gameRunning = false;
        }
    }

    /**
     * Updates the cat's state, including movement, animation, and throwing action.
     */
    public void Update() {
        if (!throwAction) {
            // Move the cat to the final position in the trail
            if (!moving && direction == null) {
                if (x / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    stop = true;
                }
                if ((x - speed) / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    x -= speed;
                    stop = true;
                } else if ((x + speed) / gamePanel.tileSize == 14 && y / gamePanel.tileSize == 29) {
                    x += speed;
                    stop = true;
                } else if (x / gamePanel.tileSize == 14 && (y - speed) / gamePanel.tileSize == 29) {
                    y += speed;
                    stop = true;
                } else if (x / gamePanel.tileSize == 14 && (y + speed) / gamePanel.tileSize == 29) {
                    y -= speed;
                    stop = true;
                } else if (y == 1852) {
                    y += 4;
                    stop = true;
                }
            }
        } else {
            playerThrowAction();
        }

        if (gamePanel.event.catStart && !stop || throwAction) {
            if (!throwAction) {
                direction();
            }

            // Move the cat according to the current direction
            if (direction != null && !waitForPlayer()) {
                switch (direction) {
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                }
                moving = true;
                frame++;
            }
        }

        time++;
        if (moving) {
            // Update walking animation
            if (frame > 10) {
                swapSkin++;
                swapIdle = 0;
                if (swapSkin >= 4) {
                    swapSkin = 0;
                }
                frame = 0;
            }
        } else {
            // Update idle animation
            idle_frame++;
            if (idle_frame >= randomTime) {
                swapIdle++;
                swapSkin = 0;
                if (swapIdle >= 4) {
                    swapIdle = 0;
                }
                idle_frame = 0;
            }
        }

        // Randomize the idle animation speed
        if (time > randomTime * 4) {
            randomTime = (rand.nextInt(17)) + 8;
            time = 0;
        }
    }

    /**
     * Determines the direction of movement for the cat based on the catnip trail.
     */
    void direction() {
        for (int i = 0; i < 50; i++) {
            int nextX = gamePanel.trail.catnipPathX[i];
            int nextY = gamePanel.trail.catnipPathY[i];

            if (!visited[i]) {
                if (x / gamePanel.tileSize + 1 == nextX) {
                    direction = "right";
                    break;
                } else if (x / gamePanel.tileSize - 1 == nextX) {
                    direction = "left";
                    break;
                } else if (y / gamePanel.tileSize - 1 == nextY) {
                    direction = "up";
                    break;
                } else if (y / gamePanel.tileSize + 1 == nextY) {
                    direction = "down";
                    break;
                } else {
                    visited[i] = true;
                }
            } else {
                previous_direction = direction;
                direction = null;
                moving = false;
            }
        }
    }

    /**
     * Checks if the cat should wait for the player to catch up.
     *
     * @return True if the cat should wait, false otherwise.
     */
    boolean waitForPlayer() {
        return abs(x / gamePanel.tileSize - gamePanel.player.x / gamePanel.tileSize) <= 1 &&
                abs(y / gamePanel.tileSize - gamePanel.player.y / gamePanel.tileSize) <= 1;
    }

    /**
     * Handles the cat's throwing action when the player interacts with the bunker.
     */
    void playerThrowAction() {
        direction = null;
        moving = false;
        if (gamePanel.bunker.materialize) {
            if (materializeCoordinates[0] > 0) {
                moving = true;
                direction = "right";
                materializeCoordinates[0] -= speed;
                x += speed;
            } else if (materializeCoordinates[1] > 0) {
                moving = true;
                direction = "up";
                materializeCoordinates[1] -= speed;
                y -= speed;
            }
        }
    }

    /**
     * Draws the cat on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void Draw(Graphics2D g2) {
        BufferedImage image = null;
        if (materializeCoordinates[1] >= 0) {
            if (moving) {
                // Select the appropriate walking animation sprite based on the direction
                image = switch (direction) {
                    case "up" -> Sprite[0][swapSkin].image;
                    case "down" -> Sprite[1][swapSkin].image;
                    case "left" -> Sprite[2][swapSkin].image;
                    case "right" -> Sprite[3][swapSkin].image;
                    default -> image;
                };
            } else {
                // Use the idle animation sprite
                image = IdleSprite[swapIdle].image;
            }

            int centerX = x - gamePanel.player.x + gamePanel.player.centerX + 7;
            int centerY = y - gamePanel.player.y + gamePanel.player.centerY + 10;

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(image, centerX, centerY, 40, 40, null);
        }
    }
}