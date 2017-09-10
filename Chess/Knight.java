/**
 * A knight - can move in an L shape
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Knight extends Piece
{
    public Knight(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        if (Math.abs(xMove) == 2 && Math.abs(yMove) == 1) return true;
        else if (Math.abs(xMove) == 1 && Math.abs(yMove) == 2) return true;
        else return false;
    }
    
    public Knight clone()
    {
        return new Knight(this.getTeam());
    }
    
    public int getPieceValue()
    {
        return 3;
    }
}
