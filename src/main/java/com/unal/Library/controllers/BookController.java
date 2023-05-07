package com.unal.Library.controllers;

import com.unal.Library.models.Book;
import com.unal.Library.services.BookService;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("by_genre/{genre}")
    public DoublyLinkedList<Book> getListByGenre(@PathVariable("genre") String genre){ return this.bookService.search(genre); }

    @GetMapping("/all")
    public List<Book> getAllBooks(){return  this.bookService.index();}

    @GetMapping("search/isbn/{isbn}")
    public Optional<Book> getBookByISBN(@PathVariable("isbn") String isbn) {return this.bookService.show(isbn);}

    @GetMapping("search/title/{title}")
    public Optional<List<Book>> getBookByTitle(@PathVariable("title") String title) {return this.bookService.showListTitle(title);}

    @GetMapping("search/author/{author}")
    public Optional<List<Book>> getBookByAuthor(@PathVariable("author") String author){return this.bookService.showListAuthor(author);}

}
