package com.unal.Library.controllers;

import com.unal.Library.dto.IssueDTO;


import com.unal.Library.dto.IssueDTO;
import com.unal.Library.models.Issue;
import com.unal.Library.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/all")
    public List<Issue> getBorrowingHistory(){
        return this.issueService.index();
    }

    @GetMapping("/by_id/{id}")
    public Optional<Issue> getBorrowing(@PathVariable("id") int id){
        long startTime = System.nanoTime();

        Optional<Issue> issue = this.issueService.show(id);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");

        return issue;
    }


    @GetMapping("/reserve/all/book/{idBook}")
    public List<IssueDTO> getBookingHistory(@PathVariable("idBook") String idBook){
        return this.issueService.indexDTO(idBook);
    }

    @GetMapping("/reserve/by_id/book/{idBook}/user/{idUser}")
    public Optional<IssueDTO> getBooking(@PathVariable("idBook") String idBook, @PathVariable("idUser") int idUser ){
        return this.issueService.showDTO(idBook, idUser);
    }


    @PostMapping("/borrow/user/{idUser}/book/{idBook}")
    @ResponseStatus(HttpStatus.CREATED)
    public Issue borrowBook(@PathVariable("idUser") int idUser, @PathVariable("idBook") String idBook ){
        return this.issueService.create(idUser, idBook);
    }

    @PostMapping("/reserve/user/{idUser}/book/{idBook}")
    @ResponseStatus(HttpStatus.CREATED)
    public IssueDTO reserveBook(@PathVariable("idUser") int idUser, @PathVariable("idBook") String idBook ){
        return this.issueService.insert(idUser, idBook);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Issue updateIssue(@PathVariable("id") int id, @RequestBody Issue issue){
        return this.issueService.update(id, issue);
    }

    @DeleteMapping("/return/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean returnBook(@PathVariable("id") String id){
        return this.issueService.delete(id);
    }
}
