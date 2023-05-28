package com.unal.Library.structures;

import java.util.Arrays;
import com.unal.Library.structures.Queue;

public class BinaryHeap<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 10;

    private T[] heapArray;
    private int size;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(int capacity) {
        this.heapArray = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void insert(T element) {
        if (size == heapArray.length - 1) {
            resizeHeap();
        }

        size++;
        heapArray[size] = element;
        siftUp(size);
    }

    public T findMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        return heapArray[1];
    }

    public T deleteMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        T minElement = heapArray[1];
        heapArray[1] = heapArray[size];
        size--;
        siftDown(1);

        return minElement;
    }

    private void siftUp(int index) {
        while (index > 1 && heapArray[index].compareTo(heapArray[parent(index)]) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void siftDown(int index) {
        while (leftChild(index) <= size) {
            int minIndex = getMinChildIndex(index);
            if (heapArray[index].compareTo(heapArray[minIndex]) <= 0) {
                break;
            }
            swap(index, minIndex);
            index = minIndex;
        }
    }

    private int parent(int index) {
        return index / 2;
    }

    private int leftChild(int index) {
        return index * 2;
    }

    private int rightChild(int index) {
        return 2 * index + 1;
    }

    private int getMinChildIndex(int index) {
        int leftChildIndex = leftChild(index);
        int rightChildIndex = leftChildIndex + 1;

        if (rightChildIndex > size || heapArray[leftChildIndex].compareTo(heapArray[rightChildIndex]) <= 0) {
            return leftChildIndex;
        } else {
            return rightChildIndex;
        }
    }

    private void swap(int index1, int index2) {
        T temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    private void resizeHeap() {
        int newCapacity = heapArray.length * 2;
        heapArray = Arrays.copyOf(heapArray, newCapacity);
    }

    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(1, sb);
        return sb.toString().trim();
    }

    private void preOrder(int index, StringBuilder sb) {
        if (index <= size) {
            sb.append(heapArray[index]).append(" "); // Append the current node value
            preOrder(leftChild(index), sb); // Traverse left subtree
            preOrder(rightChild(index), sb); // Traverse right subtree
        }
    }

    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(1, sb);
        return sb.toString().trim();
    }

    private void postOrder(int index, StringBuilder sb) {
        if (index <= size) {
            postOrder(leftChild(index), sb); // Traverse left subtree
            postOrder(rightChild(index), sb); // Traverse right subtree
            sb.append(heapArray[index]).append(" "); // Append the current node value
        }
    }

    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(1, sb);
        return sb.toString().trim();
    }

    private void inOrder(int index, StringBuilder sb) {
        if (index <= size) {
            inOrder(leftChild(index), sb); // Traverse left subtree
            sb.append(heapArray[index]).append(" "); // Append the current node value
            inOrder(rightChild(index), sb); // Traverse right subtree
        }
    }

    public String levelOrder() {
        StringBuilder sb = new StringBuilder();
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1); // Start with the root index

        while (!queue.empty()) {
            int size = queue.length();
            for (int i = 0; i < size; i++) {
                int index = queue.dequeue();
                sb.append(heapArray[index]).append(" "); // Append the current node value

                int leftChild = leftChild(index);
                int rightChild = rightChild(index);

                if (leftChild <= size) {
                    queue.enqueue(leftChild); // Enqueue left child
                }

                if (rightChild <= size) {
                    queue.enqueue(rightChild); // Enqueue right child
                }
            }
        }

        return sb.toString().trim();
    }

    public void insertLevel(T data) {
        if (size == heapArray.length - 1) {
            resizeHeap();
        }

        size++;
        heapArray[size] =  data;
    }


}