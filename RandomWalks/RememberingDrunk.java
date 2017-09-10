import java.util.ArrayList;
import java.awt.Color;

/**
 * A wandering point that keeps a record pf all prevoius points traveled (only in 2 dimentions
 * 
 * @author Hoelzel
 * @version 11/21/2015
 */
public class RememberingDrunk extends Drunk
{
    ArrayList<int[]> pastPositions;
    
    public RememberingDrunk(Color c)
    {
        super(2, c);
        pastPositions = new ArrayList<int[]>();
    }
    
    @Override
    public void wander()
    {
        pastPositions.add(getPos());
        super.wander();
    }
    
    public ArrayList<int[]> getAllPositions()
    {
        return pastPositions;
    }
    
    /**
     * ceck for and remove duplicates in the last x points
     */
    public void removeDoubles(int x)
    {
        int min = pastPositions.size() - (x + 1);
        for (int i = pastPositions.size() - 1; (i >= 0 && i >= min); i --)
        {
            int[] pos = pastPositions.get(i);
            for (int j = i - 1; j >= 0; j --)
            {
                int[] pos2 = pastPositions.get(j);
                if (pos[0] == pos2[0] && pos[1] == pos2[1])
                {
                    pastPositions.remove(i);
                    break;
                }
            }
        }
    }
}
