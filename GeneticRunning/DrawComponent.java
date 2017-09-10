import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * The thing that draws the stuff
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */

public class DrawComponent extends JComponent
{
    private Creature c;
    private int record;
    String text;
    private int time;
    
    public DrawComponent()
    {
        c = null;
        record = Integer.MIN_VALUE;
        text = "";
        time = 50;
    }
    
    public void setRecord(int newNum)
    {
        record = newNum;
    }
    
    public void setText(String t)
    {
        text = t;
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(new Color(135, 206, 250));
        Rectangle sky = new Rectangle(0, 0, getWidth(), getHeight());
        g2.fill(sky);
        
        g2.setColor(new Color(0, 175, 0));
        Rectangle floor = new Rectangle(0, 2 * getHeight() / 3, getWidth(), getHeight() / 2);
        g2.fill(floor);
        
        final int LINE_DISTANCE = 500;
        
        if (!c.equals(null))
        {
            ArrayList<Muscle> muscles = c.getMuscles();
            
            int middle = (int)c.getAverageX();
            
            g2.setStroke(new BasicStroke(1));
            
            int i = middle - (getWidth() / 2);
            
            g2.setColor(Color.BLACK);
            
            while (i % LINE_DISTANCE != 0)
            {
                i ++;
            }

            while (i < middle + (getWidth() / 2))
            {
                g2.draw(new Line2D.Double(i - middle + (getWidth() / 2), 0, i - middle + (getWidth() / 2), getHeight()));
                i += LINE_DISTANCE;
            }
            
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            
            if (record > middle - (getWidth() / 2) && record < middle + (getWidth() / 2))
            {
                g2.draw(new Line2D.Double(record - middle + (getWidth() / 2), 0, record - middle + (getWidth() / 2), getHeight()));
            }
            
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(1));
            g2.draw(new Line2D.Double(getWidth() / 2, 0, getWidth() / 2, getHeight()));
                
            g2.setStroke(new BasicStroke(10));
            
            for (Muscle m : muscles)
            {
                //if (m.isCorrectLength()) g2.setColor(Color.GREEN);
                //else
                //{
                    int num = 255 - (int)((m.getStrength() - SimpleEvolutionTester.MIN_STRENGTH) * 255 / (SimpleEvolutionTester.MAX_STRENGTH - SimpleEvolutionTester.MIN_STRENGTH));
                    if (num > 255) num = 255;
                    else if (num < 0) num = 0;
                    g2.setColor(new Color(num, num, num));
                //}
                
                Node n1 = m.getN1();
                Node n2 = m.getN2();
                Line2D.Double l = new Line2D.Double((getWidth() / 2) + n1.getX() - middle, (2 * getHeight() / 3) - n1.getY(), (getWidth() / 2) + n2.getX() - middle, (2 * getHeight() / 3) - n2.getY());
                g2.draw(l);
            }
            
            ArrayList<Node> nodes = c.getNodes();
            
            for (Node n : nodes)
            {
                int colorNum = 255 - (int)(n.getFrictionCoefficient() * 255);
                g2.setColor(new Color(colorNum, colorNum, colorNum));
                Ellipse2D.Double circle = new Ellipse2D.Double((getWidth() / 2) + n.getX() - Node.RADIUS - middle, (2 * getHeight() / 3) - (n.getY() + Node.RADIUS), Node.RADIUS * 2, Node.RADIUS * 2);
                g2.fill(circle);
            }
            
            g2.setColor(Color.BLACK);
            String distance = "" + (int)c.getAverageX();
            g2.drawString(distance, 50, 50);
            
            g2.drawString("" + time, getWidth() - 100, getHeight() - 100);
        }
        
        g2.setColor(Color.BLACK);
        g2.drawString(text, getWidth() / 2, (getHeight() * 2 / 3) + 20);
    }
    
    public void setCreature(Creature c)
    {
        this.c = c;
    }
    
    public void setTime(int t)
    {
        time = t;
    }
}
