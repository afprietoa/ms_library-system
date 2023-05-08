package com.unal.Library.structures;

public class SinglyLinkedList<T> {
    public class Node<T>{
        public T key;
        public Node<T> next;
        Node(T key){
            this.key = key;
            this.next = null;
        }
    }

    public Node<T> head;
    private Node<T> tail;
    private int length;

    public SinglyLinkedList(){
        this.length = 0;
    }

    public void pushFront(T key){
        Node<T> node = new Node<T>(key);
        node.next = head;
        head = node;
        length++;
        if(node.next == null)
            tail= node;
    }
    public void popFront(){
        if(head == null){
            System.out.println("NOTING!!");
            System.out.println("Your collection static list is empty.");
        }
        head = head.next;
        length--;
        if(head == null){
            tail = null;
        }

    }
    public void pushBack(T key){
        Node<T> node = new Node<T>(key);
        if(tail == null){
            head = tail = node;
        }else{
            tail.next = node;
            tail = node;
        }
        length++;
    }
    public void popBack(){
        if(head == null){
            System.out.println("NOTING!!");
            System.out.println("Your collection static list is empty.");
        }
        if(head == tail){
            head = tail = null;
        }else{
            Node<T> aux = head;
            while(aux.next.next != null){
                aux = aux.next;
            }
            aux.next = null;
            tail = aux;
        }
        length--;
    }
    public T topFront(){
        return head != null ? head.key : null;
    }
    public T topBack(){
        return tail != null ? tail.key : null;
    }

    public int length() {
        return length;
    }
    public Boolean empty(){
        return head == null;
    }
    public void addAfter(int position, T key){
        if(position<0){
            throw new IndexOutOfBoundsException();
        } else if (position==0) {
            pushFront(key);
        }else{
            Node<T> node = new Node<T>(key);
            Node<T> aux = head;
            for (int i=0; i<position-1; i++){
                if(aux == null)
                    throw new IndexOutOfBoundsException();
                aux = aux.next;
            }
            if(aux.next == null){
                pushBack(key);
            }else{
                node.next = aux.next;
                aux.next = node;
            }
        }
    }
    public void addAfter(Node<T> node, T key){
        Node<T> node2 = new Node<T>(key);
        node2.next = node.next;
        node.next = node2;
        if (tail == node){
            tail = node2;
        }
    }
    public void addBefore(int position, T key){
        if(position<0){
            throw new IndexOutOfBoundsException();
        } else if (position==0) {
            pushFront(key);
        }else{
            Node<T> node = new Node<T>(key);
            Node<T> aux = head;
            for (int i=0; i<position-2; i++){
                if(aux == null)
                    throw new IndexOutOfBoundsException();
                aux = aux.next;
            }
            if(aux.next == null){
                pushBack(key);
            }else{
                node.next = aux.next;
                aux.next = node;
            }
        }
    }
    public void addBefore(Node<T> node, T key){
        Node<T> node2 = new Node<T>(key);

        if (head == node){
            head = node;
        }else{
            Node<T> aux = head;
            while(!aux.next.next.equals(node)){
                aux = aux.next;
            }
            node2.next = aux.next;
            aux.next = node2;
        }
    }
    public void erase(int position){
        if(position<0){
            throw new IndexOutOfBoundsException();
        }
        if(position == 0){
            popFront();
        }else{
            Node<T> aux = head;
            for (int i=0; i<position-1; i++){
                if(aux == null)
                    throw new IndexOutOfBoundsException();
                aux = aux.next;
            }
            if(aux == tail){
                popBack();
            }else{
                aux.next = aux.next.next;
            }
        }
    }
    public T find(int position){
        if(position<0){
            throw new IndexOutOfBoundsException();
        }
        Node<T> aux= head;
        for(int i=0; i<position-1; i++){
            if(aux == null)
                throw new IndexOutOfBoundsException();
            aux = aux.next;
        }
        return aux.key;
    }

    public void print(){
        Node<T> aux = head;
        while(aux != null){
            System.out.print(aux.key + " ");
            aux = aux.next;
        }
        System.out.println();
    }
    public int size(){
        int n=0;
        for(
                Node<T> aux = head;
                aux!=null;
                aux=aux.next
        ){n++;}
        return n;
    }

    public Node<T> search(T key){
        Node<T> aux=null;
        for(
                aux = head;
                (aux!=null) && (!aux.key.equals(key));
                aux=aux.next
        );
        return aux;
    }
    public int getPosition(T key){
        Node<T> aux = head;
        int pos=0;
        while(aux != null){

            if(aux.key==key){
                return pos;
            }
            pos+=1;
            aux = aux.next;

        }
        return Integer.MAX_VALUE;
    }
}
