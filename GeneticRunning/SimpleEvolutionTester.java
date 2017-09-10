import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/**Simulate evolution with a specific set of nodes and muscles
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */
public class SimpleEvolutionTester
{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    
    public static final int X_BOUND = 200;
    public static final int Y_BOUND = 2 * X_BOUND;
    public static final int MAX_MUSCLE_LENGTH = 300;//400
    public static final int MIN_MUSCLE_LENGTH = 50;
    public static final int MIN_STRENGTH = 2;
    public static final int MAX_STRENGTH = 13; //15;
    public static final int LOW_TIME_BOUND = 50;
    public static final int HIGH_TIME_BOUND = 1000;
    public static final double MUTATION_CHANCE = .1;
    
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        boolean done = false;
        
        int length = 0;
        while (!done)
        {
            System.out.print("Length: ");
            if (in.hasNextInt()) 
            {
                length = in.nextInt();
                if (length > 0) done = true;
            }
            in.nextLine();
        }
        
        done = false;
        int population = 0;
        while (!done)
        {
            System.out.print("Population size: ");
            if (in.hasNextInt()) 
            {
                population = in.nextInt();
                if (population > 0) done = true;
            }
            in.nextLine();
        }
        
        done = false;
        int generations = 0;
        while (!done)
        {
            System.out.print("Number of generations: ");
            if (in.hasNextInt()) 
            {
                generations = in.nextInt();
                if (generations > 0) done = true;
            }
            in.nextLine();
        }
        
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Simulation");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        DrawComponent comp = new DrawComponent();
        frame.add(comp);
        
        Creature[] creatures = createCustomCreatures(in, length, population);//new Creature[population];//createCustomCreatures(in, length, population);
        
        /**for (int i = 0; i < population; i ++)
        {
            creatures[i] = createRandomWorm(length, i);
        }*/
        
        
        Trial t = new TrialRunner();
        t.setDrawer(comp, frame);
        int bestFit = -100000;
        Creature bestCreature = null;
        int invalidNum = 0;
        
