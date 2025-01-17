package main;
import character.Character;

import static java.lang.Math.abs;

/**
 * This class handles collision detection between the player,
 * the cat, and various objects and tiles in the game.
 */
public class CollisionChecker
{
    GamePanel gamePanel;

    public int playerLeft, playerRight, playerTop, playerBottom; // Player's collision box boundaries
    public boolean collisionCheck = true; // Flag to enable/disable collision checks

    /**
     * Constructor for the CollisionChecker class.
     *
     * @param gamePanel The GamePanel object associated with the collision checker.
     */
    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks for collisions between the character and tiles on the map.
     *
     * @param character The character to check for collisions.
     */
    public void CheckTileCollision(Character character)
    {
        // Calculate the boundaries of the character's collision box
        playerLeft = (character.x - gamePanel.tileSize) + (gamePanel.tileSize - (gamePanel.tileSize / 2)) + gamePanel.tileSize;
        playerRight = (character.x + gamePanel.tileSize) - (gamePanel.tileSize - (gamePanel.tileSize / 2));
        playerTop = (character.y - gamePanel.tileSize) + (int) (gamePanel.tileSize - (gamePanel.tileSize / 2.3)) + gamePanel.tileSize;
        playerBottom = (character.y + gamePanel.tileSize) - (gamePanel.tileSize - (gamePanel.tileSize / 2)-4);


        // Calculate the tile coordinates of the character's collision box corners
        int X_Left = playerLeft / gamePanel.tileSize;
        int X_Right = playerRight / gamePanel.tileSize;
        int Y_Top = playerTop / gamePanel.tileSize;
        int Y_Bottom = playerBottom / gamePanel.tileSize;

        int mapIndex1,mapIndex2;

        if(!gamePanel.map.mapSwap)
        {
            switch(character.direction)
            {
                // Check collisions with island tiles
                case "left":
                    X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.island[Y_Top][X_Left];
                    mapIndex2 = gamePanel.map.island[Y_Bottom][X_Left];

                    if(gamePanel.map.islandTile[mapIndex1].collision || gamePanel.map.islandTile[mapIndex2].collision)
                    {
                        if(collisionCheck)
                        {
                            character.collision = true;
                        }
                    }
                    break;
                case "right":
                    X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.island[Y_Top][X_Right];
                    mapIndex2 = gamePanel.map.island[Y_Bottom][X_Right];
                    if(gamePanel.map.islandTile[mapIndex1].collision || gamePanel.map.islandTile[mapIndex2].collision)
                    {
                        if(collisionCheck)
                        {
                            character.collision = true;
                        }
                    }
                    break;
                case "up":
                    Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.island[Y_Top][X_Left];
                    mapIndex2 = gamePanel.map.island[Y_Top][X_Right];
                    if(gamePanel.map.islandTile[mapIndex1].collision || gamePanel.map.islandTile[mapIndex2].collision)
                    {
                        character.collision = true;
                    }
                    break;
                case "down":
                    Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.island[Y_Bottom][X_Left];
                    mapIndex2 = gamePanel.map.island[Y_Bottom][X_Right];
                    if(gamePanel.map.islandTile[mapIndex1].collision || gamePanel.map.islandTile[mapIndex2].collision)
                    {
                        character.collision = true;
                    }
                    break;
            }
        }
        else
        {
            // Check collisions with bunker tiles
            switch(character.direction)
            {

                case "left":
                    X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.bunker[Y_Top][X_Left];
                    mapIndex2 = gamePanel.map.bunker[Y_Bottom][X_Left];

                    if(gamePanel.map.bunkerTile[mapIndex1].collision || gamePanel.map.bunkerTile[mapIndex2].collision)
                    {
                        if(collisionCheck)
                        {
                            character.collision = true;
                        }
                    }
                    break;
                case "right":
                    X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.bunker[Y_Top][X_Right];
                    mapIndex2 = gamePanel.map.bunker[Y_Bottom][X_Right];
                    if(gamePanel.map.bunkerTile[mapIndex1].collision || gamePanel.map.bunkerTile[mapIndex2].collision)
                    {
                        if(collisionCheck)
                        {
                            character.collision = true;
                        }
                    }
                    break;
                case "up":
                    Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.bunker[Y_Top][X_Left];
                    mapIndex2 = gamePanel.map.bunker[Y_Top][X_Right];
                    if(gamePanel.map.bunkerTile[mapIndex1].collision || gamePanel.map.bunkerTile[mapIndex2].collision)
                    {
                        character.collision = true;
                    }
                    break;
                case "down":
                    Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                    mapIndex1 = gamePanel.map.bunker[Y_Bottom][X_Left];
                    mapIndex2 = gamePanel.map.bunker[Y_Bottom][X_Right];
                    if(gamePanel.map.bunkerTile[mapIndex1].collision || gamePanel.map.bunkerTile[mapIndex2].collision)
                    {
                        character.collision = true;
                    }
                    break;
            }
        }
    }

