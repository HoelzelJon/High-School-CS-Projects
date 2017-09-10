import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Asks the user to pick a side - black, white, or random
 * 
 * @author Hoelzel
 * @version 10/9/2015
 */

public class MainMenuFrame3 extends JFrame
{
    class ButtonListener implements ActionListener
    {
        private boolean hasBeenPressed;
        
        public ButtonListener()
        {
            hasBeenPressed = false;
        }
        
        public void actionPerformed(ActionEvent event)
        {
            hasBeenPressed = true;
        }
        
        public boolean hasBeenPressed()
        {
            return hasBeenPressed;
        }
    }
    
    public static final int FRAME_HEIGHT = 200;
    public static final int FRAME_WIDTH = 400;
    
    private JLabel optionLabel;
    private JButton button1;
    private ButtonListener listener1;
    private JButton button2;
    private ButtonListener listener2;
    private JButton button3;
    private ButtonListener listener3;
    
    public MainMenuFrame3()
    {
        super("Chess");
        
        optionLabel = new JLabel("Select a color:");
        add(optionLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(1, 3));
        button1 = new JButton("Black");
        listener1 = new ButtonListener();
        button1.addActionListener(listener1);
        panel.add(button1);
        button2 = new JButton("Red");
        listener2 = new ButtonListener();
        button2.addActionListener(listener2);
        panel.add(button2);
        button3 = new JButton("Random");
        listener3 = new ButtonListener();
        button3.addActionListener(listener3);
        panel.add(button3);
        add(panel, BorderLayout.CENTER);
        
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public Team getLevel()
    {
        if (listener1.hasBeenPressed()) return Team.PLAYER1;
        else if (listener2.hasBeenPressed()) return Team.PLAYER2;
        else if (listener3.hasBeenPressed())
        {
            int rand = (int)(Math.random() * 2);
            if (rand == 0) return Team.PLAYER1;
            else return Team.PLAYER2;
        }
        else return Team.NEITHER;
    }
    
    public void close()
    {
        setVisible(false);
    }
}