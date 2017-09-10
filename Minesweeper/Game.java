import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Plays the game Minesweeper
 * 
 * @author Hoelzel
 * @version 3/12/2015
 */

public class Game
{
    public static void main(String[] args)
    {
        class MouseClickListener implements MouseListener
        {            
            private int xClicked;
            private int yClicked;
            private boolean hasClicked;
            private boolean wasRightClick;
            
            public MouseClickListener()
            {
                xClicked = -1;
                yClicked = -1;
                hasClicked = false;
                wasRightClick = false;
            }
            
            public void mousePressed(MouseEvent event)
            {
                hasClicked = true;
                xClicked = event.getX();
                yClicked = event.getY();
                wasRightClick = event.getButton() == MouseEvent.BUTTON3;
            }
            
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
            
            public void reset()
            {
                xClicked = -1;
                yClicked = -1;
                hasClicked = false;
                wasRightClick = false;
            }
        }
        
        
        Scanner in = new Scanner(System.in);
        
        System.out.print("Width: ");
        int w = in.nextInt();
        in.nextLine();
        System.out.print("Height: ");
        int h = in.nextInt();
        in.nextLine();
        System.out.print("Number of mines: ");
        int mines = in.nextInt();
        in.nextLine();
        
        Board b = new Board(w, h);
        
        JFrame frame = new JFrame();
        frame.setSize((2 * BoardComponent.MARGIN) + (w * BoardComponent.SQUARE) + 15, (2 * BoardComponent.MARGIN) + (h * BoardComponent.SQUARE) + 30);
        BoardComponent comp = new BoardComponent(b);
        
        MouseClickListener listener = new MouseClickListener();
        comp.addMouseListener(listener);
        
        frame.add(comp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        boolean clickedOnValidSpot = false;
        
        int x = -1;
        int y = -1;
        
        while (!clickedOnValidSpot)
        {
            while(!listener.hasClicked)
            {
                try { Thread.sleep(1); }
                catch (Exception e){ System.out.println("Something is wrong."); }
            }
            
            try { Thread.sleep(10); }
            catch (Exception e){ System.out.println("Something is wrong."); }
            
            if (listener.xClicked < BoardComponent.MARGIN) x = -1;
            else x = (listener.xClicked - BoardComponent.MARGIN) / BoardComponent.SQUARE;
            
            if (listener.yClicked < BoardComponent.MARGIN) y = -1;
            else y = (listener.yClicked - BoardComponent.MARGIN) / BoardComponent.SQUARE;
            
            if (x >= 0 && x < w && y >= 0 && y < h && !listener.wasRightClick) clickedOnValidSpot = true;
        }
        
        b.fillBoard(mines, x, y);
        listener.reset();
        
        while(!(b.hasRevealedMine() || b.hasCleared()))
        {
            frame.repaint();
            
            clickedOnValidSpot = false;
        
            x = -1;
            y = -1;
            clickedOnValidSpot = false;
            
            while (!clickedOnValidSpot)
            {
                while(!listener.hasClicked)
                {
                    try { Thread.sleep(1); }
                    catch (Exception e){ System.out.println("Something is wrong."); }
                }
                
                try { Thread.sleep(10); }
                catch (Exception e){ System.out.println("Something is wrong."); }
                
                if (listener.xClicked < BoardComponent.MARGIN) x = -1;
                else x = (listener.xClicked - BoardComponent.MARGIN) / BoardComponent.SQUARE;
                
                if (listener.yClicked < BoardComponent.MARGIN) y = -1;
                else y = (listener.yClicked - BoardComponent.MARGIN) / BoardComponent.SQUARE;
                
                if (x >= 0 && x < w && y >= 0 && y < h) clickedOnValidSpot = true;
                else listener.reset();
            }
            
            if (listener.wasRightClick) b.setFlag(x, y);
            else  b.checkForMine(x, y);
            
            listener.reset();
        }
        
        frame.repaint();
        
        if (b.hasRevealedMine()) System.out.println("You hit a mine!");
        else System.out.println("You win!");
    }
}
