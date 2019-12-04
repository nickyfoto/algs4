import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.TreeMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.ArrayList;

public class WordNet {

    private final TreeMap<String, ArrayList<Integer>> tmap = new TreeMap<String, ArrayList<Integer>>();
    private final TreeMap<Integer, String> imap = new TreeMap<Integer, String>();
    // constructor takes the name of the two input files
    private SAP sap;
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) throw new IllegalArgumentException("null argument is not allowed");
        if (hypernyms == null) throw new IllegalArgumentException("null argument is not allowed");
        In f = new In(synsets);

        String[] lines = f.readAllLines();
        // for (int i=0; i < lines.length; i++) {
        //     int k = Integer.parseInt(lines[i][0]);
        //     String v = lines[i][1];
        //     imap.put(k, v);

        // }

        for (int i=0; i < lines.length; i++) {
            String[] line = lines[i].split(",");

            int k = Integer.parseInt(line[0]);
            String v = line[1];
            imap.put(k, v);
            for (String s: line[1].split(" ")) {
                // StdOut.println(s);
                if (!tmap.containsKey(s)) {
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    arr.add(k);
                    tmap.put(s, arr);
                } else {
                    tmap.get(s).add(k);
                }
            }
        }

        // StdOut.println(tmap.size());

        In h = new In(hypernyms);

        lines = h.readAllLines();
        // StdOut.println("lines.length: "+lines.length);
        Digraph G = new Digraph(lines.length+1);

        for (int i=0; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            int v = Integer.parseInt(line[0]);
            for (int j=1; j<line.length ; j++) {
                G.addEdge(v, Integer.parseInt(line[j]));
            }
        }

        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle()) throw new IllegalArgumentException("Digraph is not DAG");


        sap = new SAP(G);
        // StdOut.println(G.V());
        // StdOut.println(G.E());
   }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return tmap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("null argument is not allowed");
        return tmap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null) throw new IllegalArgumentException("null argument is not allowed");
        if (nounB == null) throw new IllegalArgumentException("null argument is not allowed");
        if (!isNoun(nounA)) throw new IllegalArgumentException("nounA is not a WordNet noun");
        if (!isNoun(nounB)) throw new IllegalArgumentException("nounA is not a WordNet noun");
        ArrayList<Integer> v = tmap.get(nounA);
        ArrayList<Integer> w = tmap.get(nounB);
        // StdOut.println("A: " + nounA + ", B: " + nounB);
        // StdOut.println("intA: " + v + ", intB: " + w);
        // StdOut.println(sap);
        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null) throw new IllegalArgumentException("null argument is not allowed");
        if (nounB == null) throw new IllegalArgumentException("null argument is not allowed");
        if (!isNoun(nounA)) throw new IllegalArgumentException("nounA is not a WordNet noun");
        if (!isNoun(nounB)) throw new IllegalArgumentException("nounA is not a WordNet noun");
        ArrayList<Integer> v = tmap.get(nounA);
        ArrayList<Integer> w = tmap.get(nounB);
        int val = sap.ancestor(v, w);
        return imap.get(val);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        // TreeMap<String, ArrayList<Integer>> tmap = new TreeMap<String, ArrayList<Integer>>();
        // ArrayList<Integer> arr = new ArrayList<Integer>();
        // tmap.put("a", arr);
        // tmap.get("a").add(1);
        // tmap.get("a").add(2);
        // tmap.get("a").add(3);
        // StdOut.println(tmap);
    }
}
