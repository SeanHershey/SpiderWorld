import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class World extends JPanel {
    private JSONObject levels;
    private String level = "4";
    private long rows;
    private long columns;
    private Spider spider;



    World(){
        setButtons();
        fetchLevel();
        setLevel();
        DataSource.getInstance().setGrid(rows, columns);
        this.spider = new Spider();
    }


    public void fetchLevel(){
        String filePath = "levels.json";
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            // Parse the JSON data into a JSONObject
            this.levels = (JSONObject) parser.parse(fileReader);
            // Close the FileReader
            fileReader.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void setLevel(){
        this.rows = (long) ((JSONObject) levels.get(level)).get("rows");
        this.columns = (long) ((JSONObject) levels.get(level)).get("columns");

    }

    public void setButtons(){
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
                Rectangle rect = DataSource.getInstance().getGrid().get(i).get(j).getRect();
                g.setColor(Color.BLACK);
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
            }
        }

        try {
            System.out.println("drawing spider");
            spider.draw(g);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
