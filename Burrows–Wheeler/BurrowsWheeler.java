import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output


    // private static int[] transform_indices;
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        // transform_indices = new int[n];
        int index = -1;
        for (int i=0; i<n; i++) {
            // StdOut.println(csa.index(i));
            if (csa.index(i) == 0) {
                index = i;
                break;
            }
        }

        // StdOut.println(index);
        BinaryStdOut.write(index);
        for (int i=0; i<n; i++) {
            int origin_idx = csa.index(i);
            // StdOut.println(origin_idx);
            if (origin_idx == 0) {
                // StdOut.printf("origin_idx=%d, %s\n", origin_idx, s.charAt(n-1));
                BinaryStdOut.write(s.charAt(n-1));
            }
            else {
                // StdOut.printf("origin_idx=%d, %s\n", origin_idx, s.charAt(-1));
                BinaryStdOut.write(s.charAt(origin_idx-1));
            }
        }
        BinaryStdOut.close();
    }

    private static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                StdOut.print(m[i][j] + " ");
            }
            StdOut.println();
        }
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        // StdOut.println(transform_indices);
        int b = BinaryStdIn.readInt();
        // StdOut.println(b);
        ArrayList<Character> arr = new ArrayList<Character>();
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            // StdOut.println(c);
            arr.add(c);
        }
        // StdOut.println(arr);
        // Collections.sort(arr);
        int[] sortedIndices = IntStream.range(0, arr.size())
                .boxed().sorted((i, j) -> arr.get(i).compareTo(arr.get(j)) )
                .mapToInt(ele -> ele).toArray();
        // StdOut.println(Arrays.toString(sortedIndices));
        int n = sortedIndices.length;
        char[] res = new char[n];
        int i = 0;
        int idx = sortedIndices[i];
        // StdOut.println(idx);
        while (i < n) {
            if (sortedIndices[idx] == b) {
                // StdOut.printf("i= %d, sortedIndices[idx]= %d\n", 0, sortedIndices[idx]);
                res[n-1] = arr.get(b);
            }
            else {
                // StdOut.printf("i= %d, sortedIndices[idx]= %d\n", i+1, sortedIndices[idx]);
                res[i] = arr.get(sortedIndices[idx]);
            }

            idx = sortedIndices[idx];
            i++;
        }
        // StdOut.println(Arrays.toString(res));
        // StdOut.println();

        for (i=0; i<n; i++) {
             BinaryStdOut.write(res[i]);
             // StdOut.print(res[i]);
        }
        // StdOut.println();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
