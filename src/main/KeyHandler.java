package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{

    public boolean left, right, up, down;
    public String lastPressedKey;
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
        }
    }
}
