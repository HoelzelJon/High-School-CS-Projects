/**
 * an  ordered pair. has an X and a y
 * 
 * @author Hoelzel
 * @version 4/2/2015
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
    
    public boolean equals(OrderedPair other)
    {
        return (this.x == other.x && this.y == other.y);
    }
    
    public void spinBoard()
    {
        x = (7 - x);
        y = (7 - y);
    }
    
    public OrderedPair clone()
    {
        return new OrderedPair(x, y);
    }
    
    public String toString()
    {
        return Helper.getCode(this);
    }
}
