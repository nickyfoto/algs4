import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;


public class BruteCollinearPoints {

    // finds all line segments containing 4 points
    private LineSegment[] ls;
    private int numberOfSegments;
    private Queue<LineSegment> queue = new Queue<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points cannot be null");
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("point cannot be null");
        }

        int n = points.length;
        Point[] temp = points.clone(); 


        Arrays.sort(temp);
        Point[] sortedPoints = temp;


        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (sortedPoints[j].compareTo(sortedPoints[i]) == 0) throw new IllegalArgumentException("duplicate");
                for (int k = j+1; k < n; k++) {
                    for (int l = k+1; l < n; l++) {
                        if (Cl(sortedPoints[i], sortedPoints[j], sortedPoints[k], sortedPoints[l])) {
                            Point[] a = {sortedPoints[i], sortedPoints[j], sortedPoints[k], sortedPoints[l]};
                            Arrays.sort(a);
                            numberOfSegments++;
                            LineSegment segment = new LineSegment(a[0], a[3]);
                            queue.enqueue(segment);
                        }
                    }
                }
            }
        }
        ls = new LineSegment[queue.size()];
        for (int idx = 0; idx < ls.length; idx++) {
            ls[idx] = queue.dequeue();
        }
    }   

    private boolean Cl(Point p, Point q, Point r, Point s) {
        if (p.slopeTo(q) != p.slopeTo(r)) return false;
        else return (p.slopeTo(q) == p.slopeTo(s));
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }       
    
    public LineSegment[] segments() {
        LineSegment[] defensiveCopy = new LineSegment[ls.length];
 
         for (int i = 0; i < ls.length; i++) {
             defensiveCopy[i] = ls[i];
        }

        return defensiveCopy;
        
        // return ls;
    }               

    public static void main(String[] args) {
        // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }

        int n = 8;
        Point[] points = new Point[n];
        points[0] = new Point (10000    ,  0);
        points[1] = new Point (0 , 10000);
        points[2] = new Point (3000  , 7000);
        points[3] = new Point (7000 ,  3000);
        points[4] = new Point (20000 ,21000);
        points[5] = new Point (3000 ,  4000);
        points[6] = new Point (14000 , 15000);
        points[7] = new Point (6000 ,  7000);
        // points[9] = new Point (10798,  9182);
        


       

        // Point p = new Point(1, 2);
        // Point q = new Point(1, 2);
        // StdOut.println(p == q);
        // StdOut.println(p.compareTo(q));
        // StdOut.println(p.equals(q));
        
        

        // for (int j=0; j < 5; j++) {
        //     Knuth.shuffle(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("collinear.numberOfSegments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment); 
        }
        //     }
        //     StdOut.println("----");
            
        // Point[] points = new Point[n];
        points[0] = new Point (1234    ,  0);
        points[1] = new Point (0 , 1234);
        points[2] = new Point (3000  , 7000);
        points[3] = new Point (7000 ,  3000);
        points[4] = new Point (2000 ,2100);
        points[5] = new Point (3000 ,  4000);
        points[6] = new Point (14000 , 15000);
        points[7] = new Point (6000 ,  7000);

        StdOut.println("collinear.numberOfSegments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment); 
        }




        // for (Point p: points) {
            // StdOut.println(p);
        // }


        // StdOut.println("-----------");
        // Knuth.shuffle(points);

        // collinear.BruteCollinearPoints(points);

        // BruteCollinearPoints collinear2 = new BruteCollinearPoints(points);
        
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment); 
        // }

        // for (Point p: points) {
            // StdOut.println(p);
        // }

        // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        
        // for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment); 
        // }



        // StdOut.println(collinear.segments());


        /*
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
            // StdOut.println(p);
        }
        StdDraw.show();

        // print and draw the line segments
        
        

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        */
    }
}