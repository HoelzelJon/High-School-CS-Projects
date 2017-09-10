/**
 * a Pawn - can move one space forward
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Pawn extends Piece
{    
    public Pawn(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        if (yMove == -1 && Math.abs(xMove) == 1) return true;
        else if (xMove != 0) return false;
        else if (yMove == -1) return true;
        else if (!hasMoved() && yMove == -2) return true;
        else return false;
    }
    
    public Pawn clone()
    {
        Pawn temp = new Pawn(this.getTeam());
        if (hasMoved()) temp.justMoved();
        
        return temp;
    }
    
    public int getPieceValue()
    {
        return 1;
    }
}
