import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.ArrayList;

public class Board {
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int n;
    private int[][] tiles;
    
    private int zeroRow;
    private int zeroCol;
    private boolean Debug = false;
    // private int[][] twinTiles;

    public Board(int[][] blocks) {
        n = blocks.length;
        tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }

        setZero();
        // StdOut.println("zero: "+zero[0]+zero[1]);
    }          
    
    // board dimension n
    public int dimension() {
        return n;
    }

    private void setZero() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }            

    // number of blocks out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i*n+j+1) {
                    if ((i*n+j+1)!= n*n) {
                        h++;
                    }
                }
            }
        }
        return h;
    }                  

    private int getRow(int num) {
        if (num == 0) return n-1;
        return (int) (num-1) / n;
        
    }

    private int getCol(int num) {
        if (num == 0) return n-1;
        return (num-1) % n;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    // if ((i*n+j+1)!= n*n) {
                        // StdOut.print(tiles[i][j]);
                        // StdOut.print(", ");
                        // StdOut.printf("row: %d, col: %d, diff: %d\n",
                        //  getRow(tiles[i][j]),
                        //  getCol(tiles[i][j]),
                        //  Math.abs(i - getRow(tiles[i][j]))+
                        //  Math.abs(j-getCol(tiles[i][j])));
                        m = m + 
                            Math.abs(i - getRow(tiles[i][j]))+
                            Math.abs(j - getCol(tiles[i][j]));
                    // }
                    
                }
            }
        }
        if (Debug) StdOut.println();
        return m;
    }            

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }               

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        if (copy[0][0] == 0) {
            int temp = copy[0][1];
            copy[0][1] = copy[1][0];
            copy[1][0] = temp;
        } else if (copy[0][1] == 0) {
            int temp = copy[0][0];
            copy[0][0] = copy[1][0];
            copy[1][0] = temp; 
        } else {
            int temp = copy[0][0];
            copy[0][0] = copy[0][1];
            copy[0][1] = temp; 
        }
        Board b = new Board(copy);
        return b;
    }                   

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (Arrays.deepEquals(tiles, that.tiles));
    }       




    

    private Board getUpNeighbor() {
        // StdOut.println("up" + this);
        // setZero();
        // StdOut.println("zeroRow: " + zeroRow);
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        if (zeroRow == 0) return null;
        Board newBoard = new Board(copy);
        int temp = newBoard.tiles[zeroRow-1][zeroCol];
        newBoard.tiles[zeroRow-1][zeroCol] = 0;
        newBoard.tiles[zeroRow][zeroCol] = temp;
        // StdOut.println("upup" + newBoard);
        return newBoard;
    }

    private Board getDownNeighbor() {
        // StdOut.println("down" + this);

        // setZero();
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        if (zeroRow == n-1) return null;
        Board newBoard = new Board(copy);        
        int temp = newBoard.tiles[zeroRow + 1][zeroCol];
        newBoard.tiles[zeroRow + 1][zeroCol] = 0;
        newBoard.tiles[zeroRow][zeroCol] = temp;
        // StdOut.println("downdown: "+newBoard);
        return newBoard;
    }

    private Board getLeftNeighbor() {
        // setZero();
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        if (zeroCol == 0) return null;
        Board newBoard = new Board(copy);        
        int temp = newBoard.tiles[zeroRow][zeroCol-1];
        newBoard.tiles[zeroRow][zeroCol-1] = 0;
        newBoard.tiles[zeroRow][zeroCol] = temp;
        return newBoard;
    }

    private Board getRightNeighbor() {
        // setZero();
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        if (zeroCol == n-1) return null;
        Board newBoard = new Board(copy);        
        int temp = newBoard.tiles[zeroRow][zeroCol+1];
        newBoard.tiles[zeroRow][zeroCol+1] = 0;
        newBoard.tiles[zeroRow][zeroCol] = temp;
        return newBoard;
    }

    private void addNeighbor(ArrayList<Board> neighbors, Board b) {
        if (b != null) neighbors.add(b);
        
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        
        setZero();
        // if (zeroRow == n-1 && zeroCol == n-1) return null;
        ArrayList<Board> neighbors = new ArrayList<Board>();
        Board up = getUpNeighbor();   
        addNeighbor(neighbors, up);
        Board down = getDownNeighbor();   
        addNeighbor(neighbors, down);
        Board left = getLeftNeighbor();   
        addNeighbor(neighbors, left);
        Board right = getRightNeighbor();
        addNeighbor(neighbors, right);
        
        return neighbors;
    }    

    private String printTiles(int[][] tiles) {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        // StringBuilder s = new StringBuilder();
        // s.append(n + "\n");
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         s.append(String.format("%2d ", tiles[i][j]));
        //     }
        //     s.append("\n");
        // }
        return printTiles(tiles);
    }             

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = {{2, 0},
                          {1, 3}};
        Board b = new Board(blocks);
        StdOut.println(b.manhattan());
        int[][] blocks2 = {{2, 3},
                           {0, 1}};
        Board c = new Board(blocks2);
        StdOut.println(c.manhattan());
        StdOut.println(b.equals(c));
        int[][] block3 = {{1, 2, 3},
                          {0, 7 ,6},
                          {5, 4, 8}};
        Board d = new Board(block3);
        StdOut.println(d.manhattan());
        StdOut.println(d.hamming());
        block3[0][0] = 2;
        block3[0][1] = 1;
        StdOut.println(d.hamming());
        // ArrayList<Board> neighbors = new ArrayList<Board>();
        // StdOut.print(neighbors.contains(b));

    }
}