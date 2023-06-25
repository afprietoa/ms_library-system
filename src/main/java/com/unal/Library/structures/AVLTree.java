package com.unal.Library.structures;

public class AVLTree<T extends Comparable<?super T>> {

    public class Node{
        public T data;
        public Node left;
        public Node right;
        int height;


        Node(T data){
            this.data = data;
            this.height = 1;
        }
    }
    private Node root;

    public Node getRoot() {
        return this.root;
    }

    public void insert(T data){
        root = insert(root, data);
    }
    private Node insert(Node node, T data){
        if(node ==  null){
            return new Node(data);
        }
        if(data.compareTo(node.data)<0){
            node.left = insert(node.left, data);
        } else if(data.compareTo(node.data)>0){
            node.right = insert(node.right, data);
        }else{
            return node;
        }
        node.height = 1+Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void delete(T data){
        root = delete(root, data);
    }
    private Node delete(Node node, T data){
        if(node == null){
            return null;
        }
        if(data.compareTo(node.data)<0){
            node.left = delete(node.left, data);
        }else if(data.compareTo(node.data)>0){
            node.right = delete(node.right, data);
        }else{
            if(node.left == null){
                return node.right;
            }else if(node.right == null){
                return node.left;
            }else{
                Node aux = node;
                node = min(node);
                node.right = deleteMin(aux.right);
                node.left = aux.left;
            }
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }
    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node node){
        if(node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void deleteMax(){
        root = deleteMax(root);
    }
    public T min (){
        return min(root).data;
    }
    private Node min(Node node){
        if(node.left == null) return node;
        return min(node.left);
    }
    public T max (){
        return max(root).data;
    }
    private Node max(Node node){
        if(node.right == null) return node;
        return max(node.right);
    }

    private Node deleteMax(Node node){
        if(node.right == null) return node.left;
        node.right = deleteMin(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }


    private int balanceFactor(Node node){
        if(node == null) return 0;
        return height(node.left) - height(node.right);
    }
    private int height(Node node){
        if(node == null) return 0;
        return node.height;
    }

    private Node balance(Node node){
        if(balanceFactor(node)<-1){
            if(balanceFactor(node.right)>0){
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }else if(balanceFactor(node)>1){
            if(balanceFactor(node.left)<0){
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }
        return node;
    }

    private Node rotateRight(Node x){
        Node y= x.left;
        x.left = y.right;
        y.right = x;
        x.height = 1 + Math.max(height(x.left),height(x.right));
        y.height = 1 + Math.max(height(y.left),height(y.right));
        return y;
    }
    private Node rotateLeft(Node x){
        Node y=x.right;
        x.right=y.left;
        y.left=x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    public Node find(T data){
        Node node = find(root, data);
        if(node == null) return new Node(data);
        return node;
    }
    private Node find(Node node, T data){
        if(node == null) return null;
        if(data.compareTo(node.data)<0) return find(node.left, data);
        else if(data.compareTo(node.data)>0) return find(node.right, data);
        else return node;
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }


    /* ----------------------------------------------------------------------- */
    public int distanceNodes(T data1, T data2) {
        Node node1 = find(root, data1);
        Node node2 = find(root, data2);

        if (node1 == null || node2 == null) {
            return -1;  // Indica que al menos uno de los nodos no existe en el árbol
        }

        Node lca = commonAncestor(root, node1, node2);
        if (lca != null) {
            int distance1 = distanceNode(lca, node1);
            int distance2 = distanceNode(lca, node2);
            return distance1 + distance2;
        }

        return -1;  // Indica que no se encontró el ancestro común más bajo
    }

    private Node commonAncestor(Node root, Node node1, Node node2) {
        if (root == null || node1 == null || node2 == null) {
            return null;
        }

        if (root == node1 || root == node2) {
            return root;
        }

        Node left = commonAncestor(root.left, node1, node2);
        Node right = commonAncestor(root.right, node1, node2);

        if (left != null && right != null) {
            return root;
        }

        return (left != null) ? left : right;
    }

    private int distanceNode(Node root, Node target) {
        if (root == null || target == null) {
            return -1;
        }

        if (root == target) {
            return 0;
        }

        int leftDistance = distanceNode(root.left, target);
        int rightDistance = distanceNode(root.right, target);

        if (leftDistance == -1 && rightDistance == -1) {
            return -1;  // El nodo objetivo no se encontró en ningún subárbol
        } else if (leftDistance != -1) {
            return leftDistance + 1;
        } else {
            return rightDistance + 1;
        }
    }
}
