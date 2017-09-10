import javax.swing.JFrame;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Rectangle;

/**
 * The game (the main method)
 * 
 * @author Hoelzel
 * @version 10/18/2015
 */

public class Game
{
    public static void main(String[] args)
    {
        class MousePositionListener implements MouseMotionListener
        {
            private int lastX;
            
            public MousePositionListener()
            {
                lastX = 500;
            }
            
            public void mouseDragged(MouseEvent e) {}
            
            public void mouseMoved(MouseEvent e)
            {
                lastX = e.getX();
            }
            
            public int getX()
            {
                return lastX;
            }
        }
        
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        
        for (int i = 0; i < 20; i ++)
        {
            for (int j = 0; j < 7; j ++)
            {
                if ((i + j) % 2 == 0)  bricks.add(new Brick(i * Brick.WIDTH, j * Brick.HEIGHT, 2));
            }
        }
        
        Ball ball = new Ball(GameComponent.WIDTH / 2, 400, 0, 1);
        Paddle paddle = new Paddle(3);
        
        JFrame frame = new JFrame();
        frame.setSize(GameComponent.WIDTH, GameComponent.HEIGHT);
        frame.setTitle("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GameComponent myProject = new GameComponent(bricks, ball, paddle, "3");
        MousePositionListener listener = new MousePositionListener();
        myProject.addMouseMotionListener(listener);
        frame.add(myProject);
        frame.setVisible(true);
        
        boolean done = false;
        boolean ballMoving = false;
        int counter = 750;
        long targetTime = 4;
        
        while (!done)
        {
            long start = System.nanoTime();
            
            paddle.moveTo(listener.getX());
            
            if (counter > 0) 
            {
                if (counter % 250 == 0 && counter != 0) myProject.setText("" + counter / 250);
                counter --;
                if (counter == 0) myProject.clearText();
            }
            else
            {
                ball.move();
                
                if (ball.getY() == Paddle.Y - Ball.DIAMETER && Math.abs(paddle.getX() - ball.getX()) <= ((Paddle.WIDTH + 20) / 2))
                {
                    ball.changeVelocity(.04 * (ball.getX() - paddle.getX()), -1);
                }
                else if (ball.getX() < 5 || ball.getX() + Ball.DIAMETER > GameComponent.WIDTH - 5)
                {
                    ball.changeVelocity(-1 * ball.getXVel(), ball.getYVel());
                }
                else if (Helper.approxEquals(ball.getY(), 0))
                {
                    ball.changeVelocity(ball.getXVel(), -1 * ball.getYVel());
                }
                else if (ball.getY() > GameComponent.HEIGHT)
                {                
                    paddle.lostLife();
                    
                    if (paddle.getLives() <= 0)
                    {
                        done = true;
                        myProject.setText("You lose :(");
                    }
                    else 
                    {
                        ball = new Ball(GameComponent.WIDTH / 2, 400, 0, 1);
                        myProject.setNewBall(ball);
                        counter = 750;
                        ballMoving = false;
                    }
                }
                            
                for (int i = bricks.size() - 1; i >= 0; i --)
                {
                    Brick b = bricks.get(i);
                    Rectangle r = b.getRect();
                    
                    if (ball.getCircle().intersects(r))
                    {
                        if ((Helper.approxEquals(ball.getY(), r.getY() + r.getHeight()) && ball.getYVel() < 0) || (Helper.approxEquals(ball.getY() + Ball.DIAMETER, r.getY()) && ball.getYVel() > 0))
                        {
                            ball.changeVelocity(ball.getXVel(), -1 * ball.getYVel());
                            
                            if (b.getHitsLeft() == 1) bricks.remove(i);
                            else b.gotHit();
                            
                            if (bricks.size() == 0) done = true;
                        }
                        else if ((Helper.approxEquals(ball.getX() + (Ball.DIAMETER / 2), r.getX()) && ball.getXVel() > 0) || (Helper.approxEquals(ball.getX() - (Ball.DIAMETER / 2), r.getX() + r.getWidth()) && ball.getXVel() < 0))
                        {
                            ball.changeVelocity(-1 * ball.getXVel(), ball.getYVel());
                            
                            if (b.getHitsLeft() == 1) bricks.remove(i);
                            else b.gotHit();
                            
                            if (bricks.size() == 0)
                            {
                                done = true;
                                myProject.setText("You win! :)");
                            }
                        }
                        
                        break;
                    }
                }
            }
            
            frame.repaint();
            
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
}
