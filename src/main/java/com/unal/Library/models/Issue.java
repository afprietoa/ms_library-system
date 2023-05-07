package com.unal.Library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Issue {
    private Integer id;
    private User user;
    private Book book;
    private Date borrowDate;
    private Date dueDate;
}
