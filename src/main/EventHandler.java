package main;


import object.ObjectImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

public class EventHandler {
    GamePanel gamePanel;
    KeyHandler pressedKey;
    Random rand = new Random();

    boolean renderUse, airventSeen, renderUseCatnip, renderUseTeleport;
    public boolean bunkerStop, renderThrow;
    public boolean trailStart, catStart = false;
    public boolean buttonState, elevationDown, elevationUp;
    public int charX, charY;
    public int catnipsCount = 0;
    public object.ObjectImages[] catnipBar = new ObjectImages[4];
    public object.ObjectImages[] minigame = new ObjectImages[8];
    public object.ObjectImages[] use = new ObjectImages[5];
    public int randomBridge;
    public boolean teleportMinigame, minigameFinished, renderMinigame = false;
    int frame, swapSkin = 0;
    int pauseState,menuState = 0;


    public EventHandler(GamePanel gamePanel, KeyHandler pressedKey) {
        this.gamePanel = gamePanel;
        this.pressedKey = pressedKey;
        buttonState = true;
        getUIModel();
    }


    public void getUIModel() {
        String path;
        try {
            use[0] = new ObjectImages();
            use[1] = new ObjectImages();
            use[2] = new ObjectImages();
            use[3] = new ObjectImages();
            use[4] = new ObjectImages();
            use[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/use.png")));
            use[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/useCatnip.png")));
            use[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/throw.png")));
            use[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/useTeleport.png")));
            use[4].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("events/minigameframe1.png")));
            for (int i = 0; i < 4; i++) {
                path = "events/progressBar".concat(Integer.toString(i)).concat(".png");
                catnipBar[i] = new ObjectImages();
                catnipBar[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
            for (int i = 0; i < 8; i++) {
                path = "events/minigameBar".concat(Integer.toString(i + 1)).concat(".png");
                minigame[i] = new ObjectImages();
                minigame[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Update() {

        if(gamePanel.teleport.gameEND && Objects.equals(pressedKey.lastReleasedKey, "space")
                && gamePanel.gameState != menuState)
        {
            gamePanel.gameState = gamePanel.stateMain;
            menuState = 0;
            pressedKey.lastReleasedKey = null;
            gamePanel.teleport.gameEND = false;
        }

        if(gamePanel.gameState != gamePanel.stateMain)
        {
            inGameState();
        }
        if(gamePanel.gameState == gamePanel.stateMain)
        {
            MainMenuState();
        }
        if (!gamePanel.map.mapSwap)
        {
            IslandUpdate();
        }
        else
        {
            BunkerUpdate();
        }
    }

    void IslandUpdate() {
        renderUse = false;
        bunkerStop = false;
        pressedKey.UseInRange = false;
        elevationDown = false;
        elevationUp = false;
        renderUseCatnip = false;
        renderThrow = false;
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;

        catnip();
        catnipTrail();

        if (!catStart && gamePanel.trail.catnipSteps > 4) {
            catStart = true;
        }

        if (abs(charX - gamePanel.airvent.x) <= 4
                && abs(charY - gamePanel.airvent.y) <= 3) {
            int centerX = gamePanel.airvent.x + 1;
            int centerY = gamePanel.airvent.y + 1;
            if ((charX == centerX) && (charY == centerY - 1)) {

                pressedKey.UseInRange = true;
                if (!gamePanel.cat.throwAction && gamePanel.event.catnipsCount >= 2) {
                    if (gamePanel.cat.stop) {
                        renderThrow = true;
                        if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                            gamePanel.cat.throwAction = true;
                            gamePanel.bunker.materialize = true;
                            gamePanel.event.catnipsCount = 0;
                            pressedKey.lastReleasedKey = null;
                        }
                    }
                } else {
                    renderUse = true;
                    if (pressedKey.use) {
                        bunkerStop = true;
                    }
                }
            }

        } else if (abs(charX - gamePanel.ButtonState.x) <= 1
                && abs(charY - gamePanel.ButtonState.y) <= 1) {
            if ((charX == gamePanel.ButtonState.x) && (charY == gamePanel.ButtonState.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    randomBridge = rand.nextInt(2);
                    buttonState = !buttonState;
                    pressedKey.lastReleasedKey = null;
                }
            }
        } else if (abs(charX - gamePanel.ButtonElevationUp.x) <= 1
                && abs(charY - gamePanel.ButtonElevationUp.y) <= 1) {
            if ((charX == gamePanel.ButtonElevationUp.x) && (charY == gamePanel.ButtonElevationUp.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    elevationUp = true;
                    pressedKey.lastReleasedKey = null;
                }
            }
        } else if (abs(charX - gamePanel.ButtonElevationDown.x) <= 1
                && abs(charY - gamePanel.ButtonElevationDown.y) <= 1) {
            if ((charX == gamePanel.ButtonElevationDown.x) && (charY == gamePanel.ButtonElevationDown.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;

                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    elevationDown = true;
                    pressedKey.lastReleasedKey = null;
                }
            }
        }

        if (gamePanel.bunker.materialize &&
                (abs(charX - gamePanel.bunker.x) <= 2
                        && abs(charY - gamePanel.bunker.y) <= 2)) {
            if ((charX == gamePanel.bunker.x) && (charY == gamePanel.bunker.y)) {
                renderUse = true;
                pressedKey.UseInRange = true;
                if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                    gamePanel.map.mapSwap = !gamePanel.map.mapSwap;
                    gamePanel.player.x = 17 * gamePanel.tileSize;
                    gamePanel.player.y = 18 * gamePanel.tileSize + 20;
                    if (!gamePanel.cat.INBUNKER) {
                        gamePanel.cat.INBUNKER = true;
                        gamePanel.cat.x = 9 * gamePanel.tileSize;
                        gamePanel.cat.y = 15 * gamePanel.tileSize + 32;
                    }
                    pressedKey.lastReleasedKey = null;
                }
            }
        }
    }


    void BunkerUpdate() {
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;
        renderUse = false;
        renderUseTeleport = false;
        renderMinigame = false;

        if (charX == 17 && charY == 19) {
            gamePanel.map.mapSwap = false;
            gamePanel.player.x = gamePanel.bunker.x * gamePanel.tileSize + 30;
            gamePanel.player.y = gamePanel.bunker.y * gamePanel.tileSize + 30;
        } else if (abs(charX - gamePanel.bunkerComputer.x) <= 1
                && abs(charY - gamePanel.bunkerComputer.y) <= 1) {
            if ((charX == gamePanel.bunkerComputer.x) && (charY == gamePanel.bunkerComputer.y)) {
                if (!teleportMinigame) {
                    if (!gamePanel.bunkerComputer.initializeTeleportation) {
                        renderUse = true;
                        pressedKey.UseInRange = true;
                        if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                            gamePanel.bunkerComputer.computerInteraction++;
                            pressedKey.lastReleasedKey = null;
                        }
                    } else {
                        renderUseTeleport = true;
                        pressedKey.UseInRange = true;
                        if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                            teleportMinigame = true;
                            pressedKey.lastReleasedKey = null;
                        }
                    }
                } else {
                    if (!minigameFinished) {
                        frame++;
                        if (frame > 4) {
                            frame = 0;
                            swapSkin++;
                            if (swapSkin >= 7) {
                                swapSkin = 0;
                            }
                        }
                        renderMinigame = true;
                        pressedKey.UseInRange = true;
                        if (Objects.equals(pressedKey.lastReleasedKey, "use")) {
                            if (swapSkin == 3 || swapSkin == 4) {
                                minigameFinished = true;
                                gamePanel.teleport.teleportRender = true;
                            } else {
                                swapSkin = 7;
                                frame = -8;
                            }
                            pressedKey.lastReleasedKey = null;
                        }
                    }

                }
            }
        }


    }


    void inGameState()
    {
        if (Objects.equals(pressedKey.lastReleasedKey, "esc"))
        {
            if(gamePanel.gameState == gamePanel.statePause)
            {
                gamePanel.gameState = gamePanel.statePlay;
            }
            else if(gamePanel.gameState == gamePanel.statePlay)
            {
                gamePanel.gameState = gamePanel.statePause;
                pauseState = 0;
            }
            pressedKey.lastReleasedKey = null;
        }


            if (Objects.equals(pressedKey.lastReleasedKey, "down"))
            {
                if (pauseState < 2) {
                    pauseState++;
                }
                pressedKey.lastReleasedKey = null;
            }
            else if (Objects.equals(pressedKey.lastReleasedKey, "up"))
            {
                if (pauseState > 0)
                {
                    pauseState--;
                }
                pressedKey.lastReleasedKey = null;
            }
            else if (Objects.equals(pressedKey.lastReleasedKey, "space"))
            {
                if (pauseState == 0)
                {
                    gamePanel.gameState = gamePanel.statePlay;
                }
                else if(pauseState == 1)
                {
                    gamePanel.gameState = gamePanel.stateMain;
                    menuState = 0;
                }
                else
                {
                    gamePanel.gameRunning = false;
                    System. exit(0);
                }
                pressedKey.lastReleasedKey = null;
            }

    }


    void MainMenuState()
    {
        if (Objects.equals(pressedKey.lastReleasedKey, "down"))
        {
            if (menuState < 1) {
                menuState++;
            }
            pressedKey.lastReleasedKey = null;
        }
        else if (Objects.equals(pressedKey.lastReleasedKey, "up"))
        {
            if (menuState > 0)
            {
                menuState--;
            }
            pressedKey.lastReleasedKey = null;
        }
        else if (Objects.equals(pressedKey.lastReleasedKey, "space"))
        {
            if (menuState == 0)
            {
                gamePanel.gameState = gamePanel.statePlay;
            }
            else if(menuState == 1)
            {
                gamePanel.gameRunning = false;
                System. exit(0);
            }

            pressedKey.lastReleasedKey = null;
        }
    }
     void catnipTrail()
    {
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;
        if(!trailStart && catnipsCount == 3 &&
                (abs(charX - gamePanel.cat.x/gamePanel.tileSize) <= 1 && abs(charY - gamePanel.cat.y/gamePanel.tileSize) <= 1))
        {
            renderUseCatnip = true;
            pressedKey.UseInRange = true;
            if (Objects.equals(pressedKey.lastReleasedKey, "use"))
            {
                trailStart = true;
                pressedKey.lastReleasedKey = null;
            }
        }
    }


     void catnip() {
        charX = gamePanel.player.x / gamePanel.tileSize;
        charY = gamePanel.player.y / gamePanel.tileSize;

        if (!airventSeen && abs(charX - gamePanel.airvent.x) <= 5
                && abs(charY - gamePanel.airvent.y) <= 5)
        {
            airventSeen = true;
        }

        if (airventSeen)
        {
            for (int i = 0, n = gamePanel.catnips.length; i < n; i++)
            {
                gamePanel.catnips[i].isHarvesting = false;
                if(!gamePanel.catnips[i].Harvested && catnipsCount < 3)
                {
                    if (abs(charX - gamePanel.catnips[i].x) <= 1
                            && abs(charY - gamePanel.catnips[i].y) <= 1)
                    {
                        if ((charX == gamePanel.catnips[i].x-1 || (charX == gamePanel.catnips[i].x))
                                && (charY == gamePanel.catnips[i].y-1))
                        {
                            renderUse = true;
                            pressedKey.UseInRange = true;
                            if (pressedKey.use)
                            {
                                gamePanel.catnips[i].isHarvesting = true;
                            }
                        }
                    }
                }
            }
        }
    }


    public void DrawPauseScreen(Graphics2D g2)
    {
        gamePanel.map.Draw(g2);
        g2.setColor(new Color(200,200,230,128));
        {
            g2.fillRect(gamePanel.tileSize*4-32,gamePanel.tileSize*3, gamePanel.width/2, gamePanel.height/2);
        }
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 60));

        g2.drawString("PAUSE MENU", gamePanel.tileSize*4+16,gamePanel.tileSize*3-32);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        if (pauseState == 0)
        {
            g2.setColor(Color.YELLOW);
            g2.drawString("RESUME", gamePanel.tileSize*6,gamePanel.tileSize*5);
            g2.setColor(Color.BLACK);
        }
        else
        {
            g2.drawString("RESUME", gamePanel.tileSize*6,gamePanel.tileSize*5);
        }
        if (pauseState == 1)
        {
            g2.setColor(Color.YELLOW);
            g2.drawString("RETURN TO MENU", gamePanel.tileSize*6-55,gamePanel.tileSize*6);
            g2.setColor(Color.BLACK);
        }
        else
        {
            g2.drawString("RETURN TO MENU", gamePanel.tileSize*6-55,gamePanel.tileSize*6);
        }
        if (pauseState == 2)
        {
            g2.setColor(Color.YELLOW);
            g2.drawString("TERMINATE GAME", gamePanel.tileSize*6-55,gamePanel.tileSize*7);
            g2.setColor(Color.BLACK);
        }
        else
        {
            g2.drawString("TERMINATE GAME", gamePanel.tileSize*6-55,gamePanel.tileSize*7);
        }
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,15F));
        g2.drawString("press space to confirm", gamePanel.tileSize*6,gamePanel.tileSize*8);
    }

