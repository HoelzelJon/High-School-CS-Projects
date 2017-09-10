import javax.swing.JFrame;
import java.util.ArrayList;

/**
 * A tester for the basic physics engine
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */

public class Tester
{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node1 = new Node(56, 100, 1);
        nodes.add(node1);
        Node node2 = new Node(79, 200, 0);
        nodes.add(node2);
        Node node3 = new Node(-44, 300, 0);
        nodes.add(node3);
        //Node node4 = new Node(-91, 110, .4);
        //nodes.add(node4);
        
        int time = 400;
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        muscles.add(new Muscle(node1, node2, 300, 200, time, time / 2, 15));
        muscles.add(new Muscle(node1, node3, 300, 200, time, time / 2, 15));
        muscles.add(new Muscle(node2, node3, 300, 200, time, time / 2, 15));
        //muscles.add(new Muscle(node3, node4, 300, 200, time, time / 2, 10));
        //muscles.add(new Muscle(node1, node4, 300, 200, time, time / 2, 10));
        
        Creature c = new Creature(time, nodes, muscles, "");
        
        DrawComponent comp = new DrawComponent();
        comp.setCreature(c);
        frame.add(comp);
        frame.setVisible(true);
        
        TrialRunner t = new TrialRunner();
        t.setCreature(c);
        t.setDrawer(comp, frame);
        
        c.reset();
        double x2 = t.runTrial(true, 3);
    }
}
