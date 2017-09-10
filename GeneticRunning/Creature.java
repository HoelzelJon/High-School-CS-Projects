import java.util.ArrayList;

/**
 * Has some nodes and some muscles
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */

public class Creature
{
    private int totalTime;
    private int currentTime;
    private ArrayList<Node> nodes;
    private ArrayList<Muscle> muscles;
    private double fitness;
    /**
     * shows how the nodes and muscles are organized. necesary for mutated cloning.
     * each of the first brackets is a muscle (in order of "muscles"), and the two values for each muscle
     * indicate the position in "nodes" of the two nodes attached to the muscle 
     */
    private int[][] organization;
    private boolean isInvalid;
    private boolean hasFitness;
    private int mutations;
    private String heritage;
    
    
    public Creature(int t, ArrayList<Node> n, ArrayList<Muscle> m, String startingHeritage)
    {
        totalTime = t;
        nodes = n;
        muscles = m;
        currentTime = 0;
        isInvalid = false;
        hasFitness = false;
        mutations = 0;
        
        organization = new int[m.size()][2];
        for (int i = 0; i < m.size(); i ++)
        {
            Node n1 = muscles.get(i).getN1();
            for (int j = 0; j < nodes.size(); j ++)
            {
                if (nodes.get(j).equals(n1)) 
                {
                    organization[i][0] = j;
                    break;
                }
            }
            
            Node n2 = muscles.get(i).getN2();
            for (int j = 0; j < nodes.size(); j ++)
            {
                if (nodes.get(j).equals(n2)) 
                {
                    organization[i][1] = j;
                    break;
                }
            }
        }
        
        heritage = startingHeritage;
    }
    
    private void setMutations(int m)
    {
        mutations = m;
    }
    
    public ArrayList<Node> getNodes()
    {
        return nodes;
    }
    
    public ArrayList<Muscle> getMuscles()
    {
        return muscles;
    }
    
    public int getTotalTime()
    {
        return totalTime;
    }
    
    public void move()
    {
        for (Node n : nodes)
        {
            n.move();
            n.accelerate();
        }
        
        for (Muscle m : muscles)
        {
            m.setCurrentTime(currentTime);
            m.adjustForces();
            m.setCurrentTime(currentTime);
        }
        
        currentTime ++;
        if (currentTime >= totalTime) currentTime = 0;
    }
    
    public double getAverageX()
    {
        double sum = 0;
        
        for (Node n : nodes)
        {
            sum += n.getX();
        }
        
        return sum / nodes.size();
    }
    
    public void setFitness(double f)
    {
        fitness = f;
        hasFitness = true;
    }
    
    public double getFitness()
    {
        return fitness;
    }
    
    public void reset()
    {
        for (Muscle m : muscles)
        {
            m.reset();
        } 
        
        for (Node n : nodes)
        {
            n.reset();
        }
        
        currentTime = 0;
    }
    
    public boolean stillStanding()
    {
        for (Node n : nodes)
        {
            if (!n.isTouchingGround()) return true;
        }
        
        return false;
    }
    
    public Creature getMutatedClone(double mutationChance, int gen)
    {
        ArrayList<Node> newNodes = new ArrayList<Node>();
        
        for (int i = 0; i < nodes.size(); i ++)
        {
            newNodes.add(i, nodes.get(i).getMutatedClone(mutationChance));
        }
        
        double sumX = 0;
        for (Node n : newNodes)
        {
            sumX += n.getX();
        }
        double avgX = sumX / newNodes.size();
        
        for (Node n : newNodes)
        {
            n.setX(n.getX() - avgX);
        }
        
        int newTime = totalTime;
        if (Math.random() > 0.5)
        {
            while (Math.random() < mutationChance)
            {
                newTime ++;
                if (newTime > SimpleEvolutionTester.HIGH_TIME_BOUND) newTime = SimpleEvolutionTester.HIGH_TIME_BOUND;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newTime --;
                if (newTime < SimpleEvolutionTester.LOW_TIME_BOUND) newTime = SimpleEvolutionTester.LOW_TIME_BOUND;
            }
        }
        
        ArrayList<Muscle> newMuscles = new ArrayList<Muscle>();
        
        for (int i = 0; i < muscles.size(); i ++)
        {
            newMuscles.add(muscles.get(i).getMutatedClone(newTime, newNodes.get(organization[i][0]), newNodes.get(organization[i][1]), mutationChance));
        }
        
        Creature c = new Creature(newTime, newNodes, newMuscles, heritage + "." + gen);
        c.setMutations(mutations + 1);
        return c;
    }
    
    public boolean isInvalid()
    {
        return isInvalid;
    }
    
    public void setInvalid(boolean b)
    {
        isInvalid = b;
    }
    
    public boolean hasFitness()
    {
        return hasFitness;
    }
    
    public int getMutations()
    {
        return mutations;
    }
    
    public double getHeight()
    {
        double minHeight = -1;
        
        for (Node n : nodes)
        {
            if (n.getY() > minHeight) minHeight = n.getY();
        }
        
        return minHeight;
    }
    
    /**
     * only use this if the organizations are the same
     */
    public Creature createChild(Creature other, double mutationChance)
    {
        int newTotalTime;
        
        if (Math.random() > .5) 
        {
            newTotalTime = other.totalTime;
        }
        else 
        {
            newTotalTime = totalTime;
        }
        
        ArrayList<Node> newNodes = new ArrayList<Node>();
        
        if (nodes.size() == other.nodes.size())
        {
            for (int i = 0; i < nodes.size(); i ++)
            {
                double friction;
                
                if (Math.random() > .5) friction = nodes.get(i).getFrictionValue();
                else  friction = other.nodes.get(i).getFrictionValue();
                
                double x;
                double y;
                
                if (Math.random() > .5)
                {
                    x = nodes.get(i).getInitialX();
                    y = nodes.get(i).getInitialY();
                }
                else
                {
                    x = other.nodes.get(i).getInitialX();
                    y = other.nodes.get(i).getInitialY();
                }
                
                newNodes.add(new Node(x, y, friction));
            }
        }
        else System.out.println("Incompatible match");
        
        ArrayList<Muscle> newMuscles = new ArrayList<Muscle>();
        
        if (muscles.size() == other.muscles.size())
        {
            for (int i = 0; i < muscles.size(); i ++)
            {
                double d1;
                
                if (Math.random() > .5) d1 = muscles.get(i).getD1();
                else d1 = other.muscles.get(i).getD1();
                
                double d2;
                
                if (Math.random() > .5) d2 = muscles.get(i).getD2();
                else d2 = other.muscles.get(i).getD2();
                
                double strength;
                
                if (Math.random() > .5) strength = muscles.get(i).getStrength();
                else strength = other.muscles.get(i).getStrength();
                
                int switchTime;
                
                if (Math.random() > .5) switchTime = (muscles.get(i).getSwitchTime() * newTotalTime) / totalTime;
                else  switchTime = (other.muscles.get(i).getSwitchTime() * newTotalTime) / other.totalTime;
                
                newMuscles.add(new Muscle(newNodes.get(organization[i][0]), newNodes.get(organization[i][1]), d1, d2, newTotalTime, switchTime, strength));
            }
        }
        
        Creature c = new Creature(newTotalTime, newNodes, newMuscles, "I haven't done this yet");
        return c.getMutatedClone(SimpleEvolutionTester.MUTATION_CHANCE, 1000000);
    }
    
    public String getHeritage(){
        return heritage;
    }
}
