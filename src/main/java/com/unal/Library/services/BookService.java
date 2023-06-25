package com.unal.Library.services;

import com.unal.Library.models.Book;
import com.unal.Library.models.User;
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

    public Optional<List<Book>> showListGenre(String genre){
        Optional<List<Book>> result = this.bookRepository.findByGenre(genre);

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested author does not match any author");
        }

        return result;
    }

    public Book create(Book newBook){
        /*
        System.out.println(newBook.getIsbn13());
        if(newBook.getIsbn13() != null){
            Optional<Book> tempBook = this.bookRepository.findByISBN(newBook.getIsbn13());
            if(tempBook != null && tempBook.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ISBN is yet in the database.");
            }
        }
         */
        if((newBook.getCategories() != null) && (newBook.getAuthors() != null) &&
                (newBook.getCopies() != null)){
            this.bookRepository.save(newBook);
            return newBook;
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mandatory fields had not been provided.");
    }

    public Boolean delete(String isbn){
        if (isbn.length() == 13){
            this.bookRepository.erase(isbn);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "User cannot be deleted.");
    }

    public Book update(String isbn, Book book){
        if(isbn.length() == 13) {
            Optional<Book> tempBook = this.bookRepository.findByISBN(isbn);
            if (tempBook.isPresent()) {
                this.bookRepository.edit(book, book);
                return book;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User.id does not exist in database.");
            }

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User.id cannot be negative.");
        }
    }
}
