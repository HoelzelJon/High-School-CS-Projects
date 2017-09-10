
/**
 * An abstract superclass for computer player objects
 * 
 * @author Hoelzel
 * @version 3/27/2015
 */

public interface ComputerPlayer
{
    public boolean willJump();
    public boolean willJump(int x, int y);
    public Move getNextMove();
    public Move getNextMove(int x, int y);
}
