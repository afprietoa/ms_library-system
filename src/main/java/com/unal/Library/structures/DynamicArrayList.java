package com.unal.Library.structures;

public class DynamicArrayList<T> {
    private int capacity;
    private T list[];
    private int size;
    public DynamicArrayList() {
        this(1);
    }
    public DynamicArrayList(int capacity) {
        this.capacity = capacity;
        this.list =(T[]) new Object[capacity];
        this.size = 0;
    }

    public void print(){
        for(int i = 0; i<capacity; i++){
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }
    public void pushBack(T key){
        if(!full()){
            list[size++]=key;
        }else{
            list = resize(list);
            pushBack(key);
        }
    }
    public void pushFront(T key){
        if (!full()){
            T newList[] = (T[]) new Object[capacity];
            newList[0] = key;
            for (int i = 0; i+1<capacity;i++){
                newList[i+1] = list[i];
            }
            list = newList;
            size++;
        }else{
            list = resize(list);
            pushFront(key);
        }
    }
    public T[] resize(T[] list){
        T newList[] = (T[]) new Object[capacity*=2];
        for (int i=0; i<size; i++){
            newList[i] = list[i];
        }
        return newList;

    }
    private boolean full() {
        return size == capacity;
    }
    public void popBack(){
        if(!empty()){
            size--;
            T newList[] = (T[]) new Object[capacity];
            for (int i =0; i<size; i++){
                newList[i]=list[i];
            }
            list=newList;
        }else{
            System.out.println("NOTING!!");
            System.out.println("Your collection static list is empty.");
        }
    }
    public void popFront(){
        if(!empty()) {
            T newList[] = (T[]) new Object[capacity];
            for (int i=1; i<size; i++){
                newList[i-1] = list[i];
            }
            list = newList;
            size--;
        }else{
            System.out.println("NOTING!!");
            System.out.println("Your collection static list is empty.");
        }
    }
    public T topFront() {
        return !empty() ? this.list[0] : null;
    }
    public T topBack() {
        return !empty() ? this.list[size-1] : null;
    }
    public boolean empty() {
        return size == 0;
    }

    public int length(){
        return size;
    }

    public T find(int idx){
        return list[idx];
    }

    public int getPosition(T obj){
        int index = -1;
        int i = 0;
        while(i < this.length()) {
            if(this.list[i].equals(obj)) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

    public void delete(int key)
    {
        // put your code here

        T reList[] = (T[]) new Object[capacity];
        for(int i=0; i<size; i++){
            if(list[i].equals(key)){
                for(int k=i; k<size-1; k++){

                    reList[k] = list[k+1];

                }
                list = reList;
                break;
            }
            else{
                reList[i] = list[i];
            }
        }
        list = reList;
    }

    public void exchange(T user, int idx) {
        list[idx] = user;
    }
}
