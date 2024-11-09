package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TreeTop extends Object {

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
