import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Font;

/**
 * Displays the Minesweeper board
 * 
 * @author Hoelzel
 * @version 3/22/2016
 */

public class BoardComponent extends JComponent
{
    private Board board;
    
    public BoardComponent(Board b)
    {
        board = b;
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        int w = board.getWidth();
        int h = board.getHeight();
        
        g2.setFont(new Font("Default", Font.BOLD, 17));
        
        for (int i = 0; i < w; i ++)
        {
            for (int j = 0; j < h; j ++)
            {
                Rectangle r = new Rectangle(MARGIN + (SQUARE * i), MARGIN + (SQUARE * j), SQUARE, SQUARE);
                if (board.isRevealed(i, j)) g2.setColor(new Color(200, 200, 200));
                else g2.setColor(Color.BLUE);
                g2.fill(r);
                
                g2.setColor(Color.WHITE);
                g2.draw(r);
                
                if (board.isFlagged(i, j)) g2.setColor(Color.RED);
                else g2.setColor(Color.BLACK);
                g2.drawString(board.getString(i, j), MARGIN + (SQUARE * i) + (SQUARE / 2) - 5, MARGIN + (SQUARE * j) + (SQUARE / 2) + 5);
            }
        }
    }
    
    public static int MARGIN = 25;
    public static int SQUARE = 30;
}
