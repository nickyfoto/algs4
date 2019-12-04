import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Outcast {
    // constructor takes a WordNet object
    private final WordNet wn;
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException("null argument is not allowed");
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException("null argument is not allowed");
        int[] d = new int[nouns.length];
        // StdOut.println("hello");
        for (int i=0; i < nouns.length; i++) {
            int sum = 0;
            for (int j=0; j< nouns.length; j++) {
                sum += wn.distance(nouns[i], nouns[j]);
            }
            d[i] = sum;
        }
        return nouns[getIndexOfLargest(d)];
    }

    private int getIndexOfLargest(int[] array ) {
        if ( array == null || array.length == 0 ) return -1; // null or empty
        int largest = 0;
        for ( int i = 1; i < array.length; i++ ) {
          if ( array[i] > array[largest] ) largest = i;
        }
        return largest; // position of the first largest found
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
            // StdOut.println(args[t] + ": " );
        }
    }
}
