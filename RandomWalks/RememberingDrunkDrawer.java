import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.Color;

/**
 * draws a radnomly moving point going for any number of steps
 * 
 * @author Hoelzel
 * @version 11/22/15
 */

public class RememberingDrunkDrawer
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        JFrame frame = new JFrame();
        frame.setSize(RememberingDrunkComponent.WIDTH, RememberingDrunkComponent.HEIGHT);
        frame.setTitle("Random Walk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.print("Number of steps: ");
        int steps = in.nextInt();
        in.nextLine();
        
        RememberingDrunk d = new RememberingDrunk(Color.BLACK);
        RememberingDrunkComponent component = new RememberingDrunkComponent(d);
        component.setShaded(true); //true
        frame.add(component);
        frame.setVisible(true);//
        
        
        final long targetTime = 3;
        //final int checkCount = 100;
        
        for (int s = 0; s < steps; s++)
        {
            //long start = System.nanoTime();
            
            d.wander();
            
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
            
            //if (s % checkCount == 0) d.removeDoubles(checkCount);
            
            //frame.repaint();
        }
        
        int fit = component.fitScreen();
        int z = component.setZoom();
        System.out.println(z);
        if (fit == RememberingDrunkComponent.X_ONLY_FITS)
        {
            component.switchCoords();
            System.out.println("Coordinates switched");
        }
        frame.setVisible(true);
        frame.repaint();
        //System.out.println("Done");
        double shade = 1;
        while (shade > 0)
        {
            System.out.print("Enter shading factor: ");
            shade = in.nextDouble();
            in.nextLine();
            component.setDarkness(shade);
            frame.repaint();
        }
    }
}