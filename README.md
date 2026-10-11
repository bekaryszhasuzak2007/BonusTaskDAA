# BST and Hash Table Analysis

## 1. Project Description

This project compares two data structures: Binary Search Tree (BST) and Hash Table.

The project is written in Java.

Both data structures support three operations:
- Insertion — add an element.
- Searching — find an element.
- Deletion — remove an element.

## 2. Data Structures

### Binary Search Tree (BST)

BST is a tree structure. Smaller values go to the left, and larger values go to the right.

### Hash Table

A Hash Table uses a hash function to find the position of an element. If two elements have the same position, they are stored in a linked list. This is called chaining.

## 3. Time Complexity

Here, n is the number of elements.

| Operation | BST Average | BST Worst | Hash Table Average | Hash Table Worst |
|---|---|---|---|---|
| Insertion | O(log n) | O(n) | O(1) | O(n) |
| Searching | O(log n) | O(n) | O(1) | O(n) |
| Deletion | O(log n) | O(n) | O(1) | O(n) |

BST average complexity assumes a reasonably balanced tree. A normal BST can become unbalanced. Hash Table average complexity assumes a good hash distribution and a reasonable load factor.

## 4. Experiments

The program tests three input sizes:
- 100 elements
- 1,000 elements
- 10,000 elements

Each experiment is repeated five times. The program calculates the average execution time in nanoseconds.

The results are saved in `results/results.csv`.

## 5. Experimental Results

The following results were measured on my computer.

| Size | BST Insert (ns) | BST Search (ns) | BST Delete (ns) | Hash Insert (ns) | Hash Search (ns) | Hash Delete (ns) |
|---:|---:|---:|---:|---:|---:|---:|
| 100 | 172380 | 27040 | 33360 | 27400 | 13640 | 18200 |
| 1,000 | 188380 | 149540 | 196020 | 82960 | 64700 | 63780 |
| 10,000 | 1797000 | 1190640 | 1550980 | 353160 | 286760 | 285940 |

Execution time can change between runs because of JVM and computer performance.

## 6. Conclusion

In my experiments, the Hash Table was faster than the BST for all three operations.

The Hash Table usually works in constant time when the hash function distributes elements well.

The BST keeps elements in sorted order. Its performance depends on the shape of the tree.

Both data structures have advantages. The best choice depends on the task.

## 7. How to Run

1. Open the project in IntelliJ IDEA.
2. Run `Main.java`.
3. Check the results in the console.
4. Open `results/results.csv` to see the saved measurements.

## 8. Project Structure

```text
BonusTask/
├── src/
│   └── Main.java
├── results/
│   └── results.csv
├── .gitignore
└── README.md
```