    /**
     * Checks for collisions between the character and an object that is 2 tiles wide.
     *
     * @param character The character to check for collisions.
     * @param objX      The x-coordinate of the object in tile coordinates.
     * @param objY      The y-coordinate of the object in tile coordinates.
     */
    public void CheckObject_2width(Character character,int objX, int objY)
    {
        for (int i = 0; i < 2; i++) {
            int X_Left = playerLeft / gamePanel.tileSize;
            int X_Right = playerRight / gamePanel.tileSize;
            int Y_Top = playerTop / gamePanel.tileSize;
            int Y_Bottom = playerBottom / gamePanel.tileSize;

            switch (character.direction) {
                case "left":
                    X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                    if (X_Left == objX + (i+1) && Y_Top == objY ||
                            X_Left == objX + (i+1) && Y_Bottom == objY) {
                        character.collision = true;
                    }
                    break;
                case "right":
                    X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                    if (X_Right == objX + (i+1) && Y_Top == objY ||
                            X_Right == objX + (i+1) && Y_Bottom == objY) {
                        character.collision = true;
                    }
                    break;
                case "up":
                    Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                    if (X_Left == objX + (i+1) && Y_Top == objY||
                            X_Right == objX + (i+1) && Y_Top == objY) {
                        character.collision = true;
                    }
                    break;
                case "down":
                    Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                    if (X_Left == objX + (i+1) && Y_Bottom == objY ||
                            X_Right == objX + (i+1) && Y_Bottom == objY) {
                        character.collision = true;
                    }
                    break;
            }
        }
    }

    /**
     * Checks for collisions between the character and an object.
     *
     * @param character The character to check for collisions.
     * @param centerX   The x-coordinate of the object's center in tile coordinates.
     * @param centerY   The y-coordinate of the object's center in tile coordinates.
     */
    public void CheckObjectCollision(Character character, int centerX, int centerY)
    {
        int X_Left = playerLeft / gamePanel.tileSize;
        int X_Right = playerRight / gamePanel.tileSize;
        int Y_Top = playerTop / gamePanel.tileSize;
        int Y_Bottom = playerBottom / gamePanel.tileSize;

        switch (character.direction) {
            case "left":
                X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                if (X_Left == centerX && Y_Top == centerY ||
                        X_Left == centerX && Y_Bottom == centerY) {
                    character.collision = true;
                }
                break;
            case "right":
                X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                if (X_Right == centerX && Y_Top == centerY ||
                        X_Right == centerX && Y_Bottom == centerY) {
                    character.collision = true;
                }
                break;
            case "up":
                Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                if (X_Left == centerX  && Y_Top == centerY ||
                        X_Right == centerX && Y_Top == centerY) {
                    character.collision = true;
                }
                break;
            case "down":
                Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                if (X_Left == centerX && Y_Bottom == centerY ||
                        X_Right == centerX && Y_Bottom == centerY) {
                    character.collision = true;
                }
                break;
        }
    }

    /**
     * Checks for collisions between the character and a tree object.
     *
     * @param character The character to check for collisions.
     * @param centerX   The x-coordinate of the tree's center in tile coordinates.
     * @param centerY   The y-coordinate of the tree's center in tile coordinates.
     */
    public void CheckTreeCollision(Character character, int centerX, int centerY)
    {

        int X_Left = playerLeft / gamePanel.tileSize;
        int X_Right = playerRight / gamePanel.tileSize;
        int Y_Top = playerTop / gamePanel.tileSize;
        int Y_Bottom = playerBottom / gamePanel.tileSize;

        switch(character.direction)
        {
            case "left":
                X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                if(X_Left == centerX && Y_Top == centerY ||
                   X_Left == centerX && Y_Bottom == centerY)
                {
                    character.collision = true;
                }break;
            case "right":
                X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                if(X_Right == centerX && Y_Top == centerY ||
                        X_Right == centerX && Y_Bottom == centerY)
                {
                    character.collision = true;
                }break;
            case "up":
                Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                if(X_Left == centerX && Y_Top == centerY ||
                        X_Right == centerX && Y_Top == centerY)
                {
                    character.collision = true;
                }break;
            case "down":
                Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                if(X_Left == centerX && Y_Bottom == centerY||
                        X_Right == centerX && Y_Bottom == centerY)
                {
                    character.collision = true;
                }break;
            }
    }

