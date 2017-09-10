/**
 * A force changes the velocity of a node
 * 
 * @author Hoelzel
 * @version 12/28/2015
 */

public class Force
{
    private double xComponent;
    private double yComponent;
    
    public static final double EPSILON = 1E-4;
    
    public static final double GRAVITY = -.01;
    
    public Force(double x, double y)
    {
        xComponent = x;
        yComponent = y;
    }
    
    public double getXComponent()
    {
        return xComponent;
    }
    
    public double getYComponent()
    {
        return yComponent;
    }
    
    public void setXComponent(double x)
    {
        xComponent = x;
    }
    
    public void setYComponent(double y)
    {
        yComponent = y;
    }
    
    public void setByAngle(double magnitude, double angle)
    {
        xComponent = magnitude * Math.cos(angle);
        yComponent = magnitude * Math.sin(angle);
    }
    
    public boolean equals(Force other)
    {
        return (Math.abs(xComponent - other.xComponent) < EPSILON && Math.abs(yComponent - other.yComponent) < EPSILON);
    }
}
