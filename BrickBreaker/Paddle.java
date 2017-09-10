/**
 * The paddle that the user controls
 * 
 * @author Hoelzel
 * @version 7/13/2015
 */

public class Paddle
{
    private int x;
    private int lives;
    
    public static final int Y = 640;
    public static final int WIDTH = 100;
    public static final int THICKNESS = 15;
    
    public Paddle(int lives)
    {
        x = GameComponent.WIDTH / 2;
        this.lives = lives;
    }
    
    public void moveTo(int newX)
    {
        x = newX;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void lostLife()
    {
        lives --;
    }
    
    public int getLives()
    {
        return lives;
    }
}
