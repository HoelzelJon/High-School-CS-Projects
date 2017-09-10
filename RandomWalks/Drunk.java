import java.awt.Color;

/**
 * A randomly wandering point in any number of dimentions
 * 
 * @author Hoelzel
 * @version 11/21/15
 */

public class Drunk
{
    private int[] position;
    private Color c;
    private boolean canHold;
    
    public Drunk(int dimention)
    {
        position = new int[dimention];
        for (int i = 0; i < dimention; i ++)
        {
            position[i] = 0;
        }
        c = Color.BLACK;
        canHold = false;
    }
    
    public Drunk(int dimention, Color c)
    {
        position = new int[dimention];
        for (int i = 0; i < dimention; i ++)
        {
            position[i] = 0;
        }
        this.c = c;
        canHold = false;
    }
    
    public Drunk(int dimention, Color c, boolean canHold)
    {
        position = new int[dimention];
        for (int i = 0; i < dimention; i ++)
        {
            position[i] = 0;
        }
        this.c = c;
        this.canHold = canHold;
    }
    
    public boolean equals(Drunk other)
    {
        if (position.length != other.position.length) return false;
        
        for (int i = 0; i < position.length; i ++)
        {
            if (this.position[i] != other.position[i]) return false;
        }
        return true;
    }
    
    public void wander()
    {
        int max =  position.length * 2;
        if (canHold) max ++;
        
        int rand = (int)(Math.random() * max);
        if (rand == position.length * 2) return;
        else if (rand % 2 == 0) position[rand / 2] ++;
        else position[rand / 2] --;
    }
    
    public boolean isAtOrigin()
    {
        for (int i = 0; i < position.length; i ++)
        {
            if (position[i] != 0) return false;
        }
        return true;
    }
    
    public int getPos(int dimention)
    {
        return position[dimention];
    }
    
    public int[] getPos()
    {
        return position.clone();
    }
    
    public Color getColor()
    {
        return c;
    }
}
