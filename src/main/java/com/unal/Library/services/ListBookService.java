package com.unal.Library.services;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.unal.Library.models.Book;
import com.unal.Library.models.ListBook;
import com.unal.Library.models.User;
import com.unal.Library.repositories.BookRepository;
import com.unal.Library.repositories.IssueRepository;
import com.unal.Library.repositories.ListBookRepository;
import com.unal.Library.repositories.UserRepository;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListBookService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ListBookRepository listBookRepository;

    public ListBookService(ListBookRepository listBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.listBookRepository = listBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> showListOfUser(int id_user, int list) {
        // get the user lists
        Optional<ListBook> listBook = listBookRepository.getListsOfUserById(id_user);

        if (listBook == null)
        {
            return new ArrayList<>();
        }

        // get the requested list
        DoublyLinkedList<Book> books = listBook.get().getUserLists()[list];

        if (books == null) {
            System.out.println("Lista Vacia");
            return new ArrayList<>(); // Return an empty list instead of throwing an error
        }

        List<Book> result = new ArrayList<>();
        DoublyLinkedList<Book>.Node<Book> aux = null;

        for (aux = (DoublyLinkedList<Book>.Node<Book>) books.head; aux != null; aux = aux.next) {
            result.add(aux.key);
        }

        return result;
    }


    public List<Book> addInUserList(int id_user, int list, String isbn){

        // searching if the user lists already have lists
        Optional<ListBook> listBook = listBookRepository.getListsOfUserById(id_user);

        // if not
        if (listBook == null){
            // create lists

            System.out.println("Lista no existe");
            // get the user
            Optional<User> user = userRepository.findById(id_user);

            ListBook lb = new ListBook();
            lb.setUser(user.get());

            listBookRepository.createLists(lb);
            listBook = Optional.of(lb);
        }

        DoublyLinkedList<Book> books = listBookRepository.save(id_user, list, bookRepository.findByISBN(isbn).get());

        List<Book> result = new ArrayList<>();
        DoublyLinkedList<Book>.Node<Book> aux = null;

        for (aux = (DoublyLinkedList<Book>.Node<Book>)
                books.head; (aux != null);
             aux = aux.next){

            result.add(aux.key);

        }

        return result;

    }

    public List<Book> deleteInUserList(int id_user, int list, String isbn){

        DoublyLinkedList<Book> books = listBookRepository.delete(list, id_user, bookRepository.findByISBN(isbn).get());

        List<Book> result = new ArrayList<>();
        DoublyLinkedList<Book>.Node<Book> aux = null;

        for (aux = (DoublyLinkedList<Book>.Node<Book>)
                books.head; (aux != null);
             aux = aux.next){

            result.add(aux.key);

        }

        return result;

    }


}
