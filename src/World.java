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

import java.util.Random;

public class World extends JPanel implements MouseListener {
    private JSONObject levels;
    private String level = "1";
    private long rows;
    private long columns;
    private Color color;
    private int targets;


    private Spider spider;
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton blackButton;
    private JButton stepButton;
    private JButton turnButton;
    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton level4;

    private JButton runButton;
    private JButton resetButton;

    private int posI = 0;
    private int posJ = 0;



    public World(){
        addMouseListener(this);
        setButtons();
        fetchLevel();
        changeLevel();
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
        long t = (long) ((JSONObject) levels.get(level)).get("targets");
        this.targets = (int) t;

        long rLong = (long) ((JSONObject) levels.get(level)).get("r");
        long gLong = (long) ((JSONObject) levels.get(level)).get("g");
        long bLong = (long) ((JSONObject) levels.get(level)).get("b");
        int r = (int) rLong;
        int g = (int) gLong;
        int b = (int) bLong;

        Color test_c = new Color(r,g,b);
        this.color = test_c;
    }

    public void setButtons(){
        stepButton = new JButton("step");
        turnButton = new JButton("turn");
        redButton = new JButton("red");
        blueButton = new JButton("blue");
        greenButton = new JButton("green");
        blackButton = new JButton("black");
        level1 = new JButton("level1");
        level2 = new JButton("level2");
        level3 = new JButton("level3");
        level4 = new JButton("level4");
        runButton = new JButton("Run");
        resetButton = new JButton("Reset");


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));


        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();

        northPanel.add(stepButton);
        northPanel.add(turnButton);
        northPanel.add(redButton);
        northPanel.add(blueButton);
        northPanel.add(greenButton);
        northPanel.add(blackButton);
        southPanel.add(level1);
        southPanel.add(level2);
        southPanel.add(level3);
        southPanel.add(level4);
        southPanel.add(resetButton);
        southPanel.add(runButton);



        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);


        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);


        stepButton.addMouseListener(this);
        turnButton.addMouseListener(this);
        redButton.addMouseListener(this);
        blueButton.addMouseListener(this);
        greenButton.addMouseListener(this);
        blackButton.addMouseListener(this);

        level1.addMouseListener(this);
        level2.addMouseListener(this);
        level3.addMouseListener(this);
        level4.addMouseListener(this);

        runButton.addMouseListener(this);
        resetButton.addMouseListener(this);


    }


    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        super.setBackground(Color.white);

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                Rectangle rect = DataSource.getInstance().getGrid().get(i).get(j).getRect();
                Cell c = DataSource.getInstance().getGrid().get(i).get(j);;
                g.setColor( c.getColor());
                g.fillRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(), (int) rect.getHeight() );
                if (c.getTarget()){
                    g.setColor(c.getTargetColor());
                    g.fillRect((int) rect.getX() + 17 ,(int) rect.getY() + 19,(int) rect.getWidth() - 35, (int) rect.getHeight() - 35 );

                }

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
    }

    public boolean checkWin(){

        ArrayList<ArrayList<Cell>> g = DataSource.getInstance().getGrid();

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println("look here");
                Cell c = DataSource.getInstance().getGrid().get(i).get(j);;
                if ( c.getTarget() == true && c.getColor() != c.getTargetColor() ){
                    return false;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Winner Winner Chicken Dinner!");
        changeLevel();

        return true;
    }

    public ArrayList<Integer> chooseTargets(){
        long valid_indexes_1 = (this.rows * this.columns) - 1;
        int valid_indexes = (int) valid_indexes_1;

        ArrayList<Integer> indices = new ArrayList();
        Random random = new Random();

        while (indices.size() != this.targets ){
            int randomNumber = random.nextInt(valid_indexes + 1);// Generates random number in the range [0, k]
            if (! indices.contains(randomNumber) && (randomNumber != 0) ){
                indices.add(randomNumber);
            }
        }

        return indices;

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
//                    System.out.println("SPIDER AT " + i +"&" + j);
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

    public void changeLevel(){
        setLevel();
        ArrayList<Integer> targets = chooseTargets();
        DataSource.getInstance().setGrid(rows, columns, color, targets);
        repaint();
        this.spider = new Spider();
        posI = 0;
        posJ = 0;
        return;

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
                case "turn":
                    System.out.println("turn");
                    spider.turn();
                    repaint();
                    break;
                case "red":
                    System.out.println("red");
                    setGrid(Color.red);
                    checkWin();
                    break;
                case "blue":
                    System.out.println("blue");
                    setGrid(Color.BLUE);
                    checkWin();
                    break;
                case "green":
                    System.out.println("green");
                    setGrid(Color.GREEN);
                    checkWin();
                    break;
                case "black":
                    System.out.println("black");
                    setGrid(Color.BLACK);
                    checkWin();
                    break;

                case "Reset":
                    System.out.println("reset");
                    changeLevel();
                    break;
                case "Run":

                    Stack<String> intsructions = DataSource.getInstance().getInstructions();
                    int start = 0;
                    for (int i = 0; i<intsructions.size(); i++) {
                        String type = intsructions.get(i);
                        char c ;
                        if (type.charAt(0) == 'l') {
                            c = type.charAt(1);
                        }
                        else{
                            c = type.charAt(0);
                        }
                        System.out.println(c);
                        switch (c) {
                            case 'S':
                                if (checkMove()) {
                                    spider.step();
                                    updatePos();
                                    repaint();
                                }
                                break;
                            case 'T':
                                spider.turn();
                                repaint();
                                break;
                            case 'R':
                                setGrid(Color.RED);
                                repaint();
                                break;
                            case 'B':
                                setGrid(Color.BLUE);
                                repaint();
                                break;
                            case 'G':
                                setGrid(Color.GREEN);
                                repaint();
                                break;
                            case 'L':
                                start = i;
                            default:
                        }

                        if (type.charAt(0) == 'l') {
                            if( i+1 >= intsructions.size()) {
                                if (checkMove()) {
                                    i = start;

                                }
                                else {
                                    break;
                                }
                            }
                            if (intsructions.get(i+1).charAt(0) != 'l') {
                                if (checkMove()) {
                                    i = start;

                                }
                            }
                        }
                    }
                    System.out.println("dfsfsfsdf");
                    checkWin();
                    break;
                case "level1":
                    level = "1";
                    changeLevel();
                    break;

                case "level2":
                    level = "2";
                    changeLevel();
                    break;
                case "level3":
                    level = "3";
                    changeLevel();
                    break;
                case "level4":
                    level = "4";
                    changeLevel();
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
