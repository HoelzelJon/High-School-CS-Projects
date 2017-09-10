/**
 * A bishop - can move diagonally
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Bishop extends Piece
{
    public Bishop(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        return (Math.abs(xMove) == Math.abs(yMove) && xMove != 0);
    }
    
    public Bishop clone()
    {
        Bishop temp = new Bishop(super.getTeam());
        if (hasMoved()) temp.justMoved();
        
        return temp;
    }
    
    public int getPieceValue()
    {
        return 3;
    }
}
