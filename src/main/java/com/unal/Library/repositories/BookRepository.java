package com.unal.Library.repositories;

import com.unal.Library.models.common.ItemStatus;
import com.unal.Library.structures.AVLTree;
import com.unal.Library.structures.BinarySearchTree;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.models.Book;
import com.unal.Library.structures.HashTable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Repository
public class BookRepository implements InterfaceRepository<Book>{

    //DoublyLinkedList<Book> books = new DoublyLinkedList<>();
    //AVLTree<Book> books = new AVLTree();
    HashTable<String, Book> books = new HashTable();
    List<Book> booksList;

    final int MIN_VALUE = 1;
    final int MAX_VALUE = 5;
    Random random = new Random();

    public BookRepository(){
        this.booksList = new ArrayList<>();
    }

    // search by isbn using doubly linked list
    /*
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
     */

    // search by isbn using avl
   /*
    public Optional<Book> findByISBN(String isbn) {
        return searchBookByISBN(books.getRoot(), isbn);
    }

    private Optional<Book> searchBookByISBN(AVLTree<Book>.Node node, String isbn) {
        if (node == null) {
            return Optional.empty();
        }

        int compareResult = isbn.compareTo(node.data.getIsbn13());
        if (compareResult == 0) {
            return Optional.of(node.data);
        } else if (compareResult < 0) {
            return searchBookByISBN(node.left, isbn);
        } else {
            return searchBookByISBN(node.right, isbn);
        }
    }
    */

    //search by isbn using a hash table
    public Optional<Book> findByISBN(String isbn){
       return Optional.of(books.get(isbn));
    }

    // search by genre using double linked list
    /*
    public Optional<List<Book>> findByGenre(String genre)
    {
        List<Book> results = new ArrayList<>();

        DoublyLinkedList<Book>.Node<Book> aux = null;

        for (aux = (DoublyLinkedList<Book>.Node<Book>) books.head; (aux != null); aux = aux.next)
        {
            for (String gen : aux.key.getCategories()){
                if (gen.toLowerCase().indexOf(genre.toLowerCase()) != -1){
                    results.add(aux.key);
                    break;
                }
            }
        }

        return Optional.of(results);

    }
     */

    /*
    public Optional<List<Book>> findByGenre(String genre) {
        List<Book> result = new ArrayList<>();
        searchBooksByGenre(books.getRoot(), genre.toLowerCase(), result);
        return Optional.of(result);
    }

    private void searchBooksByGenre(AVLTree<Book>.Node node, String keyword, List<Book> result) {
        if (node == null) {
            return;
        }

        for (String genre : node.data.getCategories()) {
            if (genre.toLowerCase().contains(keyword)) {
                result.add(node.data);
                break; // Found a matching genre, no need to check further
            }
        }

        searchBooksByGenre(node.left, keyword, result);
        searchBooksByGenre(node.right, keyword, result);
    }
     */

    // search by category using hash table
    public Optional<List<Book>> findByGenre(String categoryKeyword){

        List<Book> foundEntries = new ArrayList<>();
        DoublyLinkedList<HashTable.Entry<String, Book>> entry = new DoublyLinkedList<>();

        for (int i = 0; i < 16; i++){

            entry = books.getList(i);

            DoublyLinkedList<HashTable.Entry<String, Book>>.Node<HashTable.Entry<String, Book>> aux = null;

            for (aux = entry.head; (aux != null);
                 aux = aux.next){

                for (String genre : aux.key.getValue().getCategories()) {
                    if (genre.toLowerCase().contains(categoryKeyword.toLowerCase())) {
                        foundEntries.add(aux.key.getValue());
                        break; // Found a matching genre, no need to check further
                    }
                }

            }

        }

        return Optional.of(foundEntries);
    }



    // search by title using doubly linked list
    /*
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
     */

    // search by title using AVL
    /*
    public Optional<List<Book>> findByTitle(String keyword) {
        List<Book> result = new ArrayList<>();
        searchBooksByTitle(books.getRoot(), keyword.toLowerCase(), result);
        return Optional.of(result);
    }

    private void searchBooksByTitle(AVLTree<Book>.Node node, String keyword, List<Book> result) {
        if (node == null) {
            return;
        }

        if (node.data.getTitle().toLowerCase().contains(keyword)) {
            result.add(node.data);
        }

        searchBooksByTitle(node.left, keyword, result);
        searchBooksByTitle(node.right, keyword, result);
    }
     */

