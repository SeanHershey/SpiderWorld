import java.awt.*;

public interface Shape {
    String getType();

    public void paint(Graphics2D g2);
    public int getX() ;
    public int getY() ;

    public void setPreX(int x) ;
    public void setPreY(int y) ;
    public int getPreX() ;
    public int getPreY() ;
    public void setBelow(Shape b) ;
    public Shape getBelow() ;
    public void setAbove(Shape b) ;
    public Shape getAbove() ;
    public void setColor(Color c) ;
    public Color getColor() ;

    public void clicked(int x, int y);

    public void setPressOut(boolean a);
    public boolean getPressOut() ;

    public void move(int x, int y);

    public boolean contains(int x, int y) ;
    }


