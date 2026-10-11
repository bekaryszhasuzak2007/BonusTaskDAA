
import java.util.Random;

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
        Random random = new Random(42);

        System.out.println(
                "Size,BST Insert,BST Search,BST Delete,"
                        + "Hash Insert,Hash Search,Hash Delete"
        );

        for (int size : sizes) {

            int[] values = new int[size];

            for (int i = 0; i < size; i++) {
                values[i] = i;
            }

            for (int i = size - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);

                int temp = values[i];
                values[i] = values[j];
                values[j] = temp;
            }

            BST bst = new BST();
            HashTable hashTable = new HashTable(size / 2 + 1);

            long start = System.nanoTime();

            for (int value : values) {
                bst.insert(value);
            }

            long bstInsert = System.nanoTime() - start;

            start = System.nanoTime();

            for (int value : values) {
                if (!bst.search(value)) {
                    throw new RuntimeException("BST search failed");
                }
            }

            long bstSearch = System.nanoTime() - start;

            start = System.nanoTime();

            for (int value : values) {
                bst.delete(value);
            }

            long bstDelete = System.nanoTime() - start;

            start = System.nanoTime();

            for (int value : values) {
                hashTable.insert(value);
            }

            long hashInsert = System.nanoTime() - start;

            start = System.nanoTime();

            for (int value : values) {
                if (!hashTable.search(value)) {
                    throw new RuntimeException("Hash search failed");
                }
            }

            long hashSearch = System.nanoTime() - start;

            start = System.nanoTime();

            for (int value : values) {
                hashTable.delete(value);
            }

            long hashDelete = System.nanoTime() - start;

            System.out.println(
                    size + ","
                            + bstInsert + ","
                            + bstSearch + ","
                            + bstDelete + ","
                            + hashInsert + ","
                            + hashSearch + ","
                            + hashDelete
            );
        }

        System.out.println("All tests passed!");
    }
}