package com.unal.Library.repositories;

import com.unal.Library.models.common.ItemStatus;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.models.Book;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class BookRepository implements InterfaceRepository<Book>{

    DoublyLinkedList<Book> books = new DoublyLinkedList<>();
    List<Book> booksList;

    final int MIN_VALUE = 1;
    final int MAX_VALUE = 5;
    Random random = new Random();

    public BookRepository(){
        this.booksList = new ArrayList<>();
    }

    public Optional<Book> findByISBN(String isbn){
        if (isbn.length() > 13) return null;

        DoublyLinkedList<Book>.Node<Book> aux = null;

        for (aux = (DoublyLinkedList<Book>.Node<Book>) books.head; (aux != null); aux = aux.next)
        {
            if ((aux.key.getIsbn10().equals(isbn)) || (aux.key.getIsbn13().equals(isbn)))
            {
                return Optional.of(aux.key);
            }
        }

        return null;

    }

    public Optional<List<Book>> findByTitle(String title){
        if ((title.length() < 3) || (title.length() > 50)) return null;

        DoublyLinkedList<Book>.Node<Book> aux = null;
        List<Book> results = new ArrayList<>();

        for (aux = (DoublyLinkedList<Book>.Node<Book>)
                books.head; (aux != null);
                aux = aux.next){
           if ((aux.key.getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1)){
                results.add(aux.key);
           }
        }

        return Optional.of(results);

    }

    public Optional<List<Book>> findByAuthor(String author){
        if ((author.length() < 3) || (author.length() > 50)) return null;

        DoublyLinkedList<Book>.Node<Book> aux = null;
        List<Book> results = new ArrayList<>();

        for (aux = (DoublyLinkedList<Book>.Node<Book>)
                books.head; (aux != null);
                aux = aux.next){

            for (String aut : aux.key.getAuthors()){
                if (aut.toLowerCase().indexOf(author.toLowerCase()) != -1){
                    results.add(aux.key);
                    break;
                }
            }
        }
        return Optional.of(results);

    }

    @Override
    public List<Book> findAll() {
        return toList();
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Book newBook) {
        books.pushBack(newBook);
    }

    @Override
    public void edit(Book book) {
        books.search(book).key = book;
    }

    @Override
    public void delete(int id) {
        books.delete(findById(id).get());
    }

    public List<Book> toList(){
        for(int i = 1; i <= books.length(); i++){
            booksList.add(books.find(i));
        }
        return booksList;
    }

    @PostConstruct
    private void init(){
        String path = "src/main/java/com/unal/Library/data/books.csv";
        String tuple;

        try(
                BufferedReader buffer = new BufferedReader(new FileReader(path))){
            while((tuple = buffer.readLine()) != null){
                String[] cellValues = tuple.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

                if (cellValues.length > 9){
                    Book book = new Book(
                            cellValues[0],
                            cellValues[1],
                            cellValues[2],
                            cellValues[3],
                            cellValues[4].split(";"),
                            cellValues[5].split(","),
                            cellValues[6],
                            cellValues[7],
                            cellValues[8],
                            cellValues[9],
                            MIN_VALUE+random.nextInt((MAX_VALUE+1)-MIN_VALUE),
                            String.valueOf(ItemStatus.AVAILABLE)
                    );
                    books.pushBack(book);
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }

    }


}
