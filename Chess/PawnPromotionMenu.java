import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Asks the user what to promote their pawn to
 * 
 * @author Hoelzel
 * @version 10/9/2015
 */

public class PawnPromotionMenu extends JFrame
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
    public static final int FRAME_WIDTH = 500;
    
    private Team t;
    private JLabel optionLabel;
    private JButton button1;
    private ButtonListener listener1;
    private JButton button2;
    private ButtonListener listener2;
    private JButton button3;
    private ButtonListener listener3;
    private JButton button4;
    private ButtonListener listener4;
    
    public PawnPromotionMenu(Team t)
    {
        super("Chess");
        
        optionLabel = new JLabel("Select a piece to promote pawn to:");
        add(optionLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(1, 4));
        button1 = new JButton("Queen");
        listener1 = new ButtonListener();
        button1.addActionListener(listener1);
        panel.add(button1);
        button2 = new JButton("Knight");
        listener2 = new ButtonListener();
        button2.addActionListener(listener2);
        panel.add(button2);
        button3 = new JButton("Bishop");
        listener3 = new ButtonListener();
        button3.addActionListener(listener3);
        panel.add(button3);
        button4 = new JButton("Rook");
        listener4 = new ButtonListener();
        button4.addActionListener(listener4);
        panel.add(button4);
        add(panel, BorderLayout.CENTER);
        
        this.t = t;
        
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public Piece getPiece()
    {
        if (listener1.hasBeenPressed()) return new Queen(t);
        else if (listener2.hasBeenPressed()) return new Knight(t);
        else if (listener3.hasBeenPressed()) return new Bishop(t);
        else if (listener4.hasBeenPressed()) return new Rook(t);
        else return new BlankSpace();
    }
    
    public void close()
    {
        setVisible(false);
    }
}