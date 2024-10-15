package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame
{
    public GameFrame()
    {
        this.setTitle("Puzzle Dungeons");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // TO DO
        //ImageIcon image = new ImageIcon();
        //this.setIconImage(image.getImage());

        GamePanel game_panel = new GamePanel();
        this.add(game_panel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
