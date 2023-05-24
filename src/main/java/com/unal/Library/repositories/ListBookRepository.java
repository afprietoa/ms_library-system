package com.unal.Library.repositories;

import com.unal.Library.models.Book;
import com.unal.Library.models.ListBook;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ListBookRepository {

    DoublyLinkedList<ListBook> listsOfUsers = new DoublyLinkedList<>();

    public void createLists(ListBook lb){
        listsOfUsers.pushBack(lb);
    }
    public DoublyLinkedList<Book> save(int id_user, int list, Book book){

        // search for that user lists
        DoublyLinkedList<ListBook>.Node<ListBook> aux = null;
        ListBook result;
        DoublyLinkedList<Book> doublyLinkedList = new DoublyLinkedList<>();

        // search for user lists
        for (aux = (DoublyLinkedList<ListBook>.Node<ListBook>)
                listsOfUsers.head; (aux != null);
             aux = aux.next){

            // push in the correspond list
            if (aux.key.getUser().getId() == id_user){

                if (aux.key.getUserLists()[list] == null){
                    aux.key.getUserLists()[list] = new DoublyLinkedList<>();
                }

                aux.key.getUserLists()[list].pushBack(book);
                break;
            }

        }

        return aux.key.getUserLists()[list];

    }

    public DoublyLinkedList<Book> delete(int list, int id_user, Book book){

        DoublyLinkedList<Book> doublyLinkedList = new DoublyLinkedList<>();

        // search for that user lists
        DoublyLinkedList<ListBook>.Node<ListBook> aux = null;
        ListBook result;

        for (aux = (DoublyLinkedList<ListBook>.Node<ListBook>)
                listsOfUsers.head; (aux != null);
             aux = aux.next){

            // delete the book from the correspond list
            if (aux.key.getUser().getId() == id_user){
                doublyLinkedList = aux.key.getUserLists()[list];

                // delete the book
                doublyLinkedList.delete(book);

            }

        }

        return doublyLinkedList;

    }

    public Optional<ListBook> getListsOfUserById(int id_user){

        //buscar en la listOfUsers la que estoy buscando
        DoublyLinkedList<ListBook>.Node<ListBook> aux = null;
        ListBook result;

        for (aux = (DoublyLinkedList<ListBook>.Node<ListBook>)
                listsOfUsers.head; (aux != null);
             aux = aux.next){

            if (aux.key.getUser().getId() == id_user){
                return Optional.of(aux.key);
            }

        }
        return null;
    }

}