    public void DrawTitleScreen(Graphics2D g2)
    {
        g2.setColor(new Color(70,120,200));
        {
            g2.fillRect(0,0, gamePanel.width, gamePanel.height);
        }
        g2.setColor(new Color(200,200,230));
        {
            g2.fillRect(gamePanel.tileSize*4-32,gamePanel.tileSize*3, gamePanel.width/2, gamePanel.height/2);
        }
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 90));
        g2.drawString("Puzzle Dungeons", gamePanel.tileSize*2,gamePanel.tileSize*2);
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        if (menuState == 0)
        {
            g2.setColor(Color.YELLOW);
            g2.drawString("START GAME", gamePanel.tileSize*5,gamePanel.tileSize*5);
            g2.setColor(Color.BLACK);
        }
        else
        {
            g2.drawString("START GAME", gamePanel.tileSize*5,gamePanel.tileSize*5);
        }
        if (menuState == 1)
        {
            g2.setColor(Color.YELLOW);
            g2.drawString("EXIT", gamePanel.tileSize*6-60,gamePanel.tileSize*6);
            g2.setColor(Color.BLACK);
        }
        else
        {
            g2.drawString("EXIT", gamePanel.tileSize*6-60,gamePanel.tileSize*6);
        }
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,15F));
        g2.drawString("press space to confirm", gamePanel.tileSize*6,gamePanel.tileSize*8);
    }

    public void Draw(Graphics2D g2)
    {
        int x = gamePanel.player.centerX + gamePanel.tileSize/3;
        int y = gamePanel.player.centerY - gamePanel.tileSize/2;
        int scale = gamePanel.tileSize;
        if(renderUse)
        {
            g2.drawImage(use[0].image, x, y, scale/2, scale/2, null);
        }
        else if(renderUseCatnip)
        {
            g2.drawImage(use[1].image, x, y, scale/2, scale/2, null);
        }
        else if(renderThrow)
        {
            g2.drawImage(use[2].image, x-20, y-25, scale, scale, null);
        }
        else if(renderUseTeleport)
        {
            g2.drawImage(use[3].image, x, y, scale/2, scale/2, null);
        }
        else if(!minigameFinished && teleportMinigame && renderMinigame)
        {
            g2.drawImage(use[4].image, x, y+96, scale/2, scale/2, null);
            g2.drawImage(minigame[swapSkin].image, x, y+96, scale/2, scale/2, null);
        }

        if(catnipsCount > 0)
        {
            g2.drawImage(catnipBar[0].image, gamePanel.width-scale,
                    0, scale, scale, null);

            g2.drawImage(catnipBar[catnipsCount].image, gamePanel.width-scale,
                    0, scale, scale, null);
        }


    }
}
