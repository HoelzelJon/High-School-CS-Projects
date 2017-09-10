import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Asks the user to play single or 2-player
 * 
 * @author Hoelzel
 * @version 10/9/2015
 */

public class MainMenuFrame2 extends JFrame
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
    
    public MainMenuFrame2()
    {
        super("Chess");
        
        optionLabel = new JLabel("Select a level:");
        add(optionLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(1, 3));
        button1 = new JButton("Easy");
        listener1 = new ButtonListener();
        button1.addActionListener(listener1);
        panel.add(button1);
        button2 = new JButton("Medium");
        listener2 = new ButtonListener();
        button2.addActionListener(listener2);
        panel.add(button2);
        button3 = new JButton("Hard");
        listener3 = new ButtonListener();
        button3.addActionListener(listener3);
        panel.add(button3);
        add(panel, BorderLayout.CENTER);
        
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public int getLevel()
    {
        if (listener1.hasBeenPressed()) return 2;
        else if (listener2.hasBeenPressed()) return 3;
        else if (listener3.hasBeenPressed()) return 4;
        else return 0;
    }
    
    public void close()
    {
        setVisible(false);
    }
}