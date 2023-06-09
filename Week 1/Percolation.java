import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int dim;
    private int numberOfOpenSites;

    public Percolation(int n)
    {
        dim = n;
        if(n <= 0)
            throw new IllegalArgumentException();
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n*n+2);
        numberOfOpenSites = 0;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                grid[i][j] = false;
    }

    public void open(int row, int col)
    {
        row--;
        col--;
        if(row < 0 || col < 0 || row >= dim || col >= dim)
            throw new IllegalArgumentException();
        if(grid[row][col])
            return;
        numberOfOpenSites++;
        grid[row][col] = true;
        if(row == 0)
            uf.union(col+1, 0);
        if(row == dim-1)
            uf.union(dim*row+col+1, dim*dim+1); 
        if(row!=0)
            if(grid[row-1][col])
                uf.union(dim*row+col+1, dim*(row-1)+col+1);
        if(row!=dim-1)
            if(grid[row+1][col])
                uf.union(dim*row+col+1, dim*(row+1)+col+1);
        if(col!=0)
            if(grid[row][col-1])
                uf.union(dim*row+col+1, dim*row+col);
        if(col!=dim-1)
            if(grid[row][col+1])
                uf.union(dim*row+col+1, dim*row+col+2);
    }

    public boolean isOpen(int row, int col)
    {
        row--;
        col--;
        if(row < 0 || col < 0 || row >= dim || col >= dim)
            throw new IllegalArgumentException();
        return grid[row][col];
    }

    public boolean isFull(int row, int col)
    {
        row--;
        col--;
        if(row < 0 || col < 0 || row >= dim || col >= dim)
            throw new IllegalArgumentException();
        return uf.find(dim*row+col+1) == uf.find(0);
    }

    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    public boolean percolates()
    {
        return uf.find(0) == uf.find(dim*dim+1);
    }
}
