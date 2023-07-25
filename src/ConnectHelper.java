public class ConnectHelper {
    public static void snap(Block block) {

        for (Block b : DataSource.getInstance().getBlockList()) {
            if (b.getBelow() != null) {
                if (b.getBelow().getY() < b.getY()) {
                    b.getBelow().setAbove(null);
                    b.setBelow(null);
                    // System.out.println(block.getType() + "rem");
                }
            }
        }

        for (Block b : DataSource.getInstance().getBlockList()) {
            if ((block.getBelow() == b) && ((Math.abs(block.getX() - b.getX()) < 50) && (block.getY() - b.getY() < 50))) {
                block.getBelow().setAbove(null);
                block.setBelow(null);
                // System.out.println(block.getType() + "rem1");
            }
        }

        for (Block block2 : DataSource.getInstance().getBlockList()) {
            if (block2 != block)
            {
                if ((Math.abs(block.getX() - block2.getX()) < 50) && (Math.abs(block.getY() - block2.getY()) < 50)) {

                    if (block2.getBelow() == null) {
                        block2.setBelow(block);
                        block.setAbove(block2);
                        // System.out.println(block2.getType() + "set" + block.getType());
                    }
                    block.move(block2.getX(), block2.getY() + 32);
                }
                else if (block2.getBelow() == block){
                    block2.setBelow(null);
                    block.setAbove(null);
                    // System.out.println(block2.getType() + "rem2");
                }
            }
        }
    }
}
