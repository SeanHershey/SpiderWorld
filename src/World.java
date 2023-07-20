import javax.swing.*;
import java.awt.*;

public class World extends JPanel {
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.WHITE);
        g2.drawString("World", 100, 100);
    }
}
