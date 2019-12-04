import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.Queue;
import java.util.ArrayList;
import java.util.Arrays;


public class BoggleSolver
{
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TST<Integer> st = new TST<Integer>();
    private BoggleBoard board;
    private int rows, cols, rootR, rootC;
    private boolean[][][] explored;
    private ArrayList<String> ValidWords = new ArrayList<String>();

    public BoggleSolver(String[] dictionary) {
        for (String s: dictionary) {
            // StdOut.print(s.length());
            if (s.length() < 3) st.put(s, 0);
            else if (s.length() < 5) st.put(s, 1);
            else if (s.length() == 5) st.put(s, 2);
            else if (s.length() == 6) st.put(s, 3);
            else if (s.length() == 7) st.put(s, 5);
            else st.put(s, 11);
        }
    }

    private void printMatrix(int[][] matrix) {
        StdOut.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StdOut.print(matrix[i][j] + " ");
            }
            StdOut.println();
        }
    }

    private ArrayList<int[]> constructPath(int r, int c, int[][][] traces) {
        ArrayList<int[]> path = new ArrayList<int[]>();
        int[] root = {rootR, rootC};
        // StdOut.println("root: " + Arrays.toString(root));
        path.add(root);
        // StdOut.println("getNeighbors r: " + r + " c: " + c);
        int nr = r;
        int nc = c;
        while (!Arrays.equals(traces[nr][nc], root)) {
            // StdOut.println("traces[nr][nc]: " + Arrays.toString(traces[nr][nc]));
            path.add(traces[nr][nc]);
            // StdOut.println("before nr: "+nr + " nc "+nc);
            int[] rc = traces[nr][nc];
            nr = rc[0];
            nc = rc[1];
            // StdOut.println("nr: "+nr + " nc "+nc);
        }
        // StdOut.println("printing path");
        // for (int[] p: path) {
        //     StdOut.print(Arrays.toString(p));
        // }
        // StdOut.println("\nfinishing printing path");
        return path;
    }

    private ArrayList<int[]> getNeighbors(int r, int c, int[][][] traces) {
        int[][] neighbors = new int[8][2];
        neighbors[0][0] = r;
        neighbors[0][1] = c-1;
        neighbors[1][0] = r-1;
        neighbors[1][1] = c-1;
        neighbors[2][0] = r-1;
        neighbors[2][1] = c;
        neighbors[3][0] = r-1;
        neighbors[3][1] = c+1;
        neighbors[4][0] = r;
        neighbors[4][1] = c+1;
        neighbors[5][0] = r+1;
        neighbors[5][1] = c;
        neighbors[6][0] = r+1;
        neighbors[6][1] = c-1;
        neighbors[7][0] = r+1;
        neighbors[7][1] = c+1;

        // StdOut.println("printing traces");
        // for (int i=0; i<traces.length; i++) {
        //     for (int j=0; j<traces[0].length; j++) {
        //         StdOut.print(Arrays.toString(traces[i][j]));
        //     }
        //     StdOut.println();
        // }
        // StdOut.println("finish printing traces");

        ArrayList<int[]> path = constructPath(r, c, traces);



        ArrayList<int[]> filteredNeighbors = new ArrayList<int[]>();
        for (int[] n: neighbors) {
            // if (!isInList(visited, n)) filteredNeighbors.add(n);
            if ((!(n[0]<0 || n[0] >= rows || n[1]<0 || n[1] >= cols)) &&
               (!isInList(path, n)))
            {
                filteredNeighbors.add(n);
            }
        }
        return filteredNeighbors;
    }

    private boolean notExplored(int r, int c) {
        for (int i=0; i<8; i++) {
            if (!explored[r][c][i]) return true;
        }
        return false;
    }

    private boolean isInList(ArrayList<int[]> list, int[] candidate){

        for(int[] item : list){
            if(Arrays.equals(item, candidate)) return true;
        }
        return false;
    }

    // private void explore(String s, int r, int c, int[][][] traces, ArrayList<int[]> visited) {
    private void explore(String s, int r, int c, int[][][] traces) {
        // StdOut.println("before return r: " + r + " c: " + c);
        // StdOut.println(Arrays.toString(explored[rootR][rootC]));
        // if (r<0 || r >= rows || c<0 || c >= cols) return;
        // StdOut.println("after return r: " + r + " c: " + c + " "+board.getLetter(r, c));
        int[] point = {r, c};
        // if (!isInList(visited, point)) visited.add(point);
        String word;
        if (String.valueOf(board.getLetter(r, c)).equals("Q")) word = s+"QU";
        else word = s+String.valueOf(board.getLetter(r, c));
        // StdOut.println(String.valueOf(board.getLetter(r, c)));
        // StdOut.println(String.valueOf(board.getLetter(r, c)).equals("Q"));
        // StdOut.println("checking r: "+ r+ " c: "+c + " " + word);

        Iterable<String> q = st.keysWithPrefix(word);
        if (((Queue) q).size() > 0) {
            // StdOut.println("q: not null");
            int v = scoreOf(word);
            // StdOut.println(v);
            if (v > 0 && (!ValidWords.contains(word))) {
                ValidWords.add(word);
                // StdOut.println(word + " is added.");
            }
            // for (int[] v: visited) {
                // StdOut.print(Arrays.toString(v));
            // }
            // StdOut.println();
            // StdOut.println();

            ArrayList<int[]> neighbors = getNeighbors(r, c, traces);
            // printMatrix(neighbors);
            // StdOut.println("printing neighbors: ");
            // for (int[] n: neighbors) {
            //     StdOut.print(Arrays.toString(n));
            // }
            // StdOut.println("\nfinish printing neighbors");
            for (int i=0; i<neighbors.size(); i++) {
                // StdOut.println("i:" + i);
                // if (r==rootR && c==rootC) explored[rootR][rootC][i] = true;
                int[] source = {r, c};
                traces[neighbors.get(i)[0]][neighbors.get(i)[1]] = source;
                // explore(word, neighbors.get(i)[0], neighbors.get(i)[1], traces, visited);
                explore(word, neighbors.get(i)[0], neighbors.get(i)[1], traces);
            }

        } //else StdOut.println("else r: "+ r+ " c: "+c + " " + word);;



        // StdOut.println("          return from r: " + r + " c: " + c + " "+board.getLetter(r, c));
        // StdOut.println(board);
    }
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        this.rows = board.rows();
        this.cols = board.cols();
        // char[][] boardcopy = new char[rows][cols];
        // for (int r=0; r<rows; r++) {
        //     for (int c=0; c<cols; c++) {
        //         boardcopy[r][c] = board.getLetter(r,c);
        //     }
        // }
        // this.board = new BoggleBoard(boardcopy);
        // StdOut.println(this.board);
        this.explored = new boolean[rows][cols][8];
        ValidWords = new ArrayList<String>();
        for (int r=0; r<rows; r++) {
        // for (int r=0; r<4; r++) {
            // for (int c=0; c<4; c++) {
            for (int c=0; c<cols; c++) {
                this.rootR = r;
                this.rootC = c;
                ArrayList<int[]> visited = new ArrayList<int[]>();
                int[][][] traces = new int[rows][cols][2];
                // explore("", r, c, traces, visited);
                int[] root = {this.rootR, this.rootC};
                traces[this.rootR][this.rootC] = root;
                // StdOut.println("start exploring r: " + r + " c: " + c + " " + board.getLetter(r, c));
                // explore("", r, c, traces);
                explore("", r, c, traces);
                // StdOut.println("================");
                // StdOut.println(board.getLetter(r, c));
            }
        }
        // ValidWords.add("a");
        return ValidWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        Object v = st.get(word);
        if (v == null) return 0;
        return (int) v;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
