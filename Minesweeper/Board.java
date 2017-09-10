/**
 * the board for the game
 * 
 * @author Hoelzel
 * @version 3/22/2015
 */

public class Board
{
    private Square[][] array;
    private boolean hasRevealedMine;
    
    public Board(int w, int h)
    {
        array = new Square[w][h];
        hasRevealedMine = false;
    }
    
    public void fillBoard(int numMines, int initX, int initY)
    {
        int w = array.length;
        int h = array[0].length;
        
        for (int x = Math.max(initX - 1, 0); x <= initX + 1 && x < w; x ++)
        {
            for (int y = Math.max(initY - 1, 0); y <= initY + 1 && y < h; y ++)
            {
                array[x][y] = new Square(false);
            }
        }
        
        while (numMines > 0)
        {
            int x = (int)(w * Math.random());
            int y = (int)(h * Math.random());
            
            if (array[x][y] == null) 
            {
                array[x][y] = new Square(true);
                numMines --;
            }
        }
        
        for (int x = 0; x < w; x ++)
        {
            for (int y = 0; y < h; y ++)
            {
                if (array[x][y] == null) array[x][y] = new Square(false);
                
                if (!array[x][y].hasMine())
                {
                    int counter = 0;
                    
                    for (int x2 = Math.max(x - 1, 0); x2 <= x + 1 && x2 < w; x2 ++)
                    {
                        for (int y2 = Math.max(y - 1, 0); y2 <= y + 1 && y2 < h; y2 ++)
                        {
                            if (array[x2][y2] != null && array[x2][y2].hasMine()) counter ++;
                        }
                    }
                    
                    array[x][y].setNum(counter);
                }
            }
        }
        
        checkForMine(initX, initY);
    }
    
    public String toString()
    {
        String s = "";
        
        for (int y = 0; y < array[0].length; y ++)
        {
            for (int x = 0; x < array.length; x ++)
            {
                if (array[x][y] == null) s += "-";
                else s += array[x][y].toString();
                s += " ";
            }
            s += "\n";
        }
        
        return s;
    }
    
    public void checkForMine(int x, int y)
    {
        array[x][y].setRevealed(true);
        if (array[x][y].hasMine() && !array[x][y].isFlagged()) hasRevealedMine = true;
        
        if (array[x][y].getNum() == 0)
        {
            for (int x2 = Math.max(x - 1, 0); x2 <= x + 1 && x2 < array.length; x2 ++)
            {
                for (int y2 = Math.max(y - 1, 0); y2 <= y + 1 && y2 < array[0].length; y2 ++)
                {
                    if (!(x2 == x && y2 == y) && !array[x2][y2].isRevealed()) checkForMine(x2, y2);
                }
            }
        }
    }
    
    public boolean hasRevealedMine()
    {
        return hasRevealedMine;
    }
    
    public boolean hasCleared()
    {
        for (int x = 0; x < array.length; x ++)
        {
            for (int y = 0; y < array[0].length; y ++)
            {
                if (!array[x][y].hasMine() && !array[x][y].isRevealed()) return false;
            }
        }
        
        return true;
    }
    
    public int getWidth()
    {
        return array.length;
    }
    
    public int getHeight()
    {
        return array[0].length;
    }
    
    public String getString(int x, int y)
    {
        if (array[x][y] == null) return " ";
        else return array[x][y].toString();
    }
    
    public boolean isRevealed(int x, int y)
    {
        if (array[x][y] == null) return false;
        else return array[x][y].isRevealed();
    }
    
    public void setFlag(int x, int y)
    {
        if (array[x][y] != null) array[x][y].changeFlagged();
    }
    
    public boolean isFlagged(int x, int y)
    {
        if (array[x][y] == null) return false;
        else return array[x][y].isFlagged();
    }
}
