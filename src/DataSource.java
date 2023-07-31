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

    public void setGrid(long rows, long columns, Color color, ArrayList<Integer> targets){
        grid = new ArrayList<>();

        for(int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                long val_2 = (i * columns) + j;
                int val = (int) val_2;

                boolean target_bool = targets.contains((val));

                if (i == 0 && j == 0) {
                    row.add(new Cell(color, (i * 5) + j, true, 20 + (50 * j), 120 + (50 * i), 48, 48, target_bool));
                } else {
                    row.add(new Cell(color, (i * 5) + j, false, 20 + (50 * j), 120 + (50 * i), 48, 48, target_bool));
                }
            }

            grid.add(row);
        }
        System.out.println(grid);
    }

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
            if (block.getAbove() == null && block.getBelow() != null)
            {
                if (!done.contains(block)) {
                    Block b = block;
                    instructions.add(b.getType());
                    done.add(b);
                    b = b.getBelow();
                    while (b != null) {
                        instructions.add(b.getType());
                        done.add(b);
                        b = b.getBelow();
                    }
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
