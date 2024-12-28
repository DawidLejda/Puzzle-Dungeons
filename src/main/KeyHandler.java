package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{

    public boolean left, right, up, down, use;
    public String lastPressedKey;
    public String lastReleasedKey;
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_A:
                left = true;
                lastPressedKey = "left";
                break;
            case KeyEvent.VK_D:
                right = true;
                lastPressedKey = "right";
                break;
            case KeyEvent.VK_W:
                up = true;
                lastPressedKey = "up";
                break;
            case KeyEvent.VK_S:
                down = true;
                lastPressedKey = "down";
                break;
            case KeyEvent.VK_E:
                use = true;
                lastPressedKey = "use";
                break;
        }
        lastReleasedKey = null;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_A:
                left = false;
                lastReleasedKey = "left";
                break;
            case KeyEvent.VK_D:
                right = false;
                lastReleasedKey = "right";
                break;
            case KeyEvent.VK_W:
                lastReleasedKey = "up";
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                lastReleasedKey = "down";
                break;
            case KeyEvent.VK_E:
                use = false;
                lastReleasedKey = "use";
                break;
            default:
                lastReleasedKey = null;
        }
    }
}
