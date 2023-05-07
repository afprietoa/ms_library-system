package com.unal.Library.services;

import com.unal.Library.models.Book;
import com.unal.Library.repositories.BookRepository;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    public DoublyLinkedList<Book> allBooks = new DoublyLinkedList<>();

    @Autowired
    private BookRepository bookRepository;


    public List<Book> index(){
        List<Book> resultList = (List<Book>) this.bookRepository.findAll();
        if (resultList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is not any book in the list");
        }
        return resultList;
    }

    public Optional<Book> show(String isbn){
        Optional<Book> result = this.bookRepository.findByISBN(isbn);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested book ISBN does not exist");
        }
        return result;
    }

    public Optional<List<Book>> showListTitle(String title){
        Optional<List<Book>> result = this.bookRepository.findByTitle(title);

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested title book does not match any book title");
        }
        return result;
    }

    public Optional<List<Book>> showListAuthor(String author){
        Optional<List<Book>> result = this.bookRepository.findByAuthor(author);

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested author does not match any author");
        }
        return result;
    }

    public DoublyLinkedList<Book> search(String genero){
        DoublyLinkedList<Book> results = new DoublyLinkedList<>();

        return results;
    }

    public void populateBooks(){

    }

}
