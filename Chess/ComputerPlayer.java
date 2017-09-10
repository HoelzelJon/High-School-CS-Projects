/**
 * A generic computer player. makes moves
 * 
 * @author Hoelzel
 * @version 4/23/2015
 */

public abstract class ComputerPlayer
{
    private Team t;
    private Board board;
    
    public void setTeam(Team t)
    {
        this.t = t;
    }
    
    public Team getTeam()
    {
        return t;
    }
    
    public void setBoard(Board b)
    {
        board = b;
    }
    
    public Board getBoard()
    {
        return board;
    }
    
    public abstract Move nextMove();
    
    public abstract Piece pieceToChangePawnTo();
}