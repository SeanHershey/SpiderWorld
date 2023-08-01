public abstract class Decorator implements Shape {
    protected Shape block;
    
    public Decorator(Shape block){
        this.block= block;
    }
    public String getType(){
        return block.getType();
    }
    
}
