import java.awt.*;

public class Cell extends Rectangle {
    private Color color = Color.RED;
    private int id;
    private boolean spider;
    private Rectangle rect;

    Cell(Color color, int id, boolean spider, int x, int y, int width, int height){
        this.color = color;
        this.id = id;
        if(this.id == 0){this.spider = true;}
        else{this.spider = false;}
        this.rect = new Rectangle(x, y, width, height);
    }

    public Color getColor() {
        return color;
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



}
