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
import java.util.ArrayList;

/**
 * Create a Checkerboard drawing
 * 
 * @author Hoelzel
 * @version 10/27/2015
 */

public class ChessComponent extends JComponent
{
    private Board board;
    private String text;
    private boolean reversed;
    
    
    public ChessComponent(Board board)
    {
        this.board = board;
        text = "";
        reversed = false;
    }
    
    public void paintComponent(Graphics g)
    {
        // Recover Graphics2D
        Graphics2D g2 = (Graphics2D) g;
        
        int pieceFontSize = getPieceFontSize();
        Font pieceFont = new Font(FONT_NAME, FONT_STYLE, pieceFontSize);
        int textFontSize = getTextFontSize();
        Font textFont = new Font(FONT_NAME, FONT_STYLE, textFontSize);
        int square = getSquare();
        int margin = getMargin();
        BasicStroke outline = new BasicStroke(getOutlineThickness());
        BasicStroke squareDrawer = new BasicStroke(1);
        
        for (int i = 0; i < 8; i++)
        {            
            for (int j = 0; j < 8; j++)
            {                
                Rectangle r;
                if (reversed) r = new Rectangle(margin + (square * (7 - i)), margin + (square * (7 - j)), square, square);
                else r = new Rectangle(margin + (square * i), margin + (square * j), square, square);
                
                if (board.getPiece(new OrderedPair(i, j)).isHighlighted1()) g2.setColor(Color.RED);
                else if (board.getPiece(new OrderedPair(i, j)).isHighlighted2()) g2.setColor(Color.GREEN);
                else if (board.getPiece(new OrderedPair(i, j)).isHighlighted3()) g2.setColor(Color.YELLOW);
                else if ((i + j) % 2 == 0) g2.setColor(COLOR1);
                else g2.setColor(COLOR2);
                
                g2.setStroke(squareDrawer);
                g2.fill(r);
                
                g2.setStroke(outline);
                g2.setColor(OUTLINE_COLOR);
                g2.draw(r);
                
                OrderedPair spot = new OrderedPair(i, j);
                if (!board.isEmpty(spot))
                {
                    g2.setColor(Color.BLACK);
                    
                    String symbol = Helper.toSymbol(board.getPiece(spot));
                    
                    g2.setFont(pieceFont);
                    
                    int extra = (square - (int)(pieceFont.getStringBounds(symbol, g2.getFontRenderContext()).getWidth())) / 2;
                    
                    if (reversed) g2.drawString(symbol, margin + (square * (7 - i)) + extra, margin + (square * (7 - j)) + pieceFontSize);
                    else g2.drawString(symbol, margin + (square * i) + extra, margin + (square * j) + pieceFontSize);
                }
            }
        }
        
        ArrayList<Piece> deadPieces1;
        if (reversed) deadPieces1 = board.getDeadPieces2();
        else deadPieces1 = board.getDeadPieces1();
        
        int graveSquareWidth = getGraveSquareWidth();
        int graveSquareHeight = getGraveSquareHeight();
        
        g2.setFont(pieceFont);
        for (int i = 0; i < deadPieces1.size(); i ++)
        {
            g2.setColor(Color.BLACK);
            String symbol = Helper.toSymbol(deadPieces1.get(i)); 
            g2.drawString(symbol, (8 * square) + (5 * margin / 2) + ((i / 4) * graveSquareWidth) , (int)(margin * 3.5) + (int)(textFont.getStringBounds(text, g2.getFontRenderContext()).getHeight() + (graveSquareHeight * (8 - (i % 4)))));
        }
        
        g2.setFont(textFont);
        g2.drawString(text, (8 * square) + (margin * 2), (4 * graveSquareHeight) + (int)(textFont.getStringBounds(text, g2.getFontRenderContext()).getHeight()) + (3 * margin));
        
        g2.setFont(pieceFont);
        ArrayList<Piece> deadPieces2;
        if (reversed) deadPieces2 = board.getDeadPieces1();
        else deadPieces2 = board.getDeadPieces2();
        
        for (int i = 0; i < deadPieces2.size(); i ++)
        {
            g2.setColor(Color.BLACK);
            String symbol = Helper.toSymbol(deadPieces2.get(i));
            g2.drawString(symbol, (8 * square) + (5 * margin / 2) + ((i / 4) * graveSquareWidth), margin + (graveSquareHeight * (i % 4)) + pieceFontSize);
        }
    }
    
    public void clearText()
    {
        text = "";
    }
    
    public void setText(String newText)
    {
        text = newText;
    }
    
    public int getMargin()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * MARGIN) / HEIGHT;
        }
        else
        {
            return (getWidth() * MARGIN) / WIDTH;
        }
    }
    
    public int getSquare()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * SQUARE) / HEIGHT;
        }
        else
        {
            return (getWidth() * SQUARE) / WIDTH;
        }
    }
    
    public int getOutlineThickness()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return ((getHeight() * OUTLINE_THICKNESS) / HEIGHT) + 1;
        }
        else
        {
            return ((getWidth() * OUTLINE_THICKNESS) / WIDTH) + 1;
        }
    }
    
    public int getPieceFontSize()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * PIECE_FONT_SIZE) / HEIGHT;
        }
        else
        {
            return (getWidth() * PIECE_FONT_SIZE) / WIDTH;
        }
    }
    
    public int getTextFontSize()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * TEXT_FONT_SIZE) / HEIGHT;
        }
        else
        {
            return (getWidth() * TEXT_FONT_SIZE) / WIDTH;
        }
    }
    
    public int getGraveSquareHeight()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * GRAVE_SQUARE_HEIGHT) / HEIGHT;
        }
        else
        {
            return (getWidth() * GRAVE_SQUARE_HEIGHT) / WIDTH;
        }
    }
    
    public int getGraveSquareWidth()
    {
        if (WIDTH * getHeight() < HEIGHT * getWidth())
        {
            return (getHeight() * GRAVE_SQUARE_WIDTH) / HEIGHT;
        }
        else
        {
            return (getWidth() * GRAVE_SQUARE_WIDTH) / WIDTH;
        }
    }
    
    public void reverse()
    {
        reversed = !reversed;
    }
    
    public static final int WIDTH = 965;
    public static final int HEIGHT = 700;
    // = get rid of eventually
    
    public static final int MARGIN = 20; //
    public static final int SQUARE = 80; //
    public static final Color OUTLINE_COLOR = Color.BLACK;
    public static final int OUTLINE_THICKNESS = 3;  //
    public static final Color COLOR1 = Color.WHITE;
    public static final Color COLOR2 = new Color(150, 150, 150);
    public static final String FONT_NAME = "Arial Unicode MS";
    public static final int FONT_STYLE = Font.PLAIN;
    public static final int PIECE_FONT_SIZE = 60; //
    public static final int TEXT_FONT_SIZE = 30; //
    public static final int GRAVE_SQUARE_HEIGHT = 65; //
    public static final int GRAVE_SQUARE_WIDTH = 60; //
}