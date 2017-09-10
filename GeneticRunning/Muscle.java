/**
 * A Muscle connects two nodes and has a compressed distance and a relaxed distance
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */

public class Muscle
{
    private static final double STRENGTH_CONSTANT = .0000001;
    
    private static final double CLOSE_ENOUGH_BASE = 5;
    private static double CLOSE_ENOUGH = 5;
    
    private Node n1;
    private Force f1;
    private Node n2;
    private Force f2;
    private double distance1;
    private double distance2;
    private int switchTime; // time when it switches from d2 to d1
    private int currentTime;
    private int time;
    private double strength;
    
    public Muscle(Node node1, Node node2, double d1, double d2, int t, int switchTime, double strength) // t = time (in frames) of one cycle of the creature
    {
        n1 = node1;
        f1 = new Force(0, 0);
        
        n2 = node2;
        f2 = new Force(0, 0);
        
        distance1 = d1;
        distance2 = d2;
        
        n1.addMuscleForce(f1);
        n2.addMuscleForce(f2);
        
        time = t;
        currentTime = 0;
        this.strength = strength;
        this.switchTime = switchTime;
        
        adjustForces();
    }
    
    public static void randomizePhysics(){
        CLOSE_ENOUGH = CLOSE_ENOUGH_BASE + ((Math.random() - .5)* .01);
    }
    
    public Node getN1()
    {
        return n1;
    }
    
    public Node getN2()
    {
        return n2;
    }
    
    public void setCurrentTime(int t)
    {
        currentTime = t;
    }
    
    public double getNodeDistance()
    {
        return Math.sqrt(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2));
    }
    
    public void adjustForces()
    {
        double distance = getNodeDistance();
        
        double difference;
        if (currentTime > switchTime) difference = distance - distance1;
        else difference = distance - distance2;
        
        double xForce1 = (n2.getX() - n1.getX()) * difference * strength * STRENGTH_CONSTANT;
        double xForce2 = - xForce1;
        double yForce1 = (n2.getY() - n1.getY()) * difference * strength * STRENGTH_CONSTANT;
        double yForce2 = - yForce1;
        
        if (Math.abs(difference) < CLOSE_ENOUGH)
        {            
            double angle = Math.atan((n1.getY() - n2.getY()) / (n1.getX() - n2.getX()));
            if (n2.getX() > n1.getX()) angle += Math.PI;
            
            double speed1 = n1.getSpeedInDirection(angle);
            double speed2 = n2.getSpeedInDirection(angle);
            
            double avgVel = (speed1 + speed2) / 2;
            double avgDifferenceFromVel = (Math.abs(speed2 - avgVel) + Math.abs(speed1 - avgVel)) / 2;
            
            if (speed1 - avgVel > 0)
            {
                xForce1 = (-avgDifferenceFromVel) * ((n1.getX() - n2.getX()) / distance);
                yForce1 = (-avgDifferenceFromVel) * ((n1.getY() - n2.getY()) / distance);
                xForce2 = (-avgDifferenceFromVel) * ((n2.getX() - n1.getX()) / distance);
                yForce2 = (-avgDifferenceFromVel) * ((n2.getY() - n1.getY()) / distance);
            }
            else 
            {
                xForce1 = (avgDifferenceFromVel) * ((n1.getX() - n2.getX()) / distance);
                yForce1 = (avgDifferenceFromVel) * ((n1.getY() - n2.getY()) / distance);
                xForce2 = (avgDifferenceFromVel) * ((n2.getX() - n1.getX()) / distance);
                yForce2 = (avgDifferenceFromVel) * ((n2.getY() - n1.getY()) / distance);
            }
        }
        else if (n1.isTouchingGround() && !n2.isTouchingGround() && difference < 0)
        {
            double yChange = (- xForce1) * Math.abs((n2.getX() - n1.getX()) / distance);
            double xChange = (- xForce1 * n1.getFrictionCoefficient()) * Math.abs((n2.getY() - n1.getY()) / distance);
            
            yForce2 += yChange;
            xForce2 += xChange;
        }
        else if (n2.isTouchingGround() && !n1.isTouchingGround() && difference < 0)
        {
            double yChange = (- xForce2) * Math.abs((n1.getX() - n2.getX()) / distance);
            double xChange = (- xForce2 * n2.getFrictionCoefficient()) * Math.abs((n1.getY() - n2.getY()) / distance);
            
            yForce1 += yChange;
            xForce1 += xChange;
        }
        
        
        f1.setXComponent(xForce1);
        f2.setXComponent(xForce2);
        f1.setYComponent(yForce1);
        f2.setYComponent(yForce2);
    }
    
    public void reset()
    {
        f1.setXComponent(0);
        f2.setXComponent(0);
        f1.setYComponent(0);
        f2.setYComponent(0);
        time = 0;
    }
    
    public boolean isCorrectLength()
    {
        if (currentTime > switchTime) return (Math.abs(getNodeDistance() - distance1) < CLOSE_ENOUGH);
        else return (Math.abs(getNodeDistance() - distance2) < CLOSE_ENOUGH);
    }
    
    public Muscle getMutatedClone(int newTime, Node newNode1, Node newNode2, double mutationChance)
    {
        double newD2 = distance2;
        
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newD2 += 2 * Math.random();
                if (newD2 > SimpleEvolutionTester.MAX_MUSCLE_LENGTH) newD2 = SimpleEvolutionTester.MAX_MUSCLE_LENGTH;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newD2 -= 2 * Math.random();
                if (newD2 < SimpleEvolutionTester.MIN_MUSCLE_LENGTH) newD2 = SimpleEvolutionTester.MIN_MUSCLE_LENGTH;
            }
        }
        
        double newStrength = strength;
        
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newStrength += Math.random();
                if (newStrength > SimpleEvolutionTester.MAX_STRENGTH) newStrength = SimpleEvolutionTester.MAX_STRENGTH;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newStrength -= Math.random();
                if (newStrength < SimpleEvolutionTester.MIN_STRENGTH) newStrength = SimpleEvolutionTester.MIN_STRENGTH;
            }
        }
        
        int newSwitchTime = switchTime;
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newSwitchTime += 1;
                if (newSwitchTime > newTime) newSwitchTime = newTime;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newSwitchTime -= 1;
                if (newSwitchTime < 0) newSwitchTime = 0;
            }
        }
        
        Muscle m = new Muscle(newNode1, newNode2, newNode1.getDistanceFrom(newNode2), newD2, newTime, newSwitchTime, newStrength);
        return m;
    }
    
    public boolean contains(Node n)
    {
        return (n.equals(n1) || n.equals(n2));
    }
    
    public double getStrength()
    {
        return strength;
    }
    
    public double getD1()
    {
        return distance1;
    }
    
    public double getD2()
    {
        return distance2;
    }
    
    public int getSwitchTime()
    {
        return switchTime;
    }
}
