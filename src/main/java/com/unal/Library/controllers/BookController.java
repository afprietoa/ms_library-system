package com.unal.Library.controllers;

import com.unal.Library.models.Book;
import com.unal.Library.models.User;
import com.unal.Library.services.BookService;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("search/genre/{genre}")
    public List<Book> getListByGenre(@PathVariable("genre") String genre){
        List<Book> books = this.bookService.showListGenre(genre).get().stream()
                .limit(10)
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){

        List<Book> books = this.bookService.index().stream()
                .limit(10)
                .collect(Collectors.toList());
        return books;
    }


    @GetMapping("search/isbn/{isbn}")
    public Optional<Book> getBookByISBN(@PathVariable("isbn") String isbn) {

        long startTime = System.nanoTime();
        Optional<Book> b = this.bookService.show(isbn);

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime);
        System.out.println("Execution time: " + executionTime + " ms");
        return b;
    }

    @GetMapping("search/title/{title}")
    public List<Book> getBookByTitle(@PathVariable("title") String title) {
        List<Book> books = this.bookService.showListTitle(title).get().stream()
                .limit(10)
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping("search/author/{author}")
    public List<Book> getBookByAuthor(@PathVariable("author") String author){
        List<Book> books = this.bookService.showListAuthor(author).get().stream()
                .limit(10)
                .collect(Collectors.toList());
        return books;
    }


    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Book insertBook(@RequestBody Book book){
        return this.bookService.create(book);
    }

    @DeleteMapping("/delete/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteBook(@PathVariable("isbn") String isbn){
        return this.bookService.delete(isbn);
    }

    @PutMapping("/update/{isbn}")
    @ResponseStatus(HttpStatus.CREATED)
    public Book updateBook(@PathVariable("isbn") String isbn, @RequestBody Book book){
        return this.bookService.update(isbn, book);
    }
}
