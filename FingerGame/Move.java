/**
 * A move in the game
 * 
 * @author Hoelzel
 * @version 4/6/2016
 */

public class Move
{
    /**
     * false if it is a pass, true if it is a hit
     */
    private boolean isAHit;
    /**
     * The hand that is doing the hitting or that is transferring fingers
     */
    private int from;
    /**
     * if it's a hit - label of the target finger
     * if it's a pass - number of fingers to pass
     */
    private int num2;
    
    public Move(boolean isAHit, int from, int num2)
    {
        this.isAHit = isAHit;
        this.from = from;
        this.num2 = num2;
    }
    
    public boolean isAHit()
    {
        return isAHit;
    }
    
    public int getFrom()
    {
        return from;
    }
    
    public int getNum2()
    {
        return num2;
    }
}