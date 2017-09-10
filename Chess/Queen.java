/**
 * A queen. can move in a straight line or diagonally
 * 
 * @author Hoelzel 
 * @version 4/2/2015
 */

public class Queen extends Piece
{
    public Queen(Team t)
    {
        super.setTeam(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        if (xMove == 0 && yMove == 0) return false;
        else if (xMove == 0 || yMove == 0) return true;
        else if (Math.abs(xMove) == Math.abs(yMove)) return true;
        else return false;
    }
    
    public Queen clone()
    {
        return new Queen(this.getTeam());
    }
    
    public int getPieceValue()
    {
        return 9;
    }
}
