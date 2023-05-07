package com.unal.Library.dto;

import com.unal.Library.models.Book;
import com.unal.Library.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IssueDTO {
    private Integer id;
    private User user;
    private Book book;
    private Date InitialRequestDate;
    private Date FinalRequestDate;
    private Integer placeInQueue;
}