import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {

    private final int n;
    // private int[][] grid;
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int numberOfOpenSites;
    public Percolation(int size) {
        if (size <= 0) throw new java.lang.IllegalArgumentException(
        "n " + size + " is less than or equal to 0");
        n = size;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                // grid[i][j] = 0;
                grid[i][j] = false;
        uf = new WeightedQuickUnionUF(n*n+2);
        // StdOut.println(uf);
    }                // create n-by-n grid, with all sites blocked
    

    private void connect(int row, 
                         int col,
                         int targetRow,
                         int targetCol) 
    {
        if (isOpen(targetRow, targetCol)) {
            int p = xyTo1D(row, col);
            int q = xyTo1D(targetRow, targetCol);
            // StdOut.printf("p: %d, q: %d\n", p, q);
            
            // StdOut.println(uf);
            uf.union(p, q);
        }
        
    }

    private void connectUp(int row, int col) {
        if (row == 1) {
            uf.union(xyTo1D(row, col), 0);
            return;
        }
        connect(row, col, row-1, col);

    }

    private void connectDown(int row, int col) {
        if (row == n) {
            uf.union(xyTo1D(row, col), n*n+1);
            return;
        }
        connect(row, col, row+1, col);
    }

    private void connectLeft(int row, int col) {
        if (col == 1) {
            // StdOut.printf("%d, %d is at first column\n", row, col);
            return;
        }
        connect(row, col, row, col-1);
    }

    private void connectRight(int row, int col) {
        if (col == n) {
            // StdOut.printf("%d, %d is at last column\n", row, col);
            return;
        }
        connect(row, col, row, col+1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n) throw new java.lang.IllegalArgumentException(
        "row " + row + " is not between 1 and " + n); // invalid row
        if (col < 1 || col > n) throw new java.lang.IllegalArgumentException(
        "col " + col + " is not between 1 and " + n); // invalid column
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // StdOut.printf("row: %d, col: %d\n", row, col);
        validate(row, col);
        if (isOpen(row, col)) return;
        // grid[row-1][col-1] = 1;
        grid[row-1][col-1] = true;
        numberOfOpenSites++;
        // if (row == 1) uf.union(xyTo1D(row, col), 0);
        // if (row == n) uf.union(xyTo1D(row, col), n*n);        
        connectUp(row, col);
        connectDown(row, col);
        connectLeft(row, col);
        connectRight(row, col);
        
        // StdOut.println("here");
        
    }    

    private int xyTo1D(int row, int col) {
        // StdOut.printf("n: %d\n", n);
        return (row-1)*n + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        // StdOut.printf("isOpen\n row: %d, col: %d\n", row, col);
        // StdOut.println(grid[row-1][col-1] == 1);
        validate(row, col);
        // return grid[row-1][col-1] == 1;
        return grid[row-1][col-1];
    }  

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int p = xyTo1D(row, col);
        return uf.connected(p, 0);
    }  
    
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }       // number of open sites


    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, n*n+1);
    }              

    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        // perc.open(0, 0);
        StdOut.println(perc.isOpen(-1, 5));
        
        // StdOut.println("Number of Open Sites: " + perc.numberOfOpenSites());
        // perc.open(1, 1);
        // StdOut.println("Number of Open Sites: " + perc.numberOfOpenSites());
        // StdOut.println(perc.isFull(1, 1));
        // perc.open(1, 2);
        // StdOut.println("Number of Open Sites: " + perc.numberOfOpenSites());
        // StdOut.println(perc.isFull(1, 2));

        // perc.open(2, 1);
        // perc.open(2, 2);
        // StdOut.println("Number of Open Sites: " + perc.numberOfOpenSites());
        // StdOut.println(perc.isFull(2, 1));
        // StdOut.println(perc.percolates());
        // StdOut.printf("xyTo1D(%d, %d), %d\n", 1, 1, (perc.xyTo1D(1, 1)));
        // StdOut.printf("xyTo1D(%d, %d), %d\n", 1, 2, (perc.xyTo1D(1, 2)));
        // StdOut.printf("xyTo1D(%d, %d), %d\n", 2, 1, (perc.xyTo1D(2, 1)));
        // StdOut.printf("xyTo1D(%d, %d), %d\n", 2, 2, (perc.xyTo1D(2, 2)));

    }  // test client (optional)
}