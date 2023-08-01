import java.awt.*;

public class Block implements Shape {
    private String type;
    Rectangle bounds;
    private boolean pressOut;
    private int preX;
    private int preY;
    private Shape above;
    private Shape below;
    private Color color = Color.darkGray;

    public Block(int x, int y, String t) {
        this.type = t;
        if(type == "Loop"){
            bounds = new Rectangle(x-20, y, 140, 30 );
        }
        else{
            bounds = new Rectangle(x, y, 100, 30);
        }
        this.pressOut = false;
        this.preX = x;
        this.preY = y;
    }

    public void paint(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 5, 5);
        int [] xPoints = {bounds.x+40, bounds.x+50, bounds.x+60};
        int [] yPoints = {bounds.y+30, bounds.y+36, bounds.y+30};
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.white);
        yPoints[0] = bounds.y; 
        yPoints[1] = bounds.y + 6; 
        yPoints[2] = bounds.y; 
        g2.fillPolygon(xPoints, yPoints, 3);
        
        if (type != "Step" && type != "Turn" && type != "Loop" && type != "lStep" && type != "lTurn") {
            System.out.println(type);
            g2.setColor(Color.RED);
            g2.fillRoundRect(bounds.x +55, bounds.y + 5, 10, bounds.height - 10, 1, 1);
            g2.setColor(Color.GREEN);
            g2.fillRoundRect(bounds.x +70, bounds.y + 5, 10, bounds.height - 10, 1, 1);
            g2.setColor(Color.BLUE);
            g2.fillRoundRect(bounds.x +85, bounds.y + 5, 10, bounds.height - 10, 1, 1);
            g2.setColor(Color.white);
            g2.drawString("Color", bounds.x + 10, bounds.y + 20);
        }
        else {
            g2.drawString(type, bounds.x + 10, bounds.y + 20);
        }
    }

    public int getX() { return bounds.x; }
    public int getY() { return bounds.y; }

    public void setPreX(int x) { preX = x; }
    public void setPreY(int y) { preY = y; }
    public int getPreX() { return preX; }
    public int getPreY() { return preY; }
    public void setBelow(Shape b) {  below = b; };
    public Shape getBelow() { return below; };
    public void setAbove(Shape b) {  above = b; };
    public Shape getAbove() { return above; };
    @Override
    public String getType() {
        return type;
    };
    public void setColor(Color c) { color = c; }
    public Color getColor() { return color; }

    public void clicked(int x, int y) {
        if (type != "Step" && type != "Turn" && type != "Loop" && type != "lStep" && type != "lTurn") {
            if (x - bounds.x > 80) {
                color = Color.BLUE;
                type = "Blue";
            }
            else if (x - bounds.x > 65) {
                color = Color.GREEN;
                type = "Green";
            }
            else if (x - bounds.x > 50) {
                color = Color.RED;
                type = "Red";
            }
        }
    }

    public void setPressOut(boolean a) { pressOut = a; }
    public boolean getPressOut() { return pressOut; }

    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }
}
