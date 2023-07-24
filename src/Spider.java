import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spider {
    private int posX= 123;
    private int posY= 222;
    public static final Color BACKGROUND_GRAY = new Color(230,230,230);

    public void draw(Graphics g) throws IOException {
        BufferedImage image = null;
        File file = new File("src/spider-png-26238.png");
        image = ImageIO.read(file);
        g.drawImage(image, posX, posY, null);

    }


    public int getX(){return posX;}

    public int getY(){return posY;}

    public void setX(int x){ posX = x;}

    public void setY(int y ){posY = y;}


    public void step()
    {
        posY -= 30;
    }

    public void left()
    {
        posX -= 30;
    }

    public void turn()
    {

    }



}


