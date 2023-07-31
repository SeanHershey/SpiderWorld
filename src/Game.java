import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public static void main(String[] args) {
        Game win = new Game();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(1200,800);
        win.setVisible(true);
    }

    public Game() {
        super("Spider World");

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.white);
        GridLayout grid = new GridLayout(1,2);
        centerPanel.setLayout(grid);

        JPanel worldPanel = new JPanel();
        worldPanel = new World();

        JPanel workPanel = new JPanel();
        workPanel = new WorkArea();

        centerPanel.add(worldPanel);
        centerPanel.add(workPanel);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(centerPanel, BorderLayout.CENTER);
    }
}
