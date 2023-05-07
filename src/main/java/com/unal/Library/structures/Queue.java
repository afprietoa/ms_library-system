package com.unal.Library.structures;

public class Queue<T>{
    public DoublyLinkedList<T> listWithTail = new DoublyLinkedList<T>();

    public void enqueue(T key){
        listWithTail.pushBack(key);
    }
    public T dequeue(){
        T aux = listWithTail.topFront();
        listWithTail.popFront();
        return aux;
    }
    public Boolean empty(){
        return listWithTail.empty();
    }

    public void print(){
        listWithTail.print();
    }

    public int length(){
        return listWithTail.length();
    }
    public T top(){
        return listWithTail.topFront();
    }
}
