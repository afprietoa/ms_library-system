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
public class ListBook {

    private int id;
    private User user;
    private DoublyLinkedList<Book>[] userLists = new DoublyLinkedList[3];

}
