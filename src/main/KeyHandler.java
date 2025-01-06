package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class handles keyboard inputs from the user.
 */
public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean UseInRange; // Indicates if the player is in range to interact with something
    public boolean left, right, up, down, use; // Flags for each movement and action key
    public String lastPressedKey; // Stores the last key pressed
    public String lastReleasedKey; // Stores the last key released
    public String previousKey; // Stores the second to last key pressed

    /**
     * Constructor for the KeyHandler class.
     *
     * @param gamePanel The GamePanel object associated with the key handler.
     */
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (lastPressedKey != null) {
            previousKey = lastPressedKey;
        }
        if (gamePanel.gameState == gamePanel.statePlay) {
            // Handle key presses only during gameplay
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    left = true;
                    lastPressedKey = "left";
                }
                case KeyEvent.VK_D -> {
                    right = true;
                    lastPressedKey = "right";
                }
                case KeyEvent.VK_W -> {
                    up = true;
                    lastPressedKey = "up";
                }
                case KeyEvent.VK_S -> {
                    down = true;
                    lastPressedKey = "down";
                }
                case KeyEvent.VK_E -> {
                    if (UseInRange) {
                        // Handle interaction key press
                        if (!gamePanel.event.renderUseCatnip) {
                            lastPressedKey = "up";
                        }
                        use = true;
                    }
                }
            }
        }
        lastReleasedKey = null;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key releases
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                left = false;
                lastReleasedKey = "left";
            }
            case KeyEvent.VK_D -> {
                right = false;
                lastReleasedKey = "right";
            }
            case KeyEvent.VK_W -> {
                lastReleasedKey = "up";
                up = false;
            }
            case KeyEvent.VK_S -> {
                down = false;
                lastReleasedKey = "down";
            }
            case KeyEvent.VK_E -> {
                if (UseInRange) {
                    use = false;
                    lastReleasedKey = "use";
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                if (gamePanel.gameState != gamePanel.stateMain) {
                    lastReleasedKey = "esc";
                }
            }
            case KeyEvent.VK_SPACE -> {
                if (gamePanel.gameState != gamePanel.statePlay || gamePanel.teleport.gameEND) {
                    System.out.println("key");
                    lastReleasedKey = "space";
                }
            }
        }
    }
}