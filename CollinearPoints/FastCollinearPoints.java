import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;


public class FastCollinearPoints {

    // finds all line segments containing 4 or more points
    private int numberOfSegments;
    private LineSegment[] ls;
    private Queue<Point[]> queue = new Queue<Point[]>();
    // private 

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points cannot be null");
        int n = points.length;
        
        
        // StdOut.println("----------");

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("point cannot be null");
        }
        Point[] temp = points.clone();
        Point[] sortPoints = points.clone();
        Arrays.sort(sortPoints);

        for (int i=0; i < n; i++) {
            Arrays.sort(temp, sortPoints[i].slopeOrder());
            double[] sortedSlopes = new double[n];
            // for (Point p: temp) {
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    if (sortPoints[j].compareTo(sortPoints[i]) == 0) throw new IllegalArgumentException("duplicate");
                }
                // StdOut.print("  ");
                // StdOut.print(temp[j]);
                sortedSlopes[j] = sortPoints[i].slopeTo(temp[j]); 
                // StdOut.print(points[i].slopeTo(temp[j]));
                // StdOut.println();
            }
            filter(sortedSlopes, temp);
        }
        // StdOut.println(queue.size());
        int qSize = queue.size();
        ls = new LineSegment[qSize];
        for (int idx = 0; idx < qSize; idx++) {
            Point[] p = queue.dequeue();
            ls[idx] = new LineSegment(p[0], p[1]);
        }
    }

    // private void filter(double[] arr, String[] arr2) {
    private void filter(double[] arr, Point[] arr2) {
        int i = 1;
        int j = 2;
        int n = arr.length;
        // StdOut.println("arr length: " + n);
        while (j < n-1) {
            if (arr[i] == arr[j]) {
                // StdOut.printf("i: %d, j: %d\n", i, j);
                int count = 1;
                while (j < n && !(arr[i] < arr[j])) {
                    count++;
                    j++;
                    // StdOut.printf("i: %d, j: %d\n", i, j);
                }
                if (count > 2) {
                    // StdOut.println("collinear found");                    
                    // String[] a = new String[j-i+1];
                    Point[] a = new Point[j-i+1];
                    a[0] = arr2[0];
                    for (int idx=1; idx<a.length; idx++) {
                        a[idx] = arr2[idx+i-1];
                    }

                    Arrays.sort(a);
                    // for (String s : a) {
                    // for (Point s : a) {
                        // StdOut.print(s+",");
                    // }
                    addToQueue(a);
                    // StdOut.println("\n Queue size: " + queue.size());
                    // StdOut.println();

                }
                // StdOut.println("count: " + count);
            }
            j++;
            i = j-1;
        }
        // StdOut.printf("count: %d\n", count);
        // return count > 1;
    }
    

    private boolean noDuplicate(Point[] pointsTuple) {
        for (Point[] pt : queue) {
            if (pt[0].compareTo(pointsTuple[0]) == 0 &&
                pt[1].compareTo(pointsTuple[1]) == 0) return false;
        }
        return true;
    }

    private void addToQueue(Point[] arr) {
        Point p = arr[0];
        Point q = arr[arr.length-1];
        Point[] pointsTuple = {p, q};
        if (queue.isEmpty()) {        
            queue.enqueue(pointsTuple);
            numberOfSegments++;
        }     
        if (noDuplicate(pointsTuple)) {
            queue.enqueue(pointsTuple);
            numberOfSegments++;
        }
        
    }
    

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }       

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] defensiveCopy = new LineSegment[ls.length];
 
         for (int i = 0; i < ls.length; i++) {
             defensiveCopy[i] = ls[i];
        }

        return defensiveCopy;
        // return ls;
    }


    public static void main(String[] args) {

        // // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // int n = 10;
        // Point[] points = new Point[n];
        // points[0] = new Point (4893, 27468);
        // points[1] = new Point (5727, 32498);
        // points[2] = new Point (4627, 16681);
        // points[3] = new Point (30452, 5519);
        // points[4] = new Point (14920, 17048);
        // points[5] = new Point (26364,  6415);
        // points[6] = new Point (1408, 31097);
        // points[7] = null;
        // points[8] = new Point (5285,  7864);
        // points[9] = new Point (10798,  9182);        
        

        
        


        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        // }

        // for (int j=0; j < 5; j++) {
        //     Knuth.shuffle(points);
        //     FastCollinearPoints collinear = new FastCollinearPoints(points);
        //     for (LineSegment segment : collinear.segments()) {
        //         StdOut.println(segment); 
        //     }
        // }
        // StdOut.println(collinear.numberOfSegments());

        /*
        double[] test = {Double.NEGATIVE_INFINITY, -3.0, -2.0, -1.0, -1.0,
                                             -1.0,  1.0,  2.0,  2.0,  2.0,
                                              4.0,  5.0,  5.0,  5.0,  5.0,
                                              6.0, Double.POSITIVE_INFINITY,
                                              Double.POSITIVE_INFINITY
                                             };
        String[] s = {"a", "b", "c", "d", "e",
                      "f", "g", "h", "i", "j",
                      "k", "l", "m", "n", "o",
                      "p", "q", "r"
                     };

        collinear.filter(test, s);
        */



        // Point p = new Point(1, 2);
        // Point q = new Point(1, 2);

        // StdOut.println("a" == "a");

        // StdOut.println(p.toString().equals(q.toString()));


        // StdOut.println(p.equals(q));
        // StdOut.println(q.toString());
        // LineSegment l = new LineSegment(p, q);
        // StdOut.println(l.p);
        // collinear.filter(test, s);
        
        // draw the points

        /*
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();

        }
        StdDraw.show();
        */
    }
}