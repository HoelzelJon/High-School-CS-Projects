/**
 * One square in the board. either has a mine or a number
 * 
 * @author Hoelzel
 * @version 3/12/2015
 */

public class Square
{
    /**
     * -1 : has a mine
     * 0-8 : number of mines surrounding this point
     * 9 : number has not been set, but doesn't have a mine
     */
    private int n;
    private boolean isRevealed;
    private boolean isFlagged;
    
    public Square(boolean hasMine)
    {
        if (hasMine) n = -1;
        else n = 9;
        isRevealed = false;
        isFlagged = false;
    }
    
    public boolean hasMine()
    {
        return (n == -1);
    }
    
    public void setNum(int n)
    {
        this.n = n;
    }
    
    public void setRevealed(boolean r)
    {
        if (!isFlagged) isRevealed = r;
    }
    
    public boolean isRevealed()
    {
        return isRevealed;
    }
    
    public String toString()
    {
        if (isFlagged && !isRevealed) return "F";
        else if (!isRevealed || n == 0) return " ";
        else if (n == -1) return "\u2605";
        else return "" + n;
    }
    
    public int getNum()
    {
        return n;
    }
    
    public void changeFlagged()
    {
        if (!isRevealed) isFlagged = !isFlagged;
    }
    
    public boolean isFlagged()
    {
        return isFlagged;
    }
}
