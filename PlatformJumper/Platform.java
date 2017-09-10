/**
 * A platform for the player to jump off of
 * 
 * @author Hoelzel
 * @version 8/28/15
 */

public class Platform
{
    public static final int HEIGHT = 5;
    public static final int WIDTH = 100;
    
    private double x;
    private int y;
    
    public Platform(double x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setX(double newX)
    {
        x = newX;
    }
    
    public void move() {};
}