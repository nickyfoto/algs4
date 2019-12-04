import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Solver {

    private Board board;
    // private int moves;
    private int realMoves;
    private ArrayList<Board> solutions = new ArrayList<Board>();
    // private ArrayList<Board> neighborList = new ArrayList<Board>();
    // private ArrayList<Board> neighborListBT = new ArrayList<Board>();
    private SearchNode out;
    private SearchNode outBT;
    private Boolean isSolvable;

    // private int count;
    // find a solution to the initial board (using the A* algorithm)
    private boolean Debug = false;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null Board is not allowed");
        board = initial;

        if (Debug) StdOut.println("origin: ");
        if (Debug) StdOut.print(board);





        if (Debug) StdOut.println("twin: ");
        Board boardBT = board.twin();
        if (Debug) StdOut.print(boardBT);

        if (Debug) StdOut.println("initial hamming: " + board.hamming());
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(SearchNode.PRIORITY_ORDER);
        MinPQ<SearchNode> pqBT = new MinPQ<SearchNode>(SearchNode.PRIORITY_ORDER);

        SearchNode sn = new SearchNode(board, 
                                       board.hamming(),
                                       null,
                                       board.manhattan(),
                                       0);
        SearchNode snBT = new SearchNode(boardBT, 
                                         boardBT.hamming(),
                                         null,
                                         boardBT.manhattan(),
                                         0);

        if (board.hamming() == 0) {
            out = sn;
            isSolvable = true;
        } else if (boardBT.hamming() == 0) {
            isSolvable = false;
        } else {
            // neighborList.add(sn.snBoard);
            // neighborListBT.add(snBT.snBoard);
            pq.insert(sn);
            pqBT.insert(snBT);
            // count = count+2;
            while (isSolvable == null) {
                // StdOut.println(isSolvable);
                
                // if (pq.isEmpty()) {
                //     isSolvable = false;
                //     break;
                // }

                if (Debug) StdOut.println("pq.size():   " + pq.size());
                

                try {
                    out = pq.delMin();
                    // count++;
                }
                catch (NoSuchElementException ex) {
                    StdOut.println(ex);
                    isSolvable = false;
                    break;
                }

                // neighborList.add(out.snBoard);

                try {
                    outBT = pqBT.delMin();
                    // count++;
                }
                catch (NoSuchElementException ex) {
                    StdOut.println(ex);
                    StdOut.println("twin pq empty");
                    // isSolvable = false;
                    break;
                }            

                // neighborListBT.add(outBT.snBoard);
                
                
                // if (!pqBT.isEmpty()) outBT = ;



                if (Debug) StdOut.println("pqBT.size(): " + pqBT.size());
                
                if (out.getHamming() == 0) {
                    isSolvable = true;
                    break;
                }
                if (outBT.getHamming() == 0) {
                    isSolvable = false;
                    break;
                }

                if (Debug) StdOut.println("---------------");
                if (Debug) StdOut.println("out: \n" + out.snBoard);
                if (Debug) StdOut.println("out.getHamming(): " + out.getHamming());
                if (Debug) StdOut.println("out.getManhattan(): " + out.getManhattan());
                if (Debug) StdOut.println("out.getPriority(): " + out.getPriority());
                if (Debug) StdOut.println("---------------");
                // moves++;
                ArrayList<SearchNode> neighbors = out.neighbors();
                ArrayList<SearchNode> neighborsBT = outBT.neighbors();
                
                for (SearchNode snn : neighbors) {
                    // if (!neighborList.contains(snn.snBoard)) {
                        if (Debug) StdOut.println("adding to pq:");
                        if (Debug) StdOut.println(snn.snBoard);
                        if (Debug) StdOut.println("hamming: " + snn.getHamming());
                        if (Debug) StdOut.println("manhattan: " + snn.getManhattan());
                        if (Debug) StdOut.println("ssn.getPriority(): " + snn.getPriority());
                        // if (snn.getHamming() == 0) {
                        //     isSolvable = true;
                        //     out = snn;
                        //     break;
                        // }
                        
                        // neighborList.add(snn.snBoard);
                        // if (neighborList.size() % 1000 == 0) StdOut.println("neighborList.size(): " + neighborList.size());
                        pq.insert(snn);
                        // count++;
                    // }
                }
                for (SearchNode snnBT : neighborsBT) {
                    // if (!neighborListBT.contains(snnBT.snBoard)) {

                        // snn.addMovesToPriority(moves);
                        // if (Debug) StdOut.println("adding to pq:");
                        // if (Debug) StdOut.println(snnBT.snBoard);
                        // if (Debug) StdOut.println("hamming: " + snnBT.getHamming());
                        if (snnBT.getHamming() == 0) {
                            isSolvable = false;
                            break;
                        }
                        // StdOut.println("moves: " + moves);
                        // if (Debug) StdOut.println("ssn.getPriority(): " + snnBT.getPriority());
                        // neighborListBT.add(snnBT.snBoard);
                        // if (neighborListBT.size() % 1000 == 0) StdOut.println("neighborListBT.size(): " + neighborListBT.size());
                        pqBT.insert(snnBT);
                        // count++;
                    // }
                }

                // if (isSolvable != null && isSolvable) {
                    // break;
                // }
            }
        }
        
        // StdOut.println(isSolvable);
        if (isSolvable) {
            if (Debug) StdOut.println("SOLUTION FOUND: ");
            // StdOut.println(out ==null);
            // StdOut.println(out.predecessor);
            solutions.add(0, out.snBoard);
            while (out.getPredecessor() != null) {
                // StdOut.println("add to solutions: "+ out.getPredecessor().snBoard);
                realMoves++;
                solutions.add(0, out.getPredecessor().snBoard);
                out = out.getPredecessor();
            }
        }
        
        // StdOut.println(count);
        
    }
    
    private static class SearchNode {

        public static final Comparator<SearchNode> PRIORITY_ORDER = new PriorityOrder();

        private final int priority;
        private final int manhattan;
        private final int hamming;
        private final int moves;
        private final Board snBoard;
        private final SearchNode predecessor;
        public SearchNode(Board b,
                          int h,
                          SearchNode pred,
                          int m,
                          int steps) {
            snBoard = b;
            hamming = h;
            predecessor = pred;
            manhattan = m;
            moves = steps;
            // priority = moves + hamming;
            priority = moves + manhattan;
        }

        private static class PriorityOrder implements Comparator<SearchNode> {
            public int compare(SearchNode a, SearchNode b) {
                if ((a.priority - b.priority) != 0) return a.priority - b.priority;
                // return a.manhattan - b.manhattan;
                else return a.hamming - b.hamming;
            }
        }

        public ArrayList<SearchNode> neighbors() {
            // StdOut.println("before: " + this.snBoard);
            // StdOut.println("Solver::neighbors: ");
            ArrayList<SearchNode> neighbors = new ArrayList<SearchNode>();
            // StdOut.println("before: " + this.snBoard);
            Iterable<Board> boards = new ArrayList<Board>();
            boards = this.snBoard.neighbors();
            // StdOut.println("after: " + this.snBoard);
            for (Board b: boards) {
                // StdOut.println(b);
                // StdOut.println("checking: "+b);
                // StdOut.println("checking father: "+this.getPredecessor());
                if (this.getPredecessor() == null) {
                    SearchNode sn = new SearchNode(b,
                                               b.hamming(),
                                               this,
                                               b.manhattan(),
                                               this.moves+1);
                    // StdOut.println("checking: "+(this.priority - this.snBoard.hamming()));
                    // StdOut.println("checking: "+(this.priority - this.snBoard.hamming()));
                    // sn.addMovesToPriority(this.priority - this.snBoard.hamming()+1);
                    neighbors.add(sn);
                }   else {    
                        if (!b.equals(this.getPredecessor().snBoard)) {
                            SearchNode sn = new SearchNode(b,
                                                       b.hamming(),
                                                       this,
                                                       b.manhattan(),
                                                       this.moves+1);
                            neighbors.add(sn);
                        }
                    }
                
            }
            return neighbors;
        }

        // public int addMovesToPriority(int moves) {
            // return this.priority = this.priority + moves;
        // }

        public SearchNode getPredecessor() {
            return this.predecessor;
        }

        public int getHamming() {
            return this.hamming;
        }

        public int getManhattan() {
            return this.manhattan;
        }

        public int getPriority() {
            return this.priority;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }           
    

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return realMoves;
    }                    

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        return solutions;
    }   

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}