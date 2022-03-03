/**
 * Name: Nikhil Kashyap(nikhilka)
 * Course: Data Structures and Algorithms 95-771
 * Assignment: Graph Coloring and Red Black Trees
 */


/**
 * This is a RedBlack Tree where each node is of type RedBlackNode. In this project we make use of RedBlack tree as a
 * dictionary where, the key is course name and value is a unique sequential non-negative integer.We implement methods
 * to insert and lookup nodes in the tree.
 *
 * Design is obtained from: https://www.andrew.cmu.edu/user/mm6/95-771/examples/DSARedBlackTreeProject/dist/javadoc/index.html
 */
public class RedBlackTree {
    /**
     * BLACK, RED - are static variables used to color the nodes of RedBlack Tree in a readable form
     * count - keeps track of number of nodes in the RedBlack Tree
     * globalCourses - contains Key of each of the nodes in the RedBlack Tree Dictionary
     * rootNode - always holds the root of the RedBlack Tree
     * NilNode - an empty node, alternative to null
     */
    static int BLACK = 1;
    static int RED = 0;
    int count;
    String[] globalCourses;
    RedBlackNode rootNode;
    RedBlackNode NilNode;

    public RedBlackTree(){
        NilNode = new RedBlackNode("Nil", -1, RedBlackTree.BLACK,NilNode, NilNode, NilNode);
        rootNode = NilNode;
        count = 0;
    }

    /**
     * Inserts a new node which holds key:value into the RedBlack Tree.
     * @param key
     *  CourseName
     * @param value
     *  A unique integer value associated to the key
     * @preconditons
     *  A tree of type RedBlackTree is created and initialized.
     *  The Key:Value pair is not already present in the tree
     * @postcondition
     *  A node holding the key:value pair is inserted into the RedBlackTree.
     */
    public void insert(String key, int value){
        RedBlackNode newNode = new RedBlackNode(key, value, RedBlackTree.RED, NilNode,NilNode,NilNode);
        RedBlackNode prev = NilNode;
        RedBlackNode iterator = rootNode;

        if(isTreeEmpty()){ // if root node is nil node
            rootNode = newNode;
        }
        else {
            while (notNilNode(iterator)) {
                prev = iterator;

                if (newNode.getCourseName().compareTo(iterator.getCourseName()) < 0) {
                    iterator = iterator.getLeftChild();
                }
                else {
                    iterator = iterator.getRightChild();
                }
            }

            if(newNode.getCourseName().compareTo(prev.getCourseName()) < 0){
                prev.setLeftChild(newNode);
            }
            else {
                prev.setRightChild(newNode);
            }
            count++;
            RBInsertFixUp(newNode);
        }
    }

    /**
     * Checks if the insertion of a new node to RedBlack Tree is violating any rule of RedBlack Tree. If violated, it
     * fixes the violations
     * @param z
     *  The node which was inserted into the RedBlack Tree
     */
    public void RBInsertFixUp(RedBlackNode z){
        RedBlackNode y;

        while(z.getParent().getColor() == RedBlackNode.RED){
            if(z.getParent() == z.getParent().getParent().getLeftChild()){
                y = z.getParent().getParent().getRightChild();

                if(y.getColor() == RedBlackNode.RED){
                    z.getParent().setColor(RedBlackNode.BLACK);
                    y.setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    z = z.getParent().getParent();
                }
                else {
                    if(z == z.getParent().getRightChild()){
                        z = z.getParent();
                        this.leftRotate(z);
                    }

                    z.getParent().setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    this.rightRotate(z.getParent().getParent());
                }
            }

            else {
                y = z.getParent().getParent().getLeftChild();

                if(y.getColor() == RedBlackNode.RED){
                    z.getParent().setColor(RedBlackNode.BLACK);
                    y.setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    z = z.getParent().getParent();
                }
                else {
                    if(z == z.getParent().getLeftChild()){
                        z = z.getParent();
                        this.rightRotate(z);
                    }

                    z.getParent().setColor(RedBlackNode.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.RED);
                    this.leftRotate(z.getParent().getParent());
                }
            }
        }

        rootNode.setColor(RedBlackNode.BLACK);
    }

