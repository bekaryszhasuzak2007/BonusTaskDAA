
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static class BSTNode {
        int value;
        BSTNode left;
        BSTNode right;

        BSTNode(int value) {
            this.value = value;
        }
    }

    static class BST {
        BSTNode root;

        void insert(int value) {
            root = insertNode(root, value);
        }

        BSTNode insertNode(BSTNode node, int value) {
            if (node == null) {
                return new BSTNode(value);
            }

            if (value < node.value) {
                node.left = insertNode(node.left, value);
            } else if (value > node.value) {
                node.right = insertNode(node.right, value);
            }

            return node;
        }

        boolean search(int value) {
            BSTNode node = root;

            while (node != null) {
                if (node.value == value) {
                    return true;
                }

                if (value < node.value) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }

            return false;
        }

        void delete(int value) {
            root = deleteNode(root, value);
        }

        BSTNode deleteNode(BSTNode node, int value) {
            if (node == null) {
                return null;
            }

            if (value < node.value) {
                node.left = deleteNode(node.left, value);
            } else if (value > node.value) {
                node.right = deleteNode(node.right, value);
            } else {
                if (node.left == null) {
                    return node.right;
                }

                if (node.right == null) {
                    return node.left;
                }

                BSTNode min = node.right;

                while (min.left != null) {
                    min = min.left;
                }

                node.value = min.value;
                node.right = deleteNode(node.right, min.value);
            }

            return node;
        }
    }

    static class HashTable {

        static class Entry {
            int key;
            Entry next;

            Entry(int key) {
                this.key = key;
            }
        }

        Entry[] table;

        HashTable(int size) {
            table = new Entry[size];
        }

        int hash(int key) {
            return Math.floorMod(key, table.length);
        }

        void insert(int key) {
            int index = hash(key);
            Entry current = table[index];

            while (current != null) {
                if (current.key == key) {
                    return;
                }

                current = current.next;
            }

            Entry entry = new Entry(key);
            entry.next = table[index];
            table[index] = entry;
        }

        boolean search(int key) {
            int index = hash(key);
            Entry current = table[index];

            while (current != null) {
                if (current.key == key) {
                    return true;
                }

                current = current.next;
            }

            return false;
        }

        void delete(int key) {
            int index = hash(key);
            Entry current = table[index];
            Entry previous = null;

            while (current != null) {
                if (current.key == key) {
                    if (previous == null) {
                        table[index] = current.next;
                    } else {
                        previous.next = current.next;
                    }

                    return;
                }

                previous = current;
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {

        int[] sizes = {100, 1000, 10000};
        int repeats = 5;
        Random random = new Random(42);

        String header =
                "Size,BST Insert,BST Search,BST Delete,"
                        + "Hash Insert,Hash Search,Hash Delete";

        System.out.println(header);

        try (FileWriter writer = new FileWriter("results/results.csv")) {

            writer.write(header + "\n");

            for (int size : sizes) {

                long bstInsertTotal = 0;
                long bstSearchTotal = 0;
                long bstDeleteTotal = 0;
                long hashInsertTotal = 0;
                long hashSearchTotal = 0;
                long hashDeleteTotal = 0;

                for (int run = 0; run < repeats; run++) {

                    int[] values = new int[size];

                    for (int i = 0; i < size; i++) {
                        values[i] = i;
                    }

                    // Shuffle values
                    for (int i = size - 1; i > 0; i--) {
                        int j = random.nextInt(i + 1);

                        int temp = values[i];
                        values[i] = values[j];
                        values[j] = temp;
                    }

                    BST bst = new BST();
                    HashTable hashTable =
                            new HashTable(size / 2 + 1);

                    // BST insertion
                    long start = System.nanoTime();

                    for (int value : values) {
                        bst.insert(value);
                    }

                    bstInsertTotal += System.nanoTime() - start;

                    // BST searching
                    start = System.nanoTime();

                    for (int value : values) {
                        if (!bst.search(value)) {
                            throw new RuntimeException(
                                    "BST search failed"
                            );
                        }
                    }

                    bstSearchTotal += System.nanoTime() - start;

                    // BST deletion
                    start = System.nanoTime();

                    for (int value : values) {
                        bst.delete(value);
                    }

                    bstDeleteTotal += System.nanoTime() - start;

                    // Hash Table insertion
                    start = System.nanoTime();

                    for (int value : values) {
                        hashTable.insert(value);
                    }

                    hashInsertTotal += System.nanoTime() - start;

                    // Hash Table searching
                    start = System.nanoTime();

                    for (int value : values) {
                        if (!hashTable.search(value)) {
                            throw new RuntimeException(
                                    "Hash Table search failed"
                            );
                        }
                    }

                    hashSearchTotal += System.nanoTime() - start;

                    // Hash Table deletion
                    start = System.nanoTime();

                    for (int value : values) {
                        hashTable.delete(value);
                    }

                    hashDeleteTotal += System.nanoTime() - start;
                }

                long avgBstInsert = bstInsertTotal / repeats;
                long avgBstSearch = bstSearchTotal / repeats;
                long avgBstDelete = bstDeleteTotal / repeats;
                long avgHashInsert = hashInsertTotal / repeats;
                long avgHashSearch = hashSearchTotal / repeats;
                long avgHashDelete = hashDeleteTotal / repeats;

                String result =
                        size + ","
                                + avgBstInsert + ","
                                + avgBstSearch + ","
                                + avgBstDelete + ","
                                + avgHashInsert + ","
                                + avgHashSearch + ","
                                + avgHashDelete;

                System.out.println(result);
                writer.write(result + "\n");
            }

            System.out.println("Results saved to results/results.csv");

        } catch (IOException e) {
            System.out.println(
                    "Could not save results: " + e.getMessage()
            );
        }

        System.out.println("All tests passed!");
    }
}
