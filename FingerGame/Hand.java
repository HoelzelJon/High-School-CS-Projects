
/**
 * One hand in the game
 * 
 * @author Hoelzel
 * @version 4/6/2016
 */

public class Hand
{
    public static int MAX_FINGERS = 4;
    
    private int numFingers;
    
    public Hand()
    {
        numFingers = 1;
    }
    
    public Hand(int n)
    {
        numFingers = n;
    }
    
    public void hit(Hand other)
    {
        other.numFingers += numFingers;
        if (other.numFingers > MAX_FINGERS) other.numFingers = 0;
    }
    
    /**
     * transfers (num) fingers from (this) onto (other)
     */
    public void pass(Hand other, int num)
    {
        numFingers -= num;
        other.numFingers += num;
        if(other.numFingers > MAX_FINGERS) other.numFingers = 0;
    }
    
    public int getFingers()
    {
        return numFingers;
    }
    
    public boolean equals(Hand other)
    {
        return (numFingers == other.numFingers);
    }
}
