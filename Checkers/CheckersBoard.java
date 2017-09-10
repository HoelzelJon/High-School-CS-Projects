import java.util.ArrayList;

/**
 * A board for checkers
 * 
 * @author Hoelzel
 * @version 11/10/2015
 */
public class CheckersBoard
{
    private Piece[][] boardArray;
    
    
    public CheckersBoard()
    {
        boardArray = new Piece[8][8];
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                boardArray[i][j] = new BlankSpace();
            }
        }
    }
    
    public void addPiece(Piece p, int x, int y)
    {
        boardArray[x][y] = p;
    }
    
    public void removePiece(int x, int y)
    {
        boardArray[x][y] = new BlankSpace();
    }
    
    public boolean isEmpty(int x, int y)
    {
        return (x >= 0 && y >= 0 && x < 8 && y < 8 && (boardArray[x][y] instanceof BlankSpace));
    }
    
    public boolean isValidMove(Move move, Team whoseTurn)
    {
        if (move.getX() >= 0 && move.getX() < 8 && move.getY() >= 0 && move.getY() < 8 && move.getNewX() >= 0 && move.getNewX() < 8 && move.getNewY() >= 0 && move.getNewY() < 8)
        {
            Piece movingPiece = boardArray[move.getX()][move.getY()];
            return (movingPiece.getTeam().equals(whoseTurn) && movingPiece.isValidMove(move.getNewX() - move.getX(), move.getNewY() - move.getY()) && this.isEmpty(move.getNewX(), move.getNewY()));
        }
        else return false;
    }
    
    public void movePiece(Move move)
    {
        Piece movingPiece = boardArray[move.getX()][move.getY()];
        this.removePiece(move.getX(), move.getY());
        this.addPiece(movingPiece, move.getNewX(), move.getNewY());
        
        if (move.getNewY() == 0 && !(movingPiece instanceof KingChecker)) boardArray[move.getNewX()][move.getNewY()] = new KingChecker(movingPiece.getTeam());
    }
    
    public boolean isValidJumpMove(Move move, Team whoseTurn)
    {
        if (move.getX() >= 0 && move.getX() < 8 && move.getY() >= 0 && move.getY() < 8 && move.getNewX() >= 0 && move.getNewX() < 8 && move.getNewY() >= 0 && move.getNewY() < 8)
        {
            Piece movingPiece = boardArray[move.getX()][move.getY()];
            Piece jumpedPiece = boardArray[(move.getX() + move.getNewX()) / 2][(move.getY() + move.getNewY()) / 2];
            return (movingPiece.getTeam().equals(whoseTurn) && movingPiece.isValidJumpMove(move.getNewX() - move.getX(), move.getNewY() - move.getY()) && this.isEmpty(move.getNewX(), move.getNewY()) && !this.isEmpty((move.getX() + move.getNewX()) / 2, (move.getY() + move.getNewY()) / 2) && !jumpedPiece.getTeam().equals(movingPiece.getTeam()));
        }
        else return false;
    }
    
    /**
     * Helper method for the ComputerPlayer class
     * precondition: spot must have a piece of the correct team in it
     */
    public boolean pieceCanMove(int x, int y)
    {
        Piece p = boardArray[x][y];
        if (this.isEmpty(x+1, y-1) || this.isEmpty(x-1, y-1)) return true;
        else if ((p instanceof KingChecker) && (this.isEmpty(x+1, y+1) || this.isEmpty(x-1, y+1))) return true;
        return false;
    }
    
    /**
     * Helper for ComputerPlayerLv4. is used when board is facing correct way for piece being jumped
     * gets a list of ways a specific piece could be jumped
     */
    public ArrayList<Move> getWaysToJump(int x, int y)
    {
        ArrayList<Move> waysToJump = new ArrayList<Move>();
        Team myTeam = this.getPieceTeam(x, y);
        
        if (x >= 7 || y >= 7 || x <= 0 || y <= 0) return waysToJump;
        if ((!this.getPieceTeam(x-1, y-1).equals(myTeam)) && (!this.isEmpty(x-1, y-1)) && this.isEmpty(x+1, y+1)) waysToJump.add(new Move(x-1, y-1, x+1, y+1));
        if ((!this.getPieceTeam(x+1, y-1).equals(myTeam)) && (!this.isEmpty(x+1, y-1)) && this.isEmpty(x-1, y+1)) waysToJump.add(new Move(x+1, y-1, x-1, y+1));
        if (this.isAKing(x-1, y+1) && (!this.getPieceTeam(x-1, y+1).equals(myTeam)) && this.isEmpty(x+1, y-1)) waysToJump.add(new Move(x-1, y+1, x+1, y-1));
        if (this.isAKing(x+1, y+1) && (!this.getPieceTeam(x+1, y+1).equals(myTeam)) && this.isEmpty(x-1, y-1)) waysToJump.add(new Move(x+1, y+1, x-1, y-1));
        
        return waysToJump;
    }
    
    /**
     * Helper method for the ComputerPlayer class
     */
    public ArrayList<OrderedPair> getAllPieces(Team t)
    {
        ArrayList<OrderedPair> pieces = new ArrayList<OrderedPair>();
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if (boardArray[i][j].getTeam().equals(t)) pieces.add(new OrderedPair(i, j));
            }
        }
        
        return pieces;
    }
    
    /**
     * Helper method for ComputerPlayer class
     * preconditions: must be a spot with a piece of the correct team
     */
    public ArrayList<OrderedPair> getPlacesToMoveTo(int x, int y)
    {
        Piece p = boardArray[x][y];
        ArrayList<OrderedPair> movesList = new ArrayList<OrderedPair>();
        
        if (x > 0 && y > 0 && this.isEmpty(x-1, y-1)) movesList.add(new OrderedPair(x-1, y-1));
        if (x < 7 && y > 0 && this.isEmpty(x+1, y-1)) movesList.add(new OrderedPair(x+1, y-1));
        if (p instanceof KingChecker)
        {
            if (x < 7 && y < 7 && this.isEmpty(x+1, y+1)) movesList.add(new OrderedPair(x+1, y+1));
            if (x > 0 && y < 7 && this.isEmpty(x-1, y+1)) movesList.add(new OrderedPair(x-1, y+1));
        }
        
        return movesList;
    }
    
    public ArrayList<OrderedPair> getPlacesToJumpTo(int x, int y)
    {
        Piece p = boardArray[x][y];
        ArrayList<OrderedPair> movesList = new ArrayList<OrderedPair>();
        
        if (x < 7 && y > 1 && p.isOppositeTeam(boardArray[x+1][y-1]) && this.isEmpty(x + 2, y - 2)) movesList.add(new OrderedPair(x+2, y-2));
        if (x > 1 && y > 1 && p.isOppositeTeam(boardArray[x-1][y-1]) && this.isEmpty(x - 2, y - 2)) movesList.add(new OrderedPair(x-2, y-2));
        if (p instanceof KingChecker)
        {
            if (x < 7 && y < 7 && p.isOppositeTeam(boardArray[x+1][y+1]) && this.isEmpty(x + 2, y + 2)) movesList.add(new OrderedPair(x+2, y+2));
            if (x > 1 && y  < 7 && p.isOppositeTeam(boardArray[x-1][y+1]) && this.isEmpty(x - 2, y + 2)) movesList.add(new OrderedPair(x-2, y+2));
        }
        
        return movesList;
    }
    
    public void jumpMove(Move move)
    {
        this.removePiece((move.getX() + move.getNewX()) / 2, (move.getY() + move.getNewY()) / 2);
        Piece movingPiece = boardArray[move.getX()][move.getY()];
        this.removePiece(move.getX(), move.getY());
        this.addPiece(movingPiece, move.getNewX(), move.getNewY());
        
        if (move.getNewY() == 0 && !(movingPiece instanceof KingChecker)) boardArray[move.getNewX()][move.getNewY()] = new KingChecker(movingPiece.getTeam());
    }
    
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
        
        boardArray = tempArray;
    }
    
    public Team getPieceTeam(int x, int y)
    {
        return boardArray[x][y].getTeam();
    }
    
    /**
     * helps for the ComputerPlayer level 2 , 3, and 4. is used in a temporary board where a prospective move has already been made
     */
    public boolean canGetJumped(int x, int y)
    {      
        Team myTeam = this.getPieceTeam(x, y);
        
        if (x >= 7 || y >= 7 || x <= 0 || y <= 0) return false;
        else if ((!this.getPieceTeam(x-1, y-1).equals(myTeam)) && (!this.isEmpty(x-1, y-1)) && this.isEmpty(x+1, y+1)) return true;
        else if ((!this.getPieceTeam(x+1, y-1).equals(myTeam)) && (!this.isEmpty(x+1, y-1)) && this.isEmpty(x-1, y+1)) return true;
        else if (this.isAKing(x-1, y+1) && (!this.getPieceTeam(x-1, y+1).equals(myTeam)) && this.isEmpty(x+1, y-1)) return true;
        else if (this.isAKing(x+1, y+1) && (!this.getPieceTeam(x+1, y+1).equals(myTeam)) && this.isEmpty(x-1, y-1)) return true;
        else return false;
    }
    
    public boolean hasMoreJumpMoves(int x, int y)
    {
        Piece movingPiece = boardArray[x][y];
        
        if (x < 7 && y > 1 && movingPiece.isOppositeTeam(boardArray[x+1][y-1]) && this.isEmpty(x + 2, y - 2)) return true;
        else if (x > 1 && y > 1 && movingPiece.isOppositeTeam(boardArray[x-1][y-1]) && this.isEmpty(x - 2, y - 2)) return true;
        else if (movingPiece instanceof KingChecker)
        {
            if (x < 7 && y < 7 && movingPiece.isOppositeTeam(boardArray[x+1][y+1]) && this.isEmpty(x + 2, y + 2)) return true;
            else if (x > 1 && y  < 7 && movingPiece.isOppositeTeam(boardArray[x-1][y+1]) && this.isEmpty(x - 2, y + 2)) return true;
        }
        
        return false;
    }
    
    public Team getWinner()
    {
        if (!this.hasMoreChips(Team.PLAYER1) && this.hasMoreChips(Team.PLAYER2)) return Team.PLAYER2;
        else if (!this.hasMoreChips(Team.PLAYER2) && this.hasMoreChips(Team.PLAYER1)) return Team.PLAYER1;
        else return Team.NEITHER;
    }
    
    public boolean hasMoreChips(Team player)
    {
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if (boardArray[i][j].getTeam().equals(player)) return true;
            }
        }
        
        return false;
    }
    
    public boolean isAKing(int x, int y)
    {
        return (boardArray[x][y] instanceof KingChecker);
    }
    
    public boolean canMove(Team t)
    {
        ArrayList<OrderedPair> allPieces = this.getAllPieces(t);
        
        for (OrderedPair spot : allPieces)
        {
            if (this.pieceCanMove(spot.getX(), spot.getY())) return true;
            else if (this.hasMoreJumpMoves(spot.getX(), spot.getY())) return true;
        }
        
        return false;
    }
    
    @Override
    public CheckersBoard clone()
    {
        CheckersBoard temp = new CheckersBoard();
        
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                temp.addPiece(boardArray[i][j], i, j);
            }
        }
        
        return temp;
    }
    
    // Not used anymore (replaced by graphics)
    @Override
    public String toString()
    {
        String temp = "    0   1   2   3   4   5   6   7\n";
        temp += "   ______________________________\n";
        
        for (int i = 0; i < 8; i ++)
        {
            temp += i + " |";
            for (int j = 0; j < 8; j ++)
            {
                temp += " ";
                temp += boardArray[j][i].toString();
                temp += " |";
            }
            temp += "\n  |_______________________________|\n";
        }
        
        return temp;
    }
    
    public void highlight1(OrderedPair spot)
    {
        boardArray[spot.getX()][spot.getY()].setHighlight1(true);
    }
    
    public void clearHighlight1()
    {
        for (int i = 0; i < 8; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                boardArray[i][j].setHighlight1(false);
            }
        }
    }
    
    public Piece getPiece(int x, int y)
    {
        return boardArray[x][y];
    }
    
    public boolean canJump(Team t)
    {
        for (int oldX = 0; oldX < 8; oldX ++)
        {
            for (int oldY = 0; oldY < 8; oldY ++)
            {
                for (int newX = 0; newX < 8; newX ++)
                {
                    for (int newY = 0; newY < 8; newY ++)
                    {
                        if (isValidJumpMove(new Move(oldX, oldY, newX, newY), t))  return true;
                    }
                }
            }
        }
        
        return false;
    }
}
