import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class DataSource {
    private static DataSource instance;
    Stack<Block> blocks = new Stack<Block>();
    Stack<String> instructions = new Stack<String>();
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

    public void setGrid(long rows, long columns, Color color){
        System.out.println("rows: " + rows);
        System.out.println("columns: " + columns);

        for(int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j < columns; j++) {
                row.add( new Cell(color, (i * 5) + j, false, 20 + (50*j), 120 + (50 * i), 48, 48 ));
            }
            grid.add(row);
    } }

    public ArrayList<ArrayList<Cell>> getGrid() {
        return this.grid;
    }

    public Stack<Block> getBlockList() {
        return this.blocks;
    }

    public Stack<String> getInstructions() {
        LinkedList<Block> done = new LinkedList<Block>();

        instructions.clear();

        for (Block block : blocks) {
            if (block.getAbove() != null) {
                System.out.print(" ("+ block.getAbove().getType() + ")");
            }
            else {
                System.out.print(" (NONE) ");
            }
            System.out.print(block.getType());
            if (block.getBelow() != null) {
                System.out.println(" ("+ block.getBelow().getType() + ")");
            }
            else {
                System.out.println(" (NONE) ");
            }
        }

        for (Block block : blocks) {
            if (!done.contains(block)) {
                Block b = block;
                while (b.getBelow() != null) {
                    instructions.add(b.getType());
                    done.add(b);
                    b = b.getBelow();
                }
            }
        }

        for (Block block : blocks) {
            if (!done.contains(block)) {
                instructions.add(block.getType());
                done.add(block);
            }
        }

        return instructions;
    }

}