    /**
     * Checks if the character should be hidden behind an object.
     *
     * @param character The character to check for visibility.
     */
    public void CheckObjectVisibility(Character character)
    {
        int charX,charY,centerX,centerY;
        charX = character.x/gamePanel.tileSize;
        charY = character.y/gamePanel.tileSize;
        if(!gamePanel.map.mapSwap)
        {
            for (int i = 0, n = gamePanel.trees[0].length; i < n; i++) {

                if(abs(charX- gamePanel.trees[1][i].x) <= 1 && abs(charY - gamePanel.trees[1][i].y) <= 1)
                {
                    centerX = gamePanel.trees[1][i].x;
                    centerY = gamePanel.trees[1][i].y;
                    CheckTreeCollision(character, gamePanel.trees[0][i].x, gamePanel.trees[0][i].y);
                    if ((charX == centerX || charX == centerX-1) && (charY == centerY ||charY == centerY-1)) {
                        character.visibility = false;
                    }
                }
            }

            for (int i = 0; i < 3; i++)
            {

                if (abs(charX - gamePanel.airvent.x) <= 4 && abs(charY - gamePanel.airvent.y) <= 3) {
                    CheckObject_2width(character, gamePanel.airvent.x, gamePanel.airvent.y);
                    centerX = gamePanel.airvent.x + i;
                    centerY = gamePanel.airvent.y + i;
                    if ((charX == centerX) && (charY == centerY - (i+1) )) {
                        character.visibility = false;
                    }
                }
            }

            centerX = gamePanel.ButtonState.x;
            centerY = gamePanel.ButtonState.y;
            if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
                CheckObjectCollision(character,centerX,centerY);
                if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                    character.visibility = false;
                }
            }

            centerX = gamePanel.ButtonElevationUp.x;
            centerY = gamePanel.ButtonElevationUp.y;
            if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
                CheckObjectCollision(character, centerX, centerY);
                if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                    character.visibility = false;
                }
            }

            centerX = gamePanel.ButtonElevationDown.x;
            centerY = gamePanel.ButtonElevationDown.y;
            if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
                CheckObjectCollision(character, centerX, centerY);
                if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                    character.visibility = false;
                }
            }

            for(int i = 0, n = gamePanel.catnips.length; i < n; i++)
            {
                centerX = gamePanel.catnips[i].x;
                centerY = gamePanel.catnips[i].y;
                if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1)
                {
                    if ((charX == centerX || charX == centerX-1) &&
                            (((character.y+28)/gamePanel.tileSize) == centerY-1))
                    {
                        character.visibility = false;
                    }
                }
            }

            if(!gamePanel.cat.throwAction)
            {
                centerX = gamePanel.cat.x / gamePanel.tileSize;
                centerY = gamePanel.cat.y / gamePanel.tileSize;
                if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1)
                {
                    if (!gamePanel.cat.stop )
                    {
                        CheckObjectCollision(character, centerX, centerY);
                    }
                    else
                    {
                        CheckObjectCollision(character, 14, 29);
                    }
                    if ((charX == centerX || charX == centerX - 1) && (charY == centerY - 1))
                    {
                        if (!gamePanel.cat.stop) {
                            character.visibility = false;
                        }
                    }
                }
            }

            if(gamePanel.bunker.materialize)
            {
                centerX = gamePanel.bunker.x;
                centerY = gamePanel.bunker.y;
                if (abs(charX - centerX) <= 2 && abs(charY - centerY) <= 2)
                {
                    CheckObject_2width(character,centerX-1,centerY);
                    if ((charX == centerX || charX == centerX-1 || charX == centerX+1 ) && (charY == centerY-1))
                    {

                        character.visibility = false;
                    }
                }
            }
        }

        else
        {
            centerX = gamePanel.bunkerComputer.x;
            centerY = gamePanel.bunkerComputer.y;
            if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
                CheckObjectCollision(character, centerX, centerY);
                if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                    character.visibility = false;
                }
            }

            centerX = gamePanel.cat.x / gamePanel.tileSize;
            centerY = gamePanel.cat.y / gamePanel.tileSize;
            if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1)
            {
                CheckObjectCollision(character, centerX, centerY);
                if ((charX == centerX || charX == centerX - 1) && (charY == centerY - 1))
                {
                    character.visibility = false;
                }
            }
        }
    }

    /**
     * Checks for collisions with the bridge, taking into account the bridge's state.
     *
     * @param character The character to check for collisions.
     */
    public void CheckBridgeCollision(Character character)
    {
        int charX = character.x/gamePanel.tileSize;
        int charY = character.y/gamePanel.tileSize;
        collisionCheck = true;
        if(gamePanel.ButtonState.traversable)
        {
            if((charX == 23 && charY == 13) || (charX == 22 && charY == 13) || (charX == 21 && charY == 13)
            ||(charX == 20 && charY == 13) || (charX == 19 && charY == 13) || (charX == 18 && charY == 13))
            {
                if((character.y-33)/gamePanel.tileSize == 13 &&
                        (character.y+13)/gamePanel.tileSize == 13)
                {
                    collisionCheck = false;
                }
            }
        }
    }
}