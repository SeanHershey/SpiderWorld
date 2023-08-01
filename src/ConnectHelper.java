public class ConnectHelper {
    public static void snap(Shape block) {

        for (Shape other_block : DataSource.getInstance().getBlockList()) {

            if (other_block.getBelow() != null) {
                if (other_block.getBelow().getY() < other_block.getY() || Math.abs(other_block.getBelow().getX() - other_block.getX()) > 50) {
                    other_block.getBelow().setAbove(null);
                    other_block.setBelow(null);
                    // System.out.println(block.getType() + " rem");
                }
            }
        }

        for (Shape other_block : DataSource.getInstance().getBlockList()) {

            if ((block.getBelow() == other_block) && ((Math.abs(block.getX() - other_block.getX()) < 50) && (block.getY() - other_block.getY() < 50))) {
                block.getBelow().setAbove(null);
                block.setBelow(null);
                // System.out.println(block.getType() + " rem1");
            }
        }

        for (Shape other_block : DataSource.getInstance().getBlockList()) {
            if (other_block != block)
            {
                if ((Math.abs(block.getX() - other_block.getX()) < 50) && (Math.abs(block.getY() - other_block.getY()) < 50)) {

                    if (other_block.getBelow() == null) {
                        other_block.setBelow(block);
                        block.setAbove(other_block);
                        // System.out.println(other_block.getType() + " set" + block.getType());
                        block.move(other_block.getX(), other_block.getY() + 32);
                    }
                    else {
                        Shape b = other_block.getBelow();
                        while (b.getBelow() != null) {
                            b = b.getBelow();
                        }
                        b.setBelow(block);
                        block.setAbove(b);
                        // System.out.println(b.getType() + " set (m)" + b.getType());
                        block.move(b.getX(), b.getY() + 32);
                    }
                    break;
                }
                else if (other_block.getBelow() == block) {
                    other_block.setBelow(null);
                    block.setAbove(null);
                    // System.out.println(other_block.getType() + " rem2");
                }
            }
        }
        if (block.getAbove()!= null){
       if(block.getAbove().getType() == "Loop" || block.getAbove().getType().charAt(0)== 'l'){
//           System.out.println(block.getAbove());
           DataSource.getInstance().getBlockList().remove(block);
           Shape b = new Loop(block);
           b.getAbove().setBelow(b);
           DataSource.getInstance().getBlockList().add(b);
//           System.out.println(b.getAbove());
       }

    }

    }
}
