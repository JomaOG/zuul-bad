import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> previousRoom;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        previousRoom = new Stack<Room>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        // outside
        outside.setExits("east", theater);
        outside.setExits("west", lab);
        outside.setExits("south", pub);
        // outside Items
        outside.addItem("bottle", "wotoh", 25);
        outside.addItem("can", "It's empty", 26);
        outside.addItem("paper", "Blank paper", 3);
        
        // theater
        theater.setExits("west", outside);
        // theater Items
        theater.addItem("curtain", "piece of the curtain", 5);
        
        // pub
        pub.setExits("east", outside);
        // pub Items
        pub.addItem("beer", "It's empty", 2);
        pub.addItem("billiard", "I do not have time for this", 15);
        pub.addItem("darts", "I do not have time for this", 4);
        pub.addItem("cookie", "eat this to double carry weight", 1);
        
        // lab
        lab.setExits("north", outside);
        lab.setExits("east", office);
        // lab Items
        lab.addItem("coats", "The ones scientist wear", 1);
        lab.addItem("tubes", "It's empty", 1);
        lab.addItem("stove", "It's inside a locked cabinet", 4);
        lab.addItem("gloves", "medical grade gloves", 1);
        
        // office
        office.setExits("west", lab);
        // office Items
        office.addItem("word", "This is part of Office", 1);
        office.addItem("sheets", "This is part of Office", 1);

        currentRoom = outside;  // start game outside
        player = new Player(currentRoom);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("take")) {
            player.pickUpItem(command);
        }
        else if (commandWord.equals("drop")) {
            player.dropItem(command);
        }
        else if (commandWord.equals("check")) {
            System.out.println(player.getItemString());
        }
        else if (commandWord.equals("eat")) {
            player.eat(command);
        }
        return wantToQuit;
    }
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showAllCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom.push(currentRoom);
            currentRoom = nextRoom;
            player.setRoom(currentRoom);
            printLocationInfo();
        }
    }
    
    private void back() {
        // Check if stack is empty
        if(previousRoom.empty()) {
            System.out.println("You are at the beginning!");
            return;
        }
        
        // Move back too previous room
        currentRoom = previousRoom.pop();
        player.setRoom(currentRoom);
        printLocationInfo();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
