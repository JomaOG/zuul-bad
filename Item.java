
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private int weight;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        // initialise instance variables
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Return item name
     */
    public String getName() {
        return name;
    }
    
    /**
     * return item description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * return item weight
     */
    public int getWeight() {
        return weight;
    }
}
