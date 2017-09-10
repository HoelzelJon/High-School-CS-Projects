import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A game of chess for two human players
 * 
 * @author Hoelzel
 * @version 11/6/2015
 */

public class TwoPersonGame
{
    public static final int FRAME_WIDTH = ChessComponent.WIDTH;
    public static final int FRAME_HEIGHT = ChessComponent.HEIGHT;
    
    public static void play()
    {
        class MouseClickListener implements MouseListener
        {            
            private OrderedPair spotClicked;
            private boolean hasClicked;
            private ChessComponent c;
            
            public MouseClickListener(ChessComponent comp)
            {
                spotClicked = new OrderedPair(-1, -1);
                hasClicked = false;
                c = comp;
            }
            
            public void mousePressed(MouseEvent event)
            {
                hasClicked = true;
                spotClicked = Helper.getBoardPosition(event.getX(), event.getY(), c);
            }
            
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        
        Board gameBoard = new Board();
        
        JFrame frame = new JFrame();
        
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i < 8; i ++)
        {
            OrderedPair spot1 = new OrderedPair(i, 6);
            gameBoard.addPiece(new Pawn(Team.PLAYER1), spot1);
            OrderedPair spot2 = new OrderedPair(i, 1);
            gameBoard.addPiece(new Pawn(Team.PLAYER2), spot2);
        }
        
        gameBoard.addPiece(new Rook(Team.PLAYER2), new OrderedPair(0, 0));
        gameBoard.addPiece(new Rook(Team.PLAYER2), new OrderedPair(7, 0));
        gameBoard.addPiece(new Rook(Team.PLAYER1), new OrderedPair(0, 7));
        gameBoard.addPiece(new Rook(Team.PLAYER1), new OrderedPair(7, 7));
        
        gameBoard.addPiece(new Bishop(Team.PLAYER2), new OrderedPair(2, 0));
        gameBoard.addPiece(new Bishop(Team.PLAYER2), new OrderedPair(5, 0));
        gameBoard.addPiece(new Bishop(Team.PLAYER1), new OrderedPair(2, 7));
        gameBoard.addPiece(new Bishop(Team.PLAYER1), new OrderedPair(5, 7));
        
        gameBoard.addPiece(new Knight(Team.PLAYER2), new OrderedPair(1, 0));
        gameBoard.addPiece(new Knight(Team.PLAYER2), new OrderedPair(6, 0));
        gameBoard.addPiece(new Knight(Team.PLAYER1), new OrderedPair(1, 7));
        gameBoard.addPiece(new Knight(Team.PLAYER1), new OrderedPair(6, 7));
        
        gameBoard.addPiece(new King(Team.PLAYER2), new OrderedPair(4 , 0));
        gameBoard.addPiece(new King(Team.PLAYER1), new OrderedPair(4 , 7));
        
        gameBoard.addPiece(new Queen(Team.PLAYER2), new OrderedPair(3, 0));
        gameBoard.addPiece(new Queen(Team.PLAYER1), new OrderedPair(3, 7));
        
        ChessComponent myProject = new ChessComponent(gameBoard);
        MouseClickListener listener = new MouseClickListener(myProject);
        myProject.addMouseListener(listener);
        frame.add(myProject);
        frame.setVisible(true);
        
        Team whoseTurn = Team.PLAYER1;
        boolean doneWithGame = false;
        
        while (!doneWithGame)
        {
            frame.repaint();
            boolean isInCheck = false;
            
            String s;
            if (whoseTurn.equals(Team.PLAYER1)) s = "White";
            else s = "Black";
            
            if (gameBoard.isInCheck(whoseTurn)) 
            {
                myProject.setText(s + " is in check!");
                isInCheck = true;
            }
            else myProject.setText(s + "'s turn");
            
            boolean doneWithTurn = false;
            
            OrderedPair highlight3Spot1 = new OrderedPair(-1, -1);
            OrderedPair highlight3Spot2 = new OrderedPair(-1, -1);
            
            while(!doneWithTurn)
            {
                frame.repaint();
                boolean hasClickedOnValidPiece = false;
                OrderedPair oldSpot = new OrderedPair(-1, -1);
                while(!hasClickedOnValidPiece)
                {
                    listener.hasClicked = false;
                    while(!listener.hasClicked) 
                    {
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch (Exception e)
                        {
                            System.out.println("Something is wrong.");                    
                        }
                    }
                    
                    oldSpot = listener.spotClicked;
                    
                    if (whoseTurn.equals(Team.PLAYER2)) oldSpot.spinBoard();
                    
                    if (Helper.isWithinBoard(oldSpot) && gameBoard.getPiece(oldSpot).getTeam().equals(whoseTurn))  hasClickedOnValidPiece = true;
                }
                
                highlight3Spot1 = oldSpot.clone();
                gameBoard.getPiece(oldSpot).highlight1();
                ArrayList<OrderedPair> spots = gameBoard.getAllMovesFrom(oldSpot);
                for (OrderedPair spot : spots)
                {
                    gameBoard.getPiece(spot).highlight2();
                }
                frame.repaint();
                
                boolean hasClickedOnValidMoveSpot = false;
                OrderedPair newSpot = new OrderedPair(-1, -1);
                
                while (!hasClickedOnValidMoveSpot)
                {
                    listener.hasClicked = false;
                    while(!listener.hasClicked) 
                    {
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch (Exception e)
                        {
                            System.out.println("Something is wrong.");                    
                        }
                    }
                    newSpot = listener.spotClicked;
                    
                    if (whoseTurn.equals(Team.PLAYER2)) newSpot.spinBoard();
                    
                    if (Helper.isWithinBoard(newSpot)) hasClickedOnValidMoveSpot = true;
                }
                Move m = new Move(oldSpot, newSpot);
                highlight3Spot2 = newSpot.clone();
                
                boolean madeValidMove = false;
                if ((gameBoard.isValidMove(m) || gameBoard.isValidEnPassant(m)) && !isInCheck)
                {
                    Board tempBoard = gameBoard.clone();
                    if (gameBoard.isValidMove(m))tempBoard.movePiece(m);
                    else tempBoard.enPassant(m);
                    
                    if (!tempBoard.isInCheck(whoseTurn))
                    {
                        if (gameBoard.isValidMove(m)) gameBoard.movePiece(m);
                        else gameBoard.enPassant(m);
                        
                        doneWithTurn = true;
                        madeValidMove = true;
                    }
                }
                else if ((gameBoard.isValidMove(m) || gameBoard.isValidEnPassant(m)) && isInCheck)
                {
                    Board tempBoard = gameBoard.clone();
                    if (gameBoard.isValidMove(m))tempBoard.movePiece(m);
                    else tempBoard.enPassant(m);
                    
                    if (!tempBoard.isInCheck(whoseTurn)) 
                    {
                        if (gameBoard.isValidMove(m)) gameBoard.movePiece(m);
                        else gameBoard.enPassant(m);
                        
                        doneWithTurn = true;
                        madeValidMove = true;
                    }
                }
                else if (gameBoard.isValidCastle(m))
                {
                   if (!isInCheck)
                    {
                        gameBoard.castle(m);
                        doneWithTurn = true;
                    }
                }
                
                if (madeValidMove && gameBoard.getPiece(newSpot) instanceof Pawn && newSpot.getY() == 0)
                {
                    frame.repaint();
                    
                    Piece p = new BlankSpace();
                    boolean doneChoosingPiece = false;
                    PawnPromotionMenu menu = new PawnPromotionMenu(whoseTurn);
                    
                    while (!doneChoosingPiece)
                    {
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch (Exception e)
                        {
                            System.out.println("Something is wrong.");                    
                        }
                        
                        p = menu.getPiece();
                        if (!(p instanceof BlankSpace))   doneChoosingPiece = true;
                    }
                    
                    gameBoard.addPiece(p, newSpot);
                    menu.close();
                }
                
                gameBoard.resetAllHighlights1();
                gameBoard.resetAllHighlights2();
            }
            gameBoard.resetAllHighlights3();
            gameBoard.highlight3(highlight3Spot1);
            gameBoard.highlight3(highlight3Spot2);
            myProject.clearText();
            gameBoard.spinBoard();
            myProject.reverse();
            
            if (whoseTurn.equals(Team.PLAYER1)) whoseTurn = Team.PLAYER2;
            else whoseTurn = Team.PLAYER1;
            
            if (gameBoard.isInCheckmate(whoseTurn))
            {
                doneWithGame = true;
                if (whoseTurn.equals(Team.PLAYER1)) myProject.setText("Black wins!");
                else myProject.setText("White wins!");
                frame.repaint();
            }
            else if (gameBoard.isInStalemate(whoseTurn))
            {
                doneWithGame = true;
                myProject.setText("Stalemate! It's a tie.");
                frame.repaint();
            }
        }
    }       
}
