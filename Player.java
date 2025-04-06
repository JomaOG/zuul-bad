import java.util.HashMap;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
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
    
    /**
     * return item name in inventory
     */
    public Item getItem(String name) {
        return items.get(name);
    }
    
    /**
     * check player inventory weight
     */
    public int checkWeight() {
        int weight = 0;
        for(String item : items.keySet()) {
            weight += items.get(item).getWeight();
        }
        return weight;
    }
    
    /**
     * pick up command
     */
    public void pickUpItem(Command command) {
        String item = command.getSecondWord();
        Room room = currentRoom.getRoom();
        
        if(!command.hasSecondWord()) {
            //If no secondWord
            System.out.println("Which item?");
            return;
        }
        
        //Calculates current weight + targetItem weight
        int checkWeight = checkWeight() + room.getItem(item).getWeight();
        if (this.maxWeight <= checkWeight) {
            // prints if weight would be above maxWeight
            System.out.printf("Item [%s] is too heavy to pick up.\n", command.getSecondWord());
            return;
        }
        
        if(room.getItem(item)!=null) {
            items.put(item,room.getItem(item));
            System.out.printf("You picked up: [%s]\n", room.getItem(item).getName());
            System.out.printf("Current weight: %d / %d.\n", checkWeight(), this.maxWeight);
            room.removeItem(item);
        } else {
            System.out.printf("Item [%s] does not exist.\n", item);
            }
    }
    
    /**
     * drop command
     */
    public void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            //If no secondWord
            System.out.println("Which item?");
            return;
        }
        
        String item = command.getSecondWord();
        
        if(items.containsKey(item)) {
            Room room = currentRoom.getRoom();
            room.addItem(item, items.get(item).getDescription(), items.get(item).getWeight());
            System.out.printf("You dropped: [%s]\n", items.get(item).getName());
            items.remove(item);
            System.out.printf("Current weight: %d / %d.\n", checkWeight(), this.maxWeight);
        } else {
            System.out.printf("Item [%s] does not exist.\n", item);
        }
    }
    
    /**
     * eat command
     */
    public void eat(Command command) {
        String item = command.getSecondWord();
        if(!command.hasSecondWord()) {
            //If no secondWord
            System.out.println("Eat what?");
            return;
        } else if (item.equals("cookie") && items.containsKey(item)){
            //Checks if targetItem is Cookie and is in inventory
            items.remove(item);
            setMaxWeight(50);
            System.out.printf("You ate the magic cookie, and you max carry weight has increased to %d\n", this.maxWeight);
        } else {
            //Prints if player tries to eat something else than the cookie
            System.out.println("You can only eat the magic cookie");
        }
    }

    /**
     * Set max Weight 
     * use when eating cookie
     */
    public void setMaxWeight(int weightIncrease) {
        this.maxWeight += weightIncrease;
    }
    
    /**
     * Return String with all items in player inventory
     */
    public String getItemString()
    {
        // put your code here
        String itemString = "Player Items: | ";
        for(String item : items.keySet()) {
            itemString += "[" +item +"] - " +items.get(item).getWeight() +" wt | ";
        }
        return itemString;
    }
    
    /**
     * places player in current room.
     */
    public void setRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
