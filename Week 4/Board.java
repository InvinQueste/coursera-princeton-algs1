import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Board
{
    private final int[][] tiles;
    private int hamming;
    private int manhattan;
    private final boolean isGoal;
    private int zeroI;
    private int zeroJ;
    
    public Board(int[][] formalTiles)
    {
        tiles = new int[formalTiles.length][formalTiles[0].length];
        for (int i = 0; i < tiles.length; i++)
             tiles[i] = Arrays.copyOf(formalTiles[i], formalTiles[i].length);
        hamming = manhattan = 0;
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                int goalValue = i * dimension() + j + 1;
                if (i == dimension()-1 && j == dimension() - 1)
                    goalValue = 0;
                if (tiles[i][j] != goalValue && tiles[i][j] != 0)
                    hamming++;
                if(tiles[i][j] == 0)
                {
                    zeroI = i;
                    zeroJ = j;
                }
                manhattan += getManhattan(i, j, tiles[i][j]);
            }
        }
        isGoal = (hamming == 0);
    }
    
    private int getManhattan(int i, int j, int slotValue)
    {
        int goalI, goalJ;
        if (slotValue == 0)
            return 0;
        else
        {
            slotValue--;
            goalI = slotValue / dimension();
            goalJ = slotValue - (goalI * dimension());
        }
        return (Math.abs(i-goalI) + Math.abs(j-goalJ));
    }
    
    public String toString()
    {
        String str = "";
        str += dimension()+"\n";
        for(int i = 0; i < dimension(); i++)
        {
            for(int j = 0; j < dimension(); j++)
                str+=tiles[i][j]+" ";
            if(i != dimension()-1)
                str+="\n";
        }
        return str;
    }
    
    public int dimension(){return tiles.length;}
    
    public int hamming(){return hamming;}
    
    public int manhattan(){return manhattan;}
    
    public boolean isGoal(){return isGoal;}
    
    public boolean equals(Object y)
    {
        if(y instanceof Board)
            return this.toString().equals(((Board) y).toString());
        return false;
    }
    
    public Iterable<Board> neighbors(){return new NeighbourIterator(this, zeroI, zeroJ, tiles);}
    
    private class NeighbourIterator implements Iterable<Board>
    {
        final Board disboard;
        final int zeroI;
        final int zeroJ;
        final int[][] tiles;
        NeighbourIterator(Board b, int f0i, int f0j, int[][] ftiles)
        {
            disboard = b;
            zeroI = f0i;
            zeroJ = f0j;
            tiles = ftiles;
        }
        
        public Iterator<Board> iterator()
        {
            Stack<Board> neighbourStack = new Stack<Board>();
            if(zeroI != 0)
                neighbourStack.push(createNB(zeroI, zeroJ, zeroI - 1, zeroJ));
            if(zeroI != disboard.dimension()-1)
                neighbourStack.push(createNB(zeroI, zeroJ, zeroI + 1, zeroJ));
            if(zeroJ != 0)
                neighbourStack.push(createNB(zeroI, zeroJ, zeroI, zeroJ - 1));
            if(zeroJ != disboard.dimension()-1)
                neighbourStack.push(createNB(zeroI, zeroJ, zeroI, zeroJ + 1));
            return neighbourStack.iterator();
        }
        
        private Board createNB(int ui, int uj, int vi, int vj)
        {
            int[][] newTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < newTiles.length; i++)
                 newTiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
            int temp = newTiles[ui][uj];
            newTiles[ui][uj] = newTiles[vi][vj];
            newTiles[vi][vj] = temp;
            return new Board(newTiles);
        }
    }
    
    public Board twin()
    {
        int[][] twinTiles = new int[dimension()][dimension()];
        for (int i = 0; i < twinTiles.length; i++)
             twinTiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        if(twinTiles[0][0] != 0 && twinTiles[0][1] != 0)
        {
            int temp = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[0][1];
            twinTiles[0][1] = temp;
        }
        else
        {
            int temp = twinTiles[1][0];
            twinTiles[1][0] = twinTiles[1][1];
            twinTiles[1][1] = temp;
        }
        return new Board(twinTiles);
    }
}