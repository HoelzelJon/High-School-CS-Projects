import java.util.ArrayList;


/**
 * A computer player that makes better moves by making a tree of board states
 * 
 * @author Hoelzel
 * @version 8/19/2016
 */
public class CompLv5 extends ComputerPlayer
{
    private static final int MOVES_LOOKED_AHEAD = 4;
    
    public CompLv5(Board b, Team t){
        super.setTeam(t);
        super.setBoard(b);
    }
    
    public Move nextMove(){
        BoardState b = new BoardState(getBoard(), getTeam(), MOVES_LOOKED_AHEAD, null);
        Move m = b.getBestMove();
        m.spinBoard();
        return m;
    }
    
    public Piece pieceToChangePawnTo(){
        return new Queen(getTeam());
    }
}
