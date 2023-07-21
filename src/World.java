import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class World extends JPanel {
    private int level = 1;
    private int rows = 5;
    private int columns = 5;
    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();
    private Spider spider = new Spider();
    World(){
        for(int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j < columns; j++) {
                row.add( new Cell(Color.BLACK, (i * 5) + j, false, 20 + (50*j), 120 + (50 * i), 48, 48 ));
            }
            grid.add(row);
        }
        JButton step = new JButton("step");
        JButton turn = new JButton("turn");
        JButton red = new JButton("red");
        JButton blue = new JButton("blue");
        JButton green = new JButton("green");
        JButton black = new JButton("black");
        add(step);
        add(turn);
        add(red);
        add(blue);
        add(green);
        add(black);
    }

    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.white);

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                Rectangle rect =  grid.get(i).get(j).getRect();
                g.setColor(Color.BLACK);
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
            }
        }
        try {
            this.spider.draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
