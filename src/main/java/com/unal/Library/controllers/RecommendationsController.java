package com.unal.Library.controllers;

import com.unal.Library.models.Book;
import com.unal.Library.services.ListBookService;
import com.unal.Library.services.RecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@CrossOrigin(origins = "*")
public class RecommendationsController {

    @Autowired
    private RecommendationsService recommendationsService;

    @GetMapping("user/{idUser}")
    public List<Book> getUserRecommendations(@PathVariable("idUser") int id_user){
        return this.recommendationsService.generateRecommendations(id_user);
    }

}
