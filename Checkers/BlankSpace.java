/**
 * a blank spot (counts as a piece)
 * 
 * @author Hoelzel
 * @version 3/21/2015
 */

public class BlankSpace extends Piece
{
    public BlankSpace()
    {
        super(Team.NEITHER);
    }
    
    public boolean isValidMove(int x, int y)
    {
        return false;
    }
    
    public boolean isValidJumpMove(int x, int y)
    {
        return false;
    }
    
    public String toString()
    {
        return " ";
    }
}
