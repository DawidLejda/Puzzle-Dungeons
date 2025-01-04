package character;

import main.GamePanel;
import main.KeyHandler;
import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Character
{

    GamePanel gamePanel;
    KeyHandler pressedKey;
    public boolean playerMoving;
    private int swapSkin = 1;
    private int swapIdle = 1;
    private int animationFrame = 1;
    private int animationIdle = 1;
    public object.ObjectImages[][] Sprite = new ObjectImages[4][6];
    public object.ObjectImages[][] IdleSprite = new ObjectImages[4][5];
    public final int centerX;
    public final int centerY;


    public Player(GamePanel gamePanel, KeyHandler pressedKey)
    {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        getPlayerModel();
        direction = "down";
        centerX = (gamePanel.width / 2) - (gamePanel.tileSize / 2);
        centerY = (gamePanel.height / 2) - (gamePanel.tileSize / 2);

        // Coordinates of player starting position // 17x 29y lewa, 30x,10y prawa
        x = gamePanel.tileSize * 17;
        y = gamePanel.tileSize * 14;
        speed = 4;
    }


    public void getPlayerModel()
    {
        try {
            String direction;
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    direction = "down";
                }
                else if (j == 1) {
                    direction = "up";
                }
                else if (j == 2) {
                    direction = "left";
                }
                else {
                    direction = "right";
                }
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
        }
        catch (IOException e)
        {
            System.out.println("Couldn't read player character model file");
            gamePanel.gameRunning = false;
        }
    }


    void CollisionCheck()
    {
        visibility = true;
        collision = false;
        gamePanel.collisionChecker.CheckBridgeCollision(this);
        gamePanel.collisionChecker.CheckTileCollision(this);
        gamePanel.collisionChecker.CheckObjectVisibility(this);
    }


    public void Update() {

        if (pressedKey.left)
        {
            direction = "left";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.right)
        {
            direction = "right";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.up)
        {
            direction = "up";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.down)
        {
            direction = "down";
            animationFrame++;
            playerMoving = true;
        }
        else if (pressedKey.use)
        {
            direction = pressedKey.previousKey;
            playerMoving = false;
            animationIdle++;
        }
        else
        {
            animationIdle++;
            playerMoving = false;
        }
        CollisionCheck();
        if(!collision)
        {
            if (pressedKey.left)
            {
                x -= speed;
            }
            else if (pressedKey.right)
            {
                x += speed;
            }
            else if (pressedKey.up)
            {
                y -= speed;
            }
            else if (pressedKey.down)
            {
                y += speed;
            }
        }

        if (playerMoving)
        {
            if (animationFrame >= 5)
            {
                swapSkin++;
                if (swapSkin > 5)
                {
                    swapSkin = 1;
                }
                animationFrame = 0;
            }
        }
        else
        {
            if (animationIdle >= 12)
            {
                swapIdle++;
                if (swapIdle > 4)
                {
                    swapIdle = 1;
                }
                animationIdle = 0;
            }
        }
    }


    public void Draw(Graphics2D g2)
    {
        BufferedImage image = null;

        if(playerMoving)
        {
            image = switch (pressedKey.lastPressedKey) {
                case "down" -> Sprite[0][swapSkin].image;
                case "up" -> Sprite[1][swapSkin].image;
                case "left" -> Sprite[2][swapSkin].image;
                case "right" -> Sprite[3][swapSkin].image;
                default -> image;
            };
        }

        else
        {
            if (pressedKey.lastReleasedKey != null)
            {
                image = switch (pressedKey.lastReleasedKey) {
                    case "down" -> IdleSprite[0][swapIdle].image;
                    case "up" -> IdleSprite[1][swapIdle].image;
                    case "use" -> IdleSprite[1][swapIdle].image;
                    case "left" -> IdleSprite[2][swapIdle].image;
                    case "right" -> IdleSprite[3][swapIdle].image;
                    default -> image;
                };
            }
            else if(pressedKey.previousKey != null)
            {
                    image = switch (pressedKey.previousKey) {
                        case "down" -> IdleSprite[0][swapIdle].image;
                        case "up" -> IdleSprite[1][swapIdle].image;
                        case "use" -> IdleSprite[1][swapIdle].image;
                        case "left" -> IdleSprite[2][swapIdle].image;
                        case "right" -> IdleSprite[3][swapIdle].image;
                        default -> image;};
            }
            else
            {
                image = IdleSprite[0][swapIdle].image;
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(image, centerX, centerY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}