package com.unal.Library.controllers;

import java.util.List;

import com.unal.Library.models.Book;
import com.unal.Library.models.ListBook;
import com.unal.Library.services.ListBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "http://localhost:3000")
public class ListBookController {

    @Autowired
    private ListBookService listBookService;

    @GetMapping("/get/{id_user}/{list}")
    public List<Book> getListBookByUser(@PathVariable("id_user") int id_user, @PathVariable("list") int list){
        return this.listBookService.showListOfUser(id_user, list);
    }

    @GetMapping("/add/{id_user}/{isbn}/{list}")
    public List<Book> addBookToUserList(@PathVariable("id_user") int id_user, @PathVariable("isbn") String isbn, @PathVariable("list") int list){
        return this.listBookService.addInUserList(id_user, list, isbn);
    }

    @GetMapping("/delete/{id_user}/{isbn}/{list}")
    public List<Book> deleteBookToUserList(@PathVariable("id_user") int id_user, @PathVariable("isbn") String isbn, @PathVariable("list") int list){
        return this.listBookService.deleteInUserList(id_user, list, isbn);
    }


}
