/**
 * The player of the game
 * 
 * @author Hoelzel
 * @version 8/28/2015
 */

public class Player
{
    public static final double ACCELERATION = .02;
    public static final double SPEED = 10;
    
    public static final int WIDTH = 50;
    public static final int HEIGHT = 75;
    
    private double x;
    private double y;
    private double lastY;
    private double yVel;
    private double maxY;
    
    public Player(double x)
    {
        this.x = x;
        y = 0;
        lastY = 0;
        yVel = 1;
    }
    
    public void setYVel(int yVel)
    {
        this.yVel = yVel;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void move()
    {
        lastY = y;
        y += yVel;
        if (y > maxY) maxY = y;
    }
    
    public void incrementYVel()
    {
        yVel -= ACCELERATION;
    }
    
    public void moveLeft()
    {
        x -= SPEED;
    }
    
    public void moveRight()
    {
        x += SPEED;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getYVel()
    {
        return yVel;
    }
    
    public double getYMax()
    {
        return maxY;
    }
    
    public double getLastY()
    {
        return lastY;
    }
}