        for (int gen = 1; gen <= generations; gen ++)
        {
            Muscle.randomizePhysics();
            
            for (int i = 0; i < population; i ++)
            {
                Creature c = creatures[i];
                c.reset();
                t.setCreature(c);
                double f = t.runTrial(false, 1);
                if (Double.isNaN(f))
                {
                    c.setInvalid(true);
                    invalidNum ++;
                }
                c.setFitness(f);
            }
            
            /**for (Creature c : creatures)
            {
                if (c.isInvalid())
                {
                    comp.setCreature(c);
                    t.setCreature(c);
                    c.reset();
                    t.runTrial(false, 1);
                }
            }*/
            
            double sum = 0;
            for (int i = 0; i < population; i ++) // sorts the creatures
            {
                Creature c = creatures[i];
                
                if (i > 0 && c != null && !c.isInvalid())
                {
                    sum += c.getFitness();
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
                else if (i == 0)
                {
                    sum += c.getFitness();
                }
            }
            
            System.out.println();
            System.out.println("Gen: " + gen + " / " + generations);
            int newBestFit = (int)creatures[0].getFitness();
            System.out.println("Best: " + newBestFit);
            System.out.println("Heritage: " + creatures[0].getHeritage());
            System.out.println("Median: " + (int)creatures[(population - invalidNum) / 2].getFitness());
            System.out.println("Mean: " + (int)(sum / population));
            System.out.println("Invalid: " + invalidNum);
            
            if (creatures[0] != bestCreature)
            {
                bestFit = newBestFit;
                bestCreature = creatures[0];
                Creature c = creatures[0];
                comp.setCreature(c);
                t.setCreature(c);
                c.reset();
                t.runTrial(true, 3);
                comp.setRecord(newBestFit);
            }
            //else frame.repaint();
            
            //for (int i = 0; i < population; i ++)
            //{
            //    if (creatures[i] != null) System.out.println((i + 1) + " : " + (int)creatures[i].getFitness());
            //}
            
            //Creature c = creatures[0];
            //c.reset();
            //comp.setCreature(c);
            //t.setCreature(c);
            //t.runTrial(true);
            
            //c = creatures[population - 1];
            //c.reset();
            //comp.setCreature(c);
            //t.setCreature(c);
            //t.runTrial(true);           
            
            
            if (gen == generations)
            {
                done = false;
                while (!done)
                {
                    System.out.print("Which to view? (# 0(best) - " + (population - invalidNum - 1) + "(worst)) or \"c\" to continue:");
                    int num = -1;
                    String s = "";
                    if (in.hasNextInt())
                    {
                        num = in.nextInt();
                        in.nextLine();
                    }
                    else s = in.nextLine();
                    
                    if (num >= 0 && num < population - invalidNum)
                    {
                        boolean done2 = false;
                        int rate = -1;
                        while (!done2)
                        {
                            System.out.print("Enter frame rate (15-17?): ");
                            if (in.hasNextInt()) 
                            {
                                rate = in.nextInt();
                                if (rate > 0) done2 = true;
                            }
                            in.nextLine();
                        }
                        
                        Creature c = creatures[num];
                        c.reset();
                        comp.setCreature(c);
                        t.setCreature(c);
                        System.out.println(t.runTrial(true, rate));
                        System.out.println(c.getHeritage());
                    }
                    else if (s.equalsIgnoreCase("c"))
                    {
                        int newNum = -1;
                        boolean done3 = false;
                        
                        while (!done3)
                        {
                            System.out.print("# of additional generations: ");
                            if (in.hasNextInt()) 
                            {
                                newNum = in.nextInt();
                                if (newNum >= 0) done3 = true;
                            }
                            in.nextLine();
                        }
                        
                        generations += newNum;
                        done = true;
                    }
                }
            }
            
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
                        creatures[i] = create1NodeCreature();
                    }
                }
            }
            
            invalidNum = 0;
        }
        
        //for (int i = 0; i < population; i ++)
        //{
        //    if (creatures[i] != null) System.out.println((i + 1) + " : " + (int)creatures[i].getFitness());
        //}
    }
    
    public static Creature create1NodeCreature(){
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(0, Node.RADIUS, 1));
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        int time = LOW_TIME_BOUND;
        
        return new Creature(time, nodes, muscles, ":(");
    }
    
    public static Creature createRandomCreature5(int num)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        double x1 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x2 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x3 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x4 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double x5 = (Math.random() * 2 * X_BOUND) - X_BOUND;
        double avg = (x1 + x2 + x3 + x4 + x5) / 5;
        x1 -= avg;
        x2 -= avg;
        x3 -= avg;
        x4 -= avg;
        x5 -= avg;
        
        Node node1 = new Node(x1, 0, Math.random());
        nodes.add(node1);
        Node node2 = new Node(x2, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node2);
        Node node3 = new Node(x3, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node3);
        Node node4 = new Node(x4, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node4);
        Node node5 = new Node(x5, (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
        nodes.add(node5);
        
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node1, node3, node1.getDistanceFrom(node3), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node2, node3, node2.getDistanceFrom(node3), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node1, node4, node1.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        //muscles.add(new Muscle(node2, node4, node2.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node3, node4, node3.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        //muscles.add(new Muscle(node1, node5, node1.getDistanceFrom(node5), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node2, node5, node2.getDistanceFrom(node5), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        //muscles.add(new Muscle(node3, node5, node3.getDistanceFrom(node5), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(node4, node5, node4.getDistanceFrom(node5), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));

        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature createRandomCreature4(int num)
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
        //muscles.add(new Muscle(node3, node4, node3.getDistanceFrom(node4), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature createRandomWorm(int length, int num)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        double[] x = new double[length];
        double sum = 0;
        
        for (int i = 0; i < length; i ++)
        {
            x[i] = (Math.random() * 2 * X_BOUND) - X_BOUND;
            sum += x[i];
        }
        
        double avg = sum / length;
        for (int i = 0; i < length; i ++)
        {
            x[i] -= avg;
        }
        
        for (int i = 0; i < length; i ++)
        {
            Node n;
            if (i == 0) n = new Node(x[i], 0, Math.random());
            else n = new Node(x[i], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
            nodes.add(n);
        }
        
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        for (int i = 0; i < length - 1; i ++)
        {
            Node node1 = nodes.get(i);
            Node node2 = nodes.get(i + 1);
            muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        }
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature createRandomCircle(int length, int num)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        double[] x = new double[length];
        double sum = 0;
        
        for (int i = 0; i < length; i ++)
        {
            x[i] = (Math.random() * 2 * X_BOUND) - X_BOUND;
            sum += x[i];
        }
        
        double avg = sum / length;
        for (int i = 0; i < length; i ++)
        {
            x[i] -= avg;
        }
        
        for (int i = 0; i < length; i ++)
        {
            Node n;
            if (i == 0) n = new Node(x[i], 0, Math.random());
            else n = new Node(x[i], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random());
            nodes.add(n);
        }
        
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        for (int i = 0; i < length - 1; i ++)
        {
            Node node1 = nodes.get(i);
            Node node2 = nodes.get(i + 1);
            muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        }
        if (length > 2) muscles.add(new Muscle(nodes.get(0), nodes.get(length - 1), nodes.get(0).getDistanceFrom(nodes.get(length - 1)), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature createRandomFilledCreature(int size, int num)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(0, 0, Math.random()));
        double[] xValues = new double[size - 1];
        double sum = 0;
        
        for (int i= 1; i < size; i ++)
        {
            xValues[i - 1] = (Math.random() * 2 * X_BOUND) - X_BOUND;
            sum += xValues[i - 1];
        }
        
        for (int i = 1; i < size; i ++)
        {
            xValues[i - 1] -= (sum / (size - 1));
            nodes.add(new Node(xValues[i - 1], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random()));
        }
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        for (int i = 0; i < size - 1; i ++)
        {
            for (int j = i + 1; j < size; j ++)
            {
                muscles.add(new Muscle(nodes.get(i), nodes.get(j), nodes.get(i).getDistanceFrom(nodes.get(j)), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH)); 
            }
        }
        
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature createRandomWheel(int size, int num){
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(0, 0, Math.random()));
        double[] xValues = new double[size - 1];
        double sum = 0;
        
        for (int i= 1; i < size; i ++)
        {
            xValues[i - 1] = (Math.random() * 2 * X_BOUND) - X_BOUND;
            sum += xValues[i - 1];
        }
        
        for (int i = 1; i < size; i ++)
        {
            xValues[i - 1] -= (sum / (size - 1));
            nodes.add(new Node(xValues[i - 1], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random()));
        }
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        Node centerNode = nodes.get(size - 1);
        
        for (int i = 0; i < size - 2; i ++)
        {
            Node node1 = nodes.get(i);
            Node node2 = nodes.get(i + 1);
            muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
            muscles.add(new Muscle(node1, centerNode, node1.getDistanceFrom(centerNode), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        }
        muscles.add(new Muscle(nodes.get(0), nodes.get(size - 2), nodes.get(0).getDistanceFrom(nodes.get(size - 2)), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        muscles.add(new Muscle(nodes.get(size - 2), nodes.get(size - 1), nodes.get(size - 2).getDistanceFrom(nodes.get(size - 1)), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    // only worls right for size>=5
    public static Creature createTriangledCreature(int size, int num){
        int time = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        nodes.add(new Node(0, 0, Math.random()));
        double[] xValues = new double[size - 1];
        double sum = 0;
        
        for (int i= 1; i < size; i ++)
        {
            xValues[i - 1] = (Math.random() * 2 * X_BOUND) - X_BOUND;
            sum += xValues[i - 1];
        }
        
        for (int i = 1; i < size; i ++)
        {
            xValues[i - 1] -= (sum / (size - 1));
            nodes.add(new Node(xValues[i - 1], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random()));
        }
        
        ArrayList<Muscle> muscles = new ArrayList<Muscle>();
        for (int i = 0; i < size; i ++){
            Node node1 = nodes.get(i);
            Node node2 = nodes.get((i + 1) % size);
            Node node3 = nodes.get((i + 2) % size);
            
            muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
            muscles.add(new Muscle(node1, node3, node1.getDistanceFrom(node3), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, time, (int)(Math.random() * time), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
        }
        
        return new Creature(time, nodes, muscles, "" + num);
    }
    
    public static Creature[] createCustomCreatures(Scanner in, int length, int num){
        Creature[] creatures = new Creature[num];
        ArrayList[] nodesList = new ArrayList[num];
        ArrayList[] musclesList = new ArrayList[num];
        int[] times = new int[num];
        
        for (int n = 0; n < num; n ++){
            ArrayList<Node> nodes = new ArrayList<Node>();
            nodes.add(new Node(0, 0, Math.random()));
            double[] xValues = new double[length - 1];
            double sum = 0;
        
            for (int i= 1; i < length; i ++)
            {
                xValues[i - 1] = (Math.random() * 2 * X_BOUND) - X_BOUND;
                sum += xValues[i - 1];
            }
            
            for (int i = 1; i < length; i ++)
            {
                xValues[i - 1] -= (sum / (length - 1));
                nodes.add(new Node(xValues[i - 1], (Math.random() * Y_BOUND) + Node.RADIUS, Math.random()));
            }
            
            nodesList[n] = nodes;
            musclesList[n] = new ArrayList<Muscle>();
            times[n] = ((int)(Math.random() * (HIGH_TIME_BOUND - LOW_TIME_BOUND))) + LOW_TIME_BOUND;
        }
        
        for (int i = 0; i < length - 1; i ++){
            for (int j = i + 1; j < length; j ++){
                System.out.print("Connect node " + i + " with node " + j + "? (y for yes):");
                String input = in.nextLine();
                if (input.equalsIgnoreCase("y")){
                    for (int n = 0; n < num; n ++){
                        ArrayList<Muscle> muscles = musclesList[n];
                        ArrayList<Node> nodes = nodesList[n];
                        
                        Node node1 = nodes.get(i);
                        Node node2 = nodes.get(j);
                        
                        muscles.add(new Muscle(node1, node2, node1.getDistanceFrom(node2), (Math.random() * (MAX_MUSCLE_LENGTH - MIN_MUSCLE_LENGTH)) + MIN_MUSCLE_LENGTH, times[n], (int)(Math.random() * times[n]), (Math.random() * (MAX_STRENGTH - MIN_STRENGTH)) + MIN_STRENGTH));
                    }
                }
            }
        }
        
        for (int i = 0; i < num; i ++){
            creatures[i] = new Creature(times[i], nodesList[i], musclesList[i], "" + i);
        }
        
        return creatures;
    }
}
