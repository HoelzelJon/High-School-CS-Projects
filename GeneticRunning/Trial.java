import javax.swing.JFrame;
import java.util.ArrayList;

/**
 * runs a trial of a running creature
 * 
 * @author Hoelzel
 * @version 2/4/2016
 */

public abstract class Trial
{
    private DrawComponent comp;
    private JFrame frame;
    private Creature creature;
    
    public void setDrawer(DrawComponent component, JFrame frame)
    {
        comp = component;
        this.frame = frame;
    }
    
    public abstract double runTrial(boolean willDisplay, int targetTime);
    
    public void setCreature(Creature c)
    {
        creature = c;
    }
    
    public JFrame getFrame()
    {
        return frame;
    }
    
    public DrawComponent getComp()
    {
        return comp;
    }
    
    public Creature getCreature()
    {
        return creature;
    }
}
