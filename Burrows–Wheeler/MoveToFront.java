import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    private static void encodeOutput(int pos, int[] ascii, char c) {
        int[] ascii_copy = ascii.clone();
        updateAscii(pos, ascii, c);
        for (int i = 0; i < ascii_copy.length; i++) {
            if (ascii_copy[i] == (int) c) {
                // StdOut.print((char) i); // correct
                BinaryStdOut.write((char) i);
                // StdOut.println(i);   //debug for decode
                break;
            }
        }

    }
    private static void updateAscii(int pos, int[] ascii, char c) {
        int front = ascii[pos];
        int[] ascii_copy = ascii.clone();
        for (int i=1; i<ascii.length; i++) {
            if (i <= pos) ascii[i] = ascii_copy[i-1];
        }
        ascii[0] = front;
        // encodeOutput(ascii_copy, c);
    }

    private static int[] buildAscii() {
        int[] ascii = new int[256];
        for (int i=0; i<ascii.length; i++) {
            ascii[i] = i;
        }
        return ascii;
    }
    public static void encode() {
        int[] ascii = buildAscii();
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            // StdOut.print(" ");
            // StdOut.printf("%02x", c & 0xff);
            int pos = -1;
            for (int j=0; j<ascii.length; j++) {
                if ((int) c == ascii[j]) {
                    pos = j;
                    break;
                }
            }
            if (pos != -1) encodeOutput(pos, ascii, c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] original_ascii = buildAscii();
        int[] ascii = original_ascii.clone();
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            int pos = -1;
            for (int j=0; j<original_ascii.length; j++) {
                if ((int) c == original_ascii[j]) {
                    pos = j;
                    break;
                }
            }
            // StdOut.print((char) ascii[pos]);
            BinaryStdOut.write((char) ascii[pos]);
            updateAscii(pos, ascii, c);
        }
        // StdOut.println();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
