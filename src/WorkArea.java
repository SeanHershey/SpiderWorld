import javax.swing.*;
import java.awt.*;

public class WorkArea extends JPanel {
    public WorkArea() {
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.BLACK);
        JLabel l1 = new JLabel("Pallete");
        eastPanel.add(l1);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(eastPanel, BorderLayout.EAST);
    }
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.GRAY);
        g2.drawString("WorkArea", 100, 100);
    }
}
