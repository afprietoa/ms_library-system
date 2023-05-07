package com.unal.Library.models;


import com.unal.Library.structures.DoublyLinkedList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    private String isbn13;
    private String isbn10;
    private String title;
    private String subTitle;
    private String[] authors;
    private String[] categories;
    private String thumbnail;
    private String description;
    private String publishedYear;
    private String averageRating;
    private Integer copies;
    private String ItemStatus;

}
