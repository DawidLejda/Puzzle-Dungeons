package main;

import character.Cat;
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
    public KeyHandler pressedKey = new KeyHandler(this);

    public Player player = new Player(this, pressedKey);
    public Cat cat = new Cat(this);
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
    public Catnip[] catnips = new Catnip[3];
    public CatnipTrail trail = new CatnipTrail(this);
    public Object[] catnipTrails = new Object[50];

    public Teleport teleport = new Teleport(this);
    public Computer bunkerComputer = new Computer(this);
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
        objectPlacement.CatnipPlacement();
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
        if(!teleport.playerDesintegration)
        {
            player.Update();
        }

        event.Update();


        if(!map.mapSwap)
        {
            map.Update();
            trail.Update();
            bunker.Update();
            airvent.Update();
            ButtonState.Update();
            ButtonElevationUp.Update();
            ButtonElevationDown.Update();
            bridgeRight.Update();
            bridgeLeft.Update();
            for (Catnip catnip : catnips)
            {
                if (catnip != null)
                {
                    catnip.Update();
                }
            }
            if (event.trailStart)
            {
                trail.catnipPathX[0] = player.x / tileSize;
                trail.catnipPathY[0] = player.y / tileSize;
                event.trailStart = false;
            }
            if (!cat.stop && (trail.catnipPathY[0] != 0 && event.catnipsCount > 0) && !trail.stopTrailPlacement)
            {
                event.renderUseCatnip = false;
                objectPlacement.Catnip_TrailPlacement();
                player.speed = 2;
            } else
            {
                player.speed = 4;
            }
        }
        else
        {
            bunkerComputer.Update();
            if(teleport.teleportRender)
            {
                teleport.Update();
            }
        }

        cat.Update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(!map.mapSwap)
        {
            map.Draw(g2);

            if (cat.throwAction)
            {
                cat.Draw(g2);
                airvent.Draw(g2, this);
            }

            if (!player.visibility)
            {
                player.Draw(g2);
                cat.Draw(g2);
                airvent.Draw(g2, this);
                ButtonState.Draw(g2);
                ButtonElevationUp.Draw(g2);
                ButtonElevationDown.Draw(g2);
                bridgeLeft.Draw(g2);
                bridgeRight.Draw(g2);
                bridgeMid.draw(g2, this);
                for (int i = 0, n = trees[0].length; i < n; i++)
                {
                    if (trees[0][i] != null && trees[1][i] != null)
                    {
                        trees[0][i].draw(g2, this);
                        trees[1][i].draw(g2, this);
                    }
                }
                for (Catnip catnip : catnips)
                {
                    if (catnip != null)
                    {
                        catnip.Draw(g2);
                    }
                }
                bunker.DrawStanding(g2, this);
                for (int i = 0; i < trail.catnipSteps; i++)
                {
                    if (catnipTrails[i] != null)
                    {
                        catnipTrails[i].draw(g2, this);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            else
            {
                if (!cat.throwAction)
                {
                    airvent.Draw(g2, this);
                }
                ButtonState.Draw(g2);
                ButtonElevationUp.Draw(g2);
                ButtonElevationDown.Draw(g2);
                bridgeLeft.Draw(g2);
                bridgeRight.Draw(g2);
                bridgeMid.draw(g2, this);
                for (int i = 0, n = trees[0].length; i < n; i++)
                {
                    if (trees[0][i] != null && trees[1][i] != null)
                    {
                        trees[0][i].draw(g2, this);
                        trees[1][i].draw(g2, this);
                    }
                }
                for (Catnip catnip : catnips)
                {
                    if (catnip != null)
                    {
                        catnip.Draw(g2);
                    }
                }
                for (int i = 0; i < trail.catnipSteps; i++)
                {
                    if (catnipTrails[i] != null)
                    {
                        catnipTrails[i].draw(g2, this);
                    } else
                    {
                        break;
                    }
                }
                bunker.DrawStanding(g2, this);
                if (!cat.throwAction)
                {
                    cat.Draw(g2);
                }
                player.Draw(g2);
            }

            event.Draw(g2);
            trail.Draw(g2);
        }


        else
        {
            if (!player.visibility)
            {
                map.Draw(g2);
                player.Draw(g2);

                cat.Draw(g2);
                teleport.Draw(g2);
                bunkerComputer.Draw(g2);
            }
            else
            {
                map.Draw(g2);
                cat.Draw(g2);
                teleport.Draw(g2);
                bunkerComputer.Draw(g2);
                event.Draw(g2);
                if(!teleport.playerDesintegration)
                {
                    player.Draw(g2);
                }
            }
        }

        g2.drawString("FPS: " + averageFPS,3,12);
        g2.drawString("Vis: " + player.visibility,3,24);
        g2.drawString("playerMoving: " + player.playerMoving,3,36);
        g2.drawString("X: " + player.x/tileSize,3,82);
        g2.drawString("Y: " + player.y/tileSize,3,94);

        g2.dispose();
    }

}
