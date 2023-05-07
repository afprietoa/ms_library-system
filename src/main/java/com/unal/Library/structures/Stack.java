package com.unal.Library.structures;

public class Stack<T> {
    public DoublyLinkedList<T> listWithTail = new DoublyLinkedList<T>();

    public void push(T key){
        listWithTail.pushBack(key);
    }
    public T top(){
        return listWithTail.topBack();
    }
    public T pop(){
        T aux = listWithTail.topBack();
        listWithTail.popBack();
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
}
