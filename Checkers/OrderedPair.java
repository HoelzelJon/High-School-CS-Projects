/**
 * An ordered pair on the checkers board
 * 
 * @author Hoelzel
 * @version 3/27/2015
 */

public class OrderedPair
{
    private int x;
    private int y;
    
    public OrderedPair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    // not used
    public void spinBoard()
    {
        x = (7 - x);
        y = (7 - y);
    }
}
