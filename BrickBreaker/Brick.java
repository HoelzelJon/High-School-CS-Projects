import java.awt.Color;
import java.awt.Rectangle;

/**
 * A generic brick that the ball can break
 * 
 * @author Hoelzel
 * @version 7/13/15
 */

public class Brick
{
    private Rectangle rect;
    private int hitsLeft;
    
    public static final int WIDTH = 60;
    public static final int HEIGHT = 50;
    
    public Brick(int x, int y, int hitsLeft)
    {
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
        this.hitsLeft = hitsLeft;
    }
    
    public int getX()
    {
        return (int)rect.getX();
    }
    
    public int getY()
    {
        return (int)rect.getY();
    }
    
    public Color getColor()
    {
        if (hitsLeft == 1) return Color.RED;
        else if (hitsLeft == 2) return Color.GREEN;
        else return Color.WHITE;
    }
    
    public Rectangle getRect()
    {
        return  rect;
    }
    
    public int getHitsLeft()
    {
        return hitsLeft;
    }
    
    public void gotHit()
    {
        hitsLeft --;
    }
}
