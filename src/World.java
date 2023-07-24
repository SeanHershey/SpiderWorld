import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class World extends JPanel implements MouseListener {
    private JSONObject levels;
    private String level = "4";
    private long rows;
    private long columns;
    private Spider spider;
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton blackButton;
    private JButton stepButton;
    private JButton turnButton;





    public World(){
        addMouseListener(this);

        setButtons();
        fetchLevel();
        setLevel();
        DataSource.getInstance().setGrid(rows, columns);
        this.spider = new Spider();

    }


    public void fetchLevel(){
        String filePath = "levels.json";
        // JSONParser parser = new JSONParser();
        // try {
        //     FileReader fileReader = new FileReader(filePath);
        //     // Parse the JSON data into a JSONObject
        //     this.levels = (JSONObject) parser.parse(fileReader);
        //     // Close the FileReader
        //     fileReader.close();

        // } catch (IOException | ParseException e) {
        //     e.printStackTrace();
        // }

    }

    public void setLevel(){
        // this.rows = (long) ((JSONObject) levels.get(level)).get("rows");
        // this.columns = (long) ((JSONObject) levels.get(level)).get("columns");

    }

    public void setButtons(){
        stepButton = new JButton("step");
        turnButton = new JButton("turn");
        redButton = new JButton("red");
        blueButton = new JButton("blue");
        greenButton = new JButton("green");
        blackButton = new JButton("black");
        add(stepButton);
        add(turnButton);
        add(redButton);
        add(blueButton);
        add(greenButton);
        add(blackButton);

        stepButton.addMouseListener(this);
        turnButton.addMouseListener(this);
        redButton.addMouseListener(this);
        blueButton.addMouseListener(this);
        greenButton.addMouseListener(this);
        blackButton.addMouseListener(this);
    }


    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.white);

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                Rectangle rect = DataSource.getInstance().getGrid().get(i).get(j).getRect();
                g.setColor(Color.BLACK);
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
            }
        }

        try {
            spider.draw(g);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
