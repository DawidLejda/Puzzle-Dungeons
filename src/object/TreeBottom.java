package object;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TreeBottom extends Object
{
    public TreeBottom() {
        name = "tree";
        collision = false;
        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/tree/tree1_bottom.png")));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

