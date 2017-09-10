import java.util.ArrayList;
import javax.swing.JFrame;

/**Simulate evolution with a specific set of nodes and muscles
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */
public class FinalSimulator
{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    
    public static final int X_BOUND = SimpleEvolutionTester.X_BOUND;
    public static final int Y_BOUND = SimpleEvolutionTester.Y_BOUND;
    public static final int MAX_MUSCLE_LENGTH = SimpleEvolutionTester.MAX_MUSCLE_LENGTH;
    public static final int MIN_MUSCLE_LENGTH = SimpleEvolutionTester.MIN_MUSCLE_LENGTH;
    public static final int MIN_STRENGTH = SimpleEvolutionTester.MIN_STRENGTH;
    public static final int MAX_STRENGTH = SimpleEvolutionTester.MAX_STRENGTH;
    public static final int LOW_TIME_BOUND = SimpleEvolutionTester.LOW_TIME_BOUND;
    public static final int HIGH_TIME_BOUND = SimpleEvolutionTester.HIGH_TIME_BOUND;
    public static final double MUTATION_CHANCE = SimpleEvolutionTester.MUTATION_CHANCE;
    
    public static void main(String[] args)
    {
        boolean done = false;
        int population = 1000;
        
        int generations = 1000;
        
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Evolution Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawComponent comp = new DrawComponent();
        frame.add(comp);
        
        Creature[] creatures = new Creature[population];
        
        for (int i = 0; i < population; i ++)
        {
            creatures[i] = createRandomCreature4(i);
        }
        
        TrialRunner t = new TrialRunner();
        t.setDrawer(comp, frame);
        int bestFit = -100000;
        
        for (int gen = 1; gen <= generations; gen ++)
        {
            for (int i = 0; i < population; i ++)
            {
                Creature c = creatures[i];
                if (!c.hasFitness())
                {
                    c.reset();
                    t.setCreature(c);
                    double f = t.runTrial(false, 5);
                    if (Double.isNaN(f))
                    {
                        c.setInvalid(true);
                    }
                    c.setFitness(f);
                }
            }
            
            for (int i = 0; i < population; i ++)
            {
                Creature c = creatures[i];
                
                if (i > 0 && c != null && !c.isInvalid())
                {
                    int j = i;
                    while (j > 0 && creatures[j - 1].getFitness() < c.getFitness())
                    {
                        creatures[j] = creatures[j - 1];
                        j --;
                    }
                    creatures[j] = c;
                }
                else if (c != null && c.isInvalid())
                {
                    for (int j = i; j < population - 1; j ++)
                    {
                        creatures[j] = creatures[j + 1];
                    }
                    creatures[population - 1] = null;
                    i --;
                }
            }
            
            comp.setText("Generation " + gen);
            int newBestFit = (int)creatures[0].getFitness();
            
            if (newBestFit > bestFit)
            {
                bestFit = newBestFit;
                Creature c = creatures[0];
                comp.setCreature(c);
                t.setCreature(c);
                c.reset();
                t.runTrial(true, 5);
                comp.setRecord(newBestFit);
            }
            else frame.repaint();
               
            
            if (gen < generations)
            {
                for (int i = 0; i < population / 2; i ++)
                {
                    creatures[i + (population / 2)] = creatures[i].getMutatedClone(MUTATION_CHANCE, gen);
                }
                
                for (int i = 0; i < population; i ++)
                {
                    if (creatures[i] == null)
                    {
                        creatures[i] = createRandomCreature4(0);
                    }
                }
            }
        }
    }
    
    public static Creature createRandomCreature4(int creatureNum)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        double x1 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x2 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x3 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x4 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double avg = (x1 + x2 + x3 + x4) / 4;
        x1 -= avg;
        x2 -= avg;
        x3 -= avg;
        x4 -= avg;
        
        Node node1 = new Node(x1, 0, Math.random());
        nodes.add(node1);
        Node node2 = new Node(x2, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node2);
        Node node3 = new Node(x3, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node3);
        Node node4 = new Node(x4, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node4);
        
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node1, node3, node1.getDistanceFrom(node3), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node2, node3, node2.getDistanceFrom(node3), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node1, node4, node1.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node2, node4, node2.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node3, node4, node3.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        
        return new Creature(time, nodes, muscles, creatureNum + "");
    }
}