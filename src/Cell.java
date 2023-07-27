import java.awt.*;

public class Cell extends Rectangle {
    private Color c = Color.BLACK;
    private int id;
    private boolean spider;
    private Rectangle rect;

    Cell(Color color, int id, boolean spider, int x, int y, int width, int height){
       /* this.c = color; */
        this.id = id;
        if(this.id == 0){this.spider = true;}
        else{this.spider = false;}
        this.rect = new Rectangle(x, y, width, height);
    }

    public Color getColor() {
        System.out.println("in get color");
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
