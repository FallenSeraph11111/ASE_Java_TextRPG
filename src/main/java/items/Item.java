package items;

public class Item {
    public final String name;
    public final int stacksize;
    public final double value;
	public final ItemType type;
    public Item(String name, double value, int stacksize, ItemType type) {
        this.name = name;
        this.stacksize = stacksize;
        this.value = value;
	    this.type = type;
    }
}
