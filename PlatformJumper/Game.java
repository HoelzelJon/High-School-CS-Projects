import javax.swing.JFrame;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Rectangle;

/**
 * The game (the main method)
 * 
 * @author Hoelzel
 * @version 7/13/2015
 */

public class Game
{
    public static void main(String[] args)
    {
        final int LEFT = 5;
        final int RIGHT = 6;
        final int NOT_MOVING = 7;
        
        class MovementListener implements MouseMotionListener
        {
            private int lastX;
            
            public MovementListener()
            {
                lastX = GameComponent.WIDTH / 2;
            }
            
            public void mouseDragged(MouseEvent e){ };
            
            public void mouseMoved(MouseEvent e)
            {
                lastX = e.getX();
            }
            
            public int getX()
            {
                return lastX;
            }
        }
        
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        platforms.add(new Platform(600, -300)); // set starting platform(s)
        platforms.add(new Platform(500, 0));
        Player player = new Player(600);
        
        JFrame frame = new JFrame();
        frame.setSize(GameComponent.WIDTH, GameComponent.HEIGHT);
        frame.setTitle("Platform Jumper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GameComponent myProject = new GameComponent(platforms, player);
        MovementListener listener = new MovementListener();
        myProject.addMouseMotionListener(listener);
        frame.add(myProject);
        frame.setVisible(true);
        
        boolean done = false;
        long targetTime = 3;
        int nextHeight = 300;
        int counter = 999;
        
        
        while (!done)
        {
            long start = System.nanoTime();
            frame.repaint();
            
            if (counter > 0)
            {
                if (counter % 333 == 0 && counter != 0) myProject.setText("" + counter / 333);
                counter --;
                if (counter == 0) myProject.clearText();
            }
            else
            {
                // do stuff here
                player.move();
                player.incrementYVel();
                player.setX(listener.getX() - (Player.WIDTH / 2));
                
                if (player.getY() > (myProject.getScreenHeight() + GameComponent.HEIGHT - 250)) myProject.setScreenHeight((int)(player.getY() + 250 - GameComponent.HEIGHT));
                else if (player.getY() < (myProject.getScreenHeight()))
                {
                    done = true;
                    myProject.setText("You lose :(");
                }
                
                if (GameComponent.HEIGHT + myProject.getScreenHeight() > platforms.get(platforms.size() - 1).getY() + nextHeight) 
                {
                    int random = (int)(Math.random() * 5);
                    if (random == 0)
                    {
                        double speed = ((Math.random() * Math.sqrt(player.getY() + 100)) + 5) / 50;
                        platforms.add(new MovingPlatform((Math.random() * (GameComponent.WIDTH - Platform.WIDTH)), GameComponent.HEIGHT + myProject.getScreenHeight(), speed));
                    }
                    else platforms.add(new Platform((Math.random() * (GameComponent.WIDTH - Platform.WIDTH)), GameComponent.HEIGHT + myProject.getScreenHeight()));
                    nextHeight = (int)((Math.random() * 200) + 150);
                }
                
                for (int i = platforms.size() - 1; i >= 0; i --)
                {
                    Platform p = platforms.get(i);
                    p.move();
                    
                    if (player.getY() - Player.HEIGHT < p.getY() && player.getLastY() - Player.HEIGHT > p.getY() && player.getX() < (p.getX() + Platform.WIDTH) && (player.getX() + Player.WIDTH) > (p.getX()) && player.getYVel() < 0) player.setYVel(4);
                    
                    if (p.getY() < myProject.getScreenHeight()) platforms.remove(i);
                }
                // do stuff here
            }
            
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