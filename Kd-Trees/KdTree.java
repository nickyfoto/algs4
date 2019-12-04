// Corner cases.  Throw a java.lang.IllegalArgumentException if any argument is null. 

// Performance requirements.  Your implementation should support insert() and contains()
// in time proportional to the logarithm of the number of points in the set in the worst case; 
// it should support nearest() and range() in time proportional to the number of points in the set.

import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.NoSuchElementException;

public class KdTree {
    // construct an empty set of points 
    
    public KdTree() {
        
        // StdOut.println("hello");
        // StdOut.println(nodeList);
    }

    private Node root;             // root of BST
    private ArrayList<Node> nodeList = new ArrayList<Node>();
    private static class Node {

        private final Point2D point;
        private Node left, right, parent;    // left and right subtrees
        private int size;            // number of nodes in subtree
        private boolean vertical;
        private double upEnd, downEnd, leftEnd, rightEnd;
        public Node(Point2D point, int size) {
            this.point = point;
            this.size = size;
            this.vertical = true;
            // else this.vertical = !this.parent.vertical;
        }

        
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }



    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to insert() is null");
        // StdOut.print(p);
        // StdOut.println(" " + contains(p));
        if (!contains(p)) root = insert(root, p, null);
        // root = insert(root, p, null);
    }

    private Node insert(Node x, Point2D point, Node parentNode) {
        if (x == null) {
            Node node = new Node(point, 1);
            node.parent = parentNode;
            if (parentNode!=null) {
                node.vertical = !parentNode.vertical;
                if (!node.vertical) {
                    if (node.point.x() > node.parent.point.x()) {
                        node.leftEnd = node.parent.point.x();
                        if (node.parent.parent == null) node.rightEnd = 1.0;
                        else node.rightEnd = node.parent.parent.rightEnd;
                    } else {
                        node.rightEnd = node.parent.point.x();
                        if (node.parent.parent == null) node.leftEnd = 0.0;
                        else node.leftEnd = node.parent.parent.leftEnd;
                    }
                } else {
                    if (node.point.y() < node.parent.point.y()) {
                        node.upEnd = node.parent.point.y();
                        node.downEnd = node.parent.parent.downEnd;
                    } else {
                        node.downEnd = node.parent.point.y();
                        node.upEnd = node.parent.parent.upEnd;
                    }
                }
            } else {
                node.upEnd = 1.0;
                node.downEnd = 0.0;
            }
            // StdOut.println("node.vertical: "+node.vertical);
            // StdOut.println("------------------------");
            nodeList.add(node);
            return node;
        }
        if (parentNode == null) {
            // if (x.left == null) StdOut.println("parentNode null: \nx.left: "+x.left+" point: " + point + " x: " + x.point);
            // else StdOut.println("parentNode null: \nx.left.point: "+x.left.point+" point: " + point + " x: " + x.point);
            
            double cmp = point.x() - x.point.x();
            if (cmp < 0) x.left  = insert(x.left,  point, x);
            else x.right = insert(x.right, point, x);

        } else {
            if (parentNode.vertical) {

                // if (x.left == null) StdOut.println("parentNode.vertical "+ parentNode.vertical + "\nx.left: "+x.left+" point: " + point + " x: " + x.point);
                // else StdOut.println("parentNode.vertical "+ parentNode.vertical + "\nx.left: "+x.left.point+" point: " + point + " x: " + x.point);
                
                double cmp = point.y() - x.point.y();
                if (cmp < 0) x.left  = insert(x.left,  point, x);
                else {
                    // StdOut.println("here: " + point + "x: " + x.point +" x.right: " +x.right);
                    x.right = insert(x.right, point, x);
                }
            } else {

                // if (x.left == null) StdOut.println("parentNode.vertical "+ parentNode.vertical + "\nx.left: "+x.left+" point: " + point + " x: " + x.point);
                // else StdOut.println("parentNode.vertical "+ parentNode.vertical + "\nx.left: "+x.left.point+" point: " + point + " x: " + x.point);
                // StdOut.println(point);
                double cmp = point.x() - x.point.x();
                if (cmp < 0) x.left  = insert(x.left,  point, x);
                else x.right = insert(x.right, point, x);
            }
        }




        
        
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }




    private Node get(Point2D point) {
        // StdOut.println("get: " + point);
        // StdOut.println("res: " + get(root, point, null));
        // return get(root, point, null);
        return get(root, point);
    }

    private Node get(Node x, Point2D point) {
        if (x == null) return null;
        if (x.vertical) {
            double cmp = point.x() - x.point.x();
            if (cmp < 0) return get(x.left,  point);
            else if (cmp > 0) return get(x.right, point);
            else {
                if (point.y() - x.point.y() == 0) return x;
                else return get(x.right, point);
            }
        } else {
            double cmp = point.y() - x.point.y();
            if (cmp < 0) return get(x.left,  point);
            else if (cmp > 0) return get(x.right, point);
            else {
                if (point.x() - x.point.x() == 0) return x;
                else return get(x.right, point);
            }
        } 
    }
    

    

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(p) != null;
        // return nodeList.contains(p);
    }

    
    



    // draw all points to standard draw 
    public void draw() {
        for (Node n: nodeList) {
            // StdOut.println("point: " + n.point);
            StdDraw.setPenRadius(0.01);
            if (n.vertical) {
                // StdOut.println("draw vertical " + n.point);
                // StdDraw.setPenColor(StdDraw.RED);
                // StdDraw.line(n.point.x(), n.downEnd, n.point.x(), n.upEnd);
            } else {
                // StdOut.println("draw horizontal " + n.point);
                // StdDraw.setPenColor(StdDraw.BLUE);
                // StdDraw.line(n.leftEnd, n.point.y(), n.rightEnd, n.point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.03);
            n.point.draw();
        }
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument could not be null");
        ArrayList<Point2D> inside = new ArrayList<Point2D>();
        // for (Node n: nodeList) {
            // if (rect.contains(n.point)) {
                // inside.add(n.point);
            // }
        // }
        search(rect, inside, root);

        // StdOut.println("inside size: "+ inside.size());
        return inside;
    }             

    private void search(RectHV rect, ArrayList<Point2D> inside, Node n) {
        if (n == null) return;
        if (rect.contains(n.point)) {
            inside.add(n.point);
            search(rect, inside, n.left);
            search(rect, inside, n.right);
        } else {
            if (n.vertical){
                // StdOut.println(rect);
                // StdOut.println("xmax(): " + rect.xmax());
                // StdOut.println("xmin(): " + rect.xmin());
                // StdOut.println(" " + n.point);
                if (rect.xmax() <= n.point.x()) search(rect, inside, n.left);
                if (rect.xmin() >= n.point.x()) search(rect, inside, n.right);
                if (rect.xmin() < n.point.x() && rect.xmax() > n.point.x()) {
                    search(rect, inside, n.left);
                    search(rect, inside, n.right);
                }
            } else {
                if (rect.ymax() <= n.point.y()) search(rect, inside, n.left);
                if (rect.ymin() >= n.point.y()) search(rect, inside, n.right);
                if (rect.ymax() > n.point.y() && rect.ymin() < n.point.y()) {
                    search(rect, inside, n.left);
                    search(rect, inside, n.right);
                }
            }
        }
    }

    private Point2D searchNearestPoint(Node n, Point2D query) {
        // StdOut.println("n: "+ n);
        // StdOut.println("query: " + query);
        // StdOut.println("n.point: " + n.point);
        // if n == null
        if (query.equals(n.point)) return n.point;
        
        double nearestDist = query.distanceSquaredTo(n.point);
        Point2D nearestPoint = n.point;

        if (n.vertical) {
            if (query.x() < n.point.x()) {
                double horizontalDist = n.point.x() - query.x();
                // StdOut.println("query: "+ query);
                // StdOut.println("n.point: "+ n.point);
                // StdOut.println("n.left: " + n.left);
                if (n.left != null) {
                    Point2D leftNearestPoint = searchNearestPoint(n.left, query);
                    double leftDist = query.distanceSquaredTo(leftNearestPoint);
                    if (leftDist < nearestDist) {
                        nearestPoint = leftNearestPoint;
                        nearestDist = leftDist;
                    }
                }
                if (horizontalDist < Math.sqrt(nearestDist) && n.right != null) {
                    Point2D leftNearestPoint = searchNearestPoint(n.right, query);
                    double leftDist = query.distanceSquaredTo(leftNearestPoint);
                    if (leftDist < nearestDist) {
                        nearestPoint = leftNearestPoint;
                        nearestDist = leftDist;
                    }
                }

            } else {
                double horizontalDist = query.x() - n.point.x();
                if (n.right != null) {
                    Point2D rightNearestPoint = searchNearestPoint(n.right, query);
                    double rightDist = query.distanceSquaredTo(rightNearestPoint);
                    if (rightDist < nearestDist) {
                        nearestPoint = rightNearestPoint;
                        nearestDist = rightDist;
                    }
                } 
                if (horizontalDist < Math.sqrt(nearestDist) && n.left != null) {
                    Point2D rightNearestPoint = searchNearestPoint(n.left, query);
                    double rightDist = query.distanceSquaredTo(rightNearestPoint);
                    if (rightDist < nearestDist) {
                        nearestPoint = rightNearestPoint;
                        nearestDist = rightDist;
                    }
                }
                
            }   
                
        } else {
            if (query.y() < n.point.y()) {

                double verticalDist = n.point.y() - query.y();
                
                if (n.left != null) {
                    Point2D leftNearestPoint = searchNearestPoint(n.left, query);
                    double leftDist = query.distanceSquaredTo(leftNearestPoint);
                    if (leftDist < nearestDist) {
                        nearestPoint = leftNearestPoint;
                        nearestDist = leftDist;
                    }
                }
                if (verticalDist < Math.sqrt(nearestDist) && n.right != null) {
                    Point2D leftNearestPoint = searchNearestPoint(n.right, query);
                    double leftDist = query.distanceSquaredTo(leftNearestPoint);
                    if (leftDist < nearestDist) {
                        nearestPoint = leftNearestPoint;
                        nearestDist = leftDist;
                    }
                }
                // StdOut.println("nn: "+ n.point);
                // StdOut.println("n.left: "+ n.left);
            } else {

                double verticalDist = query.y() - n.point.y();
                if (n.right != null) {
                    // StdOut.println("query: "+ query);
                    // StdOut.println("n.point: "+ n.point);
                    // StdOut.println("verticalDist: "+verticalDist);
                    Point2D rightNearestPoint = searchNearestPoint(n.right, query);
                    double rightDist = query.distanceSquaredTo(rightNearestPoint);
                    if (rightDist < nearestDist) {
                        nearestPoint = rightNearestPoint;
                        nearestDist = rightDist;
                    }
                }
                if (verticalDist < Math.sqrt(nearestDist) && n.left!= null) {
                    Point2D rightNearestPoint = searchNearestPoint(n.left, query);
                    double rightDist = query.distanceSquaredTo(rightNearestPoint);
                    if (rightDist < nearestDist) {
                        nearestPoint = rightNearestPoint;
                        nearestDist = rightDist;
                    }
                }
            }
        }   
        // StdOut.println("query: "+ query);
        // StdOut.println("n.point: "+ n.point);
        return nearestPoint;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p could not be null");
        if (root == null) return null;
        Point2D res = searchNearestPoint(root, p);
        return res;
    }

    

    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        // KdTree brute = new KdTree();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            // StdOut.println(kdtree.contains(p));
        }

        // StdOut.println("size() " + kdtree.size());
        // StdOut.println(kdtree.nodeList.size());
        // Point2D query = new Point2D(0.91, 0.59);
        // Point2D student = kdtree.nearest(query);
        // StdOut.println("------------------------");
        // StdOut.println("student: "+student);
        // StdOut.println(student.distanceTo(query));
        // Point2D answer = new Point2D(0.785, 0.725);
        // StdOut.println("correct: "+ answer);
        // StdOut.println(answer.distanceTo(query));

        // RectHV rect = new RectHV(0.5, 0.5,
                                 // 0.875, 1.0);
        // StdOut.println(rect);
        // RectHV rect = new RectHV(0.19, 0.29,
        //                          0.61, 0.31);

        // for (Point2D p : kdtree.range(rect)) {
            // StdOut.println(p);   
        // }

        // StdOut.println("kdtree.root: "+ kdtree.root);
        // StdOut.println("kdtree.root.point: "+ kdtree.root.point);
        // StdOut.println(kdtree.size());
        // StdOut.println(kdtree.min());
        // StdOut.println(kdtree.max());
        // StdOut.println("-----------");
        // for (Node n: kdtree.nodeList) {
            // StdOut.println(n.point);
        //     StdOut.println(n.vertical);
        // }
        // StdOut.println("-----------");

        // Point2D p1 = new Point2D(0.793893, 0.904508);
        // Node n1 = kdtree.get(p1);
        // StdOut.println(n1.point);
        // StdOut.println("n1.vertical: " + n1.vertical);
        // StdOut.printf("n1.upEnd: %f n1.downEnd: %f\n", n1.upEnd, n1.downEnd);
        // StdOut.println("n1 parent: " + n1.parent);
        // StdOut.println("n1 parent point: " + n1.parent.point);
        // StdOut.println("n1 parent right: " + n1.parent.right.point);
        
        // Point2D p1 = new Point2D(0.7, 0.2);
        // Node root = kdtree.get(p1);
        // StdOut.println("root point: " + root.point);
        // StdOut.println("root parent: " + root.parent);
        // StdOut.println("root vertical: "+ root.vertical);
        // Node n1_left = root.left;
        // StdOut.println("n1_left point: "+ n1_left.point);
        // StdOut.println("n1_left parent node: " + n1_left.parent);
        // StdOut.println("n1_left parent point: " + n1_left.parent.point);
        // StdOut.println("n1_left parent vertical: " + n1_left.parent.vertical);
        // StdOut.println("n1_left vertical: "+ n1_left.vertical);
        // Node n1_left_left = n1_left.left;
        // StdOut.println("n1_left_left point: "+ n1_left_left.point);
        // StdOut.println("n1_left_left parent node: " + n1_left_left.parent);
        // StdOut.println("n1_left_left parent point: " + n1_left_left.parent.point);
        // StdOut.println("n1_left_left parent vertical: " + n1_left_left.parent.vertical);
        // StdOut.println("n1_left_left vertical: " + n1_left_left.vertical);
        
        // Node n1_left_right = n1_left.right;
        // StdOut.println("n1_left_right point: "+ n1_left_right.point);
        // StdOut.println("n1_left_right parent point: " + n1_left_right.parent.point);
        // StdOut.println("n1_left_right parent vertical: " + n1_left_right.parent.vertical);
        // StdOut.println("n1_left_right vertical: " + n1_left_right.vertical);
        
        /*
        StdDraw.enableDoubleBuffering();
        while (true) {
            
                // StdOut.println(p2);
            StdDraw.clear();
            // StdDraw.setPenColor(StdDraw.BLACK);
            // StdDraw.setPenRadius(0.03);
            kdtree.draw();
            
            StdDraw.show();
            StdDraw.pause(40);
        }
        */
    }                 
}