/**
 * A king - can move one space in any direction
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class King extends Piece
{
    public King(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        if (xMove == 0 && yMove == 0) return false;
        else return (Math.abs(xMove) <= 1 && Math.abs(yMove) <= 1);
    }
    
    public King clone()
    {
        King temp = new King(this.getTeam());
        if (hasMoved()) temp.justMoved();
        
        return temp;
    }
    
    public int getPieceValue()
    {
        return 0;
    }
}
