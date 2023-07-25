import java.awt.*;

public class Block {
    private String type;
    Rectangle bounds;
    private boolean pressOut;
    private int preX;
    private int preY;
    private Block above;
    private Block below;

    public Block(int x, int y, String t) {
        this.type = t;
        bounds = new Rectangle(x, y, 100, 30);
        this.pressOut = false;
        this.preX = x;
        this.preY = y;
    }

    public void paint(Graphics2D g2) {
        g2.setColor(Color.gray);
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 5, 5);
        int [] xPoints = {bounds.x+40, bounds.x+50, bounds.x+60};
        int [] yPoints = {bounds.y+30, bounds.y+36, bounds.y+30};
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.white);
        yPoints[0] = bounds.y; 
        yPoints[1] = bounds.y + 6; 
        yPoints[2] = bounds.y; 
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.drawString(type, bounds.x + 10, bounds.y + 20);
    }

    public int getX() { return bounds.x; }
    public int getY() { return bounds.y; }

    public void setPreX(int x) { preX = x; }
    public void setPreY(int y) { preY = y; }
    public int getPreX() { return preX; }
    public int getPreY() { return preY; }
    public void setBelow(Block b) {  below = b; };
    public Block getBelow() { return below; };
    public void setAbove(Block b) {  above = b; };
    public Block getAbove() { return above; };
    public String getType() {return type;};

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