    /**
     * Performs one left rotation to the tree of type RedBlackTree
     * @param x
     *  The node around which the RedBlackTree will be rotated left.
     * @preconditons
     *  The rightChild of node x is not NilNode
     *  The parent of the root node is NilNode
     */
    private void leftRotate(RedBlackNode x){
        if(notNilNode(x.getRightChild())){
            RedBlackNode y;
            y = x.getRightChild();
            x.setRightChild(y.getLeftChild());
            y.getLeftChild().setParent(x);
            y.setParent(x.getParent());

            if(x.getParent() == NilNode){
                rootNode = y;
            }
            else {
                if(x == x.getParent().getLeftChild()){
                    x.getParent().setLeftChild(y);
                }
                else {
                    x.getParent().setRightChild(y);
                }
            }

            y.setLeftChild(x);
            x.setParent(y);
        }
    }

    /**
     * Performs one right rotation to the tree of type RedBlackTree
     * @param x
     *  The node around which the RedBlackTree will be rotated right.
     * @preconditons
     *  The leftChild of node x is not NilNode
     *  The parent of the root node is NilNode
     */
    private void rightRotate(RedBlackNode x){
        if(notNilNode(x.getLeftChild())){
            RedBlackNode y;
            y = x.getLeftChild();
            x.setLeftChild(y.getRightChild());
            y.getRightChild().setParent(x);
            y.setParent(x.getParent());

            if(x.getParent() == NilNode){
                rootNode = y;
            }
            else {
                if(x == x.getParent().getLeftChild()){
                    x.getParent().setLeftChild(y);
                } else {
                    x.getParent().setRightChild(y);
                }
            }

            y.setRightChild(x);
            x.setParent(y);
        }
    }

    /**
     * Checks if the course is present in the RedBlackTree or not.
     * @param searchCourse
     *  The course to search on the RedBlackTree
     * @preconditons
     *  Assumes that the RedBlackTree is NilNode terminating. The left and right child from parent is determined by
     *  lexicographic comparison of course name the node contain.
     * @return
     *  True - if the course present, False - if the course is absent
     */
    public boolean contains(String searchCourse){
        RedBlackNode temp = rootNode;
        boolean isPresent = false;

        while (notNilNode(temp)){
            if(temp.getCourseName().equalsIgnoreCase(searchCourse)){
                isPresent = true;
                break;
            }
            else if(temp.getCourseName().compareTo(searchCourse) < 0){
                temp = temp.getRightChild();
            }
            else {
                temp = temp.getLeftChild();
            }
        }
        return isPresent;
    }

    /**
     * Given the Key, it returns the value associated with it.
     * @param courseName
     *  The key for which the value is needed
     * @preconditons
     *  The RedBlackTree is NilNode terminating. The left and right child from parent is determined by
     *  lexicographic comparison of course name the node contain.
     * @return
     *  An integer value associated with the key "courseName"
     */
    public int get(String courseName){
        RedBlackNode temp = rootNode;
        int value = -1;

        while (notNilNode(temp)){
            if(temp.getCourseName().equalsIgnoreCase(courseName)){
                value = temp.getUniqueCount();
                break;
            }
            else if(temp.getCourseName().compareTo(courseName) < 0){
                temp = temp.getRightChild();
            }
            else {
                temp = temp.getLeftChild();
            }
        }
        return value;
    }

    /**
     * Given the value, the Key associated with it is returned
     * @param value
     *  The value for which key is needed
     * @preconditons
     *  A valid RedBlackTree is constructed and rootNode tracks the root of the tree
     * @return
     *  A String Key associated with the integer value
     */
    public String getKey(int value){
        globalCourses = new String[count+1];
        traversal(rootNode);
        return globalCourses[value];
    }

    /**
     * A preOrder traversal of the tree, populated the globalCourses with the course Names
     * @param t
     *  Root of the RedBlackTree
     * @preconditons
     *  The RedBlackTree is NilNode terminating
     */
    public void traversal(RedBlackNode t){
        globalCourses[t.getUniqueCount()] = t.getCourseName();
        if(notNilNode(t.getLeftChild())){
            traversal(t.getLeftChild());
        }

        if(notNilNode(t.getRightChild())){
            traversal(t.getRightChild());
        }
    }

    public boolean isTreeEmpty(){
        return !notNilNode(rootNode);
    }

    public boolean notNilNode(RedBlackNode node){
        return !node.getCourseName().equals("Nil");
    }

    public RedBlackNode getRootNode(){
        return rootNode;
    }
}
