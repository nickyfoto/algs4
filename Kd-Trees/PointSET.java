// Corner cases.  Throw a java.lang.IllegalArgumentException if any argument is null. 

// Performance requirements.  Your implementation should support insert() and contains()
// in time proportional to the logarithm of the number of points in the set in the worst case; 
// it should support nearest() and range() in time proportional to the number of points in the set.

import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class PointSET {
    // construct an empty set of points 
    private TreeSet<Point2D> ts;
    public PointSET() {
        ts = new TreeSet<Point2D>();
        // StdOut.println("hello");
        // StdOut.println(ts);
    }

    // is the set empty?                               
    public boolean isEmpty() {
        return ts.isEmpty();
    }                      

    // number of points in the set 
    public int size() {
        return ts.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p could not be null");
        ts.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p could not be null");
        return ts.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        for (Point2D p: ts) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument p could not be null");
        TreeSet<Point2D> inside = new TreeSet<Point2D>();
        for (Point2D p: ts) {
            if (rect.contains(p)) {
                inside.add(p);
            }
        }
        return inside;
    }             

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p could not be null");
        double distance = Double.POSITIVE_INFINITY;
        if (ts.isEmpty()) return null;
        Point2D res = null;
        for (Point2D point: ts) {
            // StdOut.println(point);
            double d = point.distanceTo(p);
            if (d < distance) {
                // StdOut.println(d);
                distance = d;
                res = point;
            }
        }
        return res;
    }


    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        // KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            // kdtree.insert(p);
            brute.insert(p);
        }
        for (Point2D p2: brute.ts) {
            StdOut.println(p2);
        }
        Point2D query = new Point2D(0.5, 0.5);
        // brute.nearest(query);
        // StdOut.println(brute.nearest(query));
    }                 
}