/**
 * A piece in the game of checkers. Has a color, an x position, and a y position
 * 
 * @author Hoelzel
 * @version 3/21/2015
 */

public class CheckersPiece extends Piece
{
    public CheckersPiece(Team t)
    {
        super(t);
    }
    
    public boolean isValidMove(int xMove, int yMove)
    {
        return (yMove == -1 && (xMove == 1 || xMove == -1));
    }
    
    public boolean isValidJumpMove(int xMove, int yMove)
    {
        return (yMove == -2 && (xMove == 2 || xMove == -2));
    }
    
    public String toString()
    {
        if (getTeam().equals(Team.PLAYER1)) return "r";
        else return "b";
    }
}