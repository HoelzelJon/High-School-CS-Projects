/**
 * A generic piece - has a team
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public abstract class Piece
{
    private Team team;
    private boolean isHighlighted1;
    private boolean isHighlighted2;
    private boolean isHighlighted3;
    private boolean hasMoved;
    
    public Team getTeam()
    {
        return team;
    }
    
    public void justMoved()
    {
        hasMoved = true;
    }
    
    public boolean hasMoved()
    {
        return hasMoved;
    }
    
    public abstract boolean isValidMove(int xMove, int yMove);
    
    public void setTeam(Team t)
    {
        team = t;
        hasMoved = false;
    }
    
    public abstract Piece clone();
    
    public abstract int getPieceValue();
    
    public boolean isHighlighted1()
    {
        return isHighlighted1;
    }
    
    public boolean isHighlighted2()
    {
        return isHighlighted2;
    }
    
    public boolean isHighlighted3()
    {
        return isHighlighted3;
    }
    
    public void highlight1()
    {
        isHighlighted1 = true;
    }
    
    public void highlight2()
    {
        isHighlighted2 = true;
    }
    
    public void highlight3()
    {
        isHighlighted3 = true;
    }
    
    public void resetHighlight1()
    {
        isHighlighted1 = false;
    }
    
    public void resetHighlight2()
    {
        isHighlighted2 = false;
    }
    
    public void resetHighlight3()
    {
        isHighlighted3 = false;
    }
}
