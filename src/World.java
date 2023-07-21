import javax.swing.*;
import java.awt.*;

public class World extends JPanel {
    private int level = 1;
    private int rows = 5;
    private int columns = 5;
    World(){

        DataSource.getInstance().setGrid(rows, columns);
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
                Rectangle rect =  DataSource.getInstance().getGrid().get(i).get(j).getRect();
                g.setColor(Color.BLACK);
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
            }
        }
    }

}
