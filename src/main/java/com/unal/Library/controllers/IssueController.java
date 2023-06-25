package com.unal.Library.controllers;

import com.unal.Library.dto.IssueDTO;
import com.unal.Library.models.Issue;
import com.unal.Library.models.User;
import com.unal.Library.services.IssueService;
import com.unal.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issue")
@CrossOrigin(origins = "*")
public class IssueController {
    @Autowired
    private IssueService issueService;

    // obtener todos los issues -- si sirve
    @GetMapping("/all")
    public List<Issue> getBorrowingHistory(){
        return this.issueService.index();
    }


    // obtener prestamo por id -- no se guarda bien el id
    @GetMapping("/by_id/{id}")
    public Optional<Issue> getBorrowing(@PathVariable("id") int id){
        return this.issueService.show(id);
    }


    /*
    @GetMapping("/reserve/by_user/{idUser}")
    public List<IssueDTO> getIssueDTOOfUser(@PathVariable("idUser") int idUser){
        return this.issueService.userIssueDTO(idUser);
    }
     */

    // obtener prestamos de un usuario -- si sirve
    @GetMapping("/borrow/by_user/{idUser}")
    public List<Issue> getIssueOfUser(@PathVariable("idUser") int idUser)
    {
        return this.issueService.userIssue(idUser);
    }

    // obtener todas las reservas de un libro -- si sirve
    @GetMapping("/reserve/all/book/{idBook}")
    public List<IssueDTO> getBookingHistory(@PathVariable("idBook") String idBook){
        return this.issueService.indexDTO(idBook);
    }

    // obtener una reserva por libro y usuario -- si sirve
    @GetMapping("/reserve/by_id/book/{idBook}/user/{idUser}")
    public Optional<IssueDTO> getBooking(@PathVariable("idBook") String idBook,@PathVariable("idUser") int idUser ){
        return this.issueService.showDTO(idBook, idUser);
    }

    // crear un prestamo de un usuario a un libro -- si sirve
    @PostMapping("/borrow/user/{idUser}/book/{idBook}")
    @ResponseStatus(HttpStatus.CREATED)
    public Issue borrowBook(@PathVariable("idUser") int idUser, @PathVariable("idBook") String idBook ){
        return this.issueService.create(idUser, idBook);
    }

    // crear una reserva de un usuario a un libro -- si sirve
    @PostMapping("/reserve/user/{idUser}/book/{idBook}")
    @ResponseStatus(HttpStatus.CREATED)
    public IssueDTO reserveBook(@PathVariable("idUser") int idUser, @PathVariable("idBook") String idBook ){
        return this.issueService.insert(idUser, idBook);
    }

    // actualizar un issue -- no se
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Issue updateIssue(@PathVariable("id") int id, @RequestBody Issue issue){
        return this.issueService.update(id, issue);
    }

    // devolver un libro -- no se
    @DeleteMapping("/return/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean returnBook(@PathVariable("id") String id){
        return this.issueService.delete(id);
    }
}
