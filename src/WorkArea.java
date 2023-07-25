import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;

// need to check if the block's location is valid in mouseReleased
public class WorkArea extends JPanel implements MouseListener, MouseMotionListener {
    private int x, y;
    private JPanel duplicatePanel;

    public WorkArea() {
        addMouseListener(this);
        addMouseMotionListener(this);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(180, 180, 180, 80));
        eastPanel.setPreferredSize(new Dimension(150, 0));
        // Border blackline = BorderFactory.createLineBorder(Color.gray);
        // eastPanel.setBorder(blackline);

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
                x = blockPanel.getX() + e.getX() - blockPanel.getWidth()/2;
                y = blockPanel.getY() + e.getY() - blockPanel.getHeight()/2;
                
                duplicatePanel = new JPanel();
                duplicatePanel.setPreferredSize(new Dimension(100, 30));

                JLabel duplicateLabel = new JLabel(label);
                duplicatePanel.add(duplicateLabel);
                duplicatePanel.setBounds(x - blockPanel.getParent().getWidth(), y, blockPanel.getWidth(), blockPanel.getHeight());

                // Add the duplicate panel to the grandParent panel, then repaint
                Container grandParent = parentPanel.getParent();
                if (grandParent instanceof JPanel) {
                    ((JPanel) grandParent).setLayout(null);
                    ((JPanel) grandParent).add(duplicatePanel);
                    ((JPanel) grandParent).setComponentZOrder(duplicatePanel, 0);
                }

                parentPanel.revalidate();
                parentPanel.repaint();
                System.out.println("mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Container grandParent = parentPanel.getParent();
                if (grandParent instanceof JPanel) {
                    ((JPanel) grandParent).remove(duplicatePanel);
                }
                parentPanel.revalidate();
                parentPanel.repaint();
                System.out.println("mouseReleased");
                DataSource.getInstance().addBlock(x + parentPanel.getParent().getWidth() - parentPanel.getWidth() ,y ,label);
            }
        });

        blockPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Update the position of the duplicate panel to follow the cursor
                x = blockPanel.getX() + e.getX() - blockPanel.getWidth()/2;
                y = blockPanel.getY() + e.getY() - blockPanel.getHeight()/2;
                duplicatePanel.setBounds(x + parentPanel.getParent().getWidth() - parentPanel.getWidth() , y , duplicatePanel.getWidth(), duplicatePanel.getHeight());
                parentPanel.revalidate();
                parentPanel.repaint();
                parentPanel.getParent().revalidate();
                parentPanel.getParent().repaint();
                System.out.println("mouseDragged");
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

        // trash can
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
