/*
 * Node.java
 */
package huffmantree;

import java.io.Serializable;

/**
 * File name:   Node.java <br>      
 * Class Name:  Node <br>
 * Description: Huffman Tree Node class - Character Node for Huffman Tree
 * from a stored huffman tree.<br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Node implements Comparable, Serializable{
    private Object data;
    private int frequency;
    private Node left;
    private Node right;
    private Node parent;
    public String code;
    public int codeBits;
    
    /**
     * Main Constructor
     *      set data to null
     *      set frequency to 0
     *      set left to null
     *      set right to null.
     */
    public Node(){
        this(null);
    }
    
    /**
     * Main Constructor
     *      set frequency to 0
     *      set left to null
     *      set right to null.
     * @param data to set to Node
     */
    public Node(Object data){
        this(data, 0, null, null);
    }
    
    /**
     * Main Constructor
     *      set left to null
     *      set right to null.
     * @param data to set to Node
     * @param frequency of Node
     */
    public Node(Object data, int frequency){
        this(data, frequency, null, null);
    }
    
    /**
     * Main Constructor.
     * @param data to set to Node
     * @param frequency of Node
     * @param left this Node's left Child
     * @param right this Node's right Child
     */
    public Node( Object data, int frequency, Node left, Node right){
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * get Node Data.
     * @return data of this Node
     */
    public Object getData() {
        return data;
    }

    /**
     * set Data.
     * @param newData Object to set to Data
     */
    public void setData(Object newData) {
        data = newData;
    }

    /**
     * return Parent Node.
     * @return Node of parent.
     */
    public Node getParent(){
        return parent;
    }
    
    /**
     * get left child Node.
     * @return Node of left child.
     */
    public Node getLeftChild() {
        return left;
    }

    /**
     * get right child Node.
     * @return Node of right child.
     */
    public Node getRightChild() {
        return right;
    }
    
    /**
     * get Frequency of Node
     * @return int frequency of Node.
     */
    public int getFrequency(){
        return frequency;
    }
    
    /**
     * set frequency of Node
     * @param i int frequency.
     */
    public void setFrequency(int i){
        frequency = i;
    }

    /**
     * set parent Node
     * @param p Parent Node
     */
    public void setParent(Node p){
        parent = p;
    }
    
    /**
     * set left child Node.
     * @param leftChild new left Node child
     */
    public void setLeftChild(Node leftChild) {
        left = leftChild;
    }

    /**
     * set right child Node.
     * @param leftChild new right Node child
     */
    public void setRightChild(Node rightChild) {
        right = rightChild;
    }

    /**
     * gets parent state of this node.
     * @return true if Node has parent
     */
    public boolean hasParent(){
        return parent != null;
    }
    
    /**
     * gets left child state if this Node.
     * @return true is Node has left child.
     */
    public boolean hasLeftChild() {
        return left != null;
    }

    /**
     * gets right child state if this Node.
     * @return true is Node has right child.
     */
    public boolean hasRightChild() {
        return right != null;
    }

    /**
     * gets data state if this Node.
     * @return true if data is null, non null data have children.
     */
    public boolean isLeaf() {
        return data != null;
    }

    /**
     * getNumberOfNodes Not Implemented
     * throws UnsupportedOperationException.
     * @return int - not implemented
     */
    public int getNumberOfNodes() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * getHeight Not Implemented
     * throws UnsupportedOperationException.
     * @return int - not implemented
     */
    public int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * copy Not Implemented
     * throws UnsupportedOperationException.
     * @return Node - not implemented
     */
    public Node copy() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Compares node frequency, this frequency - passed in Node frequency.
     * @param o Node to compare to
     * @return int == 0 if equal, int < 0 if passed in Node is greater than
     * this Node, int > 0 if passed in Node is less than this Node.
     */
    @Override
    public int compareTo(Object o) {
        Node in = (Node) o;
        return frequency - in.getFrequency();
    }
    
    /**
     * Recursive in order tree traversal - build Node mapping
     * run after successful tree build.
     * @param depth tree depth, start traversal with 0
     * @param currentCode String map to Node in tree
     */
    public void BuildCode( int depth, String currentCode )
    {
          code = currentCode;
          codeBits = depth;

          // Give '0' for left
          if(left != null)
              left.BuildCode(depth+1, code + "0");
          
          // Give '1' for right
          if(right != null)
              right.BuildCode(depth+1, code + "1");
     }
        
     
}
