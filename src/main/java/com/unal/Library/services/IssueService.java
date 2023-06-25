package com.unal.Library.services;

import com.unal.Library.dto.IssueDTO;
import com.unal.Library.models.Book;
import com.unal.Library.models.Issue;
import com.unal.Library.models.User;
import com.unal.Library.models.common.ItemStatus;
import com.unal.Library.repositories.BookRepository;
import com.unal.Library.repositories.IssueRepository;
import com.unal.Library.repositories.UserRepository;
import com.unal.Library.structures.DoublyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;


    /**
     *
     * @return
     */
    public List<Issue> index(){
        List<Issue> resultList = (List<Issue>) this.issueRepository.findAll();
        if (resultList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is not any issue in the list.");
        return  resultList;
    }

    /**
     * @param id
     * @return
     */
    public Optional<Issue> show(int id){
        Optional<Issue> result = this.issueRepository.findById(id);
        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested issue.id does not exist.");
        return  result;
    }

    /**
     * @param idBook
     * @return
     */
    public List<IssueDTO> indexDTO(String idBook){
        List<IssueDTO> resultList = (List<IssueDTO>) this.issueRepository.findAllDTO(idBook);
        if (resultList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is not any issue in the list.");
        return  resultList;
    }

    public List<Issue> userIssue(int idUser)
    {
        // find the issues of the user
        DoublyLinkedList<Issue> issues = this.issueRepository.findUserIssues(idUser);

        // doubly -> list
        DoublyLinkedList<Issue>.Node<Issue> aux = null;
        List<Issue> result = new ArrayList<>();

        for( aux = issues.head; (aux != null); aux = aux.next){

            result.add(aux.key);
        }

        return result;
    }

    /*
    public List<IssueDTO> userIssuDTO(int idUser)
    {
        // retornar la lista de IssuesDtos del usuario
    }
    */
    /**
     * @param idBook
     * @param idUser
     * @return
     */
    public Optional<IssueDTO> showDTO(String idBook, Integer idUser){
        Optional<IssueDTO> result = this.issueRepository.findByIdDTO(idBook, idUser);
        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested issue.id does not exist.");
        return  result;
    }

    /**
     *
     * @return
     */
    public Issue create(int idUser, String idBook){

        Issue newIssue = new Issue();
        Optional<User> user = this.userRepository.findById(idUser);
        Optional<Book> book = this.bookRepository.findByISBN(idBook);

        Date borrowDate = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 15);

        newIssue.setUser(user.get());
        Date dueDate = calendar.getTime();

        newIssue.setBorrowDate(borrowDate);
        newIssue.setDueDate(dueDate);

        if(book.get().getCopies()!=0){
            newIssue.setBook(book.get());
            book.get().setCopies(book.get().getCopies()-1);
            //this.bookRepository.edit(book.get(), book.get());

        }else{
            book.get().setItemStatus(String.valueOf(ItemStatus.UNAVAILABLE));
            this.bookRepository.edit(book.get(), book.get());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "The book does not available");
        }


        if(newIssue.getId() != null){
            Optional<Issue> tempUser = this.issueRepository.findById(newIssue.getId());
            if(tempUser.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ID is yet in the database.");
        }
        if((newIssue.getUser() != null) && (newIssue.getBook() != null) &&
                (newIssue.getBorrowDate() != null) && (newIssue.getDueDate() != null) ){
            this.issueRepository.save(newIssue);
            return newIssue;
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mandatory fields had not been provided.");
    }
    /**
     *
     * @return
     */
    public IssueDTO insert(int idUser, String idBook){

        Optional<Book> book = this.bookRepository.findByISBN(idBook);
        Optional<User> user = this.userRepository.findById(idUser);

        IssueDTO newIssue = new IssueDTO();
        if(book.get().getCopies()==0){

            Date borrowDate = new Date(System.currentTimeMillis());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 15);


            Date dueDate = calendar.getTime();

            newIssue.setInitialRequestDate(borrowDate);
            newIssue.setFinalRequestDate(dueDate);

            newIssue.setUser(user.get());
            newIssue.setBook(book.get());

            book.get().setItemStatus(String.valueOf(ItemStatus.IN_RESERVE));
            this.bookRepository.edit(book.get(), book.get());


        }else{
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "The book does not available");
        }
        if(newIssue.getId() != null){
            Optional<Issue> tempUser = this.issueRepository.findById(newIssue.getId());
            if(tempUser.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ID is yet in the database.");
        }
        if((newIssue.getUser() != null) && (newIssue.getBook() != null) &&
                (newIssue.getInitialRequestDate() != null) && (newIssue.getFinalRequestDate() != null) ){
            this.issueRepository.save(newIssue, idBook);
            return newIssue;
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mandatory fields had not been provided.");

    }
    /**
     *
     * @param id
     * @param issue
     * @return
     */
    public  Issue update(int id, Issue issue){
        if(id > 0){
            Optional<Issue> tempIssue = this.issueRepository.findById(id);
            if(tempIssue.isPresent()){
                if(issue.getBook() != null)
                    tempIssue.get().setBook(issue.getBook());
                if (issue.getUser() != null)
                    tempIssue.get().setUser(issue.getUser());
                if (issue.getBorrowDate() != null)
                    tempIssue.get().setBorrowDate(issue.getBorrowDate());
                if (issue.getDueDate() != null)
                    tempIssue.get().setDueDate(issue.getDueDate());
                this.issueRepository.edit(issue, tempIssue.get());
                return tempIssue.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User.id does not exist in database.");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id cannot be negative.");
        }
    }

    /**
     *
     * @param idBook
     * @return
     */
    public Boolean delete(String idBook){
        Optional<Book> book = this.bookRepository.findByISBN(idBook);
        if(idBook!=null) {
            this.issueRepository.delete(idBook);

            if(!indexDTO(idBook).isEmpty()){
                book.get().setCopies(book.get().getCopies()+1);

                if(book.get().getCopies()>0){
                    this.issueRepository.eliminate(idBook);
                }
            }

            return true;
        }else
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User cannot be deleted.");
    }

}
