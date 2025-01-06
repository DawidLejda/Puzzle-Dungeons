package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the top part of a tree object in the game.
 */
public class TreeTop extends Object {

    /**
     * Constructor for the TreeTop class.
     * Loads the image for the tree top and sets its properties.
     */
    public TreeTop() {
        name = "tree";
        collision = false;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/tree/tree1_top.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}