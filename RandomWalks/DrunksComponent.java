import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * draws a bunch of 2-dimentional wandering points
 * 
 * @author Hoelzel
 * @version 11/22/2015
 */

public class DrunksComponent extends JComponent
{
    public static int HEIGHT = 728;
    public static int WIDTH = 1368;
    
    private Drunk[] drunks;
    
    public DrunksComponent(Drunk[] drunks)
    {
        this.drunks = drunks;
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        for (Drunk d : drunks)
        {
            g2.setColor(d.getColor());
           
            Rectangle p = new Rectangle(d.getPos(0) + (getWidth() / 2), d.getPos(1) + (getHeight() / 2), 0, 0);
            //g2.setColor(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
            g2.draw(p);
        }
    }
}