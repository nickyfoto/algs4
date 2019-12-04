import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


// import java.util.concurrent.TimeUnit;

public class SAP {

    // constructor takes a digraph (not necessarily a DAG)

    private final Digraph Gc;
    private final int size;
    // private ArrayList<Integer> vList = new ArrayList<Integer>();;
    private ArrayList<Integer> vList2 = new ArrayList<Integer>();;
    // private ArrayList<Integer> wList = new ArrayList<Integer>();;
    private ArrayList<Integer> wList2 = new ArrayList<Integer>();;
    // private int anc = -2;
    private HashMap<String, int[]> hmap = new HashMap<String, int[]>();

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("null Board is not allowed");
        // DirectedCycle dc = new DirectedCycle(G);
        // if (dc.hasCycle()) throw new IllegalArgumentException("Digraph is not DAG");
        Gc = new Digraph(G);

        size = Gc.V();
    }



    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        // StdOut.println("length v: " + v + " w: " + w);
        if (v < 0 || v > size-1) throw new IllegalArgumentException("vertex argument is outside its prescribed range");
        if (w < 0 || w > size-1) throw new IllegalArgumentException("vertex argument is outside its prescribed range");


        int[] key1 = {v, w};
        int[] key2 = {w, v};

        if (v == w) {
            int[] val = {v, 0};
            hmap.put(Arrays.toString(key1), val);
            return 0;
        };

        int[] val1 = hmap.get(Arrays.toString(key1));
        int[] val2 = hmap.get(Arrays.toString(key2));
        if (val1 != null) return val1[1];
        if (val2 != null) return val2[1];












        // vList.add(v);
        vList2.add(v);
        // wList.add(w);
        wList2.add(w);

        // ArrayList<Integer> vNeighbor = new ArrayList<Integer>();
        ArrayList<Integer> vNeighbor2 = new ArrayList<Integer>();
        // ArrayList<Integer> wNeighbor = new ArrayList<Integer>();
        ArrayList<Integer> wNeighbor2 = new ArrayList<Integer>();
        for (Integer n: Gc.adj(v)) {
            // vNeighbor.add(n);
            vNeighbor2.add(n);
        }



        for (Integer n: Gc.adj(w)) {
            // wNeighbor.add(n);
            wNeighbor2.add(n);
        }

        ArrayList<Integer> all_v_neighbors = find_all_neighbors(vList2, vNeighbor2);
        // StdOut.println("all_v_neighbors: " + all_v_neighbors);
        ArrayList<Integer> all_w_neighbors = find_all_neighbors(wList2, wNeighbor2);
        // StdOut.println("all_w_neighbors: " + all_w_neighbors);

        ArrayList<Integer> common = new ArrayList<Integer>();
        for (Integer vv : all_v_neighbors) {
            for (Integer ww: all_w_neighbors) {
                // StdOut.println("vv: " + vv + " ww: " + ww + " " + (vv-ww==0));
                if (vv - ww == 0) common.add(vv);
            }
        }

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(Gc, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(Gc, w);

        // StdOut.println("common: " + common);
        int anc2 = -2;
        int distance = Integer.MAX_VALUE;
        if (common.isEmpty()) {
            anc2 = -1;
            distance = -1;
        }

        for (Integer c : common) {
            int d = bfsv.distTo(c)+ bfsw.distTo(c);
            if (d < distance) {
                distance = d;
                anc2 = c;
            }
        }
        // StdOut.println("anc2: " +anc2);
        // StdOut.println("distance: " +distance);
        wList2 = new ArrayList<Integer>();
        vList2 = new ArrayList<Integer>();
        vNeighbor2 = new ArrayList<Integer>();
        wNeighbor2 = new ArrayList<Integer>();

        int[] val = {anc2, distance};
        hmap.put(Arrays.toString(key1), val);
        hmap.put(Arrays.toString(key2), val);


        return val[1];
        /*
        anc = ancestorR(vList, wList, vNeighbor, wNeighbor);

        if (anc == -1) {
            int[] val = {-1, -1};
            hmap.put(Arrays.toString(key1), val);
            hmap.put(Arrays.toString(key2), val);
            return -1;
        }


        // StdOut.println(bfsv.distTo(v));

        int dvw = Integer.MAX_VALUE;
        int dwv = Integer.MAX_VALUE;
        int ancDistance = bfsv.distTo(anc)+ bfsw.distTo(anc);

        if (bfsv.hasPathTo(w)) dvw = bfsv.distTo(w);
        if (bfsw.hasPathTo(v)) dwv = bfsw.distTo(v);
        // StdOut.println("dvw: "+dvw);
        // StdOut.println("dwv: "+dwv);

        if (dvw < dwv) {
            if (dvw < ancDistance) {
                anc = w;
                ancDistance = dvw;
            }
        }
        if (dwv < dvw) {
            if (dwv < ancDistance) {
                anc = v;
                ancDistance = dwv;
            }
        }


        wList = new ArrayList<Integer>();
        vList = new ArrayList<Integer>();
        vNeighbor = new ArrayList<Integer>();
        wNeighbor = new ArrayList<Integer>();
        // StdOut.println(anc);
        int[] val = {anc, ancDistance};
        hmap.put(Arrays.toString(key1), val);
        hmap.put(Arrays.toString(key2), val);
        // Arrays.toString(hmap.get(key))
        // StdOut.println(Arrays.toString(hmap.get(key)));
        return val[1];
        */

    }

    private ArrayList<Integer> find_all_neighbors(ArrayList<Integer> list,
                                                  ArrayList<Integer> neighbors) {
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (Integer i: list) {
            l.add(i);
        }
        // StdOut.println("list: "+ list);

        // StdOut.println("neighbors: "+ neighbors);
        // StdOut.println("-----------------");
        ArrayList<Integer> newNeighbors = new ArrayList<Integer>();
        for (Integer n: neighbors) {
            if (!l.contains(n)) l.add(n);
            for (Integer nn: Gc.adj(n)) {
                newNeighbors.add(nn);
            }
        }
        // if (newNeighbors.isEmpty()) return l;
        if (list.equals(l)) return l;
        // StdOut.println("list: "+ list);
        // StdOut.println("l == list: " + list.equals(l));
        // StdOut.println("neighbors: "+ neighbors);
        // StdOut.println("newNeighbors: "+ newNeighbors);
        // StdOut.println("-----------------");
        return find_all_neighbors(l, newNeighbors);
    }

    private int check(ArrayList<Integer> list, ArrayList<Integer> neighbor) {
        for (Integer i: list) {
            for (Integer j: neighbor) {
                if (i == j) return i;
            }
        }
        return -1;
    }



    private int ancestorR(ArrayList<Integer> vList,
                          ArrayList<Integer> wList,
                          ArrayList<Integer> vNeighbor,
                          ArrayList<Integer> wNeighbor) {
        StdOut.println("ancestorR: vList: "+ vList);
        StdOut.println("ancestorR: vNeighbor: "+ vNeighbor);
        StdOut.println("ancestorR: wList: "+ wList);
        StdOut.println("ancestorR: wNeighbor: "+ wNeighbor);
        StdOut.println("-----------------");
        ArrayList<Integer> nvNeighbor = new ArrayList<Integer>();
        ArrayList<Integer> nwNeighbor = new ArrayList<Integer>();



        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(Gc, vList.get(0));
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(Gc, wList.get(0));

        int ansV = check(vList, wNeighbor);
        int ansW = check(wList, vNeighbor);
        if (ansV != -1 && ansW != -1) {
            if (bfsv.distTo(ansV) + bfsw.distTo(ansV) <  bfsv.distTo(ansW) + bfsw.distTo(ansW)) {
                return ansV;
            }
            else return ansW;
        }

        if (ansV != -1 && ansW == -1) return ansV;
        if (ansW != -1 && ansV == -1) return ansW;

        for (Integer v : vList) {
            for (Integer w : wList) {
                if (v == w) return v;
            }
        }

        for (Integer v : vNeighbor) {
            for (Integer w : wNeighbor) {
                if (v == w) return v;
            }
        }



        for (Integer n: vNeighbor) {
            vList.add(n);
            for (Integer nn: Gc.adj(n)) {
                nvNeighbor.add(nn);
            }
        }
        for (Integer n: wNeighbor) {
            wList.add(n);
            for (Integer nn: Gc.adj(n)) {
                nwNeighbor.add(nn);
            }
        }
        if (nvNeighbor.isEmpty() && nwNeighbor.isEmpty()) return -1;
        return ancestorR(vList, wList, nvNeighbor, nwNeighbor);
    }


    public int ancestor(int v, int w) {
        // StdOut.println("aaa");
        if (v < 0 || v > size-1) throw new IllegalArgumentException("vertex argument is outside its prescribed range");
        if (w < 0 || w > size-1) throw new IllegalArgumentException("vertex argument is outside its prescribed range");
        wList2 = new ArrayList<Integer>();
        vList2 = new ArrayList<Integer>();
        int[] key1 = {v, w};
        int[] key2 = {w, v};
        int[] val1 = hmap.get(Arrays.toString(key1));
        int[] val2 = hmap.get(Arrays.toString(key2));
        if (val1 != null) return val1[0];
        if (val2 != null) return val2[0];
        length(v, w);
        int[] res = hmap.get(Arrays.toString(key1));
        if (res != null) return res[0];
        return -1;

    }




    private int[] length2(Iterable<Integer> v, Iterable<Integer> w) {
        int minLength = Integer.MAX_VALUE;
        int vv = -1;
        int ww = -1;
        for (int i : v) {
            for (int j : w) {
                int leng = length(i, j);
                if (leng != -1 && leng < minLength) {
                    minLength = leng;
                    vv = i;
                    ww = j;
                }
            }
        }
        int[] res = {vv, ww, minLength};
        return res;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new IllegalArgumentException("null argument is not allowed");
        if (w == null) throw new IllegalArgumentException("null argument is not allowed");
        for (Integer vv: v) {
            if (vv==null) throw new IllegalArgumentException("null argument is not allowed");
        }
        for (Integer ww: w) {
            if (ww==null) throw new IllegalArgumentException("null argument is not allowed");
        }
        // if (v.size==0) throw new IllegalArgumentException("v is empty");
        // if (w.size==0) throw new IllegalArgumentException("w is empty");
        // if (w.contains(null)) throw new IllegalArgumentException("v.contains(null)");
        if (length2(v, w)[2] == Integer.MAX_VALUE) return -1;
        return length2(v, w)[2];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new IllegalArgumentException("null argument is not allowed");
        if (w == null) throw new IllegalArgumentException("null argument is not allowed");
        for (Integer vv: v) {
            if (vv==null) throw new IllegalArgumentException("null argument is not allowed");
        }
        for (Integer ww: w) {
            if (ww==null) throw new IllegalArgumentException("null argument is not allowed");
        }
        // if (v.size==0) throw new IllegalArgumentException("v is empty");
        // if (w.size==0) throw new IllegalArgumentException("w is empty");
        int[] arr = length2(v, w);
        int vv = arr[0];
        int ww = arr[1];
        int[] key = {vv, ww};
        // StdOut.println(Arrays.toString(key));
        if (hmap.get(Arrays.toString(key))!= null) return hmap.get(Arrays.toString(key))[0];
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        // while (!StdIn.isEmpty()) {
        //     int v = StdIn.readInt();
        //     int w = StdIn.readInt();
        //     int ancestor = sap.ancestor(v, w);
        //     int length   = sap.length(v, w);
        //     StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        //     // StdOut.printf("ancestor = %d\n", ancestor);
        //     // StdOut.printf("length = %d\n", length);
        // }

        // digraph1.txt
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(3, 3)==0, sap.ancestor(3, 3)==3);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(3, 11)==4, sap.ancestor(3, 11)==1);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(9, 12)==3, sap.ancestor(9, 12)==5);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(7, 2)==4, sap.ancestor(7, 2)==0);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(1, 6)==-1, sap.ancestor(1, 6)==-1);

        // digraph3.txt
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(1, 2)==1, sap.ancestor(1, 2)==2);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(2, 1)==1, sap.ancestor(2, 1)==2);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(10, 11)==1, sap.ancestor(10, 11)==11);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(10, 1)==-1, sap.ancestor(10, 1)==-1);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(3, 6)==3, sap.ancestor(3, 6)==6);

        // digraph4.txt
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(4, 1)==3, sap.ancestor(4, 1)==4);


        // digraph5
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(21, 11)==4, sap.ancestor(21, 11)==21);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(17, 8)==4, sap.ancestor(17, 8)==17);

        // digraph6
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(0, 5) == 5, sap.ancestor(0, 5) == 4);
        // StdOut.printf("length = %s, ancestor = %s\n", sap.length(5, 0) == 5, sap.ancestor(5, 0) == 4);

        // diagraph 25
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(13, 6), sap.ancestor(13, 6));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(13, 16), sap.ancestor(13, 16));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(13, 17), sap.ancestor(13, 17));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(23, 6), sap.ancestor(23, 6));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(23, 16), sap.ancestor(23, 16));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(23, 17), sap.ancestor(23, 17));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(24, 6), sap.ancestor(24, 6));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(24, 16), sap.ancestor(24, 16));
        // StdOut.printf("length = %d, ancestor = %d\n", sap.length(24, 17), sap.ancestor(24, 17));



        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();
        // v.add(22618);
        // w.add(1323);
        // w.add(76352);
        // v.add(13);
        // v.add(23);
        // v.add(24);
        // w.add(6);
        // w.add(16);
        // w.add(17);

        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        // StdOut.printf("length = %s, ancestor = %s\n", length == 4, ancestor == 3);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // int length   = sap.length(v, w);
        // int ancestor = sap.ancestor(1, 6);
        // StdOut.printf("ancestor = %d\n", ancestor);
    }

}
