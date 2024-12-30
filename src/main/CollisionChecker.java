package main;
import character.Character;

import static java.lang.Math.abs;

public class CollisionChecker
{
    GamePanel gamePanel;

    public int playerLeft,playerRight,playerTop,playerBottom;
    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void CheckTileCollision(Character character)
    {
        playerLeft = (character.x - gamePanel.tileSize) + (gamePanel.tileSize - (gamePanel.tileSize / 2)) + gamePanel.tileSize;
        playerRight = (character.x + gamePanel.tileSize) -  (gamePanel.tileSize - (gamePanel.tileSize / 2));
        playerTop = (character.y - gamePanel.tileSize) + (int) (gamePanel.tileSize - (gamePanel.tileSize / 2.6)) + gamePanel.tileSize;
        playerBottom = (character.y + gamePanel.tileSize) - (gamePanel.tileSize - (gamePanel.tileSize / 2));

        // Corner coordinates of character BOX;
        int X_Left = playerLeft / gamePanel.tileSize;
        int X_Right = playerRight / gamePanel.tileSize;
        int Y_Top = playerTop / gamePanel.tileSize;
        int Y_Bottom = playerBottom / gamePanel.tileSize;

        int mapIndex1,mapIndex2;

        switch(character.direction)
        {
            case "left":
                X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Left];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "right":
                X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Right];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "up":
                Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Top][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "down":
                Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Bottom][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
        }
    }
    public void CheckObjectAirVentCollision(Character character)
    {
        for (int i = 0; i < 2; i++) {
            int X_Left = playerLeft / gamePanel.tileSize;
            int X_Right = playerRight / gamePanel.tileSize;
            int Y_Top = playerTop / gamePanel.tileSize;
            int Y_Bottom = playerBottom / gamePanel.tileSize;

            switch (character.direction) {
                case "left":
                    X_Left = (playerLeft - character.speed) / gamePanel.tileSize;
                    if (X_Left == gamePanel.airvent.x + (i+1) && Y_Top == gamePanel.airvent.y ||
                            X_Left == gamePanel.airvent.x + (i+1) && Y_Bottom == gamePanel.airvent.y) {
                        character.collision = true;
                    }
                    break;
                case "right":
                    X_Right = (playerRight + character.speed) / gamePanel.tileSize;
                    if (X_Right == gamePanel.airvent.x + (i+1) && Y_Top == gamePanel.airvent.y ||
                            X_Right == gamePanel.airvent.x + (i+1) && Y_Bottom == gamePanel.airvent.y) {
                        character.collision = true;
                    }
                    break;
                case "up":
                    Y_Top = (playerTop - character.speed) / gamePanel.tileSize;
                    if (X_Left == gamePanel.airvent.x + (i+1) && Y_Top == gamePanel.airvent.y ||
                            X_Right == gamePanel.airvent.x + (i+1) && Y_Top == gamePanel.airvent.y) {
                        character.collision = true;
                    }
                    break;
                case "down":
                    Y_Bottom = (playerBottom + character.speed) / gamePanel.tileSize;
                    if (X_Left == gamePanel.airvent.x + (i+1) && Y_Bottom == gamePanel.airvent.y ||
                            X_Right == gamePanel.airvent.x + (i+1) && Y_Bottom == gamePanel.airvent.y) {
                        character.collision = true;
                    }
                    break;
            }
        }
    }
    public void CheckButtonCollision(Character character, int centerX, int centerY)
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

    public void CheckObjectVisibility(Character character)
    {
        int charX,charY,centerX,centerY;
        for (int i = 0, n = gamePanel.trees[0].length; i < n; i++) {
            charX = character.x/gamePanel.tileSize;
            charY = character.y/gamePanel.tileSize;
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
            charX = character.x / gamePanel.tileSize;
            charY = character.y / gamePanel.tileSize;
            if (abs(charX - gamePanel.airvent.x) <= 4 && abs(charY - gamePanel.airvent.y) <= 3) {
                CheckObjectAirVentCollision(character);
                centerX = gamePanel.airvent.x + i;
                centerY = gamePanel.airvent.y + i;
                if ((charX == centerX) && (charY == centerY - (i+1) )) {
                    character.visibility = false;
                }
            }
        }


        charX = character.x / gamePanel.tileSize;
        charY = character.y / gamePanel.tileSize;
        centerX = gamePanel.ButtonState.x;
        centerY = gamePanel.ButtonState.y;
        if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
            CheckButtonCollision(character,centerX,centerY);
            if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                character.visibility = false;
            }
        }


        centerX = gamePanel.ButtonElevation.x;
        centerY = gamePanel.ButtonElevation.y;
        if (abs(charX - centerX) <= 1 && abs(charY - centerY) <= 1) {
            CheckButtonCollision(character, centerX, centerY);
            if ((charX == centerX || charX == centerX-1) && (charY == centerY-1)) {

                character.visibility = false;
            }
        }
    }

}