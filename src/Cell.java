import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Cell extends Rectangle {
    private Color c;
    private int id;
    private boolean spider;
    private Rectangle rect;
    private boolean target;
    private Color target_color;

    Cell(Color color, int id, boolean spider, int x, int y, int width, int height, boolean target){
        this.target = target;
        this.c = color;
        this.id = id;
        if(this.id == 0){this.spider = true;}
        else{this.spider = false;}
        this.rect = new Rectangle(x, y, width, height);

        if(target){
            ArrayList<Color> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
            Random random = new Random();
            int randomNumber = random.nextInt(3);
            this.target_color = colors.get(randomNumber);

        }
    }

    public Boolean getTarget(){
        return this.target;
    }

    public Color getTargetColor(){
        return this.target_color;
    }
    public Color getColor() {
        return this.c;
    }
    public void setColor(Color colors){
        this.c = colors;
    }
    public Rectangle getRect(){
        return this.rect;
    }

    public int getId(){
        return this.id;
    }

    public boolean getSpider(){
        return this.spider;
    }
    public void setSpider(Boolean boo){
        this.spider = boo;
    }



}
