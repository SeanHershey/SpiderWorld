import java.util.Stack;

public class DataSource {
    private static DataSource instance;
    Stack<Block> blocks = new Stack<Block>();

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

    public Stack<Block> getBlockList(){
        return this.blocks;
    }

}
