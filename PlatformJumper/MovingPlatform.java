/**
 * A platform that moves left-to right
 * 
 * @author Hoelzel
 * @version 11/3/2015
 */

public class MovingPlatform extends Platform
{
    private boolean isMovingRight;
    private double speed;
    
    public MovingPlatform(double x, int y, double speed)
    {
        super(x, y);
        isMovingRight = ((int)(2 * Math.random()) == 1);
        this.speed = speed;
    }
    
    @Override
    public void move()
    {
        if (isMovingRight) setX(getX() + speed);
        else setX(getX() - speed);
        
        if (getX() + WIDTH >= GameComponent.WIDTH && isMovingRight)  isMovingRight = false;
        else if (getX() <= 0 && !isMovingRight) isMovingRight = true;
    }
}
