/**
 * A Move. has an old spot and a new spot
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Move
{
    private OrderedPair oldSpot;
    private OrderedPair newSpot;
    
    public Move(OrderedPair oldSpot, OrderedPair newSpot)
    {
        this.oldSpot = oldSpot;
        this.newSpot = newSpot;
    }
    
    public Move(int x, int y, int newX, int newY)
    {
        oldSpot = new OrderedPair(x, y);
        newSpot = new OrderedPair(newX, newY);
    }
    
    public OrderedPair getOldSpot()
    {
        return oldSpot;
    }
    
    public OrderedPair getNewSpot()
    {
        return newSpot;
    }
    
    public void spinBoard()
    {
        oldSpot.spinBoard();
        newSpot.spinBoard();
    }
    
    public Move clone()
    {
        return new Move(oldSpot.clone(), newSpot.clone());
    }
}
