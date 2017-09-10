/**
 * a rook - can move in straight lines vertically or horizontally
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Rook extends Piece
{
    public Rook(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        if (xMove == 0 && yMove == 0) return false;
        else if (xMove == 0 || yMove == 0) return true;
        else return false;
    }
    
    public Rook clone()
    {
        Rook temp = new Rook(this.getTeam());
        if (hasMoved()) temp.justMoved();
        
        return temp;
    }
    
    public int getPieceValue()
    {
        return 5;
    }
}
