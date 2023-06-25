package com.unal.Library.structures;

import com.unal.Library.models.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private DoublyLinkedList<Entry<K, V>>[] table;
    private int size;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        table = new DoublyLinkedList[capacity];
        size = 0;
    }



    public void put(K key, V value) {
        int index = getIndex(key);
        DoublyLinkedList<Entry<K, V>> list = getList(index);

        Entry<K, V> entry = findEntry(list, key);
        if (entry != null) {
            entry.setValue(value);
        } else {
            entry = new Entry<>(key, value);
            list.pushBack(entry);
            size++;
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        DoublyLinkedList<Entry<K, V>> list = getList(index);

        Entry<K, V> entry = findEntry(list, key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        DoublyLinkedList<Entry<K, V>> list = getList(index);

        Entry<K, V> entry = findEntry(list, key);
        if (entry != null) {
            list.delete(entry);
            size--;
        }
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        DoublyLinkedList<Entry<K, V>> list = getList(index);

        Entry<K, V> entry = findEntry(list, key);
        return entry != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int getIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % table.length;
    }

     public DoublyLinkedList<Entry<K, V>> getList(int index) {
        if (table[index] == null) {
            table[index] = new DoublyLinkedList<>();
        }
        return table[index];
    }

    private Entry<K, V> findEntry(DoublyLinkedList<Entry<K, V>> list, K key) {
        DoublyLinkedList<Entry<K, V>>.Node<Entry<K, V>> node = list.head;
        while (node != null) {
            if (node.key.getKey().equals(key)) {
                return node.key;
            }
            node = node.next;
        }
        return null;
    }

    public List<V> getAllObjects() {
        List<V> objects = new ArrayList<>();

        for (DoublyLinkedList<Entry<K, V>> list : table) {
            if (list != null) {
                DoublyLinkedList<Entry<K, V>>.Node<Entry<K, V>> node = list.head;
                while (node != null) {
                    objects.add(node.key.getValue());
                    node = node.next;
                }
            }
        }

        return objects;
    }

    public void editObject(K key, V newValue) {
        int index = getIndex(key);
        DoublyLinkedList<Entry<K, V>> list = getList(index);

        Entry<K, V> entry = findEntry(list, key);
        if (entry != null) {
            entry.setValue(newValue);
        }
    }


    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (DoublyLinkedList<Entry<K, V>> list : table) {
            if (list != null) {
                DoublyLinkedList<Entry<K, V>>.Node<Entry<K, V>> node = list.head;
                while (node != null) {
                    keySet.add(node.key.getKey());
                    node = node.next;
                }
            }
        }

        return keySet;
    }



    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}

