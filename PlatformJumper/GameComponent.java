import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

/**
 * draw the game
 * 
 * @author Hoelzel
 * @version 8/28/2015
 */

public class GameComponent extends JComponent
{    
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 850;
    public static final BasicStroke OUTLINE_THICKNESS = new BasicStroke(3);
    
    private ArrayList<Platform> platforms;
    private Player player;
    private int screenHeight;
    private String text;
    
    public GameComponent(ArrayList<Platform> plats, Player play)
    {
        platforms = plats;
        player = play;
        screenHeight = -500;
        text = "";
    }
    
    public void setScreenHeight(int y)
    {
        screenHeight = y;
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.WHITE);
        g2.fill(background);
        
        for (Platform p : platforms)
        {
            Rectangle r = new Rectangle((int)p.getX(), HEIGHT - (p.getY() - screenHeight), Platform.WIDTH, Platform.HEIGHT);
            g2.setColor(Color.BLUE);
            g2.draw(r);
            g2.fill(r);
        }
        
        Rectangle playerRect = new Rectangle((int)player.getX(), (int)(HEIGHT - (player.getY() - screenHeight)), Player.WIDTH, Player.HEIGHT);
        g2.setColor(Color.BLACK);
        g2.draw(playerRect);
        g2.fill(playerRect);
        
        String score = "Score: " + (int)(player.getYMax() / 25);
        g2.setFont(new Font("Arial Unicode MS", Font.BOLD, 25));
        g2.drawString(score, 25, HEIGHT - 75);
        
        Font f = new Font("Arial Unicode MS", Font.PLAIN, 100);
        g2.setFont(f);
        Rectangle2D StringRect = f.getStringBounds(text, g2.getFontRenderContext());
        int x = (WIDTH / 2) - (int)(StringRect.getWidth() / 2);
        g2.drawString(text, x, 550);
    }
    
    public int getScreenHeight()
    {
        return screenHeight;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public void clearText()
    {
        text = "";
    }
}