package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{

    public boolean left, right, up, down;
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
                lastReleasedKey = null;
                break;
            case KeyEvent.VK_D:
                right = true;
                lastPressedKey = "right";
                lastReleasedKey = null;
                break;
            case KeyEvent.VK_W:
                up = true;
                lastPressedKey = "up";
                lastReleasedKey = null;
                break;
            case KeyEvent.VK_S:
                down = true;
                lastPressedKey = "down";
                lastReleasedKey = null;
                break;
        }
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
            default:
                lastReleasedKey = null;
        }
    }
}
