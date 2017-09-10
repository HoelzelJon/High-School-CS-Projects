import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * Draw a bunch of wandering points
 * 
 * @author Hoelzel
 * @version 11/22/2015
 */

public class DrunksDrawer
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        JFrame frame = new JFrame();
        frame.setSize(DrunksComponent.WIDTH, DrunksComponent.HEIGHT);
        frame.setTitle("Random Walk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //System.out.print("Number of points: ");
        //int num = in.nextInt();
        int num = 20000;
        //in.nextLine();
        
        Drunk[] drunks = new Drunk[num];
        
        for (int i = 0; i < num; i ++)
        {
            drunks[i] = new Drunk(2, new Color((int)(Math.random() * 200), (int)(Math.random() * 200), (int)(Math.random() * 200)), true);
        }
        
        DrunksComponent component = new DrunksComponent(drunks);
        frame.add(component);
        frame.setVisible(true);
        
        long targetTime = 8;
        
        while (true)
        {
            //long start = System.nanoTime();
            
            for (Drunk d : drunks)
            {
                d.wander();
            }
            
            //long elapsed = System.nanoTime() - start;
    
            //long wait = targetTime - elapsed / 1000000;
            //if (wait < 0)  wait = 5;
    
            //try
            //{
            //    Thread.sleep(wait);
            //} catch (Exception e) 
            //{
            //    System.out.println("AAAAAAHHHHH");
            //}
            
            frame.repaint();
        }
        
        //System.out.println("Done");
    }
}