    // search by title using hash table
    public Optional<List<Book>> findByTitle(String keyword){
        List<Book> foundEntries = new ArrayList<>();
        DoublyLinkedList<HashTable.Entry<String, Book>> entry = new DoublyLinkedList<>();

        for (int i = 0; i < 16; i++){


            entry = books.getList(i);

            DoublyLinkedList<HashTable.Entry<String, Book>>.Node<HashTable.Entry<String, Book>> aux = null;

            for (aux = entry.head; (aux != null);
                 aux = aux.next){

               if (aux.key.getValue().getTitle().toLowerCase().contains(keyword.toLowerCase())){
                   foundEntries.add(aux.key.getValue());
               }

            }

        }

        return Optional.of(foundEntries);
    }

    // search by author using doubly linked list
    /*
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
     */

    // search by author using AVL
    /*
    public Optional<List<Book>> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        searchBooksByAuthor(books.getRoot(), author.toLowerCase(), result);
        return Optional.of(result);
    }

    private void searchBooksByAuthor(AVLTree<Book>.Node node, String keyword, List<Book> result) {
        if (node == null) {
            return;
        }

        for (String author : node.data.getAuthors()) {
            if (author.toLowerCase().contains(keyword)) {
                result.add(node.data);
                break; // Found a matching author, no need to check further
            }
        }

        searchBooksByAuthor(node.left, keyword, result);
        searchBooksByAuthor(node.right, keyword, result);
    }
     */

    // search by author using hash table
    public Optional<List<Book>> findByAuthor(String author) {
        List<Book> foundEntries = new ArrayList<>();
        DoublyLinkedList<HashTable.Entry<String, Book>> entry = new DoublyLinkedList<>();

        for (int i = 0; i < 16; i++){

            entry = books.getList(i);

            DoublyLinkedList<HashTable.Entry<String, Book>>.Node<HashTable.Entry<String, Book>> aux = null;

            for (aux = entry.head; (aux != null);
                 aux = aux.next){

                for (String aut : aux.key.getValue().getAuthors()) {
                    if (aut.toLowerCase().contains(author.toLowerCase())) {
                        foundEntries.add(aux.key.getValue());
                        break; // Found a matching genre, no need to check further
                    }
                }

            }

        }

        return Optional.of(foundEntries);
    }

   // findAll using Doubly
    /*
    @Override
    public List<Book> findAll() {
        return toList();
    }
     */

    // findAll using AVL
    /*
    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        getAllBooks(books.getRoot(), result);
        return result;
    }

    private void getAllBooks(AVLTree<Book>.Node node, List<Book> result) {
        if (node == null) {
            return;
        }

        result.add(node.data);
        getAllBooks(node.left, result);
        getAllBooks(node.right, result);
    }
     */

    // find all using hash table
    @Override
    public List<Book> findAll(){
        return books.getAllObjects();
    }


    @Override
    public Optional<Book> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Book newBook) {
        books.put(newBook.getIsbn13(), newBook);
    }

    // edit with doubly
    /*
    @Override
    public void edit(Book book) {
        books.find(book) = book;
    }

     */

    // edit with AVL
    /*
    @Override
    public void edit(Book oldBook, Book newBook) {
        // Search for the old book in the AVL tree
        //AVLTree<Book>.Node node = books.find(oldBook);

        // Remove the old book from the AVL tree
        books.delete(oldBook);

        // Insert the new book into the AVL tree
        books.insert(newBook);
    }
     */

    // edit with hash table
   @Override
   public void edit(Book book, Book newBook){
        books.editObject(book.getIsbn13(), book);
   }

    @Override
    public void delete(int id) {
    }

    public void erase(String isbn){
      books.remove(isbn);
    }

    // for the doubly
    /*
    public List<Book> toList(){
        for(int i = 1; i <= books.length(); i++){
            booksList.add(books.find(i));
        }
        return booksList;
    }
     */

    @PostConstruct
    private void init(){
        long startTime = System.nanoTime();
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
                    books.put(book.getIsbn13(), book);
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) ;
        System.out.println("Execution time: " + executionTime + " ms");

    }
}
