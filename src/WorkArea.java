import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;

public class WorkArea extends JPanel implements MouseListener, MouseMotionListener {
    private JPanel dragPanel;
    private int offsetX, offsetY;
    private JPanel duplicatePanel; // Store the duplicate panel

    public WorkArea() {
        addMouseListener(this);
        addMouseMotionListener(this);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.white);
        eastPanel.setPreferredSize(new Dimension(120, 0));
        Border blackline = BorderFactory.createLineBorder(Color.gray);
        eastPanel.setBorder(blackline);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(eastPanel, BorderLayout.EAST);

        addPaletteBlock(eastPanel, "Turn");
        addPaletteBlock(eastPanel, "Step");
        addPaletteBlock(eastPanel, "Color");
    }

    private void addPaletteBlock(JPanel parentPanel, String label) {
        JPanel blockPanel = new JPanel();
        blockPanel.setPreferredSize(new Dimension(100, 30));

        JLabel blockLabel = new JLabel(label);
        blockPanel.add(blockLabel);

        blockPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                blockPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
                blockPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                blockPanel.setBorder(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = blockPanel.getX() + e.getX() - blockPanel.getWidth()/2;
                int y = blockPanel.getY() + e.getY() - blockPanel.getHeight()/2;

                if (label == "Turn") {
                    DataSource.getInstance().addBlock(300,10,label);
                }
                if (label == "Step") {
                    DataSource.getInstance().addBlock(300,50,label);
                }
                if (label == "Color") {
                    DataSource.getInstance().addBlock(300,90,label);
                }

                duplicatePanel = new JPanel();
                duplicatePanel.setPreferredSize(new Dimension(100, 30));

                JLabel duplicateLabel = new JLabel(label);
                duplicatePanel.add(duplicateLabel);

                duplicatePanel.setBounds(x, y, blockPanel.getWidth(), blockPanel.getHeight());
                offsetX = e.getX() - blockPanel.getWidth()/2;
                offsetY = e.getY() - blockPanel.getHeight()/2;

                // Add the duplicate panel to the parent panel, then repaint
                parentPanel.setLayout(null);
                parentPanel.add(duplicatePanel);
                parentPanel.setComponentZOrder(duplicatePanel, 0);
                parentPanel.revalidate();
                parentPanel.repaint();
                // System.out.println("mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                parentPanel.remove(duplicatePanel);
                parentPanel.revalidate();
                parentPanel.repaint();
                // System.out.println("mouseReleased");
            }
        });

        blockPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Update the position of the duplicate panel to follow the cursor
                int x = e.getX() - offsetX;
                int y = e.getY() - offsetY;
                duplicatePanel.setBounds(x, y, duplicatePanel.getWidth(), duplicatePanel.getHeight());
                parentPanel.revalidate();
                parentPanel.repaint();
                
                // System.out.println("mousedragged");
            }
        });

        parentPanel.add(blockPanel);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.white);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        super.setBorder(blackline);

        g2.setColor(Color.lightGray);
        g2.fillRoundRect(20, 680, 35, 40, 10, 10);
        g2.setColor(getBackground());
        g2.fillRect(20, 670, 35, 15);
        g2.setColor(Color.lightGray);
        g2.fillRect(17, 675, 42, 6);
        g2.fillRoundRect(27, 671, 21, 6, 5, 5);

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
                ConnectHelper.snap(block);
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
