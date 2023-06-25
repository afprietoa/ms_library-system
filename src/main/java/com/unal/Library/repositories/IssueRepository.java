package com.unal.Library.repositories;


import com.unal.Library.dto.IssueDTO;
import com.unal.Library.models.Book;
import com.unal.Library.models.Issue;
import com.unal.Library.models.User;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.structures.DynamicArrayList;
import com.unal.Library.structures.Queue;
import com.unal.Library.structures.Stack;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueRepository implements InterfaceRepository<Issue> {
    Stack<Issue> issues = new Stack<>();

    List<Issue> issueList = new ArrayList<>();
    List<IssueDTO> reserveList = new ArrayList<>();

    HashMap<String,Queue<IssueDTO>> requestMap = new HashMap<>();
    @Override
    public List<Issue> findAll() {
        return toList();
    }

    public List<Issue> toList(){
        for(int i=1; i<=issues.length();i++){
            issueList.add(issues.listWithTail.find(i));
        }
        return issueList;
    }

    public List<IssueDTO> findAllDTO(String idBook) {
        return toList(idBook);
    }

    public List<IssueDTO> toList(String idBook){

        reserveList = new ArrayList<>();

        Queue reserves = requestMap.get(idBook);

        if (reserves == null) return null;

        DoublyLinkedList<IssueDTO>.Node<IssueDTO> aux = null;

        for( aux = reserves.listWithTail.head; (aux!=null); aux = aux.next)
        {
            reserveList.add(aux.key);
        }

        return reserveList;

        /*
        for (Map.Entry<String, Queue<IssueDTO>> entry :  requestMap.entrySet())
        {

            if(Objects.equals(idBook, entry.getKey()))
            {

                DoublyLinkedList<IssueDTO>.Node<IssueDTO> aux = null;

                for( aux = entry.getValue().listWithTail.head; (aux!=null); aux = aux.next)
                {
                    reserveList.add(aux.key);
                }

                return reserveList;

            }

        }
        return null;
         */
    }

    public HashMap<String, Queue<IssueDTO>> mapList(DoublyLinkedList<Book> books){
        for(int i = 1; i <= books.length(); i++){
            requestMap.put(books.find(i).getIsbn10(),new Queue<IssueDTO>());
        }
        return requestMap ;
    }

    @Override
    public Optional<Issue> findById(int id) {
        return null;
    }
    public Optional<Issue> findById(String idBook) {
        DoublyLinkedList<Issue>.Node<Issue> aux = null;
        for(
                aux = issues.listWithTail.head;
                (aux!=null) && (!(aux.key.getBook().getIsbn10()==idBook));
                aux=aux.next
        );
        return Optional.of(aux.key);
    }

    public DoublyLinkedList<Issue> findUserIssues(int idUser)
    {
        DoublyLinkedList<Issue>.Node<Issue> aux = null;
        DoublyLinkedList<Issue> userIssues = new DoublyLinkedList<>();

        for( aux = issues.listWithTail.head; (aux!=null);aux=aux.next){

            if ( aux.key.getUser().getId() == idUser)
            {
                userIssues.pushBack(aux.key);
            }
        }

        return userIssues;
    }

    public Optional<IssueDTO> findByIdDTO(String idBook, Integer idUser) {

        // obteniendo reservas
        Queue reserves = requestMap.get(idBook);

        DoublyLinkedList<IssueDTO>.Node<IssueDTO> aux = null;
        for( aux = reserves.listWithTail.head; (aux!=null); aux=aux.next) {

            if (aux.key.getUser().getId() == idUser) {

                return Optional.of(aux.key);

            }
        }

        /*
        for (Map.Entry<String, Queue<IssueDTO>> entry :  requestMap.entrySet()) {
            if(Objects.equals(idBook, entry.getKey())){

                }
            }
        }
         */
        return null;
    }

    @Override
    public void save(Issue newIssue) {
        newIssue.setId(issues.length());
        issues.push(newIssue);
    }

    public void save(IssueDTO newIssue, String idBook) {

        Queue reserves = requestMap.get(idBook);

        // si no esta el libro
        if (reserves == null){
            System.out.println("Primera vez");
            Queue newQueue = new Queue();
            newQueue.enqueue(newIssue);
            newIssue.setPlaceInQueue(0);

            requestMap.put(idBook, newQueue);
        } else {
            // si el libro ya tiene reserva
            System.out.println("Las demas veces");
            newIssue.setPlaceInQueue(reserves.length());
            reserves.enqueue(newIssue);
            requestMap.put(idBook, reserves);
        }

        /*
        for (Map.Entry<String, Queue<IssueDTO>> entry :  requestMap.entrySet()) {
            if(Objects.equals(idBook, entry.getKey())){
                newIssue.setPlaceInQueue(entry.getValue().length());
                entry.getValue().enqueue(newIssue);
            }
        }
         */
    }

    @Override
    public void edit(Issue issue, Issue newIssue) {
        issues.listWithTail.search(issue).key = issue;
    }

    @Override
    public void delete(int id) {}

    public void delete(String idBook) {
        issues.listWithTail.delete(findById(idBook).get());;
    }

    public void eliminate(String idBook) {

        for (Map.Entry<String, Queue<IssueDTO>> entry :  requestMap.entrySet()) {

            if(Objects.equals(idBook, entry.getKey())){
                entry.getValue().dequeue();
                DoublyLinkedList<IssueDTO>.Node<IssueDTO> aux = null;
                for(
                        aux = entry.getValue().listWithTail.head;
                        (aux!=null);
                        aux=aux.next
                ){
                    aux.key.setPlaceInQueue(aux.key.getPlaceInQueue()-1);
                }
            }
        }
    }

}
