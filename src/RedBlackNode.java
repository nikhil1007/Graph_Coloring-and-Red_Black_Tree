/**
 * @author Nikhil Kashyap
 */

/**
 * This class represents a single node in the RedBlackTree.
 * Design is obtained from: https://www.andrew.cmu.edu/user/mm6/95-771/examples/DSARedBlackTreeProject/dist/javadoc/index.html
 */
public class RedBlackNode {
    /**
     * BLACK, RED - are static variables used to color the nodes of RedBlack Tree in a readable form
     * leftChild - points to the left child of the current node in the RedBlackTree
     * rightChild - points to the right child of the current node in the RedBlackTree
     * parent - points to the parent of the current node in the RedBlackTree
     * uniqueCount - a unique integer value associated with the key: courseName
     * color - the color of the node in the RedBlackTree - RED OR BLACK
     * courseName - Key of the dictionary, holds a string value: Name of the course
     */
    static int BLACK = 1;
    static int RED = 0;
    private RedBlackNode leftChild;
    private RedBlackNode rightChild;
    private RedBlackNode parent;
    private int uniqueCount;
    private int color;
    private String courseName;


    public RedBlackNode(String courseName, int uniqueCount, int color, RedBlackNode lc, RedBlackNode rc, RedBlackNode p){
        setCourseName(courseName);
        setUniqueCount(uniqueCount);
        setLeftChild(lc);
        setRightChild(rc);
        setParent(p);
        setColor(color);
    }

    //-------------------------------------- Getters and Setters for instance variable --------------------------------
    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    public RedBlackNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(RedBlackNode leftChild) {
        this.leftChild = leftChild;
    }

    public RedBlackNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(RedBlackNode rightChild) {
        this.rightChild = rightChild;
    }

    public int getUniqueCount() {
        return uniqueCount;
    }

    public void setUniqueCount(int uniqueCount) {
        this.uniqueCount = uniqueCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String toString(){ return uniqueCount + " : " + courseName; }

}
