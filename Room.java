import java.util.HashMap;
import java.util.HashSet;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String,Room> exits;
    private HashMap<String,Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        items = new HashMap<String,Item>();
    }
    
    public Room getRoom() {
        return this;
    }
    
    public void addItem(String name, String description, int weight) {
        items.put(name,new Item(name,description,weight));
    }
    
    public Item getItem(String name) {
        return items.get(name);
    }
    
    public void removeItem(String name) {
        items.remove(name);
    }
    
    public String getItemString() {
        String itemString = "Items: | ";
        for(String item : items.keySet()){
            itemString += "[" +item +"] - " +items.get(item).getWeight() +" wt | ";
        }
        return itemString;
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }
    
    /** * Return a description of the room’s exits,
    * for example, "Exits: north west".
    * @return A description of the available exits.
    */
    public String getExitString() {
        String exitString = "Exits: | ";
        for(String direction : exits.keySet()) {
            exitString += direction.toString() + " | ";
        }
        return exitString;
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    public String getLongDescription() {
        return "You are " +description + "\n" +getExitString() +"\n" +getItemString();
    }

}
