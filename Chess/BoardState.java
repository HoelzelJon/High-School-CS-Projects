import java.util.ArrayList;

/**
 * Basically does all the grunt work for CompLv5
 * 
 * @author Hoelzel
 * @version 8/19/2016
 */
public class BoardState
{
    private int value;
    private BoardState lastState;
    private ArrayList<BoardState> nextStates;
    private Board b;
    private Team t;
    private boolean hasAssignedValue;
    
    public BoardState(Board b, Team whoseTurn, int levels, BoardState last){
        this.b = b;
        t = whoseTurn;
        lastState = last;
        nextStates = new ArrayList<BoardState>();
        hasAssignedValue = false;
        
        // PLAYER1 wants value to be high, PLAYER2 wants it to be low
        if (t == Team.PLAYER1) value = -10000;
        else value = 10000;
        
        if (levels == 1) {
            value = b.getTeamValue(Team.PLAYER1) - b.getTeamValue(Team.PLAYER2);
            hasAssignedValue = true;
        }
        else if (b.isInCheckmate(Helper.getOppositeTeam(t))){
            if (t == Team.PLAYER1) value = 1000;
            else value = -1000;
            hasAssignedValue = true;
        }
        else{
            ArrayList<Move> moves = b.getAllValidMoves(t);
            for (Move m : moves){
                Board newBoard = b.clone();
                newBoard.movePiece(m);
                newBoard.spinBoard();
                nextStates.add(new BoardState(newBoard, Helper.getOppositeTeam(t), levels - 1, this));
            }
        }
        
        this.findValue();
    }
    
    private void findValue(){
        if (hasAssignedValue) return;
        
        for (BoardState state : nextStates){
            if (!state.hasAssignedValue) state.findValue();
            
            if ((t == Team.PLAYER1 && state.value > value) || (t == Team.PLAYER2 && state.value < value)) value = state.value;
        }
        
        hasAssignedValue = true;
    }
    
    public Move getBestMove(){
        for (BoardState bs : nextStates){
            if (bs.value == value){
                return bs.b.getLastMove();
            }
        }
        System.out.println("Didn't pick a move :(");
        return null;
    }
}
