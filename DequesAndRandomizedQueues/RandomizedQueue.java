/******************************************************************************
 *  Compilation:  javac RandomQueue.java
 *  Execution:    java RandomQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  
 *  Stack implementation with a resizing array.
 *
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java RandomQueue < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on stack


    /**
     * Initializes an empty stack.
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return n;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("null item is not allowed");
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        swap();
        Item item = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    private int randomInt() {
        double r = Math.random();   // uniform between 0.0 and 1.0
        return (int) (r * n);
    }

    private void swap() {
        int tempIndex = randomInt();
        Item temp = a[tempIndex];
        a[tempIndex] = a[n-1];
        a[n-1] = temp;
        // Stdout.println(a[temp_index])
    }



    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack
     * @throws java.util.NoSuchElementException if this stack is empty
     
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[n-1];
    }
     */


    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int tempIndex = randomInt();
        return a[tempIndex];
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = n-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }
     */

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    /* an iterator, doesn't implement remove() since it's optional
    
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[i];
            i++;
            return item;
        }
    }
    */

    private Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        swap();
        Item item = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        // if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        
        private final RandomizedQueue<Integer> index = new RandomizedQueue<Integer>();  

        public ArrayIterator() {
            buildIndex();
        }

        private void buildIndex() {
            for (int i = 0; i < n; i++) {
                index.enqueue(i);
            }
        }  

        public boolean hasNext()  { return index.size() != 0;                   }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Integer itemIndex = index.peek();
            Item item = a[itemIndex];
            return item;
        }
    }
    

    /**
     * Unit tests the {@code Stack} data type.
     */
    public static void main(String[] args) {
        RandomizedQueue<String> stack = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.enqueue(item);
            else if (!stack.isEmpty()) StdOut.print(stack.dequeue() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
        // StdOut.println("(" + stack + " stack left)");
        
        for (String s : stack)
            StdOut.println(s);
    }
}
