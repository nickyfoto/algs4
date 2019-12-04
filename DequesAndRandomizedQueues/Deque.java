import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.lang.IllegalArgumentException;
// import edu.princeton.cs.algs4.StdOut;
// import com.javamex.classmexer.MemoryUtil;

public class Deque<Item> implements Iterable<Item> {


    
    
    private int n;              // number of elements on queue
    private Node first;         // beginning of queue
    // private Node oldlast;
    private Node last;          // end of queue


    private class Node {
        private Node prev;
        private Item item;
        private Node next;

        // public String toString() {
        //     // if (item == null) return "item: null";
        //     return "[item: " + item + "]";
        // }
    }


    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?                           
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }                       

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("null item is not allowed");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.prev = first;
        n++;
        if (size() == 1) last = first;
        // StdOut.println("addFirst: " + oldfirst.item);
        // StdOut.println("addFirst: " + oldfirst.prev);
        // StdOut.println("addFirst: " + oldfirst.next);
        
    }         

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("null item is not allowed");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }         

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;
        first.prev = null;
        first.item = null;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }               

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = last.item;
        last.item = null;
        n--;
        if (size() == 0) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }                

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }        

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        
        private Node current = first;  // node containing current item

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("not implement remove()");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("You already iterate all items");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // public void inspect(Node node) {
    //     if (node != null) {
    //         if (node.prev != null) StdOut.print("[prev: " + node.prev.item);
    //         else StdOut.print("[prev: null");
    //         StdOut.print(", item: " + node.item);
    //         if (node.next != null) StdOut.println(", next: " + node.next.item + "]");
    //         else StdOut.println(", next: null]");
    //     } else StdOut.println("is null");
    // }

    
    // unit testing (optional)
    public static void main(String[] args) {
        /*
        Deque<String> d = new Deque<String>();
        // long initBytes = MemoryUtil.deepMemoryUsageOf(d);
        // StdOut.println(initBytes);
        // for (int i=0; i < 17; i++) {
            // d.addLast("a");
            // StdOut.println(MemoryUtil.deepMemoryUsageOf(d));
            // StdOut.println(noBytes);
            // StdOut.println(d.size());
        // }
        // StdOut.println("------");
        // StdOut.println(MemoryUtil.deepMemoryUsageOf(d));
        // for (int i=0; i < 17; i++) {
            // d.removeLast();
            // d.removeFirst();
            // d.removeLast();
            // StdOut.println(d.size());
            // StdOut.println(d.isEmpty());
            // StdOut.println(MemoryUtil.deepMemoryUsageOf(d));
        // }

               
        d.addFirst("a");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        
        d.addFirst("b");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");


        d.addFirst("c");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        // StdOut.println("removeLast: " + d.removeLast());
        // StdOut.print("first: ");
        // d.inspect(d.first);
        // StdOut.print("last:  ");
        // d.inspect(d.last);
        // StdOut.println("isEmpty: " + d.isEmpty());
        // StdOut.println("Queue size: " + d.size());
        // StdOut.println("-----------");

        // StdOut.println("removeLast: " + d.removeLast());
        // StdOut.print("first: ");
        // d.inspect(d.first);
        // StdOut.print("last:  ");
        // d.inspect(d.last);
        // StdOut.println("isEmpty: " + d.isEmpty());
        // StdOut.println("Queue size: " + d.size());
        // StdOut.println("-----------");

        // StdOut.println("removeLast: " + d.removeLast());
        // StdOut.print("first: ");
        // d.inspect(d.first);
        // StdOut.print("last:  ");
        // d.inspect(d.last);
        // StdOut.println("isEmpty: " + d.isEmpty());
        // StdOut.println("Queue size: " + d.size());
        // StdOut.println("-----------");



        d.addLast("a");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");


        d.addLast("b");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");


        d.addLast("c");
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");


        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        
        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");

        StdOut.println("removeFirst: " + d.removeFirst());
        StdOut.print("first: ");
        d.inspect(d.first);
        StdOut.print("last:  ");
        d.inspect(d.last);
        StdOut.println("isEmpty: " + d.isEmpty());
        StdOut.println("Queue size: " + d.size());
        StdOut.println("-----------");


        d.addFirst("a");
        d.removeFirst();
        d.addLast("a");
        d.removeLast();

        StdOut.println("test Exception ------");

        try {
            d.addFirst(null);
        } 
        catch (Exception ex) {
            StdOut.println(ex);
        }
        


        try {
            d.removeLast(); 
        }
        catch (Exception ex) {
            StdOut.println(ex);
        }

        try {
            d.removeFirst(); 
        }
        catch (Exception ex) {
            StdOut.println(ex);
        }
        
        */


        // Iterator<String> iterator = d.iterator();
        // while (iterator.hasNext())
        // {
        //    String str = iterator.next();
        //    StdOut.println(str);
        // }



        
        
    }  

}