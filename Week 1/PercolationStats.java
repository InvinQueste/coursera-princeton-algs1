
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;


public class PercolationStats
{
    private int n, trials;
    private double[] values;
    
    public PercolationStats(int nf, int trialsf)
    {
        n = nf;
        trials = trialsf;
        if(nf<=0 || trials<=0)
            throw new IllegalArgumentException();
        values = new double[trials];
        for(int i = 0; i < trials; i++)
        {
            Percolation percolator = new Percolation(n);
            while(!percolator.percolates())
            {
                percolator.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
            }
            values[i] = (double)percolator.numberOfOpenSites()/(n*n);
        }
    }
    
    public double mean()
    {
        return StdStats.mean(values);
    }
    
    public double stddev()
    {
        return StdStats.stddev(values);
    }
    
    public double confidenceLo()
    {
        return mean() - (1.96*stddev()/Math.sqrt(trials));
    }
    
    public double confidenceHi()
    {
        return mean() + (1.96*stddev()/Math.sqrt(trials));
    }
    
    public static void main(String[] args)
    {
        int nf = Integer.parseInt(args[0]);
        int tf = Integer.parseInt(args[1]);
        PercolationStats obj = new PercolationStats(nf, tf);
        System.out.println("mean\t\t\t = "+obj.mean());
        System.out.println("stddev\t\t\t = "+obj.stddev());
        System.out.println("95% confidence interval\t = ["+obj.confidenceLo()+", "+obj.confidenceHi()+"]");
    }
}
