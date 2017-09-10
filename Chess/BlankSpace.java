/**
 * an empty space - team = neither
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class BlankSpace extends Piece
{
    public BlankSpace()
    {
        super.setTeam(Team.NEITHER);
        hasMoved();
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        return false;
    }
    
    public BlankSpace clone()
    {
        return new BlankSpace();
    }
    
    public int getPieceValue()
    {
        return 0;
    }
}
