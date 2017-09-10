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
 * @version 10/18/2015
 */

public class GameComponent extends JComponent
{
    public static final int WIDTH = 1215;
    public static final int HEIGHT = 720;
    public static final BasicStroke OUTLINE_THICKNESS = new BasicStroke(3);
    
    private ArrayList<Brick> bricks;
    private Ball ball;
    private Paddle paddle;
    private String textToDisplay;
    
    public GameComponent(ArrayList<Brick> bricks, Ball ball, Paddle paddle, String text)
    {
        this.bricks = bricks;
        this.ball = ball;
        this.paddle = paddle;
        textToDisplay = text;
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.BLACK);
        g2.fill(background);
        
        g2.setStroke(OUTLINE_THICKNESS);
        
        for (Brick b : bricks)
        {
            Rectangle rect = b.getRect();
            g2.setColor(b.getColor());
            g2.fill(rect);
            g2.setColor(Color.BLACK);
            g2.draw(rect);
        }
        
        Ellipse2D.Double b = ball.getCircle();
        g2.setColor(Color.WHITE);
        g2.fill(b);
        
        Rectangle p = new Rectangle(paddle.getX() - (Paddle.WIDTH / 2), Paddle.Y, Paddle.WIDTH, Paddle.THICKNESS);
        g2.setColor(Color.YELLOW);
        g2.fill(p);
        
        String lives = "Lives: " + paddle.getLives();
        Font f1 = new Font("Arial Unicode MS", Font.PLAIN, 20);
        g2.setFont(f1);
        g2.setColor(Color.WHITE);
        g2.drawString(lives, 100, 675);
        
        Font f2 = new Font("Arial Unicode MS", Font.PLAIN, 100);
        g2.setFont(f2);
        
        Rectangle2D StringRect = f2.getStringBounds(textToDisplay, g2.getFontRenderContext());
        int x = (WIDTH / 2) - (int)(StringRect.getWidth() / 2);
        g2.drawString(textToDisplay, x, 550);
    }
    
    public void setNewBall(Ball b)
    {
        ball = b;
    }
    
    public void setText(String text)
    {
        textToDisplay = text;
    }
    
    public void clearText()
    {
        textToDisplay = "";
    }
}