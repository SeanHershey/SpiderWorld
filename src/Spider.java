import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Spider extends JPanel {
    private int posX = 23;
    private int posY = 120;
    public static final Color BACKGROUND_GRAY = new Color(230, 230, 230);
    private int angle = 0;
    private ImageObserver observer;
    private String[] pic = {"000.png", "090.png", "180.png", "270.png"};
    int index = 0;
    private String[] dir = {"North", "East", "South", "West"};

    public void draw(Graphics g) throws IOException {
        BufferedImage image = null;
        System.out.println(this.pic[index]);
        String st = this.pic[index];
        st.replace('"', ' ');
        File file = new File(st);
        System.out.println("2!!!!!!");
        image = ImageIO.read(file);
        System.out.println("!!!!!!");
        g.drawImage(image, posX, posY, observer);

    }

    public String getDirection(){
        System.out.println(this.dir[index]);
        return this.dir[index];

    }
    public String getPic(){
        return this.pic[index];
    }
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setX(int x) {
        posX = x;
    }

    public void setY(int y) {
        posY = y;
    }
    public void step() {
        if(this.pic[this.index].equals( "000.png")){
            posY = posY - 52;
            repaint();
        }
        if(this.pic[this.index].equals( "090.png")){
            System.out.println("got it");
            posX = posX + 52;
            repaint();
        }
        if(this.pic[this.index].equals( "180.png")){
            posY = posY + 52;
            repaint();
        }
        if(this.pic[this.index].equals("270.png")){
            posX = posX - 52;
            repaint();
        }



    }



    public void turn() {
        System.out.println("TUUUUUUUUURNING");
        if (this.angle == 0) {
            this.angle = 90;
            this.index = 1;

        } else if (this.angle == 90) {
            this.angle = 180;
            this.index = 2;
        } else if (this.angle == 180) {
            this.angle = 270;
            this.index = 3;
        } else {
            this.angle = 0;
            this.index = 0;
        }

    }}








