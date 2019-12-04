import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    
    private static final double CONF = 1.96;
    private final int t;
    private final double mean;
    private final double std;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new java.lang.IllegalArgumentException(
        "n " + n + " is less than or equal to 0");
        if (trials <= 0) throw new java.lang.IllegalArgumentException(
        "trails " + trials + " is less than or equal to 0");


        t = trials;
        double[] res = new double[trials];
        for (int i = 0; i < trials; i++) {
            // StdOut.println(i);
            Percolation perc = new Percolation(n);
            // StdOut.println(perc.percolates());
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                perc.open(row, col);
            }
            res[i] = (double) perc.numberOfOpenSites() / (n*n);    
        }
        mean = StdStats.mean(res);
        std = StdStats.stddev(res);
    }   
    public double mean() {
        // StdOut.println(res);
        return mean;
    }                         // sample mean of percolation threshold
    public double stddev() {
        return std;
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean - ((CONF * std) / Math.sqrt(t));
    }                // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + ((CONF * std) / Math.sqrt(t));
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // StdOut.println("n: " + n);
        // StdOut.println("trials: " + trials);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f \n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
        // double[] a = {1.0, 2.0, 3.0};
        // StdOut.println(StdStats.mean(a));
    }       // test client (described below)
}