import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class DataSource {
    private static DataSource instance;
    Stack<Block> blocks = new Stack<Block>();
    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();



    private DataSource() { }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public void addBlock(int x, int y, String type) {
        blocks.add(new Block(x, y, type));
    }

    public void setGrid(long rows, long columns){
        System.out.println("rows: " + rows);
        System.out.println("columns: " + columns);

        for(int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j < columns; j++) {
                row.add( new Cell(Color.BLACK, (i * 5) + j, false, 20 + (50*j), 120 + (50 * i), 48, 48 ));
            }
            grid.add(row);
    } }

    public ArrayList<ArrayList<Cell>> getGrid(){
        return this.grid;
    }

    public Stack<Block> getBlockList(){
        return this.blocks;
    }

}
