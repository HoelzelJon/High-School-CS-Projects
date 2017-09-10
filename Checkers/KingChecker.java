/**
 * A special kind of ckeckers piece. It can move backwards
 * 
 * @author Hoelzel
 * @version 3/21/2015
 */

public class KingChecker extends CheckersPiece
{
    public KingChecker(Team t)
    {
        super(t);
    }
    
    @Override
    public boolean isValidMove(int xMove, int yMove)
    {
        return ((xMove == 1 || xMove == -1) && (yMove == 1 || yMove == -1));
    }
    
    @Override
    public boolean isValidJumpMove(int xMove, int yMove)
    {
        return ((xMove == 2 || xMove == -2) && (yMove == 2 || yMove == -2));
    }
    
    @Override
    public String toString()
    {
        if (getTeam().equals(Team.PLAYER1)) return "R";
        else return "B";
    }
}
