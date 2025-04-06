
/**
 * Enumeration class Direction - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Direction
{
    NORTH("north"), SOUTH("south"), EAST("east"), WEST("west");
    
    private String directionString;
    
    Direction(String directionString) {
        this.directionString = directionString;
    }
    
    public String toString() {
        return directionString;
    }
}
