import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JComponent;
import java.util.ArrayList;

/**
 * Draws the path of multiple wandering points
 * 
 * @author Hoelzel
 * @version 11/22/2015
**/

public class RememberingDrunksComponent extends JComponent
{
    public static int HEIGHT = 728;
    public static int WIDTH = 1368;
    
    private RememberingDrunk[] drunks;
    int[] centerPoint;
    
    public RememberingDrunksComponent(RememberingDrunk[] drunks)
    {
        this.drunks = drunks;
        centerPoint = new int[] {0, 0};
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        int length = drunks[0].getAllPositions().size();
        for (int i = 0; i < length; i ++)
        {
            for (RememberingDrunk d: drunks)
            {
                int[] pos = d.getAllPositions().get(i);
                g2.setColor(d.getColor());
                Rectangle p = new Rectangle(pos[0] + (getWidth() / 2) - centerPoint[0], pos[1] + (getHeight() / 2) - centerPoint[1], 0, 0);
                g2.draw(p);
            }
        }
    }
    
    public void fitScreen()
    {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        
        for (RememberingDrunk d : drunks)
        {
            ArrayList<int[]> positions = d.getAllPositions();
            for (int[] p : positions)
            {
                if (p[0] < minX) minX = p[0];
                if (p[0] > maxX) maxX = p[0];
                if (p[1] < minY) minY = p[1];
                if (p[1] > maxY) maxY = p[1];
            }
        }
        
        centerPoint[0] = (minX + maxX) / 2;
        centerPoint[1] = (minY + maxY) / 2;
    }
}