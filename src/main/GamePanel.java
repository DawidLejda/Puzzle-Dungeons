package main;

import character.Player;
import map.Map;
import object.*;
import object.Object;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    // Parameters ************************************************
    public final int width = 960;
    public final int height = 768;
    public final int tileSize = 64;
    final int TARGET_FPS = 60;
    public int averageFPS;
    public boolean gameRunning = true;
    public int frameCount = 0;

    Thread GameThread;
    KeyHandler pressedKey = new KeyHandler();

    public Player player = new Player(this, pressedKey);
    public Map map = new Map(this, player);
    public EventHandler event = new EventHandler(this,pressedKey);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public ObjectPlacement objectPlacement = new ObjectPlacement(this);
    public QuantumBunker bunker = new QuantumBunker(this);
    public AirVent airvent = new AirVent(this);
    public ButtonQuantumState ButtonState = new ButtonQuantumState(this);
    public ButtonElevation ButtonElevationUp = new ButtonElevation(this, 26);
    public ButtonElevation ButtonElevationDown = new ButtonElevation(this, 24);
    public BridgeLeft bridgeLeft = new BridgeLeft(this);
    public BridgeRight bridgeRight = new BridgeRight(this);
    public BridgeMid bridgeMid = new BridgeMid(this);
    public Object[][] trees = new Object[2][5];

    // ********************************************************

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setDoubleBuffered(true);
        this.addKeyListener(pressedKey);
        this.setFocusable(true);
    }

    public void Placement()
    {
        objectPlacement.TreePlacement();
    }

    public void startGameThread()
    {
        GameThread = new Thread(this);
        GameThread.start();
    }

    @Override
    public void run()
    {
        final double targetTime = 1000000000.0 / TARGET_FPS;
        double delta = 0;

        long timer = System.currentTimeMillis();
        long initialTime = System.nanoTime();

        // Game loop
        while (gameRunning)
        {
            long currentTime = System.nanoTime();

            delta += (currentTime - initialTime) / targetTime;
            initialTime = currentTime;

            if (delta >= 1)
            {
                Update();
                repaint();
                delta--;
                frameCount++;
            }

            if (System.currentTimeMillis() - timer > 1000)
            {
                averageFPS = frameCount;
                timer += 1000;
                frameCount = 0;
            }

        }
    }

    public void Update()
    {
        map.Update();
        player.Update();
        event.Update();
        bunker.Update();
        airvent.Update();
        ButtonState.Update();
        ButtonElevationUp.Update();
        ButtonElevationDown.Update();
        bridgeRight.Update();
        bridgeLeft.Update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        map.Draw(g2);


        if(!player.visibility)
        {
            player.Draw(g2);

            bunker.DrawStanding(g2, this);
            airvent.Draw(g2, this);
            ButtonState.Draw(g2);
            ButtonElevationUp.Draw(g2);
            ButtonElevationDown.Draw(g2);
            bridgeLeft.Draw(g2);
            bridgeRight.Draw(g2);
            bridgeMid.draw(g2,this);
            for (int i = 0, n = trees[0].length; i < n; i++) {
                if (trees[0][i] != null && trees[1][i] != null) {
                    trees[0][i].draw(g2, this);
                    trees[1][i].draw(g2, this);
                }
            }
        }

        else
        {
            bunker.DrawStanding(g2, this);
            airvent.Draw(g2, this);
            ButtonState.Draw(g2);
            ButtonElevationUp.Draw(g2);
            ButtonElevationDown.Draw(g2);
            bridgeLeft.Draw(g2);
            bridgeRight.Draw(g2);
            bridgeMid.draw(g2,this);
            for (int i = 0, n = trees[0].length; i < n; i++) {
                if (trees[0][i] != null && trees[1][i] != null) {
                    trees[0][i].draw(g2, this);
                    trees[1][i].draw(g2, this);
                }
            }
            player.Draw(g2);
        }

        event.Draw(g2);
        g2.drawString("FPS: " + averageFPS,3,12);
        g2.drawString("Vis: " + player.visibility,3,24);
        g2.drawString("buttonState: " + event.buttonState,3,36);
        g2.drawString("LEFT: " + bridgeLeft.swapSkin,3,48);
        g2.drawString("RIGHT: " + bridgeRight.swapSkin,3,60);
        g2.drawString("Traversable: " + ButtonState.traversable,3,72);
        g2.dispose();
    }

}
