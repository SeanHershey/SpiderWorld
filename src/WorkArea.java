import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;

public class WorkArea extends JPanel implements MouseListener, MouseMotionListener {
    public WorkArea() {
        addMouseListener(this);
        addMouseMotionListener(this);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.white);
        eastPanel.setPreferredSize(new Dimension(150, 0));
        Border blackline = BorderFactory.createLineBorder(Color.gray);
        eastPanel.setBorder(blackline);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        JButton button1 = new JButton("Step");
        JButton button2 = new JButton("Turn");
        JButton button3 = new JButton("Paint");

        button1.setPreferredSize(new Dimension(80, 30));
        button2.setPreferredSize(new Dimension(80, 30));
        button3.setPreferredSize(new Dimension(80, 30));
        eastPanel.add(button1);
        eastPanel.add(button2);
        eastPanel.add(button3);

        add(eastPanel, BorderLayout.EAST);

        DataSource.getInstance().addBlock(300,100,"Turn");
        DataSource.getInstance().addBlock(300,150,"Step");
        DataSource.getInstance().addBlock(300,200,"Color");
    }

    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.white);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        super.setBorder(blackline);

        g2.setColor(Color.lightGray);
        g2.fillRoundRect(20,680,35,40,10,10);
        g2.setColor(getBackground());
        g2.fillRect(20,670,35,15);
        g2.setColor(Color.lightGray);
        g2.fillRect(17,675,42,6);
        g2.fillRoundRect(27,671,21,6,5,5);

        for (Block block : DataSource.getInstance().getBlockList()) {
            block.paint(g2);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Block block : DataSource.getInstance().getBlockList()) {
            block.setPreX((int) (block.getX() - e.getX()));
            block.setPreY((int) (block.getY() - e.getY()));
            if (block.contains(e.getX(), e.getY())) {
                block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                repaint();
                break;
            } else {
                block.setPressOut(true);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (Block block : DataSource.getInstance().getBlockList()) {
            if (!block.getPressOut()) {
                block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                repaint();
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (Block block : DataSource.getInstance().getBlockList()) {
            block.setPreX((int) (block.getX() - e.getX()));
            block.setPreY((int) (block.getY() - e.getY()));
            if (block.contains(e.getX(), e.getY())) {
                block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                repaint();
                break;
            } else {
                block.setPressOut(false);
            }
        }
    }

    public void mouseMoved(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}
