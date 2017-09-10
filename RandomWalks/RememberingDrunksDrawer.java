import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.Color;

/**
 * draws a radnomly moving point going for any number of steps
 * 
 * @author Hoelzel
 * @version 11/21/15
 */

public class RememberingDrunksDrawer
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        JFrame frame = new JFrame();
        frame.setSize(RememberingDrunksComponent.WIDTH, RememberingDrunksComponent.HEIGHT);
        frame.setTitle("Random Walk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.print("Number of points: ");
        int num = in.nextInt();
        in.nextLine();
        
        System.out.print("Number of steps: ");
        int steps = in.nextInt();
        in.nextLine();
        
        RememberingDrunk[] drunks = new RememberingDrunk[num];
        for (int i = 0; i < num; i ++)
        {
            drunks[i] = new RememberingDrunk(new Color((int)(Math.random() * 200), (int)(Math.random() * 200), (int)(Math.random() * 200)));
        }
        RememberingDrunksComponent component = new RememberingDrunksComponent(drunks);
        frame.add(component);
        frame.setVisible(true);
        
        final long targetTime = 5;
        
        for (int s = 0; s < steps; s ++)
        {
            long start = System.nanoTime();
            
            for (RememberingDrunk d : drunks)
            {
                d.wander();
            }
            
            long elapsed = System.nanoTime() - start;
    
            long wait = targetTime - elapsed / 1000000;
            if (wait < 0)  wait = 5;
    
            try
            {
                Thread.sleep(wait);
            } catch (Exception e) 
            {
                System.out.println("AAAAAAHHHHH");
            }
            
            frame.repaint();
        }
        component.fitScreen();
        frame.repaint();
        System.out.println("Done");
    }
}