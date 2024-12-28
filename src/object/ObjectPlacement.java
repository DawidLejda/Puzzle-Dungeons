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
        gamePanel.trees[0][0] = new TreeBottom();
        gamePanel.trees[0][0].x = 20;
        gamePanel.trees[0][0].y = 9;

        gamePanel.trees[0][1] = new TreeBottom();
        gamePanel.trees[0][1].x = 20;
        gamePanel.trees[0][1].y = 7;

        gamePanel.trees[0][2] = new TreeBottom();
        gamePanel.trees[0][2].x = 33;
        gamePanel.trees[0][2].y = 25;
        //*******************
        gamePanel.trees[1][0] = new TreeTop();
        gamePanel.trees[1][0].x = 20;
        gamePanel.trees[1][0].y = 8;

        gamePanel.trees[1][1] = new TreeTop();
        gamePanel.trees[1][1].x = 20;
        gamePanel.trees[1][1].y = 6;

        gamePanel.trees[1][2] = new TreeTop();
        gamePanel.trees[1][2].x = 33;
        gamePanel.trees[1][2].y = 24;
    }

}
