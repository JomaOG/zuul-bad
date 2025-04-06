import java.util.HashMap;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    // private String name;
    // private String description;
    private Room currentRoom;
    private int weight;
    private int maxWeight = 50;
    private HashMap<String,Item> items;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        // initialise instance variables
        items = new HashMap<String,Item>();
        this.currentRoom = currentRoom;
    }
    
    public Item getItem(String name) {
        return items.get(name);
    }
    
    public int checkWeight() {
        int weight = 0;
        for(String item : items.keySet()) {
            weight += items.get(item).getWeight();
        }
        return weight;
    }
    
    public void pickUpItem(Command command) {
        String item = command.getSecondWord();
        Room room = currentRoom.getRoom();
        int checkWeight = checkWeight() + room.getItem(item).getWeight();
        
        if(!command.hasSecondWord()) {
            //
            System.out.println("Which item?");
            return;
        } else if (this.maxWeight <= checkWeight) {
            //
            System.out.printf("Item [%s] is too heavy to pick up.\n", command.getSecondWord());
            return;
        }
        
        if(room.getItem(item)!=null) {
            items.put(item,room.getItem(item));
            System.out.printf("you picked up: %s\n", room.getItem(item).getName());
            System.out.printf("%d / %d weight\n", checkWeight(), this.maxWeight);
            room.removeItem(item);
        } else {
            System.out.printf("Item [%s] does not exist.\n", item);
            }
    }
    
    public void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            //
            System.out.println("Which item?");
            return;
        }
        
        String item = command.getSecondWord();
        
        if(items.containsKey(item)) {
            Room room = currentRoom.getRoom();
            room.addItem(item, items.get(item).getDescription(), items.get(item).getWeight());
            System.out.printf("You dropped: %s\n", items.get(item).getName());
            items.remove(item);
        } else {
            System.out.printf("Item [%s] does not exist.\n", item);
        }
    }
    
    public void eat(Command command) {
        String item = command.getSecondWord();
        if(!command.hasSecondWord()) {
            //
            System.out.println("Eat what?");
            return;
        } else if (item.equals("cookie") && items.containsKey(item)){
            //
            items.remove(item);
            setMaxWeight(50);
            System.out.printf("You ate the magic cookie, and you max carry weight has increased to %d", this.maxWeight);
        } else {
            //
            System.out.println("You can only eat the magic cookie");
        }
    }

    public void setMaxWeight(int weightIncrease) {
        this.maxWeight += weightIncrease;
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getItemString()
    {
        // put your code here
        String itemString = "Player Items: | ";
        for(String item : items.keySet()) {
            itemString += item +" " +items.get(item).getWeight() +"wt | ";
        }
        return itemString;
    }
    
    public void setRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
