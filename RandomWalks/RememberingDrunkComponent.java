import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JComponent;
import java.util.ArrayList;

/**
 * Draws the path of a wandering point
 * 
 * @author Hoelzel
 * @version 11/21/2015
**/

public class RememberingDrunkComponent extends JComponent
{
    public static final int ALL_FITS = 0;
    public static final int X_ONLY_FITS = 1;
    public static final int Y_ONLY_FITS = 2;
    public static final int NONE_FIT = 3;
    
    public static final double DARKNESS_MULTIPLIER = 1;
    
    public static final int HEIGHT = 900;
    public static final int WIDTH = 1600;
    
    private RememberingDrunk d;
    int[] centerPoint;
    private boolean coordsSwitched;
    private boolean shaded;
    private int zoom;
    private double darkness;
    
    public RememberingDrunkComponent(RememberingDrunk drunk)
    {
        d = drunk;
        centerPoint = new int[] {0, 0};
        coordsSwitched = false;
        shaded = false;
        zoom = 1;
        darkness = DARKNESS_MULTIPLIER;
    }
    
    /**
     * only uses the zoom thing for shaded graphs
     * only switches coordinates for non-shaded graphs
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        
        if (!shaded)
        {
            g2.setColor(d.getColor());
            ArrayList<int[]> positions = d.getAllPositions();
            
            if (!coordsSwitched)
            {
                for (int i = 0; i < positions.size(); i ++)
                {
                    int[] pos = positions.get(i);
                    Rectangle p = new Rectangle(pos[0] + (getWidth() / 2) - centerPoint[0], pos[1] + (getHeight() / 2) - centerPoint[1], 0, 0);
                    g2.draw(p);
                }
            }
            else
            {
                for (int i = 0; i < positions.size(); i ++)
                {
                    int[] pos = positions.get(i);
                    Rectangle p = new Rectangle(pos[1] + (getWidth() / 2) - centerPoint[1], pos[0] + (getHeight() / 2) - centerPoint[0], 0, 0);
                    g2.draw(p);
                }
            }
        }
        else
        {
            ArrayList<int[]> positions = d.getAllPositions();
            
            if (!coordsSwitched)
            {
                int left = getLeftEdge();
                int top = getTopEdge();
                
                int width = getWidth();
                int height = getHeight();
                
                g2.setColor(Color.WHITE);
                Rectangle background = new Rectangle(0, 0, width, height);
                g2.fill(background);
                
                int w; // width of space to be drawn on (in pixels)
                int xOffset;
                
                if (width < (getMaxX() - getMinX()) / zoom)
                {
                    w = width;
                    xOffset = 0;
                }
                else
                {
                    w = (getMaxX() - getMinX()) / zoom;
                    xOffset = (getMinX() - left) / zoom;
                }
                
                int h;
                int yOffset;
                
                if (height < (getMaxY() - getMinY()) / zoom)
                {
                    h = height;
                    yOffset = 0;
                }
                else
                {
                    h = (getMaxY() - getMinY()) / zoom;
                    yOffset = (getMinY() - top) / zoom;
                }
                
                int[][] grid = new int[w+2][h+2]; // +1, +1?
                
                for (int i = positions.size() - 1; i >= 0; i --)
                {
                    int[] pos = positions.get(i);
                    if (pos[0] > left && pos[0] < left + (width * zoom) && pos[1] > top && pos[1] < top + (height * zoom))
                    {
                        grid[((pos[0] - left)/ zoom) - xOffset][((pos[1] - top) / zoom) - yOffset] ++;
                    }
                }
                
                
                for (int x = 0; x < w; x ++)
                {
                    for (int y = 0; y < h; y ++)
                    {
                        if (grid[x][y] > 0)
                        {
                            int light = 255 - (int)(grid[x][y] *darkness);
                            if (light < 0) light = 0;
                            
                            g2.setColor(new Color(light, light, light));
                            Rectangle r = new Rectangle(x + xOffset, y + yOffset, 0, 0);
                            g2.draw(r);
                        }
                    }
                }
            }
            else
            {
            }
        }
    }
    
    /**
     * Fits the drawing to the screen. one of the static ints describing what coordinates fit
     */
    public int fitScreen()
    {
        ArrayList<int[]> positions = d.getAllPositions();
        
        int minX = getMinX();
        int minY = getMinY();
        int maxX = getMaxX();
        int maxY = getMaxY();
        
        centerPoint[0] = (minX + maxX) / 2;
        centerPoint[1] = (minY + maxY) / 2;
        
        if (maxX - minX < WIDTH && maxY - minY < HEIGHT) return ALL_FITS;
        else if (maxX - minX < WIDTH && maxX - minX < HEIGHT) return X_ONLY_FITS;
        else if (maxY - minY < WIDTH && maxY - minY < HEIGHT) return Y_ONLY_FITS;
        else return NONE_FIT;
    }
    
    public void switchCoords()
    {
        coordsSwitched = !coordsSwitched;
    }
    
    public int getMinX()
    {
        ArrayList<int[]> positions = d.getAllPositions();
        
        int minX = 0;
        
        for (int i = 0; i < positions.size(); i ++)
        {
            int[] p = positions.get(i);
            if (p[0] < minX) minX = p[0];
        }
        
        return minX;
    }
    
    public int getMinY()
    {
        ArrayList<int[]> positions = d.getAllPositions();
        
        int minY = 0;
        
        for (int i = 0; i < positions.size(); i ++)
        {
            int[] p = positions.get(i);
            if (p[1] < minY) minY = p[1];
        }
        
        return minY;
    }
    
    public int getMaxX()
    {
        ArrayList<int[]> positions = d.getAllPositions();
        int maxX = 0;
        
        for (int i = 0; i < positions.size(); i ++)
        {
            int[] p = positions.get(i);
            if (p[0] > maxX) maxX = p[0];
        }
        
        return maxX;
    }
    
    public int getMaxY()
    {
        ArrayList<int[]> positions = d.getAllPositions();
        int maxY = 0;
        
        for (int i = 0; i < positions.size(); i ++)
        {
            int[] p = positions.get(i);
            if (p[1] > maxY) maxY = p[1];
        }
        
        return maxY;
    }
    
    public void setShaded(boolean s)
    {
        shaded = s;
    }
    
    private int getLeftEdge()
    {
        return (centerPoint[0] - ((getWidth() / 2) * zoom));
    }
    
    private int getTopEdge()
    {
        return (centerPoint[1] - ((getHeight() / 2) * zoom));
    }
    
    /**
     * only use after doing fitScreen
     */
    public int setZoom()
    {
        int zoomX;
        int zoomY;
        if (getWidth() > 0 && getHeight() > 0)
        {
            zoomX = ((getMaxX() - getMinX()) / getWidth()) + 1;
            zoomY = ((getMaxY() - getMinY()) / getHeight()) + 1;
        }
        else
        {
            zoomX = ((getMaxX() - getMinX()) / WIDTH) + 1;
            zoomY = ((getMaxY() - getMinY()) / HEIGHT) + 1;
        }
        
        if (zoomX > zoomY)
        {
            zoom = zoomX;
            return zoomX;
        }
        else 
        {
            zoom = zoomY;
            return zoomY;
        }
    }
    
    public void setDarkness(double dark)
    {
        darkness = dark;
    }
}