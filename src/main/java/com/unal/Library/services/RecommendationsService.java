package com.unal.Library.services;

import com.unal.Library.models.Book;
import com.unal.Library.models.ListBook;
import com.unal.Library.models.User;
import com.unal.Library.repositories.BookRepository;
import com.unal.Library.repositories.ListBookRepository;
import com.unal.Library.repositories.RecommendationRepository;
import com.unal.Library.repositories.UserRepository;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.structures.HashTable;
import org.springframework.beans.factory.annotation.Autowired;
import com.unal.Library.structures.Graph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationsService {

    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ListBookRepository listBookRepository;


    public List<Book> generateRecommendations(int idUser){

        DoublyLinkedList readBooks = listBookRepository.getListsOfUserById(idUser).get().getUserLists()[1];

        if (readBooks.empty()) return new ArrayList<>();

        // Agregar libros al sistema de recomendaciones
        for (Book book : bookRepository.findAll()) {
            recommendationRepository.addBook(book);
        }

        // Establecer conexiones entre los libros
        for (Book book : bookRepository.findAll()) {
            // Obtener los libros relacionados de la HashTable
            // y establecer conexiones en el sistema de recomendaciones
            List<Book> relatedBooks = getRelatedBooksFromHashTable(book);
            for (Book relatedBook : relatedBooks) {
                recommendationRepository.addConnection(book, relatedBook);
            }
        }

        DoublyLinkedList<Book>.Node<Book> aux = null;
        List<Book> recommendations = new ArrayList<>();

        for (aux = (DoublyLinkedList<Book>.Node<Book>)
                readBooks.head; (aux != null);
             aux = aux.next){

           recommendations.addAll(recommendationRepository.generateRecommendations(aux.key).subList(0,4));

        }

        return recommendations;

    }


    private List<Book> getRelatedBooksFromHashTable(Book book) {
        List<Book> relatedBooks = new ArrayList<>();

        for (Book otherBook : bookRepository.findAll()) {
            // Evitar que el libro se relacione consigo mismo
            if (!otherBook.getIsbn13().equals(book.getIsbn13())) {
                // Verificar si comparten algún género o autor en común
                if (hasCommonGenre(book, otherBook) || hasCommonAuthor(book, otherBook)) {
                    relatedBooks.add(otherBook);
                }
            }
        }

        return relatedBooks;
    }

    private static boolean hasCommonGenre(Book book1, Book book2) {
        String[] genres1 = book1.getCategories();
        String[] genres2 = book2.getCategories();

        // Verificar si hay algún género en común
        for (String genre1 : genres1) {
            for (String genre2 : genres2) {
                if (genre1.toLowerCase().contains(genre2.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasCommonAuthor(Book book1, Book book2) {
        String[] authors1 = book1.getAuthors();
        String[] authors2 = book2.getAuthors();

        // Verificar si hay algún autor en común
        for (String author1 : authors1) {
            for (String author2 : authors2) {
                if (author1.equals(author2)) {
                    return true;
                }
            }
        }

        return false;
    }


}
