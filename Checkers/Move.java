/**
 * A class to hold the positions of a move the computer is making
 * 
 * @author Hoelzel
 * @version 3/27/2015
 */

public class Move
{
    private OrderedPair oldSpot;
    private OrderedPair newSpot;
    
    public Move(int x, int y, int newX, int newY)
    {
        oldSpot = new OrderedPair(x, y);
        newSpot = new OrderedPair(newX, newY);
    }
    
    public Move(OrderedPair oldSpot, OrderedPair newSpot)
    {
        this.oldSpot = oldSpot;
        this.newSpot = newSpot;
    }
    
    public int getX()
    {
        return oldSpot.getX();
    }
    
    public int getY()
    {
        return oldSpot.getY();
    }
    
    public int getNewX()
    {
        return newSpot.getX();
    }
    
    public int getNewY()
    {
        return newSpot.getY();
    }
    
    public OrderedPair getNewSpot()
    {
        return newSpot;
    }
    
    public OrderedPair getOldSpot()
    {
        return oldSpot;
    }
    
    public void spinBoard()
    {
        oldSpot.spinBoard();
        newSpot.spinBoard();
    }
}
