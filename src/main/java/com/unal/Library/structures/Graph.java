package com.unal.Library.structures;

import com.unal.Library.models.Book;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private HashTable<Book, List<Book>> adjacencyList;

    public Graph() {
        adjacencyList = new HashTable<>();
    }

    public void addBook(Book book) {
        adjacencyList.put(book, new ArrayList<>());
    }

    public void addEdge(Book book1, Book book2) {
        List<Book> relatedBooks1 = adjacencyList.get(book1);
        relatedBooks1.add(book2);

        List<Book> relatedBooks2 = adjacencyList.get(book2);
        relatedBooks2.add(book1);
    }

    public List<Book> getRelatedBooks(Book book) {
        return adjacencyList.get(book);
    }
}