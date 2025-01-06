package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the bottom part of a tree object in the game.
 */
public class TreeBottom extends Object {

    /**
     * Constructor for the TreeBottom class.
     * Loads the image for the tree bottom and sets its properties.
     */
    public TreeBottom() {
        name = "tree";
        collision = false;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/tree/tree1_bottom.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}