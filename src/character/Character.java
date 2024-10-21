package character;

import java.awt.image.BufferedImage;

public class Character
{
    public int x;
    public int y;
    public int speed;
    public boolean collision;
    public String direction = "down";

    public BufferedImage down1, down2, down3, down4, down5, down6;
    public BufferedImage idle_down1, idle_down2, idle_down3, idle_down4, idle_down5;

    public BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage idle_up1, idle_up2, idle_up3, idle_up4, idle_up5;

    public BufferedImage left1, left2, left3, left4, left5, left6;
    public BufferedImage idle_left1, idle_left2, idle_left3, idle_left4, idle_left5;

    public BufferedImage right1, right2, right3, right4, right5, right6;
    public BufferedImage idle_right1, idle_right2, idle_right3, idle_right4, idle_right5;

}

