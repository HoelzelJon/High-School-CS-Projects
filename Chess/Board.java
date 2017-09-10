import java.util.ArrayList;

/**
 * The board to play chess on
 * 
 * @author Hoelzel
 * @version 10/30/2015
 */

public class Board
{
    private Piece[][] boardArray;
    private Move lastMove;
    private ArrayList<Piece> deadPieces1;
    private ArrayList<Piece> deadPieces2;
    
    public static final int TAKING_ABILITY_VALUE = 1;
    public static final int VULNERABLE_VALUE = 3;
    
    public Board()
    {
        boardArray = new Piece[8][8];
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                boardArray[i][j] = new BlankSpace();
            }
        }
        
        lastMove = new Move(-1, -1, -1, -1);
        deadPieces1 = new ArrayList<Piece>();
        deadPieces2 = new ArrayList<Piece>();
    }
    
    public void addPiece(Piece p, OrderedPair spot)
    {
        boardArray[spot.getX()][spot.getY()] = p;
    }
    
    public void removePiece(OrderedPair spot)
    {
        boardArray[spot.getX()][spot.getY()] = new BlankSpace();
    }
    
    public boolean isEmpty(OrderedPair spot)
    {
        return (boardArray[spot.getX()][spot.getY()] instanceof BlankSpace);
    }
    
    
    /**
     * Spins the board for the other player to take a turn
     */
    public void spinBoard()
    {
        Piece[][] tempArray = new Piece[8][8];
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                tempArray[7-i][7-j] = boardArray[i][j];
            }
        }
        
        lastMove.spinBoard();
        boardArray = tempArray;
        ArrayList<Piece> tempDeadList = deadPieces1;
        deadPieces1 = deadPieces2;
        deadPieces2 = tempDeadList;
    }
    
    public Team getPieceTeam(OrderedPair spot)
    {
        return boardArray[spot.getX()][spot.getY()].getTeam();
    }
    
    public Team getPieceTeam(int x, int y)
    {
        return boardArray[x][y].getTeam();
    }
    
    public Piece getPiece(OrderedPair spot)
    {
        return boardArray[spot.getX()][spot.getY()];
    }
    
    public boolean isAKing(int x, int y)
    {
        return (boardArray[x][y] instanceof King);
    }
    
    /**
     * Not used anymore because of checkmate and stuff
     */
    public Team getWinner()
    {
        boolean player1HasKing = false;
        boolean player2HasKing = false;
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if (this.isAKing(i, j))
                {
                    if (this.getPieceTeam(i, j).equals(Team.PLAYER1)) player1HasKing = true;
                    else player2HasKing = true;
                }
            }
        }
        
        if (player1HasKing && player2HasKing) return Team.NEITHER;
        else if (player1HasKing) return Team.PLAYER1;
        else return Team.PLAYER2;
    }
    
    public boolean isValidMove(Move m)
    {
        int x = m.getOldSpot().getX();
        int y = m.getOldSpot().getY();
        int newX = m.getNewSpot().getX();
        int newY = m.getNewSpot().getY();
        Piece p = boardArray[x][y];
        
        if (!p.isValidMove(newX - x, newY - y)) return false;
        else if (p instanceof Pawn)
        {
            if (newX - x != 0)
            {
                if (Helper.isOppositeTeam(p.getTeam(), this.getPieceTeam(newX, newY))) return true;
                else return false;
            }
            else if (newY - y == -2)
            {
                if (this.isEmpty(m.getNewSpot()) && this.isEmpty(new OrderedPair(x, newY + 1))) return true;
                else return false;
            }
            else return (this.isEmpty(m.getNewSpot()));
        }
        else if (p instanceof Rook || p instanceof Bishop || p instanceof Queen)
        {
            ArrayList<OrderedPair> spacesInBetween = Helper.getAllSpotsBetween(m);
            
            for (OrderedPair spot : spacesInBetween)
            {
                if (!this.isEmpty(spot)) return false;
            }
            
            if (!this.getPieceTeam(m.getNewSpot()).equals(p.getTeam())) return true;
            else return false;
        }
        else if (p instanceof Knight || p instanceof King)
        {
            if (!this.getPieceTeam(m.getNewSpot()).equals(p.getTeam())) return true;
            else return false;
        }
        else return false;
    }
    
    public void movePiece(Move m)
    {
        Piece p = this.getPiece(m.getOldSpot());
        if (!this.isEmpty(m.getNewSpot()))  deadPieces1.add(this.getPiece(m.getNewSpot()));
        this.removePiece(m.getOldSpot());
        this.addPiece(p, m.getNewSpot());
        
        p.justMoved();
        
        lastMove = m.clone();
    }
    
    public ArrayList<Move> getAllValidMoves(Team t)
    {
        ArrayList<Move> temp = new ArrayList<Move>();
        
        for (int x = 0; x < 8; x ++)
        {
            for (int y = 0; y < 8; y ++)
            {
                if (this.getPieceTeam(x, y).equals(t))
                {
                    for (int newX = 0; newX < 8; newX ++)
                    {
                        for (int newY = 0; newY < 8; newY ++)
                        {
                            Move m = new Move(x, y, newX, newY);
                            if (this.isValidMove(m)) temp.add(m);
                        }
                    }
                }
            }
        }
        
        return temp;
    }
    
    public ArrayList<Move> getAllValidEnPassants(Team t)
    {
        ArrayList<Move> temp = new ArrayList<Move>();
        
        for (int x = 0; x < 8; x ++)
        {
            for (int y = 0; y < 8; y ++)
            {
                if (this.getPieceTeam(x, y).equals(t))
                {
                    for (int newX = 0; newX < 8; newX ++)
                    {
                        for (int newY = 0; newY < 8; newY ++)
                        {
                            Move m = new Move(x, y, newX, newY);
                            if (this.isValidEnPassant(m)) temp.add(m);
                        }
                    }
                }
            }
        }
        
        return temp;
    }
    
    public ArrayList<Move> getAllValidCastles(Team t)
    {
        ArrayList<Move> temp = new ArrayList<Move>();
        
        for (int x = 0; x < 8; x ++)
        {
            for (int y = 0; y < 8; y ++)
            {
                if (this.getPieceTeam(x, y).equals(t))
                {
                    for (int newX = 0; newX < 8; newX ++)
                    {
                        for (int newY = 0; newY < 8; newY ++)
                        {
                            Move m = new Move(x, y, newX, newY);
                            if (this.isValidCastle(m)) temp.add(m);
                        }
                    }
                }
            }
        }
        
        return temp;
    }
    
    public OrderedPair getKingSpot(Team t)
    {
        for (int x = 0; x < 8; x ++)
        {
            for (int y = 0; y < 8; y ++)
            {
                if (this.isAKing(x, y) && this.getPieceTeam(x, y).equals(t)) return new OrderedPair(x, y);
            }
        }
        return new OrderedPair(-1, -1);
    }
    
    /**
     * To be used to check if a player is in check
     */
    public boolean isInCheck(Team t)
    {        
        spinBoard();
        
        boolean isInCheck = false;
        OrderedPair kingSpot = getKingSpot(t);
        ArrayList<Move> otherPlayersMoves = getAllValidMoves(Helper.getOppositeTeam(t));
        
        for (Move m : otherPlayersMoves)
        {
            if (m.getNewSpot().equals(kingSpot)) isInCheck = true;
        }
        
        spinBoard();
        
        return isInCheck;
    }
    
    /**
     * Add a thing to clone the graveyards of both sides???????????????????????????????????????????
     */ 
    public Board clone()
    {
        Board temp = new Board();
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                temp.addPiece(boardArray[i][j].clone(), new OrderedPair(i, j));
            }
        }
        
        temp.lastMove = lastMove.clone();
        
        return temp;
    }
    
    public boolean isInCheckmate(Team t)
    {
        if (!this.isInCheck(t)) return false;
        
        ArrayList<Move> allMoves = this.getAllValidMoves(t);
        
        for (Move m : allMoves)
        {
            Board tempBoard = this.clone();
            tempBoard.movePiece(m);
            
            if (!tempBoard.isInCheck(t)) return false;
        }
        
        return true;
    }
    
    public boolean isValidCastle(Move m)
    {
        int x = m.getOldSpot().getX();
        int y = m.getOldSpot().getY();
        if (y != 7) return false;
        
        Team t = this.getPieceTeam(m.getOldSpot());
        
        int newX = m.getNewSpot().getX();
        int newY = m.getNewSpot().getY();
        if (newY != 7) return false;
        
        
        if (this.isInCheck(t)) return false;
        
        if (Math.abs(x - newX) == 2 && this.isAKing(x, y))
        {
            King king = (King)boardArray[x][y];
            if (king.hasMoved()) return false;
            OrderedPair rookPosition;
            
            if (x - newX == 2)
            {
                if (boardArray[0][7] instanceof Rook) rookPosition = new OrderedPair(0, 7);
                else return false;
            }
            else if (boardArray[7][7] instanceof Rook) rookPosition = new OrderedPair(7, 7);
            else return false;
            
            //have to check if spots between are open
            Rook rook = (Rook)this.getPiece(rookPosition);
            if (rook.hasMoved()) return false;
            
            ArrayList<OrderedPair> spotsBetweenKingAndRook = Helper.getAllSpotsBetween(new Move(m.getOldSpot(), rookPosition));
            for (OrderedPair spot : spotsBetweenKingAndRook)
            {
                if (!this.isEmpty(spot)) return false;
            }
            
            // Check if it is moving through check
            OrderedPair spotKingPassesThrough = new OrderedPair((x + newX) / 2, y);
            Board tempBoard = this.clone();
            tempBoard.movePiece(new Move(m.getOldSpot(), spotKingPassesThrough));
            if (tempBoard.isInCheck(t)) return false;
            
            return true;
        }
        
        return false;
    }
    
    public void castle(Move m)
    {
        int x = m.getOldSpot().getX();
        int y = m.getOldSpot().getY();
        int newX = m.getNewSpot().getX();
        int newY = m.getNewSpot().getY();
        
        // move the king
        this.movePiece(m);
        
        //Move the rook
        OrderedPair rookPosition;
        
        if (x - newX == 2) rookPosition = new OrderedPair(0, 7);
        else rookPosition = new OrderedPair(7, 7);
        
        OrderedPair spotToMoveRookTo = new OrderedPair((x + newX) / 2, y);
        this.movePiece(new Move(rookPosition, spotToMoveRookTo));
        lastMove = m;
    }
    
    public boolean isValidEnPassant(Move m)
    {
        //Check if the last move was a pawn moving up two spaces
        if (lastMove.getOldSpot().getY() - lastMove.getNewSpot().getY() == -2 && this.getPiece(lastMove.getNewSpot()) instanceof Pawn)
        {
            //check if the current move is up 1 spot and over 1
            if (m.getNewSpot().getY() - m.getOldSpot().getY() == -1 && Math.abs(m.getNewSpot().getX() - m.getOldSpot().getX()) == 1)
            {
                // Check if the new spot of the current move is moving into the spot the pawn of the last move passed through
                if (m.getNewSpot().equals(new OrderedPair(lastMove.getNewSpot().getX(), (lastMove.getNewSpot().getY() + lastMove.getOldSpot().getY()) / 2)) && this.getPiece(m.getOldSpot()) instanceof Pawn) return true;
                else return false;
            }
            else return false;
        }
        else return false;
    }
    
    public void enPassant(Move m)
    {
        OrderedPair otherPawnSpot = new OrderedPair(m.getNewSpot().getX(), m.getOldSpot().getY());
        if (!this.isEmpty(otherPawnSpot))  deadPieces1.add(this.getPiece(otherPawnSpot));
        this.removePiece(otherPawnSpot);
        this.movePiece(m);
        Piece movingPiece = this.getPiece(m.getNewSpot());
        
        if(movingPiece instanceof Pawn)
        {
            Pawn p = (Pawn)movingPiece;
            p.justMoved();
        }
        
        lastMove = m;
    }
    
    public ArrayList<Piece> getDeadPieces1()
    {
        return deadPieces1;
    }
    
    public ArrayList<Piece> getDeadPieces2()
    {
        return deadPieces2;
    }
    
    public ArrayList<OrderedPair> getAllPieces(Team t)
    {
        ArrayList<OrderedPair> temp = new ArrayList<OrderedPair>();
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if (boardArray[i][j].getTeam().equals(t)) temp.add(new OrderedPair(i, j));
            }
        }
        
        return temp;
    }
    
    /**
     * called during the computer's turn to check if a possible move could get the moving piece taken
     */
    public boolean canGetTaken(OrderedPair spot)
    {
        spinBoard();
        spot.spinBoard();
        ArrayList<Move> allMoves = getAllValidMoves(Helper.getOppositeTeam(getPieceTeam(spot)));
        spinBoard();
        spot.spinBoard();
        
        for (Move m : allMoves)
        {
            m.spinBoard();
            if (m.getNewSpot().equals(spot)) return true;
        }
        
        return false;
    }
    
    /**
     * Evaluates the value of a board for the Computer player
     */
    public int getBoardValue(Team t)
    {
        int temp = 0;
        ArrayList<OrderedPair> thisTeamsPieces = getAllPieces(t);
        
        // ways t can be taken
        for (OrderedPair spot : thisTeamsPieces)
        {
            if (canGetTaken(spot)) temp -= VULNERABLE_VALUE * getPiece(spot).getPieceValue();
        }
        
        return temp;
    }
    
    public boolean hasPieceOfOtherTeam(OrderedPair spot, Team t)
    {
        return (Helper.getOppositeTeam(t).equals(getPieceTeam(spot)));
    }
    
    public int getTeamValue(Team t)
    {
        int sum = 0;
        
        ArrayList<OrderedPair> all = getAllPieces(t);
        
        for (OrderedPair spot : all)
        {
            sum += getPiece(spot).getPieceValue();
        }
        
        return sum;
    }
    
    public ArrayList<Move> getAllNonCheckMoves(Team t)
    {
        ArrayList<Move> all = getAllValidMoves(t);
        
        ArrayList<Move> sorted = new ArrayList<Move>();
        for(Move m : all)
        {
            Board temp = clone();
            temp.movePiece(m);
            if (!temp.isInCheck(t)) sorted.add(m);
        }
        
        return sorted;
    }
    
    public void resetAllHighlights1()
    {
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)            
            {
                boardArray[i][j].resetHighlight1();
            }
        }
    }
    
    public void resetAllHighlights2()
    {
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)            
            {
                boardArray[i][j].resetHighlight2();
            }
        }
    }
    
    public void resetAllHighlights3()
    {
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)            
            {
                boardArray[i][j].resetHighlight3();
            }
        }
    }
    
    public void highlight1(OrderedPair spot)
    {
        getPiece(spot).highlight1();
    }
    
    public void highlight2(OrderedPair spot)
    {
        getPiece(spot).highlight2();
    }
    
    public void highlight3(OrderedPair spot)
    {
        getPiece(spot).highlight3();
    }
    
    public ArrayList<OrderedPair> getAllMovesFrom(OrderedPair spot)
    {
        ArrayList<OrderedPair> moves = new ArrayList<OrderedPair>();
        Team t = getPieceTeam(spot);
        boolean isInCheckBefore = isInCheck(t);
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                Move m = new Move(spot, new OrderedPair(i, j));
                Board temp = clone();
                
                
                if(isValidMove(m)) 
                {
                    temp.movePiece(m);
                    if (!temp.isInCheck(t)) moves.add(new OrderedPair(i, j));
                }
                else if(isValidEnPassant(m))
                {
                    temp.enPassant(m);
                    if (!temp.isInCheck(t)) moves.add(new OrderedPair(i, j));
                }
                else if(!isInCheckBefore && isValidCastle(m))
                {
                    temp.castle(m);
                    if (!temp.isInCheck(t)) moves.add(new OrderedPair(i, j));
                }
                else if (isInCheckBefore)
                {
                    
                }
            }
        }
        
        return moves;
    }
    
    public boolean isInStalemate(Team t)
    {
        if (isInCheck(t)) return false;
        ArrayList<Move> moves = getAllValidMoves(t);
        
        for (int i = moves.size() - 1; i >= 0; i--)
        {
            Board temp = clone();
            Move m = moves.get(i);
            
            if(isValidMove(m)) 
            {
                temp.movePiece(m);
                if (temp.isInCheck(t)) moves.remove(i);
            }
            else if(isValidEnPassant(m))
            {
                temp.enPassant(m);
                if (!temp.isInCheck(t)) moves.remove(i);
            }
            else if(isValidCastle(m))
            {
                temp.castle(m);
                if (!temp.isInCheck(t)) moves.remove(i);
            }
        }
        return (moves.size() == 0);
    }
    
    public boolean equals(Board other){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j ++) {
                if (!other.boardArray[i][j].equals(boardArray[i][j])) return false;
            }
        }
        return true;
    }
    
    public Move getLastMove(){
        return lastMove;
    }
}