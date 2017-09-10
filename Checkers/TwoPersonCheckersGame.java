import javax.swing.JFrame;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * The game of checkers
 * 
 * @author Hoelzel
 * @version 11/10/2015
 */

public class TwoPersonCheckersGame
{
    public static void play()
    {
        class MouseClickListener implements MouseListener
        {            
            private OrderedPair spotClicked;
            private boolean hasClicked;
            
            public MouseClickListener()
            {
                spotClicked = new OrderedPair(-1, -1);
                hasClicked = false;
            }
            
            public void mousePressed(MouseEvent event)
            {
                hasClicked = true;
                spotClicked = CheckersHelper.getBoardPosition(event.getX(), event.getY());
            }
            
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        
        CheckersBoard gameBoard = new CheckersBoard();
        
        for (int i = 5; i <= 7; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if ((i + j) % 2 == 1) gameBoard.addPiece(new CheckersPiece(Team.PLAYER1), j, i);
            }
        }
        
        for (int i = 0; i <= 2; i ++)
        {
            for (int j = 0; j < 8; j ++)
            {
                if ((i + j) % 2 == 1) gameBoard.addPiece(new CheckersPiece(Team.PLAYER2), j, i);
            }
        }
        
        Team whoseTurn = Team.PLAYER1;
        
        boolean doneWithGame = false;
        
        JFrame frame = new JFrame();
        frame.setSize(CheckersComponent.WIDTH, CheckersComponent.HEIGHT);
        frame.setTitle("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CheckersComponent myProject = new CheckersComponent(gameBoard);
        frame.add(myProject);
        MouseClickListener listener = new MouseClickListener();
        myProject.addMouseListener(listener);
        frame.setVisible(true);
        
        while (!doneWithGame)
        {            
            if (gameBoard.canMove(whoseTurn))
            {
                frame.repaint();
                boolean doneWithTurn = false;
                
                while (!doneWithTurn)
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
                        
                        if (CheckersHelper.isWithinBoard(oldSpot) && gameBoard.getPieceTeam(oldSpot.getX(), oldSpot.getY()).equals(whoseTurn))  hasClickedOnValidPiece = true;
                    }
                    gameBoard.highlight1(oldSpot);
                    myProject.clearText();
                    frame.repaint();
                    
                    boolean wasKingBefore = gameBoard.isAKing(oldSpot.getX(), oldSpot.getY());
                    hasClickedOnValidPiece = false;
                    OrderedPair newSpot = new OrderedPair(-1, -1);
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
                        newSpot = listener.spotClicked;
                        
                        if (CheckersHelper.isWithinBoard(newSpot))  hasClickedOnValidPiece = true;
                    }
                    
                    int newX = newSpot.getX();
                    int newY = newSpot.getY();
                    
                    Move move = new Move(oldSpot.getX(), oldSpot.getY(), newX, newY);
                    boolean isKingAfter = wasKingBefore;
                    
                    if (gameBoard.canJump(whoseTurn))
                    {
                        if (gameBoard.isValidJumpMove(move, whoseTurn))
                        {
                            gameBoard.jumpMove(move);
                            isKingAfter = gameBoard.isAKing(newX, newY);
                            
                            while (gameBoard.hasMoreJumpMoves(newX, newY) && !(!wasKingBefore && isKingAfter))
                            {
                                boolean hasMadeAnotherJumpMove = false;
                                int currentX = newX;
                                int currentY = newY;
                                
                                frame.repaint();
                                //myProject.setText("You can move the piece at " + CheckersHelper.toCode(currentX, currentY) + " again.");
                                
                                while (!hasMadeAnotherJumpMove)
                                {
                                    boolean hasMadeValidInput = false;
                                    
                                    hasClickedOnValidPiece = false;
                                    newSpot = new OrderedPair(-1, -1);
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
                                        newSpot = listener.spotClicked;
                                        
                                        if (CheckersHelper.isWithinBoard(newSpot))  hasClickedOnValidPiece = true;
                                    }
                                    
                                    newX = newSpot.getX();
                                    newY = newSpot.getY();
                                    
                                    Move newJumpMove = new Move(currentX, currentY, newX, newY);
                                    
                                    if (gameBoard.isValidJumpMove(newJumpMove, whoseTurn))
                                    {
                                        gameBoard.jumpMove(newJumpMove);
                                        isKingAfter = gameBoard.isAKing(newX, newY);
                                        hasMadeAnotherJumpMove = true;
                                    }
                                    //else System.out.println("Invalid move");
                                }
                            }
                            doneWithTurn = true;
                        }
                        else if (gameBoard.isValidMove(move, whoseTurn)) myProject.setText("Invalid move. If you can make a jump, you must jump.");
                        //else System.out.println("Invalid move.");
                    }
                    else if (gameBoard.isValidMove(move, whoseTurn)) 
                    {
                        gameBoard.movePiece(move);
                        doneWithTurn = true;
                        isKingAfter = gameBoard.isAKing(move.getNewSpot().getX(), move.getNewSpot().getY());
                    }
                    //else System.out.println("Invalid move");
                    
                    gameBoard.clearHighlight1();
                }
            }
            else
            {
                myProject.setText("You cannot move. It is a draw.");
                doneWithGame = true;
                frame.repaint();
            }
            
            if (gameBoard.getWinner().equals(Team.PLAYER1))
            {
                doneWithGame = true;
                myProject.setText("Black wins!");
                frame.repaint();
            }
            else if (gameBoard.getWinner().equals(Team.PLAYER2))
            {
                doneWithGame = true;
                myProject.setText("Red wins!");
                frame.repaint();
            }
            
            gameBoard.spinBoard();
            
            if (whoseTurn.equals(Team.PLAYER1)) whoseTurn = Team.PLAYER2;
            else if (whoseTurn.equals(Team.PLAYER2)) whoseTurn = Team.PLAYER1;
        }
    }
}
