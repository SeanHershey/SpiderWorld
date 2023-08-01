import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.LinkedList;
import java.awt.*;

public class WorkArea extends JPanel implements MouseListener, MouseMotionListener {
    private int x, y;
    private JPanel duplicatePanel;

    public WorkArea() {
        addMouseListener(this);
        addMouseMotionListener(this);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(180, 180, 180, 80));
        eastPanel.setPreferredSize(new Dimension(150, 0));

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(eastPanel, BorderLayout.EAST);

        addPaletteBlock(eastPanel, "Turn");
        addPaletteBlock(eastPanel, "Step");
        addPaletteBlock(eastPanel, "Color");
        addPaletteBlock(eastPanel, "Loop");
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
                // System.out.println("mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Container grandParent = parentPanel.getParent();
                if (grandParent instanceof JPanel) {
                    ((JPanel) grandParent).remove(duplicatePanel);
                }
                parentPanel.revalidate();
                parentPanel.repaint();
                System.out.println("create");
                DataSource.getInstance().addBlock(x + parentPanel.getParent().getWidth() - parentPanel.getWidth() ,y ,label);
               ConnectHelper.snap(DataSource.getInstance().getBlockList().peek());
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
                // System.out.println("mouseDragged");
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
        g2.setColor(Color.darkGray);
        g2.fillRoundRect(20, 680, 35, 40, 10, 10);
        g2.setColor(getBackground());
        g2.fillRect(20, 670, 35, 15);
        g2.setColor(Color.darkGray);
        g2.fillRect(17, 675, 42, 6);
        g2.fillRoundRect(27, 671, 21, 6, 5, 5);

        LinkedList<Shape> unpainted = new LinkedList<Shape>();
        for (Shape s : DataSource.getInstance().getBlockList()) {
            unpainted.add(s);
        }
        while (unpainted.size() > 0) {
            int maxY = 0;
            Shape bottom = null;
            for (Shape s : unpainted) {
                if (s.getY() > maxY) {
                    bottom = s;
                    maxY = s.getY();
                }
            }
            bottom.paint(g2);
            unpainted.remove(bottom);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Shape block : DataSource.getInstance().getBlockList()) {
            if (block.contains(e.getX(), e.getY())) {
                block.setPreX((int) (block.getX() - e.getX()));
                block.setPreY((int) (block.getY() - e.getY()));
                if ((block.getType() != "Step" && block.getType() != "Turn") && e.getX() - block.getX() > 50 ) {
                    block.clicked(e.getX(), e.getY());
                }
                else {
                    block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                }
                repaint();
                break;
            } else {
                block.setPressOut(true);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (Shape block : DataSource.getInstance().getBlockList()) {
            if (!block.getPressOut()) {
                if ((block.getType() != "Step" && block.getType() != "Turn") && e.getX() - block.getX() > 50 ) { }
                else {
                    block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                }
                repaint();
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("got it!!!");
        for (Shape block : DataSource.getInstance().getBlockList()) {
            if (block.contains(e.getX(), e.getY())) {
                if (e.getX() < 100 && e.getY() > 650) {
                    DataSource.getInstance().blocks.remove(block);
                }

                block.setPreX((int) (block.getX() - e.getX()));
                block.setPreY((int) (block.getY() - e.getY()));
                if ((block.getType() != "Step" && block.getType() != "Turn") && e.getX() - block.getX() > 50 ) { }
                else {
                    block.move(block.getPreX() + e.getX(), block.getPreY() + e.getY());
                    ConnectHelper.snap(block);
                }
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
