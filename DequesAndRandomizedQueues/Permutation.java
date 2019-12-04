import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.In;

public class Permutation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> stack = new RandomizedQueue<String>();
        // StdOut.println(n);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            stack.enqueue(s);
            // StdOut.println(s);
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(stack.dequeue());
        }
    }
}