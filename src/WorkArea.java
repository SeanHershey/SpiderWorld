import javax.swing.*;
import java.awt.*;

public class WorkArea extends JPanel {
    public WorkArea() {
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.darkGray);
        eastPanel.setPreferredSize(new Dimension(200, 0));

        JLabel l1 = new JLabel("Pallete");
        eastPanel.add(l1);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        JButton button1 = new JButton("Step");
        JButton button2 = new JButton("Turn");
        JButton button3 = new JButton("Paint{color}");

        button1.setPreferredSize(new Dimension(80, 30));
        button2.setPreferredSize(new Dimension(80, 30));
        button3.setPreferredSize(new Dimension(80, 30));
        eastPanel.add(button1);
        eastPanel.add(button2);
        eastPanel.add(button3);

        add(eastPanel, BorderLayout.EAST);
    }

    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.GRAY);
        g2.drawString("WorkArea", 100, 100);

        // trashcan
        int lidHeight = 10;
        int trashcanWidth = 45;
        int trashcanHeight = 60;
        int xPosition = 20;
        int yPosition = getHeight() - trashcanHeight - 20;

        g2.setColor(Color.BLACK);
        g2.fillRect(xPosition + 5, yPosition, trashcanWidth, trashcanHeight);
        g2.fillRect(xPosition, yPosition - lidHeight - 2, trashcanWidth + 10, lidHeight);
        g2.fillRect(trashcanWidth/2 + xPosition - 5, yPosition - lidHeight - 7, trashcanWidth/2, lidHeight);
    }
}
