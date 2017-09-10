import javax.swing.JFrame;

/**
 * A tester to find how high a creature jumps
 * 
 * @author Hoelzel
 * @version 2/4/2015
 */

public class TrialJumper extends Trial
{
    public static final int FRAMES_RUN = 1000;
    
    public double runTrial(boolean willDisplay, int targetTime)
    {
        boolean done = false;
        
        DrawComponent comp = getComp();
        JFrame frame = getFrame();
        Creature creature = getCreature();
        
        double maxHeight = -1;
        
        if (willDisplay)
        {
            if (!frame.isVisible()) frame.setVisible(true);
            int i = 0;
            int timerCount = 1000 / targetTime;
            
            while (i < FRAMES_RUN && creature.stillStanding())
            {
                long start = System.nanoTime();
                frame.repaint();
                
                if (i % timerCount == 0) comp.setTime((FRAMES_RUN - i) / timerCount);
                creature.move();
                if (creature.getHeight() > maxHeight) maxHeight = creature.getHeight();
                i ++;
                
                long elapsed = System.nanoTime() - start;
                long wait = targetTime - elapsed / 1000000;
                if (wait < 0) 
                {
                    wait = 5;
                }
                
                try 
                {
                    Thread.sleep(wait);
                } catch (Exception e) 
                {
                    System.out.println("AAAAAAHHHHH");
                }
            }
        }
        else
        {
            int i = 0;
            
            while (i < FRAMES_RUN && creature.stillStanding())
            {
                creature.move();
                if (creature.getHeight() > maxHeight) maxHeight = creature.getHeight();
                i ++;
            }
        }
        
        return maxHeight;
    }
}
