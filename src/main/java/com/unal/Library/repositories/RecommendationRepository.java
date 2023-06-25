package com.unal.Library.repositories;

import com.unal.Library.models.Book;
import com.unal.Library.structures.Graph;
import com.unal.Library.structures.HashTable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class RecommendationRepository {

    Graph graph;

    public RecommendationRepository() {
        graph = new Graph();
    }

    public void addBook(Book book) {
        graph.addBook(book);
    }

    public void addConnection(Book bookRead, Book bookRelated) {
        graph.addEdge(bookRead, bookRelated);
        graph.addEdge(bookRelated, bookRead);
    }

    public List<Book> generateRecommendations(Book book) {
        HashTable<Book, Integer> recommendationMap = new HashTable<>();

        List<Book> relatedBooks = graph.getRelatedBooks(book);
        for (Book relatedBook : relatedBooks) {
            int weight = calculateWeight(book, relatedBook);
            recommendationMap.put(relatedBook, weight);
        }

        List<Book> recommendations = new ArrayList<>(recommendationMap.keySet());
        recommendations.sort(Comparator.comparingInt(recommendationMap::get).reversed());

        return recommendations;
    }

    private int calculateWeight(Book book1, Book book2) {
        int weight = 0;

        // Comprobación de autores
        for (String author1 : book1.getAuthors()) {
            for (String author2 : book2.getAuthors()) {
                if (author1.equals(author2)) {
                    weight += 1;
                    break; // Salir del bucle interno si se encuentra una coincidencia
                }
            }
        }

        // Comprobación de categorías
        for (String category1 : book1.getCategories()) {
            for (String category2 : book2.getCategories()) {
                if (category1.toLowerCase().contains(category2.toLowerCase())) {
                    weight += 4;
                    break; // Salir del bucle interno si se encuentra una coincidencia
                }
            }
        }

        return weight;
    }


}
