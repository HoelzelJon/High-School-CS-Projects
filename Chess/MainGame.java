/**
 * Makes the menu panels and starts the game
 * 
 * @author Hoelzel
 * @version 8/19/2016
 */
public class MainGame
{
    public static void main(String[] args)
    {
        MainMenuFrame1 frame1 = new MainMenuFrame1();
        
        boolean done = false;
        int numPlayers = 0;
        
        while(!done)
        {
            numPlayers = frame1.getNumPlayers();
            try
            {
                Thread.sleep(1);
            }
            catch (Exception e)
            {
                System.out.println("Something is wrong.");                    
            }
            if (numPlayers != 0) done = true;
        }
        
        frame1.close();
        
        if (numPlayers == 1)
        {
            MainMenuFrame2 frame2 = new MainMenuFrame2();
            int level = 0;
            done = false;
            
            while (!done)
            {
                level = frame2.getLevel();
                try
                {
                    Thread.sleep(1);
                }
                catch (Exception e)
                {
                    System.out.println("Something is wrong.");                    
                }
                if (level != 0) done = true;
            }
            
            frame2.close();
            
            MainMenuFrame3 frame3 = new MainMenuFrame3();
            Team t = Team.NEITHER;
            done = false;
            
            while (!done)
            {
                t = frame3.getLevel();
                try
                {
                    Thread.sleep(1);
                }
                catch (Exception e)
                {
                    System.out.println("Something is wrong.");                    
                }
                if (t != Team.NEITHER) done = true;
            }
            
            frame3.close();
            VsComputerGame.play(level, t);
        }
        else TwoPersonGame.play();
    }
}
