package object;

import main.GamePanel;


public class ObjectPlacement extends Object
{
    GamePanel gamePanel;
    public ObjectPlacement(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void TreePlacement()
    {

        //*******************
        gamePanel.trees[1][0] = new TreeTop();
        gamePanel.trees[1][0].x = 22;
        gamePanel.trees[1][0].y = 8;

        gamePanel.trees[1][1] = new TreeTop();
        gamePanel.trees[1][1].x = 20;
        gamePanel.trees[1][1].y = 6;

        gamePanel.trees[1][2] = new TreeTop();
        gamePanel.trees[1][2].x = 33;
        gamePanel.trees[1][2].y = 10;

        gamePanel.trees[1][3] = new TreeTop();
        gamePanel.trees[1][3].x = 37;
        gamePanel.trees[1][3].y = 14;

        gamePanel.trees[1][4] = new TreeTop();
        gamePanel.trees[1][4].x = 43;
        gamePanel.trees[1][4].y = 12;
        //*******************
        gamePanel.trees[0][0] = new TreeBottom();
        gamePanel.trees[0][0].x = 22;
        gamePanel.trees[0][0].y = 9;

        gamePanel.trees[0][1] = new TreeBottom();
        gamePanel.trees[0][1].x = 20;
        gamePanel.trees[0][1].y = 7;

        gamePanel.trees[0][2] = new TreeBottom();
        gamePanel.trees[0][2].x = 33;
        gamePanel.trees[0][2].y = 11;

        gamePanel.trees[0][3] = new TreeBottom();
        gamePanel.trees[0][3].x = 37;
        gamePanel.trees[0][3].y = 15;

        gamePanel.trees[0][4] = new TreeBottom();
        gamePanel.trees[0][4].x = 43;
        gamePanel.trees[0][4].y = 13;

    }


    public void CatnipPlacement()
    {
        gamePanel.catnips[0] = new Catnip(gamePanel);
        gamePanel.catnips[0].x = 33;
        gamePanel.catnips[0].y = 15;

        gamePanel.catnips[1] = new Catnip(gamePanel);
        gamePanel.catnips[1].x = 44;
        gamePanel.catnips[1].y = 10;

        gamePanel.catnips[2] = new Catnip(gamePanel);
        gamePanel.catnips[2].x = 21;
        gamePanel.catnips[2].y = 7;

        gamePanel.catnips[3] = new Catnip(gamePanel);
        gamePanel.catnips[3].x = 40;
        gamePanel.catnips[3].y = 8;
    }

    public void Catnip_TrailPlacement()
    {
        gamePanel.player.speed = 2;
        int charX = (gamePanel.player.x+10)/gamePanel.tileSize;
        int charY = (gamePanel.player.y+10)/gamePanel.tileSize;
        int i;
        for(i = 0; i < gamePanel.trail.catnipSteps; i++)
        {
            if(gamePanel.trail.catnipPathX[i] != 0)
            {
                gamePanel.catnipTrails[i] = new Object();
                gamePanel.catnipTrails[i].x = gamePanel.trail.catnipPathX[i];
                gamePanel.catnipTrails[i].y = gamePanel.trail.catnipPathY[i];

                if(gamePanel.trail.randomTrail[i] == 0)
                {
                    gamePanel.catnipTrails[i].image = gamePanel.trail.trailsIMG[0].image;
                }
                else
                {
                    gamePanel.catnipTrails[i].image = gamePanel.trail.trailsIMG[1].image;
                }



            }
        }
        i++;
        if(gamePanel.event.catnipsCount > 0 && !gamePanel.trail.stopTrailPlacement &&
            charX != gamePanel.trail.catnipPathX[i-1] || charY != gamePanel.trail.catnipPathY[i-1])
        {
            gamePanel.trail.catnipSteps++;
            gamePanel.trail.catnipPathX[gamePanel.trail.catnipSteps] = charX;
            gamePanel.trail.catnipPathY[gamePanel.trail.catnipSteps] = charY;
        }

        if(gamePanel.trail.catnipSteps <= 10)
        {
            gamePanel.event.catnipsCount = 3;
        }
        else if(gamePanel.trail.catnipSteps <= 20 &&  gamePanel.trail.catnipSteps > 11)
        {
            gamePanel.event.catnipsCount = 2;
        }
        else if(gamePanel.trail.catnipSteps <= 30 &&  gamePanel.trail.catnipSteps > 21)
        {
            gamePanel.event.catnipsCount = 1;
        }
        else if(gamePanel.trail.catnipSteps > 31)
        {
            gamePanel.event.catnipsCount = 0;
        }
    }
}
