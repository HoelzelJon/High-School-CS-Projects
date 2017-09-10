import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A game of chess for one person against the computer
 * 
 * @author Hoelzel
 * @version 8/19/2016
 */

public class VsComputerGame
{
    public static final int FRAME_WIDTH = ChessComponent.WIDTH;
    public static final int FRAME_HEIGHT = ChessComponent.HEIGHT;
    
    public static void play(int level, Team playerTeam)
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
        
        Team compTeam = Helper.getOppositeTeam(playerTeam);
        ComputerPlayer comp = new CompLv2(gameBoard, compTeam);
        if (level == 3) comp = new CompLv3(gameBoard, compTeam);
        else if (level == 4)  comp = new CompLv4(gameBoard, compTeam);
        else if (level == 5) comp = new CompLv5(gameBoard, compTeam);
        else if (level != 2) System.out.println("What?");

        Team whoseTurn = Team.PLAYER1;
        boolean doneWithGame = false;
        
        while (!doneWithGame)
        {
            if (whoseTurn.equals(playerTeam)) 
            {
                boolean isInCheck = false;
                if (gameBoard.isInCheck(whoseTurn)) 
                {
                    isInCheck = true;
                    myProject.setText("You are in check!");
                }
                
                boolean doneWithTurn = false;
                
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
                        
                        if (Helper.isWithinBoard(oldSpot) && gameBoard.getPiece(oldSpot).getTeam().equals(whoseTurn))  hasClickedOnValidPiece = true;
                    }
                    
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
                        
                        if (Helper.isWithinBoard(newSpot)) hasClickedOnValidMoveSpot = true;
                    }
                    Move m = new Move(oldSpot, newSpot);
                    
                    
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
            }
            else 
            {                
                gameBoard.spinBoard();
                
                int rand = (int)(Math.random() * 1000) + 500;
                frame.repaint();
                try
                {
                    Thread.sleep(rand);
                }
                catch (Exception e)
                {
                    System.out.println("Something is wrong.");
                }
                gameBoard.spinBoard();
                
                Move m = comp.nextMove();
                gameBoard.movePiece(m);
                gameBoard.highlight3(m.getOldSpot());
                gameBoard.highlight3(m.getNewSpot());
                
                if (gameBoard.getPiece(m.getNewSpot()) instanceof Pawn && m.getNewSpot().getY() == 0)
                {
                    gameBoard.spinBoard();
                    frame.repaint();
                    
                    int rand2 = (int)(Math.random() * 1000) + 500;
                    try
                    {
                        Thread.sleep(rand2);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Something is wrong.");                    
                    }
                    gameBoard.spinBoard();
                    
                    gameBoard.addPiece(comp.pieceToChangePawnTo(), m.getNewSpot());
                    gameBoard.highlight3(m.getNewSpot());
                    frame.repaint();
                }
                
                m.spinBoard();
            }
            myProject.clearText();
            gameBoard.spinBoard();
            
            if (whoseTurn.equals(Team.PLAYER1)) whoseTurn = Team.PLAYER2;
            else whoseTurn = Team.PLAYER1;
            
            if (gameBoard.isInCheckmate(whoseTurn))
            {
                doneWithGame = true;
                if (whoseTurn.equals(playerTeam)) myProject.setText("The computer wins!");
                else
                {
                    gameBoard.spinBoard();
                    myProject.setText("You win!");
                }
                frame.repaint();
            }
            else if (gameBoard.isInStalemate(whoseTurn))
            {
                doneWithGame = true;
                myProject.setText("Stalemate! It's a tie.");
                if (!whoseTurn.equals(playerTeam)) gameBoard.spinBoard();
                frame.repaint();
            }
        }
    }       
}