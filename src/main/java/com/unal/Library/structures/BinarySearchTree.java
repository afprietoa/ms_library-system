package com.unal.Library.structures;

import com.unal.Library.models.Book;

public class BinarySearchTree<T extends Comparable<?super T>> {
    public BinaryNode<T> getRoot() {
        return this.root;
    }

    public class BinaryNode<T> {
        private BinaryNode<T> left;
        private T element;
        private BinaryNode<T> right;

        BinaryNode() {
            this(null);
        }

        BinaryNode(T data) {
            this.left = null;
            this.element = data;
            this.right = null;
        }
    }

    private BinaryNode<T> root;



    public BinarySearchTree(){
        this.root = null;
    }

    public void makeEmpty(){
        root=null;
    }

    public boolean isEmpty()
    {
        return (root == null);
    }

    public void insert(T x){
        root=insert(x,root);
    }

    private BinaryNode<T> insert(T x, BinaryNode<T> t){
        if(t==null)
            return new BinaryNode<>(x);
        if(x.compareTo(t.element)<0)
            t.left = insert(x, t.left);
        else if(x.compareTo(t.element)>0)
            t.right=insert(x, t.right);
            else
                System.out.println("Item in tree and not inserted.");
        return t;
    }

    public boolean contains(T x){
        return contains(x,root);
    }

    private boolean contains(T x, BinaryNode<T> t){
        if(t==null)
            return false;
        if(x.compareTo(t.element)<0)
            return contains(x,t.left);
        else if(x.compareTo(t.element)>0)
                return contains(x,t.left);
            else
                return true;
    }

    public T findMin(){
        if(isEmpty())
            throw new RuntimeException("Is empty!");
        return findMin(root).element;
    }

    private BinaryNode<T> findMin(BinaryNode<T> t){
        if(t == null)
            return null;
        else if(t.left == null)
            return t;
        return findMin(t.left);
    }

    public void remove(T x){
        root= remove(x, root);
    }

    private BinaryNode<T> remove(T x, BinaryNode<T> t){
        if(t==null)
            return t;
        if(x.compareTo(t.element)<0)
            t.left=remove(x, t.left);
        else if(x.compareTo(t.element)>0)
            t.right = remove(x,t.right);
            else if(t.left!=null && t.right!=null)
            {
                t.element=findMin(t.right).element;
                t.right=remove(t.element, t.right);
            }
            else
                t=(t.left!=null)?t.left:t.right;
                return t;
    }

    private int height(BinaryNode<T> t){
        if(t==null)
            return -1;
        else
            return 1+Math.max(height(t.left), height(t.right));
    }



}
