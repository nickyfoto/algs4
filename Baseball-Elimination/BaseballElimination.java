import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// create a baseball division from given filename in format specified below
public class BaseballElimination {
    private String[] teams;
    private int[] w, l, r;
    private int n;
    private int[][] g;
    private String[][] c; // certificate
    private boolean[] elimination;
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = Integer.parseInt(in.readLine());
        teams = new String[n];
        elimination = new boolean[n];
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        c = new String[n][n];

        String[][] input = new String[n][n+4];
        for (int row=0; row<n; row++) {
            for (int i=0; i<n+4; i++) {
                String s = in.readString();
                input[row][i] = s;
            }
        }

        for (int i=0; i<n; i++) {
            teams[i] = input[i][0];
            w[i] = Integer.parseInt(input[i][1]);
            l[i] = Integer.parseInt(input[i][2]);
            r[i] = Integer.parseInt(input[i][3]);
            for (int col=0; col<n; col++) {
                g[i][col] = Integer.parseInt(input[i][col+4]);
            }
        }
        // StdOut.println(Arrays.toString(teams));
        // printMatrix(g);
        // printMatrix(c);
        for (int i=0; i<n; i++) {
            if (teamIsEliminated(teams[i])) elimination[i] = true;
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
    private void printMatrix(String[][] matrix) {
        StdOut.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StdOut.print(matrix[i][j] + " ");
            }
            StdOut.println();
        }
    }
    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return Arrays.asList(teams);
    }

    private void validate(String team) {
        if (team==null) throw new IllegalArgumentException("team is null ");
        for (int i=0; i<n; i++) {
            if (teams[i].equals(team)) return;
        }
        throw new IllegalArgumentException("team: " + team + " does not exits");
    }
    // number of wins for given team
    public int wins(String team) {
        validate(team);
        int x = getIndex(team);
        return w[x];
    }

    // number of losses for given team
    public int losses(String team) {
        validate(team);
        int x = getIndex(team);
        return l[x];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validate(team);
        int x = getIndex(team);
        return r[x];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        int i = getIndex(team1);
        int j = getIndex(team2);
        return g[i][j];
    }

    private int getIndex(String team) {
        for (int x=0; x<n; x++) {
            if (teams[x].equals(team)) return x;
        }
        return -1;
    }

    private int nChoose(int x) {
        return (n*n-1) / x;
    }
    private int getNumVertices() {
        // StdOut.println("n: "+n);
        // StdOut.println(nChoose(2));
        return 2 + nChoose(2);
    }

    private ArrayList<int[]> getCapacities(int x) {
        ArrayList<int[]> capacities = new ArrayList<int[]>();
        for (int i=0; (i<n ); i++) {
            for (int j=0; (j<n); j++) {
                if (i<j && i!=x && j!=x) {
                    // StdOut.println("i: " + i + " j: " + j);
                    int[] ij = {i, j};
                    capacities.add(ij);
                }
            }
        }
        return capacities;
    }

    private FlowNetwork constructFlowNetwork(int x) {
        int num_of_verteces = getNumVertices();
        // StdOut.println("num_of_verteces: " + num_of_verteces);
        FlowNetwork G = new FlowNetwork(num_of_verteces);
        // edges from source to game vertices
        ArrayList<int[]> capacities = getCapacities(x);
        for (int i=0; i<capacities.size(); i++) {
            // StdOut.println();
            FlowEdge e = new FlowEdge(0, i+1, (double) g[capacities.get(i)[0]][capacities.get(i)[1]]);
            G.addEdge(e);
        }
        // edges from game vertices to team vertices
        for (int i=0; i<capacities.size(); i++) {
            for (int j=0; j<capacities.get(i).length; j++) {
                FlowEdge e;
                if (capacities.get(i)[j] > x) {
                    // StdOut.println("i: " + (i+1) + " :" + (capacities.get(i)[j]+capacities.size()));
                    e = new FlowEdge(i+1, capacities.get(i)[j]+capacities.size(), Double.POSITIVE_INFINITY);
                } else {
                    // StdOut.println("i: " + (i+1) + " :" + (capacities.get(i)[j]+capacities.size()+1));
                    e = new FlowEdge(i+1, capacities.get(i)[j]+capacities.size()+1, Double.POSITIVE_INFINITY);
                }
                G.addEdge(e);
            }
        }
        // edges from team vertices to t
        ArrayList<Integer> teamToT = new ArrayList<Integer>();
        for (int i=0; i<n; i++) {
            if (i != x) teamToT.add(w[x]+r[x]-w[i]);//StdOut.println(" w: " + x + " r: " + x + " -w: " + i);

        }
        for (int i=0; i<teamToT.size(); i++) {
            // StdOut.println("i: " + (i+1+capacities.size()) + " t: " + (num_of_verteces-1));
            FlowEdge e = new FlowEdge((i+1+capacities.size()), (num_of_verteces-1), (double) teamToT.get(i));
            G.addEdge(e);
        }
        return G;
    }

    public boolean isEliminated(String team) {
        validate(team);
        int x = getIndex(team);
        return elimination[x];
    }

    // is given team eliminated?
    private boolean teamIsEliminated (String team) {
        validate(team);
        int x = getIndex(team);
        for (int i=0; i<n; i++) {
            if (w[x]+r[x] < w[i]) {
                c[x][i] = teams[i];
                return true;
            }
        }
        // StdOut.println(team);
        // StdOut.println("x: " + x);
        FlowNetwork G = constructFlowNetwork(x);
        // StdOut.println(G);
        int s = 0, t = G.V()-1;
        // StdOut.println(s);
        // StdOut.println(t);
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        // StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                // if ((v == e.from()) && e.flow() > 0) {
                    // StdOut.println("   " + e);
                    if (v == 0 && e.flow() < e.capacity()) {
                        // StdOut.println(">>>>>>>>>>>>>>>>>>>>>>>>"+team);
                        ArrayList<Integer> cteams = new ArrayList<Integer>();
                        for (int w = 0; w < G.V(); w++) {
                            if (maxflow.inCut(w)) {
                                if (w > getCapacities(x).size()) {
                                    // StdOut.println(w);
                                    cteams.add(w-(1+getCapacities(x).size()));
                                }
                            }
                        }
                        // StdOut.println(cteams);
                        ArrayList<Integer> otherteams = new ArrayList<Integer>();
                        for (int i=0; i<n; i++) {
                            if (i != x) otherteams.add(i);
                        }
                        // StdOut.println(otherteams);
                        for (int i : cteams) {
                            c[x][otherteams.get(i)] = teams[otherteams.get(i)];
                        }
                        return true;
                    }
                // }
            }
        }

        // print min-cut
        // StdOut.print("Min cut: ");
        // for (int v = 0; v < G.V(); v++) {
        //     if (maxflow.inCut(v)) StdOut.print(v + " ");
        // }
        // StdOut.println();

        // StdOut.println("Max flow value = " +  maxflow.value());
        // StdOut.println("=============");
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validate(team);
        int x = getIndex(team);
        ArrayList<String> s = new ArrayList<String>();
        List<String> list = Arrays.asList(c[x]);
        for (String t: list) {
            if (t != null) s.add(t);
        }
        if (s.size() != 0) return s;
        return null;
    }
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        // StdOut.println(division.against("Atlanta", "New_York"));
        for (String team : division.teams()) {
            // StdOut.println(team);
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        // StdOut.println(division.certificateOfElimination("New_York"));
    }
}
