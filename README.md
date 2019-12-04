# algs4

Programming Assignments for Princeton Algorithms courses in coursera.

## Introduction

### [Percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

- A [WeightedQuickUnionUF](https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/WeightedQuickUnionUF.java) application to estimate the value of the percolation threshold via Monte Carlo simulation.

### [Deques and Randomized Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)

- Implementation of a generic data type for a deque and a randomized queue using arrays and linked lists.

### [Collinear Points](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

- A pattern recognition program. Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

### [8-puzzle](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)

- Solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.

### [Kd-Trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)

- A data type represents a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point).

### [WordNet](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)

- Build the WordNet digraph that groups words into sets of synonyms called synsets and also represent a hyponym (more specific synset) to a hypernym (more general synset) relationships between synsets.

- Outcast detection. Given a list of WordNet nouns, output the noun is the least related to the others.

### [Seam-carving](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

- image resizing technique preserves the most interest features (aspect ratio, set of objects present, etc.) of the image.

### [Baseball Elimination](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)

- Use Ford-Fulkerson Max Flow to determine which teams have been mathematically eliminated from winning their division, given the standings in a sports division at some point during the season.

### [Boggle](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)

- Use ternary search trie (TST) and recursion to solve the Boggle game.

### Burrows–Wheeler

- Implement the Burrows–Wheeler data compression algorithm.

## Development

Set up `CLASSPATH` on shell profile.

```
export CLASSPATH=$CLASSPATH:~/algs4/algs4.jar
```

or specify `cp` when compile and run the program/

```
javac -cp ".:./algs4.jar" HelloWorld.java
java -cp ".:./algs4.jar" HelloWorld
```

source code algs4 can be found at [https://github.com/kevin-wayne/algs4](https://github.com/kevin-wayne/algs4).

More setup [instruction](https://algs4.cs.princeton.edu/code/) can be found on Algorithms, 4th Edition [booksite](https://algs4.cs.princeton.edu/home/).

