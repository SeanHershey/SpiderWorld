import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class World extends JPanel implements MouseListener {
    private JSONObject levels;
    private String level = "4";
    private long rows;
    private long columns;
    private Color color;

    private Spider spider;
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton blackButton;
    private JButton stepButton;
    private JButton turnButton;
    private int posI = 0;
    private int posJ = 0;


    public World(){
        addMouseListener(this);
        setButtons();
        fetchLevel();
        setLevel();
        DataSource.getInstance().setGrid(rows, columns, color);
        this.spider = new Spider();
    }


    public void fetchLevel(){
        String filePath = "levels.json";
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filePath);
            this.levels = (JSONObject) parser.parse(fileReader);
            fileReader.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void setLevel(){
        this.rows = (long) ((JSONObject) levels.get(level)).get("rows");
        this.columns = (long) ((JSONObject) levels.get(level)).get("columns");
        String colorString = (String) ((JSONObject) levels.get(level)).get("color");

        // System.out.println("color string:" + colorString + "!");
        this.color = Color.getColor(colorString);
        // System.out.println("color: " + this.color);
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
                Cell c = (Cell) DataSource.getInstance().getGrid().get(i).get(j);;
                g.setColor((Color) c.getColor());
                System.out.println("color: " + c.getColor());
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
            }
        }

        try {
            spider.draw(g);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // To Get Block Instructions 
        Stack<String> intsructions = DataSource.getInstance().getInstructions();
        for (String type : intsructions) {
            System.out.print(type + ",");
        }
        System.out.println("");

    }
    public void setGrid(Color col){
        DataSource ds =  DataSource.getInstance();
        ArrayList<ArrayList<Cell>> grid = ds.getGrid();
        grid.get(posI).get((posJ)).setColor(col);
        repaint();
    }

    public boolean checkMove(){
        DataSource ds =  DataSource.getInstance();
        ArrayList<ArrayList<Cell>> grid = ds.getGrid();
        String dir = this.spider.getDirection();
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                Boolean pos = grid.get(i).get(j).getSpider();

                if (pos) {
                    System.out.println("SPIDER AT " + i +"&" + j);
                    if ((dir.equals("North") && (i == 0))) {
                        return false;
                    }
                    else if ((dir.equals("East") && (j == columns-1))) {

                        return false;
                    }
                    else if ((dir.equals("South") && (i == rows-1 ))) {
                        return false;
                    }
                    else if ((dir.equals("West") && (j == 0))) {
                        return false;
                    }



                }
            }

        }
        return true;
    }

    public void updatePos(){
        DataSource ds =  DataSource.getInstance();
        ArrayList<ArrayList<Cell>> grid = ds.getGrid();
        String dir = this.spider.getDirection();
        grid.get(posI).get((posJ)).setSpider(false);
        if (dir.equals("North")) {
            this.posI -= 1;
        }
        else if (dir.equals("East")) {
            this.posJ += 1;

        }
        else if (dir.equals("South")) {
            this.posI += 1;
        }
        else if (dir.equals("West"))  {
            this.posJ -= 1;
        }
        grid.get(posI).get((posJ)).setSpider(true);
    }


    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonLabel = clickedButton.getText();
            switch (buttonLabel) {
                case "step":
                    if(checkMove()){
                        spider.step();
                        updatePos();
                        repaint();

                        System.out.println("step");
                        break;
                    }
                    else{
                        break;
                    }

        //need to update the spidrs position on the set grid--
        // waiting for implementation. Spider steps, but there are no boundaries on the grid for the spider stop

                case "turn":
                    System.out.println("turn");
                    spider.turn();
                    repaint();


                    break;
                case "red":
                    System.out.println("red");
                    setGrid(Color.red);
                    break;
                case "blue":
                    System.out.println("blue");
                    setGrid(Color.BLUE);
                    break;
                case "green":
                    System.out.println("green");
                    setGrid(Color.GREEN);
                    break;
                case "black":
                    System.out.println("black");
                    setGrid(Color.BLACK);
                    break;
                default:
            }
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }


}
