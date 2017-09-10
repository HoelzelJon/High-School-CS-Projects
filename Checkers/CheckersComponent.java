import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * Create a Checkerboard drawing
 * 
 * @author Hoelzel
 * @version 11/12/2015
 */

public class CheckersComponent extends JComponent
{
    private CheckersBoard board;
    private String text;
    
    
    public static final int MARGIN = 30;
    public static final int SQUARE = 80;
    public static final Color HIGHLIGHT1_COLOR = Color.WHITE;
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    public static final Color OUTLINE_COLOR = Color.YELLOW;
    public static final BasicStroke OUTLINE_THICKNESS = new BasicStroke(3);
    public static final Color COLOR1 = Color.RED;
    public static final Color COLOR2 = Color.BLACK;
    public static final Font textFont = new Font("Dialog", Font.PLAIN, 25);
    public static final int WIDTH = (2 * MARGIN) + (COLUMNS * SQUARE) + 15;
    public static final int HEIGHT = (2 * MARGIN) + (ROWS * SQUARE) + 30;
    
    public CheckersComponent(CheckersBoard board)
    {
        this.board = board;
        text = "";
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i = 0; i < ROWS; i++)
        {            
            for (int j = 0; j < COLUMNS; j++)
            { 
                Rectangle r = new Rectangle(MARGIN + (SQUARE * i), MARGIN + (SQUARE * j), SQUARE, SQUARE);
                
                if (board.getPiece(i, j).isHighlighted1()) g2.setColor(HIGHLIGHT1_COLOR);
                else if ((i + j) % 2 == 0) g2.setColor(COLOR1);
                else g2.setColor(COLOR2);
                
                g2.setStroke(new BasicStroke(1));
                g2.fill(r);
                
                g2.setStroke(OUTLINE_THICKNESS);
                g2.setColor(OUTLINE_COLOR);
                g2.draw(r);
                
                if (!board.isEmpty(i, j))
                {
                    Ellipse2D.Double checker = new Ellipse2D.Double(MARGIN + (SQUARE * i) + 15, MARGIN + (SQUARE * j) + 15, 50, 50);
                    
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(5));
                    g2.draw(checker);
                    
                    if (board.getPieceTeam(i, j).equals(Team.PLAYER2)) g2.setColor(Color.RED);
                    else g2.setColor(Color.BLACK);
                    
                    g2.fill(checker);
                    
                    if (board.isAKing(i, j))
                    {
                        String kingSymbol = "\u2654";
                        
                        if (board.getPieceTeam(i, j).equals(Team.PLAYER2)) g2.setColor(Color.BLACK);
                        else g2.setColor(Color.RED);
                        
                        Font kingFont = new Font("Dialog", Font.PLAIN, 40);
                        g2.setFont(kingFont);
                        
                        g2.drawString(kingSymbol, MARGIN + (SQUARE * i) + 22, MARGIN + (SQUARE * j) + 53);
                    }
                }
            }
        }
        
        g2.setFont(textFont);
        g2.setColor(Color.BLACK);
        Rectangle2D StringRect = textFont.getStringBounds(text, g2.getFontRenderContext());
        int x = (WIDTH / 2) - (int)(StringRect.getWidth() / 2);
        g2.drawString(text, x, 25);
    }
    
    public void setText(String newText)
    {
        text = newText;
    }
    
    public void clearText()
    {
        text = "";
    }
}