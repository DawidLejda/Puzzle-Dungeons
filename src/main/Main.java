package main;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame GameFrame = new JFrame();
        GameFrame.setTitle("Puzzle Dungeons");
        GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameFrame.setResizable(false);

        // TO DO
        //ImageIcon image = new ImageIcon();
        //this.setIconImage(image.getImage());

        GamePanel game_panel = new GamePanel();
        GameFrame.add(game_panel);
        GameFrame.pack();

        GameFrame.setLocationRelativeTo(null);
        GameFrame.setVisible(true);

        game_panel.startGameThread();
    }
}
