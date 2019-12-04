import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;

public class CircularSuffixArray {
    // circular suffix array of s

    private static class ArrayIndexComparator implements Comparator<Integer> {
        // private final int[][] array;
        // public ArrayIndexComparator(int[][] array) {
        //     this.array = array;
        // }
        private final String s;
        private final int n;
        public ArrayIndexComparator(String s) {
            this.s = s;
            this.n = s.length();
        }

        // public Integer[] createIndexArray() {
        //     Integer[] indexes = new Integer[array.length];
        //     for (int i = 0; i < array.length; i++) {
        //         indexes[i] = i; // Autoboxing
        //     }
        //     return indexes;
        // }
        private char getAsciiNum(int index, int i) {
            if (index+i < n) return s.charAt(index+i);
            else return s.charAt(index+i-n);
        }

        @Override
        public int compare(Integer index1, Integer index2) {
            for (int i=0; i<s.length(); i++) {
                char c1 = getAsciiNum(index1, i);
                char c2 = getAsciiNum(index2, i);
                if (c1 != c2) return Character.compare(c1, c2);
            }
            return 0;
        }
    }


    private final int n;
    private Integer[] indexes;
    public CircularSuffixArray(String s) {
        if (s==null) throw new java.lang.IllegalArgumentException("CircularSuffixArray input cannot be null");
        n = s.length();
        // StdOut.println(n);
        // int[][] suffixMatrix = new int[n][n];
        // for (int i=0; i<n; i++) {
        //     suffixMatrix[0][i] = (int) s.charAt(i);
        // }
        // for (int i=1; i<n; i++) {
        //     for (int j=0; j<n-1; j++) {
        //         int fin = suffixMatrix[i-1][0];
        //         suffixMatrix[i][j] = suffixMatrix[i-1][j+1];
        //         suffixMatrix[i][n-1] = fin;
        //     }
        // }
        // Arrays.sort(suffixMatrix, (a, b) -> Integer.compare(a[0], b[0]));
        // printMatrix(suffixMatrix);
        // StdOut.println("==============");
        ArrayIndexComparator comparator = new ArrayIndexComparator(s);
        indexes = new Integer[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = i; // Autoboxing
        }
        Arrays.sort(indexes, comparator);
        // StdOut.println(Arrays.toString(indexes));

        // int[][] sortedSuffixes = new int[n][n];
        // for (int i=0; i<n; i++) {
        //     sortedSuffixes[i] = suffixMatrix[indexes[i]];
        // }
        // printMatrix(sortedSuffixes);
    }

    // length of s
    public int length() {
        return n;
    }
    // returns index of ith sorted suffix
    // return ith row corresponds to which row in original suffix
    public int index(int i) {
        if (i < 0 || i > n-1) throw new java.lang.IllegalArgumentException("i is not between 0 to n-1");
        // StdOut.println(Arrays.toString(indexes));
        // for (int j=0; j<indexes.length; j++) {
        //     if (indexes[j]==i) return j;
        // }
        // return -1;
        return indexes[i];
    }

    private static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                StdOut.print((char )m[i][j] + " ");
            }
            StdOut.println();
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        // transform_indices = new int[n];
        int index = csa.index(3);
        StdOut.println(index);
        // BinaryStdOut.write(index);
        // BinaryStdOut.close();
    }
}
