import java.util.ArrayList;

/**
 * A Node - point where muscles connect
 * just a circle with a position for now
 * 
 * @author Hoelzel 
 * @version 8/20/2016
 */

public class Node
{
    public static final int RADIUS = 20;
    
    public static final double EPSILON = 1E-3;
    
    private double x; //at center of circle
    private double initialX;
    private double y; //center of circle
    private double initialY;
    private double xVel;
    private double yVel;
    private Force gravity;
    private ArrayList<Force> muscleForces;
    private double frictionCoefficient;
    
    public Node(double x, double y, double friction) // (x, y) are the center of the circle
    {
        this.x = x;
        initialX = x;
        this.y = y;
        initialY = y;
        xVel = 0;
        yVel = 0;
        gravity = new Force(0, Force.GRAVITY);
        muscleForces = new ArrayList<Force>();
        frictionCoefficient = friction;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getXVel()
    {
        return xVel;
    }
    
    public double getYVel()
    {
        return yVel;
    }
    
    public void move()
    {
        x += xVel;
        y += yVel;
        if (y < RADIUS) y = RADIUS;
    }
    
    public void accelerate()
    {
        double yVelChange = 0;
        double xVelChange = 0;
        
        yVelChange += gravity.getYComponent();
        xVelChange += gravity.getXComponent();  //Should do nothing
        
        for (Force f : muscleForces)
        {
            yVelChange += f.getYComponent();
            xVelChange += f.getXComponent();
        }
        
        Force norm = getNormalForce();
        
        yVelChange += norm.getYComponent();
        xVelChange += norm.getXComponent();
        
        Force friction = getFriction();
        
        yVelChange += friction.getYComponent();
        xVelChange += friction.getXComponent();
        
        yVel += yVelChange;
        xVel += xVelChange;
    }
    
    public Force getNormalForce()
    {
        if (!isTouchingGround()) return new Force(0, 0); // if not touching ground, no normal force
        else
        {
            double sumOfYForces = 0;
            sumOfYForces += gravity.getYComponent();
            
            for (Force f : muscleForces)
            {
                sumOfYForces += f.getYComponent();
            }
            
            if (sumOfYForces < 0) return new Force(0, - sumOfYForces - yVel);
            else return new Force(0, 0);
        }
    }
    
    public Force getFriction()
    {
        Force normalForce = getNormalForce();
        double maxForce = Math.abs(normalForce.getYComponent() * frictionCoefficient);
        if (Math.abs(xVel) > maxForce)
        {
            if (xVel > 0) return new Force(- maxForce, 0);
            else return new Force(maxForce, 0);
        }
        else return new Force(- xVel, 0);
    }
    
    public void addMuscleForce(Force f)
    {
        muscleForces.add(f);
    }
    
    public boolean isTouchingGround()
    {
        return Math.abs(y - RADIUS) < EPSILON;
    }
    
    public double getDistanceFrom(Node other)
    {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
    
    public double getSpeedInDirection(double angle) // angle is in radians measured counterclockwise from right
    {
        double velocity = Math.sqrt((xVel * xVel) + (yVel * yVel));
        double velAngle = Math.atan(yVel / xVel);
        if (xVel < 0) velAngle += Math.PI;
        double diffAngle = Math.abs(angle - velAngle);
        
        return Math.cos(diffAngle) * velocity;
    }
    
    public void reset()
    {
        x = initialX;
        y = initialY;
        xVel = 0;
        yVel = 0;
    }
    
    public double getFrictionCoefficient()
    {
        return frictionCoefficient;
    }
    
    public Node getMutatedClone(double mutationChance)
    {
        double newFriction = frictionCoefficient;
        
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newFriction += .02 * Math.random();
                if (newFriction > 1) newFriction = 1;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newFriction -= .02 * Math.random();
                if (newFriction < 0) newFriction = 0;
            }
        }
        
        double newX = initialX;
        
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newX += 2 * Math.random();
                if (newX > SimpleEvolutionTester.X_BOUND) newX = SimpleEvolutionTester.X_BOUND;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newX -= 2 * Math.random();
                if (newX < - SimpleEvolutionTester.X_BOUND) newX = - SimpleEvolutionTester.X_BOUND;
            }
        }
        
        double newY = initialY;
        
        if (Math.random() < .5)
        {
            while (Math.random() < mutationChance)
            {
                newY += 2 * Math.random();
                if (newY > SimpleEvolutionTester.Y_BOUND) newY = SimpleEvolutionTester.Y_BOUND;
            }
        }
        else
        {
            while (Math.random() < mutationChance)
            {
                newY -= 2 * Math.random();
                if (newY < RADIUS) newY = RADIUS;
            }
        }
        
        return new Node(newX, newY, newFriction);
    }
    
    public boolean equals(Node other)
    {
        if (Math.abs(x - other.x) > EPSILON) return false;
        else if (Math.abs(initialX - other.initialX) > EPSILON) return false;
        else if (Math.abs(y - other.y) > EPSILON) return false;
        else if (Math.abs(initialY - other.initialY) > EPSILON) return false;
        else if (Math.abs(xVel - other.xVel) > EPSILON) return false;
        else if (Math.abs(yVel - other.yVel) > EPSILON) return false;
        else if (Math.abs(frictionCoefficient - other.frictionCoefficient) > EPSILON) return false;
        
        if (muscleForces.size() != other.muscleForces.size()) return false;
        else
        {
            for (int i = 0; i < muscleForces.size(); i ++)
            {
                if (! muscleForces.get(i).equals(other.muscleForces.get(i))) return false;
            }
        }
        
        return true;
    }
    
    public void setX(double newX) // only used in the cloning methods
    {
        x = newX;
        initialX = newX;
    }
    
    public double getFrictionValue()
    {
        return frictionCoefficient;
    }
    
    public double getInitialX()
    {
        return initialX;
    }
    
    public double getInitialY()
    {
        return initialY;
    }
}
