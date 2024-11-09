package main;
import character.Character;
public class CollisionChecker
{
    GamePanel gamePanel;
    public int mapIndex1,mapIndex2;

    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void CheckTileCollision(Character character)
    {
        int[] X = new int[2];
        int[] Y = new int[2];
        // Character pixel characterBOX : X-left[0]/right[1], Y-top[0]/bottom[1]
        X[0] = (character.x - gamePanel.tileSize) + (int) (gamePanel.tileSize - (gamePanel.tileSize / 2)) + gamePanel.tileSize;
        X[1] = (character.x + gamePanel.tileSize) - (int) (gamePanel.tileSize - (gamePanel.tileSize / 2));
        Y[0] = (character.y - gamePanel.tileSize) + (int) (gamePanel.tileSize - (gamePanel.tileSize / 2.6)) + gamePanel.tileSize;
        Y[1] = (character.y + gamePanel.tileSize) - (int) (gamePanel.tileSize - (gamePanel.tileSize / 2));

        // Corner coordinates of character BOX;
        int X_Left = X[0] / gamePanel.tileSize;
        int X_Right = X[1] / gamePanel.tileSize;
        int Y_Top = Y[0] / gamePanel.tileSize;
        int Y_Bottom = Y[1] / gamePanel.tileSize;


        int characterX = X[0] / gamePanel.tileSize;
        int characterY = Y[0] / gamePanel.tileSize;
        if(gamePanel.map.starting_area[characterY][characterX] == 52)
        {
            character.visibility = false;
        }

        switch(character.direction)
        {
            case "left":

                X_Left = (X[0] - character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Left];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "right":
                X_Right = (X[1] + character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Right];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "up":
                Y_Top = (Y[0] - character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Top][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Top][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
            case "down":
                Y_Bottom = (Y[1] + character.speed) / gamePanel.tileSize;
                mapIndex1 = gamePanel.map.starting_area[Y_Bottom][X_Left];
                mapIndex2 = gamePanel.map.starting_area[Y_Bottom][X_Right];
                if(gamePanel.map.tile[mapIndex1].collision || gamePanel.map.tile[mapIndex2].collision)
                {
                    character.collision = true;
                }
                break;
        }
    }

    public void CheckObjectCollision(Character character, Object object)
    {

    }
}