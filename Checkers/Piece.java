/**
 * a checkers piece
 * It has an x and y position on the board and a team
 * 
 * @author Hoelzel
 * @version 3/21/2015
 */

public abstract class Piece
{
    private Team team;
    private boolean highlight1;
    
    public Piece(Team t)
    {
        team = t;
        highlight1 = false;
    }
    
    public abstract boolean isValidMove(int xMove, int yMove);
    
    public abstract boolean isValidJumpMove(int xMove, int yMove);
    
    @Override
    public abstract String toString();
    
    public boolean isOppositeTeam(Piece other)
    {
        if (this.team.equals(Team.PLAYER1)) return other.team.equals(Team.PLAYER2);
        else if (this.team.equals(Team.PLAYER2)) return other.team.equals(Team.PLAYER1);
        else return false;
    }
    
    public Team getTeam()
    {
        return team;
    }
    
    public void setTeam(Team t)
    {
        team = t;
    }
    
    public void setHighlight1(boolean x)
    {
        highlight1 = x;
    }
    
    public boolean isHighlighted1()
    {
        return highlight1;
    }
}
