import java.awt.geom.Ellipse2D;

/**
 * The ball for the game
 * 
 * @author Hoelzel
 * @version 7/13/15
 */

public class Ball
{
    private Ellipse2D.Double circle;
    private double xVel;
    private double yVel;
    
    public static final int DIAMETER = 20;
    
    public Ball(double x, double y, double xVel, double yVel)
    {
        circle = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        this.xVel = xVel;
        this.yVel = yVel;
    }
    
    public double getX()
    {
        return circle.getX();
    }
    
    public double getY()
    {
        return circle.getY();
    }
    
    public double getXVel()
    {
        return xVel;
    }
    
    public double getYVel()
    {
        return yVel;
    }
    
    public void changeVelocity(double newXVel, double newYVel)
    {
        xVel = newXVel;
        yVel = newYVel;
    }
    
    public void move()
    {
        circle = new Ellipse2D.Double(getX() + xVel, getY() + yVel, DIAMETER, DIAMETER);
    }
    
    public Ellipse2D.Double getCircle()
    {
        return new Ellipse2D.Double(getX() - (DIAMETER / 2), getY(), DIAMETER, DIAMETER);
    }
}
